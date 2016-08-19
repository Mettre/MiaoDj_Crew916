package cn.chenhai.miaodj_monitor.views;

import android.annotation.TargetApi;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.squareup.leakcanary.RefWatcher;

import cn.chenhai.miaodj_monitor.MyApplication;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.theme.Theme;
import cn.chenhai.miaodj_monitor.utils.PreUtils;
import cn.chenhai.miaodj_monitor.AppManager;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by ChenHai--霜华 on 2016/5/31. 07:31
 * 邮箱：248866527@qq.com
 */
public abstract class BaseActivity extends SupportActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        //AppManager.getAppManager().addActivity(this);

        onPreCreate();

//        //在自己的应用初始Activity中加入如下两行代码
//        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
//        refWatcher.watch(this);

        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);

            //获取某个属性attr内的颜色值
            TypedValue typedValue = new TypedValue();
            getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
//            int[] attribute = new int[] { android.R.attr.colorPrimary };
//            TypedArray array = obtainStyledAttributes(typedValue.resourceId, attribute);
//            array.recycle();//TypedArray.recycle()方法回收资源。
            final int color = typedValue.resourceId;

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            //tintManager.setStatusBarTintResource(R.color.status_bar_bg);//通知栏所需颜色
            tintManager.setStatusBarTintResource(color);//通知栏所需颜色
            //tintManager.setStatusBarTintColor(0x11ff0000);//通知栏所需颜色
        }



//        // 修改状态栏颜色，4.4+生效
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }

        /*        //Android5.0全透明状态栏效果
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }*/

    }

    protected String getCloseWarning() {
        return getString(R.string.app_exit_tip);
    }

    @Override
    public void onBackPressedSupport() {
        super.onBackPressedSupport();
    }


    /**------------------------------------------------------------------------*/

    @TargetApi(19)
    protected void setTranslucentStatus() {
        Window window = getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // Translucent navigation bar
//        window.setFlags(
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
//                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
    /**---------------------------------------------------------------------------------*/
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    private void onPreCreate() {
        Theme theme = PreUtils.getCurrentTheme(this);
        switch (theme) {
            case Blue:
                setTheme(R.style.BlueTheme);
                break;
            case Red:
                setTheme(R.style.RedTheme);
                break;
            case Green:
                setTheme(R.style.GreenTheme);
                break;
            case Orange:
                setTheme(R.style.OrangeTheme);
                break;
            case Black:
                setTheme(R.style.BlackTheme);
                break;
        }

    }

    protected String getName() {
        return BaseActivity.class.getName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消请求
        //RequestManager.cancelRequest(getName());
        // 结束Activity从堆栈中移除
        //AppManager.getAppManager().finishActivity(this);
    }

}
