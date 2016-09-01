package cn.chenhai.miaodj_monitor.ui.fragment.home.childpager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.MyProjectsEntity;
import cn.chenhai.miaodj_monitor.model.info.HomePageInfo;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.utils.CLog;
import cn.chenhai.miaodj_monitor.utils.diskCache.ACache;
import cn.chenhai.miaodj_monitor.ui.view_custom.SearchViewCut;
import cn.chenhai.miaodj_monitor.ui.activity.DetailActivity;
import cn.chenhai.miaodj_monitor.ui.activity.MainActivity;
import cn.chenhai.miaodj_monitor.ui.event.TabSelectedEvent;
import cn.chenhai.miaodj_monitor.ui.fragment.home.HomeFragment;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.adapter.HomePagerAdapter;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseFragment;

/**
 * Created by ChenHai--霜华 on 2016/5/28. 3:18
 * 邮箱：248866527@qq.com
 */
public class FirstPagerFragment extends BaseFragment implements SearchViewCut.SearchViewListener {
    private static final String ARG_FROM = "arg_from";
    private static final int REQ_START_DETAIL_FOR_RESULT = 1099;

    private PtrFrameLayout ptrFrameLayout;
    final String[] mStringList = {"MiaoDJ", "Wei Gan Ju", "Complete"};
    private int mFrom = 0;

    private LinearLayout mEmptyViewLayout;

    private RecyclerView mRecy;
    private LinearLayoutManager mLLmanager;
    private HomePagerAdapter mAdapter;

    private int pageSize = 10;
    private int pageIndex = 1;

    private List<HomePageInfo> mdataList = new ArrayList<>();

    /**
     * 搜索结果列表view
     */
    private SearchViewCut searchView;

    private SubscriberOnSuccessListener mOnSuccessListener;
    private boolean ifSaveCache = false;

