package cn.chenhai.miaodj_monitor.ui.fragment.personal;

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
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ChenHai--霜华 on 2016/7/11. 15:32
 * 邮箱：248866527@qq.com
 */
public class PersonalChangePass extends BaseBackFragment_Swip {

    private SubscriberOnSuccessListener mOnSuccessChangePass;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private EditText mEtOldPassword;
    private EditText mEtNewPassword;
    private EditText mEtNewPasswordAgain;
    private Button mBtnDone;


    public static PersonalChangePass newInstance() {
        PersonalChangePass fragment = new PersonalChangePass();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_change_pass, container, false);

        //initReceiver();
        initView(view);
        initData();

        //return view;
        return attachToSwipeBack(view);
    }


    private void initView(View view) {



        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);

        mEtOldPassword = (EditText) view.findViewById(R.id.et_old_password);
        mEtNewPassword = (EditText) view.findViewById(R.id.et_new_password);
        mEtNewPasswordAgain = (EditText) view.findViewById(R.id.et_new_password_again);
        mBtnDone = (Button) view.findViewById(R.id.btn_done);


        mBtnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strOldPassWord = mEtOldPassword.getText().toString();
                String strPassword = mEtNewPassword.getText().toString();
                String strPasswordConfirm = mEtNewPasswordAgain.getText().toString();

                if (TextUtils.isEmpty(strOldPassWord.trim())) {
                    Toast.makeText(_mActivity, "旧密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(strPassword.trim()) || TextUtils.isEmpty(strPasswordConfirm.trim())) {
                    Toast.makeText(_mActivity, "新密码不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!strPassword.equals(strPasswordConfirm)) {
                    Toast.makeText(_mActivity, "两次输入密码不一致!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mEtNewPassword.length() < 6){
                    Toast.makeText(_mActivity, "密码长度必须大于 6 位！", Toast.LENGTH_SHORT).show();
                    return;
                }

                String user_code = PreferencesUtils.getString(_mActivity,"user_code");
                String access_token =  PreferencesUtils.getString(_mActivity,"access_token");

                HttpMethods.getInstance().doChangePassword(new ProgressSubscriber(mOnSuccessChangePass, _mActivity), user_code, access_token,mEtOldPassword.getText().toString(),mEtNewPassword.getText().toString());


            }
        });


        mOnSuccessChangePass = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {

                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("修改密码已成功!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismiss();

                                        Bundle bundle = new Bundle();
                                        bundle.putString("result","已修改");
                                        setFramgentResult(RESULT_OK, bundle);
                                        pop();
                                    }
                                })
                                .show();

                        //refreshData();
                    }
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
        mToolbarTitle.setText("修改密码");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
