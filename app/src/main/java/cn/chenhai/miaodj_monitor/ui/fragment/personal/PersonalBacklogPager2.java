package cn.chenhai.miaodj_monitor.ui.fragment.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.adapter.PersonalBacklogPagerAdapter;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.BackLogEntity;
import cn.chenhai.miaodj_monitor.model.entity.BackLogNewMsgEntity;
import cn.chenhai.miaodj_monitor.model.info.Backlog_Info;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.utils.CLog;
import cn.chenhai.miaodj_monitor.utils.diskCache.ACache;
import cn.chenhai.miaodj_monitor.ui.base.BaseFragment;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by ChenHai--霜华 on 2016/6/23. 15:17
 * 邮箱：248866527@qq.com
 */
public class PersonalBacklogPager2 extends BaseFragment {
    private static final String ARG_FROM = "arg_from";
    private int mFrom;
    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessInit;
    private boolean ifSaveCache = false;

    private PtrClassicFrameLayout mRefreshPtrFrameLayout;
    private RecyclerView mRecycler;
    private LinearLayoutManager mLLmanager;
    private PersonalBacklogPagerAdapter mAdapter;

    private LinearLayout mEmptyViewLayout;


    public static PersonalBacklogPager2 newInstance(int from , String mProjectCode) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);
        args.putString("mProjectCode",mProjectCode);

        PersonalBacklogPager2 fragment = new PersonalBacklogPager2();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mFrom = args.getInt(ARG_FROM);
            mProjectCode = args.getString("mProjectCode");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_personal_backlog_pager, container, false);

        initView(view);
        //initDataTemp();

        initPullRefresh(view);
        return view;
    }

    private void initView(View view) {
        mRefreshPtrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.refresh_ptrFrameLayout);
        mRecycler = (RecyclerView) view.findViewById(R.id.recycler);
        mEmptyViewLayout = (LinearLayout) view.findViewById(R.id.empty_view_layout);

        mAdapter = new PersonalBacklogPagerAdapter(_mActivity,mFrom);
        mLLmanager = new LinearLayoutManager(_mActivity);
        mRecycler.setLayoutManager(mLLmanager);
        mRecycler.setAdapter(mAdapter);

