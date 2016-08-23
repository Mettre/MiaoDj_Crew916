package cn.chenhai.miaodj_monitor.ui.base;

import android.support.v7.widget.Toolbar;
import android.view.View;

import cn.chenhai.miaodj_monitor.R;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by ChenHai--霜华 on 2016/7/13. 09:54
 * 邮箱：248866527@qq.com
 */
public class BaseBackFragment_Swip extends SwipeBackFragment {
    private static final String TAG = "Fragmentation";

    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });

        initToolbarMenu(toolbar);
    }

    protected void initToolbarMenu(Toolbar toolbar) {
//        toolbar.inflateMenu(R.menu.hierarchy);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_hierarchy:
//                        _mActivity.showFragmentStackHierarchyView();
//                        _mActivity.logFragmentStackHierarchy(TAG);
//                        break;
//                }
//                return true;
//            }
//        });
    }
}
