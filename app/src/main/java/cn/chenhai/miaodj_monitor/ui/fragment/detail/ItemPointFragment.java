package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.labo.kaji.fragmentanimations.CubeAnimation;
import com.labo.kaji.fragmentanimations.FlipAnimation;
import com.labo.kaji.fragmentanimations.MoveAnimation;
import com.labo.kaji.fragmentanimations.PushPullAnimation;
import com.labo.kaji.fragmentanimations.SidesAnimation;
import com.zhy.autolayout.AutoFrameLayout;

import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.entity.PointProgressDetailEntity;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.ui.adapter.ItemPointAdapter;
import cn.chenhai.miaodj_monitor.ui.view_custom.AutoCardView;
import cn.chenhai.miaodj_monitor.ui.view_custom.RatingBar;
import cn.chenhai.miaodj_monitor.utils.TimeUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by ChenHai--霜华 on 2016/6/14. 22:46
 * 邮箱：248866527@qq.com
 */
public class ItemPointFragment extends SupportFragment {
    private static final String ARG_ITEM = "item_content";

    @IntDef({NONE, CUBE, FLIP, PUSHPULL, SIDES, MOVEPULL})
    public @interface AnimationStyle {
    }

    public static final int NONE = 0;
    public static final int CUBE = 1;
    public static final int FLIP = 2;
    public static final int PUSHPULL = 3;
    public static final int SIDES = 4;
    public static final int MOVEPULL = 5;

    @IntDef({NODIR, LEFT, RIGHT})
    public @interface AnimationDirection {
    }

    public static final int NODIR = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    private static final long DURATION = 500;

    @AnimationStyle
    private static int sAnimationStyle = MOVEPULL;


    private TextView mItemTvWorkerType;
    private TextView mItemTvWorkerType2;
    private TextView mItemTvStartDatePredict;
    private TextView mItemTvStartDateReality;
    private TextView mItemTvEndDatePredict;
    private TextView mItemTvEndDateReality;
    private TextView mItemTvNowStatus;
    private LinearLayout mItemLlStatusHide;
    private RecyclerView mItemRecycler;
    private AutoFrameLayout mWorkerTypeLayout;

    private String mTemCode;

    //支付工人款项
    private TextView mIdppPrive;
    private TextView mIdppMemoTv;
    private AutoCardView mIdppPayCardview;

    //业主也评价
    private TextView mIdppEvaluateDataTv;
    private RatingBar mIdppRatingBar;
    private TextView mIdppCustomerContextTv;
    private AutoCardView mIdppAssessCardview;

    private LinearLayoutManager mLLmanager;
    private ItemPointAdapter mAdapter;

    private OnItemRefreshListener itemRefreshListener;

    private int item_Index_Num = 1;

