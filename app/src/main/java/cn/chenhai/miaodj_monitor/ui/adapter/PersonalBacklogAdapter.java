package cn.chenhai.miaodj_monitor.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cn.chenhai.miaodj_monitor.ui.fragment.personal.PersonalBacklogPager1;
import cn.chenhai.miaodj_monitor.ui.fragment.personal.PersonalBacklogPager2;

/**
 * Created by ChenHai--霜华 on 2016/6/23. 15:06
 * 邮箱：248866527@qq.com
 */
public class PersonalBacklogAdapter extends FragmentPagerAdapter {
    String[] mTitles = new String[]{"待办事项", "消息通知"};
    private Context mContext;
    String mProjectCode;




    public PersonalBacklogAdapter(Activity context,FragmentManager fm, String projectCode) {
        super(fm);
        mContext = context;
        mProjectCode = projectCode;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return PersonalBacklogPager1.newInstance(0 , mProjectCode);
        } else {
            return PersonalBacklogPager2.newInstance(1 , mProjectCode);
        }
    }


    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

//        Drawable image;
//        if (position == 0) {
//            image = mContext.getResources().getDrawable(tabFirstImg[position]);
//        } else {
//            image = mContext.getResources().getDrawable(tabSecondImg[position]);
//        }
//
//        //image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
//        image.setBounds(0, 0, 39, 39);
//        //这里前面加的空格就是为图片显示
//        SpannableString sp = new SpannableString("  " + mTitles[position]);
//        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BASELINE);
//        sp.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        return sp;

        return mTitles[position];
    }
}
