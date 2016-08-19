package cn.chenhai.miaodj_monitor.views.fragment.personal;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Timer;
import java.util.TimerTask;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.UserEntity;
import cn.chenhai.miaodj_monitor.model.entity.UserInfoEntity;
import cn.chenhai.miaodj_monitor.network_proxy.HttpMethods;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.view_custom.DragTipsView;
import cn.chenhai.miaodj_monitor.view_custom.DraggableFlagView;
import cn.chenhai.miaodj_monitor.views.activity.MainActivity;
import cn.chenhai.miaodj_monitor.views.activity.PersonalActivity;
import cn.chenhai.miaodj_monitor.views.base.BaseLazyMainFragment;
import cn.chenhai.miaodj_monitor.views.base.BaseMainFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ChenHai--霜华 on 2016/5/29. 15:28
 * 邮箱：248866527@qq.com
 */
public class PersonalFragment extends BaseMainFragment implements View.OnClickListener{
    private static final int REQ_START_PERSONAL_FOR_RESULT = 1099;
    private static final int REQ_START_PERSONAL_FOR_INFO_RESULT = 1100;

    private SubscriberOnSuccessListener mOnSuccessInit;

    private SimpleDraweeView mSimpleDraweeView;

    private DraggableFlagView draggableFlagView;

    private TextView mPersonalTvName;
    private TextView mPersonalTvRemind;
    private TextView mPersonalTvNewRemindNum;
    private TextView mPersonalTvMyPlace;
    private TextView mPersonalTvCargoScan;
    private TextView mPersonalTvWorkerRecommend;
    private TextView mPersonalTvInfo;
    private TextView mPersonalTvBankCard;
    private TextView mPersonalTvSystemSetting;
    private TextView mPersonalTvFeedback;
    private TextView mPersonalTvAboutUs;

    private TextView mTvNotifyText;

    private FrameLayout mPersonalLayoutNewRemind;
    private LinearLayout mPersonalLl1;
    private LinearLayout mPersonalLl2;
    private LinearLayout mPersonalLl3;
    private LinearLayout mPersonalLl4;
    private LinearLayout mPersonalLl5;
    private LinearLayout mPersonalLl6;
    private LinearLayout mPersonalLl7;
    private LinearLayout mPersonalLl8;
    private LinearLayout mPersonalLl9;


    public static PersonalFragment newInstance() {
        return new PersonalFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        initView(view);
        initFrescoView(view);

        refreshView();
        return view;
    }

