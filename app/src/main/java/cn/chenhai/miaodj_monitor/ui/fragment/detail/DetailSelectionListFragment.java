package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.ui.view_custom.ExpandableLayout.ExpandableLayout;
import cn.chenhai.miaodj_monitor.ui.view_custom.ExpandableLayout.ExpandableLayoutListenerAdapter;
import cn.chenhai.miaodj_monitor.ui.view_custom.ExpandableLayout.ExpandableLinearLayout;
import cn.chenhai.miaodj_monitor.ui.view_custom.ExpandableLayout.Utils;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.adapter.DetailSelectAuxiliaryMaterialAdapter;
import cn.chenhai.miaodj_monitor.ui.adapter.DetailSelectMainMaterialAdapter;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListEntity;
import cn.chenhai.miaodj_monitor.model.info.Material_auxiliary_Info;
import cn.chenhai.miaodj_monitor.model.info.Material_main_Info;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;

/**
 * Created by ChenHai--霜华 on 2016/6/27. 10:44
 * 邮箱：248866527@qq.com
 */
public class DetailSelectionListFragment extends BaseBackFragment_Swip {
    private static final String ARG_ITEM = "arg_item";
    private static final String TAG = "FragmentLib";

    private String mProjectCode;
    private String mCustomer_code;


    private SubscriberOnSuccessListener mOnSuccessInit;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLLmanager1 ,mLLmanager2;
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
    private ExpandableLinearLayout mSelectionExpandableLayout1;
    private RecyclerView mSelectionRecyclerView1;

    private LinearLayout mLlSelectionAuxiliaryMaterial;
    private TextView mLlSelectionAuxiliaryMaterialTitle;
    private TextView mLlSelectionAuxiliaryMaterialStatus;
    private ImageView mLlSelectionAuxiliaryMaterialArrow;
    private ExpandableLinearLayout mSelectionExpandableLayout2;
    private RecyclerView mSelectionRecyclerView2;


