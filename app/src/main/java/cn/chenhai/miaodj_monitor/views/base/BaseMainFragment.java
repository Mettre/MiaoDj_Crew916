package cn.chenhai.miaodj_monitor.views.base;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.uuzuche.lib_zxing.activity.CaptureActivity;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import cn.chenhai.miaodj_monitor.R;


/**
 * Created by ChenHai--霜华 on 2016/6/5.
 * 邮箱：248866527@qq.com
 */
public class BaseMainFragment extends BaseFragment {

    protected OnFragmentOpenDrawerListener mOpenDraweListener;

    protected void initToolbarNav(Toolbar toolbar) {
        initToolbarNav(toolbar, false);
    }

    protected void initToolbarNav(Toolbar toolbar, boolean isHome) {
        //toolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        toolbar.setNavigationIcon(R.drawable.ic_menu_scan_32);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (mOpenDraweListener != null) {
//                    mOpenDraweListener.onOpenDrawer();
//                }
                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("提示")
                        .setContentText("扫描签收功能建设中，敬请期待!")
                        .show();
            }
        });

        if(!isHome) {
            initToolbarMenu(toolbar);
        }
    }


    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {
        FragmentAnimator fragmentAnimator = _mActivity.getFragmentAnimator();
        fragmentAnimator.setEnter(0);
        fragmentAnimator.setExit(0);
        return fragmentAnimator;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentOpenDrawerListener) {
            mOpenDraweListener = (OnFragmentOpenDrawerListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentOpenDrawerListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mOpenDraweListener = null;
    }

    public interface OnFragmentOpenDrawerListener {
        void onOpenDrawer();
    }
}
