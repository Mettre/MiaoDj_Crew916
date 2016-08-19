package cn.chenhai.miaodj_monitor.network_proxy.proxy;

import android.content.Context;
import android.content.Intent;


/**
 * Created by ChenHai--霜华 on 2016/5/3. 13:27
 * 邮箱：248866527@qq.com
 */
public class DataLocal{

    Intent broadcastIntent;
    /**初始化*/
    private void DataLocal(){
        broadcastIntent = new Intent();
    }

    /**获取验证码*/
    public String GetAuthCode(Context context,String phone){
        String strResult = "123456";
        return strResult;
    }


}
