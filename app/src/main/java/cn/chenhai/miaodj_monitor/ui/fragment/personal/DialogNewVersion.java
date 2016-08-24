package cn.chenhai.miaodj_monitor.ui.fragment.personal;

import android.app.Dialog;
import android.content.Context;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okhttpserver.download.DownloadInfo;
import com.lzy.okhttpserver.download.DownloadManager;
import com.lzy.okhttpserver.download.DownloadService;
import com.lzy.okhttpserver.listener.DownloadListener;

import java.io.File;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.utils.ApkUtils;
import cn.chenhai.miaodj_monitor.ui.view_custom.NumberProgressBar;

/**
 * 版本更新Dialog
 * Created by ChenHai--霜华 on 2016/7/23. 11:44
 * 邮箱：248866527@qq.com
 */
public class DialogNewVersion extends Dialog {

    View customView;
    Context context;

    // 上一次显示的dialog
    static DialogNewVersion lastDialog = null;

    private TextView mDialogTitleView;
    private LinearLayout mDialogLlVersionContent;
    private TextView mDialogNewVersion;
    private TextView mDialogMsgView;

    private LinearLayout mDialogLlProgress;
    private TextView mProgressName;
    private TextView mDownloadSize;
    private TextView mNetSpeed;
    private TextView mTvProgress;
    private NumberProgressBar mPbProgress;
    private Button mButtonCancel;
    private Button mButtonDone;

    private DownloadInfo downloadInfo;
    private DownloadManager downloadManager;
    private MyDownloadListener myDownloadListener;

    DoCallBack mDoCallBack;

    public DialogNewVersion(Context context) {
        super(context, R.style.customDialog);

        this.context = context;
        //this.mDoCallBack = mDoCallBack;
    }


    /**
     * 展示
     *
     */
    public void show() {
        // TODO Auto-generated method stub
        super.show();
        LayoutInflater mInflater = LayoutInflater.from(getContext());
        customView = mInflater.inflate(R.layout.dialog_new_version, null);
        setContentView(customView);

        // 设置他的宽度

        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = screenWidth * 4 / 5;
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(lp);

        setCancelable(false);
        setCanceledOnTouchOutside(false);

//        ButterKnife.bind(this);


        mDialogTitleView = (TextView) customView.findViewById(R.id.dialog_titleView);

        mDialogLlVersionContent = (LinearLayout) customView.findViewById(R.id.dialog_ll_versionContent);
        mDialogNewVersion = (TextView) customView.findViewById(R.id.dialog_newVersion);
        mDialogMsgView = (TextView) customView.findViewById(R.id.dialog_msgView);

        mDialogLlProgress = (LinearLayout) customView.findViewById(R.id.dialog_ll_progress);
        mProgressName = (TextView) customView.findViewById(R.id.progress_name);
        mDownloadSize = (TextView) customView.findViewById(R.id.downloadSize);
        mNetSpeed = (TextView) customView.findViewById(R.id.netSpeed);
        mTvProgress = (TextView) customView.findViewById(R.id.tvProgress);
        mPbProgress = (NumberProgressBar) customView.findViewById(R.id.pbProgress);

        mButtonCancel = (Button) customView.findViewById(R.id.buttonCancel);
        mButtonDone = (Button) customView.findViewById(R.id.buttonDone);



        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
//        mButtonDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mDoCallBack != null) {
//                    mDoCallBack.doCallBack("");
//                    dismiss();
//                }
//            }
//        });

    }

    public View getRootView() {
        return customView;
    }

    @Override
    public void setTitle(CharSequence title) {
        // super.setTitle(title);
        mDialogTitleView.setText(title);
        if (lastDialog != null
                && lastDialog.mDialogTitleView.getText().equals(mDialogTitleView.getText())) {
            lastDialog.dismiss();
        }
        lastDialog = this;
    }

    public void setVersionMessage(CharSequence msg) {
        mDialogNewVersion.setText(msg);
    }

    public void setMessage(CharSequence msg) {
        // TODO Auto-generated method stub
        mDialogMsgView.setText(msg);
    }

