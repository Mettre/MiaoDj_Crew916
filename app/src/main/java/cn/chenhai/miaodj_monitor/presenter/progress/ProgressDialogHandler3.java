package cn.chenhai.miaodj_monitor.presenter.progress;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import cn.chenhai.miaodj_monitor.R;

/**
 * Created by ChenHai--霜华 on 2016/7/13. 17:56
 * 邮箱：248866527@qq.com
 */
public class ProgressDialogHandler3 extends Handler {

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    //private SweetAlertDialog pd;
    private ProgressDialog pd;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler3(Context context, ProgressCancelListener mProgressCancelListener,
                                  boolean cancelable) {
        super();
        this.context = context;
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog(){
        if (pd == null) {
            final View pop = LayoutInflater.from(context).inflate(R.layout.view_progress, null);
            pd = new ProgressDialog(context);

            pd.setCancelable(cancelable);

            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if (!pd.isShowing()) {
                pd.show();
//                pd.getProgressHelper().setBarColor(0xff3ca0ec);
//                pd.getProgressHelper().setBarWidth(6);
//                pd.getProgressHelper().setCircleRadius(90);
//                pd.getProgressHelper().setRimColor(0xffff3300);
//                pd.getProgressHelper().setRimWidth(6);
//                pd.getProgressHelper().setSpinSpeed((float) 1.2);
            }
        }
    }

    private void dismissProgressDialog(){
        if (pd != null) {
            if(pd.isShowing()){
                pd.dismiss();
            }
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
