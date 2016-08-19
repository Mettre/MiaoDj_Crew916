package cn.chenhai.miaodj_monitor.views.activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.color.ColorChooserDialog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.flyco.tablayout.SlidingTabLayout;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;

import cn.chenhai.miaodj_monitor.AppManager;
import cn.chenhai.miaodj_monitor.MyApplication;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesObjectUtils;
import cn.chenhai.miaodj_monitor.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.Account;
import cn.chenhai.miaodj_monitor.model.entity.NewVersionEntity;
import cn.chenhai.miaodj_monitor.network_proxy.HttpMethods;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.theme.ColorUiUtil;
import cn.chenhai.miaodj_monitor.theme.Theme;
import cn.chenhai.miaodj_monitor.utils.NetUtils;
import cn.chenhai.miaodj_monitor.utils.PreUtils;
import cn.chenhai.miaodj_monitor.utils.ThemeUtils;
import cn.chenhai.miaodj_monitor.views.BaseActivity;
import cn.chenhai.miaodj_monitor.views.base.BaseLazyMainFragment;
import cn.chenhai.miaodj_monitor.views.base.BaseMainFragment;
import cn.chenhai.miaodj_monitor.views.event.TabSelectedEvent;
import cn.chenhai.miaodj_monitor.views.fragment.bottom.BottomBar;
import cn.chenhai.miaodj_monitor.views.fragment.bottom.BottomBarTab;
import cn.chenhai.miaodj_monitor.views.fragment.home.MultiFirstFragment;
import cn.chenhai.miaodj_monitor.views.fragment.home.HomeFragment;
import cn.chenhai.miaodj_monitor.views.fragment.personal.DialogNewVersion;
import cn.chenhai.miaodj_monitor.views.fragment.personal.MultiSecondFragment;
import cn.chenhai.miaodj_monitor.views.fragment.personal.PersonalFragment;
import cn.chenhai.miaodj_monitor.widget.ResideLayout;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by ChenHai--霜华 on 2016/5/27. 11:00
 * 邮箱：248866527@qq.com
 */

public class MainActivity extends SupportActivity
        implements View.OnClickListener ,ColorChooserDialog.ColorCallback ,BaseLazyMainFragment.OnBackToFirstListener,PersonalFragment.BackToFirstListener{

    private static final int REQ_START_PERSONAL_FOR_RESULT = 1099;

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    private SupportFragment[] mFragments = new SupportFragment[2];
    private BottomBar mBottomBar;

    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    private SubscriberOnSuccessListener mOnSuccessGetNewVersion;

    private static final String FRAGMENT_TAGS = "fragmentTags";
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;
    private RadioGroup group;

    private DrawerLayout mDrawer;
    private NavigationView mNavigationView;
    private ImageView mAvatar;
    private TextView mDesc;
    private TextView mUpdate;
    private TextView mFeedback;
    private TextView mSetting;
    private TextView mAbout;
    private TextView mTheme;
    private ResideLayout mResideLayout;

    private SimpleDraweeView mSimpleDraweeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AppManager.getAppManager().addActivity(this);
        onPreCreate();
        setStatusBar();

        setContentView(R.layout.activity_main);

//        if (savedInstanceState == null) {
//            start(HomeFragment.newInstance());
//        }
//        //在自己的应用初始Activity中加入如下两行代码
//        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
//        refWatcher.watch(this);

        if (savedInstanceState == null) {
            //start(HomeFragment.newInstance());
            currIndex = 0;

            mFragments[FIRST] = MultiFirstFragment.newInstance();
            mFragments[SECOND] = MultiSecondFragment.newInstance();

            loadMultipleRootFragment(R.id.fragment_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[FIRST] = findFragment(MultiFirstFragment.class);
            mFragments[SECOND] = findFragment(MultiSecondFragment.class);
        }

        initView();
    }


    private void setStatusBar(){
        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            //tintManager.setStatusBarTintResource(R.color.status_bar_bg);//通知栏所需颜色
            //tintManager.setStatusBarTintResource(color);//通知栏所需颜色
            tintManager.setStatusBarTintColor(0x06220000);//通知栏所需颜色
        }
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    private void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Red:
                setTheme(R.style.RedTheme);
                break;
            case Green:
                setTheme(R.style.GreenTheme);
                break;
            case Orange:
                setTheme(R.style.OrangeTheme);
                break;
            case Black:
                setTheme(R.style.BlackTheme);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消请求
        //RequestManager.cancelRequest(getName());

    }
    /**
     * --------------------------------------------------------------------------------------
     */


    private void initView() {
//        group = (RadioGroup) findViewById(R.id.group);
//        RadioButton radioHome = (RadioButton) findViewById(R.id.foot_bar_home);
//        RadioButton radioUser = (RadioButton) findViewById(R.id.foot_bar_user);
//
//        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                //处理resideLayout 和 ViewPager的滑动冲突
//                getResideLayout().setIsNeedSlide(true);
//                switch (checkedId) {
//                    case R.id.foot_bar_home:
//                        currIndex = 0;
//                        break;
//                    case R.id.foot_bar_quick:
//                        currIndex = 1;
//                        break;
//                    case R.id.foot_bar_selection:
//                        currIndex = 2;
//                        break;
//                    case R.id.foot_bar_user:
//                        currIndex = 3;
//                        break;
//                    default:
//                        break;
//                }
//                changeFragment();
//            }
//        });
        mBottomBar = (BottomBar) findViewById(R.id.bottomBar);

        mBottomBar.addItem(new BottomBarTab(this, R.drawable.widget_bar_home_bg,"我的工地"))
                .addItem(new BottomBarTab(this, R.drawable.widget_bar_user_bg,"个人中心"));

        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);

                /********处理resideLayout 和 ViewPager的滑动冲突******/
                if (position == 0) {
                    MultiFirstFragment multifragment = (MultiFirstFragment)mFragments[position];
                    //HomeFragment fragment = (HomeFragment)mFragments[position];
                    //HomeFragment fragment = (HomeFragment)multifragment.getTopChildFragment();
                    HomeFragment fragment = multifragment.findChildFragment(HomeFragment.class);
                    if(fragment != null) {
                        ViewPager temPage = fragment.getViewPager();
                        SlidingTabLayout temTab = fragment.getTabLayout();
                        if (temPage != null && temTab != null) {
                            //temTab.setupWithViewPager(temPage);
                            temPage.setCurrentItem(0);
                        }
                    }
                }
