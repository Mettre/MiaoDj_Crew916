package cn.chenhai.miaodj_monitor.ui.fragment.personal;

import android.content.Intent;
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
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.BackLogEntity;
import cn.chenhai.miaodj_monitor.model.info.Backlog_Info;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.utils.CLog;
import cn.chenhai.miaodj_monitor.utils.diskCache.ACache;
import cn.chenhai.miaodj_monitor.ui.activity.DetailActivity;
import cn.chenhai.miaodj_monitor.ui.base.BaseFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by ChenHai--霜华 on 2016/6/23. 15:17
 * 邮箱：248866527@qq.com
 */
public class PersonalBacklogPager1 extends BaseFragment {
    private static final String ARG_FROM = "arg_from";
    private static final int REQ_START_DETAIL_FOR_RESULT = 1199;

    private static final int STATUS_CREW_CHECK = 9;                 //选定为项目施工员通知   {PROJECT}  --跳转至 待确认项目页面
    private static final int STATUS_CREW_DISAGREE_JINGCHANG = 10;   //业主反馈施工进场申请结果提醒（不同意）--跳转至 待项目详情
    private static final int STATUS_CREW_CHECK_DRAWING = 11;        //图纸确认通知	--跳转至 待项目详情
    private static final int STATUS_CREW_ORDER_MATERIAL_MUN = 12;   //辅材订单用量确认通知  {PROJECT}
    private static final int STATUS_CREW_MATERIAL_FINISH = 13;      //材料备货完成提醒  {PROJECT}
    private static final int STATUS_CREW_NOTICE_MATERIAL_ARRIVE = 14;//材料配送到场提醒签收  {PROJECT},{ARRIVE_DATE}
    private static final int STATUS_CREW_CHECK_NODE = 15;           //节点完成验收提醒  {PROJECT},{NODE_TITLE}  --跳转至 节点详情
    private static final int STATUS_CREW_DISAGREE_WORKER = 16;      //工人拒绝被添加为项目工人的结果提醒  {PROJECT},{WORKER_TYPE} --跳转至 项目详情
    private static final int STATUS_CREW_DISAGREE_YINGBI = 17;      //隐蔽工程业主验收不通过通知 {PROJECT} --跳转至 节点详情
    private static final int STATUS_CREW_DISAGREE_JUNGONG = 18;     //项目完工业主验收不通过通知 {PROJECT} --跳转至 节点详情

    private int mFrom;
    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessInit;
    private boolean ifSaveCache = false;

    private PtrClassicFrameLayout mRefreshPtrFrameLayout;
    private RecyclerView mRecycler;
    private LinearLayoutManager mLLmanager;
    private PersonalBacklogPagerAdapter mAdapter;

    private LinearLayout mEmptyViewLayout;


