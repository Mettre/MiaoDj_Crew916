package cn.chenhai.miaodj_monitor.ui.fragment.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.ui.adapter.DetailBuildDiaryPager1Adapter;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.BuildDiaryEntity;
import cn.chenhai.miaodj_monitor.model.info.BuildDiary_Info;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.base.BaseFragment;
import cn.pedant.SweetAlert.SweetAlertDialog;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 日志
 * <p>
 * Created by ChenHai--霜华 on 2016/6/24. 15:21
 * 邮箱：248866527@qq.com
 */
public class DetailBuildDiaryPager1 extends BaseFragment {
    private static final String ARG_FROM = "arg_from";
    private int mFrom;

    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessInit;
    private SubscriberOnSuccessListener mOnSuccessWriteDiary;

    private RecyclerView mRecycler;
    private LinearLayoutManager mLLmanager;
    private DetailBuildDiaryPager1Adapter mAdapter;
    private PtrClassicFrameLayout mRefreshPtrFrameLayout;
    private LinearLayout mEmptyViewLayout;

    private int pageSize = 10;
    private int pageIndex = 1;

    private List<BuildDiary_Info> mData = new ArrayList<>();

    public static DetailBuildDiaryPager1 newInstance(int from, String projectCode) {
        Bundle args = new Bundle();
        args.putInt(ARG_FROM, from);
        args.putString("projectCode", projectCode);

        DetailBuildDiaryPager1 fragment = new DetailBuildDiaryPager1();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mFrom = args.getInt(ARG_FROM);
            mProjectCode = args.getString("projectCode");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_build_diary_pager, container, false);

        initView(view);

        initPullRefresh(view);

