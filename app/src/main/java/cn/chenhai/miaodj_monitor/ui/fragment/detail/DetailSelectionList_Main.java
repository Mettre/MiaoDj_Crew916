package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.request.BaseRequest;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;

import cn.chenhai.miaodj_monitor.Manifest;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.UploadImgResult;
import cn.chenhai.miaodj_monitor.model.entity.EmptyEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListMainEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;
import cn.chenhai.miaodj_monitor.ui.view_custom.NumberProgressBar;
import cn.chenhai.miaodj_monitor.ui.view_custom.TimeSelectPop;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ChenHai--霜华 on 2016/7/12. 16:31
 * 邮箱：248866527@qq.com
 */
public class DetailSelectionList_Main extends BaseBackFragment_Swip {

    private String mOrder_code, mMaterial_code, mSpace_id;
    private String mSignForPicturePath = "";
    private String material_type = "1";
    private String space_id;

    private SubscriberOnSuccessListener mOnSuccessMainMaterial;
    private SubscriberOnSuccessListener mOnSuccessDeliver;
    private SubscriberOnSuccessListener mOnSuccessSignFor;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mTvSelectionCategoryName;
    private TextView mTvSelectionClassName;
    private TextView mTvSelectionTitle;
    private TextView mTvSelectionModel;
    private TextView mTvSelectionBrand;
    private TextView mTvSelectionStandard;
    private TextView mTvSelectionTitle1;
    private TextView mTvSelectionNum;
    private TextView mTvSelectionNumUnit;
    private TextView mTvSelectionStatusDirect;
    private FrameLayout mLayoutDirectHide0;
    private TextView mTvSelectionHearTime;
    private FrameLayout mLayoutDirectHide1;
    private TextView mTvSelectionPrepareTime;
    private FrameLayout mLayoutDirectHide2;
    private TextView mTvSelectionDeliverTime;
    private FrameLayout mLayoutDirectHide3;
    private TextView mTvSelectionDeliverStartTime;
    private FrameLayout mLayoutDirectHide4;
    private TextView mTvSelectionArriveTime;
    private FrameLayout mLayoutDirectHide5;
    private Button mBtnSelectionDirectSignFor;
    private TextView mTvSelectionTitle2;
    private TextView mTvSelectionNum2;
    private TextView mTvSelectionNum2Unit;
    private TextView mTvSelectionStatusProcess;
    private FrameLayout mLayoutProcessHide0;
    private TextView mTvSelectionHearTime2;
    private FrameLayout mLayoutProcessHide1;
    private TextView mTvSelectionPrepareTime2;
    private FrameLayout mLayoutProcessHide2;
    private TextView mTvSelectionProcessTime2;
    private FrameLayout mLayoutProcessHide3;
    private TextView mTvSelectionProcessCompleteTime2;
    private FrameLayout mLayoutProcessHide4;
    private TextView mTvSelectionDeliverTime2;
    private FrameLayout mLayoutProcessHide5;
    private TextView mTvSelectionDeliverTimeStart2;
    private FrameLayout mLayoutProcessHide6;
    private TextView mTvSelectionArriveTime2;
    private FrameLayout mLayoutProcessHide7;
    private Button mBtnSelectionProcessSignFor;

    private LinearLayout mLLCardLayoutDirect;
    private LinearLayout mLLCardLayoutProcess;

    TimeSelectPop mTimePickPop;


    public static DetailSelectionList_Main newInstance(String order_code, String material_code, String space_id) {
        DetailSelectionList_Main fragment = new DetailSelectionList_Main();
        Bundle args = new Bundle();
        args.putString("order_code", order_code);
        args.putString("material_code", material_code);
        args.putString("space_id", space_id);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mOrder_code = args.getString("order_code");
            mMaterial_code = args.getString("material_code");
            mSpace_id = args.getString("space_id");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection_main_detail, container, false);

        //initReceiver();
        initView(view);
        initData();

