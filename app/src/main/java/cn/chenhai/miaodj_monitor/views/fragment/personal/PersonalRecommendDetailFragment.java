package cn.chenhai.miaodj_monitor.views.fragment.personal;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.adapter.GridWorkerAdapter;
import cn.chenhai.miaodj_monitor.adapter.PersonalRecommendDetailAdapter;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.RecommendWorkerDetailEntity;
import cn.chenhai.miaodj_monitor.model.info.RecommendDetailInfo;
import cn.chenhai.miaodj_monitor.network_proxy.HttpMethods;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment_Swip;

/**
 * Created by ChenHai--霜华 on 2016/6/28. 12:56
 * 邮箱：248866527@qq.com
 */
public class PersonalRecommendDetailFragment extends BaseBackFragment_Swip {
    private static final String ARG_ITEM = "arg_item";
    private static final String TAG = "FragmentLib";

    private String mWorkerCode;

    private SubscriberOnSuccessListener mOnSuccessInit;

    private LinearLayoutManager mLLmanager;
    private PersonalRecommendDetailAdapter mAdapter;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private SimpleDraweeView mWorkerSdvPortrait;
    private TextView mTvWorkerName;
    private GridView mGridPopupWorkerType;
    private TextView mTvWorkerPhone;
    private TextView mTvWorkerCount;
    private TextView mTvWorkerMoneyTotal;
    private RecyclerView mRecommendRecycler;

    public static PersonalRecommendDetailFragment newInstance(String workerCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, workerCode);
        PersonalRecommendDetailFragment fragment = new PersonalRecommendDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWorkerCode = getArguments().getString(ARG_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommend_detail, container, false);
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

        mWorkerSdvPortrait = (SimpleDraweeView) view.findViewById(R.id.worker_sdv_portrait);
        mTvWorkerName = (TextView) view.findViewById(R.id.tv_worker_name);
        mGridPopupWorkerType = (GridView) view.findViewById(R.id.grid_popup_workerType);
        mTvWorkerPhone = (TextView) view.findViewById(R.id.tv_worker_phone);
        mTvWorkerCount = (TextView) view.findViewById(R.id.tv_worker_count);
        mTvWorkerMoneyTotal = (TextView) view.findViewById(R.id.tv_worker_moneyTotal);
        mRecommendRecycler = (RecyclerView) view.findViewById(R.id.recommend_recycler);


        mAdapter = new PersonalRecommendDetailAdapter(_mActivity);
        mLLmanager = new LinearLayoutManager(_mActivity);
        mRecommendRecycler.setLayoutManager(mLLmanager);
        mRecommendRecycler.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //execute the task
                //start(DetailAgreeFragment.newInstance("getItem"));
            }
        });


        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<RecommendWorkerDetailEntity>>() {
            @Override
            public void onSuccess(HttpResult<RecommendWorkerDetailEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {

                    List<RecommendWorkerDetailEntity.RecommendBean.WorkerTypeBean> typeList = result.getInfo().getRecommend().getWorker_type();
                    List<String> types = new ArrayList<>();
                    for (int i=0 ;i<typeList.size() ;i++){
                        types.add(typeList.get(i).getWorker_type_name());
                    }
                    mGridAdapter.setData(types);
                    mGridAdapter.notifyDataSetChanged();

                    String headPath = "";
                    if(result.getInfo().getRecommend().getHeadimg() != null) {
                        if(!result.getInfo().getRecommend().getHeadimg().equals("")) {
                            headPath = HttpMethods.BASE_ROOT_URL + result.getInfo().getRecommend().getHeadimg();
                        }
                    }
                    mWorkerSdvPortrait.setImageURI(Uri.parse(headPath));

                    mTvWorkerName.setText(result.getInfo().getRecommend().getReal_name());
                    mTvWorkerPhone.setText(result.getInfo().getRecommend().getTelephone());
                    mTvWorkerCount.setText(String.valueOf(result.getInfo().getRecommend().getOrder_count()));
                    mTvWorkerMoneyTotal.setText(String.valueOf(result.getInfo().getRecommend().getCommission_all()));

                    List<RecommendWorkerDetailEntity.RecommendBean.RecommendLogBean> projects = result.getInfo().getRecommend().getRecommend_log();
                    List<RecommendDetailInfo> list = new ArrayList<>();
                    for (int i=0 ;i<projects.size() ;i++){
                        RecommendDetailInfo pageInfo = new RecommendDetailInfo();
                        RecommendWorkerDetailEntity.RecommendBean.RecommendLogBean nodeInfo = projects.get(i);


                        pageInfo.setOrderNum(nodeInfo.getProject_code());
                        pageInfo.setProjectType(nodeInfo.getTitle());
                        pageInfo.setBuildStartDate(nodeInfo.getWorking_start());
                        pageInfo.setBuildEndDate(nodeInfo.getWorking_end());
                        pageInfo.setMoneyCount(nodeInfo.getCommission());
                        pageInfo.setGiveTime(nodeInfo.getCreatetime());

                        list.add(pageInfo);
                    }

                    mAdapter.refreshDatas(list);

                    mAdapter.notifyDataSetChanged();
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
        HttpMethods.getInstance().getRecommendWorkerDetail(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token,mWorkerCode);
    }

    private GridWorkerAdapter mGridAdapter;
    private void initData() {
        mToolbarTitle.setText("我推荐的工人");

        String workerTypes[] = {"水电工","木工"};
        mGridAdapter = new GridWorkerAdapter(_mActivity, Arrays.asList(workerTypes));
        mGridPopupWorkerType.setAdapter(mGridAdapter);
    }

    private void initDataTemp() {
        List<RecommendDetailInfo> list = new ArrayList<>();


        for (int i = 0; i < 10; i++) {
            RecommendDetailInfo workerInfo = new RecommendDetailInfo();

            if (i % 4 == 0) {
                workerInfo.setOrderNum("YJSZ201605091409021");
                workerInfo.setProjectType("施工放线");
                workerInfo.setBuildStartDate("2016-05-08");
                workerInfo.setBuildEndDate("2016-05-09");
                workerInfo.setMoneyCount("68.50");
                workerInfo.setGiveTime("2016-05-10 14:30");

            } else if (i % 4 == 1) {
                workerInfo.setOrderNum("YJSZ201605091409021");
                workerInfo.setProjectType("水电安装");
                workerInfo.setBuildStartDate("2016-05-08");
                workerInfo.setBuildEndDate("2016-05-09");
                workerInfo.setMoneyCount("36.00");
                workerInfo.setGiveTime("2016-05-10 14:30");

            } else if (i % 4 == 2) {
                workerInfo.setOrderNum("YJSZ201605091409021");
                workerInfo.setProjectType("家具定做");
                workerInfo.setBuildStartDate("2016-05-08");
                workerInfo.setBuildEndDate("2016-05-09");
                workerInfo.setMoneyCount("120.50");
                workerInfo.setGiveTime("2016-05-10 14:30");

            } else if (i % 4 == 3) {
                workerInfo.setOrderNum("YJSZ201605091409021");
                workerInfo.setProjectType("吊顶");
                workerInfo.setBuildStartDate("2016-05-08");
                workerInfo.setBuildEndDate("2016-05-09");
                workerInfo.setMoneyCount("100.00");
                workerInfo.setGiveTime("2016-05-10 14:30");

            }
            list.add(workerInfo);
        }
        mAdapter.refreshDatas(list);
    }
}
