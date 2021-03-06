package cn.chenhai.miaodj_monitor.presenter.subscribers;

import android.app.Activity;
import android.util.Log;

import com.google.gson.stream.MalformedJsonException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import cn.chenhai.miaodj_monitor.presenter.progress.ProgressCancelListener;
import cn.chenhai.miaodj_monitor.presenter.progress.ProgressDialogHandler;
import cn.chenhai.miaodj_monitor.presenter.progress.ProgressDialogHandler3;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.TimeUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 * 用于在Http请求开始时，自动显示一个ProgressDialog
 * 在Http请求结束时，关闭ProgressDialog
 * 调用者自己对请求数据进行处理
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private SubscriberOnSuccessListener mSubscriberOnSuccessListener;
    private ProgressDialogHandler3 mProgressDialogHandler;

    private final Activity context;

    private long timeMillStart, timeMillEnd;

    private SweetAlertDialog mDialog;

    public ProgressSubscriber(SubscriberOnSuccessListener mSubscriberOnSuccessListener, Activity context) {
        this.mSubscriberOnSuccessListener = mSubscriberOnSuccessListener;
        this.context = context;
//        mProgressDialogHandler = new ProgressDialogHandler2(context, this, true);
        //this.context = context.getApplicationContext();
        mProgressDialogHandler = new ProgressDialogHandler3(context, this, true);
    }

    private void showProgressDialog() {
        if (!context.isFinishing()) {
            if (mProgressDialogHandler != null) {
                mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
                timeMillStart = TimeUtils.getCurrentTimeInLong();
            }
        }
    }

    private void dismissProgressDialog() {
        if (!context.isFinishing()) {
            if (mProgressDialogHandler != null) {
                mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
                mProgressDialogHandler = null;
//                timeMillEnd = TimeUtils.getCurrentTimeInLong();
//                if ((timeMillEnd - timeMillStart) > 800 || (timeMillEnd - timeMillStart) < 0) {
//                    mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
//                    mProgressDialogHandler = null;
//                } else {
//                    TimerTask task = new TimerTask() {
//                        public void run() {
//                            if (mProgressDialogHandler != null) {
//                                mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
//                                mProgressDialogHandler = null;
//                            }
//                        }
//                    };
//                    Timer timer = new Timer();
//                    timer.schedule(task, 800 - (timeMillEnd - timeMillStart));
//                }

            }
        }
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */
    @Override
    public void onStart() {
        showProgressDialog();
    }

    /**
     * 完成，隐藏ProgressDialog
     */
    @Override
    public void onCompleted() {
        dismissProgressDialog();
        //Toast.makeText(context, "网络操作成功", Toast.LENGTH_SHORT).show();
        mSubscriberOnSuccessListener.onCompleted();
    }

    /**
     * 对错误进行统一处理
     * 隐藏ProgressDialog
     *
     * @param e
     */
    @Override
    public void onError(Throwable e) {
        if (e instanceof SocketTimeoutException) {
            showDialog(context, "错误！", "网络超时，请检查您的网络状态", "知道了");
        } else if (e instanceof ConnectException) {
            showDialog(context, "错误！", "网络中断，请检查您的网络状态", "知道了");
        } else if (e instanceof UnknownHostException || e instanceof MalformedJsonException) {
            showDialog(context, "错误！", "网络错误，请检查您的网络状态", "知道了");
        } else {
            Log.d("ProgressSubscriber 测试", "错误---:" + e.getMessage());
            showDialog(context, "错误！", e.getMessage(), "知道了");
        }

        dismissProgressDialog();
        mSubscriberOnSuccessListener.onError();
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     *
     * @param t 创建Subscriber时的泛型类型
     */
    @Override
    public void onNext(T t) {
        if (mSubscriberOnSuccessListener != null) {
            mSubscriberOnSuccessListener.onSuccess(t);
        }
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    /**
     * 显示Dialog
     */
    private void showDialog(Activity mContext, String title, String context, String confirm) {
        mDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
        mDialog.setTitleText(title)
                .setContentText(context)
                .setConfirmText(confirm);
        if (mDialog.isShowing()) {
            return;
        } else {
            mDialog.show();
        }

    }
}