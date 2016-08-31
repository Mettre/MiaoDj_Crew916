package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.autolayout.AutoFrameLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.CheckPictureEntity;
import cn.chenhai.miaodj_monitor.model.entity.MyProjectsDetailEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.ui.fragment.worker.WorkerCheckFragment;
import cn.chenhai.miaodj_monitor.ui.module.preview.ImageInfo;
import cn.chenhai.miaodj_monitor.ui.module.preview.ImagePreviewActivity;
import cn.chenhai.miaodj_monitor.utils.TimeUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 项目详情
 * <对应状态：施工中，施工完成，停工>
 * <p>
 * Created by ChenHai--霜华 on 2016/6/13. 14:28
 * 邮箱：248866527@qq.com
 */
public class DetailIndexFragment extends BaseBackFragment {
    private static final String ARG_ITEM = "arg_item";

    private String mProjectCode;
    private String mCustomer_code;
    private String mBargain_code;

    private SubscriberOnSuccessListener mOnSuccessInit;
    private SubscriberOnSuccessListener mOnSuccessListenerPic;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ScrollView mDetailScroll;
    private TextView mDetailName;

    private TextView mDetailProgress;
    private TextView mDetailHouseType;
    private TextView mDetailArea;
    private TextView mDetailPeopleNumber;
    private TextView mDetailManager;
    private TextView mDetailManagerPhone;
    private TextView mDetailDesign;
    private TextView mDetailDesignPhone;
    private TextView mDetailMonitor;
    private TextView mDetailMonitorPhone;
    private TextView mDetailContractTime;
    private TextView mDetailWorkStartTime;
    private TextView mDetailWorkLimitTime;
    private TextView mDetailProjectAddr;
    private TextView mDelayData;
    private ImageView mIvDetailMessage;
    private ImageView mIvDetailLog;
    private ImageView mIvDetailChoose;
    private ImageView mIvDetailPic;
    private AutoFrameLayout mDetailLayoutCheck;
    private AutoFrameLayout mDetailLayoutContractInfo;
    private AutoFrameLayout mDetailLayoutWorkers;

    private LinearLayout mDetailManagerPhoneImg;
    private LinearLayout mDetailDesignPhoneImg;
    private LinearLayout mDetailMonitorPhoneImg;
    private LinearLayout mDetailProjectAddrImg;


    private CardView mCardView;
    private PtrClassicFrameLayout mPtrFrame;


    public static DetailIndexFragment newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        DetailIndexFragment fragment = new DetailIndexFragment();
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
        View view = inflater.inflate(R.layout.fragment_detail_index, container, false);
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

        /**********************************************************************/
//        WaveView mWaveView = (WaveView) view.findViewById(R.id.detail_waveView);
//        mWaveView.setDuration(5000);
//        //mWaveView.setStyle(Paint.Style.STROKE);
//        mWaveView.setStyle(Paint.Style.FILL);
//        mWaveView.setSpeed(700);
//        mWaveView.setColor(Color.parseColor("#ff0000"));
//        mWaveView.setInterpolator(new LinearOutSlowInInterpolator());
//        mWaveView.start();
        /**********************************************************************/

        mDetailScroll = (ScrollView) view.findViewById(R.id.detail_Scroll);
        mDetailName = (TextView) view.findViewById(R.id.detail_name);
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

        mDetailProgress = (TextView) view.findViewById(R.id.detail_progress);
        mDetailLayoutCheck = (AutoFrameLayout) view.findViewById(R.id.detail_layout_check);

        mIvDetailMessage = (ImageView) view.findViewById(R.id.Iv_detail_message);
        mIvDetailLog = (ImageView) view.findViewById(R.id.Iv_detail_log);
        mIvDetailChoose = (ImageView) view.findViewById(R.id.Iv_detail_choose);
        mIvDetailPic = (ImageView) view.findViewById(R.id.Iv_detail_pic);
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

                    mCustomer_code = project.getCustomer_code();
                    mBargain_code=project.getBargain_code();

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

        mOnSuccessListenerPic = new SubscriberOnSuccessListener<HttpResult<CheckPictureEntity>>() {
            @Override
            public void onSuccess(HttpResult<CheckPictureEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    List<CheckPictureEntity.DrawingBean> projects = result.getInfo().getDrawing();
                    ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
                    for (int i = 0; i < projects.size(); i++) {
                        ImageInfo info1 = new ImageInfo();
                        CheckPictureEntity.DrawingBean drawInfo = projects.get(i);

                        info1.setThumbnailUrl(HttpMethods.BASE_ROOT_URL + drawInfo.getImgurl());
                        info1.setBigImageUrl(HttpMethods.BASE_ROOT_URL + drawInfo.getImgurl());

                        imageInfoList.add(info1);
                    }

                    if (imageInfoList.size() != 0) {
                        Intent intent = new Intent(_mActivity, ImagePreviewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfoList);
                        bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        (_mActivity).overridePendingTransition(0, 0);
                    } else {
                        //Toast.makeText(_mActivity,"图纸不存在，或网络获取错误！",Toast.LENGTH_SHORT).show();
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("错误！")
                                .setContentText("图纸不存在，或网络获取错误！")
                                .setConfirmText("关闭")
                                .show();
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

        refreshData();
    }

    private void initData() {
        mToolbarTitle.setText("项目详情");

        mDetailManagerPhoneImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new AlertDialog.Builder(_mActivity)
//                        .setTitle("拨打电话")
//                        .setMessage( mDetailManagerPhone.getText())
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String phone = mDetailManagerPhone.getText().toString();
//                                //用intent启动拨打电话
//                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
//                                startActivity(intent);
//                            }
//                        })
//                        .setNegativeButton("关闭", null)
//                        .setCancelable(true)
//                        .show();
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
//                new AlertDialog.Builder(_mActivity)
//                        .setTitle("拨打电话")
//                        .setMessage( mDetailDesignPhone.getText())
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String phone = mDetailDesignPhone.getText().toString();
//                                //用intent启动拨打电话
//                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
//                                startActivity(intent);
//                            }
//                        })
//                        .setNegativeButton("关闭", null)
//                        .setCancelable(true)
//                        .show();

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
        mDetailProjectAddrImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

        mDetailLayoutCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看节点
                start(DetailPointFragment.newInstance(mProjectCode));
            }
        });
        /////////////////////////////////////////////////////////////////////////
        mIvDetailMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mIvDetailLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(DetailBuildDiaryFragment.newInstance(mProjectCode));
            }
        });
        //选品单
        mIvDetailChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(DetailSelectionListFragment2.newInstance(mProjectCode, mCustomer_code));
            }
        });
        mIvDetailPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                HttpMethods.getInstance().getDrawPicture(new ProgressSubscriber(mOnSuccessListenerPic, _mActivity), user_code, access_token, mProjectCode);
            }
        });
    }

    private void initPullRefresh(View view) {
        //view.setBackgroundColor(getResources().getColor(R.color.gray));
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

    private void refreshData() {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().getProjectDetail(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token, mProjectCode);
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

    @Override
    public void onStop() {
        super.onStop();
        //mOnSuccessListener = null;
    }
}
