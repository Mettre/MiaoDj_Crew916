package cn.chenhai.miaodj_monitor.views.fragment.personal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.flyco.tablayout.utils.UnreadMsgUtils;
import com.flyco.tablayout.widget.MsgView;

import java.util.ArrayList;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.adapter.PersonalBacklogAdapter;
import cn.chenhai.miaodj_monitor.model.TabEntity;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment_Swip;

/**
 * Created by ChenHai--霜华 on 2016/6/23. 11:22
 * 邮箱：248866527@qq.com
 */
public class PersonalBacklogFragment extends BaseBackFragment_Swip implements ViewPager.OnPageChangeListener{
    private static final String ARG_ITEM = "arg_item";
    private String mProjectCode;

    String[] mTitles = new String[]{"待办事项", "消息通知"};
    private int[] tabSelectImg = new int[]{R.drawable.ic_head1_1, R.drawable.ic_head3_1};
    private int[] tabUnselectImg = new int[]{R.drawable.ic_head1_2, R.drawable.ic_head3_2};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ViewPager mViewPager;
    //private TabLayout mTabLayout;
    private CommonTabLayout mTabLayout;
    private PersonalBacklogAdapter mAdapter;

    public static PersonalBacklogFragment newInstance(String projectCode) {
        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        PersonalBacklogFragment fragment = new PersonalBacklogFragment();
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
        View view = inflater.inflate(R.layout.fragment_personal_backlog, container, false);

        initView(view);
        initData();
        //return view;
        return attachToSwipeBack(view);
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
        mAdapter = new PersonalBacklogAdapter(_mActivity,getChildFragmentManager(),mProjectCode);


        initToolbarNav(mToolbar);



//        TabLayout.Tab tab1 = mTabLayout.newTab();
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.confirm_no));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.underway));
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        //mTabLayout.setupWithViewPager(mViewPager);
        //mTabLayout.setViewPager(mViewPager);

        setCommonTabLayout();

        //两位数
//        mTabLayout.showMsg(0, 55);
//        mTabLayout.setMsgMargin(0, -5, 5);

//        //三位数
//        mTabLayout.showMsg(1, 100);
//        mTabLayout.setMsgMargin(1, -5, 5);

//        //设置未读消息红点
//        mTabLayout.showDot(1);
//        MsgView rtv_2_2 = mTabLayout.getMsgView(1);
//        if (rtv_2_2 != null) {
//            UnreadMsgUtils.setSize(rtv_2_2, dp2px(4.5f));
//        }
//        mTabLayout.setMsgMargin(1, 1, 0);

//        //设置未读消息背景
//        mTabLayout.showMsg(3, 5);
//        mTabLayout.setMsgMargin(3, 0, 5);
//        MsgView rtv_2_3 = mTabLayout.getMsgView(3);
//        if (rtv_2_3 != null) {
//            rtv_2_3.setBackgroundColor(Color.parseColor("#6D8FB0"));
//        }
    }

    public void showNewDot(int position){
        mTabLayout.showDot(position);
        MsgView rtv_0_2 = mTabLayout.getMsgView(position);
        if (rtv_0_2 != null) {
            UnreadMsgUtils.setSize(rtv_0_2, dp2px(4.5f));
        }
        mTabLayout.setMsgMargin(position, 1, 0);
    }
    public void hideNewDot(int position){
        mTabLayout.hideMsg(position);
    }

    protected int dp2px(float dp) {
        final float scale = _mActivity.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    private void initData() {
        mToolbarTitle.setText("消息中心");
    }


    public PersonalBacklogAdapter getAdapter(){
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
