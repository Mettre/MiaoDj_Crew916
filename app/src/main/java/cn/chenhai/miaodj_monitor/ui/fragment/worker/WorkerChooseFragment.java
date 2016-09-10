package cn.chenhai.miaodj_monitor.ui.fragment.worker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import cn.chenhai.miaodj_monitor.ui.view_custom.DropDownMenu.DropDownMenu;
import cn.chenhai.miaodj_monitor.ui.view_custom.DropDownMenu.ListDropDownAdapter;
import cn.chenhai.miaodj_monitor.ui.view_custom.DropDownMenu.DefaultDropDownAdapter;
import cn.chenhai.miaodj_monitor.ui.view_custom.DropDownMenu.StarsDropAdapter;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.adapter.WorkerChooseAdapter;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.ChooseWorkerEntity;
import cn.chenhai.miaodj_monitor.model.entity.WorkerTypesEntity;
import cn.chenhai.miaodj_monitor.model.info.WorkerChooseInfo;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.utils.CLog;
import cn.chenhai.miaodj_monitor.ui.view_custom.SearchViewCut;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by ChenHai--霜华 on 2016/6/20. 09:45
 * 邮箱：248866527@qq.com
 */
public class WorkerChooseFragment extends BaseBackFragment_Swip implements SearchViewCut.SearchViewListener {
    private static final String ARG_ITEM = "arg_item";

    private String mProjectCode;
    private String mWorkerType;

    private SubscriberOnSuccessListener mOnSuccessInit;
    private SubscriberOnSuccessListener mOnSuccessWorkerTypes;
    private SubscriberOnSuccessListener mOnSuccessChooseWorker;
    private SubscriberOnSuccessListener mOnSuccessChooseWorkerFactory;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private PtrClassicFrameLayout mRefreshPtrFrameLayout;
    private RecyclerView mRecycler;
    private Button mWorkerBtnChooseFactory;

    private LinearLayoutManager mLLmanager;
    private WorkerChooseAdapter mAdapter;

    private View mContentView;
    /**************
     * 下拉菜单
     *************/
    private DropDownMenu mDropDownMenu;
    private String headers[] = {"工种(全部)", "工龄(全部)", "星级(全部)"};
    private List<View> popupViews = new ArrayList<>();

    private ListDropDownAdapter typeAdapter;
    private DefaultDropDownAdapter ageAdapter;
    //private DefaultDropDownAdapter sexAdapter;
    //private BtnDropDownAdapter gradeAdapter;
    private StarsDropAdapter starAdapter;

    //    private String types[] = {"不限", "放线员", "拆墙工", "普瓦", "辅工", "木工", "油漆工", "水电工", "专业防水", "质检员", "专业配送", "瓦工",
//            "木门安装工", "保洁工", "地板安装工", "成保员", "集成吊顶安装工","淋浴房安装工", "橱柜安装工", "浴室柜安装工", "台面安装工", "精修工"};
    private String types[] = {"不限"};
    private String ages[] = {"不限", "1年以下", "1年-3年", "3年-5年", "5年-10年", "10年-20年", "20年以上"};
    //private String constellations[] = {"不限", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座"};
    private String stars[] = {"不限", "0", "1", "2", "3", "4", "5"};

    private List<WorkerTypesEntity.WorktypeBean> mTypeList = new ArrayList<>();
    private String mDropDownType = "";
    private String mDropDownAges = "";
    private String mDropDownStars = "";

    //private int constellationPosition = 0;
    private int starPosition = 0;
    /*********************************************/
    /**
     * 搜索view
     */
    private SearchViewCut searchView;

