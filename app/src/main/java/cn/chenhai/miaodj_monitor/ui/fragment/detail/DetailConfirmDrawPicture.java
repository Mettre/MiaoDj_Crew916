package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.ui.view_custom.ExpandableLayout.ExpandableLayout;
import cn.chenhai.miaodj_monitor.ui.view_custom.ExpandableLayout.ExpandableLayoutListenerAdapter;
import cn.chenhai.miaodj_monitor.ui.view_custom.ExpandableLayout.ExpandableLinearLayout;
import cn.chenhai.miaodj_monitor.ui.view_custom.ExpandableLayout.Utils;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.CheckPictureEntity;
import cn.chenhai.miaodj_monitor.model.entity.ConfirmDrawPicEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment;
import cn.chenhai.miaodj_monitor.ui.module.preview.FiveGridView;
import cn.chenhai.miaodj_monitor.ui.module.preview.FiveGridViewClickAdapter;
import cn.chenhai.miaodj_monitor.ui.module.preview.ImageInfo;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 图纸确认
 * <p>
 * Created by ChenHai--霜华 on 2016/7/5. 14:55
 * 邮箱：248866527@qq.com
 */
public class DetailConfirmDrawPicture extends BaseBackFragment {
    private static final String ARG_ITEM = "ProjectCode";

    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessListener;
    private SubscriberOnSuccessListener mOnSuccessListenerOK;
    private SubscriberOnSuccessListener mOnSuccessListenerCancel;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private Button mBtnPictureOK;
    private TextView mTvPictureOK;
    private Button mBtnPictureCancel;
    private TextView mTvPictureCancel;
    private LinearLayout mLlPictureContent;
    private TextView mLlPictureTitle;
    private ImageView mLlPictureArrow;
    private ExpandableLinearLayout mPictureExpandableLayout1;
    private FiveGridView mFiveGrid;
    // private NineGridView mNineGrid;

    //负责存储布尔值的pair
    private SparseBooleanArray expandState = new SparseBooleanArray();