    private void initView(View view){

        mPersonalTvName = (TextView) view.findViewById(R.id.personal_tv_name);
        mPersonalTvRemind = (TextView) view.findViewById(R.id.personal_tv_remind);
        mPersonalTvNewRemindNum = (TextView) view.findViewById(R.id.personal_tv_newRemind_num);
        mPersonalTvMyPlace = (TextView) view.findViewById(R.id.personal_tv_myPlace);
        mPersonalTvCargoScan = (TextView) view.findViewById(R.id.personal_tv_cargoScan);
        mPersonalTvWorkerRecommend = (TextView) view.findViewById(R.id.personal_tv_worker_recommend);
        mPersonalTvInfo = (TextView) view.findViewById(R.id.personal_tv_info);
        mPersonalTvBankCard = (TextView) view.findViewById(R.id.personal_tv_bankCard);
        mPersonalTvSystemSetting = (TextView) view.findViewById(R.id.personal_tv_system_setting);
        mPersonalTvFeedback = (TextView) view.findViewById(R.id.personal_tv_feedback);
        mPersonalTvAboutUs = (TextView) view.findViewById(R.id.personal_tv_aboutUs);
        mTvNotifyText = (TextView) view.findViewById(R.id.tv_notify_text);

        mPersonalLayoutNewRemind = (FrameLayout) view.findViewById(R.id.personal_layout_newRemind);

        mPersonalLl1 = (LinearLayout) view.findViewById(R.id.personal_ll_1);
        mPersonalLl2 = (LinearLayout) view.findViewById(R.id.personal_ll_2);
        mPersonalLl3 = (LinearLayout) view.findViewById(R.id.personal_ll_3);
        mPersonalLl4 = (LinearLayout) view.findViewById(R.id.personal_ll_4);
        mPersonalLl5 = (LinearLayout) view.findViewById(R.id.personal_ll_5);
        mPersonalLl6 = (LinearLayout) view.findViewById(R.id.personal_ll_6);
        mPersonalLl7 = (LinearLayout) view.findViewById(R.id.personal_ll_7);
        mPersonalLl8 = (LinearLayout) view.findViewById(R.id.personal_ll_8);
        mPersonalLl9 = (LinearLayout) view.findViewById(R.id.personal_ll_9);

        mPersonalLl1.setLayoutAnimation(getAnimationController());//这是第一种动画方式
        mPersonalLl2.setLayoutAnimation(getAnimationController());
        mPersonalLl3.setLayoutAnimation(getAnimationController());
        mPersonalLl4.setLayoutAnimation(getAnimationController());
        mPersonalLl5.setLayoutAnimation(getAnimationController());
        mPersonalLl6.setLayoutAnimation(getAnimationController());
        mPersonalLl7.setLayoutAnimation(getAnimationController());
        mPersonalLl8.setLayoutAnimation(getAnimationController());
        mPersonalLl9.setLayoutAnimation(getAnimationController());

        // layout.startAnimation(getAnimation());//这是第2种方式

        mPersonalLl1.setOnClickListener(this);
        mPersonalLl2.setOnClickListener(this);
        mPersonalLl3.setOnClickListener(this);
        mPersonalLl4.setOnClickListener(this);
        mPersonalLl5.setOnClickListener(this);
        mPersonalLl6.setOnClickListener(this);
        mPersonalLl7.setOnClickListener(this);
        mPersonalLl8.setOnClickListener(this);
        mPersonalLl9.setOnClickListener(this);


        DragTipsView.create(getActivity()).attach(mPersonalLayoutNewRemind //);
                , new DragTipsView.DragListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onCancel() {
                        mPersonalLayoutNewRemind.setVisibility(View.VISIBLE);
                    }
                });

        DragTipsView.create(getActivity()).attach(mTvNotifyText //);
                , new DragTipsView.DragListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onCancel() {
                        mTvNotifyText.setVisibility(View.VISIBLE);
                    }
                });
