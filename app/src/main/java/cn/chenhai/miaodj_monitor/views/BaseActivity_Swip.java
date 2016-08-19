package cn.chenhai.miaodj_monitor.views;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import cn.chenhai.miaodj_monitor.AppManager;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.theme.Theme;
import cn.chenhai.miaodj_monitor.utils.PreUtils;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SwipeBackLayout;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Created by ChenHai--霜华 on 2016/7/13. 10:52
 * 邮箱：248866527@qq.com
 */
public abstract class BaseActivity_Swip extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 添加Activity到堆栈
        //AppManager.getAppManager().addActivity(this);

        onPreCreate();
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
            //tintManager.setStatusBarTintColor(0xffffff);//通知栏所需颜色
        }

        getSwipeBackLayout().setEdgeOrientation(SwipeBackLayout.EDGE_LEFT);
    }

    protected String getCloseWarning() {
        return getString(R.string.app_exit_tip);
    }

    /**
     * 限制SwipeBack的条件,默认栈内Fragment数 <= 1时 , 优先滑动退出Activity , 而不是Fragment
     *
     * @return true: Activity可以滑动退出, 并且总是优先;  false: Activity不允许滑动退出
     */
    @Override
    public boolean swipeBackPriority() {
        return super.swipeBackPriority();
    }

    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
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
