package cn.chenhai.miaodj_monitor.views.fragment.personal;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment_Swip;

/**
 * Created by ChenHai--霜华 on 2016/6/30. 09:17
 * 邮箱：248866527@qq.com
 */
public class PersonalAboutUSTeam extends BaseBackFragment_Swip {
    private static final String ARG_ITEM = "arg_item";
    private static final String TAG = "FragmentLib";

    private String mProjectCode;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private SimpleDraweeView mAboutSdv;



    public static PersonalAboutUSTeam newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        PersonalAboutUSTeam fragment = new PersonalAboutUSTeam();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectCode = getArguments().getString(ARG_ITEM);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_about_us_team, container, false);
        initView(view);
        initData();
        //initDataTemp();
        //return view;
        return attachToSwipeBack(view);
    }

    private void initView(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);
        initToolbarNav(mToolbar);
        mAboutSdv = (SimpleDraweeView) view.findViewById(R.id.about_sdv);

    }


    private void initData() {
        mToolbarTitle.setText("喵咚家团队");

//        //Uri imageUri = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");
//        Uri imageUri = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.logo_color);
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                //加载的图片URI地址
//                .setUri(imageUri)
//                //设置点击重试是否开启
//                .setTapToRetryEnabled(true)
//                //设置旧的Controller
//                .setOldController(mAboutSdv.getController())
//                //构建
//                .build();
//
//        //设置DraweeController
//        mAboutSdv.setController(controller);
//
//        //开始下载
//        mAboutSdv.setImageURI(imageUri);
    }
}
