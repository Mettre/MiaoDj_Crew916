package cn.chenhai.miaodj_monitor.ui.fragment.personal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;

/**
 * Created by ChenHai--霜华 on 2016/6/29. 20:02
 * 邮箱：248866527@qq.com
 */
public class PersonalInfoEditPhone extends BaseBackFragment_Swip {

    private String mTitle ,mPhone ,mMethName;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private EditText mEtPhone;
    private EditText mEtAuthCode;
    private Button mPhoneBtGetCode;
    private Button mButtonSave;


    public static PersonalInfoEditPhone newInstance(String item_title,String et_change,String methName) {
        PersonalInfoEditPhone fragment = new PersonalInfoEditPhone();
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
            mPhone = args.getString("item_et_phone");
            mMethName = args.getString("item_methName");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info_phone, container, false);

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


        mEtPhone = (EditText) view.findViewById(R.id.et_phone);
        mEtAuthCode = (EditText) view.findViewById(R.id.et_authCode);
        mPhoneBtGetCode = (Button) view.findViewById(R.id.phone_bt_getCode);
        mButtonSave = (Button) view.findViewById(R.id.button_save);


        mPhoneBtGetCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideSoftInput();
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////
                pop();
            }
        });

    }

    private void initData(){
        mToolbarTitle.setText(mTitle);
        mEtPhone.setText(mPhone);
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
