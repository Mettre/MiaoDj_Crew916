package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.autolayout.AutoFrameLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.MyProjectsDetailEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.ui.fragment.worker.WorkerCheckFragment;
import cn.chenhai.miaodj_monitor.utils.TimeUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 项目详情
 * <对应状态：施工员已确认，施工员申请施工进场，业主拒绝施工进场>
 * <p>
 * Created by ChenHai--霜华 on 2016/6/7. 11:03
 * 邮箱：248866527@qq.com
 */
public class DetailStartFragment extends BaseBackFragment {
    private static final String ARG_ITEM = "arg_item";

    private SubscriberOnSuccessListener mOnSuccessInit;
    private SubscriberOnSuccessListener mOnSuccessApplyStart;

    private String mProjectCode;
    private String mBargain_code;
    private int mIfCanApplyStart = 0;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ScrollView mDetailScroll;
    private TextView mDetailName;
    private FrameLayout mDetailFlNotOk;
    private TextView mDetailTvNotOk;
    private TextView mDetailTvNotOkReason;
    private AutoFrameLayout mDetailFlApply;
    private Button mDetailBtnApply;
    private TextView mDetailHouseType;
    private TextView mDetailArea;
    private TextView mDetailPeopleNumber;
    private TextView mDetailManager;
    private TextView mDetailManagerPhone;
    private LinearLayout mDetailManagerPhoneImg;
    private TextView mDetailDesign;
    private TextView mDetailDesignPhone;
    private LinearLayout mDetailDesignPhoneImg;
    private TextView mDetailMonitor;
    private TextView mDetailMonitorPhone;
    private LinearLayout mDetailMonitorPhoneImg;
    private TextView mDetailContractTime;
    private AutoFrameLayout mDetailLayoutContractInfo;
    private TextView mDetailWorkStartTime;
    private TextView mDetailWorkLimitTime;
    private AutoFrameLayout mDetailLayoutWorkers;
    private TextView mDetailProjectAddr;
    private LinearLayout mDetailProjectAddrImg;

    private FrameLayout mDetailFlConfirmPic;
    private ImageView mDetailIvConfirmPicStatus;
    private TextView mDetailTvConfirmPic;
    private TextView mDetailTvConfirmPicTime;
    private ImageView mDetailIvConfirmPicArrow;
    private TextView mDelayData;


    private CardView mCardView;
    private PtrClassicFrameLayout mPtrFrame;

