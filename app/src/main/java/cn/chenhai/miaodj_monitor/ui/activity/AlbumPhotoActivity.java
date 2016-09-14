package cn.chenhai.miaodj_monitor.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.adapter.PhotoPagerAdapter;
import cn.chenhai.miaodj_monitor.utils.ImageLoader2;
import uk.co.senab.photoview.PhotoView;


/**
 * Created by jiejp on 2016/7/30.
 * 照片轮播
 */
public class AlbumPhotoActivity extends Activity {

    private List<View> viewList;
    private ViewPager viewPager;
    private ArrayList<String> pictures;//图片Uri
    private int position;//起始照片位置


    public static void startActivity(Context context, ArrayList<String> pictures,int position) {
        Intent intent = new Intent(context, AlbumPhotoActivity.class);
        intent.putStringArrayListExtra("pictures", pictures);
        intent.putExtra("position", position);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_photo);
        viewPager = new ViewPager(this);
        viewPager = (ViewPager) findViewById(R.id.activity_album_photo_viewPager);

        pictures = getIntent().getStringArrayListExtra("pictures");
        position = getIntent().getIntExtra("position", 0);
        if (position == -1) {
            finish();
        }

        viewList = new ArrayList<>();
        for (int i = 0; i < pictures.size(); i++) {
            PhotoView imageView = new PhotoView(this);
            ImageLoader2.displayImage(pictures.get(i), imageView);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewList.add(imageView);
        }

        viewPager.setFocusable(true);
        viewPager.setAdapter(new PhotoPagerAdapter(this, viewList,pictures));
        viewPager.setCurrentItem(position);

    }

}
