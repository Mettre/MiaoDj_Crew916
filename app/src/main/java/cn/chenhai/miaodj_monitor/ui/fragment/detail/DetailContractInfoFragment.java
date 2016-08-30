package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.TabEntity;
import cn.chenhai.miaodj_monitor.ui.adapter.DetailContractInfoAdapter;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment;

/**
 * 合同及付款信息
 *
 * Created by ChenHai--霜华 on 2016/6/24. 15:20
 * 邮箱：248866527@qq.com
 */
public class DetailContractInfoFragment extends BaseBackFragment implements ViewPager.OnPageChangeListener{
    private static final String ARG_ITEM = "arg_item";
    private String mProjectCode;

    String[] mTitles = new String[]{"合同记录", "付款信息"};
    private int[] tabSelectImg = new int[]{R.color.white, R.color.white};
    private int[] tabUnselectImg = new int[]{R.color.white, R.color.white};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ViewPager mViewPager;
    private CommonTabLayout mTabLayout;
    private DetailContractInfoAdapter mAdapter;

    public static DetailContractInfoFragment newInstance(String projectCode) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        DetailContractInfoFragment fragment = new DetailContractInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_contract_info, container, false);

        initView(view);
        initData();
        return view;
        //return attachToSwipeBack(view);
    }

    private void initView(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        mTabLayout = (CommonTabLayout) view.findViewById(R.id.tab_layout);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], tabSelectImg[i], tabUnselectImg[i]));
        }

        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        //关闭预加载，默认一次只加载一个Fragment(注：setOffscreenPageLimit(0)设置成0的话即是不加载，但是默认的support-v4包会把默认值改为1的，
        // 也就是至少默认会加载左右一个Fragment，要想完全不加载，只有修改support-v4的源码，然后重新打jar包调用了)
        mViewPager.setOffscreenPageLimit(1);
        mAdapter = new DetailContractInfoAdapter(getChildFragmentManager() , mProjectCode);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

        initToolbarNav(mToolbar);

        /**自定义部分属性*/
        setCommonTabLayout();
    }

    private void initData() {
        mToolbarTitle.setText("合同及付款信息");
    }


    public DetailContractInfoAdapter getAdapter(){
        return mAdapter;
    }


    private void setCommonTabLayout() {
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
                if (position == 0) {

                }
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //mViewPager.setCurrentItem(1);
    }


    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onPause() {
        super.onPause();

    }
    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mViewPager.removeOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }
    @Override
    public void onPageSelected(int position) {

    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
