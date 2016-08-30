package cn.chenhai.miaodj_monitor.ui.fragment.personal;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.kyleduo.switchbutton.SwitchButton;

import java.io.File;
import java.util.Arrays;

import cn.chenhai.miaodj_monitor.MyApplication;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.NewVersionEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;
import cn.chenhai.miaodj_monitor.utils.NetUtils;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 系统设置
 * <p>
 * Created by ChenHai--霜华 on 2016/6/28. 22:33
 * 邮箱：248866527@qq.com
 */
public class PersonalSettingFragment extends BaseBackFragment_Swip {
    private static final String ARG_ITEM = "arg_item";
    private static final String TAG = "FragmentLib";

    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessGetNewVersion;
    private SubscriberOnSuccessListener mOnSuccessChangePushMaterial;
    private SubscriberOnSuccessListener mOnSuccessChangePushNodes;

    private String mStrChangeMaterial;
    private String mStrChangeNodes;
    private String mStrIfPushMaterial;
    private String mStrIfPushNodes;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mTvSettingIfOpen;
    private SwitchButton mSbButton1;
    private SwitchButton mSbButton2;
    private LinearLayout mLlSettingCache;
    private TextView mTvSettingCache;
    private LinearLayout mLlSetting2;
    private TextView mTvSettingVersions;
    private Button mBtnSettingLogout;


