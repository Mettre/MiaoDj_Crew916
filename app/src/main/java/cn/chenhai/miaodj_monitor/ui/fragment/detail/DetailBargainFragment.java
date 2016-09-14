package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.BargainEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.ui.activity.PdfActivity;
import cn.chenhai.miaodj_monitor.ui.adapter.BargainAdapter;
import cn.chenhai.miaodj_monitor.ui.base.BaseFragment;

/**
 * <合同信息>
 * <功能详细描述>
 *
 * @author Allen
 * @version [版本号, 2016/8/30 16:51]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DetailBargainFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    private BargainAdapter mAdapter;

    private static final String ARG_FROM = "arg_from";

    private String mBargain_code;

    private SubscriberOnSuccessListener mOnSuccessListener;

    private List<BargainEntity.BargainBean> publicDataResults = new ArrayList<BargainEntity.BargainBean>();

    public static DetailBargainFragment newInstance(String bargain_code) {
        Bundle args = new Bundle();
        args.putString("bargain_code", bargain_code);

        DetailBargainFragment fragment = new DetailBargainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mBargain_code = args.getString("bargain_code");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_bargain, container, false);

        initView(view);

        requestData();

        return view;
    }


    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fdb_recyclerView);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fdb_swipeLayout);

        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));


        mOnSuccessListener = new SubscriberOnSuccessListener<HttpResult<BargainEntity>>() {
            @Override
            public void onSuccess(HttpResult<BargainEntity> result) {
                if (publicDataResults == null) {
                    publicDataResults = new ArrayList<>();
                }

                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {

                    List<BargainEntity.BargainBean> projects = result.getInfo().getBargain();

                    publicDataResults.addAll(projects);
                    initAdapter();
                }

                overRefresh();
            }

            @Override
            public void onCompleted() {
                overRefresh();
            }

            @Override
            public void onError() {
                if (publicDataResults == null) {
                    publicDataResults = new ArrayList<>();
                }
                initAdapter();
                overRefresh();

            }
        };

    }


    private void initAdapter() {

        if (mAdapter == null) {
            mAdapter = new BargainAdapter(publicDataResults);
            View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
            mAdapter.setEmptyView(emptyView);
            mRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    PdfActivity.startActivity(getActivity(), publicDataResults.get(i).getCode());
                }
            });
        } else {
            mAdapter.updateDatas(publicDataResults);
        }


    }

    /**
     * 请求网络数据
     */
    void requestData() {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().getBargain(new ProgressSubscriber(mOnSuccessListener, _mActivity), user_code, access_token, mBargain_code);
    }

    @Override
    public void onRefresh() {
        publicDataResults = null;
        requestData();
    }

    private void overRefresh() {
        if (mRefreshLayout == null) {
            return;
        }
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }
}
