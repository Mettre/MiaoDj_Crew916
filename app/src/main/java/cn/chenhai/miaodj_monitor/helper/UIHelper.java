package cn.chenhai.miaodj_monitor.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

//import cn.chenhai.miaodj_monitor.views.activity.LoginActivity;
import cn.chenhai.miaodj_monitor.account.UserAccountActivity;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.views.activity.MainActivity;

/**
 * 应用程序UI工具包：封装UI相关的一些操作
 */
public class UIHelper {

	public final static String TAG = "UIHelper";

	public final static int RESULT_OK = 0x00;
	public final static int REQUEST_CODE = 0x01;

	public static void ToastMessage(Context cont, String msg) {
        if(cont == null || msg == null) {
            return;
        }
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
        if(cont == null || msg <= 0) {
            return;
        }
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
        if(cont == null || msg == null) {
            return;
        }
		Toast.makeText(cont, msg, time).show();
	}

    public static void showHome(Activity context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    public static void showLogin(Activity context){
        Intent intent = new Intent(context, UserAccountActivity.class);
        context.startActivity(intent);
        context.finish();
    }

    public static void showLoginErrorAgain(Activity context){
        PreferencesUtils.putString(context,"expiretime","0");
        Intent intent = new Intent(context, UserAccountActivity.class);

        //此处代码表示，清空栈里所有activity，并启动新的task。。。。2个flag必须一起使用才有效
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK );

        context.startActivity(intent);
        context.finish();
    }
//
//    public static void showHouseDetailActivity(Activity context){
//        Intent intent = new Intent(context, HouseDetailActivity.class);
//        context.startActivity(intent);
//    }

}
