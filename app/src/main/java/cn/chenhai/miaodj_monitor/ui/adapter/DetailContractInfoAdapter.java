package cn.chenhai.miaodj_monitor.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.chenhai.miaodj_monitor.ui.fragment.detail.DetailBargainFragment;
import cn.chenhai.miaodj_monitor.ui.fragment.detail.DetailBargainPayFragment;

/**
 * Created by ChenHai--霜华 on 2016/6/24. 15:33
 * 邮箱：248866527@qq.com
 */
public class DetailContractInfoAdapter extends FragmentPagerAdapter {
    String[] mTitles = new String[]{"合同记录", "付款信息"};
    String mProjectCode = "";

    public DetailContractInfoAdapter(FragmentManager fm, String mBargain_code) {
        super(fm);
        mProjectCode = mBargain_code;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return DetailBargainFragment.newInstance(mProjectCode);
        } else {
            return DetailBargainPayFragment.newInstance(mProjectCode);
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
