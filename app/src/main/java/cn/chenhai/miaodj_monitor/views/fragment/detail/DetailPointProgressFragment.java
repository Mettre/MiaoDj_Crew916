package cn.chenhai.miaodj_monitor.views.fragment.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.PointProgressDetailEntity;
import cn.chenhai.miaodj_monitor.network_proxy.HttpMethods;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment_Swip;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by ChenHai--霜华 on 2016/6/14. 22:07
 * 邮箱：248866527@qq.com
 */
public class DetailPointProgressFragment extends BaseBackFragment_Swip {
    private static final String ARG_ITEM = "arg_item";

    private static final String TAG = "FragmentLib";
    private ItemPointFragment mItemPointFragment;
    private String mProjectCode;
    private String mPointID;
    private int mTotalIndex;

    private SubscriberOnSuccessListener mOnSuccessInit;
    private SubscriberOnSuccessListener mOnSuccessNodeStartIn;
    private SubscriberOnSuccessListener mOnSuccessNodeStartWorking;
    private SubscriberOnSuccessListener mOnSuccessNodeFinishWorking;
    private SubscriberOnSuccessListener mOnSuccessCheckNodeFinish;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private ScrollView mBounceScrollView;
    private ImageView mIvPointCircle;
    private TextView mTvPointCircleNum;
    private TextView mTvPointName;
    private ImageView mIvPointArrowLeft;
    private ImageView mIvPointArrowRight;
    private LinearLayout mLLPointArrowLeft;
    private LinearLayout mLLPointArrowRight;
    private FrameLayout mFlPointFrameLayout;
    private Button mBtnOK;

    private PointProgressDetailEntity.NodeBean mNodeBean;

    @ItemPointFragment.AnimationStyle
    private static int sAnimationStyle = ItemPointFragment.MOVEPULL;

    public static DetailPointProgressFragment newInstance(String projectCode ,String mPointID ,int mTotalIndex) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        args.putString("mPointID",mPointID);
        args.putInt("mTotalIndex",mTotalIndex);