        return view;
    }

    private void initView(View view) {
        mRecycler = (RecyclerView) view.findViewById(R.id.fbdp_recycler);
        mRefreshPtrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.fbdp_ptrFrameLayout);
        mEmptyViewLayout = (LinearLayout) view.findViewById(R.id.empty_view_layout);

        mAdapter = new DetailBuildDiaryPager1Adapter(_mActivity, mData);
        mLLmanager = new LinearLayoutManager(_mActivity);
        mRecycler.setLayoutManager(mLLmanager);
        mRecycler.setAdapter(mAdapter);


        mAdapter.setOnItemBtnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                showPopupWindow(view, mAdapter.getItem(position));
            }
        });


        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<BuildDiaryEntity>>() {
            @Override
            public void onSuccess(HttpResult<BuildDiaryEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    List<BuildDiaryEntity.DrawingsBean> projects = result.getInfo().getDrawings();
                    List<BuildDiary_Info> list = new ArrayList<>();

                    if (projects.size() == 0) {
                        mEmptyViewLayout.setVisibility(View.VISIBLE);
                        mRecycler.setVisibility(View.GONE);
                    } else {
                        mEmptyViewLayout.setVisibility(View.GONE);
                        mRecycler.setVisibility(View.VISIBLE);
                    }


                    for (int i = 0; i < projects.size(); i++) {
                        BuildDiary_Info pageInfo = new BuildDiary_Info();
                        BuildDiaryEntity.DrawingsBean nodeInfo = projects.get(i);

                        pageInfo.setDiary_content(nodeInfo.getInfo());
                        if (nodeInfo.getInfo() != null) {
                            if (nodeInfo.getInfo().equals("")) {
                                pageInfo.setIfContent(false);
                            } else {
                                pageInfo.setIfContent(true);
                            }
                        }

                        String headPath = "";
                        if (nodeInfo.getCrew_headimg() != null) {
                            if (!nodeInfo.getCrew_headimg().equals("")) {
                                headPath = HttpMethods.BASE_ROOT_URL + nodeInfo.getCrew_headimg();
                            }
                        }
                        pageInfo.setImg_portraitPath(headPath);
                        pageInfo.setDate(nodeInfo.getCreatetime());
                        pageInfo.setDayNum(String.valueOf(nodeInfo.getAfter_start()));
                        if (nodeInfo.getCan_edit() != null) {
                            if (nodeInfo.getCan_edit().equals("Y")) {
                                pageInfo.setIfEdit(true);
                            } else if (nodeInfo.getCan_edit().equals("N")) {
                                pageInfo.setIfEdit(false);
                            }
                        }

                        pageInfo.setWorker_name(nodeInfo.getCrew_name());
                        pageInfo.setWorker_type("施工员");

                        list.add(pageInfo);
                    }

                    mData = list;

                    if (pageIndex == 1) {
                        mAdapter.refreshDatas(mData);
                    } else {
                        mAdapter.addDatas(mData);
                    }

                    if (list.size() >= pageSize) {
                        mRefreshPtrFrameLayout.setMode(PtrFrameLayout.Mode.BOTH);
                    } else {
                        mRefreshPtrFrameLayout.setMode(PtrFrameLayout.Mode.REFRESH);
                    }

                    pageIndex++;

                    if (mRefreshPtrFrameLayout != null) mRefreshPtrFrameLayout.refreshComplete();
                }
            }

            @Override
            public void onCompleted() {
                mRefreshPtrFrameLayout.refreshComplete();
            }

            @Override
            public void onError() {
                mRefreshPtrFrameLayout.refreshComplete();
                if (mData.size() == 0) {
                    mEmptyViewLayout.setVisibility(View.VISIBLE);
                    mRecycler.setVisibility(View.GONE);
                } else {
                    mEmptyViewLayout.setVisibility(View.GONE);
                    mRecycler.setVisibility(View.VISIBLE);
                }

            }
        };

        mOnSuccessWriteDiary = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("成功")
                            .setContentText("添加日志成功")
                            .setConfirmText("知道了")
                            .show();
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                    }
                    pageIndex = 1;
                    refreshData();
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {

            }
        };

        refreshData();
    }


    private void initPullRefresh(View view) {
        mRefreshPtrFrameLayout.setLastUpdateTimeRelateObject(this);

        // the following are default settings
        mRefreshPtrFrameLayout.setResistance(1.7f); // you can also set foot and header separately
        mRefreshPtrFrameLayout.setRatioOfHeaderHeightToRefresh(1.2f);
        mRefreshPtrFrameLayout.setDurationToClose(1000);  // you can also set foot and header separately
        // default is false
        mRefreshPtrFrameLayout.setPullToRefresh(false);

        // default is true
        mRefreshPtrFrameLayout.setKeepHeaderWhenRefresh(true);

        mRefreshPtrFrameLayout.setPtrHandler(new PtrDefaultHandler2() {

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshData();
                    }
                }, 300);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndex = 1;
                        refreshData();
                    }
                }, 300);
            }

            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return super.checkCanDoLoadMore(frame, mRecycler, footer);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return super.checkCanDoRefresh(frame, mRecycler, header);
