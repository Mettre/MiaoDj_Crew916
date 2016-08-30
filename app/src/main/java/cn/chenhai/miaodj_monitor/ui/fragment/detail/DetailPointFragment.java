package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.chenhai.miaodj_monitor.ui.view_custom.FunRefresh.FunGameRefreshRecyclerView;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.adapter.DetailPointAdapter;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.PointEntity;
import cn.chenhai.miaodj_monitor.model.info.DetailPointInfo;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;
import cn.chenhai.miaodj_monitor.ui.fragment.worker.WorkerCheckFragment;

/**
 * 项目节点
 * Created by ChenHai--霜华 on 2016/6/13. 21:08
 * 邮箱：248866527@qq.com
 */
public class DetailPointFragment extends BaseBackFragment_Swip {
    private static final String ARG_ITEM = "arg_item";
    private static final String TAG = "FragmentLib";

    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessListener;

    private FunGameRefreshRecyclerView refreshView;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLLmanager;
    private DetailPointAdapter mAdapter;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mPointStartTime;
    private TextView mPointDateText;
    private TextView mPointDateNum;
    private TextView mPointDateText2;

    private LinearLayout mPointLlMsgShow;
    private TextView mPointTvDoChooseMsg;
    private Button mPointBtnDoChoose;

    private int mGameType = 0;

    public static DetailPointFragment newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        DetailPointFragment fragment = new DetailPointFragment();
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
        View view = inflater.inflate(R.layout.fragment_detail_point, container, false);
        initView(view);
        initData();
        //initDataTemp();
        //return view;
        return attachToSwipeBack(view);
    }

    private void initView(View view) {
        refreshView = (FunGameRefreshRecyclerView) view.findViewById(R.id.refresh_fun_game);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mAdapter = new DetailPointAdapter(_mActivity);
        mLLmanager = new LinearLayoutManager(_mActivity);
        mRecyclerView.setLayoutManager(mLLmanager);
        mRecyclerView.setAdapter(mAdapter);


        mPointLlMsgShow = (LinearLayout) view.findViewById(R.id.point_ll_msgShow);
        mPointTvDoChooseMsg = (TextView) view.findViewById(R.id.point_tv_doChooseMsg);
        mPointBtnDoChoose = (Button) view.findViewById(R.id.point_btn_doChoose);


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //((MainActivity)getActivity()).getResideLayout().setIfSlide(true);
                TimerTask task = new TimerTask() {
                    public void run() {
                        //execute the task
                        //start(DetailAgreeFragment.newInstance("测试单号111"));
                        start(DetailPointProgressFragment.newInstance(mProjectCode, mAdapter.getItem(position).getPointID(), mAdapter.getItemCount()));
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 260);
            }
        });

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);
        mToolbar.inflateMenu(R.menu.point_now);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
//                    case R.id.action_hierarchy:
//                        _mActivity.showFragmentStackHierarchyView();
//                        _mActivity.logFragmentStackHierarchy(TAG);
//                        break;
                    case R.id.action_now:
                        Toast.makeText(_mActivity, "当前", Toast.LENGTH_SHORT).show();