    public static DetailStartFragment newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        DetailStartFragment fragment = new DetailStartFragment();
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
        View view = inflater.inflate(R.layout.fragment_detail_start, container, false);
        initView(view);
        initData();
        initPullRefresh(view);
        return view;
        //return attachToSwipeBack(view);
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);

        mDetailScroll = (ScrollView) view.findViewById(R.id.detail_Scroll);
        mDetailName = (TextView) view.findViewById(R.id.detail_name);
        mDetailFlNotOk = (FrameLayout) view.findViewById(R.id.detail_fl_notOk);
        mDetailTvNotOk = (TextView) view.findViewById(R.id.detail_tv_notOk);
        mDetailTvNotOkReason = (TextView) view.findViewById(R.id.detail_tv_notOk_reason);


        mDetailFlApply = (AutoFrameLayout) view.findViewById(R.id.detail_fl_apply);
        mDetailBtnApply = (Button) view.findViewById(R.id.detail_btn_apply);
        mDetailHouseType = (TextView) view.findViewById(R.id.detail_house_type);
        mDetailArea = (TextView) view.findViewById(R.id.detail_area);
        mDetailPeopleNumber = (TextView) view.findViewById(R.id.detail_people_number);
        mDetailManager = (TextView) view.findViewById(R.id.detail_manager);
        mDetailManagerPhone = (TextView) view.findViewById(R.id.detail_manager_phone);
        mDetailManagerPhoneImg = (LinearLayout) view.findViewById(R.id.detail_manager_phone_img);
        mDetailDesign = (TextView) view.findViewById(R.id.detail_design);
        mDetailDesignPhone = (TextView) view.findViewById(R.id.detail_design_phone);
        mDetailDesignPhoneImg = (LinearLayout) view.findViewById(R.id.detail_design_phone_img);
        mDetailMonitor = (TextView) view.findViewById(R.id.detail_monitor);
        mDetailMonitorPhone = (TextView) view.findViewById(R.id.detail_monitor_phone);
        mDetailMonitorPhoneImg = (LinearLayout) view.findViewById(R.id.detail_monitor_phone_img);
        mDetailContractTime = (TextView) view.findViewById(R.id.detail_contract_time);
        mDetailLayoutContractInfo = (AutoFrameLayout) view.findViewById(R.id.detail_layout_contract_info);
        mDetailWorkStartTime = (TextView) view.findViewById(R.id.detail_work_start_time);
        mDetailWorkLimitTime = (TextView) view.findViewById(R.id.detail_work_limit_time);
        mDetailLayoutWorkers = (AutoFrameLayout) view.findViewById(R.id.detail_layout_workers);
        mDetailProjectAddr = (TextView) view.findViewById(R.id.detail_project_addr);
        mDetailProjectAddrImg = (LinearLayout) view.findViewById(R.id.detail_project_addr_img);

        mDetailFlConfirmPic = (FrameLayout) view.findViewById(R.id.detail_fl_confirmPic);
        mDetailIvConfirmPicStatus = (ImageView) view.findViewById(R.id.detail_iv_confirmPic_status);
        mDetailTvConfirmPic = (TextView) view.findViewById(R.id.detail_tv_confirmPic);
        mDetailTvConfirmPicTime = (TextView) view.findViewById(R.id.detail_tv_confirmPic_time);
        mDetailIvConfirmPicArrow = (ImageView) view.findViewById(R.id.detail_iv_confirmPic_arrow);
        mDelayData = (TextView) view.findViewById(R.id.detail_work_delayDataTv);

        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<MyProjectsDetailEntity>>() {
            @Override
            public void onSuccess(HttpResult<MyProjectsDetailEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    MyProjectsDetailEntity.ProjectBean project = result.getInfo().getProject();

                    StringBuilder itemName = new StringBuilder();
                    itemName.append(project.getResidential());
                    if (project.getApartment() != null) {
                        if (!project.getApartment().equals("")) {
                            itemName.append(project.getApartment());
                            itemName.append("幢");
                        }
                    }
                    if (project.getRoom() != null) {
                        if (!project.getRoom().equals("")) {
                            itemName.append(project.getRoom());
                        }
                    }
                    itemName.append("装修项目");
                    mDetailName.setText(itemName.toString());
                    mBargain_code = project.getBargain_code();

                    String status = "";
                    switch (project.getStatus()) {
                        case "1":
                            status = "新建的项目";
                            break;
                        case "20":
                            status = "等待施工员确认";
                            break;
                        case "30":
                            status = "施工员拒绝";
                            break;
                        case "40":
                            status = "施工员已确认";
                            break;
                        case "50":
                            status = "施工员申请施工进场";
                            mDetailFlNotOk.setVisibility(View.VISIBLE);
                            mDetailTvNotOk.setText("等待业主确认施工进场");
                            mDetailTvNotOk.setTextColor(0xff3ca0ec);
                            String date = "申请施工进场日期 " + project.getApply_start_date();
                            mDetailTvNotOkReason.setText(date);
                            mDetailFlApply.setVisibility(View.GONE);
                            break;
                        case "60":
                            status = "业主拒绝施工进场";
                            mDetailFlNotOk.setVisibility(View.VISIBLE);
                            mDetailTvNotOk.setText("业主不确认施工进场");
                            mDetailTvNotOk.setTextColor(0xffFE1901);
                            mDetailTvNotOkReason.setText(project.getDisagree_reason());
                            mDetailFlApply.setVisibility(View.VISIBLE);
                            mDetailBtnApply.setText("重新申请施工进场");
                            break;
                        case "70":
                            status = "施工中";
                            break;
                        case "80":
                            status = "项目完成";
                            break;
                        case "90":
                            status = "停工";
                            break;
                    }

                    String drawStatus = "";
                    switch (project.getDrawing_status()) {
                        case "1":
                            drawStatus = "图纸待上传";
                            mIfCanApplyStart = 0;
                            break;
                        case "20":
                            drawStatus = "图纸待客户确认";
                            mIfCanApplyStart = 0;
                            break;
                        case "21":
                            drawStatus = "图纸客户确认不通过";
                            mIfCanApplyStart = 0;
                            break;
                        case "30":
                            drawStatus = "图纸待施工员确认";
                            mIfCanApplyStart = 1;
                            break;
                        case "45":
                            drawStatus = "施工员已确认图纸";
                            mIfCanApplyStart = 2;
                            break;
                        case "46":
                            drawStatus = "施工员不确认图纸";
                            mIfCanApplyStart = 0;
                            break;
                    }
                    if (mIfCanApplyStart == 0) {
                        mDetailIvConfirmPicStatus.setBackgroundResource(R.drawable.ic_status_2);
                        mDetailTvConfirmPic.setText("图纸未确认");
                        mDetailTvConfirmPicTime.setVisibility(View.GONE);
                        mDetailIvConfirmPicArrow.setVisibility(View.GONE);
                        mDetailFlConfirmPic.setEnabled(false);
                        mDetailBtnApply.setEnabled(false);
                    } else if (mIfCanApplyStart == 1) {
                        mDetailIvConfirmPicStatus.setBackgroundResource(R.drawable.ic_status_2);
                        mDetailTvConfirmPic.setText("图纸未确认");
                        mDetailTvConfirmPicTime.setVisibility(View.GONE);
                        mDetailIvConfirmPicArrow.setVisibility(View.VISIBLE);
                        mDetailFlConfirmPic.setEnabled(true);
                        mDetailBtnApply.setEnabled(false);
                    } else {
                        mDetailIvConfirmPicStatus.setBackgroundResource(R.drawable.ic_status_1);
                        mDetailTvConfirmPic.setText("图纸已确认");
                        mDetailTvConfirmPicTime.setVisibility(View.VISIBLE);
                        mDetailTvConfirmPicTime.setText(TimeUtil.getDate(project.getUpdatetime()));
                        mDetailIvConfirmPicArrow.setVisibility(View.VISIBLE);
                        mDetailFlConfirmPic.setEnabled(true);
                        mDetailBtnApply.setEnabled(true);
                    }
                    //mDetailProgress.setText(status);

                    StringBuilder house = new StringBuilder();
                    house.append(project.getLiveroom_chinese());
                    house.append("室");
                    house.append(project.getHall_chinese());
                    house.append("厅");
                    house.append(project.getKitchen_chinese());
                    house.append("厨");
                    house.append(project.getBathroom_chinese());
                    house.append("卫");
                    house.append(project.getSunplat_chinese());
                    house.append("阳台");
                    mDetailHouseType.setText(house);

                    String area = project.getArea() + " ㎡";
                    mDetailArea.setText(area);

                    String peopleNumber = project.getPersons() + " 人";
                    mDetailPeopleNumber.setText(peopleNumber);

                    mDetailManager.setText(project.getManager_name());
                    mDetailManagerPhone.setText(project.getManager_telephone());

                    mDetailDesign.setText(project.getDesign_name());
                    mDetailDesignPhone.setText(project.getDesign_telephone());

                    mDetailMonitor.setText(project.getCrew_name());
                    mDetailMonitorPhone.setText(project.getCrew_telephone());

                    mDetailContractTime.setText(TimeUtil.getDate(project.getBargain_createtime()));

                    if (project.getStart_date() == null) {
                        mDetailWorkStartTime.setText("未开始");
                    } else {
                        mDetailWorkStartTime.setText(project.getStart_date());
                    }

                    String limitdays = project.getTotal_days() + "天";
                    mDetailWorkLimitTime.setText(limitdays);

                    StringBuilder address = new StringBuilder();
                    address.append(project.getHouse_province_name());
                    address.append(project.getHouse_city_name());
                    address.append(project.getHouse_area_name());
                    address.append(project.getStreet());
                    address.append(project.getResidential());
                    if (project.getApartment() != null) {
                        if (!project.getApartment().equals("")) {
                            address.append(project.getApartment());
                            address.append("幢");
                        }
                    }
                    if (project.getRoom() != null) {
                        if (!project.getRoom().equals("")) {
                            address.append(project.getRoom());
                            address.append("室");
                        }
                    }
                    mDetailProjectAddr.setText(address);

                    //延期工期
//                    String limitdays = project.getTotal_days()+"天";
//                    mDetailWorkLimitTime.setText(limitdays);

                    //工期
                    mDetailWorkLimitTime.setText(project.getDelay_days() > 0 ? "41" : project.getTotal_days() + "天");
                    //延期日期
                    mDelayData.setVisibility(project.getDelay_days() > 0 ? View.VISIBLE : View.GONE);
                    mDelayData.setText(Html.fromHtml(String.format(getResources().getString(R.string.project_detail_delay), project.getDelay_days())));
                }
            }

            @Override
            public void onCompleted() {
                mPtrFrame.refreshComplete();
            }

            @Override
            public void onError() {
                mPtrFrame.refreshComplete();
            }
        };


        mOnSuccessApplyStart = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("已提交进场申请!")
                            .setConfirmText("关闭")
                            .show();
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {

            }
        };
    }

    private void initData() {
        mToolbarTitle.setText("项目详情");

        refreshData();

        mDetailFlConfirmPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(DetailConfirmDrawPicture.newInstance(mProjectCode));
            }
        });

        mDetailBtnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mDetailFlNotOk.setVisibility(View.VISIBLE);
                showPopupWindow(v);
            }
        });

        ////////////////////////////////////////////////////////////////////////
        mDetailManagerPhoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(_mActivity, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("拨打电话")
                        .setContentText(mDetailManagerPhone.getText().toString())
                        .setCancelText("取消")
                        .setConfirmText("确定！")
                        .showCancelButton(true)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                String phone = mDetailManagerPhone.getText().toString();
                                //用intent启动拨打电话
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
        mDetailDesignPhoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(_mActivity, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("拨打电话")
                        .setContentText(mDetailDesignPhone.getText().toString())
                        .setCancelText("取消")
                        .setConfirmText("确定！")
                        .showCancelButton(true)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                String phone = mDetailDesignPhone.getText().toString();
                                //用intent启动拨打电话
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });
        mDetailMonitorPhoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(_mActivity, SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("拨打电话")
                        .setContentText(mDetailMonitorPhone.getText().toString())
                        .setCancelText("取消")
                        .setConfirmText("确定！")
                        .showCancelButton(true)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                String phone = mDetailMonitorPhone.getText().toString();
                                //用intent启动拨打电话
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                                startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        ////////////////////////////////////////////////////////////////////////
        mDetailLayoutContractInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //合同及付款信息
                start(DetailContractInfoFragment.newInstance(mBargain_code));
            }
        });
        mDetailLayoutWorkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //施工工人
                start(WorkerCheckFragment.newInstance(mProjectCode));
            }
        });
        mDetailProjectAddrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //项目地址定位
            }
        });

    }

    private void refreshData() {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().getProjectDetail(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token, mProjectCode);
    }

    private void initPullRefresh(View view) {
        view.setBackgroundColor(getResources().getColor(R.color.gray));
        mPtrFrame = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_refresh);
        mCardView = (CardView) view.findViewById(R.id.detail_cardView);

        mPtrFrame.setLastUpdateTimeRelateObject(this);


        mPtrFrame.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 500);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 500);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, mCardView, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //return super.checkCanDoRefresh(frame, mCardView, header);
                return checkCanDoRefreshLocal();
            }
        });


        // the following are default settings
        mPtrFrame.setResistance(1.7f); // you can also set foot and header separately
        mPtrFrame.setRatioOfHeaderHeightToRefresh(1.2f);
        mPtrFrame.setDurationToClose(1000);  // you can also set foot and header separately
        // default is false
        mPtrFrame.setPullToRefresh(false);

        // default is true
        mPtrFrame.setKeepHeaderWhenRefresh(true);
        mPtrFrame.postDelayed(new Runnable() {
            @Override
            public void run() {
                // mPtrFrame.autoRefresh();
            }
        }, 100);
    }

    public boolean checkCanDoRefreshLocal() {

        int a = mDetailScroll.getScrollY();
        if (a <= 0) {
            return true;
        }
        return false;
//        int a = mLLmanager.findFirstVisibleItemPosition();
//        int b = mRecy.getChildAt(0).getTop();
//        boolean ifTrue = a == 0 && b == 25;
//        return ifTrue;
    }