    public static FirstPagerFragment newInstance(int from) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);

        FirstPagerFragment fragment = new FirstPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pager, container, false);
        EventBus.getDefault().register(this);

        //cacheHelper = MyApplication.getCacheHelper();
        initView(view);

        initSearchView(view);

        initPullRefresh(view);

        return view;
    }


    private void initView(View view) {
        mRecy = (RecyclerView) view.findViewById(R.id.recy);
        mEmptyViewLayout = (LinearLayout) view.findViewById(R.id.empty_view_layout);
        ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.store_house_ptr_frame);

        mAdapter = new HomePagerAdapter(_mActivity, mFrom, mdataList);
        mLLmanager = new LinearLayoutManager(_mActivity);
        mRecy.setLayoutManager(mLLmanager);
        mRecy.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //((MainActivity)getActivity()).getResideLayout().setIfSlide(true);

                //execute the task
                if (getParentFragment() instanceof HomeFragment) {
                    //((HomeFragment) getParentFragment()).start(DetailAgreeFragment.newInstance("测试单号111"));
                    Bundle bundle = new Bundle();
                    bundle.putString("FragmentName", "DetailAgreeFragment");
                    bundle.putString("ProjectCode", mAdapter.getItem(position).getProjectCode());
                    Intent intent = new Intent(_mActivity, DetailActivity.class);
                    intent.putExtras(bundle);
                    ((HomeFragment) getParentFragment()).startActivityForResult(intent, REQ_START_DETAIL_FOR_RESULT);
                }

            }
        });
        mAdapter.setOnItemBtnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {

            }
        });


        mOnSuccessListener = new SubscriberOnSuccessListener<HttpResult<MyProjectsEntity>>() {
            @Override
            public void onSuccess(HttpResult<MyProjectsEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (ifSaveCache) {
                        ACache mCache = ACache.get(_mActivity);
                        mCache.put("FirstPagerFragment", result, 1 * ACache.TIME_DAY);//保存一天，如果超过一天去获取这个key，将为null
                    }
                    List<MyProjectsEntity.ProjectsBean> projects = result.getInfo().getProjects();
                    List<HomePageInfo> list = new ArrayList<>();

                    if (projects.size() == 0) {
                        mEmptyViewLayout.setVisibility(View.VISIBLE);
                        mRecy.setVisibility(View.GONE);
                    } else {
                        mEmptyViewLayout.setVisibility(View.GONE);
                        mRecy.setVisibility(View.VISIBLE);
                    }

                    for (int i = 0; i < projects.size(); i++) {
                        HomePageInfo pageInfo = new HomePageInfo();
                        MyProjectsEntity.ProjectsBean projectInfo = projects.get(i);

                        StringBuilder itemName = new StringBuilder();
                        itemName.append(projectInfo.getStreet());
                        itemName.append(projectInfo.getResidential());
                        if (projectInfo.getApartment() != "") {
                            itemName.append(projectInfo.getApartment());
                            itemName.append("幢");
                        }
                        if (projectInfo.getRoom() != "") {
                            itemName.append(projectInfo.getRoom());
                        }
                        itemName.append("装修项目");
                        pageInfo.setItemName(itemName.toString());
                        pageInfo.setProjectCode(projectInfo.getProject_code());
                        pageInfo.setOwnerName("业主：" + projectInfo.getCustomer_name());
                        String status = "";
                        switch (projectInfo.getProject_status()) {
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
                                status = "施工完成";
                                break;
                            case "90":
                                status = "停工";
                                break;
                        }
                        pageInfo.setWorkProgress(status);
                        pageInfo.setStatus(String.valueOf(mFrom));

                        list.add(pageInfo);
                    }

                    mdataList = list;

                    if (pageIndex == 1) {
                        mAdapter.refreshDatas(mdataList);
                    } else {
                        mAdapter.addDatas(mdataList);
                    }

                    if (result.getInfo().getTotal_page() > pageIndex) {
                        ptrFrameLayout.setMode(PtrFrameLayout.Mode.BOTH);
                    } else {
                        ptrFrameLayout.setMode(PtrFrameLayout.Mode.REFRESH);
                    }

                    pageIndex++;

                    if (ptrFrameLayout != null) ptrFrameLayout.refreshComplete();

                }
            }

            @Override
            public void onCompleted() {
                ptrFrameLayout.refreshComplete();
            }

            @Override
            public void onError() {
                ptrFrameLayout.refreshComplete();
                if (mdataList.size() == 0) {
                    mEmptyViewLayout.setVisibility(View.VISIBLE);
                    mRecy.setVisibility(View.GONE);
                } else {
                    mEmptyViewLayout.setVisibility(View.GONE);
                    mRecy.setVisibility(View.VISIBLE);
                }
            }
        };

        ACache mCache = ACache.get(_mActivity);
        HttpResult<MyProjectsEntity> result = (HttpResult<MyProjectsEntity>) mCache.getAsObject("FirstPagerFragment");

        if (result != null) {
            ifSaveCache = false;
            mOnSuccessListener.onSuccess(result);
        } else {
            refreshData();
        }
    }

    private void refreshData() {
        ifSaveCache = true;
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().doSearchMyProjects(new ProgressSubscriber(mOnSuccessListener, _mActivity), user_code, access_token, String.valueOf(mFrom + 1), "", pageIndex, pageSize);
    }

    private void initPullRefresh(View view) {

        // header
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        //header.setPadding(0, LocalDisplay.dp2px(15), 0, 0);
        header.setPadding(0, LocalDisplay.dp2px(20), 0, LocalDisplay.dp2px(20));
        /**
         * using a string, support: A-Z 0-9 - .
         * you can add more letters by {@link in.srain.cube.views.ptr.header.StoreHousePath#addChar}
         */

        header.initWithString(mStringList[0]);

        ptrFrameLayout.setDurationToCloseHeader(3000);
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
//        ptrFrameLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ptrFrameLayout.autoRefresh(false);
//            }
//        }, 100);


        StoreHouseHeader footer = new StoreHouseHeader(getContext());
        footer.setPadding(0, LocalDisplay.dp2px(20), 0, LocalDisplay.dp2px(20));
        footer.initWithString("More");

        ptrFrameLayout.setFooterView(footer);
        ptrFrameLayout.addPtrUIHandler(footer);
        ptrFrameLayout.setPtrHandler(new PtrHandler2() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                //return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                if (mRecy.getChildCount() > 0) {
                    return checkCanDoRefreshLocal();
                } else
                    return true;
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                //return PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
                if (mRecy.getChildCount() > 0) {
                    return checkCanDoLoadMoreLocal();
                } else
                    return false;
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 200);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndex = 1;
                        refreshData();
                        //frame.refreshComplete();
                    }
                }, 200);
            }
        });
    }


    public boolean checkCanDoRefreshLocal() {
        if (mAdapter.getItemCount() == 0 || mRecy == null) {
            return true;
        }
        //LinearLayoutManager llmanager = (LinearLayoutManager) mRecy.getLayoutManager();
        CLog.d("test", "checkCanDoRefresh: %s %s", mLLmanager.findFirstVisibleItemPosition(), mRecy.getChildAt(0).getTop());
        int a = mLLmanager.findFirstVisibleItemPosition();
        int b = mRecy.getChildAt(0).getTop();
        boolean ifTrue = a == 0 && b == 25;
        return ifTrue;
    }

    public boolean checkCanDoLoadMoreLocal() {
        if (mAdapter.getItemCount() == 0 || mRecy == null) {
            return true;
        }
        CLog.d("test", "checkCanDoRefresh: %s %s", mLLmanager.findFirstVisibleItemPosition(), mRecy.getChildAt(0).getTop());
        int a = mLLmanager.findLastVisibleItemPosition();
        //int b = mAdapter.getItemCount() - 1;
        int b = mAdapter.getItemCount() - 1;
        boolean ifTrue1 = a >= b;
//        boolean ifTrue2 = true;
//        int test = mRecy.getChildCount();
//        if(mRecy.getChildAt(mAdapter.getItemCount() - 1) == null){
//            ifTrue2 = false;
//        }else {
//            int x = mRecy.getChildAt(mAdapter.getItemCount() - 1).getBottom();
//            int y = mRecy.getPaddingBottom();
//            ifTrue2 =  x > y;
//        }

        return ifTrue1;
        //return  ifTrue1 || ifTrue2;
    }


    /**
     * 初始化视图
     */
    private void initSearchView(View view) {

        searchView = (SearchViewCut) view.findViewById(R.id.sv_search_layout);
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
        String token = PreferencesUtils.getString(_mActivity, "user_token");
        //ProxyService.newInstance().SearchVans(_mActivity,token,text);

        //第一次获取结果 还未配置适配器
        if (mRecy.getAdapter() == null) {
            //获取搜索数据 设置适配器
            LinearLayoutManager manager = new LinearLayoutManager(_mActivity);
            mRecy.setLayoutManager(manager);
            mRecy.setAdapter(mAdapter);
        } else {
            //更新搜索数据
            String user_code = PreferencesUtils.getString(_mActivity, "user_code");
            String access_token = PreferencesUtils.getString(_mActivity, "access_token");
            HttpMethods.getInstance().doSearchMyProjects(new ProgressSubscriber(mOnSuccessListener, _mActivity), user_code, access_token, String.valueOf(mFrom + 1), searchView.getEtInputText(), 1, pageSize);
            mAdapter.notifyDataSetChanged();
        }
        Toast.makeText(_mActivity, "搜索完成", Toast.LENGTH_SHORT).show();


        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) _mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }


    private void scrollToTop() {
        mRecy.smoothScrollToPosition(0);
    }

    /**
     * 选择tab事件
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainActivity.FIRST) return;

//        if (mAtTop) {
//            mRefreshLayout.setRefreshing(true);
//            onRefresh();
//        } else {

        scrollToTop();
        //}
    }


    @Override
    public void onResume() {
        super.onResume();
        boolean isVisible = getUserVisibleHint();
        if (isVisible) {
            if (mFrom == 0) {
                ((MainActivity) getActivity()).getResideLayout().setIsNeedSlide(true);
            } else {
                ((MainActivity) getActivity()).getResideLayout().setIsNeedSlide(false);
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //mOnSuccessListener = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        //注销广播
//        _mActivity.unregisterReceiver(mReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
    }

}
