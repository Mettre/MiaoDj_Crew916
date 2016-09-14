package cn.chenhai.miaodj_monitor;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.lzy.okhttputils.OkHttpUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.config.AutoLayoutConifg;

import cn.chenhai.miaodj_monitor.service.AppException;
import cn.chenhai.miaodj_monitor.ui.module.preview.FiveGridView;
import cn.chenhai.miaodj_monitor.ui.view_custom.image.DemoDuiTangImageReSizer;
import cn.chenhai.miaodj_monitor.ui.view_custom.image.PtrImageLoadHandler;
import cn.chenhai.miaodj_monitor.utils.CollectUtils;
import cn.chenhai.miaodj_monitor.utils.ImageLoader2;
import cn.jpush.android.api.JPushInterface;
import in.srain.cube.Cube;
import in.srain.cube.image.ImageLoaderFactory;
import in.srain.cube.request.RequestCacheManager;
import in.srain.cube.util.CubeDebug;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by ChenHai--霜华 on 2016/5/23. 07:22
 * 邮箱：248866527@qq.com
 */
public class MyApplication extends MultiDexApplication {

    public static MyApplication instance;
    private static Context context;
    //private static DiskLruCacheHelper cacheHelper;
    //LeakCanary的使用
    private RefWatcher refWatcher;

    private static Handler mHandler;

    public MyApplication() {
        instance = this;
    }

    public static synchronized MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }
    public static Context getContext() {
        return context;
    }


    //LeakCanary的使用
    public static RefWatcher getRefWatcher(Context context) {
        MyApplication application = (MyApplication) context.getApplicationContext();
        return application.refWatcher;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        context = getApplicationContext();
        ImageLoader2.init(context);

        CollectUtils.initialize(this);
        CollectUtils.setDebug(true, "MiaoDongJia_Test");

        Fresco.initialize(this);

        //LeakCanary的使用
        refWatcher = LeakCanary.install(this);
        //registerUncaughtExceptionHandler();

        mHandler = new Handler();

        CubeDebug.DEBUG_IMAGE = true;
        PtrFrameLayout.DEBUG = true;
        ImageLoaderFactory.setDefaultImageReSizer(DemoDuiTangImageReSizer.getInstance());
        ImageLoaderFactory.setDefaultImageLoadHandler(new PtrImageLoadHandler());
        String dir = "request-cache";
        // ImageLoaderFactory.init(this);
        RequestCacheManager.init(this, dir, 1024 * 10, 1024 * 10);
        Cube.onCreate(this);

        //默认使用的高度是设备的可用高度，也就是不包括状态栏和底部的操作栏的，如果你希望拿设备的物理高度进行百分比化，则如下设置：
        AutoLayoutConifg.getInstance().useDeviceSize().init(this);

        //初始化腾讯Bugly Android
        CrashReport.initCrashReport(getApplicationContext(), "900042108", true);

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush

        //必须调用初始化
        OkHttpUtils.init(this);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS);                 //全局的写入超时时间

        FiveGridView.setImageLoader(new GlideImageLoader());
    }

    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    /** Glide 加载 */
    private class GlideImageLoader implements FiveGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Glide.with(context).load(url)//
                    .placeholder(R.drawable.ic_camera)//
                    .error(R.drawable.ic_error)//
                    .diskCacheStrategy(DiskCacheStrategy.ALL)//
                    .into(imageView);
        }
        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }

}
