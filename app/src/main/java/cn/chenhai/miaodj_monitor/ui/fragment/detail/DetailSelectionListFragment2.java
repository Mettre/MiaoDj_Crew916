package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.EmptyEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListEntity;
import cn.chenhai.miaodj_monitor.model.info.Material_auxiliary_Info;
import cn.chenhai.miaodj_monitor.model.info.Material_main_Info;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.ui.adapter.DetailSelectAuxiliaryMaterialAdapter;
import cn.chenhai.miaodj_monitor.ui.adapter.DetailSelectMainMaterialAdapter;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;
import cn.chenhai.miaodj_monitor.ui.view_custom.ExpandableLayout.Utils;
import cn.chenhai.miaodj_monitor.ui.view_custom.TimeSelectPop;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 选品单列表
 * <p>
 * Created by ChenHai--霜华 on 2016/7/13. 16:18
 * 邮箱：248866527@qq.com
 */
public class DetailSelectionListFragment2 extends BaseBackFragment_Swip {
    private static final String ARG_ITEM = "arg_item";
    private static final String TAG = "FragmentLib";

    private String mProjectCode;
    private String mCustomer_code;

    private String mAuxiOrderCode;
    private boolean ifShowDeliverBtn = false;

    private boolean ifExpand1 = false;
    private boolean ifExpand2 = false;

    private SubscriberOnSuccessListener mOnSuccessInit;
    private SubscriberOnSuccessListener mOnSuccessCheckOK;
    private SubscriberOnSuccessListener mOnSuccessCheckCancel;
    private SubscriberOnSuccessListener mOnSuccessDeliver;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLLmanager1, mLLmanager2;
    private DetailSelectMainMaterialAdapter mMainAdapter;
    private DetailSelectAuxiliaryMaterialAdapter mAuxiAdapter;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mTvSelectionTitle1;
    private TextView mTvSelectionDate1;
    private TextView mTvSelectionTitle2;
    private ImageView mIvSelectionWarn;
    private TextView mTvSelectionDate2;
    private TextView mTvSelectionTitle3;
    private TextView mTvSelectionDate3;
    private TextView mTvSelectionTitle4;
    private TextView mTvSelectionDate4;

    private LinearLayout mLlSelectionMainMaterial;
    private TextView mLlSelectionMainMaterialTitle;
    private ImageView mLlSelectionMainMaterialArrow;
    private LinearLayout mSelectionExpandableLayout1;
    private RecyclerView mSelectionRecyclerView1;

    private LinearLayout mLlSelectionAuxiliaryMaterial;
    private TextView mLlSelectionAuxiliaryMaterialTitle;
    private TextView mLlSelectionAuxiliaryMaterialStatus;
    private ImageView mLlSelectionAuxiliaryMaterialArrow;
    private LinearLayout mSelectionExpandableLayout2;
    private RecyclerView mSelectionRecyclerView2;

    private Button mBtnAuxiliaryMaterialOK;
    private Button mBtnAuxiliaryMaterialCancel;
    private TextView mTvBtnDescribe;
    private Button mBtnOKDeliver;

    TimeSelectPop mTimePickPop;

    private List<Material_main_Info> mMainData = new ArrayList<>();