//                return checkCanDoRefreshLocal();
            }
        });

    }


    private void refreshData() {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().getBuildDiary(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token, mProjectCode, pageIndex, pageSize);
    }

    protected void updateData() {

    }

    private void scrollToTop() {
        mRecycler.smoothScrollToPosition(0);
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    /**
     * ---------------------------PoputWindow--------------------------------
     */
    private PopupWindow mPopupWindow;


    private LinearLayout mPopupDiary;
    private TextView mTvDiaryName;
    private TextView mTvDiaryDayNum;
    private TextView mTvDiaryDate;
    private LinearLayout mLlPopupClose;
    private EditText mTvDiaryAddContent;

    private TagFlowLayout mTagsFlowlayout;
    private TagAdapter<String> mTagAdapter;

    private Button mBtnDiarySubmit;


    /**
     * 初始化popWindow
     */
    private void initPopWindow(View popView, PopupWindow popupWindow) {

        //popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        //popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);


        mPopupDiary = (LinearLayout) popView.findViewById(R.id.popup_diary);

        mLlPopupClose = (LinearLayout) popView.findViewById(R.id.ll_popup_close);
        mTvDiaryName = (TextView) popView.findViewById(R.id.tv_diary_name);
        mTvDiaryDayNum = (TextView) popView.findViewById(R.id.tv_diary_dayNum);
        mTvDiaryDate = (TextView) popView.findViewById(R.id.tv_diary_date);
        mTvDiaryAddContent = (EditText) popView.findViewById(R.id.tv_diary_add_content);

        mTagsFlowlayout = (TagFlowLayout) popView.findViewById(R.id.tags_flowlayout);

        mBtnDiarySubmit = (Button) popView.findViewById(R.id.btn_diary_submit);

    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha, float bgDim) {
        WindowManager.LayoutParams lp = _mActivity.getWindow().getAttributes();
        lp.dimAmount = bgDim;
        lp.alpha = bgAlpha; //0.0-1.0
        _mActivity.getWindow().setAttributes(lp);

        _mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     *
     * @author cg
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f, 0.1f);
        }
    }


    private String commonTags[] = {"施工进度正常进行中...", "材料进场", "由于xxx原因导致施工节点耽误x天时间", "防水测试成功完成",
            "安装地板前期完成", "厨房墙砖和地砖铺贴", "卫生间洁具准备安装"};

    private void showPopupWindow(View view, BuildDiary_Info pageInfo) {

        // 一个自定义的布局，作为显示的内容
        View popView = LayoutInflater.from(_mActivity).inflate(R.layout.popup_build_diary, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //初始化
        initPopWindow(popView, mPopupWindow);

        mTvDiaryDayNum.setText(pageInfo.getDayNum());
        mTvDiaryDate.setText(pageInfo.getDate());

        final LayoutInflater mInflater = LayoutInflater.from(_mActivity);
//        GridWorkerAdapter gridAdapter = new GridWorkerAdapter(_mActivity, Arrays.asList(commonTags));
//        mGridPopupWorkerType.setAdapter(gridAdapter);
        mTagAdapter = new TagAdapter<String>(commonTags) {

            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tag_textview,
                        mTagsFlowlayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mTagsFlowlayout.setAdapter(mTagAdapter);
        //mTagAdapter.setSelectedList(1,3,5,7,8,9);
        mTagsFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                //Toast.makeText(getActivity(), mVals[position], Toast.LENGTH_SHORT).show();
                //view.setVisibility(View.GONE);

                return true;
            }
        });


        mTagsFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                int position = 0;
                StringBuilder tagTotal = new StringBuilder();
                Iterator<Integer> iter = selectPosSet.iterator();
                while (iter.hasNext()) {
                    position = iter.next();
                    tagTotal.append(commonTags[position]);
                }
                mTvDiaryAddContent.setText(tagTotal);

                //getActivity().setTitle("choose:" + selectPosSet.toString());
            }
        });

        // 设置按钮的点击事件
        mLlPopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                //测试
            }
        });

        mBtnDiarySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strContent = mTvDiaryAddContent.getText().toString();
                if (TextUtils.isEmpty(strContent.trim())) {
                    Toast.makeText(_mActivity, "请填写日志内容！!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                HttpMethods.getInstance().doAddProjectDiary(new ProgressSubscriber(mOnSuccessWriteDiary, _mActivity), user_code, access_token, mProjectCode, mTvDiaryAddContent.getText().toString());
                //mPopupWindow.dismiss();
            }
        });


        /** 禁止点击外部区域取消popup windows*/
        LinearLayout layouttemp = (LinearLayout) popView
                .findViewById(R.id.popup_diary);
        layouttemp.setFocusable(true);
        layouttemp.setFocusableInTouchMode(true);
        layouttemp.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 手机键盘上的返回键
                switch (keyCode) {
                    case KeyEvent.KEYCODE_BACK:
                        mPopupWindow.dismiss();
                        break;
                }
                return false;
            }
        });
        /** ----------------------------------------------*/

        mPopupWindow.setTouchable(true);

        //获取焦点
        mPopupWindow.setFocusable(true);

        backgroundAlpha(0.3f, 1f);//透明度
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());

        mPopupWindow.update();
        if (!mPopupWindow.isShowing()) {
            //设置显示位置
            mPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }

    }
    /** -------------------------------------------------------------------------------------*/
}