//                else if(position == 1){
//                    MultiSecondFragment multifragment = (MultiSecondFragment)mFragments[position];
//                    //HomeFragment fragment = (HomeFragment)mFragments[position];
//                    //HomeFragment fragment = (HomeFragment)multifragment.getTopChildFragment();
//                    PersonalFragment fragment = multifragment.findChildFragment(PersonalFragment.class);
//                    if(fragment != null) {
//                        fragment.refreshData();
//                    }
//                }
                /*****************************************************/
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();

                // 如果不在该类别Fragment的主页,则回到主页;
                if (count > 1) {
                    if (currentFragment instanceof MultiFirstFragment) {
                        currentFragment.popToChild(HomeFragment.class, false);
                    } else if (currentFragment instanceof MultiSecondFragment) {
                        currentFragment.popToChild(PersonalFragment.class, false);
                    }
                    return;
                }


                // 这里推荐使用EventBus来实现 -> 解耦
                if (count == 1) {
                    EventBus.getDefault().post(new TabSelectedEvent(position));
                }
            }
        });
        mResideLayout = (ResideLayout) findViewById(R.id.resideLayout);

        mDesc = (TextView) findViewById(R.id.desc);
        mDesc.setOnClickListener(this);
        mUpdate = (TextView) findViewById(R.id.update);
        mUpdate.setOnClickListener(this);
        mFeedback = (TextView) findViewById(R.id.feedback);
        mFeedback.setOnClickListener(this);
        mSetting = (TextView) findViewById(R.id.setting);
        mSetting.setOnClickListener(this);
        mAbout = (TextView) findViewById(R.id.about);
        mAbout.setOnClickListener(this);
        mTheme = (TextView) findViewById(R.id.theme);
        mTheme.setOnClickListener(this);


        PreferencesObjectUtils objectUtils = new PreferencesObjectUtils(MainActivity.this,"Login_Account");
        Account.CrewBean accountCrew = objectUtils.getObject("account_login",Account.CrewBean.class);


        //创建SimpleDraweeView对象
        mSimpleDraweeView = (SimpleDraweeView) findViewById(R.id.main_sdv);
        String headImg = "http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg";

        if(accountCrew!=null){
            mDesc.setText(accountCrew.getReal_name());
            if(accountCrew.getHeadimg()!=null){
                headImg = HttpMethods.BASE_ROOT_URL + accountCrew.getHeadimg();
            }
        }

        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(headImg);
        //开始下载
        mSimpleDraweeView.setImageURI(imageUri);

        //创建DraweeController
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                //加载的图片URI地址
                .setUri(imageUri)
                //设置点击重试是否开启
                .setTapToRetryEnabled(true)
                //设置旧的Controller
                .setOldController(mSimpleDraweeView.getController())
                //构建
                .build();

        //设置DraweeController
        mSimpleDraweeView.setController(controller);



        mOnSuccessGetNewVersion = new SubscriberOnSuccessListener<HttpResult<NewVersionEntity>>() {
            @Override
            public void onSuccess(HttpResult<NewVersionEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(MainActivity.this,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(MainActivity.this);
                } else {
                    NewVersionEntity.VersionBean beanInfo = result.getInfo().getVersion();

                    String downPath = "";
                    if(beanInfo.getPath() != null) {
                        downPath = beanInfo.getPath();
                    }

                    if (NetUtils.isVersionUpdate(MainActivity.this, beanInfo)) {

                        boolean isAutoUpdate = false;
                        if(beanInfo.getEnforceUpdate() != null) {
                            isAutoUpdate = beanInfo.getEnforceUpdate().equals("Y");
                        }

                        DialogNewVersion dialog = new DialogNewVersion(MainActivity.this);
                        dialog.show();
                        dialog.setTitle("发现新版本");
                        dialog.setVersionMessage("最新版本："+beanInfo.getVersion());
                        dialog.setMessage(isAutoUpdate ? "您需要更新版本才能使用!"
                                : "检测到新版本,是否更新");
                        dialog.setCancelable(!isAutoUpdate);
                        dialog.setCanceledOnTouchOutside(!isAutoUpdate);
                        dialog.setSubmitDoing("立即更新", downPath);

                        dialog.showCancelBtn(!isAutoUpdate);
                        dialog.setCancelDoing(null);
                        dialog.setSubmitText("立即更新");
                        dialog.setCancelText("以后再说");
                    } else {
                        new SweetAlertDialog(MainActivity.this,SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("提示")
                                .setContentText("已是最新版本！")
                                .setConfirmText("知道了")
                                .show();
                    }

                }
            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){

            }
        };

    }

    @Override
    public void onClick(View v){

        int id = v.getId();
        mResideLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (id) {
                    case R.id.main_sdv:
                        break;
                    case R.id.desc:
                        mResideLayout.closePane();
                        //start(PersonalInfoEditFragment.newInstance());
                        break;
                    case R.id.update:
                        //版本更新检查
                        if (NetUtils.hasNetWorkConection(MainActivity.this)) {
                            //检测更新
                            //UmengUpdateAgent.update(this);
                            HttpMethods.getInstance().getNewVersion(new ProgressSubscriber(mOnSuccessGetNewVersion, MainActivity.this));

                        } else {
                            new SweetAlertDialog(MainActivity.this,SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("提示")
                                    .setContentText("请检查网络设置！")
                                    .setConfirmText("知道了")
                                    .show();

                        }
                        mResideLayout.closePane();
                        //start(PersonalInfoEditFragment.newInstance());
                        break;
                    case R.id.feedback:
                        //意见反馈
                        Bundle bundle5 = new Bundle();
                        bundle5.putString("FragmentName", "PersonalFeedbackFragment");
                        bundle5.putString("ProjectCode","测试单号！");
                        Intent intent5 = new Intent(MainActivity.this, PersonalActivity.class);
                        intent5.putExtras(bundle5);
                        startActivityForResult(intent5,REQ_START_PERSONAL_FOR_RESULT);
                        mResideLayout.closePane();
                        //start(PersonalInfoEditFragment.newInstance());
                        break;
                    case R.id.setting:
                        //系统设置
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("FragmentName", "PersonalSettingFragment");
                        bundle2.putString("ProjectCode","测试单号！");
                        Intent intent2 = new Intent(MainActivity.this, PersonalActivity.class);
                        intent2.putExtras(bundle2);
                        startActivityForResult(intent2,REQ_START_PERSONAL_FOR_RESULT);
                        mResideLayout.closePane();
                        //start(PersonalInfoEditFragment.newInstance());
                        break;

                    case R.id.about:
                        //关于我们
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("FragmentName", "PersonalAboutUSFragment");
                        bundle3.putString("ProjectCode","测试单号！");
                        Intent intent3 = new Intent(MainActivity.this, PersonalActivity.class);
                        intent3.putExtras(bundle3);
                        startActivity(intent3);

                        break;
                    case R.id.theme:
                        new ColorChooserDialog.Builder(MainActivity.this, R.string.theme)
                                .customColors(R.array.colors, null)
                                .doneButton(R.string.done)
                                .cancelButton(R.string.cancel)
                                .allowUserColorInput(false)
                                .allowUserColorInputAlpha(false)
                                .show();
                        break;
                }
            }
        },300);

    }

    @Override
    public void onColorSelection(@NonNull ColorChooserDialog dialog, @ColorInt int selectedColor) {
        if (selectedColor == ThemeUtils.getThemeColor(this, R.attr.colorPrimary))
            return;
        //EventBus.getDefault().post(new SkinChangeEvent());

        if (selectedColor == getResources().getColor(R.color.colorBluePrimary)) {
            setTheme(R.style.BlueTheme);
            PreUtils.setCurrentTheme(this, Theme.Blue);

        } else if (selectedColor == getResources().getColor(R.color.colorRedPrimary)) {
            setTheme(R.style.RedTheme);
            PreUtils.setCurrentTheme(this, Theme.Red);

        } else if (selectedColor == getResources().getColor(R.color.colorGreenPrimary)) {
            setTheme(R.style.GreenTheme);
            PreUtils.setCurrentTheme(this, Theme.Green);

        } else if (selectedColor == getResources().getColor(R.color.colorOrangePrimary)) {
            setTheme(R.style.OrangeTheme);
            PreUtils.setCurrentTheme(this, Theme.Orange);

        } else if (selectedColor == getResources().getColor(R.color.colorBlackPrimary)) {
            setTheme(R.style.BlackTheme);
            PreUtils.setCurrentTheme(this, Theme.Black);

        }


        final View rootView = getWindow().getDecorView();
        rootView.setDrawingCacheEnabled(true);
        rootView.buildDrawingCache(true);

        final Bitmap localBitmap = Bitmap.createBitmap(rootView.getDrawingCache());
        rootView.setDrawingCacheEnabled(false);
        if (null != localBitmap && rootView instanceof ViewGroup) {
            final View tmpView = new View(getApplicationContext());
            tmpView.setBackgroundDrawable(new BitmapDrawable(getResources(), localBitmap));
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) rootView).addView(tmpView, params);
            tmpView.animate().alpha(0).setDuration(400).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    ColorUiUtil.changeTheme(rootView, getTheme());
                    System.gc();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    ((ViewGroup) rootView).removeView(tmpView);
                    localBitmap.recycle();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            }).start();
        }


        //用于刷新activity，fragment颜色
