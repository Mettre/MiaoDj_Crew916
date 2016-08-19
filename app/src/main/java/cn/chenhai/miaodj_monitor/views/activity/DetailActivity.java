package cn.chenhai.miaodj_monitor.views.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.chenhai.miaodj_monitor.AppManager;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.theme.Theme;
import cn.chenhai.miaodj_monitor.utils.PreUtils;
import cn.chenhai.miaodj_monitor.views.BaseActivity;
import cn.chenhai.miaodj_monitor.views.BaseActivity_Swip;
import cn.chenhai.miaodj_monitor.views.fragment.detail.DetailAgreeFragment;
import cn.chenhai.miaodj_monitor.views.fragment.detail.DetailBuildDiaryFragment;
import cn.chenhai.miaodj_monitor.views.fragment.detail.DetailIndexFragment;
import cn.chenhai.miaodj_monitor.views.fragment.detail.DetailPointFragment;
import cn.chenhai.miaodj_monitor.views.fragment.detail.DetailPointProgressFragment;
import cn.chenhai.miaodj_monitor.views.fragment.detail.DetailSelectionListFragment;
import cn.chenhai.miaodj_monitor.views.fragment.detail.DetailStartFragment;
import cn.chenhai.miaodj_monitor.views.fragment.home.HomeFragment;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by ChenHai--霜华 on 2016/6/7. 09:36
 * 邮箱：248866527@qq.com
 */
public class DetailActivity extends BaseActivity {

    public static final String TAG = DetailActivity.class.getSimpleName();

    private String mProjectCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

//        if (savedInstanceState == null) {
//            start(HomeFragment.newInstance());
//        }

        String fragmentName = "";

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mProjectCode = bundle.getString("ProjectCode");
            fragmentName = bundle.getString("FragmentName");
        }

        initView();

        if (savedInstanceState == null) {
            if(TextUtils.equals(fragmentName,"DetailAgreeFragment")) {
                loadRootFragment(R.id.detail_fl_container, DetailAgreeFragment.newInstance(mProjectCode));
            }
            else if(TextUtils.equals(fragmentName,"DetailIndexFragment")) {
                loadRootFragment(R.id.detail_fl_container, DetailIndexFragment.newInstance(mProjectCode));
            }
            else if(TextUtils.equals(fragmentName,"DetailStartFragment")) {
                loadRootFragment(R.id.detail_fl_container, DetailStartFragment.newInstance(mProjectCode));
            }
            else if(TextUtils.equals(fragmentName,"DetailBuildDiaryFragment")) {
                loadRootFragment(R.id.detail_fl_container, DetailBuildDiaryFragment.newInstance(mProjectCode));
            }
            else if(TextUtils.equals(fragmentName,"DetailPointFragment")) {
                loadRootFragment(R.id.detail_fl_container, DetailPointFragment.newInstance(mProjectCode));
            }
            else if(TextUtils.equals(fragmentName,"DetailPointProgressFragment")) {
                String pointID = "";
                String totalCount = "0";
                if (bundle != null) {
                    pointID = bundle.getString("pointID");
                    totalCount = bundle.getString("totalCount");
                }
                loadRootFragment(R.id.detail_fl_container, DetailPointProgressFragment.newInstance(mProjectCode,pointID,Integer.valueOf(totalCount)));
            }
//            else if(TextUtils.equals(fragmentName,"DetailSelectionListFragment")) {
//                loadRootFragment(R.id.detail_fl_container, DetailSelectionListFragment.newInstance(mProjectCode));
//            }

            else {
                onBackPressed();
            }
        }

        //getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_LEFT);
    }

    private void initView() {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置默认Fragment动画  默认竖向(和安卓5.0以上的动画相同)
        //return super.onCreateFragmentAnimator();
        // 设置横向(和安卓4.x动画相同)
        return new DefaultHorizontalAnimator();
        // 设置自定义动画
//        return new FragmentAnimator(enter,exit,popEnter,popExit);
    }


    protected String getName() {
        return BaseActivity.class.getName();
    }

}