    public static ItemPointFragment newInstance(String temCode, @AnimationDirection int direction, int indexNum) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, temCode);
        args.putInt("direction", direction);
        args.putInt("indexNum", indexNum);
        ItemPointFragment fragment = new ItemPointFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTemCode = getArguments().getString(ARG_ITEM);
        item_Index_Num = getArguments().getInt("indexNum");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_detail_point_progress, container, false);
        initView(view);
        initData();
        initDataTemp();
        return view;
    }

    private void initView(View view) {

        mItemTvWorkerType = (TextView) view.findViewById(R.id.item_tv_workerType);
        mItemTvWorkerType2 = (TextView) view.findViewById(R.id.item_tv_workerType2);
        mItemTvStartDatePredict = (TextView) view.findViewById(R.id.item_tv_startDate_predict);
        mItemTvStartDateReality = (TextView) view.findViewById(R.id.item_tv_startDate_reality);
        mItemTvEndDatePredict = (TextView) view.findViewById(R.id.item_tv_endDate_predict);
        mItemTvEndDateReality = (TextView) view.findViewById(R.id.item_tv_endDate_reality);
        mItemTvNowStatus = (TextView) view.findViewById(R.id.item_tv_now_status);
        mItemLlStatusHide = (LinearLayout) view.findViewById(R.id.item_ll_status_hide);
        mWorkerTypeLayout = (AutoFrameLayout) view.findViewById(R.id.item_tv_workertypeLayout);
        mItemLlStatusHide.setVisibility(View.VISIBLE);

        mItemRecycler = (RecyclerView) view.findViewById(R.id.item_recycler);

        mAdapter = new ItemPointAdapter(_mActivity);
        mLLmanager = new LinearLayoutManager(_mActivity);
        mItemRecycler.setLayoutManager(mLLmanager);
        mItemRecycler.setAdapter(mAdapter);
        mIdppPrive = (TextView) view.findViewById(R.id.idpp_prive);
        mIdppMemoTv = (TextView) view.findViewById(R.id.idpp_memoTv);
        mIdppPayCardview = (AutoCardView) view.findViewById(R.id.idpp_payCardview);
        mIdppEvaluateDataTv = (TextView) view.findViewById(R.id.idpp_evaluateDataTv);
        mIdppRatingBar = (RatingBar) view.findViewById(R.id.idpp_ratingBar);
        mIdppCustomerContextTv = (TextView) view.findViewById(R.id.idpp_customerContextTv);
        mIdppAssessCardview = (AutoCardView) view.findViewById(R.id.idpp_assessCardview);
    }

    public void setItemData(PointProgressDetailEntity.NodeBean dataInfo) {
        mItemTvWorkerType.setText(dataInfo.getWorker_name());
        mItemTvWorkerType2.setText(dataInfo.getWorker_type());
        mItemTvStartDatePredict.setText(dataInfo.getExpect_start_date());
        mItemTvStartDateReality.setText(dataInfo.getActual_start_date());
        mItemTvEndDatePredict.setText(dataInfo.getExpect_end_date());
        mItemTvEndDateReality.setText(dataInfo.getActual_end_date());

        mWorkerTypeLayout.setVisibility(TextUtils.isEmpty(dataInfo.getWorker_type()) ? View.GONE : View.VISIBLE);

        String status = "";
        switch (dataInfo.getStatus()) {
            case "1":
                status = "未开始";
                break;
            case "10":
                status = "待进场";
                break;
            case "20":
                status = "待施工";
                break;
            case "30":
                status = "施工中";
                break;
            case "40":
                status = "待施工员验收";
                break;
            case "42":
                status = "施工员验收不通过";
                break;
            case "50":
                status = "待业主验收";
                break;
            case "52":
                status = "业主验收不通过";
                break;
            case "90":
                status = "停工";
                break;
            case "100":
                status = "已完成";
                break;
            case "110":
                status = "已评价";
                break;
        }
        mItemTvNowStatus.setText(status);

        if (dataInfo.getLogs().size() > 0) {
            mItemLlStatusHide.setVisibility(View.VISIBLE);
            mAdapter.refreshDatas(dataInfo.getLogs());
            mAdapter.notifyDataSetChanged();
        } else {
            mItemLlStatusHide.setVisibility(View.GONE);
        }


        //工人施工款项
        loadPayInfo(dataInfo.getPay_info());

        //业主评价
        if (TextUtils.equals("110", dataInfo.getStatus())) {
            mIdppAssessCardview.setVisibility(View.VISIBLE);
            mIdppEvaluateDataTv.setText(TextUtils.isEmpty(dataInfo.getEvaluated_date()) ? "" : TimeUtil.getDateForMin(dataInfo.getEvaluated_date()));
            mIdppRatingBar.setStar(Float.valueOf(TextUtils.isEmpty(dataInfo.getScore()) ? "0" : dataInfo.getScore()));
            mIdppCustomerContextTv.setText(TextUtils.isEmpty(dataInfo.getAssess()) ? "" : dataInfo.getAssess());

        } else {
            mIdppAssessCardview.setVisibility(View.GONE);
        }

    }

    /**
     * 加载显示工人施工款项
     *
     * @param payInfo
     */
    private void loadPayInfo(List<PointProgressDetailEntity.NodeBean.PayInfoBean> payInfo) {
        if (payInfo == null || payInfo.size() == 0) {
            mIdppPayCardview.setVisibility(View.GONE);
        } else {
            mIdppPayCardview.setVisibility(View.VISIBLE);
            mIdppPrive.setText(TextUtils.isEmpty(payInfo.get(0).getPrice()) ? "" : payInfo.get(0).getPrice());
            mIdppMemoTv.setText(TextUtils.isEmpty(payInfo.get(0).getMemo()) ? "" : payInfo.get(0).getMemo());
        }

    }

    private void initData() {

        mAdapter.setOnItemBtnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                showPopupWindow(view, mAdapter.getItem(position));
            }
        });

    }

    private void initDataTemp() {
    }

    public interface OnItemRefreshListener {
        void onItemRefresh();
    }

    public void setOnItemRefreshListener(OnItemRefreshListener itemRefreshListener) {
        this.itemRefreshListener = itemRefreshListener;
    }


    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
