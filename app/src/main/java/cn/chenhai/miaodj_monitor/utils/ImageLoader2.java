package cn.chenhai.miaodj_monitor.utils;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import cn.chenhai.miaodj_monitor.R;

public class ImageLoader2 {

    public static final String TAG = "ImageLoader";

    private static DisplayImageOptions options;

    private static DisplayImageOptions.Builder builder;

    private static Context applicationContext;

    public static void init(Context applicationContext) {

        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(applicationContext)
                .threadPoolSize(Thread.NORM_PRIORITY - 2)//线程池大小
                .denyCacheImageMultipleSizesInMemory()//否定高速缓存图像多大小在内存中
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//磁盘缓存文件名生成器MD5
                .tasksProcessingOrder(QueueProcessingType.LIFO)//后进先出法
                .writeDebugLogs()//编写调试日志
                .build();//建立
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(imageLoaderConfiguration);


        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.photo_loading)//加载图片时显示的默认图片
                .showImageForEmptyUri(R.drawable.img_fail)//显示空URI的图像
                .showImageOnFail(R.drawable.img_fail)//显示图像失败
                .cacheInMemory(true)//是否添加到内存缓存中
                .cacheOnDisk(true)//是否添加到硬盘缓存中
                .considerExifParams(true)
                .build();

        builder = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.photo_loading)//加载图片时显示的默认图片
                .showImageForEmptyUri(R.drawable.img_fail)//显示空URI的图像
                .showImageOnFail(R.drawable.img_fail)//显示图像失败
                .cacheInMemory(true)//是否添加到内存缓存中
                .cacheOnDisk(true)//是否添加到硬盘缓存中
                .considerExifParams(true);
        ImageLoader2.applicationContext = applicationContext;
    }

    public static DisplayImageOptions.Builder getBuilder() {
        return builder;
    }

    /**
     * 普通图片加载
     *
     * @param imageURL
     * @param imageView
     */
    public static void displayImage(String imageURL, ImageView imageView) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(urlTransformation(imageURL), imageView, options);
    }

    /**
     * 普通图片加载
     *
     * @param image
     * @param imageView
     */
    public static void displayImage(int image, ImageView imageView) {
        displayImage("drawable://" + image, imageView);
    }

    /**
     * 圆角图片加载
     *
     * @param image
     * @param imageView
     */
    public static void displayImage(int image, ImageView imageView, int cornerRadiusPixels) {
        displayImage("drawable://" + image, imageView, cornerRadiusPixels);
    }

    /**
     * 圆角图片加载
     *
     * @param imageURL           图片地址
     * @param imageView          控件
     * @param cornerRadiusPixels 圆角直径（控件宽或高）
     */
    public static void displayImage(String imageURL, ImageView imageView, int cornerRadiusPixels) {
        builder.displayer(new RoundedBitmapDisplayer(Math.round(applicationContext.getResources().getDisplayMetrics().density * cornerRadiusPixels)));
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(urlTransformation(imageURL), imageView, builder.build());
    }

    public static void loadImage(String imageURL, ImageLoadingListener listener) {
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImage(ImageLoader2.urlTransformation(imageURL), listener);
    }

    /**
     * 高斯模糊
     *
     * @param imageURL
     * @param imageView
     */
    public static void displayBlur(String imageURL, ImageView imageView) {
//        com.nostra13.universalimageloader.core.ImageLoader.getInstance().loadImage(urlTransformation(imageURL), new BlurImageLoadingListener(imageView));
    }

    /**
     * 地址转换
     * 把相对地址转换成实际地址
     *
     * @param imageURL
     * @return
     */
    public static String urlTransformation(String imageURL) {
        if (imageURL == null || imageURL.trim().isEmpty()) {
            return "drawable://" + R.drawable.default_error;
        } else if (imageURL.matches(NO_TRANSFORMATION)) {
            Log.e(TAG, "不转换 " + imageURL);
            return imageURL;
        } else {
            Log.e(TAG, "转换 " + imageURL);
            return imageURL;
        }
    }

    public static String FILE = "^[Ff][Ii][Ll][Ee]://[\\S]*";
    public static String HTTP = "^[Hh][Tt][Tt][Pp]://[\\S]*";
    public static String HTTPS = "^[Hh][Tt][Tt][Pp][Ss]://[\\S]*";
    public static String CONTENT = "^[Cc][Oo][Nn][Tt][Ee][Nn][Tt]://[\\S]*";
    public static String ASSETS = "^[Aa][Ss][Ee][Tt][Ss]://[\\S]*";
    public static String DRAWABLE = "^[Dd][Rr][Aa][Ww][Aa][Bb][Ll][Ee]://[\\S]*";

    public static String NO_TRANSFORMATION = "(" + FILE + ")|("
            + HTTP + ")|("
            + HTTPS + ")|("
            + CONTENT + ")|("
            + ASSETS + ")|("
            + FILE + ")|("
            + DRAWABLE + ")";
}
