package cn.chenhai.miaodj_monitor.views.activity;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;


import com.tbruyelle.rxpermissions.RxPermissions;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.helper.UIHelper;
import cn.chenhai.miaodj_monitor.views.viewpagerindicator.CirclePageIndicator;
import cn.chenhai.miaodj_monitor.utils.CollectUtils;
import cn.chenhai.miaodj_monitor.utils.SharedPreferencesUtil;

/**
 * Created by ChenHai--霜华 on 2016/5/24. 13:47
 * 邮箱：248866527@qq.com
 */
public class LaunchActivity extends FragmentActivity {

    private Button btnHome;
    private CirclePageIndicator indicator;
    private ViewPager pager;
    private GalleryPagerAdapter adapter;
    private int[] images = {
            R.drawable.newer01,
            R.drawable.newer02
//            R.drawable.newer03,
//            R.drawable.newer04
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_launch);

        boolean firstTimeUse = PreferencesUtils.getBoolean(LaunchActivity.this,"first-time-use", true);
        if(firstTimeUse) {
            initGuideGallery();
        } else {
            //initGuideGallery();
            initLaunchLogo();
        }

    }

    private void initLaunchLogo() {
        ImageView guideImage = (ImageView) findViewById(R.id.guideImage);
        guideImage.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RxPermissions.getInstance(LaunchActivity.this)
                        .request(Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) {
                                //startActivity(new Intent(LaunchActivity.this, MainActivity.class));
                                //UIHelper.showHome(LaunchActivity.this);
                                UIHelper.showLogin(LaunchActivity.this);
                                finish();
                            } else {
                                CollectUtils.Toast("请同意我们的权限，方可提供服务");
                            }
                        });
            }
        }, 1000);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                UIHelper.showHome(LaunchActivity.this);
//            }
//        }, 500);
    }


    private void initGuideGallery() {
        final Animation fadeIn= AnimationUtils.loadAnimation(this, R.anim.fadein);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferencesUtil.getInstance().putBoolean("first-time-use", false);
                //UIHelper.showHome(LaunchActivity.this);
                UIHelper.showLogin(LaunchActivity.this);
                finish();
            }
        });

        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setVisibility(View.VISIBLE);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setVisibility(View.VISIBLE);

        adapter = new GalleryPagerAdapter();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == images.length - 1) {
                    btnHome.setVisibility(View.VISIBLE);
                    btnHome.startAnimation(fadeIn);
                } else {
                    btnHome.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(LaunchActivity.this);
            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
            item.setImageResource(images[position]);
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

}
