package cn.chenhai.miaodj_monitor.ui.fragment.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.adapter.PersonalRecommendAdapter;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.StringUtils;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.RecommendWorkerListEntity;
import cn.chenhai.miaodj_monitor.model.info.RecommendWorkerInfo;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ChenHai--霜华 on 2016/6/27. 17:33
 * 邮箱：248866527@qq.com
 */
public class PersonalRecommendFragment extends BaseBackFragment_Swip {
    private static final String ARG_ITEM = "arg_item";
    private static final String TAG = "FragmentLib";

    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessInit;
    private SubscriberOnSuccessListener mOnSuccessWorkerRecommend;

    private LinearLayoutManager mLLmanager;
    private PersonalRecommendAdapter mAdapter;

    private FrameLayout mRecommendBack;
    private TextView mTvRecommendTitle;
    private TextView mTvRecommendWorkerNum;
    private FrameLayout mTvRecommendToRecommend;
    private TextView mTvRecommendMoneyNum;
    private FrameLayout mFlRecommendTakeMoney;
    private TextView mTvRecommendMoney;
    private RecyclerView mRecommendRecycler;

    private LinearLayout mEmptyViewLayout;

    public static PersonalRecommendFragment newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        PersonalRecommendFragment fragment = new PersonalRecommendFragment();
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
        View view = inflater.inflate(R.layout.fragment_recommend, container, false);
        initView(view);
        initData();
        //initDataTemp();
        //return view;
        return attachToSwipeBack(view);
    }

    private void initView(View view) {

        mRecommendBack = (FrameLayout) view.findViewById(R.id.recommend_back);
        mTvRecommendTitle = (TextView) view.findViewById(R.id.tv_recommend_title);
        mTvRecommendWorkerNum = (TextView) view.findViewById(R.id.tv_recommend_workerNum);
        mTvRecommendToRecommend = (FrameLayout) view.findViewById(R.id.tv_recommend_toRecommend);
        mTvRecommendMoneyNum = (TextView) view.findViewById(R.id.tv_recommend_moneyNum);
        mFlRecommendTakeMoney = (FrameLayout) view.findViewById(R.id.fl_recommend_takeMoney);
        mTvRecommendMoney = (TextView) view.findViewById(R.id.tv_recommend_money);
        mRecommendRecycler = (RecyclerView) view.findViewById(R.id.recommend_recycler);

        mEmptyViewLayout = (LinearLayout) view.findViewById(R.id.empty_view_layout);

        mAdapter = new PersonalRecommendAdapter(_mActivity);
        mLLmanager = new LinearLayoutManager(_mActivity);
        mRecommendRecycler.setLayoutManager(mLLmanager);
        mRecommendRecycler.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //((MainActivity)getActivity()).getResideLayout().setIfSlide(true);

                //execute the task
                start(PersonalRecommendDetailFragment.newInstance(mAdapter.getItem(position).getWorkerID()));
            }
        });

        mRecommendBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });

        mTvRecommendToRecommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });

        mFlRecommendTakeMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("此功能建设中，敬请期待!")
                        .show();
            }
        });

        mOnSuccessWorkerRecommend = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("推荐")
                                .setContentText("推荐成功!")
                                .show();
                        if(mPopupWindow.isShowing()){
                            mPopupWindow.dismiss();
                        }
                        refreshData();
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

        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<RecommendWorkerListEntity>>() {
            @Override
            public void onSuccess(HttpResult<RecommendWorkerListEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    float money = result.getInfo().getSum_commission();
                    mTvRecommendMoney.setText(String.valueOf(money));
                    mTvRecommendWorkerNum.setText(String.valueOf(result.getInfo().getSum()));

                    List<RecommendWorkerListEntity.RecommendBean> projects = result.getInfo().getRecommend();
                    List<RecommendWorkerInfo> list = new ArrayList<>();

                    if(projects.size()==0){
                        mEmptyViewLayout.setVisibility(View.VISIBLE);
                        mRecommendRecycler.setVisibility(View.GONE);
                    }else {
                        mEmptyViewLayout.setVisibility(View.GONE);
                        mRecommendRecycler.setVisibility(View.VISIBLE);
                    }

                    for (int i=0 ;i<projects.size() ;i++){
                        RecommendWorkerInfo pageInfo = new RecommendWorkerInfo();
                        RecommendWorkerListEntity.RecommendBean nodeInfo = projects.get(i);

                        if(nodeInfo.getUsername() != null && !nodeInfo.getUsername().equals("")) {
                            pageInfo.setIfRegister(true);
                            pageInfo.setWorkerName(nodeInfo.getReal_name());
                            pageInfo.setWorkerPhone(nodeInfo.getTelephone());

                        }else {
                            pageInfo.setIfRegister(false);
                            pageInfo.setWorkerName(nodeInfo.getRecommended_name());
                            pageInfo.setWorkerPhone(nodeInfo.getRecommended_telephone());
                        }

                        String headPath = "";
                        if(nodeInfo.getHeadimg() != null) {
                            if(!nodeInfo.getHeadimg().equals("")) {
                                headPath = HttpMethods.BASE_ROOT_URL + nodeInfo.getHeadimg();
                            }
                        }
                        pageInfo.setImg_portraitPath(headPath);

                        pageInfo.setMoney(nodeInfo.getCommission());
                        pageInfo.setWorkerID(nodeInfo.getCode());


                        list.add(pageInfo);
                    }

                    mAdapter.refreshDatas(list);

                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){
                mEmptyViewLayout.setVisibility(View.VISIBLE);
                mRecommendRecycler.setVisibility(View.GONE);
            }
        };

        refreshData();
    }

    private void refreshData(){
        String user_code = PreferencesUtils.getString(_mActivity,"user_code");
        String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
        HttpMethods.getInstance().getRecommendWorkersList(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token);
    }

    private void initData() {

    }

    private void initDataTemp() {
        List<RecommendWorkerInfo> list = new ArrayList<>();


        for (int i = 5; i < 30; i++) {
            RecommendWorkerInfo workerInfo = new RecommendWorkerInfo();

            if (i % 4 == 0) {
                workerInfo.setImg_portraitPath("");
                workerInfo.setWorkerName("李丽丽");
                workerInfo.setWorkerPhone("13562525215");
                workerInfo.setMoney("10.00");

            } else if (i % 4 == 1) {
                workerInfo.setImg_portraitPath("http://h.hiphotos.baidu.com/zhidao/pic/item/7c1ed21b0ef41bd5da8c805250da81cb38db3dbc.jpg");
                workerInfo.setWorkerName("李小冉");
                workerInfo.setWorkerPhone("13562525215");
                workerInfo.setMoney("10.00");

            } else if (i % 4 == 2) {
                workerInfo.setImg_portraitPath("http://img3.duitang.com/uploads/item/201501/28/20150128194217_mYSVJ.jpeg");
                workerInfo.setWorkerName("钱莉");
                workerInfo.setWorkerPhone("13562525215");
                workerInfo.setMoney("12.00");

            } else if (i % 4 == 3) {
                workerInfo.setImg_portraitPath("http://img2.imgtn.bdimg.com/it/u=375192498,2173854692&fm=21&gp=0.jpg");
                workerInfo.setWorkerName("崇丽丽");
                workerInfo.setWorkerPhone("13562525215");
                workerInfo.setMoney("30.00");

            }
            list.add(workerInfo);
        }
        mAdapter.refreshDatas(list);
    }





    /**---------------------------PoputWindow--------------------------------*/
    private PopupWindow mPopupWindow;

    private LinearLayout mPopupRecommend;
    private LinearLayout mLlPopupClose;
    private EditText mEtPopupName;
    private EditText mEtPopupPhone;
    private Button mPopupBtn;

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


        mPopupRecommend = (LinearLayout) popView.findViewById(R.id.popup_recommend);
        mLlPopupClose = (LinearLayout) popView.findViewById(R.id.ll_popup_close);
        mEtPopupName = (EditText) popView.findViewById(R.id.et_popup_name);
        mEtPopupPhone = (EditText) popView.findViewById(R.id.et_popup_phone);
        mPopupBtn = (Button) popView.findViewById(R.id.popup_btn);

    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha ,float bgDim)
    {
        WindowManager.LayoutParams lp = _mActivity.getWindow().getAttributes();
        lp.dimAmount = bgDim;
        lp.alpha = bgAlpha; //0.0-1.0
        _mActivity.getWindow().setAttributes(lp);

        _mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
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
            backgroundAlpha(1f, 0.1f);
        }
    }


    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View popView = LayoutInflater.from(_mActivity).inflate(R.layout.popup_recommend_worker, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //初始化
        initPopWindow(popView,mPopupWindow);

        // 设置按钮的点击事件
        mLlPopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                //测试
            }
        });

        mPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strName = mEtPopupName.getText().toString();
                String strPhone = mEtPopupPhone.getText().toString();
                if (TextUtils.isEmpty(strName.trim())) {
                    Toast.makeText(_mActivity, "推荐工人姓名不能为空！!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(strPhone.trim())) {
                    Toast.makeText(_mActivity, "推荐工人手机号不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!StringUtils.isPhoneNumberValid(strPhone.trim())) {
                    Toast.makeText(_mActivity, "无效格式的手机号!", Toast.LENGTH_SHORT).show();
                    return;
                }

                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("")
                        .setContentText("确认推荐此工人吗？")
                        .setCancelText("关闭")
                        .setConfirmText("确定")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                String user_code = PreferencesUtils.getString(_mActivity,"user_code");
                                String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
                                HttpMethods.getInstance().doRecommendWorker(new ProgressSubscriber(mOnSuccessWorkerRecommend, _mActivity), user_code, access_token,
                                        mEtPopupName.getText().toString(),mEtPopupPhone.getText().toString());

                                sDialog.dismiss();
                            }
                        })
                        .show();


            }
        });


        /** 禁止点击外部区域取消popup windows*/
        LinearLayout layouttemp = (LinearLayout) popView
                .findViewById(R.id.popup_recommend);
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

        backgroundAlpha(0.3f ,1f);//透明度
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
