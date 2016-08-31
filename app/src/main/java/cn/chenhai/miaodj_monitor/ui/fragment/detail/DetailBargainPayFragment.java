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

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.bean.PayInfoBean;
import cn.chenhai.miaodj_monitor.model.entity.BargainPayEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.ui.adapter.BargainPayAdapter;
import cn.chenhai.miaodj_monitor.ui.base.BaseFragment;

/**
 * <付款信息>
 * <功能详细描述>
 *
 * @author Allen
 * @version [版本号, 2016/8/30 16:51]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DetailBargainPayFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    private BargainPayAdapter mAdapter;

    private static final String ARG_FROM = "arg_from";

    private String mBargain_code;

    private SubscriberOnSuccessListener mOnSuccessListener;

    private List<PayInfoBean> publicDataResults = new ArrayList<PayInfoBean>();

    public static DetailBargainPayFragment newInstance(String bargain_code) {
        Bundle args = new Bundle();
        args.putString("bargain_code", bargain_code);

        DetailBargainPayFragment fragment = new DetailBargainPayFragment();
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
        View view = inflater.inflate(R.layout.fragment_detail_bargain_pay, container, false);

        initView(view);

        requestData();

        return view;
    }


    private void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fdbp_recyclerView);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fdbp_swipeLayout);

        mRefreshLayout.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(_mActivity));


        mOnSuccessListener = new SubscriberOnSuccessListener<HttpResult<BargainPayEntity>>() {
            @Override
            public void onSuccess(HttpResult<BargainPayEntity> result) {
                if (publicDataResults == null) {
                    publicDataResults = new ArrayList<>();
                }

                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {

                    publicDataResults.add(new PayInfoBean(true, "已支付"));
                    List<BargainPayEntity.BargainPayBean> mHasPayList = new ArrayList<>();
                    List<BargainPayEntity.BargainPayBean> mNoPayList = new ArrayList<>();
                    for (BargainPayEntity.BargainPayBean mData : result.getInfo().getBargain_pay()) {
                        if (mData.getStatus() == 8) {
                            mHasPayList.add(mData);
                        } else if (mData.getStatus() == 1) {
                            mNoPayList.add(mData);
                        }
                    }

                    for (BargainPayEntity.BargainPayBean mData : mHasPayList) {
                        publicDataResults.add(new PayInfoBean(mData));
                    }

                    publicDataResults.add(new PayInfoBean(true, "未支付"));
                    for (BargainPayEntity.BargainPayBean mData : mNoPayList) {
                        publicDataResults.add(new PayInfoBean(mData));
                    }

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
            mAdapter = new BargainPayAdapter(getActivity(),publicDataResults);
            View emptyView = getActivity().getLayoutInflater().inflate(R.layout.empty_view, (ViewGroup) mRecyclerView.getParent(), false);
            mAdapter.setEmptyView(emptyView);
            mRecyclerView.setAdapter(mAdapter);
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
        HttpMethods.getInstance().getBargainPay(new ProgressSubscriber(mOnSuccessListener, _mActivity), user_code, access_token, mBargain_code);
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