//        mAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View view) {
//                //((MainActivity)getActivity()).getResideLayout().setIfSlide(true);
//                TimerTask task = new TimerTask(){
//                    public void run(){
//                        //execute the task
//                        if (getParentFragment() instanceof PersonalBacklogFragment) {
//                            //((HomeFragment) getParentFragment()).start(DetailAgreeFragment.newInstance("测试单号111"));
//                            ((PersonalBacklogFragment) getParentFragment()).start(CycleFragment.newInstance(1));
//                        }
//                    }
//                };
//                Timer timer = new Timer();
//                timer.schedule(task, 260);
//            }
//        });
//        mAdapter.setOnItemBtnClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position, View view) {
//                if (getParentFragment() instanceof PersonalBacklogFragment) {
//                    ((PersonalBacklogFragment) getParentFragment()).start(CycleFragment.newInstance(1));
//                }
//            }
//        });


        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<BackLogNewMsgEntity>>() {
            @Override
            public void onSuccess(HttpResult<BackLogNewMsgEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(ifSaveCache) {
                        ACache mCache = ACache.get(_mActivity);
                        mCache.put("PersonalBacklogPager2", result, 10*60);//保存10分钟，如果超过10分钟去获取这个key，将为null
                    }
                    List<BackLogNewMsgEntity.CommonMessageBean> projectsCommon = result.getInfo().getCommon_message();
                    List<BackLogNewMsgEntity.SingleMessageBean> projectsSingle = result.getInfo().getSingle_message();

                    List<Backlog_Info> list = new ArrayList<>();

                    if(projectsCommon.size()==0 && projectsSingle.size()==0){
                        mEmptyViewLayout.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                        ((PersonalBacklogFragment) getParentFragment()).hideNewDot(1);
                    }else {
                        mEmptyViewLayout.setVisibility(View.GONE);
                        mRecycler.setVisibility(View.VISIBLE);
                        ((PersonalBacklogFragment) getParentFragment()).showNewDot(1);
                    }

                    for (int i=0 ;i<projectsCommon.size() ;i++){
                        Backlog_Info pageInfo = new Backlog_Info();
                        BackLogNewMsgEntity.CommonMessageBean nodeInfo = projectsCommon.get(i);

                        pageInfo.setIsNew(true);
                        pageInfo.setMessageTitle(nodeInfo.getTitle());
                        pageInfo.setMessageDetail(nodeInfo.getContent());
                        pageInfo.setMessageTime(nodeInfo.getCreatetime());


                        list.add(pageInfo);
                    }

                    for (int i=0 ;i<projectsSingle.size() ;i++){
                        Backlog_Info pageInfo = new Backlog_Info();
                        BackLogNewMsgEntity.SingleMessageBean nodeInfo = projectsSingle.get(i);

                        pageInfo.setIsNew(true);
                        pageInfo.setMessageTitle(nodeInfo.getTitle());
                        pageInfo.setMessageDetail(nodeInfo.getContent());
                        pageInfo.setMessageTime(nodeInfo.getCreatetime());


                        list.add(pageInfo);
                    }

                    mAdapter.refreshDatas(list);

                    mAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCompleted(){
                mRefreshPtrFrameLayout.refreshComplete();
            }
            @Override
            public void onError(){
                mRefreshPtrFrameLayout.refreshComplete();
                mEmptyViewLayout.setVisibility(View.VISIBLE);
                mRecycler.setVisibility(View.GONE);
                ((PersonalBacklogFragment) getParentFragment()).hideNewDot(1);
            }
        };

        ACache mCache = ACache.get(_mActivity);
        HttpResult<BackLogEntity> result = (HttpResult<BackLogEntity>) mCache.getAsObject("PersonalBacklogPager2");

        if(result!=null){
            ifSaveCache = false;
            mOnSuccessInit.onSuccess(result);
        }else {
            refreshData();
        }
    }

    private void refreshData(){
        ifSaveCache = true;
        String user_code = PreferencesUtils.getString(_mActivity,"user_code");
        String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
        HttpMethods.getInstance().getMyMessage(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token,"0","0");
    }

    private void initDataTemp() {
        List<Backlog_Info> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Backlog_Info pageInfo = new Backlog_Info();

            if (i % 3 == 0) {
                pageInfo.setIsNew(true);
                pageInfo.setMessageTitle("施工进度消息");
                pageInfo.setMessageDetail("您的项目 ： 中海国际三期5幢1121室，已经完成了施工进场节点，进场放线节点即将开始");
                pageInfo.setMessageTime("2016-06-12 10:23");

            } else if (i % 3 == 1) {
                pageInfo.setIsNew(true);
                pageInfo.setMessageTitle("项目付款消息");
                pageInfo.setMessageDetail("您的项目 ： 中海国际三期5幢1121室，已经成功支付了合同第一笔款项，款项金额为：12000.00元。");
                pageInfo.setMessageTime("2016-06-12 10:25");

            } else if (i % 3 == 2) {
                pageInfo.setIsNew(false);
                pageInfo.setMessageTitle("项目合同签订消息");
                pageInfo.setMessageDetail("您已成功签订合同，您的项目装修地址为：中海国际三期5幢1121室，项目成立后系统会随时提醒您后续进展，请保持关注。");
                pageInfo.setMessageTime("2016-06-12 10:27");

            }
            list.add(pageInfo);
        }
        mAdapter.refreshDatas(list);
    }

    private void initPullRefresh(View view){
        mRefreshPtrFrameLayout.setLastUpdateTimeRelateObject(this);

        // the following are default settings
        mRefreshPtrFrameLayout.setResistance(1.7f); // you can also set foot and header separately
        mRefreshPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mRefreshPtrFrameLayout.setDurationToClose(1000);  // you can also set foot and header separately
        // default is false
        mRefreshPtrFrameLayout.setPullToRefresh(false);

        // default is true
        mRefreshPtrFrameLayout.setKeepHeaderWhenRefresh(true);

        mRefreshPtrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 300);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 300);
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

    }

    public boolean checkCanDoRefreshLocal() {
        if (mAdapter.getItemCount() == 0 || mRecycler == null) {
            return true;
        }
        //LinearLayoutManager llmanager = (LinearLayoutManager) mRecy.getLayoutManager();
        CLog.d("test", "checkCanDoRefresh: %s %s", mLLmanager.findFirstVisibleItemPosition(), mRecycler.getChildAt(0).getTop());
        int a = mLLmanager.findFirstVisibleItemPosition();
        int b = mRecycler.getChildAt(0).getTop();
        boolean ifTrue = a == 0 && b == 27;
        return ifTrue;
    }

    protected void updateData() {
        mRefreshPtrFrameLayout.refreshComplete();
    }

    private void scrollToTop() {
        mRecycler.smoothScrollToPosition(0);
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