//    protected void updateData() {
//        mPtrFrame.refreshComplete();
//    }


    /**
     * ---------------------------PoputWindow--------------------------------
     */
    private PopupWindow mPopupWindow;

    //private AutoFrameLayout mPopupApply;
    private ImageButton mApplyImgClose;
    private TextView mPublishTvDate;
    private Button mPublishBtn;
    private AutoFrameLayout mSelectTime;

    /**
     * 初始化popWindow
     */
    private void initPopWindow(View popView, PopupWindow popupWindow) {

        //popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        //popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        //mPopupApply = (AutoFrameLayout) popView.findViewById(R.id.popup_apply);
        mPublishTvDate = (TextView) popView.findViewById(R.id.publish_tv_date);

        mApplyImgClose = (ImageButton) popView.findViewById(R.id.apply_img_close);
        mPublishBtn = (Button) popView.findViewById(R.id.publish_btn);
        mSelectTime = (AutoFrameLayout) popView.findViewById(R.id.publish_tv_dateLayout);

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


    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View popView = LayoutInflater.from(_mActivity).inflate(R.layout.popup_apply_to_work, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //初始化
        initPopWindow(popView, mPopupWindow);

        // 设置按钮的点击事件
        mApplyImgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                //测试

            }
        });

        mSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dateDlg = new DatePickerDialog(_mActivity,
                        dataSetCallBack,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH));

                dateDlg.getDatePicker().setMinDate(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 3);
                dateDlg.show();

            }
        });
        upDateDate();


        mPublishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(_mActivity, "输入的货物重量超标！请重输", Toast.LENGTH_SHORT).show();
                long timeMill = dateAndTime.getTimeInMillis();

                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                HttpMethods.getInstance().applyBuildStart(new ProgressSubscriber(mOnSuccessApplyStart, _mActivity), user_code, access_token, mProjectCode, TimeUtil.getFormat(String.valueOf(timeMill)));

                mPopupWindow.dismiss();

                refreshData();

            }
        });


        /** 禁止点击外部区域取消popup windows*/
        FrameLayout layouttemp = (FrameLayout) popView
                .findViewById(R.id.popup_apply);
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


    //获取日期格式器对象
    private DateFormat fmtDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private DateFormat fmtTime = new SimpleDateFormat("HH:mm", Locale.getDefault());

    //获取一个日历对象
    private Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);

    //当点击DatePickerDialog控件的设置按钮时，调用该方法
    DatePickerDialog.OnDateSetListener dataSetCallBack = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            //修改日历控件的年，月，日
            //这里的year,monthOfYear,dayOfMonth的值与DatePickerDialog控件设置的最新值一致
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            //将页面TextView的显示更新为最新时间
            upDateDate();

        }
    };

    private void upDateDate() {
        mPublishTvDate.setText(fmtDate.format(dateAndTime.getTime()));
    }

    /**
     * -------------------------------------------------------------------------------------
     */

    @Override
    public void onStop() {
        super.onStop();
        //mOnSuccessListener = null;
    }
}