//        if (itemRefreshListener != null) {
//            itemRefreshListener.onItemRefresh();
//        }

        switch (sAnimationStyle) {

            case CUBE:
                switch (getArguments().getInt("direction")) {
                    case LEFT:
                        return CubeAnimation.create(CubeAnimation.LEFT, enter, DURATION);
                    case RIGHT:
                        return CubeAnimation.create(CubeAnimation.RIGHT, enter, DURATION);
                }
                break;
            case FLIP:
                switch (getArguments().getInt("direction")) {
                    case LEFT:
                        return FlipAnimation.create(FlipAnimation.LEFT, enter, DURATION);
                    case RIGHT:
                        return FlipAnimation.create(FlipAnimation.RIGHT, enter, DURATION);
                }
                break;
            case PUSHPULL:
                switch (getArguments().getInt("direction")) {
                    case LEFT:
                        return PushPullAnimation.create(PushPullAnimation.LEFT, enter, DURATION);
                    case RIGHT:
                        return PushPullAnimation.create(PushPullAnimation.RIGHT, enter, DURATION);
                }
                break;
            case SIDES:
                switch (getArguments().getInt("direction")) {
                    case LEFT:
                        return SidesAnimation.create(SidesAnimation.LEFT, enter, DURATION);
                    case RIGHT:
                        return SidesAnimation.create(SidesAnimation.RIGHT, enter, DURATION);
                }
                break;

            case MOVEPULL:
                switch (getArguments().getInt("direction")) {
                    case LEFT:
                        return enter ? PushPullAnimation.create(PushPullAnimation.LEFT, enter, DURATION) :
                                MoveAnimation.create(MoveAnimation.LEFT, enter, DURATION).fading(1.0f, 0.3f);
                    case RIGHT:
                        return enter ? PushPullAnimation.create(PushPullAnimation.RIGHT, enter, DURATION) :
                                MoveAnimation.create(MoveAnimation.RIGHT, enter, DURATION).fading(1.0f, 0.3f);
                }
                break;
        }
        return null;
    }


    public void setAnimationStyle(@AnimationStyle int style) {
        if (sAnimationStyle != style) {
            sAnimationStyle = style;
            Snackbar.make(getView(), "Animation Style is Changed", Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * ---------------------------PoputWindow--------------------------------
     */
    private PopupWindow mPopupWindow;

    private LinearLayout mPopupRemind;
    private LinearLayout mLlPopupClose;
    private LinearLayout mLlPopupPhoneIcon;
    private ImageView mIvPopupIcon;
    private ImageView mIvPopupImage;
    private TextView mTvPopupTitle;
    private TextView mTvPopupContent1;
    private TextView mTvPopupContent2;
    private TextView mTvPopupTime;
    private TextView mTvPopupOwnerName;
    private TextView mTvPopupOwnerPhone;

    /**
     * 初始化popWindow
     */
    private void initPopWindow(View popView, PopupWindow popupWindow) {

        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        //popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);


        mPopupRemind = (LinearLayout) popView.findViewById(R.id.popup_remind);

        mIvPopupIcon = (ImageView) popView.findViewById(R.id.iv_popup_icon);
        mIvPopupImage = (ImageView) popView.findViewById(R.id.iv_popup_image);
        mTvPopupTitle = (TextView) popView.findViewById(R.id.tv_popup_title);
        mTvPopupContent1 = (TextView) popView.findViewById(R.id.tv_popup_content1);
        mTvPopupContent2 = (TextView) popView.findViewById(R.id.tv_popup_content2);
        mTvPopupTime = (TextView) popView.findViewById(R.id.tv_popup_time);
        mTvPopupOwnerName = (TextView) popView.findViewById(R.id.tv_popup_ownerName);
        mTvPopupOwnerPhone = (TextView) popView.findViewById(R.id.tv_popup_ownerPhone);

        mLlPopupClose = (LinearLayout) popView.findViewById(R.id.ll_popup_close);
        mLlPopupPhoneIcon = (LinearLayout) popView.findViewById(R.id.ll_popup_phoneIcon);

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha, float bgDim) {
        WindowManager.LayoutParams lp = _mActivity.getWindow().getAttributes();
        lp.dimAmount = bgDim;
        lp.alpha = bgAlpha; //0.0-1.0
        _mActivity.getWindow().setAttributes(lp);

        _mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f, 0.1f);
        }
    }


    private void showPopupWindow(View view, PointProgressDetailEntity.NodeBean.LogsBean mLogsBean) {

        // 一个自定义的布局，作为显示的内容
        View popView = LayoutInflater.from(_mActivity).inflate(R.layout.popup_remind_with_picture, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //初始化
        initPopWindow(popView, mPopupWindow);

        mTvPopupTitle.setText(mLogsBean.getTitle());
        mTvPopupContent1.setText("不确认原因：");
        mTvPopupContent2.setText(mLogsBean.getReason());
        mTvPopupTime.setText(mLogsBean.getCreatetime());
        mTvPopupOwnerName.setText(mLogsBean.getCustomer_name());
        mTvPopupOwnerPhone.setText(mLogsBean.getTelephone());

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

                new SweetAlertDialog(_mActivity, SweetAlertDialog.NORMAL_TYPE)
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
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });


        /** 禁止点击外部区域取消popup windows*/
        LinearLayout layouttemp = (LinearLayout) popView
                .findViewById(R.id.popup_remind);
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

        backgroundAlpha(0.3f, 1f);//透明度
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());

        mPopupWindow.update();
        if (!mPopupWindow.isShowing()) {
            //设置显示位置
            mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }

    }


    /** -------------------------------------------------------------------------------------*/
}