        DetailPointProgressFragment fragment = new DetailPointProgressFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProjectCode = getArguments().getString(ARG_ITEM);
        mPointID = getArguments().getString("mPointID");
        mTotalIndex = getArguments().getInt("mTotalIndex");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_point_progress, container, false);

        initView(view);
        initData();
        initDataTemp();
        //return view;
        return attachToSwipeBack(view);
    }

    private void initView(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);
        mToolbar.inflateMenu(R.menu.change_anim);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.action_change_anim:
                        final PopupMenu popupMenu = new PopupMenu(_mActivity, mToolbar, GravityCompat.END);
                        popupMenu.inflate(R.menu.point_animation);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                //ItemPointFragment f = (ItemPointFragment)getSupportFragmentManager().findFragmentById(R.id.layout_main);
                                ItemPointFragment f = findChildFragment(ItemPointFragment.class);
                                if(f==null){return false;}
                                switch (item.getItemId()) {

                                    case R.id.style_cube:
                                        sAnimationStyle = ItemPointFragment.CUBE;
                                        f.setAnimationStyle(ItemPointFragment.CUBE);
                                        return true;
                                    case R.id.style_flip:
                                        sAnimationStyle = ItemPointFragment.FLIP;
                                        f.setAnimationStyle(ItemPointFragment.FLIP);
                                        return true;
                                    case R.id.style_pushpull:
                                        sAnimationStyle = ItemPointFragment.PUSHPULL;
                                        f.setAnimationStyle(ItemPointFragment.PUSHPULL);
                                        return true;
                                    case R.id.style_sides:
                                        sAnimationStyle = ItemPointFragment.SIDES;
                                        f.setAnimationStyle(ItemPointFragment.SIDES);
                                        return true;
                                    case R.id.style_movepull:
                                        sAnimationStyle = ItemPointFragment.MOVEPULL;
                                        f.setAnimationStyle(ItemPointFragment.MOVEPULL);
                                        return true;
                                }
                                popupMenu.dismiss();
                                return true;
                            }
                        });
                        popupMenu.show();
                        break;
                }
                return true;
            }
        });

        mBounceScrollView = (ScrollView) view.findViewById(R.id.bounceScrollView);
        mTvPointCircleNum = (TextView) view.findViewById(R.id.tv_point_circle_num);
        mTvPointName = (TextView) view.findViewById(R.id.tv_point_name);

        mIvPointArrowLeft = (ImageView) view.findViewById(R.id.iv_point_arrow_left);
        mIvPointCircle = (ImageView) view.findViewById(R.id.iv_point_circle);
        mIvPointArrowRight = (ImageView) view.findViewById(R.id.iv_point_arrow_right);
        mLLPointArrowLeft = (LinearLayout) view.findViewById(R.id.ll_point_arrow_left);
        mLLPointArrowRight = (LinearLayout) view.findViewById(R.id.ll_point_arrow_right);

        mFlPointFrameLayout = (FrameLayout) view.findViewById(R.id.fl_point_frameLayout);

        //mItemPointFragment = ItemPointFragment.newInstance(mProjectCode,ItemPointFragment.NODIR);
        //加载卡片
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.fl_point_frameLayout,mItemPointFragment );
//        ft.commit();
        //嵌套fragment。。。。
        ItemPointFragment item =  ItemPointFragment.newInstance(mProjectCode,ItemPointFragment.NODIR,1);
        loadRootFragment(R.id.fl_point_frameLayout,item);

        mBtnOK = (Button) view.findViewById(R.id.btn_OK);

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshData();
            }
        });

        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<PointProgressDetailEntity>>() {
            @Override
            public void onSuccess(HttpResult<PointProgressDetailEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {

                    PointProgressDetailEntity.NodeBean nodeBean = result.getInfo().getNode();

                    mTvPointCircleNum.setText(nodeBean.getNode_id());
                    mTvPointName.setText(nodeBean.getTitle());

                    mPointID = nodeBean.getId();

                    //String test = mBtnOK.getText().toString() + mPointID;
                    mBtnOK.setText(mPointID);

                    String user_code = PreferencesUtils.getString(_mActivity,"user_code");
                    String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
                    String status = "";
                    switch(nodeBean.getStatus()){
                        case "1":
                            status = "未开始";
                            mIvPointCircle.setBackgroundResource(R.drawable.ic_point_nostart);
                            mBtnOK.setVisibility(View.GONE);
                            break;
                        case "10":
                            status = "待进场";
                            mIvPointCircle.setBackgroundResource(R.drawable.ic_point_wait1);
                            mBtnOK.setVisibility(View.VISIBLE);
                            mBtnOK.setText("确认进场");
                            mBtnOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HttpMethods.getInstance().doNodeStartIn(new ProgressSubscriber(mOnSuccessNodeStartIn, _mActivity), user_code, access_token,mPointID);
                                }
                            });
                            break;
                        case "20":
                            status = "待施工";
                            mIvPointCircle.setBackgroundResource(R.drawable.ic_point_wait1);
                            if(nodeBean.getWorker_name()==null || nodeBean.getWorker_name().equals("驻厂工人") ||  nodeBean.getWorker_name().equals("")){
                                mBtnOK.setVisibility(View.VISIBLE);
                                mBtnOK.setText("开始施工");
                                mBtnOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HttpMethods.getInstance().doNodeStartWorking(new ProgressSubscriber(mOnSuccessNodeStartWorking, _mActivity), user_code, access_token,mPointID);
                                    }
                                });
                            }else {
                                mBtnOK.setVisibility(View.GONE);
                            }
                            break;
                        case "30":
                            status = "施工中";
                            mIvPointCircle.setBackgroundResource(R.drawable.ic_point_wait1);
                            if(nodeBean.getWorker_name()==null || nodeBean.getWorker_name().equals("驻厂工人") ||  nodeBean.getWorker_name().equals("")){
                                mBtnOK.setVisibility(View.VISIBLE);
                                mBtnOK.setText("施工完成");
                                mBtnOK.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        HttpMethods.getInstance().doNodeFinishWorking(new ProgressSubscriber(mOnSuccessNodeFinishWorking, _mActivity), user_code, access_token,mPointID);
                                    }
                                });
                            }else {
                                mBtnOK.setVisibility(View.GONE);
                            }
                            break;
                        case "40":
                            status = "待施工员验收";
                            mIvPointCircle.setBackgroundResource(R.drawable.ic_point_wait1);
                            mBtnOK.setVisibility(View.VISIBLE);
                            mBtnOK.setText("确认完成");
                            mBtnOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    HttpMethods.getInstance().doCrewCheckNodeFinish(new ProgressSubscriber(mOnSuccessCheckNodeFinish, _mActivity), user_code, access_token,mPointID,"Y","");
                                }
                            });
                            break;
                        case "42":
                            status = "施工员验收不通过";
                            mIvPointCircle.setBackgroundResource(R.drawable.ic_point_red);
                            mBtnOK.setVisibility(View.GONE);
                            break;
                        case "50":
                            status = "待业主验收";
                            mIvPointCircle.setBackgroundResource(R.drawable.ic_point_wait1);
                            mBtnOK.setVisibility(View.GONE);
                            break;
                        case "52":
                            status = "业主验收不通过";
                            mIvPointCircle.setBackgroundResource(R.drawable.ic_point_red);
                            mBtnOK.setVisibility(View.VISIBLE);
                            mBtnOK.setText("确认完成");
                            mBtnOK.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            break;
                        case "90":
                            status = "停工";
                            mIvPointCircle.setBackgroundResource(R.drawable.ic_point_red);
                            mBtnOK.setVisibility(View.GONE);
                            break;
                        case "100":
                            status = "已完成";
                            mIvPointCircle.setBackgroundResource(R.drawable.ic_point_ok);
                            mBtnOK.setVisibility(View.GONE);
                            break;
                    }


                    List<Fragment> fragmentList = getChildFragmentManager().getFragments();
                    if (fragmentList == null) return;
                    for (int i = fragmentList.size() - 1; i >= 0; i--) {
                        Fragment childFragment = fragmentList.get(i);
                        if (childFragment instanceof SupportFragment && childFragment.getClass().getName().equals(ItemPointFragment.class.getName())) {
                            ItemPointFragment item = (ItemPointFragment) childFragment;
                            item.setItemData(nodeBean);
                        }
                    }