    public static WorkerChooseFragment newInstance(String projectCode, String workerType) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        args.putString("choose_workerType", workerType);
        WorkerChooseFragment fragment = new WorkerChooseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectCode = getArguments().getString(ARG_ITEM);
        mWorkerType = getArguments().getString("choose_workerType");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worker_choose, container, false);
        mContentView = inflater.inflate(R.layout.fragment_worker_choose_content, container, false);
        ButterKnife.bind(_mActivity);

        initView(view);
        initData();
        //initDataTemp();
        initSearchView();
        initPullRefresh();
        //return view;
        return attachToSwipeBack(view);
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);

        initDropDownMenu(view);

        mWorkerBtnChooseFactory = (Button) view.findViewById(R.id.worker_btn_chooseFactory);

        searchView = (SearchViewCut) mContentView.findViewById(R.id.sv_search_layout);
        mRefreshPtrFrameLayout = (PtrClassicFrameLayout) mContentView.findViewById(R.id.refresh_ptrFrameLayout);
        mRecycler = (RecyclerView) mContentView.findViewById(R.id.recycler);


        mAdapter = new WorkerChooseAdapter(_mActivity);
        mLLmanager = new LinearLayoutManager(_mActivity);
        mRecycler.setLayoutManager(mLLmanager);
        mRecycler.setAdapter(mAdapter);

        mWorkerBtnChooseFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mWorkerType.equals("108")) {
                    new SweetAlertDialog(_mActivity, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("错误")
                            .setContentText("质检员不能选择驻场工人！")
                            .setConfirmText("关闭")
                            .show();
                } else {
                    String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                    String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                    HttpMethods.getInstance().chooseProjectWorkerFactory(new ProgressSubscriber(mOnSuccessChooseWorkerFactory, _mActivity), user_code, access_token, mProjectCode,
                            mDropDownType);//mWorkerType
                }
            }
        });

//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View view) {
//
//            }
//        });
        mAdapter.setOnItemBtnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                HttpMethods.getInstance().chooseProjectWorker(new ProgressSubscriber(mOnSuccessChooseWorker, _mActivity), user_code, access_token, mProjectCode, mAdapter.getItem(position).getWorkerCode(), mDropDownType);//mWorkerType
            }
        });


        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<ChooseWorkerEntity>>() {
            @Override
            public void onSuccess(HttpResult<ChooseWorkerEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    List<ChooseWorkerEntity.WorkerBean> projects = result.getInfo().getWorker();
                    List<WorkerChooseInfo> list = new ArrayList<>();
                    for (int i = 0; i < projects.size(); i++) {
                        WorkerChooseInfo pageInfo = new WorkerChooseInfo();
                        ChooseWorkerEntity.WorkerBean nodeInfo = projects.get(i);

                        String types = "(" + nodeInfo.getTitle() + ")";
                        pageInfo.setWorkerAllTypes(types);
                        String headPath = "";
                        if (nodeInfo.getHeadimg() != null) {
                            headPath = HttpMethods.BASE_ROOT_URL + nodeInfo.getHeadimg();
                        }
                        pageInfo.setImg_portraitPath(headPath);
                        pageInfo.setWorkerName(nodeInfo.getReal_name());
                        pageInfo.setWorkerCode(nodeInfo.getWorker_code());
                        pageInfo.setWorkerStars(nodeInfo.getScore());
                        String workerAge = nodeInfo.getWorker_age();
                        workerAge = workerAge.replace("年", "");
                        pageInfo.setWorkerYear(workerAge);

                        list.add(pageInfo);
                    }

                    mAdapter.refreshDatas(list);

                    mAdapter.notifyDataSetChanged();
                    mRefreshPtrFrameLayout.refreshComplete();
                }
            }

            @Override
            public void onCompleted() {
                mRefreshPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onError() {
                mRefreshPtrFrameLayout.refreshComplete();
            }
        };

        mOnSuccessWorkerTypes = new SubscriberOnSuccessListener<HttpResult<WorkerTypesEntity>>() {
            @Override
            public void onSuccess(HttpResult<WorkerTypesEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    mTypeList = result.getInfo().getWorktype();
                    List<String> list = new ArrayList<>();
                    list.add("不限");
                    for (int i = 0; i < mTypeList.size(); i++) {
                        String type;
                        WorkerTypesEntity.WorktypeBean nodeInfo = mTypeList.get(i);
                        type = nodeInfo.getTitle();


                        list.add(type);
                    }

                    typeAdapter.refreshDatas(list);
                    typeAdapter.notifyDataSetChanged();


                    //出于流程考虑，暂时禁止点击，防止选错工种，工种由外部传入
                    int temPosition = 0;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).equals(mWorkerType)) {
                            temPosition = i;
                        }
                    }

                    typeAdapter.setCheckItem(temPosition);
                    types = (String[]) list.toArray(new String[list.size()]);
                    mDropDownMenu.setTabText(temPosition == 0 ? headers[0] : types[temPosition]);
                    mDropDownMenu.setTabText(types[temPosition]);

                    if (mTypeList.size() != 0 && temPosition != 0) {
                        mDropDownType = mTypeList.get(temPosition - 1).getId();
                    } else {
                        mDropDownType = "";
                    }
                    //mDropDownMenu.getChildAt(0).setEnabled(false);
                    //////////////////////////////////////////////////////////////////

                    mRefreshPtrFrameLayout.refreshComplete();

                    refreshData();
                }
            }

            @Override
            public void onCompleted() {
                mRefreshPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onError() {
                mRefreshPtrFrameLayout.refreshComplete();
            }
        };

        mOnSuccessChooseWorker = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("提示")
                            .setContentText("选择已提交！")
                            .setConfirmText("关闭")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString("result", "已选择");
                                    setFramgentResult(RESULT_OK, bundle);
                                    pop();

                                    sweetAlertDialog.dismiss();
                                }
                            })
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
        mOnSuccessChooseWorkerFactory = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("提示")
                            .setContentText("选择已提交！")
                            .setConfirmText("关闭")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {

                                    Bundle bundle = new Bundle();
                                    bundle.putString("result", "已选择");
                                    setFramgentResult(RESULT_OK, bundle);
                                    pop();

                                    sweetAlertDialog.dismiss();
                                }
                            })
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


        //下拉工种列表
        HttpMethods.getInstance().getAllWorkerTypes(new ProgressSubscriber(mOnSuccessWorkerTypes, _mActivity));

    }

    private void refreshData() {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().doSearchWorkers(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token, mProjectCode, mDropDownType,
                mDropDownAges, mDropDownStars, searchView.getEtInputText());
    }

    private void initDropDownMenu(View view) {
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);


        //init city menu
        final ListView cityView = new ListView(_mActivity);
        cityView.setBackgroundColor(0x88ffffff);
        typeAdapter = new ListDropDownAdapter(_mActivity, Arrays.asList(types));
        cityView.setDividerHeight(0);
        cityView.setAdapter(typeAdapter);

        //init age menu
        final ListView ageView = new ListView(_mActivity);
        ageView.setBackgroundColor(0x88ffffff);
        ageView.setDividerHeight(0);
        ageAdapter = new DefaultDropDownAdapter(_mActivity, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init stars
        final View starView = _mActivity.getLayoutInflater().inflate(R.layout.drop_custom_stars_layout, null);
        ListView ratingStar = ButterKnife.findById(starView, R.id.ratingStars);
        starAdapter = new StarsDropAdapter(_mActivity, Arrays.asList(stars));
        ratingStar.setAdapter(starAdapter);
        TextView ok = ButterKnife.findById(starView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mDropDownMenu.setTabText((starPosition == 0 ? headers[2] : stars[starPosition]) + "星");
                if (starPosition == 0) {
                    mDropDownMenu.setTabText((headers[2]));
                } else {
                    mDropDownMenu.setTabText(stars[starPosition] + "星");
                }
                mDropDownMenu.closeMenu();
            }
        });