    public static DetailSelectionListFragment2 newInstance(String projectCode, String customer_code) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        args.putString("mCustomer_code", customer_code);
        DetailSelectionListFragment2 fragment = new DetailSelectionListFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProjectCode = getArguments().getString(ARG_ITEM);
            mCustomer_code = getArguments().getString("mCustomer_code");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection_list2, container, false);
        initView(view);
        initData();
        //initDataTemp();
        //return view;
        return attachToSwipeBack(view);
    }

    private void initView(View view) {


        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);


        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mTvSelectionTitle1 = (TextView) view.findViewById(R.id.tv_selection_title_1);
        mTvSelectionDate1 = (TextView) view.findViewById(R.id.tv_selection_date_1);
        mTvSelectionTitle2 = (TextView) view.findViewById(R.id.tv_selection_title_2);
        mIvSelectionWarn = (ImageView) view.findViewById(R.id.iv_selection_warn);
        mTvSelectionDate2 = (TextView) view.findViewById(R.id.tv_selection_date_2);
        mTvSelectionTitle3 = (TextView) view.findViewById(R.id.tv_selection_title_3);
        mTvSelectionDate3 = (TextView) view.findViewById(R.id.tv_selection_date_3);
        mTvSelectionTitle4 = (TextView) view.findViewById(R.id.tv_selection_title_4);
        mTvSelectionDate4 = (TextView) view.findViewById(R.id.tv_selection_date_4);

        mLlSelectionMainMaterial = (LinearLayout) view.findViewById(R.id.ll_selection_mainMaterial);
        mLlSelectionMainMaterialTitle = (TextView) view.findViewById(R.id.ll_selection_mainMaterial_title);
        mLlSelectionMainMaterialArrow = (ImageView) view.findViewById(R.id.ll_selection_mainMaterial_arrow);
        mSelectionExpandableLayout1 = (LinearLayout) view.findViewById(R.id.selection_expandableLayout1);
        mSelectionRecyclerView1 = (RecyclerView) view.findViewById(R.id.selection_recyclerView1);

        mLlSelectionAuxiliaryMaterial = (LinearLayout) view.findViewById(R.id.ll_selection_auxiliaryMaterial);
        mLlSelectionAuxiliaryMaterialTitle = (TextView) view.findViewById(R.id.ll_selection_auxiliaryMaterial_title);
        mLlSelectionAuxiliaryMaterialStatus = (TextView) view.findViewById(R.id.ll_selection_auxiliaryMaterial_status);
        mLlSelectionAuxiliaryMaterialArrow = (ImageView) view.findViewById(R.id.ll_selection_auxiliaryMaterial_arrow);
        mSelectionExpandableLayout2 = (LinearLayout) view.findViewById(R.id.selection_expandableLayout2);
        mSelectionRecyclerView2 = (RecyclerView) view.findViewById(R.id.selection_recyclerView2);

        mBtnAuxiliaryMaterialOK = (Button) view.findViewById(R.id.btn_OK);
        mBtnAuxiliaryMaterialCancel = (Button) view.findViewById(R.id.btn_Cancel);
        mTvBtnDescribe = (TextView) view.findViewById(R.id.tv_btn_describe);
        mBtnOKDeliver = (Button) view.findViewById(R.id.btn_OK_Deliver);


        mLlSelectionMainMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onClickButton(mSelectionExpandableLayout1);
                if (ifExpand1) {
                    mSelectionExpandableLayout1.setVisibility(View.GONE);
                    createRotateAnimator(mLlSelectionMainMaterialArrow, 180f, 0f).start();
                    ifExpand1 = false;
                } else {
                    mSelectionExpandableLayout1.setVisibility(View.VISIBLE);
                    createRotateAnimator(mLlSelectionMainMaterialArrow, 0f, 180f).start();
                    ifExpand1 = true;
                }
            }
        });
        mLlSelectionAuxiliaryMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onClickButton(mSelectionExpandableLayout2);
                if (ifExpand2) {
                    mSelectionExpandableLayout2.setVisibility(View.GONE);
                    createRotateAnimator(mLlSelectionAuxiliaryMaterialArrow, 180f, 0f).start();
                    ifExpand2 = false;
                } else {
                    mSelectionExpandableLayout2.setVisibility(View.VISIBLE);
                    createRotateAnimator(mLlSelectionAuxiliaryMaterialArrow, 0f, 180f).start();
                    ifExpand2 = true;
                }
            }
        });
        /**********************************************************************************/

        mMainAdapter = new DetailSelectMainMaterialAdapter(_mActivity, mMainData);
        mLLmanager1 = new LinearLayoutManager(_mActivity);
        mSelectionRecyclerView1.setLayoutManager(mLLmanager1);
        mSelectionRecyclerView1.setAdapter(mMainAdapter);

        mMainAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                String order_code = mMainAdapter.getItem(position).getOrder_code();
                String material_code = mMainAdapter.getItem(position).getMaterial_code();
                String space_id = mMainAdapter.getItem(position).getSpace_id();
                start(DetailSelectionList_Main.newInstance(order_code, material_code, space_id));
            }
        });

        mAuxiAdapter = new DetailSelectAuxiliaryMaterialAdapter(_mActivity);
        mLLmanager2 = new LinearLayoutManager(_mActivity);
        mSelectionRecyclerView2.setLayoutManager(mLLmanager2);
        mSelectionRecyclerView2.setAdapter(mAuxiAdapter);

        mAuxiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                String deliverCode = mAuxiAdapter.getItem(position).getDeliverCode();
                start(DetailSelectionList_Auxilary.newInstance(deliverCode));

            }
        });
        mAuxiAdapter.setOnCheckBoxClickListener(new DetailSelectAuxiliaryMaterialAdapter.OnCheckBoxClickListener() {
            @Override
            public void onCheckBoxClick(int position, boolean isChecked) {
                int totalCheck = 0;
                for (int i = 0; i < mAuxiAdapter.getItemCount(); i++) {
                    int partCheck = 0;
                    if (mAuxiAdapter.getItem(i).isIfChecked()) {
                        partCheck = 1;
                    }

                    totalCheck += partCheck;
                }

                if (totalCheck == 0) {
                    mBtnOKDeliver.setVisibility(View.GONE);
                } else {
                    mBtnOKDeliver.setVisibility(View.VISIBLE);
                }
            }
        });

        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<SelectionListEntity>>() {
            @Override
            public void onSuccess(HttpResult<SelectionListEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {

                        List<SelectionListEntity.MaterialsBean> beanInfos = result.getInfo().getMaterials();
                        List<Material_main_Info> list = new ArrayList<>();

                        String title = "主材内容（" + beanInfos.size() + "）";
                        mLlSelectionMainMaterialTitle.setText(title);

                        for (int i = 0; i < beanInfos.size(); i++) {
                            SelectionListEntity.MaterialsBean beanInfo = beanInfos.get(i);
                            Material_main_Info materialInfo = new Material_main_Info();

                            if (beanInfo.getImages().size() != 0) {
                                if (beanInfo.getImages().get(0).getPath() != null) {
                                    String path = HttpMethods.BASE_ROOT_URL + beanInfo.getImages().get(0).getPath();
                                    materialInfo.setImg_portraitPath(path);
                                }
                            }
                            materialInfo.setMaterial_name(beanInfo.getCategory_name());
                            materialInfo.setMaterial_name2(beanInfo.getClass_name());
                            materialInfo.setMaterial_name_describe(beanInfo.getTitle());
                            materialInfo.setMaterial_number(beanInfo.getModel());
                            materialInfo.setMaterial_brand(beanInfo.getBrand_name());
                            materialInfo.setMaterial_count(beanInfo.getSpecs());
                            materialInfo.setMaterial_room(beanInfo.getSpace_name());

                            materialInfo.setOrder_code(beanInfo.getOrder_code());
                            materialInfo.setMaterial_code(beanInfo.getCode());
                            materialInfo.setSpace_id(beanInfo.getSpace_id());

                            list.add(materialInfo);
                        }

                        mMainData = list;

                        mMainAdapter.refreshDatas(list);


                        //辅材辅料

                        List<SelectionListEntity.AuxiliaryBean> a_beanInfos = result.getInfo().getAuxiliary();
                        List<Material_auxiliary_Info> list2 = new ArrayList<>();

                        String title2 = "辅材辅料（" + a_beanInfos.size() + "）";
                        mLlSelectionAuxiliaryMaterialTitle.setText(title2);


                        for (int i = 0; i < a_beanInfos.size(); i++) {
                            SelectionListEntity.AuxiliaryBean beanInfo2 = a_beanInfos.get(i);
                            Material_auxiliary_Info auxiliaryInfo = new Material_auxiliary_Info();

//                            if(beanInfo2.getImages().size()!=0){
//                                if(beanInfo2.getImages().get(0).getPath()!=null){
//                                    String path = HttpMethods.BASE_ROOT_URL + beanInfo.getImages().get(0).getPath();
//                                    materialInfo.setImg_portraitPath(path);
//                                }
//                            }
                            if (beanInfo2.getThumb_image() != null && !beanInfo2.getThumb_image().equals("")) {
                                String path = HttpMethods.BASE_ROOT_URL + beanInfo2.getThumb_image();
                                auxiliaryInfo.setImg_portraitPath(path);
                            }
                            auxiliaryInfo.setAuxiliaryNameDes(beanInfo2.getTitle());
                            auxiliaryInfo.setAuxiliaryBrand(beanInfo2.getBrand_name());
                            auxiliaryInfo.setAuxiliarySpecs(beanInfo2.getSpecs());
                            auxiliaryInfo.setAuxiliarySpecs2(beanInfo2.getSpecs2());
                            auxiliaryInfo.setAuxiliaryCount(beanInfo2.getAmount());
                            auxiliaryInfo.setAuxiliaryCountUnit(beanInfo2.getUnit());

                            auxiliaryInfo.setStatus(beanInfo2.getStatus());
                            auxiliaryInfo.setMaterialCode(beanInfo2.getCode());
                            auxiliaryInfo.setDeliverCode(beanInfo2.getDeliver_code());

                            list2.add(auxiliaryInfo);
                        }

                        mAuxiAdapter.refreshDatas(list2);
                        mAuxiAdapter.notifyDataSetChanged();


                        SelectionListEntity.OrderAuxiliaryBean order_auxiliary = result.getInfo().getOrder_auxiliary();
                        String textStatus = "";
                        if (order_auxiliary != null) {
                            switch (order_auxiliary.getAssistant_status()) {
                                case "1":
                                    textStatus = "后台处理中";
                                    mBtnAuxiliaryMaterialOK.setVisibility(View.GONE);
                                    mBtnAuxiliaryMaterialCancel.setVisibility(View.GONE);
                                    mTvBtnDescribe.setVisibility(View.GONE);
                                    mBtnOKDeliver.setVisibility(View.GONE);
                                    break;
                                case "2":
                                    textStatus = "待确认";
                                    mBtnAuxiliaryMaterialOK.setVisibility(View.VISIBLE);
                                    mBtnAuxiliaryMaterialCancel.setVisibility(View.VISIBLE);
                                    mTvBtnDescribe.setVisibility(View.VISIBLE);
                                    mBtnOKDeliver.setVisibility(View.GONE);
                                    break;
                                case "3":
                                    textStatus = "";
                                    mBtnAuxiliaryMaterialOK.setVisibility(View.GONE);
                                    mBtnAuxiliaryMaterialCancel.setVisibility(View.GONE);
                                    mTvBtnDescribe.setVisibility(View.GONE);
                                    mBtnOKDeliver.setVisibility(View.GONE);
                                    break;
                                case "4":
                                    textStatus = "";
                                    mBtnAuxiliaryMaterialOK.setVisibility(View.GONE);
                                    mBtnAuxiliaryMaterialCancel.setVisibility(View.GONE);
                                    mTvBtnDescribe.setVisibility(View.GONE);
                                    mBtnOKDeliver.setVisibility(View.GONE);
                                    break;

                                default:
                                    textStatus = "";
                                    mBtnAuxiliaryMaterialOK.setVisibility(View.GONE);
                                    mBtnAuxiliaryMaterialCancel.setVisibility(View.GONE);
                                    mTvBtnDescribe.setVisibility(View.GONE);
                                    mBtnOKDeliver.setVisibility(View.GONE);
                                    break;
                            }
                        }
                        mLlSelectionAuxiliaryMaterialStatus.setText(textStatus);

                        mAuxiOrderCode = "";
                        if (order_auxiliary != null && order_auxiliary.getCode() != null) {
                            mAuxiOrderCode = order_auxiliary.getCode();
                        }

                        //辅材配送Pop
                        mTimePickPop = new TimeSelectPop(_mActivity, 2, new TimeSelectPop.SubmitOnClickListener() {
                            @Override
                            public void SubmitOnClickListener(String mData) {
                                String material_codes = "";
                                for (int i = 0; i < mAuxiAdapter.getItemCount(); i++) {
                                    if (mAuxiAdapter.getItem(i).isIfChecked()) {
                                        String temCode = mAuxiAdapter.getItem(i).getMaterialCode();

                                        material_codes += (temCode + ",");
                                    }
                                }
                                if (!material_codes.equals("")) {
                                    material_codes = material_codes.substring(0, material_codes.length() - 1);
                                }

                                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                                HttpMethods.getInstance().doStartDeliverAuxilary(new ProgressSubscriber(mOnSuccessDeliver, _mActivity), user_code, access_token, mAuxiOrderCode, material_codes, mData);
                            }
                        });

                        mBtnAuxiliaryMaterialOK.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText("提示")
                                        .setContentText("辅材辅料确定要确认下单吗？")
                                        .setCancelText("取消")
                                        .setConfirmText("确定")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                                                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                                                HttpMethods.getInstance().doConfirmAuxiliary(new ProgressSubscriber(mOnSuccessCheckOK, _mActivity), user_code, access_token, mAuxiOrderCode, "Y", "");

                                                sweetAlertDialog.dismissWithAnimation();
                                            }
                                        })
                                        .show();

                            }
                        });
                        mBtnAuxiliaryMaterialCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DialogCheckNotOk dialog = new DialogCheckNotOk(_mActivity, new DialogCheckNotOk.SubmitDoing() {
                                    @Override
                                    public void submitDoing(String strReason) {
                                        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                                        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                                        HttpMethods.getInstance().doConfirmAuxiliary(new ProgressSubscriber(mOnSuccessCheckCancel, _mActivity), user_code, access_token, mAuxiOrderCode, "N", strReason);
                                    }
                                });
                                dialog.show();
                            }
                        });


                        mBtnOKDeliver.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //showPopupWindow(v);
                                mTimePickPop.show(v);
                            }
                        });
//时间选择器

//                        mSelectionExpandableLayout1.initLayout(false);
//                        mSelectionExpandableLayout2.initLayout(false);

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

        mOnSuccessCheckOK = new SubscriberOnSuccessListener<HttpResult<EmptyEntity>>() {
            @Override
            public void onSuccess(HttpResult<EmptyEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("确认下单已提交！")
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
        mOnSuccessCheckCancel = new SubscriberOnSuccessListener<HttpResult<EmptyEntity>>() {
            @Override
            public void onSuccess(HttpResult<EmptyEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("提示")
                                .setContentText("不确认原因已提交！")
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

    private void refreshData() {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().getSelectionList(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token, mCustomer_code);
    }


    /**
     * 创建 旋转动画！！！！
     */
    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    private void initData() {
        mToolbarTitle.setText("选品单");
    }


}
