package cn.chenhai.miaodj_monitor.ui.fragment.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
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
import cn.chenhai.miaodj_monitor.model.entity.EmptyEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ChenHai--霜华 on 2016/6/29. 13:39
 * 邮箱：248866527@qq.com
 */
public class PersonalFeedbackFragment extends BaseBackFragment_Swip {
    private static final String ARG_ITEM = "arg_item";
    private static final String TAG = "FragmentLib";

    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessFeedBack;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private EditText mEtFeedbackContent;
    private Button mBtnFeedback;


    public static PersonalFeedbackFragment newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        PersonalFeedbackFragment fragment = new PersonalFeedbackFragment();
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
        View view = inflater.inflate(R.layout.fragment_personal_feedback, container, false);
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

        mEtFeedbackContent = (EditText) view.findViewById(R.id.et_feedback_content);
        mBtnFeedback = (Button) view.findViewById(R.id.btn_feedback);

        mBtnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pop();
                String user_code = PreferencesUtils.getString(_mActivity,"user_code");
                String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
                HttpMethods.getInstance().doFeedBack(new ProgressSubscriber(mOnSuccessFeedBack, _mActivity), user_code, access_token,mEtFeedbackContent.getText().toString());
            }
        });



        mOnSuccessFeedBack = new SubscriberOnSuccessListener<HttpResult<EmptyEntity>>() {
            @Override
            public void onSuccess(HttpResult<EmptyEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("提示")
                            .setContentText("意见反馈上传成功！感谢您的支持，我们会尽快处理。")
                            .setConfirmText("知道了")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismiss();
                                    _mActivity.finish();
                                }
                            })
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


    private void initData() {
        mToolbarTitle.setText("意见反馈");

    }
}
