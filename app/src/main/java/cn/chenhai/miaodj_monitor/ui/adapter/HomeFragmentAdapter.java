package cn.chenhai.miaodj_monitor.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.chenhai.miaodj_monitor.ui.fragment.home.childpager.FirstPagerFragment;
import cn.chenhai.miaodj_monitor.ui.fragment.home.childpager.OtherPagerFragment;
import cn.chenhai.miaodj_monitor.ui.fragment.home.childpager.SecondPagerFragment;
import cn.chenhai.miaodj_monitor.ui.fragment.home.childpager.ThirdPagerFragment;

/**
 * Created by ChenHai--霜华 on 2016/5/28. 3:14
 * 邮箱：248866527@qq.com
 */
public class HomeFragmentAdapter extends FragmentPagerAdapter {
    String[] mTitles = new String[]{"待确认", "进行中", "已完成","已拒绝"};

    public HomeFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return FirstPagerFragment.newInstance(0);
        } else if (position == 1) {
            return SecondPagerFragment.newInstance(1);
        } else if (position == 2) {
            return ThirdPagerFragment.newInstance(2);
        }else {
            return OtherPagerFragment.newInstance(3);
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