    public static DetailConfirmDrawPicture newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        DetailConfirmDrawPicture fragment = new DetailConfirmDrawPicture();
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
        View view = inflater.inflate(R.layout.fragment_draw_picture_confirm, container, false);
        initView(view);
        initData();
        //initDataTemp();
        return view;
    }

    private void initView(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);

        mBtnPictureOK = (Button) view.findViewById(R.id.btn_picture_OK);
        mTvPictureOK = (TextView) view.findViewById(R.id.tv_picture_OK);
        mBtnPictureCancel = (Button) view.findViewById(R.id.btn_picture_Cancel);
        mTvPictureCancel = (TextView) view.findViewById(R.id.tv_picture_Cancel);

        mLlPictureContent = (LinearLayout) view.findViewById(R.id.ll_picture_content);
        mLlPictureTitle = (TextView) view.findViewById(R.id.ll_picture_title);
        mLlPictureArrow = (ImageView) view.findViewById(R.id.ll_picture_arrow);
        mPictureExpandableLayout1 = (ExpandableLinearLayout) view.findViewById(R.id.picture_expandableLayout1);
        mFiveGrid = (FiveGridView) view.findViewById(R.id.fiveGrid);
        // mNineGrid = (NineGridView) view.findViewById(R.id.nineGrid);

        mOnSuccessListener = new SubscriberOnSuccessListener<HttpResult<CheckPictureEntity>>() {
            @Override
            public void onSuccess(HttpResult<CheckPictureEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    List<CheckPictureEntity.DrawingBean> projects = result.getInfo().getDrawing();
                    ArrayList<ImageInfo> imageInfoList = new ArrayList<>();
                    for (int i = 0; i < projects.size(); i++) {
                        ImageInfo info1 = new ImageInfo();
                        CheckPictureEntity.DrawingBean drawInfo = projects.get(i);

                        info1.setThumbnailUrl(HttpMethods.BASE_ROOT_URL + drawInfo.getImgurl());
                        info1.setBigImageUrl(HttpMethods.BASE_ROOT_URL + drawInfo.getImgurl());

                        imageInfoList.add(info1);
                    }

                    mLlPictureTitle.setText("图纸内容(" + imageInfoList.size() + ")");

                    mFiveGrid.setAdapter(new FiveGridViewClickAdapter(_mActivity, imageInfoList));
                    //mNineGrid.setAdapter(new NineGridViewClickAdapter(_mActivity, imageInfoList));

                    switch (result.getInfo().getDrawing_status()) {
                        case "1":
                            mBtnPictureOK.setVisibility(View.GONE);
                            mTvPictureOK.setVisibility(View.GONE);
                            mBtnPictureCancel.setVisibility(View.GONE);
                            mTvPictureCancel.setVisibility(View.GONE);
                            break;
                        case "20":
                            mBtnPictureOK.setVisibility(View.VISIBLE);
                            mTvPictureOK.setVisibility(View.VISIBLE);
                            mBtnPictureCancel.setVisibility(View.VISIBLE);
                            mTvPictureCancel.setVisibility(View.VISIBLE);
                            mBtnPictureOK.setEnabled(false);
                            mBtnPictureCancel.setEnabled(false);
                            mTvPictureOK.setText("在业主确认图纸后，施工员才可确认图纸！");
                            mTvPictureCancel.setText("在业主确认图纸后，施工员才可确认图纸！");
                            break;
                        case "21":
                            mBtnPictureOK.setVisibility(View.VISIBLE);
                            mTvPictureOK.setVisibility(View.VISIBLE);
                            mBtnPictureCancel.setVisibility(View.VISIBLE);
                            mTvPictureCancel.setVisibility(View.VISIBLE);
                            mBtnPictureOK.setEnabled(false);
                            mBtnPictureCancel.setEnabled(false);
                            mTvPictureOK.setText("在业主确认图纸后，施工员才可确认图纸！");
                            mTvPictureCancel.setText("在业主确认图纸后，施工员才可确认图纸！");
                            break;
                        case "30":
                            mBtnPictureOK.setVisibility(View.VISIBLE);
                            mTvPictureOK.setVisibility(View.VISIBLE);
                            mBtnPictureCancel.setVisibility(View.VISIBLE);
                            mTvPictureCancel.setVisibility(View.VISIBLE);
                            mBtnPictureOK.setEnabled(true);
                            mBtnPictureCancel.setEnabled(true);
                            mTvPictureOK.setText("请认真核对图纸内容和数据，核对无误后点击确认按钮！");
                            mTvPictureCancel.setText("如果图纸中的内容有问题或有需要修改的地方，点击不确认按钮让设计师重新修改后再确认！");
                            break;
                        case "45":
                            mBtnPictureOK.setVisibility(View.GONE);
                            mTvPictureOK.setVisibility(View.GONE);
                            mBtnPictureCancel.setVisibility(View.GONE);
                            mTvPictureCancel.setVisibility(View.GONE);
                            break;
                        case "46":
                            mBtnPictureOK.setVisibility(View.GONE);
                            mTvPictureOK.setVisibility(View.GONE);
                            mBtnPictureCancel.setVisibility(View.GONE);
                            mTvPictureCancel.setVisibility(View.GONE);
                            break;
                        default:
                            mBtnPictureOK.setVisibility(View.GONE);
                            mTvPictureOK.setVisibility(View.GONE);
                            mBtnPictureCancel.setVisibility(View.GONE);
                            mTvPictureCancel.setVisibility(View.GONE);
                            break;
                    }

                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {

            }
        };

        mOnSuccessListenerOK = new SubscriberOnSuccessListener<HttpResult<ConfirmDrawPicEntity>>() {
            @Override
            public void onSuccess(HttpResult<ConfirmDrawPicEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("已提交!")
                            .setConfirmText("关闭")
                            .show();
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {

            }
        };
        mOnSuccessListenerCancel = new SubscriberOnSuccessListener<HttpResult<ConfirmDrawPicEntity>>() {
            @Override
            public void onSuccess(HttpResult<ConfirmDrawPicEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("已提交!")
                            .setConfirmText("关闭")
                            .show();
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {

            }
        };


        updateData();
    }


    private void initData() {
        mToolbarTitle.setText("图纸");

        mBtnPictureOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确认图纸?")
                        .setContentText("确定要确认图纸吗!")
                        .setCancelText("取消")
                        .setConfirmText("确定！")
                        .showCancelButton(true)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                                HttpMethods.getInstance().doCrewCheckDrawingOK(new ProgressSubscriber(mOnSuccessListenerOK, _mActivity), user_code, access_token, mProjectCode, "Y");

                                sDialog.dismissWithAnimation();
//                                sDialog.setTitleText("已确认!")
//                                        //.setContentText("Your imaginary file has been deleted!")
//                                        .setConfirmText("关闭")
//                                        .showCancelButton(false)
//                                        .setCancelClickListener(null)
//                                        .setConfirmClickListener(null)
//                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();
            }
        });
        mBtnPictureCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SweetAlertDialog(_mActivity, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("图纸有问题，不确认?")
                        .setContentText("确定不确认吗!")
                        .setCancelText("取消")
                        .setConfirmText("确定！")
                        .showCancelButton(true)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                                HttpMethods.getInstance().doCrewCheckDrawingOK(new ProgressSubscriber(mOnSuccessListenerCancel, _mActivity), user_code, access_token, mProjectCode, "N");
                                sDialog.dismissWithAnimation();

                            }
                        })
                        .show();
            }
        });


        mPictureExpandableLayout1.setInterpolator(Utils.createInterpolator(Utils.BOUNCE_INTERPOLATOR));
        //mPictureExpandableLayout1.setInterpolator(Utils.createInterpolator(Utils.LINEAR_OUT_SLOW_IN_INTERPOLATOR));
        mPictureExpandableLayout1.setExpanded(expandState.get(1));
        mPictureExpandableLayout1.setListener(new ExpandableLayoutListenerAdapter() {
            @Override
            public void onPreOpen() {
                createRotateAnimator(mLlPictureArrow, 0f, 180f).start();
                expandState.put(1, true);
            }

            @Override
            public void onPreClose() {
                createRotateAnimator(mLlPictureArrow, 180f, 0f).start();
                expandState.put(1, false);
            }
        });

        mLlPictureContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onClickButton(mPictureExpandableLayout1);
                //mPictureExpandableLayout1.expand();
            }
        });
    }

    private void onClickButton(final ExpandableLayout expandableLayout) {
        expandableLayout.toggle();
    }

    /**
     * 创建 旋转动画！！！！
     */
    public ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR));
        return animator;
    }

    private void updateData() {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().getDrawPicture(new ProgressSubscriber(mOnSuccessListener, _mActivity), user_code, access_token, mProjectCode);
    }

}
