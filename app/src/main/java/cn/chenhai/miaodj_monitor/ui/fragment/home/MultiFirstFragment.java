package cn.chenhai.miaodj_monitor.ui.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.base.BaseLazyMainFragment;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by YoKeyword on 16/6/3.
 */
public class MultiFirstFragment extends BaseLazyMainFragment {

    public static MultiFirstFragment newInstance() {

        Bundle args = new Bundle();

        MultiFirstFragment fragment = new MultiFirstFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.multi_fragment_first, container, false);
        return view;
    }

    @Override
    protected void initLazyView(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_first_container, HomeFragment.newInstance());
        }
    }

    /**
     * 处理回退事件
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    @Override
    public boolean onBackPressedSupport() {
        super.onBackPressedSupport();

        boolean result = true;

        if (getChildFragmentManager().getBackStackEntryCount() > 1) {
            popChild();
        } else {
            SupportFragment frag = getTopChildFragment();
            if (frag instanceof HomeFragment) {   // 如果是
                //_mActivity.finish();
                int i = ((HomeFragment) frag).getViewPager().getCurrentItem();
                if( i != 0){
                    ((HomeFragment) frag).getViewPager().setCurrentItem(0);
                    result = true;
                }
                else {
                    result = false;
                }
            }
            else {
                result = false; //跳回activity处理
            }
        }
        return result;
    }
}
