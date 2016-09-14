package cn.chenhai.miaodj_monitor.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by jiejp on 2016/7/30.
 */
public class PhotoPagerAdapter extends PagerAdapter {
    private Context context;
    private List<View> viewList;
    private ArrayList<String> pictures;


    public PhotoPagerAdapter(Context context, List<View> viewList, ArrayList<String> pictures) {
        this.context = context;
        this.viewList = viewList;
        this.pictures=pictures;
    }


    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(viewList.get(position));//删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        container.addView(viewList.get(position));//添加页卡


        ((PhotoView)viewList.get(position)).setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                ((Activity)context).finish();
            }


        });

        return viewList.get(position);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
