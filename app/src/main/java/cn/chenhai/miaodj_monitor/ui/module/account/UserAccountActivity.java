package cn.chenhai.miaodj_monitor.ui.module.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.LinkedHashSet;
import java.util.Set;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.module.account.fragment.LoginFragment;
import cn.chenhai.miaodj_monitor.ui.module.account.fragment.LoginFragmentFindPass;
import cn.chenhai.miaodj_monitor.ui.module.account.fragment.RegisterFragment;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.ui.base.BaseActivity;
import cn.chenhai.miaodj_monitor.ui.activity.MainActivity;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by ChenHai--霜华 on 2016/4/24. 14:23
 * 邮箱：248866527@qq.com
 */
public class UserAccountActivity extends BaseActivity
        implements LoginFragment.OnLoginSuccessListener,RegisterFragment.OnRegisterSuccessListener
        ,LoginFragmentFindPass.OnFindPassSuccessListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_user_account);

        //initView();
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            //start(HomeFragment.newInstance());
            // 第一次启动时
            loadRootFragment(R.id.account_fl_container, LoginFragment.newInstance());
        }
    }


    @Override
    public void onLoginSuccess() {

//        //登录成功，返回到MainActivity
//        Intent iData = new Intent();
//        iData.putExtra("AccountName",account);
//        UserAccountActivity.this.setResult(RESULT_OK ,iData);
//        UserAccountActivity.this.finish();
        //极光推送别名设置 和 tag
        Set<String> tagSet = new LinkedHashSet<String>();
        tagSet.add("crew");
        String user_code = PreferencesUtils.getString(UserAccountActivity.this,"user_code");
        JPushInterface.setAliasAndTags(this, user_code, tagSet, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i("JPush######Allias:", "style=" + i + ",Alias=" + s);
            }
        });


        Intent intent = new Intent(UserAccountActivity.this ,MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, "注册成功,即将登录!", Toast.LENGTH_SHORT).show();
        onLoginSuccess();
    }
    @Override
    public void onFindPassSuccess() {

        Toast.makeText(this, "重置密码成功 !", Toast.LENGTH_SHORT).show();
        //onLoginSuccess();
    }

}
