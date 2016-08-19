package cn.chenhai.miaodj_monitor.views.fragment.personal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.network_proxy.HttpMethods;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment_Swip;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ChenHai--霜华 on 2016/6/29. 19:12
 * 邮箱：248866527@qq.com
 */
public class PersonalInfoEditFragment extends BaseBackFragment_Swip {

    private String mTitle,mHintText ,mChangeContent ,mMethName;

    private SubscriberOnSuccessListener mOnSuccessChangeName;
    private SubscriberOnSuccessListener mOnSuccessChangeAdress;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private EditText mEtChange;
    private FrameLayout mFlDelete;
    private Button mButtonSave;


    public static PersonalInfoEditFragment newInstance(String item_title,String hintText,String et_change,String methName) {
        PersonalInfoEditFragment fragment = new PersonalInfoEditFragment();
        Bundle args = new Bundle();
        args.putString("item_title", item_title);
        args.putString("item_et_change", et_change);
        args.putString("item_methName", methName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mTitle = args.getString("item_title");
            mHintText = args.getString("item_hintText");
            mChangeContent = args.getString("item_et_change");
            mMethName = args.getString("item_methName");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info_item, container, false);

        //initReceiver();
        initView(view);
        initData();

        //return view;
        return attachToSwipeBack(view);
    }

//    private void initReceiver() {
//        //动态注册receiver
//        IntentFilter filter = new IntentFilter(IntentConstants.ACTION__MSG_FACTORY_INFO_EDIT);
//
//        filter.addCategory(Intent.CATEGORY_DEFAULT);
//        mReceiver = new MessageReceiver();
//        _mActivity.registerReceiver(mReceiver, filter);
//    }

    private void initView(View view) {


        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);


        mEtChange = (EditText) view.findViewById(R.id.et_change);
        mFlDelete = (FrameLayout) view.findViewById(R.id.fl_delete);
        mButtonSave = (Button) view.findViewById(R.id.button_save);

        mFlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtChange.setText("");
                hideSoftInput();
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////
                //pop();
                String user_code = PreferencesUtils.getString(_mActivity,"user_code");
                String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
                if(mTitle.equals("更改姓名")){
                    HttpMethods.getInstance().doChangeName(new ProgressSubscriber(mOnSuccessChangeName, _mActivity), user_code, access_token,mEtChange.getText().toString());
                }else if(mTitle.equals("更改我的地址")){
                    HttpMethods.getInstance().doChangeAddressDetail(new ProgressSubscriber(mOnSuccessChangeAdress, _mActivity), user_code, access_token,"","","",mEtChange.getText().toString());
                }

            }
        });


        mOnSuccessChangeName = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {

                        Bundle bundle = new Bundle();
                        bundle.putString("result","已修改");
                        setFramgentResult(RESULT_OK, bundle);
                        pop();

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

        mOnSuccessChangeAdress = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {

                        Bundle bundle = new Bundle();
                        bundle.putString("result","已修改");
                        setFramgentResult(RESULT_OK, bundle);
                        pop();

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
        mToolbarTitle.setText(mTitle);
        mEtChange.setHint(mHintText);
        mEtChange.setText(mChangeContent);
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