//                    SupportFragment frag1 = getTopChildFragment();
//                    ItemPointFragment frag = findChildFragment(ItemPointFragment.class);
//                    //ItemPointFragment frag = (ItemPointFragment) getTopChildFragment();
//                    frag.setItemData(nodeBean);

                    mNodeBean = nodeBean;

                }
            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){

            }
        };

        mOnSuccessNodeStartIn  = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("已确认进场!")
                                .show();

                        refreshData();
                    }
                }
            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){

            }
        };
        mOnSuccessNodeStartWorking   = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("开始施工已确认!")
                                .show();

                        refreshData();
                    }
                }
            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){

            }
        };
        mOnSuccessNodeFinishWorking   = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("施工完成已确认!")
                                .show();

                        refreshData();
                    }
                }
            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){

            }
        };
        mOnSuccessCheckNodeFinish  = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("已确认完成!")
                                .show();

                        refreshData();
                    }
                }
            }
            @Override
            public void onCompleted(){

            }
            @Override
            public void onError(){

            }
        };

        refreshData();

    }

    private void refreshData(){
        String user_code = PreferencesUtils.getString(_mActivity,"user_code");
        String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
        HttpMethods.getInstance().getNodeDetail(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token,mPointID);
    }

    private int mIndex = 0;
    private int getIndexNum(boolean isleftOrRight){
        if(isleftOrRight){
            if(mIndex>1) mIndex--;
        }else {
            mIndex++;
        }
        return mIndex;
    }
    private void initData() {
        mToolbarTitle.setText("施工节点详情");

        mLLPointArrowLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//                ft.replace(R.id.fl_point_frameLayout, ItemPointFragment.newInstance(mProjectCode,ItemPointFragment.LEFT));
//                ft.commit();
                int index = Integer.valueOf(mTvPointCircleNum.getText().toString());
                if(index >1){
                    int idIndex = Integer.valueOf(mPointID);
                    idIndex--;
                    mPointID = String.valueOf(idIndex);

                    //int index = getIndexNum(true);
                    //mTvPointCircleNum.setText(String.valueOf(index));
                    ItemPointFragment frag = findChildFragment(ItemPointFragment.class);
                    ItemPointFragment fragNew = ItemPointFragment.newInstance(mProjectCode,ItemPointFragment.LEFT,index);
                    frag.setAnimationStyle(sAnimationStyle);
                    fragNew.setAnimationStyle(sAnimationStyle);
                    //frag.popToChild(ItemPointFragment.class,false);
                    frag.replaceFragment(fragNew,false);

                    refreshData();
                }

            }
        });
        mLLPointArrowRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.fl_point_frameLayout, ItemPointFragment.newInstance(mProjectCode,ItemPointFragment.RIGHT));
//                ft.commit();
                int index = Integer.valueOf(mTvPointCircleNum.getText().toString());
                if(index < mTotalIndex){
                    int idIndex = Integer.valueOf(mPointID);
                    idIndex++;
                    mPointID = String.valueOf(idIndex);

                    ItemPointFragment frag = findChildFragment(ItemPointFragment.class);
                    ItemPointFragment fragNew = ItemPointFragment.newInstance(mProjectCode,ItemPointFragment.RIGHT,index);
                    frag.setAnimationStyle(sAnimationStyle);
                    fragNew.setAnimationStyle(sAnimationStyle);
                    //frag.popToChild(ItemPointFragment.class,false);
//                frag.start(fragNew);
//                frag.pop();

                    SupportFragment a = getTopChildFragment();
                    //frag.pop();
                    SupportFragment b = findChildFragment(ItemPointFragment.class);
//                    replaceLoadRootFragment(R.id.fl_point_frameLayout,fragNew,false);
                    frag.replaceFragment(fragNew,false);
                    //fragNew.setItemData(mNodeBean);

                    refreshData();
                }

            }
        });
    }

    private void initDataTemp() {
    }


    @Override
    public void onPause(){
        super.onPause();
    }


}