//        HomeFragment fragment = findFragment(HomeFragment.class);
//        if(fragment!=null){
//            fragment.getAdapter().notifyDataSetChanged();
//        }
        this.reload();

    }

    //重启Activity
    public void reload() {

//        Intent intent = getIntent();
//        overridePendingTransition(0, 0);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        finish();
//        overridePendingTransition(0, 0);
//        startActivity(intent);
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }



    public ResideLayout getResideLayout(){
        return mResideLayout;
    }

    private void changeFragment() {
//        if (currIndex == 3) {
//            UIHelper.showLogin(MainActivity.this);
//        }

        final SupportFragment topFragment = getTopFragment();
        if (currIndex == 0) {

            HomeFragment fragment = findFragment(HomeFragment.class);
            /********处理resideLayout 和 ViewPager的滑动冲突******/
            ViewPager temPage = fragment.getViewPager();
            SlidingTabLayout temTab = fragment.getTabLayout();
            if(temPage != null && temTab != null){
                //temTab.setupWithViewPager(temPage);
                temPage.setCurrentItem(0);
            }
            /*****************************************************/
            Bundle newBundle = new Bundle();
            newBundle.putString("from", "主页-->来自:" + topFragment.getClass().getSimpleName());
            fragment.putNewBundle(newBundle);

            start(fragment, SupportFragment.SINGLETASK);
        } else if (currIndex == 3) {
            PersonalFragment fragment = findFragment(PersonalFragment.class);
            if (fragment == null) {
                popTo(HomeFragment.class, false, new Runnable() {
                    @Override
                    public void run() {
                        start(PersonalFragment.newInstance());
                    }
                });
            } else {
                // 如果已经在栈内,则以SingleTask模式start
                start(fragment, SupportFragment.SINGLETASK);
            }
        }


    }


    /**
     * --------------------------------------------------------------------------------------
     */

    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
        //return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
        // 设置自定义动画
