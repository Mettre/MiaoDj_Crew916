package cn.chenhai.miaodj_monitor.views.fragment.worker;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.chenhai.miaodj_monitor.FunRefresh.FunGameRefreshRecyclerView;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.adapter.GridWorkerAdapter;
import cn.chenhai.miaodj_monitor.adapter.WorkerCheckAdapter;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.CheckWorkersEntity;
import cn.chenhai.miaodj_monitor.model.entity.ProjectWorkersEntity;
import cn.chenhai.miaodj_monitor.model.entity.ProjectWorkersInfoEntity;
import cn.chenhai.miaodj_monitor.model.info.WorkerCheckInfo;
import cn.chenhai.miaodj_monitor.network_proxy.HttpMethods;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.view_custom.RatingBar;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment_Swip;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ChenHai--霜华 on 2016/6/20. 09:46
 * 邮箱：248866527@qq.com
 */
public class WorkerCheckFragment extends BaseBackFragment_Swip{
    private static final String ARG_ITEM = "arg_item";
    private static final int REQ_START_FOR_RESULT = 1100;

    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessInit;
    private SubscriberOnSuccessListener mOnSuccessPopup;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private FunGameRefreshRecyclerView mRefreshFunGame;
    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLLmanager;
    private WorkerCheckAdapter mAdapter;


    public static WorkerCheckFragment newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        WorkerCheckFragment fragment = new WorkerCheckFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectCode = getArguments().getString(ARG_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worker_check, container, false);
        initView(view);
        initData();
        initPullRefresh(view);
        //initDataTemp();