//        mPersonalLayoutNewRemind.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //draggableFlagView.setText("5");
//            }
//        });
//        draggableFlagView = (DraggableFlagView) view.findViewById(R.id.main_dfv);
//        draggableFlagView.setOnDraggableFlagViewListener(new DraggableFlagView.OnDraggableFlagViewListener() {
//            @Override
//            public void onFlagDismiss(DraggableFlagView view) {
//                Toast.makeText(_mActivity, "onFlagDismiss", Toast.LENGTH_SHORT).show();
//            }
//        });
//        draggableFlagView.setText("7");

        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<UserEntity>>() {
            @Override
            public void onSuccess(HttpResult<UserEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    UserEntity.PersonBean beanInfo = result.getInfo().getPerson();

                    String headPath = "";
                    if(beanInfo.getCrew_headimg() != null) {
                        headPath = HttpMethods.BASE_ROOT_URL + beanInfo.getCrew_headimg();
                    }
                    Uri imageUri = Uri.parse(headPath);
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

                    //开始下载
                    mSimpleDraweeView.setImageURI(imageUri);


                    mPersonalTvName.setText(beanInfo.getCrew_name());

                    mPersonalLayoutNewRemind.setVisibility(View.GONE);
                    if(beanInfo.getTodo()!=null && !beanInfo.getTodo().equals("") && !beanInfo.getTodo().equals("0"))
                    {
                        mPersonalLayoutNewRemind.setVisibility(View.VISIBLE);
                        mPersonalTvNewRemindNum.setText(beanInfo.getTodo());
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


        refreshData();
    }

    public void refreshData(){
        String user_code = PreferencesUtils.getString(_mActivity,"user_code");
        String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
        HttpMethods.getInstance().doGetPersonalInfos(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token);
    }

    protected LayoutAnimationController getAnimationController() {
        LayoutAnimationController controller;
// AnimationSet set = new AnimationSet(true);
        Animation anim = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f,
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF,
                1.0f);// 从0.5倍放大到1倍
        anim.setDuration(1200);
        controller = new LayoutAnimationController(anim, 0.1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        return controller;
    }

    protected Animation getAnimation() {
        Animation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);// 从0.5倍放大到1倍
        anim.setDuration(1500);
        return anim;
    }

    private void initFrescoView(View view){

        //创建SimpleDraweeView对象
        mSimpleDraweeView = (SimpleDraweeView) view.findViewById(R.id.personal_sdv);
        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");


//        //初始化圆角圆形参数对象
//        RoundingParams rp = new RoundingParams();
//        //设置图像是否为圆形
//        rp.setRoundAsCircle(true);
//        //设置圆角半径
//        //rp.setCornersRadius(20);
//        //分别设置左上角、右上角、左下角、右下角的圆角半径
//        //rp.setCornersRadii(20,25,30,35);
//        //分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径
//        //rp.setCornersRadii(new float[]{20,25,30,35,40,45,50,55});
//        //设置边框颜色及其宽度
//        rp.setBorder(Color.BLACK, 10);
//        //设置叠加颜色
//        rp.setOverlayColor(Color.GRAY);
//        //设置圆形圆角模式
//        //rp.setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
//        //设置圆形圆角模式
//        rp.setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR);
//
//        //获取GenericDraweeHierarchy对象
//        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
//                //设置圆形圆角参数
//                .setRoundingParams(rp)
//                //设置圆角半径
//                //.setRoundingParams(RoundingParams.fromCornersRadius(20))
//                //分别设置左上角、右上角、左下角、右下角的圆角半径
//                //.setRoundingParams(RoundingParams.fromCornersRadii(20,25,30,35))
//                //分别设置（前2个）左上角、(3、4)右上角、(5、6)左下角、(7、8)右下角的圆角半径
//                //.setRoundingParams(RoundingParams.fromCornersRadii(new float[]{20,25,30,35,40,45,50,55}))
//                //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
//                //.setRoundingParams(RoundingParams.asCircle())
//                //设置淡入淡出动画持续时间(单位：毫秒ms)
//                .setFadeDuration(5000)
//                //设置单张背景图
//                .setBackground(ContextCompat.getDrawable(this, R.mipmap.bg_zero))
//                //设置多张背景图
//                //.setBackgrounds(bgs)
//                //设置单张叠加图
//                //.setOverlay(ContextCompat.getDrawable(this,R.mipmap.overlay_one))
//                //设置多张叠加图
//                //.setOverlays(overlays)
//                //设置占位图及它的缩放类型
//                .setPlaceholderImage(ContextCompat.getDrawable(this, R.mipmap.icon_placeholder), ScalingUtils.ScaleType.FOCUS_CROP)
//                //设置正在加载图及其缩放类型
//                .setProgressBarImage(ContextCompat.getDrawable(this, R.mipmap.icon_progress_bar), ScalingUtils.ScaleType.FOCUS_CROP)
//                //设置失败图及其缩放类型
//                .setFailureImage(ContextCompat.getDrawable(this, R.mipmap.icon_failure), ScalingUtils.ScaleType.FOCUS_CROP)
//                //设置重试图及其缩放类型
//                .setRetryImage(ContextCompat.getDrawable(this, R.mipmap.icon_retry), ScalingUtils.ScaleType.FOCUS_CROP)
//                //构建
//                .build();
//
//        //设置Hierarchy
//        mSimpleDraweeView.setHierarchy(hierarchy);

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

        //开始下载
        mSimpleDraweeView.setImageURI(imageUri);

    }

    private void refreshView(){
//        String token = PreferencesUtils.getString(_mActivity,"user_token");
//        ProxyService.newInstance().GetUserInfo(_mActivity,token);
    }

    protected BackToFirstListener backToFirst;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BackToFirstListener) {
            backToFirst = (BackToFirstListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement BackToFirstListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        backToFirst = null;
    }
    public interface BackToFirstListener {
        void backToFirstFragment();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQ_START_PERSONAL_FOR_INFO_RESULT){
            refreshData();
        }
//        if (resultCode != RESULT_OK) {
//        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_ll_1:
                //待办事项
                //start(PersonalInfoEditFragment.newInstance());
                    //((HomeFragment) getParentFragment()).start(DetailAgreeFragment.newInstance("测试单号111"));
                Bundle bundle = new Bundle();
                bundle.putString("FragmentName", "PersonalBacklogFragment");
                bundle.putString("ProjectCode","测试单号！");
                Intent intent = new Intent(_mActivity, PersonalActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQ_START_PERSONAL_FOR_RESULT);

                break;
            case R.id.personal_ll_2:
                //我的工地
                //start(PersonalEditPassFragment.newInstance());
                TimerTask task = new TimerTask(){
                    public void run(){
                        //execute the task
                        //start(DetailAgreeFragment.newInstance("测试单号111"));
                        backToFirst.backToFirstFragment();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 300);

                break;
            case R.id.personal_ll_3:
                //收货扫描
                //start(WayBillFragment.newInstance());
                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("收货功能建设中，敬请期待!")
                        .show();
                break;
            case R.id.personal_ll_4:
                //工人推荐
                Bundle bundle1 = new Bundle();
                bundle1.putString("FragmentName", "PersonalRecommendFragment");
                bundle1.putString("ProjectCode","测试单号！");
                Intent intent1 = new Intent(_mActivity, PersonalActivity.class);
                intent1.putExtras(bundle1);
                startActivityForResult(intent1,REQ_START_PERSONAL_FOR_RESULT);
                //start(PersonalRecommendFragment.newInstance("测试。个人单号"));

                break;
            case R.id.personal_ll_5:
                //个人信息
                Bundle bundle4 = new Bundle();
                bundle4.putString("FragmentName", "PersonalInfoFragment");
                bundle4.putString("ProjectCode","测试单号！");
                Intent intent4 = new Intent(_mActivity, PersonalActivity.class);
                intent4.putExtras(bundle4);
                startActivityForResult(intent4,REQ_START_PERSONAL_FOR_INFO_RESULT);
                break;
            case R.id.personal_ll_6:
                //我的银行卡
                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("银行卡功能建设中，敬请期待!")
                        .show();
                break;
            case R.id.personal_ll_7:
                //系统设置
                Bundle bundle2 = new Bundle();
                bundle2.putString("FragmentName", "PersonalSettingFragment");
                bundle2.putString("ProjectCode","测试单号！");
                Intent intent2 = new Intent(_mActivity, PersonalActivity.class);
                intent2.putExtras(bundle2);
                startActivityForResult(intent2,REQ_START_PERSONAL_FOR_RESULT);

                break;
            case R.id.personal_ll_8:
                //意见反馈
                Bundle bundle5 = new Bundle();
                bundle5.putString("FragmentName", "PersonalFeedbackFragment");
                bundle5.putString("ProjectCode","测试单号！");
                Intent intent5 = new Intent(_mActivity, PersonalActivity.class);
                intent5.putExtras(bundle5);
                startActivityForResult(intent5,REQ_START_PERSONAL_FOR_RESULT);
                break;


            case R.id.personal_ll_9:
                //关于我们
                Bundle bundle3 = new Bundle();
                bundle3.putString("FragmentName", "PersonalAboutUSFragment");
                bundle3.putString("ProjectCode","测试单号！");
                Intent intent3 = new Intent(_mActivity, PersonalActivity.class);
                intent3.putExtras(bundle3);
                startActivityForResult(intent3,REQ_START_PERSONAL_FOR_RESULT);

                break;
        }
    }
}
