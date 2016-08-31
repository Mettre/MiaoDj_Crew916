package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.autolayout.AutoFrameLayout;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.MyProjectsDetailEntity;
import cn.chenhai.miaodj_monitor.model.entity.MyProjectsEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.utils.TimeUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 项目详情
 * <对应状态：新建的项目，等待施工员确认，施工员拒绝>
 * <p>
 * Created by ChenHai--霜华 on 2016/6/7. 11:03
 * 邮箱：248866527@qq.com
 */
public class DetailAgreeFragment extends BaseBackFragment {
    private static final String ARG_ITEM = "arg_item";

    private boolean ifAgree = false;
    private SubscriberOnSuccessListener mOnSuccessInit;
    private SubscriberOnSuccessListener mOnSuccessListenerBecomeTo;

    private String mProjectCode;
    private String mBargain_code;
    private Toolbar mToolbar;
    private TextView mTvTitle;

    private ScrollView mDetailScroll;
    private TextView mDetailName;
    private TextView mDetailProgress;
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
    private TextView mDetailProjectAddr;
    private LinearLayout mDetailProjectAddrImg;
    private Button mBtnOK;
    private Button mBtnCancel;

    private TextView mTvOKRefuse;
    private Button mBtnCancelRefuse;
    private TextView mDelayData;

    public static DetailAgreeFragment newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        DetailAgreeFragment fragment = new DetailAgreeFragment();
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
        View view = inflater.inflate(R.layout.fragment_detail_agree, container, false);
        initView(view);
        initData();
        return view;
        //return attachToSwipeBack(view);
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mTvTitle = (TextView) view.findViewById(R.id.toolbar_title);


        mDetailScroll = (ScrollView) view.findViewById(R.id.detail_Scroll);
        mDetailScroll.smoothScrollTo(0, 0);

        initToolbarNav(mToolbar);

        mDetailName = (TextView) view.findViewById(R.id.detail_name);
        mDetailProgress = (TextView) view.findViewById(R.id.detail_progress);
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
        mDetailProjectAddr = (TextView) view.findViewById(R.id.detail_project_addr);
        mDetailProjectAddrImg = (LinearLayout) view.findViewById(R.id.detail_project_addr_img);
        mBtnOK = (Button) view.findViewById(R.id.btn_OK);
        mBtnCancel = (Button) view.findViewById(R.id.btn_Cancel);

        mTvOKRefuse = (TextView) view.findViewById(R.id.tv_OK_refuse);
        mBtnCancelRefuse = (Button) view.findViewById(R.id.btn_Cancel_refuse);
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
                    mBargain_code=project.getBargain_code();

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
                            mBtnOK.setVisibility(View.GONE);
                            mBtnCancel.setVisibility(View.GONE);
                            mTvOKRefuse.setVisibility(View.VISIBLE);
                            mBtnCancelRefuse.setVisibility(View.VISIBLE);
                            break;
                        case "40":
                            status = "施工员已确认";
                            break;
                        case "50":
                            status = "施工员申请施工进场";
                            break;
                        case "60":
                            status = "业主拒绝施工进场";
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
                    mDetailProgress.setText(status);

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

            }

            @Override
            public void onError() {

            }
        };
        mOnSuccessListenerBecomeTo = new SubscriberOnSuccessListener<HttpResult<MyProjectsEntity>>() {
            @Override
            public void onSuccess(HttpResult<MyProjectsEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (ifAgree) {
                        start(DetailStartFragment.newInstance(mProjectCode));
                        pop();
                        //replaceLoadRootFragment(DetailStartFragment.newInstance(mProjectCode));
                    } else {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("")
                                .setContentText("已提交拒绝!")
                                .setConfirmText("关闭")
                                .show();
                        mBtnOK.setVisibility(View.GONE);
                        mBtnCancel.setVisibility(View.GONE);
                        mTvOKRefuse.setVisibility(View.VISIBLE);
                        mBtnCancelRefuse.setVisibility(View.VISIBLE);
                    }
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
        mTvTitle.setText("项目详情");

        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().getProjectDetail(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token, mProjectCode);

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ifAgree = true;
                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                HttpMethods.getInstance().doBecomeToCrew(new ProgressSubscriber(mOnSuccessListenerBecomeTo, _mActivity), user_code, access_token, mProjectCode, "Y");

            }
        });

        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("确定要拒绝成为施工员吗？")
                        .setCancelText("取消")
                        .setConfirmText("确定！")
                        .showCancelButton(true)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                ifAgree = false;
                                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                                HttpMethods.getInstance().doBecomeToCrew(new ProgressSubscriber(mOnSuccessListenerBecomeTo, _mActivity), user_code, access_token, mProjectCode, "N");

                                sDialog.dismiss();
                            }
                        })
                        .show();
            }
        });

        mBtnCancelRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.setResult(RESULT_CANCELED);
                _mActivity.finish();
            }
        });

        mDetailLayoutContractInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //合同及付款信息
                start(DetailContractInfoFragment.newInstance(mBargain_code));
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

    }

    @Override
    public void onStop() {
        super.onStop();
        //mOnSuccessListener = null;
    }
}
