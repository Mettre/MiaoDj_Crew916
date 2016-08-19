package cn.chenhai.miaodj_monitor.views.base;

import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import me.yokeyword.fragmentation.SupportFragment;
import cn.chenhai.miaodj_monitor.R;

/**
 * Created by ChenHai--霜华 on 2016/6/3.
 * 邮箱：248866527@qq.com
 */
public class BaseFragment extends SupportFragment {
    private static final String TAG = "FragmentLib";

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