    //负责存储布尔值的pair
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public static DetailSelectionListFragment newInstance(String projectCode,String customer_code) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        args.putString("mCustomer_code",customer_code);
        DetailSelectionListFragment fragment = new DetailSelectionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            mProjectCode = getArguments().getString(ARG_ITEM);
            mCustomer_code = getArguments().getString("mCustomer_code");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection_list, container, false);
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
        mSelectionExpandableLayout1 = (ExpandableLinearLayout) view.findViewById(R.id.selection_expandableLayout1);
        mSelectionRecyclerView1 = (RecyclerView) view.findViewById(R.id.selection_recyclerView1);

        mLlSelectionAuxiliaryMaterial = (LinearLayout) view.findViewById(R.id.ll_selection_auxiliaryMaterial);
        mLlSelectionAuxiliaryMaterialTitle = (TextView) view.findViewById(R.id.ll_selection_auxiliaryMaterial_title);
        mLlSelectionAuxiliaryMaterialStatus = (TextView) view.findViewById(R.id.ll_selection_auxiliaryMaterial_status);
        mLlSelectionAuxiliaryMaterialArrow = (ImageView) view.findViewById(R.id.ll_selection_auxiliaryMaterial_arrow);
        mSelectionExpandableLayout2 = (ExpandableLinearLayout) view.findViewById(R.id.selection_expandableLayout2);
        mSelectionRecyclerView2 = (RecyclerView) view.findViewById(R.id.selection_recyclerView2);



        expandState.append(1, false);
        expandState.append(2, false);
        /***********************************ExpandableLayout****************************************/
        //mSelectionExpandableLayout1.setInterpolator(Utils.createInterpolator(Utils.BOUNCE_INTERPOLATOR));
        mSelectionExpandableLayout1.setInterpolator(Utils.createInterpolator(Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        mSelectionExpandableLayout1.setExpanded(expandState.get(1));
        mSelectionExpandableLayout1.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(mLlSelectionMainMaterialArrow, 0f, 180f).start();
                expandState.put(1, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(mLlSelectionMainMaterialArrow, 180f, 0f).start();
                expandState.put(1, false);
            }
        });

        mSelectionExpandableLayout2.setInterpolator(Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR));
        //mSelectionExpandableLayout2.setInterpolator(Utils.createInterpolator(Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        mSelectionExpandableLayout2.setExpanded(expandState.get(2));
        mSelectionExpandableLayout2.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(mLlSelectionAuxiliaryMaterialArrow, 0f, 180f).start();
                expandState.put(2, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(mLlSelectionAuxiliaryMaterialArrow, 180f, 0f).start();
                expandState.put(2, false);
            }
        });

        mLlSelectionMainMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton(mSelectionExpandableLayout1);
            }
        });
        mLlSelectionAuxiliaryMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButton(mSelectionExpandableLayout2);
            }
        });
        /**********************************************************************************/

        mMainAdapter = new DetailSelectMainMaterialAdapter(_mActivity);
        mLLmanager1 = new LinearLayoutManager(_mActivity);
        mSelectionRecyclerView1.setLayoutManager(mLLmanager1);
        mSelectionRecyclerView1.setAdapter(mMainAdapter);

        mMainAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                String order_code = mMainAdapter.getItem(position).getOrder_code();
                String material_code = mMainAdapter.getItem(position).getMaterial_code();
                String space_id = mMainAdapter.getItem(position).getSpace_id();
                start(DetailSelectionList_Main.newInstance(order_code,material_code,space_id));
            }
        });

        mAuxiAdapter = new DetailSelectAuxiliaryMaterialAdapter(_mActivity);
        mLLmanager2 = new LinearLayoutManager(_mActivity);
        mSelectionRecyclerView2.setLayoutManager(mLLmanager2);
        mSelectionRecyclerView2.setAdapter(mAuxiAdapter);

        mAuxiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //((MainActivity)getActivity()).getResideLayout().setIfSlide(true);

                //execute the task
                //start(DetailAgreeFragment.newInstance("测试单号111"));
                //start(DetailPointProgressFragment.newInstance(mProjectCode));

            }
        });

        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<SelectionListEntity>>() {
            @Override
            public void onSuccess(HttpResult<SelectionListEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {



                        List<SelectionListEntity.MaterialsBean> beanInfos = result.getInfo().getMaterials();
                        List<Material_main_Info> list = new ArrayList<>();

                        String title = "主材内容（" + beanInfos.size() + "）";
                        mLlSelectionMainMaterialTitle.setText(title);

                        for (int i = 0; i < beanInfos.size(); i++) {
                            SelectionListEntity.MaterialsBean beanInfo = beanInfos.get(i);
                            Material_main_Info materialInfo = new Material_main_Info();

                            if(beanInfo.getImages().size()!=0){
                                if(beanInfo.getImages().get(0).getPath()!=null){
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

                        mMainAdapter.refreshDatas(list);
                        mMainAdapter.notifyDataSetChanged();



                        //服务器暂无相关数据，先设置假数据
                        List<Material_auxiliary_Info> list2 = new ArrayList<>();

                        for (int i = 0; i < 10; i++) {
                            Material_auxiliary_Info materialInfo = new Material_auxiliary_Info();

                            if (i % 4 == 0) {
                                materialInfo.setImg_portraitPath("");
                                //materialInfo.setAuxiliaryNameDes("水泥");
                                materialInfo.setAuxiliaryNameDes("搅拌水泥");
                                materialInfo.setAuxiliaryBrand("富田");
                                materialInfo.setAuxiliarySpecs("直径7.5mm");
                                materialInfo.setAuxiliarySpecs2("??/卷");
                                materialInfo.setAuxiliaryCount("5");
                                materialInfo.setAuxiliaryCountUnit("包");
                                materialInfo.setStatus("1");

                            } else if (i % 4 == 1) {
                                materialInfo.setImg_portraitPath("http://h.hiphotos.baidu.com/zhidao/pic/item/7c1ed21b0ef41bd5da8c805250da81cb38db3dbc.jpg");
                                //materialInfo.setAuxiliaryNameDes("黄沙");
                                materialInfo.setAuxiliaryNameDes("搅拌黄沙");
                                materialInfo.setAuxiliaryBrand("天林");
                                materialInfo.setAuxiliarySpecs("体积7.5m³");
                                materialInfo.setAuxiliarySpecs2("??/立方");
                                materialInfo.setAuxiliaryCount("3");
                                materialInfo.setAuxiliaryCountUnit("包");
                                materialInfo.setStatus("2");

                            } else if (i % 4 == 2) {
                                materialInfo.setImg_portraitPath("http://img3.duitang.com/uploads/item/201501/28/20150128194217_mYSVJ.jpeg");
                                //materialInfo.setAuxiliaryNameDes("电线");
                                materialInfo.setAuxiliaryNameDes("电线");
                                materialInfo.setAuxiliaryBrand("通力");
                                materialInfo.setAuxiliarySpecs("2.5平方毫米电线");
                                materialInfo.setAuxiliarySpecs2("??/卷");
                                materialInfo.setAuxiliaryCount("20");
                                materialInfo.setAuxiliaryCountUnit("米");
                                materialInfo.setStatus("3");

                            } else if (i % 4 == 3) {
                                materialInfo.setImg_portraitPath("http://img2.imgtn.bdimg.com/it/u=375192498,2173854692&fm=21&gp=0.jpg");
                                //materialInfo.setAuxiliaryNameDes("五金");
                                materialInfo.setAuxiliaryNameDes("不锈钢五金");
                                materialInfo.setAuxiliaryBrand("名牌");
                                materialInfo.setAuxiliarySpecs("直径5mm");
                                materialInfo.setAuxiliarySpecs2("??/卷");
                                materialInfo.setAuxiliaryCount("25");
                                materialInfo.setAuxiliaryCountUnit("件");
                                materialInfo.setStatus("4");

                            }
                            list2.add(materialInfo);
                        }
                        mAuxiAdapter.setDatas(list2);
                        mAuxiAdapter.notifyDataSetChanged();

//                        mSelectionExpandableLayout1.initLayout(false);
//                        mSelectionExpandableLayout2.initLayout(false);

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

    private void refreshData(){
        String user_code = PreferencesUtils.getString(_mActivity,"user_code");
        String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
        HttpMethods.getInstance().getSelectionList(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token,mCustomer_code);
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }
    /**创建 旋转动画！！！！*/
    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    private void initData() {
        mToolbarTitle.setText("选品单");
    }

    private void initDataTemp() {
        List<Material_main_Info> list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Material_main_Info materialInfo = new Material_main_Info();

            if (i % 4 == 0) {
                materialInfo.setImg_portraitPath("");
                materialInfo.setMaterial_name("地砖");
                materialInfo.setMaterial_name2("大地砖");
                materialInfo.setMaterial_name_describe("纳福娜");
                materialInfo.setMaterial_number("YG803001");
                materialInfo.setMaterial_brand("东鹏");
                materialInfo.setMaterial_count("800*800");

            } else if (i % 4 == 1) {
                materialInfo.setImg_portraitPath("http://h.hiphotos.baidu.com/zhidao/pic/item/7c1ed21b0ef41bd5da8c805250da81cb38db3dbc.jpg");
                materialInfo.setMaterial_name("地砖");
                materialInfo.setMaterial_name2("厨卫墙地砖");
                materialInfo.setMaterial_name_describe("纳福娜");
                materialInfo.setMaterial_number("YG803001");
                materialInfo.setMaterial_brand("小东鹏");
                materialInfo.setMaterial_count("800*800");

            } else if (i % 4 == 2) {
                materialInfo.setImg_portraitPath("http://img3.duitang.com/uploads/item/201501/28/20150128194217_mYSVJ.jpeg");
                materialInfo.setMaterial_name("地板");
                materialInfo.setMaterial_name2("木地板");
                materialInfo.setMaterial_name_describe("棕色强化");
                materialInfo.setMaterial_number("DW1802");
                materialInfo.setMaterial_brand("大自然");
                materialInfo.setMaterial_count("808*130*12");

            } else if (i % 4 == 3) {
                materialInfo.setImg_portraitPath("http://img2.imgtn.bdimg.com/it/u=375192498,2173854692&fm=21&gp=0.jpg");
                materialInfo.setMaterial_name("实木板");
                materialInfo.setMaterial_name2("面漆指压板");
                materialInfo.setMaterial_name_describe("红橡木");
                materialInfo.setMaterial_number("YG803001");
                materialInfo.setMaterial_brand("天然");
                materialInfo.setMaterial_count("800*800*18");

            }
            list.add(materialInfo);
        }
        mMainAdapter.setDatas(list);


        List<Material_auxiliary_Info> list2 = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Material_auxiliary_Info materialInfo = new Material_auxiliary_Info();

            if (i % 4 == 0) {
                materialInfo.setImg_portraitPath("");
                //materialInfo.setAuxiliaryNameDes("水泥");
                materialInfo.setAuxiliaryNameDes("搅拌水泥");
                materialInfo.setAuxiliaryBrand("富田");
                materialInfo.setAuxiliarySpecs("直径7.5mm");
                materialInfo.setAuxiliarySpecs2("??/卷");
                materialInfo.setAuxiliaryCount("5");
                materialInfo.setAuxiliaryCountUnit("包");
                materialInfo.setStatus("1");

            } else if (i % 4 == 1) {
                materialInfo.setImg_portraitPath("http://h.hiphotos.baidu.com/zhidao/pic/item/7c1ed21b0ef41bd5da8c805250da81cb38db3dbc.jpg");
                //materialInfo.setAuxiliaryNameDes("黄沙");
                materialInfo.setAuxiliaryNameDes("搅拌黄沙");
                materialInfo.setAuxiliaryBrand("天林");
                materialInfo.setAuxiliarySpecs("体积7.5m³");
                materialInfo.setAuxiliarySpecs2("??/立方");
                materialInfo.setAuxiliaryCount("3");
                materialInfo.setAuxiliaryCountUnit("包");
                materialInfo.setStatus("2");

            } else if (i % 4 == 2) {
                materialInfo.setImg_portraitPath("http://img3.duitang.com/uploads/item/201501/28/20150128194217_mYSVJ.jpeg");
                //materialInfo.setAuxiliaryNameDes("电线");
                materialInfo.setAuxiliaryNameDes("电线");
                materialInfo.setAuxiliaryBrand("通力");
                materialInfo.setAuxiliarySpecs("2.5平方毫米电线");
                materialInfo.setAuxiliarySpecs2("??/卷");
                materialInfo.setAuxiliaryCount("20");
                materialInfo.setAuxiliaryCountUnit("米");
                materialInfo.setStatus("3");

            } else if (i % 4 == 3) {
                materialInfo.setImg_portraitPath("http://img2.imgtn.bdimg.com/it/u=375192498,2173854692&fm=21&gp=0.jpg");
                //materialInfo.setAuxiliaryNameDes("五金");
                materialInfo.setAuxiliaryNameDes("不锈钢五金");
                materialInfo.setAuxiliaryBrand("名牌");
                materialInfo.setAuxiliarySpecs("直径5mm");
                materialInfo.setAuxiliarySpecs2("??/卷");
                materialInfo.setAuxiliaryCount("25");
                materialInfo.setAuxiliaryCountUnit("件");
                materialInfo.setStatus("4");
            }
            list2.add(materialInfo);
        }
        mAuxiAdapter.setDatas(list2);
    }
}
