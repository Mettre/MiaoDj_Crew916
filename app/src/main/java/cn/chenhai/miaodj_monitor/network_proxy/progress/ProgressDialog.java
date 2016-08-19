package cn.chenhai.miaodj_monitor.network_proxy.progress;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.pnikosis.materialishprogress.ProgressWheel;

import cn.chenhai.miaodj_monitor.R;

/**
 * Created by ChenHai--霜华 on 2016/7/13. 19:42
 * 邮箱：248866527@qq.com
 */
public class ProgressDialog extends Dialog {

    View customView;

    Context context;
    private ProgressWheel mProgressWheel;

    String strSex = "";

    public ProgressDialog(Context context) {
        super(context, R.style.customDialog);
        this.context = context;
    }


    /**
     * 展示
     *
     */
    public void show() {
        super.show();
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        customView = mInflater.inflate(R.layout.view_progress, null);
        setContentView(customView);

        // 设置他的宽度

//        WindowManager wm = (WindowManager) context
//                .getSystemService(Context.WINDOW_SERVICE);
//        DisplayMetrics dm = new DisplayMetrics();
//        wm.getDefaultDisplay().getMetrics(dm);

        //int screenWidth = dm.widthPixels;

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.alpha = 0.9f;
        //lp.width = screenWidth * 4 / 5;
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);

        //setCancelable(false);
        setCanceledOnTouchOutside(false);

//        ButterKnife.bind(this);


        mProgressWheel = (ProgressWheel) customView.findViewById(R.id.progress_wheel);
        mProgressWheel.setBarWidth(8);
        mProgressWheel.setBarColor(0xff3ca0ec);

    }

    public View getRootView() {
        return customView;
    }


    public interface SubmitDoing {
        void submitDoing(String strSex);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
            return false;
        }
        return super.onKeyDown(keyCode, event);

    }
}
