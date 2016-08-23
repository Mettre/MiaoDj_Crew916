package cn.chenhai.miaodj_monitor.ui.fragment.personal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by ChenHai--霜华 on 2016/6/30. 09:54
 * 邮箱：248866527@qq.com
 */
public class PersonalAboutUSContact extends BaseBackFragment_Swip {
    private static final String ARG_ITEM = "arg_item";
    private static final String TAG = "FragmentLib";

    private String mProjectCode;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private SimpleDraweeView mAboutSdv;
    private TextView mTvTeamPhone;




    public static PersonalAboutUSContact newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        PersonalAboutUSContact fragment = new PersonalAboutUSContact();
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
        View view = inflater.inflate(R.layout.fragment_personal_about_us_contact, container, false);
        initView(view);
        initData();
        //initDataTemp();
        //return view;
        return attachToSwipeBack(view);
    }

    private void initView(View view) {


        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        mAboutSdv = (SimpleDraweeView) view.findViewById(R.id.about_sdv);
        mTvTeamPhone = (TextView) view.findViewById(R.id.tv_team_phone);

        initToolbarNav(mToolbar);

        mTvTeamPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(_mActivity,SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("拨打电话")
                        .setContentText(mTvTeamPhone.getText().toString())
                        .setCancelText("取消")
                        .setConfirmText("确定！")
                        .showCancelButton(true)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                String phone = mTvTeamPhone.getText().toString();
                                //用intent启动拨打电话
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ phone));
                                startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

    }


    private void initData() {
        mToolbarTitle.setText("联系我们");

    }
}
