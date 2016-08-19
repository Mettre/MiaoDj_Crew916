package cn.chenhai.miaodj_monitor.theme;

import android.content.res.Resources;
import android.view.View;

/**
 * 换肤接口
 * Created by ChenHai--霜华 on 2016/5/31. 07:31
 * 邮箱：248866527@qq.com
 */
public interface ColorUiInterface {
    View getView();
    void setTheme(Resources.Theme themeId);
}
