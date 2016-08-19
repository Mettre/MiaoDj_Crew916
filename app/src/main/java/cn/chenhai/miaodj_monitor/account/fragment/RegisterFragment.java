package cn.chenhai.miaodj_monitor.account.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.commonlib.utils.StringUtils;
import cn.chenhai.miaodj_monitor.commonlib.utils.TimeUtils;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.network_proxy.HttpMethods;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by ChenHai--霜华 on 2016/4/18. 03:55
 * 邮箱：248866527@qq.com
 */
public class RegisterFragment extends BaseBackFragment {

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private EditText mEtPhone;
    private EditText mEtAuthCode;
    private Button mBtnGetCode;
    private EditText mEtPassword;
    private EditText mEtPasswordConfirm;
    private EditText mEtInviteCode;
    private Button mBtnRegister;
    private CheckBox mRegisterCheckbox;


    private OnRegisterSuccessListener mOnRegisterSuccessListener;

    private SubscriberOnSuccessListener mOnSuccessRegister;
    private SubscriberOnSuccessListener mOnSuccessSendMsgCode;

    private boolean tag = true;
    private int i = 60;
    private Thread thread = null;

    public static RegisterFragment newInstance() {

        Bundle args = new Bundle();

        RegisterFragment fragment = new RegisterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRegisterSuccessListener) {
            mOnRegisterSuccessListener = (OnRegisterSuccessListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRegisterSuccessListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_register, container, false);
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
        mEtPasswordConfirm = (EditText) view.findViewById(R.id.et_password_confirm);
        mEtInviteCode = (EditText) view.findViewById(R.id.et_invite_code);
        mBtnRegister = (Button) view.findViewById(R.id.btn_register);
        mRegisterCheckbox = (CheckBox) view.findViewById(R.id.register_checkbox);


        //显示软键盘
        //showSoftInput(mEtAccount);

        initToolbarNav(mToolbar);

        mBtnGetCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strPhone = mEtPhone.getText().toString();
                if (TextUtils.isEmpty(strPhone.trim())) {
                    Toast.makeText(_mActivity, "验证手机号不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!StringUtils.isPhoneNumberValid(strPhone.trim())) {
                    Toast.makeText(_mActivity, "无效的手机号!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mBtnGetCode.setText("获取验证码");
                mBtnGetCode.setClickable(false);
                changeBtnGetCode();

                //ProxyService.newInstance().GetAuthCode(_mActivity,strPhone);
                HttpMethods.getInstance().getSendMsgCode(new ProgressSubscriber(mOnSuccessSendMsgCode, _mActivity), strPhone);
            }
        });

        mRegisterCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mRegisterCheckbox.setTextColor(0xff3ca0ec);
                    mBtnRegister.setEnabled(true);
                }else{
                    //mRegisterCheckbox.setTextColor(Color.argb(255,102,102,102));
                    mRegisterCheckbox.setTextColor(0xff9C9C9C);
                    mBtnRegister.setEnabled(false);
                }
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAccount = mEtPhone.getText().toString();
                String strPassword = mEtPassword.getText().toString();
                String strPasswordConfirm = mEtPasswordConfirm.getText().toString();
                String strCode = mEtAuthCode.getText().toString();
                if (TextUtils.isEmpty(strAccount.trim())) {
                    Toast.makeText(_mActivity, "注册手机号不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!StringUtils.isPhoneNumberValid(strAccount.trim())) {
                    Toast.makeText(_mActivity, "无效的手机号!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strPassword.trim()) || TextUtils.isEmpty(strPasswordConfirm.trim())) {
                    Toast.makeText(_mActivity, "密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!strPassword.equals(strPasswordConfirm)) {
                    Toast.makeText(_mActivity, "两次输入密码不一致!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtPassword.length() < 6){
                    Toast.makeText(_mActivity, "密码长度必须大于 6 位！", Toast.LENGTH_SHORT).show();
                    return;
                }

                HttpMethods.getInstance().doRegister(new ProgressSubscriber(mOnSuccessRegister, _mActivity), strAccount, strPassword,strCode);
            }
        });


        mOnSuccessRegister = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                //保存到SharedPreferences，默认规定为Context.MODE_PRIVATE
                String user_code = result.getUser_code();
                String access_token = result.getAccess_token();

                PreferencesUtils.putString(_mActivity,"expiretime",String.valueOf(TimeUtils.getCurrentTimeInLong() + 2*3600*1000));
                PreferencesUtils.putString(_mActivity,"user_code",user_code);
                PreferencesUtils.putString(_mActivity,"access_token",access_token);
                PreferencesUtils.putString(_mActivity,"phoneAccount",mEtPhone.getText().toString());

                mOnRegisterSuccessListener.onRegisterSuccess();
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
        mToolbarTitle.setText("用户注册");
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
                                String str = String.format("重新获取(%s)",i);
                                mBtnGetCode.setText(str);
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
        mOnRegisterSuccessListener = null;
        //注销广播
        //_mActivity.unregisterReceiver(mReceiver);
    }

    public interface OnRegisterSuccessListener {
        void onRegisterSuccess();
    }

}