    public static PersonalBacklogPager1 newInstance(int from , String mProjectCode) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);
        args.putString("mProjectCode",mProjectCode);

        PersonalBacklogPager1 fragment = new PersonalBacklogPager1();
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
        mAdapter.setOnItemBtnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if (getParentFragment() instanceof PersonalBacklogFragment) {
                    //((PersonalBacklogFragment) getParentFragment()).start(CycleFragment.newInstance(1));
                    int mess_Type = 0;
                    if(mAdapter.getItem(position).getType()!=null){
                        mess_Type = Integer.valueOf(mAdapter.getItem(position).getType());
                    }

                    Bundle bundle = new Bundle();
                    boolean ifReturn = false;

                    switch (mess_Type){
                        case STATUS_CREW_CHECK:
                            //选定为项目施工员通知   {PROJECT}  --跳转至 待确认项目页面
                            bundle.putString("FragmentName", "DetailAgreeFragment");
                            bundle.putString("ProjectCode",mAdapter.getItem(position).getProject_code() );
                            break;
                        case  STATUS_CREW_DISAGREE_JINGCHANG:
                            //业主反馈施工进场申请结果提醒（不同意）--跳转至 待项目详情
                            bundle.putString("FragmentName", "DetailStartFragment");
                            bundle.putString("ProjectCode",mAdapter.getItem(position).getProject_code() );
                            break;
                        case  STATUS_CREW_CHECK_DRAWING :
                            //图纸确认通知	--跳转至 待项目详情
                            bundle.putString("FragmentName", "DetailStartFragment");
                            bundle.putString("ProjectCode",mAdapter.getItem(position).getProject_code() );
                            break;
                        case STATUS_CREW_ORDER_MATERIAL_MUN :
                            //辅材订单用量确认通知  {PROJECT}
                            ifReturn = true;
                            break;
                        case STATUS_CREW_MATERIAL_FINISH :
                            //材料备货完成提醒  {PROJECT}
                            ifReturn = true;
                            break;
                        case STATUS_CREW_NOTICE_MATERIAL_ARRIVE :
                            //材料配送到场提醒签收  {PROJECT},{ARRIVE_DATE}
                            ifReturn = true;
                            break;
                        case STATUS_CREW_CHECK_NODE :
                            //节点完成验收提醒  {PROJECT},{NODE_TITLE}  --跳转至 节点详情
                            bundle.putString("pointID",mAdapter.getItem(position).getNode_id());
                            bundle.putString("totalCount","1");
                            bundle.putString("FragmentName", "DetailPointProgressFragment");
                            bundle.putString("ProjectCode",mAdapter.getItem(position).getProject_code() );
                            break;
                        case STATUS_CREW_DISAGREE_WORKER :
                            //工人拒绝被添加为项目工人的结果提醒  {PROJECT},{WORKER_TYPE} --跳转至 项目详情
                            bundle.putString("FragmentName", "DetailIndexFragment");
                            bundle.putString("ProjectCode",mAdapter.getItem(position).getProject_code() );
                            break;
                        case STATUS_CREW_DISAGREE_YINGBI :
                            //隐蔽工程业主验收不通过通知 {PROJECT} --跳转至 节点详情
                            //节点完成验收提醒  {PROJECT},{NODE_TITLE}  --跳转至 节点详情
                            bundle.putString("pointID",mAdapter.getItem(position).getNode_id());
                            bundle.putString("totalCount","1");
                            bundle.putString("FragmentName", "DetailPointProgressFragment");
                            bundle.putString("ProjectCode",mAdapter.getItem(position).getProject_code() );
                            break;
                        case STATUS_CREW_DISAGREE_JUNGONG :
                            //项目完工业主验收不通过通知 {PROJECT} --跳转至 节点详情
                            //节点完成验收提醒  {PROJECT},{NODE_TITLE}  --跳转至 节点详情
                            bundle.putString("pointID",mAdapter.getItem(position).getNode_id());
                            bundle.putString("totalCount","1");
                            bundle.putString("FragmentName", "DetailPointProgressFragment");
                            bundle.putString("ProjectCode",mAdapter.getItem(position).getProject_code() );
                            break;
                    }

                    if(ifReturn){
                        new SweetAlertDialog(_mActivity,SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("提示")
                                .setContentText("暂无跳转页面！")
                                .show();

                    }else {
                        Intent intent = new Intent(_mActivity, DetailActivity.class);
                        intent.putExtras(bundle);
                        ((PersonalBacklogFragment) getParentFragment()).startActivityForResult(intent, REQ_START_DETAIL_FOR_RESULT);
                    }
                }
            }
        });


        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<BackLogEntity>>() {
            @Override
            public void onSuccess(HttpResult<BackLogEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(ifSaveCache) {
                        ACache mCache = ACache.get(_mActivity);
                        mCache.put("PersonalBacklogPager1", result, 10*60);//保存10分钟，如果超过10分钟去获取这个key，将为null
                    }
                    List<BackLogEntity.TodoBean> projects = result.getInfo().getTodo();
                    List<Backlog_Info> list = new ArrayList<>();

                    if(projects.size()==0){
                        mEmptyViewLayout.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                        ((PersonalBacklogFragment) getParentFragment()).hideNewDot(0);
                    }else {
                        mEmptyViewLayout.setVisibility(View.GONE);
                        mRecycler.setVisibility(View.VISIBLE);
                        ((PersonalBacklogFragment) getParentFragment()).showNewDot(0);
                    }

                    for (int i=0 ;i<projects.size() ;i++){
                        Backlog_Info pageInfo = new Backlog_Info();
                        BackLogEntity.TodoBean nodeInfo = projects.get(i);

                        pageInfo.setIsNew(true);
                        pageInfo.setMessageTitle(nodeInfo.getTitle());
                        pageInfo.setMessageDetail(nodeInfo.getContent());
                        pageInfo.setMessageTime(nodeInfo.getCreatetime());

                        pageInfo.setProject_code(nodeInfo.getProject_code());
                        pageInfo.setRedirect_url(nodeInfo.getRedirect_url());
                        pageInfo.setNode_id(nodeInfo.getNode_id());
                        pageInfo.setType(nodeInfo.getType());

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
                ((PersonalBacklogFragment) getParentFragment()).hideNewDot(0);
            }
        };

        ACache mCache = ACache.get(_mActivity);
        HttpResult<BackLogEntity> result = (HttpResult<BackLogEntity>) mCache.getAsObject("PersonalBacklogPager1");

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
        HttpMethods.getInstance().getMyTodo(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token );
    }

    private void initDataTemp() {
        List<Backlog_Info> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Backlog_Info pageInfo = new Backlog_Info();

            if (i % 3 == 0) {
                pageInfo.setIsNew(true);
                pageInfo.setMessageTitle("选品单待确认");
                pageInfo.setMessageDetail("装修项目 - 中海国际三期5幢1121室");
                pageInfo.setMessageTime("2016-06-12 10:23");

            } else if (i % 3 == 1) {
                pageInfo.setIsNew(true);
                pageInfo.setMessageTitle("图纸待确认");
                pageInfo.setMessageDetail("装修项目 - 中海国际三期5幢1121室");
                pageInfo.setMessageTime("2016-06-12 10:25");

            } else if (i % 3 == 2) {
                pageInfo.setIsNew(false);
                pageInfo.setMessageTitle("隐蔽工程施工节点待审核");
                pageInfo.setMessageDetail("装修项目 - 中海国际三期5幢1121室");
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