        //return view;
        return attachToSwipeBack(view);
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        mAdapter = new WorkerCheckAdapter(_mActivity);
        mLLmanager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLLmanager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //((MainActivity)getActivity()).getResideLayout().setIfSlide(true);
                TimerTask task = new TimerTask(){
                    public void run(){
                        //execute the task
                        startForResult(WorkerChooseFragment.newInstance(mProjectCode,mAdapter.getItem(position).getWorkerNowTypes()),REQ_START_FOR_RESULT);
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 260);
            }
        });
        mAdapter.setOnItemBtnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                String user_code = PreferencesUtils.getString(_mActivity,"user_code");
                String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
                HttpMethods.getInstance().getShowWorkerCard(new ProgressSubscriber(mOnSuccessPopup, _mActivity), user_code, access_token,mAdapter.getItem(position).getWorkerCode());
            }
        });

        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<CheckWorkersEntity>>() {
            @Override
            public void onSuccess(HttpResult<CheckWorkersEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    List<CheckWorkersEntity.WorkerTypesBean> projects = result.getInfo().getWorker_types();
                    List<WorkerCheckInfo> list = new ArrayList<>();
                    for (int i=0 ;i<projects.size() ;i++){
                        WorkerCheckInfo pageInfo = new WorkerCheckInfo();
                        CheckWorkersEntity.WorkerTypesBean nodeInfo = projects.get(i);

                        pageInfo.setWorkerNowTypes(nodeInfo.getWorker_type_name());
                        String headPath = "";
                        if(nodeInfo.getWorker_headimg() != null) {
                            headPath = HttpMethods.BASE_ROOT_URL + nodeInfo.getWorker_headimg();
                        }
                        pageInfo.setImg_portraitPath(headPath);
                        pageInfo.setWorkerName(nodeInfo.getWorker_name());
                        pageInfo.setWorkerCode(nodeInfo.getWorker_code());

                        if(nodeInfo.getWorker_code().equals("") && nodeInfo.getWorker_name().equals(""))
                        {
                            pageInfo.setIfAddedWorker("false");

                        } else {
                            pageInfo.setIfAddedWorker("true");

                            if(nodeInfo.getCheck_status().equals("2")){
                                pageInfo.setIfAgree("true");
                            }else if(nodeInfo.getCheck_status().equals("3")){
                                pageInfo.setIfAgree("false");
                            }

                        }

                        list.add(pageInfo);
                    }

                    mAdapter.refreshDatas(list);

                    mAdapter.notifyDataSetChanged();
                    mRefreshFunGame.finishRefreshing();
                }
            }
            @Override
            public void onCompleted(){
                mRefreshFunGame.finishRefreshing();
            }
            @Override
            public void onError(){
                mRefreshFunGame.finishRefreshing();
            }
        };

        mOnSuccessPopup = new SubscriberOnSuccessListener<HttpResult<ProjectWorkersInfoEntity>>() {
            @Override
            public void onSuccess(HttpResult<ProjectWorkersInfoEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    ProjectWorkersInfoEntity.WorkerBean beanInfo = result.getInfo().getWorker();


                    showPopupWindow(view , beanInfo);
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {
                new SweetAlertDialog(_mActivity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("提示")
                        .setContentText("工人名片获取错误!")
                        .show();
            }
        };

        updateData();

    }

    private void initData() {
        mToolbarTitle.setText("施工工人");
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data){
        super.onFragmentResult(requestCode, resultCode, data);
        if(requestCode == REQ_START_FOR_RESULT && resultCode == RESULT_OK && data != null){

            String result = data.getString("result");
            if(result!=null && result.equals("已选择")){
                //重新加载
                updateData();
            }

        }else if (requestCode == 0) {}
    }

    private void initDataTemp() {
        List<WorkerCheckInfo> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            WorkerCheckInfo workerInfo = new WorkerCheckInfo();

            if (i % 6 == 0) {
                workerInfo.setIfAddedWorker("true");
                workerInfo.setIfAgree("true");
                workerInfo.setImg_portraitPath("");
                workerInfo.setWorkerName("张丽丽");
                workerInfo.setWorkerNowTypes("放线员");

            } else if (i % 6 == 1) {
                workerInfo.setIfAddedWorker("true");
                workerInfo.setIfAgree("true");
                workerInfo.setImg_portraitPath("http://h.hiphotos.baidu.com/zhidao/pic/item/7c1ed21b0ef41bd5da8c805250da81cb38db3dbc.jpg");
                workerInfo.setWorkerName("李丽丽");
                workerInfo.setWorkerNowTypes("拆墙工");

            } else if (i % 6 == 2) {
                workerInfo.setIfAddedWorker("true");
                workerInfo.setIfAgree("true");
                workerInfo.setImg_portraitPath("http://img3.duitang.com/uploads/item/201501/28/20150128194217_mYSVJ.jpeg");
                workerInfo.setWorkerName("王丽丽");
                workerInfo.setWorkerNowTypes("水电工");

            } else if (i % 6 == 3) {
                workerInfo.setIfAddedWorker("true");
                workerInfo.setIfAgree("false");
                workerInfo.setImg_portraitPath("http://img2.imgtn.bdimg.com/it/u=375192498,2173854692&fm=21&gp=0.jpg");
                workerInfo.setWorkerName("崇丽丽");
                workerInfo.setWorkerNowTypes("木工");

            } else if (i % 6 == 4) {
                workerInfo.setIfAddedWorker("false");
                workerInfo.setIfAgree("true");
                workerInfo.setImg_portraitPath("http://img2.imgtn.bdimg.com/it/u=375192498,2173854692&fm=21&gp=0.jpg");
                workerInfo.setWorkerName("崇丽丽");
                workerInfo.setWorkerNowTypes("油漆工");

            } else if (i % 6 == 5) {
                workerInfo.setIfAddedWorker("false");
                workerInfo.setIfAgree("true");
                workerInfo.setImg_portraitPath("http://img2.imgtn.bdimg.com/it/u=375192498,2173854692&fm=21&gp=0.jpg");
                workerInfo.setWorkerName("崇丽丽");
                workerInfo.setWorkerNowTypes("专业防水");

            }
            list.add(workerInfo);
        }
        mAdapter.addDatas(list);
    }

    private void initPullRefresh(View view){
        mRefreshFunGame = (FunGameRefreshRecyclerView) view.findViewById(R.id.refresh_fun_game);

        mRefreshFunGame.setOnRefreshListener(new FunGameRefreshRecyclerView.FunGameRefreshListener() {
            @Override
            public void onRefreshing() {
                try {
                    // 模拟网络请求耗时动作
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);
            }
        });
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //dataList.add("X");
            updateData();
//            mAdapter.notifyDataSetChanged();
//            mRefreshFunGame.finishRefreshing();
        }
    };


    protected void updateData() {
        //mRefreshFunGame.finishRefreshing();
        String user_code = PreferencesUtils.getString(_mActivity,"user_code");
        String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
        HttpMethods.getInstance().getProjectWorkerTypeList(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token,mProjectCode);
    }

    private void scrollToTop() {
        mRecyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onStop() {
        super.onStop();
    }






    /**---------------------------PoputWindow--------------------------------*/
    private PopupWindow mPopupWindow;


    private LinearLayout mPopupWorkerInfo;
    private LinearLayout mLlPopupClose;
    private LinearLayout mLlPopupPhoneIcon;

    private GridView mGridPopupWorkerType;
    private RatingBar mRbWorkerRatingBar;
    private SimpleDraweeView mSdvPopupWorkerPortrait;

    private TextView mTvPopupWorkerName;
    private TextView mTvPopupOwnerPhone;
    private TextView mTvPopupAge;
    private TextView mTvPopupWorkYears;
    private TextView mTvPopupOrdersCount;
    private TextView mTvPopupRegisterTime;

    /**
     * 初始化popWindow
     * */
    private void initPopWindow(View popView,PopupWindow popupWindow) {

        //popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        //popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);


        mPopupWorkerInfo = (LinearLayout) popView.findViewById(R.id.popup_workerInfo);
        mLlPopupClose = (LinearLayout) popView.findViewById(R.id.ll_popup_close);
        mLlPopupPhoneIcon = (LinearLayout) popView.findViewById(R.id.ll_popup_phoneIcon);
        mGridPopupWorkerType = (GridView) popView.findViewById(R.id.grid_popup_workerType);
        mRbWorkerRatingBar = (RatingBar) popView.findViewById(R.id.rb_worker_ratingBar);
        mSdvPopupWorkerPortrait = (SimpleDraweeView) popView.findViewById(R.id.sdv_popup_worker_portrait);
        mTvPopupWorkerName = (TextView) popView.findViewById(R.id.tv_popup_worker_name);
        mTvPopupOwnerPhone = (TextView) popView.findViewById(R.id.tv_popup_ownerPhone);
        mTvPopupAge = (TextView) popView.findViewById(R.id.tv_popup_age);
        mTvPopupWorkYears = (TextView) popView.findViewById(R.id.tv_popup_workYears);
        mTvPopupOrdersCount = (TextView) popView.findViewById(R.id.tv_popup_ordersCount);
        mTvPopupRegisterTime = (TextView) popView.findViewById(R.id.tv_popup_registerTime);

    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha,float bgDim)
    {
        WindowManager.LayoutParams lp = _mActivity.getWindow().getAttributes();
        lp.dimAmount = bgDim;
        lp.alpha = bgAlpha; //0.0-1.0
        _mActivity.getWindow().setAttributes(lp);

        _mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //_mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

    }
    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     *
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f,0.1f);
        }
    }