    public static PersonalSettingFragment newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        PersonalSettingFragment fragment = new PersonalSettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectCode = getArguments().getString(ARG_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_setting, container, false);
        initView(view);
        initData();
        //initDataTemp();
        //return view;
        return attachToSwipeBack(view);
    }

    private void initView(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);

        mTvSettingIfOpen = (TextView) view.findViewById(R.id.tv_setting_ifOpen);
        mSbButton1 = (SwitchButton) view.findViewById(R.id.sb_button_1);
        mSbButton2 = (SwitchButton) view.findViewById(R.id.sb_button_2);
        mLlSettingCache = (LinearLayout) view.findViewById(R.id.ll_setting_cache);
        mTvSettingCache = (TextView) view.findViewById(R.id.tv_setting_cache);
        mLlSetting2 = (LinearLayout) view.findViewById(R.id.ll_setting_2);
        mTvSettingVersions = (TextView) view.findViewById(R.id.tv_setting_versions);
        mBtnSettingLogout = (Button) view.findViewById(R.id.btn_setting_logout);

        //读取装修节点消息通知  和  材料到货消息通知的存储值
        String strNodes = PreferencesUtils.getString(_mActivity, "PushNodes_message");
        String strMaterial = PreferencesUtils.getString(_mActivity, "PushMaterial_message");
        if (strNodes != null) {
            mStrIfPushNodes = strNodes;
        } else {
            mStrIfPushNodes = "1";
        }
        if (strMaterial != null) {
            mStrIfPushMaterial = strMaterial;
        } else {
            mStrIfPushMaterial = "1";
        }

        if (mStrIfPushNodes.equals("1")) {
            mSbButton1.setChecked(true);
        } else if (mStrIfPushNodes.equals("0")) {
            mSbButton1.setChecked(false);
        }

        if (mStrIfPushMaterial.equals("1")) {
            mSbButton2.setChecked(true);
        } else if (mStrIfPushMaterial.equals("0")) {
            mSbButton2.setChecked(false);
        }

        new GetDiskCacheSizeTask(mTvSettingCache).execute(new File(_mActivity.getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR));

        mOnSuccessGetNewVersion = new SubscriberOnSuccessListener<HttpResult<NewVersionEntity>>() {
            @Override
            public void onSuccess(HttpResult<NewVersionEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    NewVersionEntity.VersionBean beanInfo = result.getInfo().getVersion();

                    String downPath = "";
                    if (beanInfo.getPath() != null) {
                        downPath = beanInfo.getPath();
                    }

                    if (NetUtils.isVersionUpdate(_mActivity, beanInfo)) {

                        boolean isAutoUpdate = false;
                        if (beanInfo.getEnforceUpdate() != null) {
                            isAutoUpdate = beanInfo.getEnforceUpdate().equals("Y");
                        }

                        DialogNewVersion dialog = new DialogNewVersion(_mActivity);
                        dialog.show();
                        dialog.setTitle("发现新版本");
                        dialog.setVersionMessage("最新版本：" + beanInfo.getVersion());
                        dialog.setMessage(isAutoUpdate ? "您需要更新版本才能使用!"
                                : "检测到新版本,是否更新");
                        dialog.setCancelable(!isAutoUpdate);
                        dialog.setCanceledOnTouchOutside(!isAutoUpdate);
                        dialog.setSubmitDoing("立即更新", downPath);

                        dialog.showCancelBtn(!isAutoUpdate);
                        dialog.setCancelDoing(null);
                        dialog.setSubmitText("立即更新");
                        dialog.setCancelText("以后再说");
                    } else {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("提示")
                                .setContentText("已是最新版本！V" + beanInfo.getVersion())
                                .setConfirmText("知道了")
                                .show();
                    }

                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {

            }
        };

        mOnSuccessChangePushMaterial = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {
                        PreferencesUtils.putString(_mActivity, "PushMaterial_message", mStrIfPushMaterial);

                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText(mStrChangeMaterial)
                                .show();

                    }
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {

            }
        };
        mOnSuccessChangePushNodes = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {
                        PreferencesUtils.putString(_mActivity, "PushNodes_message", mStrIfPushNodes);

                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText(mStrChangeNodes)
                                .show();

                    }
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {

            }
        };


        mSbButton1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mStrChangeNodes = "装修节点消息通知已开启！";
                    mStrIfPushNodes = "1";
                    String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                    String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                    HttpMethods.getInstance().changePushNodesMessage(new ProgressSubscriber(mOnSuccessChangePushNodes, _mActivity), user_code, access_token, "1");
                } else {
                    mStrChangeNodes = "装修节点消息通知已关闭！";
                    mStrIfPushNodes = "0";
                    String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                    String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                    HttpMethods.getInstance().changePushNodesMessage(new ProgressSubscriber(mOnSuccessChangePushNodes, _mActivity), user_code, access_token, "0");
                }
            }
        });
        mSbButton2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mStrChangeMaterial = "材料到货消息通知已开启！";
                    mStrIfPushMaterial = "1";
                    String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                    String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                    HttpMethods.getInstance().changePushMaterialMessage(new ProgressSubscriber(mOnSuccessChangePushMaterial, _mActivity), user_code, access_token, "1");
                } else {
                    mStrChangeMaterial = "材料到货消息通知已关闭！";
                    mStrIfPushMaterial = "0";
                    String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                    String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                    HttpMethods.getInstance().changePushMaterialMessage(new ProgressSubscriber(mOnSuccessChangePushMaterial, _mActivity), user_code, access_token, "0");
                }
            }
        });
        mLlSettingCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //清除缓存
                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("清理缓存")
                        .setContentText("确定要清除缓存吗？")
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .showCancelButton(true)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                cleanCache();

                                sDialog.dismiss();
                            }
                        })
                        .show();

            }
        });
        mLlSetting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //版本更新检查
                if (NetUtils.hasNetWorkConection(_mActivity)) {
                    //检测更新
                    //UmengUpdateAgent.update(this);
                    HttpMethods.getInstance().getNewVersion(new ProgressSubscriber(mOnSuccessGetNewVersion, _mActivity));

                } else {
                    new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("提示")
                            .setContentText("请检查网络设置！")
                            .setConfirmText("知道了")
                            .show();

                }
            }
        });

        mBtnSettingLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("确定要注销吗!")
                        .setCancelText("取消")
                        .setConfirmText("确定")
                        .showCancelButton(true)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                PreferencesUtils.remove(_mActivity, "expiretime");
                                PreferencesUtils.remove(_mActivity, "user_code");
                                PreferencesUtils.remove(_mActivity, "access_token");

                                UIHelper.showLoginErrorAgain(_mActivity);
                                _mActivity.finish();

                                sDialog.dismiss();
                            }
                        })
                        .show();

            }
        });
    }


    private void initData() {
        mToolbarTitle.setText("系统设置");

        PackageManager pm = _mActivity.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(_mActivity.getPackageName(), 0);
            //int versionCode = pi.versionCode;
            String versionName = "当前软件版本: V " + pi.versionName;
            mTvSettingVersions.setText(versionName);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    private void cleanCache() {
//        showProgressDialog("正在清理缓存...");
        //清楚硬盘缓存,需要后台线程清楚
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(MyApplication.getInstance()).clearDiskCache();
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        //清除内存缓存
                        Glide.get(_mActivity).clearMemory();
//                        showProgressSuccess("清除完毕");
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("清除完毕")
                                .show();
                        new GetDiskCacheSizeTask(mTvSettingCache).execute(new File(_mActivity.getCacheDir(), DiskCache.Factory.DEFAULT_DISK_CACHE_DIR));
                    }
                });
            }
        }).start();
    }


    //----------------------------

    class GetDiskCacheSizeTask extends AsyncTask<File, Long, Long> {
        private final TextView resultView;

        public GetDiskCacheSizeTask(TextView resultView) {
            this.resultView = resultView;
        }

        @Override
        protected void onPreExecute() {
            resultView.setText("计算中...");
        }

        @Override
        protected void onProgressUpdate(Long... values) { /* onPostExecute(values[values.length - 1]); */ }

        @Override
        protected Long doInBackground(File... dirs) {
            try {
                long totalSize = 0;
                for (File dir : dirs) {
                    publishProgress(totalSize);
                    totalSize += calculateSize(dir);
                }
                return totalSize;
            } catch (RuntimeException ex) {
                final String message = String.format("Cannot get size of %s: %s", Arrays.toString(dirs), ex);
            }
            return 0L;
        }

        @Override
        protected void onPostExecute(Long size) {
            String sizeText = Formatter.formatFileSize(resultView.getContext(), size);
            resultView.setText(sizeText);
        }

    }

    private long calculateSize(File dir) {
        if (dir == null) return 0;
        if (!dir.isDirectory()) return dir.length();
        long result = 0;
        File[] children = dir.listFiles();
        if (children != null)
            for (File child : children)
                result += calculateSize(child);
        return result;
    }

}
