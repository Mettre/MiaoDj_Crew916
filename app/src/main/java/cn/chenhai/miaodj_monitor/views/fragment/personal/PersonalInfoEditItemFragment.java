package cn.chenhai.miaodj_monitor.views.fragment.personal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment_Swip;


/**
 * Created by ChenHai--霜华 on 2016/5/16. 13:06
 * 邮箱：248866527@qq.com
 */
public class PersonalInfoEditItemFragment extends BaseBackFragment {

    private String mTitle,mChangeContent ,mMethName;

    private TextView mTv_title;
    private ImageView mIv_back;
    private Button mBtn_save;
    private EditText mEt_changeContent;


    public static PersonalInfoEditItemFragment newInstance(String item_title,String et_change,String methName) {
        PersonalInfoEditItemFragment fragment = new PersonalInfoEditItemFragment();
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
            mChangeContent = args.getString("item_et_change");
            mMethName = args.getString("item_methName");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_infoedit_item, container, false);

        //initReceiver();
        initView(view);
        return view;
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

        mTv_title = (TextView) view.findViewById(R.id.tv_title);
        mTv_title.setText(mTitle);

        mIv_back = (ImageView) view.findViewById(R.id.iv_back);
        mIv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });

        mBtn_save = (Button) view.findViewById(R.id.button_save);
        mBtn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String token = PreferencesUtils.getString(_mActivity, "user_token");
//
//                //
//                Intent msgIntent;
//                msgIntent = new Intent(_mActivity, ConnectService.class);
//                msgIntent.putExtra("method_name","FactoryInfoEdit");
//                msgIntent.putExtra("user_token", token);
//                msgIntent.putExtra(mMethName, mEt_changeContent.getText().toString());
//                _mActivity.startService(msgIntent);
            }
        });

        mEt_changeContent = (EditText) view.findViewById(R.id.et_change);
        mEt_changeContent.setText(mChangeContent);
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