//    private String popStars = "0";
//    //private String workerTypes[] = {"水电工","木工","瓦工","油漆工"};
//    private List<String> popWorkerTypesList = new ArrayList<>();
//    private String popPhone = "";

    private void showPopupWindow(View view , ProjectWorkersInfoEntity.WorkerBean dataInfo) {

        // 一个自定义的布局，作为显示的内容
        View popView = LayoutInflater.from(_mActivity).inflate(R.layout.popup_worker_info, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //初始化
        initPopWindow(popView,mPopupWindow);

        /***********************初始化数据**************************/
        mRbWorkerRatingBar.setStar(Integer.valueOf(dataInfo.getScore()));
        List<String> list = new ArrayList<>();
        for (int i=0 ;i<dataInfo.getWorker_types().size() ;i++){
            ProjectWorkersInfoEntity.WorkerBean.WorkerTypesBean typesBean = dataInfo.getWorker_types().get(i);
            String strType = typesBean.getWorker_type_name();
            list.add(strType);
        }
        GridWorkerAdapter gridAdapter = new GridWorkerAdapter(_mActivity, list);
        mGridPopupWorkerType.setAdapter(gridAdapter);

        mTvPopupOwnerPhone.setText(dataInfo.getTelephone());


        String headPath = "";
        if(dataInfo.getHeadimg() != null) {
            headPath = HttpMethods.BASE_ROOT_URL + dataInfo.getHeadimg();
        }
        Uri imageUri = Uri.parse(headPath);
        //开始下载
        mSdvPopupWorkerPortrait.setImageURI(imageUri);

        mTvPopupWorkerName.setText(dataInfo.getReal_name());
        mTvPopupOwnerPhone.setText(dataInfo.getTelephone());
        mTvPopupAge.setText(String.valueOf(dataInfo.getAge()));
        mTvPopupWorkYears.setText(String.valueOf(dataInfo.getWork_age()));
        mTvPopupOrdersCount.setText(dataInfo.getOrder());
        mTvPopupRegisterTime.setText(dataInfo.getCreatetime());

        /**********************************************************/

        // 设置按钮的点击事件
        mLlPopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                //测试
            }
        });

        mLlPopupPhoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(_mActivity,SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("拨打电话")
                        .setContentText(mTvPopupOwnerPhone.getText().toString())
                        .setCancelText("取消")
                        .setConfirmText("确定！")
                        .showCancelButton(true)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                String phone = mTvPopupOwnerPhone.getText().toString();
                                //用intent启动拨打电话
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
                                startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });



        /** 禁止点击外部区域取消popup windows*/
        LinearLayout layouttemp = (LinearLayout) popView
                .findViewById(R.id.popup_workerInfo);
        layouttemp.setFocusable(true);
        layouttemp.setFocusableInTouchMode(true);
        layouttemp.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 手机键盘上的返回键
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        mPopupWindow.dismiss();
                        break;
                }
                return false;
            }
        });
        /** ----------------------------------------------*/

        mPopupWindow.setTouchable(true);

        //获取焦点
        mPopupWindow.setFocusable(true);

        backgroundAlpha(0.3f , 1f);//透明度
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());

        mPopupWindow.update();
        if (!mPopupWindow.isShowing()) {
            //设置显示位置
            mPopupWindow.showAtLocation(view, Gravity.CENTER ,0,0);
        }

    }
    /** -------------------------------------------------------------------------------------*/
}