//        //init constellation
//        final View constellationView = _mActivity.getLayoutInflater().inflate(R.layout.drop_custom_layout, null);
//        GridView constellation = ButterKnife.findById(constellationView, R.id.constellation);
//        gradeAdapter = new BtnDropDownAdapter(_mActivity, Arrays.asList(constellations));
//        constellation.setAdapter(gradeAdapter);
//        TextView ok = ButterKnife.findById(constellationView, R.id.ok);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[2] : constellations[constellationPosition]);
//                mDropDownMenu.closeMenu();
//            }
//        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(starView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                typeAdapter.setCheckItem(position);

                if (mTypeList.size() != 0 && position != 0) {
                    mDropDownType = mTypeList.get(position - 1).getId();
                } else {
                    mDropDownType = "";
                }

                mDropDownMenu.setTabText(position == 0 ? headers[0] : types[position]);
                mDropDownMenu.closeMenu();
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);

                if (position != 0) {
                    mDropDownAges = String.valueOf(position);
                } else {
                    mDropDownAges = "";
                }

                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
            }
        });


        ratingStar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                starAdapter.setCheckItem(position);
                starPosition = position;

                if (position != 0) {
                    mDropDownStars = String.valueOf(position - 1);
                } else {
                    mDropDownStars = "";
                }
            }
        });

        //tabs 所有标题，popupViews  所有菜单，contentView 内容
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mContentView);
    }

    @Override
    public boolean onBackPressedSupport() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
            return true;
        } else {
            super.onBackPressedSupport();
            return false;
        }
    }

    private void initData() {
        mToolbarTitle.setText("选择工人");
    }

    private void initDataTemp() {
        List<WorkerChooseInfo> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            WorkerChooseInfo workerInfo = new WorkerChooseInfo();

            if (i % 4 == 0) {
                workerInfo.setImg_portraitPath("");
                workerInfo.setWorkerName("张丽丽");
                workerInfo.setWorkerAllTypes("(木工、瓦工、油漆工)");
                workerInfo.setWorkerYear("2");
                workerInfo.setWorkerStars("4.5");

            } else if (i % 4 == 1) {
                workerInfo.setImg_portraitPath("http://h.hiphotos.baidu.com/zhidao/pic/item/7c1ed21b0ef41bd5da8c805250da81cb38db3dbc.jpg");
                workerInfo.setWorkerName("李丽丽");
                workerInfo.setWorkerAllTypes("(瓦工、油漆工)");
                workerInfo.setWorkerYear("3");
                workerInfo.setWorkerStars("3");

            } else if (i % 4 == 2) {
                workerInfo.setImg_portraitPath("http://img3.duitang.com/uploads/item/201501/28/20150128194217_mYSVJ.jpeg");
                workerInfo.setWorkerName("王丽丽");
                workerInfo.setWorkerAllTypes("(水电工)");
                workerInfo.setWorkerYear("1");
                workerInfo.setWorkerStars("2");

            } else if (i % 4 == 3) {
                workerInfo.setImg_portraitPath("http://img2.imgtn.bdimg.com/it/u=375192498,2173854692&fm=21&gp=0.jpg");
                workerInfo.setWorkerName("崇丽丽");
                workerInfo.setWorkerAllTypes("(水电工、木工、瓦工、油漆工)");
                workerInfo.setWorkerYear("5");
                workerInfo.setWorkerStars("5");

            }
            list.add(workerInfo);
        }
        mAdapter.setDatas(list);
    }

    private void initPullRefresh() {
        //view.setBackgroundColor(getResources().getColor(R.color.gray));
        //mRefreshPtrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.rotate_header_refresh);

        mRefreshPtrFrameLayout.setLastUpdateTimeRelateObject(this);

        mRefreshPtrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        updateData();
                    }
                }, 1500);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 200);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, mRecycler, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //return super.checkCanDoRefresh(frame, mCardView, header);
                return checkCanDoRefreshLocal();
            }
        });


        // the following are default settings
        mRefreshPtrFrameLayout.setResistance(1.7f); // you can also set foot and header separately
        mRefreshPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mRefreshPtrFrameLayout.setDurationToClose(1000);  // you can also set foot and header separately
        // default is false
        mRefreshPtrFrameLayout.setPullToRefresh(false);

        // default is true
        mRefreshPtrFrameLayout.setKeepHeaderWhenRefresh(true);
        mRefreshPtrFrameLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                // mPtrFrame.autoRefresh();
            }
        }, 100);
    }

    public boolean checkCanDoRefreshLocal() {
        if (mAdapter.getItemCount() == 0 || mRecycler == null) {
            return true;
        }
        //LinearLayoutManager llmanager = (LinearLayoutManager) mRecy.getLayoutManager();
        CLog.d("test", "checkCanDoRefresh: %s %s", mLLmanager.findFirstVisibleItemPosition(), mRecycler.getChildAt(0).getTop());
        int a = mLLmanager.findFirstVisibleItemPosition();
        int b = mRecycler.getChildAt(0).getTop();
        boolean ifTrue = a == 0 && b == 0;
        return ifTrue;
    }

    protected void updateData() {
        mRefreshPtrFrameLayout.refreshComplete();
    }

    /**
     * 初始化视图
     */
    private void initSearchView() {
        //设置监听
        searchView.setSearchViewListener(this);
    }

    /**
     * 点击搜索键时edit text触发的回调
     *
     * @param text
     */
    @Override
    public void onSearch(String text) {

        refreshData();

        //第一次获取结果 还未配置适配器
        if (mRecycler.getAdapter() == null) {
            //获取搜索数据 设置适配器
            LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
            mRecycler.setLayoutManager(manager);
            mRecycler.setAdapter(mAdapter);
        } else {
            //更新搜索数据
            mAdapter.notifyDataSetChanged();
        }
        //Toast.makeText(_mActivity, "搜索完成", Toast.LENGTH_SHORT).show();


        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) _mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    private void scrollToTop() {
        mRecycler.smoothScrollToPosition(0);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
