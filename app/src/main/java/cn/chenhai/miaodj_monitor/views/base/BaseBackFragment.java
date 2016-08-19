package cn.chenhai.miaodj_monitor.views.base;

import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.chenhai.miaodj_monitor.R;

/**
 * Created by ChenHai--霜华 on 2016/6/3.
 * 邮箱：248866527@qq.com
 */
public class BaseBackFragment extends BaseFragment {

    protected void initToolbarNav(Toolbar toolbar) {
        //toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_left_white32);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });

        initToolbarMenu(toolbar);
    }
}