//        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }



 //   @Override
//    public void onBackPressed() {
////        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
////            mDrawer.closeDrawer(GravityCompat.START);
////        }
//        if (mResideLayout.isOpen()) {
//            mResideLayout.closePane();
//        } else {
//
////            Fragment topFragment = getTopFragment();
////            // 主页的Fragment
////            if (topFragment instanceof DiscoverFragment || topFragment instanceof ShopFragment) {
////                mNavigationView.setCheckedItem(R.id.nav_home);
////            }
//            super.onBackPressed();
//        }
//    }

    @Override
    public void onBackPressedSupport() {
        // 对于 4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        //super.onBackPressedSupport();
        if (mResideLayout.isOpen()) {
            mResideLayout.closePane();
        } else {


            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                pop();
            } else {
                if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                    finish();
                } else {
                    TOUCH_TIME = System.currentTimeMillis();
                    Toast.makeText(this, "再按一次返回键退出喵咚家",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }

    @Override
    public void backToFirstFragment(){
        mBottomBar.setCurrentItem(0);
    }

    /**
     * 这里暂没实现,忽略
     */
//    @Subscribe
//    public void onHiddenBottombarEvent(boolean hidden) {
//        if (hidden) {
//            mBottomBar.hide();
//        } else {
//            mBottomBar.show();
//        }
//    }




}
