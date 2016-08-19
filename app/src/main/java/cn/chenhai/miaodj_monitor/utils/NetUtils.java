package cn.chenhai.miaodj_monitor.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import cn.chenhai.miaodj_monitor.model.entity.NewVersionEntity;


public class NetUtils {

    /**
     * 判断是否具有网络连接
     *
     * @return
     */
    public static final boolean hasNetWorkConection(Context ctx) {
        // 获取连接活动管理器
        final ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获取连接的网络信息
        final NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isAvailable());
    }


    /**
     * 判断是否需要版本更新
     *
     * @param ctx
     * @param serverVersion
     * @return
     */
    public static boolean isVersionUpdate(Context ctx, NewVersionEntity.VersionBean serverVersion) {
        PackageManager pm = ctx.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), 0);
            //int versionCode = pi.versionCode;
            String versionName = pi.versionName;
            return !serverVersion.getVersion().equals(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