//                        if(mGameType == 0){
//                            refreshView.setGameType(mGameType);
//                            mGameType++;
//                        } else if(mGameType == 1){
//                            refreshView.setGameType(mGameType);
//                            mGameType = 0;
//                        }
                        //refreshView.setGameType(1);
                        break;
                }
                return true;
            }
        });

        mPointStartTime = (TextView) view.findViewById(R.id.point_startTime);
        mPointDateText = (TextView) view.findViewById(R.id.point_dateText);
        mPointDateNum = (TextView) view.findViewById(R.id.point_dateNum);
        mPointDateText2 = (TextView) view.findViewById(R.id.point_dateText2);


        refreshView.setOnRefreshListener(new FunGameRefreshRecyclerView.FunGameRefreshListener() {
            @Override
            public void onRefreshing() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.sendEmptyMessage(0);

            }
        });

        mPointBtnDoChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(WorkerCheckFragment.newInstance(mProjectCode));
            }
        });

        mOnSuccessListener = new SubscriberOnSuccessListener<HttpResult<PointEntity>>() {
            @Override
            public void onSuccess(HttpResult<PointEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    String days = "0";
                    if (result.getInfo().getStart_days() >= 0) {
                        days = String.valueOf(result.getInfo().getStart_days());
                        mPointDateText.setVisibility(View.VISIBLE);
                        mPointDateText2.setText("天");
                    }
                    if (result.getInfo().getBefore_start_days() > 0) {
                        days = String.valueOf(result.getInfo().getBefore_start_days());
                        mPointDateText.setVisibility(View.GONE);
                        mPointDateText2.setText("天后开始");
                    }
                    mPointDateNum.setText(days);
                    mPointStartTime.setText(result.getInfo().getStart_date());

                    //////////////////////////////////////////
                    if (result.getInfo().getShow_msg().equals("Y")) {
                        mPointLlMsgShow.setVisibility(View.VISIBLE);
                        String msg = "第" + result.getInfo().getShow_id() + "节点即将开始，【" + result.getInfo().getShow_type()
                                + "】还未选择";
                        mPointTvDoChooseMsg.setText(msg);
                        mPointBtnDoChoose.setEnabled(true);
                    } else if (result.getInfo().getShow_msg().equals("N")) {
                        mPointLlMsgShow.setVisibility(View.GONE);
                        mPointBtnDoChoose.setEnabled(false);
                    }
                    ///////////////////////////////////////////

                    List<PointEntity.NodesBean> projects = result.getInfo().getNodes();
                    List<DetailPointInfo> list = new ArrayList<>();
                    for (int i = 0; i < projects.size(); i++) {
                        DetailPointInfo pageInfo = new DetailPointInfo();
                        PointEntity.NodesBean nodeInfo = projects.get(i);

                        pageInfo.setPointID(nodeInfo.getId());
                        pageInfo.setItemIndex(nodeInfo.getNode_id());
                        pageInfo.setItemName(nodeInfo.getTitle());
                        String status = "";
                        String date = "";
                        switch (nodeInfo.getStatus()) {
                            case "1":
                                status = "未开始";
                                date = "预计开始时间" + nodeInfo.getExpect_start_date();
                                break;
                            case "10":
                                status = "待进场";
                                date = "预计开始时间" + nodeInfo.getExpect_start_date();
                                break;
                            case "20":
                                status = "待施工";
                                date = "预计开始时间" + nodeInfo.getExpect_start_date();
                                break;
                            case "30":
                                status = "施工中";
                                date = "开始时间" + nodeInfo.getActual_start_date();
                                break;
                            case "40":
                                status = "待施工员验收";
                                date = "预计开始时间" + nodeInfo.getExpect_start_date();
                                break;
                            case "42":
                                status = "施工员验收不通过";
                                date = "预计开始时间" + nodeInfo.getExpect_start_date();
                                break;
                            case "50":
                                status = "待业主验收";
                                date = "预计开始时间" + nodeInfo.getExpect_start_date();
                                break;
                            case "52":
                                status = "业主验收不通过";
                                date = "预计开始时间" + nodeInfo.getExpect_start_date();
                                break;
                            case "90":
                                status = "停工";
                                date = "预计开始时间" + nodeInfo.getExpect_start_date();
                                break;
                            case "100":
                                status = "已完成";
                                date = "完成时间" + nodeInfo.getActual_end_date();
                                break;
                            case "110":
                                status = "已完成";
                                date = "完成时间" + nodeInfo.getActual_end_date();
                                break;
                        }
                        pageInfo.setBtnStatus(status);
                        pageInfo.setStartDate(date);
                        if (status.equals("已完成")) {
                            if (nodeInfo.getScore() == null) {
                                pageInfo.setEvaluate("未评价");
                            } else {
                                pageInfo.setEvaluate("已评价");
                            }
                        } else {
                            pageInfo.setEvaluate("");
                        }

                        list.add(pageInfo);
                    }

                    int count = getCompleteItemCount(list);
                    mAdapter.setDatas(list, count);

                    mAdapter.notifyDataSetChanged();
                    refreshView.finishRefreshing();
                }
            }

            @Override
            public void onCompleted() {
                refreshView.finishRefreshing();
            }

            @Override
            public void onError() {
                refreshView.finishRefreshing();
            }
        };

        updateData();
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //dataList.add("X");
            updateData();
            //mAdapter.notifyDataSetChanged();
            //refreshView.finishRefreshing();
        }
    };


    private void initData() {
        mToolbarTitle.setText("施工进度");
    }

    private void initDataTemp() {
        List<DetailPointInfo> list = new ArrayList<>();

        DetailPointInfo pointInfo1 = new DetailPointInfo();
        pointInfo1.setItemIndex("1");
        pointInfo1.setItemName("进场");
        pointInfo1.setBtnStatus("已完成");
        pointInfo1.setStartDate("完成日期 2016-06-01");
        pointInfo1.setEvaluate("未评价");

        list.add(pointInfo1);

        pointInfo1 = new DetailPointInfo();
        pointInfo1.setItemIndex("2");
        pointInfo1.setItemName("整体(基层,面层)放线");
        pointInfo1.setBtnStatus("已完成");
        pointInfo1.setStartDate("完成日期 2016-06-03");
        pointInfo1.setEvaluate("已评价");

        list.add(pointInfo1);

        pointInfo1 = new DetailPointInfo();
        pointInfo1.setItemIndex("3");
        pointInfo1.setItemName("拆墙");
        pointInfo1.setBtnStatus("待施工");
        pointInfo1.setStartDate("预计开始日期 2016-06-05");
        pointInfo1.setEvaluate("");

        list.add(pointInfo1);

        pointInfo1 = new DetailPointInfo();
        pointInfo1.setItemIndex("4");
        pointInfo1.setItemName("码单图纸");
        pointInfo1.setBtnStatus("后场加工");
        pointInfo1.setStartDate("预计开始日期 2016-06-06");
        pointInfo1.setEvaluate("");

        list.add(pointInfo1);

        for (int i = 5; i < 30; i++) {
            DetailPointInfo pointInfo = new DetailPointInfo();

            if (i % 4 == 0) {
                pointInfo.setItemIndex(String.valueOf(i));
                pointInfo.setItemName("拆除清运");
                pointInfo.setBtnStatus("未开始");
                pointInfo.setStartDate("预计开始日期 2016-06-11");
                pointInfo.setEvaluate("");

            } else if (i % 4 == 1) {
                pointInfo.setItemIndex(String.valueOf(i));
                pointInfo.setItemName("新建墙体");
                pointInfo.setBtnStatus("未开始");
                pointInfo.setStartDate("预计开始日期 2016-06-12");
                pointInfo.setEvaluate("");

            } else if (i % 4 == 2) {
                pointInfo.setItemIndex(String.valueOf(i));
                pointInfo.setItemName("墙地固");
                pointInfo.setBtnStatus("未开始");
                pointInfo.setStartDate("预计开始日期 2016-06-13");
                pointInfo.setEvaluate("");

            } else if (i % 4 == 3) {
                pointInfo.setItemIndex(String.valueOf(i));
                pointInfo.setItemName("水电");
                pointInfo.setBtnStatus("未开始");
                pointInfo.setStartDate("预计开始日期 2016-06-14");
                pointInfo.setEvaluate("");

            }
            list.add(pointInfo);
        }
        int count = getCompleteItemCount(list);
        mAdapter.setDatas(list, count);
    }

    private void updateData() {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().getNodesList(new ProgressSubscriber(mOnSuccessListener, _mActivity), user_code, access_token, mProjectCode);
    }

    private int getCompleteItemCount(List<DetailPointInfo> list) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getBtnStatus().equals("已完成")) {
                count++;
            }
        }
        return count;
    }
}
