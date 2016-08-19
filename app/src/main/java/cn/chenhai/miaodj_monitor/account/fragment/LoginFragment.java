package cn.chenhai.miaodj_monitor.account.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesObjectUtils;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.commonlib.utils.StringUtils;
import cn.chenhai.miaodj_monitor.commonlib.utils.TimeUtils;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.TestResult;
import cn.chenhai.miaodj_monitor.model.entity.Account;
import cn.chenhai.miaodj_monitor.network_proxy.HttpMethods;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment;


/**
 * Created by ChenHai--霜华 on 2016/4/18. 03:45
 * 邮箱：248866527@qq.com
 */
public class LoginFragment extends BaseBackFragment {

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private EditText mEtUsername;
    private EditText mEtPassword;
    private Button mBtnLogin;
    private TextView mTvRegister;
    private TextView mTvForgetPass;


    private OnLoginSuccessListener mOnLoginSuccessListener;

    private SubscriberOnSuccessListener mOnSuccessListener;

    public static LoginFragment newInstance() {

        Bundle args = new Bundle();

        LoginFragment fragment = new LoginFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnLoginSuccessListener) {
            mOnLoginSuccessListener = (OnLoginSuccessListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnLoginSuccessListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_login, container, false);

        initView(view);
        initData();
        //initReceiver();

        String expiretime = PreferencesUtils.getString(_mActivity,"expiretime");
        long currentTime = TimeUtils.getCurrentTimeInLong();
        if(expiretime!=null){
            if(Long.valueOf(expiretime)  > currentTime){
                String user_code = PreferencesUtils.getString(_mActivity,"user_code");
                String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
                if(user_code!=null && access_token!=null) {
                    mOnLoginSuccessListener.onLoginSuccess();
                }
            }
        }
        return view;
    }


    private void initView(View view) {

        mOnSuccessListener = new SubscriberOnSuccessListener<HttpResult<Account>>() {
            @Override
            public void onSuccess(HttpResult<Account> result) {
                //保存到SharedPreferences，默认规定为Context.MODE_PRIVATE
                String user_code = result.getUser_code();
                String access_token = result.getAccess_token();

                PreferencesUtils.putString(_mActivity,"expiretime",String.valueOf(TimeUtils.getCurrentTimeInLong() + 24*3600*1000));
                PreferencesUtils.putString(_mActivity,"user_code",user_code);
                PreferencesUtils.putString(_mActivity,"access_token",access_token);
                PreferencesUtils.putString(_mActivity,"phoneAccount",mEtUsername.getText().toString());

                PreferencesObjectUtils objectUtils = new PreferencesObjectUtils(_mActivity,"Login_Account");
                objectUtils.setObject("account_login",result.getInfo().getCrew());

                mOnLoginSuccessListener.onLoginSuccess();
            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){

            }
        };
//        mOnSuccessListener = new SubscriberOnSuccessListener<TestResult>() {
//            @Override
//            public void onSuccess(TestResult result) {
//                //保存到SharedPreferences，默认规定为Context.MODE_PRIVATE
//                result.getHTTP_RAW_POST_DATA();
//
//                mOnLoginSuccessListener.onLoginSuccess();
//            }
//        };

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        mEtUsername = (EditText) view.findViewById(R.id.et_username);
        mEtPassword = (EditText) view.findViewById(R.id.et_password);
        mBtnLogin = (Button) view.findViewById(R.id.btn_login);
        mTvRegister = (TextView) view.findViewById(R.id.tv_register);
        mTvForgetPass = (TextView) view.findViewById(R.id.tv_forgetPass);


        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAccount = mEtUsername.getText().toString();
                String strPassword = mEtPassword.getText().toString();
                if (TextUtils.isEmpty(strAccount.trim())) {
                    Toast.makeText(_mActivity, "登录手机号不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (!StringUtils.isPhoneNumberValid(strAccount.trim())) {
                    Toast.makeText(_mActivity, "无效的手机号!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(strPassword.trim())) {
                    Toast.makeText(_mActivity, "密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtPassword.length() < 6){
                    Toast.makeText(_mActivity, "密码长度必须大于 6 位！", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 登录成功
                //ProxyService.newInstance().Login(_mActivity,strAccount,strPassword);
                //mOnLoginSuccessListener.onLoginSuccess(strAccount,strPassword);
                //pop();
//                //存储手机号
//                PreferencesUtils.putString(_mActivity,"phoneAccount",strAccount);
                //进行网络请求

                HttpMethods.getInstance().doLogin(new ProgressSubscriber(mOnSuccessListener, _mActivity), strAccount, strPassword);
                //HttpMethods.getInstance().doTest(new ProgressSubscriber(mOnSuccessListener, _mActivity), strAccount, strPassword);
            }
        });

        mTvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(RegisterFragment.newInstance());
            }
        });

        mTvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start(LoginFragmentFindPass.newInstance());
            }
        });
    }


    private void initData(){
        //toolbar.setTitle(R.string.login_title);
        mToolbarTitle.setText("用户登录");
        //加载手机号
        mEtUsername.setText(PreferencesUtils.getString(_mActivity,"phoneAccount"));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOnLoginSuccessListener = null;
        //注销广播
        //_mActivity.unregisterReceiver(mReceiver);
    }

    public interface OnLoginSuccessListener {
        void onLoginSuccess();
    }



}
