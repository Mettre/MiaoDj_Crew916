package cn.chenhai.miaodj_monitor.ui.module.account.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.StringUtils;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by ChenHai--霜华 on 2016/4/23. 17:36
 * 邮箱：248866527@qq.com
 */
public class LoginFragmentFindPass extends BaseBackFragment {

    private SubscriberOnSuccessListener mOnSuccessFindPass;
    private SubscriberOnSuccessListener mOnSuccessSendMsgCode;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private EditText mEtPhone;
    private EditText mEtAuthCode;
    private Button mBtnGetCode;
    private EditText mEtPassword;
    private Button mBtnDone;

    public boolean isChange = false;
    private boolean tag = true;
    private int i = 60;
    private Thread thread = null;


    private OnFindPassSuccessListener mOnFindPassSuccessListener;

    public static LoginFragmentFindPass newInstance() {

        Bundle args = new Bundle();

        LoginFragmentFindPass fragment = new LoginFragmentFindPass();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFindPassSuccessListener) {
            mOnFindPassSuccessListener = (OnFindPassSuccessListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFindPassSuccessListener");
        }
//        if (context instanceof SendCodefromServer) {
//            mSendCodefromServer = (SendCodefromServer) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement SendCodefromServer");
//        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_findpass, container, false);

        initView(view);
        //initReceiver();
        initData();
        return view;
    }


    private void initView(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mEtPhone = (EditText) view.findViewById(R.id.et_phone);
        mEtAuthCode = (EditText) view.findViewById(R.id.et_authCode);
        mBtnGetCode = (Button) view.findViewById(R.id.btn_getCode);
        mEtPassword = (EditText) view.findViewById(R.id.et_password);
        mBtnDone = (Button) view.findViewById(R.id.btn_done);

        //toolbar.setTitle(R.string.login_title);

        initToolbarNav(mToolbar);

        mBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strPhone = mEtPhone.getText().toString();
                String strAuthCode = mEtAuthCode.getText().toString();
                String strNewPass = mEtPassword.getText().toString();
                if (TextUtils.isEmpty(strPhone.trim())) {
                    Toast.makeText(_mActivity, "手机号不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (!StringUtils.isPhoneNumberValid(strPhone.trim())) {
                    Toast.makeText(_mActivity, "无效的手机号!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strAuthCode.trim())) {
                    Toast.makeText(_mActivity, "验证码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strNewPass.trim())) {
                    Toast.makeText(_mActivity, "新密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtPassword.length() < 6){
                    Toast.makeText(_mActivity, "密码长度必须大于 6 位！", Toast.LENGTH_SHORT).show();
                    return;
                }
//                else if (!strAuthCode.equals(temCodeService)) {
//                    Toast.makeText(_mActivity, "验证码不匹配!", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                HttpMethods.getInstance().findPassWord(new ProgressSubscriber(mOnSuccessFindPass, _mActivity), strPhone,strAuthCode,strNewPass);
            }
        });
        mBtnGetCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strPhone = mEtPhone.getText().toString();
                if (TextUtils.isEmpty(strPhone.trim())) {
                    Toast.makeText(_mActivity, "手机号不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }else if (!StringUtils.isPhoneNumberValid(strPhone.trim())) {
                    Toast.makeText(_mActivity, "无效的手机号!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBtnGetCode.setText("获取验证码");
                mBtnGetCode.setClickable(false);
                isChange = true;
                changeBtnGetCode();

                HttpMethods.getInstance().getSendMsgCode(new ProgressSubscriber(mOnSuccessSendMsgCode, _mActivity), strPhone);
            }
        });



        mOnSuccessFindPass = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                //保存到SharedPreferences，默认规定为Context.MODE_PRIVATE
                new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("提示")
                        .setContentText("密码已更新，请重新登录！")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();

                                pop();
                            }
                        })
                        .show();

            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){

            }
        };
        mOnSuccessSendMsgCode = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if(result.getCode() == 200){
                    new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("验证码(临时)")
                            .setContentText(String.valueOf(result.getVerify_code()))
                            .show();
                }
            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){

            }
        };

    }

    private void initData(){
        mToolbarTitle.setText("找回密码");
    }

    //验证码60秒重新获取
    private void changeBtnGetCode() {
        thread = new Thread() {
            @Override
            public void run() {
                if (tag) {
                    while (i > 0) {
                        i--;
                        if (_mActivity == null) {
                            break;
                        }

                        _mActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //mBtnCode.setText("获取验证码(" + i + ")");
                                        mBtnGetCode.setText(String.format("重新获取(%s)",i));
                                        mBtnGetCode.setClickable(false);
                                        mBtnGetCode.setEnabled(false);
                                    }
                                });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    tag = false;
                }
                i = 60;
                tag = true;
                if (_mActivity != null) {
                    _mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBtnGetCode.setText("获取验证码");
                            mBtnGetCode.setClickable(true);
                            mBtnGetCode.setEnabled(true);
                        }
                    });
                }
            };
        };
        thread.start();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mOnFindPassSuccessListener = null;
        //注销广播
        //_mActivity.unregisterReceiver(mReceiver);
    }

    public interface OnFindPassSuccessListener {
        void onFindPassSuccess();
    }




}
