package cn.chenhai.miaodj_monitor.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.chenhai.miaodj_monitor.ui.fragment.detail.DetailBuildDiaryPager1;
import cn.chenhai.miaodj_monitor.ui.fragment.detail.DetailBuildDiaryPager2;

/**
 * Created by ChenHai--霜华 on 2016/6/24. 15:33
 * 邮箱：248866527@qq.com
 */
public class DetailContractInfoAdapter extends FragmentPagerAdapter {
    String[] mTitles = new String[]{"合同记录", "付款信息"};
    String mProjectCode = "";

    public DetailContractInfoAdapter(FragmentManager fm , String projectCode) {
        super(fm);
        mProjectCode = projectCode;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return DetailBuildDiaryPager1.newInstance(0,mProjectCode);
        } else {
            return DetailBuildDiaryPager2.newInstance(1,mProjectCode);
        }
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