        //return view;
        return attachToSwipeBack(view);
    }


    private void initView(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);

        mLLCardLayoutDirect = (LinearLayout) view.findViewById(R.id.card_layout_direct);
        mLLCardLayoutProcess = (LinearLayout) view.findViewById(R.id.card_layout_process);

        mTvSelectionCategoryName = (TextView) view.findViewById(R.id.tv_selection_category_name);
        mTvSelectionClassName = (TextView) view.findViewById(R.id.tv_selection_class_name);
        mTvSelectionTitle = (TextView) view.findViewById(R.id.tv_selection_title);
        mTvSelectionModel = (TextView) view.findViewById(R.id.tv_selection_model);
        mTvSelectionBrand = (TextView) view.findViewById(R.id.tv_selection_brand);
        mTvSelectionStandard = (TextView) view.findViewById(R.id.tv_selection_standard);
        mTvSelectionTitle1 = (TextView) view.findViewById(R.id.tv_selection_title_1);
        mTvSelectionNum = (TextView) view.findViewById(R.id.tv_selection_num);
        mTvSelectionNumUnit = (TextView) view.findViewById(R.id.tv_selection_num_unit);
        mTvSelectionStatusDirect = (TextView) view.findViewById(R.id.tv_selection_status_direct);
        mLayoutDirectHide1 = (FrameLayout) view.findViewById(R.id.layout_direct_hide_1);
        mTvSelectionPrepareTime = (TextView) view.findViewById(R.id.tv_selection_prepare_time);
        mLayoutDirectHide2 = (FrameLayout) view.findViewById(R.id.layout_direct_hide_2);
        mTvSelectionDeliverTime = (TextView) view.findViewById(R.id.tv_selection_deliver_time);
        mLayoutDirectHide3 = (FrameLayout) view.findViewById(R.id.layout_direct_hide_3);
        mTvSelectionDeliverStartTime = (TextView) view.findViewById(R.id.tv_selection_deliver_start_time);
        mLayoutDirectHide4 = (FrameLayout) view.findViewById(R.id.layout_direct_hide_4);
        mTvSelectionArriveTime = (TextView) view.findViewById(R.id.tv_selection_arrive_time);
        mLayoutDirectHide5 = (FrameLayout) view.findViewById(R.id.layout_direct_hide_5);
        mBtnSelectionDirectSignFor = (Button) view.findViewById(R.id.btn_selection_direct_signFor);
        mTvSelectionTitle2 = (TextView) view.findViewById(R.id.tv_selection_title_2);
        mTvSelectionNum2 = (TextView) view.findViewById(R.id.tv_selection_num2);
        mTvSelectionNum2Unit = (TextView) view.findViewById(R.id.tv_selection_num2_unit);
        mTvSelectionStatusProcess = (TextView) view.findViewById(R.id.tv_selection_status_process);
        mLayoutProcessHide1 = (FrameLayout) view.findViewById(R.id.layout_process_hide_1);
        mTvSelectionPrepareTime2 = (TextView) view.findViewById(R.id.tv_selection_prepare_time2);
        mLayoutProcessHide2 = (FrameLayout) view.findViewById(R.id.layout_process_hide_2);
        mTvSelectionProcessTime2 = (TextView) view.findViewById(R.id.tv_selection_process_time2);
        mLayoutProcessHide3 = (FrameLayout) view.findViewById(R.id.layout_process_hide_3);
        mTvSelectionProcessCompleteTime2 = (TextView) view.findViewById(R.id.tv_selection_process_complete_time2);
        mLayoutProcessHide4 = (FrameLayout) view.findViewById(R.id.layout_process_hide_4);
        mTvSelectionDeliverTime2 = (TextView) view.findViewById(R.id.tv_selection_deliver_time_2);
        mLayoutProcessHide5 = (FrameLayout) view.findViewById(R.id.layout_process_hide_5);
        mTvSelectionDeliverTimeStart2 = (TextView) view.findViewById(R.id.tv_selection_deliver_time_start_2);
        mLayoutProcessHide6 = (FrameLayout) view.findViewById(R.id.layout_process_hide_6);
        mTvSelectionArriveTime2 = (TextView) view.findViewById(R.id.tv_selection_arrive_time2);
        mLayoutProcessHide7 = (FrameLayout) view.findViewById(R.id.layout_process_hide_7);
        mBtnSelectionProcessSignFor = (Button) view.findViewById(R.id.btn_selection_process_signFor);

        mLayoutDirectHide0 = (FrameLayout) view.findViewById(R.id.layout_direct_hide_0);
        mTvSelectionHearTime = (TextView) view.findViewById(R.id.tv_selection_hear_time);

        mLayoutProcessHide0 = (FrameLayout) view.findViewById(R.id.layout_process_hide_0);
        mTvSelectionHearTime2 = (TextView) view.findViewById(R.id.tv_selection_hear_time2);


        mTimePickPop = new TimeSelectPop(_mActivity, 1, new TimeSelectPop.SubmitOnClickListener() {
            @Override
            public void SubmitOnClickListener(String mData) {

                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                HttpMethods.getInstance().doStartDeliver(new ProgressSubscriber(mOnSuccessDeliver, _mActivity), user_code, access_token, mOrder_code, mMaterial_code,space_id, material_type, mData);
            }
        });


        mBtnSelectionDirectSignFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                material_type = "1"; //不需要加工
