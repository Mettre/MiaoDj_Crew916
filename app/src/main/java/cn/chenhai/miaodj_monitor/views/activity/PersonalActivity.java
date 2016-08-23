package cn.chenhai.miaodj_monitor.views.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.theme.Theme;
import cn.chenhai.miaodj_monitor.utils.PreUtils;
import cn.chenhai.miaodj_monitor.views.fragment.personal.PersonalAboutUSFragment;
import cn.chenhai.miaodj_monitor.views.fragment.personal.PersonalBacklogFragment;
import cn.chenhai.miaodj_monitor.views.fragment.personal.PersonalFeedbackFragment;
import cn.chenhai.miaodj_monitor.views.fragment.personal.PersonalInfoFragment;
import cn.chenhai.miaodj_monitor.views.fragment.personal.PersonalRecommendFragment;
import cn.chenhai.miaodj_monitor.views.fragment.personal.PersonalSettingFragment;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * 个人中心模块Fragment载体
 * <p>
 * Created by ChenHai--霜华 on 2016/6/23. 00:08
 * 邮箱：248866527@qq.com
 */
public class PersonalActivity extends SwipeBackActivity {

    public static final String TAG = DetailActivity.class.getSimpleName();

    private String mProjectCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onPreCreate();
        setStatusBar();

        setContentView(R.layout.activity_personal);

//        if (savedInstanceState == null) {
//            start(HomeFragment.newInstance());
//        }

        String fragmentName = "";

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mProjectCode = bundle.getString("ProjectCode");
            fragmentName = bundle.getString("FragmentName");
        }

        initView();

        if (savedInstanceState == null) {
            //start(HomeFragment.newInstance());
//            switch (fragmentName){
//                case "PersonalBacklogFragment":
//                    loadRootFragment(R.id.personal_fl_container, PersonalBacklogFragment.newInstance(mProjectCode));
//                    break;
//                case "PersonalRecommendFragment":
//                    loadRootFragment(R.id.personal_fl_container, PersonalRecommendFragment.newInstance(mProjectCode));
//                    break;
//                default:
//                    onBackPressed();
//                    break;
//            }
            if (TextUtils.equals(fragmentName, "PersonalBacklogFragment")) {
                loadRootFragment(R.id.personal_fl_container, PersonalBacklogFragment.newInstance(mProjectCode));
            } else if (TextUtils.equals(fragmentName, "PersonalRecommendFragment")) {
                loadRootFragment(R.id.personal_fl_container, PersonalRecommendFragment.newInstance(mProjectCode));
            } else if (TextUtils.equals(fragmentName, "PersonalSettingFragment")) {
                loadRootFragment(R.id.personal_fl_container, PersonalSettingFragment.newInstance(mProjectCode));
            } else if (TextUtils.equals(fragmentName, "PersonalAboutUSFragment")) {
                loadRootFragment(R.id.personal_fl_container, PersonalAboutUSFragment.newInstance(mProjectCode));
            } else if (TextUtils.equals(fragmentName, "PersonalInfoFragment")) {
                loadRootFragment(R.id.personal_fl_container, PersonalInfoFragment.newInstance(mProjectCode));
            } else if (TextUtils.equals(fragmentName, "PersonalFeedbackFragment")) {
                loadRootFragment(R.id.personal_fl_container, PersonalFeedbackFragment.newInstance(mProjectCode));
            } else {
                onBackPressed();
            }
        }


        //getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_ALL);
        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_LEFT);
    }

    private void setStatusBar() {
        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            //tintManager.setStatusBarTintResource(R.color.status_bar_bg);//通知栏所需颜色
            //tintManager.setStatusBarTintResource(color);//通知栏所需颜色
            tintManager.setStatusBarTintColor(0x11ff0000);//通知栏所需颜色
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

    private void initView() {

    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity可以滑动退出, 并且总是优先;  false: Activity不允许滑动退出
     */
    @Override
    public boolean swipeBackPriority() {
        return super.swipeBackPriority();
    }


    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
        //return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
        // 设置自定义动画
//        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消请求
        //RequestManager.cancelRequest(getName());

    }
}