    public void showCancelBtn(boolean isShow) {
        mButtonCancel.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    public void setSubmitText(String submitStr) {
        mButtonDone.setText(submitStr == null ? "确定" : submitStr);
    }

    public void setCancelText(String cancelStr) {
        mButtonCancel.setText(cancelStr == null ? "取消" : cancelStr);
    }

    public void setCancelDoing(final Runnable cancelDoing) {
        mButtonCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (cancelDoing != null) {
                    cancelDoing.run();
                }
            }
        });
    }

    public void setDoCallBack( DoCallBack doCallBack , String downPath) {
        this.mDoCallBack = doCallBack;

        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dismiss();
                if (mDoCallBack != null) {
                    mDoCallBack.doCallBack(downPath);
                    //dismiss();
                }
            }
        });
    }
    public void setSubmitDoing(String submit, String downPath) {

        /*******************下载管理*******************/
        downloadManager = DownloadService.getDownloadManager(context);
        //apk = (ApkInfo) getIntent().getSerializableExtra("apk");
        myDownloadListener = new MyDownloadListener();

        downloadInfo = downloadManager.getTaskByUrl(downPath);
        if (downloadInfo != null) {
            //如果任务存在，把任务的监听换成当前页面需要的监听
            downloadInfo.setListener(myDownloadListener);
            //需要第一次手动刷一次，因为任务可能处于下载完成，暂停，等待状态，此时是不会回调进度方法的
            refreshUi(downloadInfo);
        }
        /**********************************************/

        mButtonDone.setVisibility(View.VISIBLE);
        mButtonDone.setText(submit != null ? submit : "确定");
        mButtonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dismiss();
                mDialogLlVersionContent.setVisibility(View.GONE);
                mDialogLlProgress.setVisibility(View.VISIBLE);
                mProgressName.setText("喵咚家");

                //每次点击的时候，需要更新当前对象
                downloadInfo = downloadManager.getTaskByUrl(downPath);
                if (downloadInfo == null) {
                    downloadManager.addTask(downPath, myDownloadListener);
                    return;
                }
                switch (downloadInfo.getState()) {
                    case DownloadManager.PAUSE:
                        downloadManager.addTask(downloadInfo.getUrl(), myDownloadListener);
                        break;
                    case DownloadManager.NONE:
                    case DownloadManager.ERROR:
                        downloadManager.removeTask(downloadInfo.getUrl());
                        downloadManager.addTask(downloadInfo.getUrl(), myDownloadListener);
                        break;
                    case DownloadManager.DOWNLOADING:
                        downloadManager.pauseTask(downloadInfo.getUrl());
                        break;
                    case DownloadManager.FINISH:
//                        if (ApkUtils.isAvailable(context, new File(downloadInfo.getTargetPath()))) {
//                            ApkUtils.uninstall(context, ApkUtils.getPackageName(context, downloadInfo.getTargetPath()));
//                        } else {
//                            ApkUtils.install(context, new File(downloadInfo.getTargetPath()));
//                        }
                        ApkUtils.install(context, new File(downloadInfo.getTargetPath()));
                        break;
                }

                refreshUi(downloadInfo);
            }
        });

    }


    private class MyDownloadListener extends DownloadListener {

        @Override
        public void onProgress(DownloadInfo downloadInfo) {
            refreshUi(downloadInfo);
        }

        @Override
        public void onFinish(DownloadInfo downloadInfo) {
            System.out.println("onFinish");
            Toast.makeText(context, "下载完成:" + downloadInfo.getTargetPath(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
            System.out.println("onError");
            if (errorMsg != null)
                Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

    //对于实时更新的进度ui，放在这里，例如进度的显示，而图片加载等，不要放在这，会不停的重复回调
    //也会导致内存泄漏
    private void refreshUi(DownloadInfo downloadInfo) {
        String downloadLength = Formatter.formatFileSize(context, downloadInfo.getDownloadLength());
        String totalLength = Formatter.formatFileSize(context, downloadInfo.getTotalLength());
        mDownloadSize.setText(downloadLength + "/" + totalLength);

        mTvProgress.setText((Math.round(downloadInfo.getProgress() * 10000) * 1.0f / 100) + "%");

        mPbProgress.setMax((int) downloadInfo.getTotalLength());
        mPbProgress.setProgress((int) downloadInfo.getDownloadLength());

        switch (downloadInfo.getState()) {
            case DownloadManager.DOWNLOADING:
                String networkSpeed = Formatter.formatFileSize(context, downloadInfo.getNetworkSpeed());
                mNetSpeed.setText(networkSpeed + "/s");
                mButtonDone.setText("暂停");
                break;
            case DownloadManager.PAUSE:
                mNetSpeed.setText("暂停中");
                mButtonDone.setText("继续");
                break;
            case DownloadManager.WAITING:
                mNetSpeed.setText("等待中");
                mButtonDone.setText("等待");
                break;
            case DownloadManager.ERROR:
                mNetSpeed.setText("下载出错");
                mButtonDone.setText("出错");
                break;
            case DownloadManager.FINISH:
//                if (ApkUtils.isAvailable(context, new File(downloadInfo.getTargetPath()))) {
//                    mButtonDone.setText("卸载");
//                } else {
//                    mButtonDone.setText("安装");
//                }
                mButtonDone.setText("安装");
                mNetSpeed.setText("下载完成");
                break;
        }
    }



    public interface DoCallBack {
        void doCallBack(String downPath);
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