//                showPopupWindow(v, mTvSelectionNum.getText().toString(), mTvSelectionNumUnit.getText().toString());

                switch (mBtnSelectionDirectSignFor.getText().toString()) {
                    case "发起配送":
                        material_type = "1";
                        mTimePickPop.show(v);
                        break;
                    case "签收":
                        material_type = "1"; //不需要加工
                        showPopupWindow(v, mTvSelectionNum.getText().toString(), mTvSelectionNumUnit.getText().toString());
                        break;
                }
            }
        });
        mBtnSelectionProcessSignFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mBtnSelectionProcessSignFor.getText().toString()) {
                    case "发起配送":
                        material_type = "2";
                        mTimePickPop.show(v);
                        break;
                    case "签收":
                        material_type = "2"; //需要加工
                        showPopupWindow(v, mTvSelectionNum2.getText().toString(), mTvSelectionNum2Unit.getText().toString());
                        break;
                }
            }
        });

        mOnSuccessMainMaterial = new SubscriberOnSuccessListener<HttpResult<SelectionListMainEntity>>() {
            @Override
            public void onSuccess(HttpResult<SelectionListMainEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {

                        SelectionListMainEntity.MaterialBean beanInfo = result.getInfo().getMaterial();

                        mTvSelectionCategoryName.setText(beanInfo.getCategory_name());
                        mTvSelectionClassName.setText(beanInfo.getClass_name());
                        mTvSelectionTitle.setText(beanInfo.getTitle());
                        mTvSelectionModel.setText(beanInfo.getModel());
                        mTvSelectionBrand.setText(beanInfo.getBrand_name());
                        mTvSelectionStandard.setText(beanInfo.getSpecs());

                        float totalAmount = 0;
                        float cutAmount = 0;
                        if (beanInfo.getAmount() != null) {
                            totalAmount = Float.valueOf(beanInfo.getAmount());
                        }
                        if (beanInfo.getCut_amount() != null) {
                            cutAmount = Float.valueOf(beanInfo.getCut_amount());
                        }
                        mLayoutDirectHide5.setVisibility(View.GONE);
                        mLayoutProcessHide7.setVisibility(View.GONE);
                        String directStatus = "";
                        String processStatus = "";
                        if (beanInfo.getChoose_status() != null && !beanInfo.getChoose_status().equals("0")) {
                            mLLCardLayoutDirect.setVisibility(View.VISIBLE);
                            switch (beanInfo.getChoose_status()) {
                                case "1":  //初始加载客户确认订单，待受理
                                    directStatus = "待受理";
                                    break;
                                case "2":  // 供应链采购中，备货中
                                    directStatus = "备货中";
                                    break;
                                case "3":  // 供应链备货完成，待配送
                                    directStatus = "待配送";
                                    mLayoutDirectHide5.setVisibility(View.VISIBLE);
                                    mBtnSelectionDirectSignFor.setText("发起配送");
                                    break;
                                case "4":  // 已到货，待加工
                                    directStatus = "待加工";
                                    break;
                                case "6":  //加工中
                                    directStatus = "加工中";
                                    break;
                                case "7":  //加工完成，待配送
                                    directStatus = "待配送";
                                    break;
                                case "8":  // 已发起配送，配送中
                                    directStatus = "配送中";
                                    mLayoutDirectHide5.setVisibility(View.VISIBLE);
                                    mBtnSelectionDirectSignFor.setText("签收");
                                    break;
                                case "9":  //已签收
                                    directStatus = "已签收";
                                    break;
                                default:
                                    directStatus = "";
                            }
                        } else {
                            mLLCardLayoutDirect.setVisibility(View.GONE);
                        }
                        if (beanInfo.getCut_status() != null && !beanInfo.getCut_status().equals("0")) {
                            mLLCardLayoutProcess.setVisibility(View.VISIBLE);
                            switch (beanInfo.getCut_status()) {
                                case "1":  //初始加载客户确认订单，待受理
                                    processStatus = "待受理";
                                    break;
                                case "2":  // 供应链采购中，备货中
                                    processStatus = "备货中";
                                    break;
                                case "3":  // 供应链备货完成，待配送
                                    processStatus = "待配送";
                                    break;
                                case "4":  // 已到货，待加工
                                    processStatus = "待加工";
                                    break;
                                case "6":  //加工中
                                    processStatus = "加工中";
                                    break;
                                case "7":  //加工完成，待配送
                                    processStatus = "待配送";
                                    mLayoutProcessHide7.setVisibility(View.VISIBLE);
                                    mBtnSelectionProcessSignFor.setText("发起配送");
                                    break;
                                case "8":  // 已发起配送，配送中
                                    processStatus = "配送中";
                                    mLayoutProcessHide7.setVisibility(View.VISIBLE);
                                    mBtnSelectionProcessSignFor.setText("签收");
                                    break;
                                case "9":  //已签收
                                    processStatus = "已签收";
                                    break;
                                default:
                                    processStatus = "";
                            }
                        } else {
                            mLLCardLayoutProcess.setVisibility(View.GONE);
                        }

                        space_id=beanInfo.getSpace_id();

                        //直接配送
                        mTvSelectionNum.setText(String.valueOf(totalAmount - cutAmount));
                        mTvSelectionNumUnit.setText(beanInfo.getUnit());
                        mTvSelectionStatusDirect.setText(directStatus);
                        if (beanInfo.getHear_time() != null) {
                            mLayoutDirectHide0.setVisibility(View.VISIBLE);
                            mTvSelectionHearTime.setText(beanInfo.getHear_time());
                        } else {
                            mLayoutDirectHide0.setVisibility(View.GONE);
                        }

                        if (beanInfo.getPrepare_time() != null) {
                            mLayoutDirectHide1.setVisibility(View.VISIBLE);
                            mTvSelectionPrepareTime.setText(beanInfo.getPrepare_time());
                        } else {
                            mLayoutDirectHide1.setVisibility(View.GONE);
                        }

                        if (beanInfo.getStart_deliver_time() != null) {
                            mLayoutDirectHide2.setVisibility(View.VISIBLE);
                            mTvSelectionDeliverTime.setText(beanInfo.getStart_deliver_time());
                        } else {
                            mLayoutDirectHide2.setVisibility(View.GONE);
                        }

                        if (beanInfo.getDeliver_time() != null) {
                            mLayoutDirectHide3.setVisibility(View.VISIBLE);
                            mTvSelectionDeliverStartTime.setText(beanInfo.getDeliver_time());
                        } else {
                            mLayoutDirectHide3.setVisibility(View.GONE);
                        }

                        if (beanInfo.getSign_time() != null) {
                            mLayoutDirectHide4.setVisibility(View.VISIBLE);
                            mTvSelectionArriveTime.setText(beanInfo.getSign_time());
                        } else {
                            mLayoutDirectHide4.setVisibility(View.GONE);
                        }

                        //加工时间
                        mTvSelectionNum2.setText(String.valueOf(cutAmount));
                        mTvSelectionNum2Unit.setText(beanInfo.getUnit());
                        mTvSelectionStatusProcess.setText(processStatus);
                        if (beanInfo.getCut_hear_time() != null) {
                            mLayoutProcessHide0.setVisibility(View.VISIBLE);
                            mTvSelectionHearTime2.setText(beanInfo.getCut_hear_time());
                        } else {
                            mLayoutProcessHide0.setVisibility(View.GONE);
                        }

                        if (beanInfo.getCut_prepare_time() != null) {
                            mLayoutProcessHide1.setVisibility(View.VISIBLE);
                            mTvSelectionPrepareTime2.setText(beanInfo.getCut_prepare_time());
                        } else {
                            mLayoutProcessHide1.setVisibility(View.GONE);
                        }

                        if (beanInfo.getCut_reworking_time() != null) {
                            mLayoutProcessHide2.setVisibility(View.VISIBLE);
                            mTvSelectionProcessTime2.setText(beanInfo.getCut_reworking_time());
                        } else {
                            mLayoutProcessHide2.setVisibility(View.GONE);
                        }
                        if (beanInfo.getCut_reworked_time() != null) {
                            mLayoutProcessHide3.setVisibility(View.VISIBLE);
                            mTvSelectionProcessCompleteTime2.setText(beanInfo.getCut_reworked_time());
                        } else {
                            mLayoutProcessHide3.setVisibility(View.GONE);
                        }
                        if (beanInfo.getCut_start_deliver_time() != null) {
                            mLayoutProcessHide4.setVisibility(View.VISIBLE);
                            mTvSelectionDeliverTime2.setText(beanInfo.getCut_start_deliver_time());
                        } else {
                            mLayoutProcessHide4.setVisibility(View.GONE);
                        }
                        if (beanInfo.getCut_deliver_time() != null) {
                            mLayoutProcessHide5.setVisibility(View.VISIBLE);
                            mTvSelectionDeliverTimeStart2.setText(beanInfo.getCut_deliver_time());
                        } else {
                            mLayoutProcessHide5.setVisibility(View.GONE);
                        }
                        if (beanInfo.getCut_sign_time() != null) {
                            mLayoutProcessHide6.setVisibility(View.VISIBLE);
                            mTvSelectionArriveTime2.setText(beanInfo.getCut_sign_time());
                        } else {
                            mLayoutProcessHide6.setVisibility(View.GONE);
                        }


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

        mOnSuccessSignFor = new SubscriberOnSuccessListener<HttpResult<EmptyEntity>>() {
            @Override
            public void onSuccess(HttpResult<EmptyEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("货物签收状态已提交！")
                                .setConfirmText("知道了")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        refreshData();
                                        sweetAlertDialog.dismissWithAnimation();
                                        if (mPopupWindow.isShowing()) {
                                            mPopupWindow.dismiss();
                                        }
                                    }
                                })
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

        mOnSuccessDeliver = new SubscriberOnSuccessListener<HttpResult<EmptyEntity>>() {
            @Override
            public void onSuccess(HttpResult<EmptyEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("已成功发起配送！")
                                .setConfirmText("知道了")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        refreshData();
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                })
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
        mToolbarTitle.setText("选品单");

    }

    private void refreshData() {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().getMaterialDeliverDetail(new ProgressSubscriber(mOnSuccessMainMaterial, _mActivity), user_code, access_token, mOrder_code, mMaterial_code, mSpace_id);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * -------------------------------------------------------------------------------------
     */


    private ArrayList<String> mPhotoPaths;
    ArrayList<String> selectedPhotos = new ArrayList<>();

    enum RequestCode {
        //        Button(R.id.iv_popup_pic_upload),
//        ButtonNoCamera(R.id.button_no_camera),
        ButtonOnePhoto(R.id.iv_popup_pic_upload);

        @IdRes
        final int mViewId;

        RequestCode(@IdRes int viewId) {
            mViewId = viewId;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            //List<String> photos = null;
            mPhotoPaths = null;
            if (data != null) {
                mPhotoPaths = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (mPhotoPaths != null) {
                selectedPhotos.addAll(mPhotoPaths);
            }

            mIvPopupPicUpload.setVisibility(View.GONE);
            mLlPopupPic.setVisibility(View.VISIBLE);
            /**************************************************************/
            Uri uri = Uri.fromFile(new File(selectedPhotos.get(0)));
            Glide.with(_mActivity)
                    .load(uri)
                    .centerCrop()
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.ic_camera)
                    .error(me.iwf.photopicker.R.drawable.__picker_ic_broken_image_black_48dp)
                    .into(mIvPopupPic);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // permission was granted, yay!
            onClick(RequestCode.values()[requestCode].mViewId);

        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(_mActivity, "No read storage permission! Cannot perform the action.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.CAMERA:
                // No need to explain to user as it is obvious
                return false;
            default:
                return true;
        }
    }

    private void checkPermission(@NonNull RequestCode requestCode) {

        int readStoragePermissionState = ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermissionState = ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.CAMERA);

        boolean readStoragePermissionGranted = readStoragePermissionState != PackageManager.PERMISSION_GRANTED;
        boolean cameraPermissionGranted = cameraPermissionState != PackageManager.PERMISSION_GRANTED;

        if (readStoragePermissionGranted || cameraPermissionGranted) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(_mActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(_mActivity,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                String[] permissions;
                if (readStoragePermissionGranted && cameraPermissionGranted) {
                    permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                } else {
                    permissions = new String[]{
                            readStoragePermissionGranted ? Manifest.permission.READ_EXTERNAL_STORAGE
                                    : Manifest.permission.CAMERA
                    };
                }
                ActivityCompat.requestPermissions(_mActivity,
                        permissions,
                        requestCode.ordinal());
            }

        } else {
            // Permission granted
            onClick(requestCode.mViewId);
        }

    }

    public static final int RESULT_LOCAL = 233;

    private void onClick(@IdRes int viewId) {

        switch (viewId) {

            case R.id.iv_popup_pic_upload: {
                //Intent intent = new Intent(MainActivity.this, PhotoPickerActivity.class);
                //PhotoPickerIntent.setPhotoCount(intent, 1);
                //PhotoPickerIntent.setShowCamera(intent, true);
                //startActivityForResult(intent, REQUEST_CODE);

//                PhotoPicker.builder()
//                        .setPhotoCount(1)
//                        .setGridColumnCount(3)
//                        .start(_mActivity, DetailSelectionList_Auxilary.this);

                Bundle mPickerOptionsBundle = new Bundle();
                ;
                Intent mPickerIntent = new Intent();
                mPickerIntent.setClass(_mActivity, PhotoPickerActivity.class);
                mPickerOptionsBundle.putInt("MAX_COUNT", 1);
                mPickerOptionsBundle.putInt("column", 3);
                mPickerIntent.putExtras(mPickerOptionsBundle);

                startActivityForResult(mPickerIntent, RESULT_LOCAL);
                break;
            }


        }
    }


    private class ProgressUpCallBack<T> extends AbsCallback<T> {
        private Class<T> clazz;
        private Type type;

        public ProgressUpCallBack(Activity activity, Class<T> clazz) {
            this.clazz = clazz;
        }

        public ProgressUpCallBack(Type type) {
            this.type = type;
        }

        @Override
        public void onBefore(BaseRequest request) {
            super.onBefore(request);
            mTvProgress.setText("正在上传中...");
        }

        @Override
        public void onResponse(boolean isFromCache, T s, Request request, Response response) {
            //handleResponse(isFromCache, s, request, response);
            mTvProgress.setText("上传完成");
            UploadImgResult info = (UploadImgResult) s;
            mSignForPicturePath = info.getPicnamefile();

            String fullNum = mEtPopupFullNum.getText().toString().trim();
            if (!mSignForPicturePath.equals("")) {
                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                HttpMethods.getInstance().signForMainMaterial(new ProgressSubscriber(mOnSuccessSignFor, _mActivity), user_code, access_token, mOrder_code, mMaterial_code, space_id,material_type, fullNum, mSignForPicturePath);
            }
        }

        @Override
        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
            //handleError(isFromCache, call, response);
            mTvProgress.setText("上传出错");
        }

        @Override
        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            System.out.println("upProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);

            String downloadLength = Formatter.formatFileSize(_mActivity, currentSize);
            String totalLength = Formatter.formatFileSize(_mActivity, totalSize);
            mDownloadSize.setText(downloadLength + "/" + totalLength);
//            String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);
//            tvNetSpeed.setText(netSpeed + "/S");
//            tvProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
            mPbProgress.setMax(100);
            mPbProgress.setProgress((int) (progress * 100));
        }

        //该方法是子线程处理，不能做ui相关的工作
        @Override
        public T parseNetworkResponse(Response response) throws Exception {
            String responseData = response.body().string();
            if (TextUtils.isEmpty(responseData)) return null;

            /**
             * 一般来说，服务器返回的响应码都包含 code，msg，data 三部分，在此根据自己的业务需要完成相应的逻辑判断
             * 以下只是一个示例，具体业务具体实现
             */
            JSONObject jsonObject = new JSONObject(responseData);
            final String msg = jsonObject.optString("msg", "");
            final int code = jsonObject.optInt("code", 0);
            String data = jsonObject.optString("info", "");
            switch (code) {
                case 200:
                    /**
                     * code = 200 代表成功，默认实现了Gson解析成相应的实体Bean返回，可以自己替换成fastjson等
                     * 对于返回参数，先支持 String，然后优先支持class类型的字节码，最后支持type类型的参数
                     */
                    if (clazz == String.class) return (T) data;
                    if (clazz != null) return new Gson().fromJson(data, clazz);
                    if (type != null) return new Gson().fromJson(data, type);
                    break;

                case 3017:
                    //比如：用户收取信息已过期，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                    throw new IllegalStateException("操作失败");
                case 3020:
                    //比如：用户账户被禁用，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                    throw new IllegalStateException("参数不完整");
                default:
                    throw new IllegalStateException("错误代码：" + code + "，错误信息：" + msg);
            }
            //如果要更新UI，需要使用handler，可以如下方式实现，也可以自己写handler
            OkHttpUtils.getInstance().getDelivery().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OkHttpUtils.getContext(), "错误代码：" + code + "，错误信息：" + msg, Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
    }


    /**
     * popupWindow控件
     */
    private PopupWindow mPopupWindow;

    private LinearLayout mLlPopupClose;
    private TextView mTvPopupSignNum;
    private EditText mEtPopupFullNum;
    private TextView mTvPopupSignNumUnit;
    private TextView mTvPopupFullNumUnit;
    private ImageView mIvPopupPicUpload;
    private LinearLayout mLlPopupPic;
    private ImageView mIvPopupPic;
    private ImageView mIvPopupPicDelete;
    private TextView mDownloadSize;
    private NumberProgressBar mPbProgress;
    private TextView mTvProgress;
    private Button mBtnPopupDone;


    /**---------------------------PoputWindow--------------------------------*/
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


        mLlPopupClose = (LinearLayout) popView.findViewById(R.id.ll_popup_close);
        mTvPopupSignNum = (TextView) popView.findViewById(R.id.tv_popup_sign_num);
        mEtPopupFullNum = (EditText) popView.findViewById(R.id.et_popup_full_num);
        mTvPopupSignNumUnit = (TextView) popView.findViewById(R.id.tv_popup_sign_num_unit);
        mTvPopupFullNumUnit = (TextView) popView.findViewById(R.id.tv_popup_full_num_unit);
        mIvPopupPicUpload = (ImageView) popView.findViewById(R.id.iv_popup_pic_upload);
        mLlPopupPic = (LinearLayout) popView.findViewById(R.id.ll_popup_pic);
        mIvPopupPic = (ImageView) popView.findViewById(R.id.iv_popup_pic);
        mIvPopupPicDelete = (ImageView) popView.findViewById(R.id.iv_popup_pic_delete);
        mDownloadSize = (TextView) popView.findViewById(R.id.downloadSize);
        mPbProgress = (NumberProgressBar) popView.findViewById(R.id.pbProgress);
        mTvProgress = (TextView) popView.findViewById(R.id.tvProgress);
        mBtnPopupDone = (Button) popView.findViewById(R.id.btn_popup_done);

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
        //_mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

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


    private void showPopupWindow(View view, String signNum, String signNumUnit) {

        // 一个自定义的布局，作为显示的内容
        View popView = LayoutInflater.from(_mActivity).inflate(R.layout.popup_material_sign_for, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //初始化
        initPopWindow(popView, mPopupWindow);

        mTvPopupSignNum.setText(signNum);
        mTvPopupSignNumUnit.setText(signNumUnit);
        mTvPopupFullNumUnit.setText(signNumUnit);
        // 设置按钮的点击事件
        mLlPopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        mIvPopupPicUpload.setVisibility(View.VISIBLE);
        mLlPopupPic.setVisibility(View.GONE);

        mIvPopupPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(RequestCode.ButtonOnePhoto);
            }
        });
        mIvPopupPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPreview.builder()
                        .setPhotos(selectedPhotos)
                        .start(_mActivity, DetailSelectionList_Main.this);
            }
        });
        mIvPopupPicDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvPopupPicUpload.setVisibility(View.VISIBLE);
                mLlPopupPic.setVisibility(View.GONE);
            }
        });


        mBtnPopupDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strNum = mEtPopupFullNum.getText().toString();
                if (TextUtils.isEmpty(strNum.trim())) {
                    Toast.makeText(_mActivity, "请输入签收完整数量！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mIvPopupPicUpload.getVisibility() == View.VISIBLE) {
                    Toast.makeText(_mActivity, "请选择要上传的签收单！", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<File> files = new ArrayList<>();
                if (mPhotoPaths != null && mPhotoPaths.size() > 0) {
                    for (int i = 0; i < mPhotoPaths.size(); i++) {
                        files.add(new File(mPhotoPaths.get(i)));
                    }
                }

                String url = HttpMethods.BASE_URL + "App/Public/upload_imgs";
                //拼接参数
                OkHttpUtils.post(url)//
                        .tag(this)//
                        .headers("header1", "headerValue1")//
                        .headers("header2", "headerValue2")//
                        .params("param1", "paramValue1")//
                        .params("param2", "paramValue2")//
//                .params("file1",new File("文件路径"))   //这种方式为一个key，对应一个文件
//                .params("file2",new File("文件路径"))
//                .params("file3",new File("文件路径"))
                        .addFileParams("file", files)           // 这种方式为同一个key，上传多个文件
                        .execute(new ProgressUpCallBack<>(_mActivity, UploadImgResult.class));


            }
        });


        /** 禁止点击外部区域取消popup windows*/
        LinearLayout layouttemp = (LinearLayout) popView
                .findViewById(R.id.popup_material_sign_for);
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
