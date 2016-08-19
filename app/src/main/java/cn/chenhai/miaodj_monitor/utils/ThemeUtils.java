package cn.chenhai.miaodj_monitor.utils;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by ChenHai--霜华 on 2016/6/2.
 * 邮箱：248866527@qq.com
 */
public class ThemeUtils {
    public static int getThemeColor(Context context, int attrRes) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{attrRes});
        int color = typedArray.getColor(0, 0xffffff);
        typedArray.recycle();
        return color;
    }
}
