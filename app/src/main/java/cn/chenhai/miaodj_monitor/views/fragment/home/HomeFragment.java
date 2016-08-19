package cn.chenhai.miaodj_monitor.views.fragment.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.adapter.HomeFragmentAdapter;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.Account;
import cn.chenhai.miaodj_monitor.model.entity.MyProjectsEntity;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.views.activity.MainActivity;
import cn.chenhai.miaodj_monitor.views.activity.PersonalActivity;
import cn.chenhai.miaodj_monitor.views.base.BaseMainFragment;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator;

/**
 * Created by ChenHai--霜华 on 2016/5/28. 3:10
 * 邮箱：248866527@qq.com
 */
public class HomeFragment extends BaseMainFragment implements ViewPager.OnPageChangeListener,Toolbar.OnMenuItemClickListener{
    private static final String TAG = "FragmentLib";
    private static final int REQ_START_DETAIL_FOR_RESULT = 1099;
    private static final int REQ_START_PERSONAL_FOR_RESULT = 1100;

    private static final int REQ_ZXING_LIBRARY_FOR_RESULT = 1199;

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    //private TabLayout mTabLayout;
    SlidingTabLayout mTabLayout_2;
    private HomeFragmentAdapter mAdapter;

    private SubscriberOnSuccessListener mOnSuccessListener;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //mTabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        //关闭预加载，默认一次只加载一个Fragment(注：setOffscreenPageLimit(0)设置成0的话即是不加载，但是默认的support-v4包会把默认值改为1的，
        // 也就是至少默认会加载左右一个Fragment，要想完全不加载，只有修改support-v4的源码，然后重新打jar包调用了)
        mViewPager.setOffscreenPageLimit(4);
        mAdapter = new HomeFragmentAdapter(getChildFragmentManager());

        TextView tvTitle = (TextView) view.findViewById(R.id.toolbar_title);
        tvTitle.setText("我的工地");
        initToolbarNav(mToolbar,true);
        mToolbar.inflateMenu(R.menu.news);
        mToolbar.setOnMenuItemClickListener(this);

        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);

//        TabLayout.Tab tab1 = mTabLayout.newTab();
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.confirm_no));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.underway));
//        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.complete));
//
//        mTabLayout.setupWithViewPager(mViewPager);

        /**自定义部分属性*/
        mTabLayout_2 = (SlidingTabLayout) view.findViewById(R.id.tl_2);
        mTabLayout_2.setViewPager(mViewPager);

//        tabLayout_2.showDot(4);
//        tabLayout_2.showMsg(5, 5);
//        tabLayout_2.setMsgMargin(5, 0, 10);

        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    protected void initToolbarNav(Toolbar toolbar, boolean isHome) {
        //toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationIcon(R.drawable.ic_menu_scan_32);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(_mActivity, CaptureActivity.class);
                startActivityForResult(intent, REQ_ZXING_LIBRARY_FOR_RESULT);
            }
        });

//        if(!isHome) {
//            initToolbarMenu(toolbar);
//        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_hierarchy:
                _mActivity.showFragmentStackHierarchyView();
                _mActivity.logFragmentStackHierarchy(TAG);
                break;
            case R.id.action_news:
                Bundle bundle = new Bundle();
                bundle.putString("FragmentName", "PersonalBacklogFragment");
                bundle.putString("ProjectCode","测试单号！");
                Intent intent = new Intent(_mActivity, PersonalActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQ_START_PERSONAL_FOR_RESULT);
                break;
        }
        return true;
    }

    public ViewPager getViewPager(){
        return mViewPager;
    }
    public SlidingTabLayout getTabLayout(){
        return mTabLayout_2;
    }
    public HomeFragmentAdapter getAdapter(){
        return mAdapter;
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
        if(position == 0){
            ((MainActivity)getActivity()).getResideLayout().setIsNeedSlide(true);
        }else if(position == 1){
            //暂时禁止residelayout侧滑
            ((MainActivity)getActivity()).getResideLayout().setIsNeedSlide(false);
        }else {
            //暂时禁止residelayout侧滑
            ((MainActivity)getActivity()).getResideLayout().setIsNeedSlide(false);
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQ_START_DETAIL_FOR_RESULT){
            if (resultCode == RESULT_OK) {

            }
        }
        if(requestCode == REQ_ZXING_LIBRARY_FOR_RESULT){
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(_mActivity, "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(_mActivity, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
