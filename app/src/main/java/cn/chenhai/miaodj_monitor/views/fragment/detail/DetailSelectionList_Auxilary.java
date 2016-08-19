package cn.chenhai.miaodj_monitor.views.fragment.detail;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okhttpserver.download.DownloadManager;
import com.lzy.okhttpserver.listener.UploadListener;
import com.lzy.okhttpserver.upload.UploadInfo;
import com.lzy.okhttpserver.upload.UploadManager;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.AbsCallback;
import com.lzy.okhttputils.request.BaseRequest;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cn.chenhai.miaodj_monitor.Manifest;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.adapter.DetailSelectAuxiDeliveryAdapter;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.UploadImgResult;
import cn.chenhai.miaodj_monitor.model.entity.EmptyEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListAuxilaryEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListEntity;
import cn.chenhai.miaodj_monitor.model.entity.SelectionListMainEntity;
import cn.chenhai.miaodj_monitor.model.info.Material_auxiliary_Info;
import cn.chenhai.miaodj_monitor.network_proxy.HttpMethods;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.view_custom.NumberProgressBar;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment_Swip;
import cn.pedant.SweetAlert.SweetAlertDialog;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.PhotoPreview;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ChenHai--霜华 on 2016/8/9. 14:47
 * 邮箱：248866527@qq.com
 */
public class DetailSelectionList_Auxilary extends BaseBackFragment_Swip {

    private String mDeliver_code;
    private String mSignForPicturePath = "";

    private SubscriberOnSuccessListener mOnSuccessAuxilary;
    private SubscriberOnSuccessListener mOnSuccessSignFor;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private TextView mTvAuxiOrder;
    private TextView mTvAuxiOrderCode;
    private TextView mTvAuxiDelivery;
    private TextView mTvAuxiDeliveryTime;
    private TextView mTvAuxiArrive;
    private TextView mTvAuxiArriveTime;
    private FrameLayout llAuxiSignForTime;
    private TextView tvAuxiSignForTime;


    private RecyclerView mRecyclerAuxi;
    private Button mBtnOK;

    private LinearLayoutManager mLLmanager1;
    private DetailSelectAuxiDeliveryAdapter mAuxiAdapter;


    public static DetailSelectionList_Auxilary newInstance(String deliver_code) {
        DetailSelectionList_Auxilary fragment = new DetailSelectionList_Auxilary();
        Bundle args = new Bundle();
        args.putString("deliver_code",deliver_code);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mDeliver_code = args.getString("deliver_code");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection_auxilary_detail, container, false);

        //initReceiver();
        initView(view);
        initData();

        //return view;
        return attachToSwipeBack(view);
    }


    private void initView(View view) {

        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) view.findViewById(R.id.toolbar_title);

        initToolbarNav(mToolbar);

        mTvAuxiOrder = (TextView) view.findViewById(R.id.tv_auxi_order);
        mTvAuxiOrderCode = (TextView) view.findViewById(R.id.tv_auxi_order_code);
        mTvAuxiDelivery = (TextView) view.findViewById(R.id.tv_auxi_delivery);
        mTvAuxiDeliveryTime = (TextView) view.findViewById(R.id.tv_auxi_delivery_time);
        mTvAuxiArrive = (TextView) view.findViewById(R.id.tv_auxi_arrive);
        mTvAuxiArriveTime = (TextView) view.findViewById(R.id.tv_auxi_arrive_time);
        llAuxiSignForTime = (FrameLayout) view.findViewById(R.id.ll_auxi_sign_for_time);
        tvAuxiSignForTime = (TextView) view.findViewById(R.id.tv_auxi_sign_for_time);

        mRecyclerAuxi = (RecyclerView) view.findViewById(R.id.recycler_auxi);
        mBtnOK = (Button) view.findViewById(R.id.btn_OK);

        mAuxiAdapter = new DetailSelectAuxiDeliveryAdapter(_mActivity);
        mLLmanager1 = new LinearLayoutManager(_mActivity);
        mRecyclerAuxi.setLayoutManager(mLLmanager1);
        mRecyclerAuxi.setAdapter(mAuxiAdapter);

        mAuxiAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                //start(DetailPointProgressFragment.newInstance(mProjectCode));
            }
        });

        mBtnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v);
            }
        });

        mOnSuccessAuxilary = new SubscriberOnSuccessListener<HttpResult<SelectionListAuxilaryEntity>>() {
            @Override
            public void onSuccess(HttpResult<SelectionListAuxilaryEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {
                        SelectionListAuxilaryEntity.DeliverOrderBean orderBean = result.getInfo().getDeliver_order();
                        List<SelectionListAuxilaryEntity.OrderListBean> beanInfos = result.getInfo().getOrder_list();

                        switch (orderBean.getStatus()){
                            case "1":
                                llAuxiSignForTime.setVisibility(View.GONE);
                                mBtnOK.setVisibility(View.VISIBLE);
                                break;
                            case "2":
                                llAuxiSignForTime.setVisibility(View.VISIBLE);
                                mBtnOK.setVisibility(View.GONE);
                                tvAuxiSignForTime.setText(orderBean.getArrive_time());
                                break;
                            default:
                                llAuxiSignForTime.setVisibility(View.GONE);
                                mBtnOK.setVisibility(View.GONE);
                                break;
                        }
                        mTvAuxiOrderCode.setText(orderBean.getDeliver_code());
                        mTvAuxiDeliveryTime.setText(orderBean.getStart_deliver_time());
                        mTvAuxiArriveTime.setText(orderBean.getExpect_arrive_time());

                        List<Material_auxiliary_Info> list2 = new ArrayList<>();

                        for (int i=0; i<beanInfos.size(); i++){
                            SelectionListAuxilaryEntity.OrderListBean beanInfo = beanInfos.get(i);
                            Material_auxiliary_Info auxiliaryInfo = new Material_auxiliary_Info();

//                            if(beanInfo2.getImages().size()!=0){
//                                if(beanInfo2.getImages().get(0).getPath()!=null){
//                                    String path = HttpMethods.BASE_ROOT_URL + beanInfo.getImages().get(0).getPath();
//                                    materialInfo.setImg_portraitPath(path);
//                                }
//                            }
                            if(beanInfo.getThumb_image()!=null && !beanInfo.getThumb_image().equals("")){
                                String path = HttpMethods.BASE_ROOT_URL + beanInfo.getThumb_image();
                                auxiliaryInfo.setImg_portraitPath(path);
                            }
                            auxiliaryInfo.setAuxiliaryNameDes(beanInfo.getTitle());
                            auxiliaryInfo.setAuxiliaryBrand(beanInfo.getBrand_name());
                            auxiliaryInfo.setAuxiliarySpecs(beanInfo.getSpecs());
                            auxiliaryInfo.setAuxiliarySpecs2(beanInfo.getSpecs2());
                            auxiliaryInfo.setAuxiliaryCount(beanInfo.getAmount());
                            auxiliaryInfo.setAuxiliaryCountUnit(beanInfo.getUnit());

                            auxiliaryInfo.setStatus(beanInfo.getStatus());

                            list2.add(auxiliaryInfo);
                        }

                        mAuxiAdapter.refreshDatas(list2);
                        mAuxiAdapter.notifyDataSetChanged();
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

        mOnSuccessSignFor = new SubscriberOnSuccessListener<HttpResult<EmptyEntity>>() {
            @Override
            public void onSuccess(HttpResult<EmptyEntity> result) {
                if(result.getCode() == 3015) {
                    Toast.makeText(_mActivity,"登录验证失效，请重新登录！！",Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if(result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity,SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("货物签收状态已提交！")
                                .setConfirmText("知道了")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        refreshData();
                                        sweetAlertDialog.dismissWithAnimation();
                                        if (mPopupWindow.isShowing()) {
                                            mPopupWindow.dismiss();
                                        }
                                    }
                                })
                                .show();
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

    private void initData(){
        mToolbarTitle.setText("辅材辅料配送单");

    }

    private void refreshData(){
        String user_code = PreferencesUtils.getString(_mActivity,"user_code");
        String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
        HttpMethods.getInstance().getSelectionAuxiliaryDeliverOrder(new ProgressSubscriber(mOnSuccessAuxilary, _mActivity), user_code, access_token,mDeliver_code);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }



    private ArrayList<String> mPhotoPaths;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    enum RequestCode {
//        Button(R.id.iv_popup_pic_upload),
//        ButtonNoCamera(R.id.button_no_camera),
        ButtonOnePhoto(R.id.iv_popup_pic_upload);

        @IdRes
        final int mViewId;
        RequestCode(@IdRes int viewId) {
            mViewId = viewId;
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {

            //List<String> photos = null;
            mPhotoPaths = null;
            if (data != null) {
                mPhotoPaths = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (mPhotoPaths != null) {
                selectedPhotos.addAll(mPhotoPaths);
            }

            mIvPopupPicUpload.setVisibility(View.GONE);
            mLlPopupPic.setVisibility(View.VISIBLE);
            /**************************************************************/
            Uri uri = Uri.fromFile(new File(selectedPhotos.get(0)));
            Glide.with(_mActivity)
                    .load(uri)
                    .centerCrop()
                    .thumbnail(0.1f)
                    .placeholder(R.drawable.ic_camera)
                    .error(me.iwf.photopicker.R.drawable.__picker_ic_broken_image_black_48dp)
                    .into(mIvPopupPic);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // If request is cancelled, the result arrays are empty.
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            // permission was granted, yay!
            onClick(RequestCode.values()[requestCode].mViewId);

        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            Toast.makeText(_mActivity, "No read storage permission! Cannot perform the action.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
            case Manifest.permission.CAMERA:
                // No need to explain to user as it is obvious
                return false;
            default:
                return true;
        }
    }
    private void checkPermission(@NonNull RequestCode requestCode) {

        int readStoragePermissionState = ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.READ_EXTERNAL_STORAGE);
        int cameraPermissionState = ContextCompat.checkSelfPermission(_mActivity, Manifest.permission.CAMERA);

        boolean readStoragePermissionGranted = readStoragePermissionState != PackageManager.PERMISSION_GRANTED;
        boolean cameraPermissionGranted = cameraPermissionState != PackageManager.PERMISSION_GRANTED;

        if (readStoragePermissionGranted || cameraPermissionGranted) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(_mActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(_mActivity,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                String[] permissions;
                if (readStoragePermissionGranted && cameraPermissionGranted) {
                    permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA };
                } else {
                    permissions = new String[] {
                            readStoragePermissionGranted ? Manifest.permission.READ_EXTERNAL_STORAGE
                                    : Manifest.permission.CAMERA
                    };
                }
                ActivityCompat.requestPermissions(_mActivity,
                        permissions,
                        requestCode.ordinal());
            }

        } else {
            // Permission granted
            onClick(requestCode.mViewId);
        }

    }
    public static final int RESULT_LOCAL = 233;
    private void onClick(@IdRes int viewId) {

        switch (viewId) {

            case R.id.iv_popup_pic_upload: {
                //Intent intent = new Intent(MainActivity.this, PhotoPickerActivity.class);
                //PhotoPickerIntent.setPhotoCount(intent, 1);
                //PhotoPickerIntent.setShowCamera(intent, true);
                //startActivityForResult(intent, REQUEST_CODE);

//                PhotoPicker.builder()
//                        .setPhotoCount(1)
//                        .setGridColumnCount(3)
//                        .start(_mActivity, DetailSelectionList_Auxilary.this);

                Bundle mPickerOptionsBundle = new Bundle();;
                Intent mPickerIntent = new Intent();
                mPickerIntent.setClass(_mActivity, PhotoPickerActivity.class);
                mPickerOptionsBundle.putInt("MAX_COUNT", 1);
                mPickerOptionsBundle.putInt("column", 3);
                mPickerIntent.putExtras(mPickerOptionsBundle);

                startActivityForResult(mPickerIntent,RESULT_LOCAL);
                break;
            }


        }
    }


    private class ProgressUpCallBack<T> extends AbsCallback<T> {
        private Class<T> clazz;
        private Type type;

        public ProgressUpCallBack(Activity activity, Class<T> clazz) {
            this.clazz = clazz;
        }
        public ProgressUpCallBack(Type type) {
            this.type = type;
        }

        @Override
        public void onBefore(BaseRequest request) {
            super.onBefore(request);
            mTvProgress.setText("正在上传中...");
        }

        @Override
        public void onResponse(boolean isFromCache, T s, Request request, Response response) {
            //handleResponse(isFromCache, s, request, response);
            mTvProgress.setText("上传完成");
            UploadImgResult info = (UploadImgResult)s ;
            mSignForPicturePath = info.getPicnamefile();

            if(!mSignForPicturePath.equals("")){
                String user_code = PreferencesUtils.getString(_mActivity,"user_code");
                String access_token =  PreferencesUtils.getString(_mActivity,"access_token");
                HttpMethods.getInstance().signForAuxilaryMaterial(new ProgressSubscriber(mOnSuccessSignFor, _mActivity), user_code, access_token,mDeliver_code,mSignForPicturePath);
            }
        }

        @Override
        public void onError(boolean isFromCache, Call call, @Nullable Response response, @Nullable Exception e) {
            super.onError(isFromCache, call, response, e);
            //handleError(isFromCache, call, response);
            mTvProgress.setText("上传出错");
        }

        @Override
        public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
            System.out.println("upProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);

            String downloadLength = Formatter.formatFileSize(_mActivity, currentSize);
            String totalLength = Formatter.formatFileSize(_mActivity, totalSize);
            mDownloadSize.setText(downloadLength + "/" + totalLength);
//            String netSpeed = Formatter.formatFileSize(getApplicationContext(), networkSpeed);
//            tvNetSpeed.setText(netSpeed + "/S");
//            tvProgress.setText((Math.round(progress * 10000) * 1.0f / 100) + "%");
            mPbProgress.setMax(100);
            mPbProgress.setProgress((int) (progress * 100));
        }

        //该方法是子线程处理，不能做ui相关的工作
        @Override
        public T parseNetworkResponse(Response response) throws Exception {
            String responseData = response.body().string();
            if (TextUtils.isEmpty(responseData)) return null;

            /**
             * 一般来说，服务器返回的响应码都包含 code，msg，data 三部分，在此根据自己的业务需要完成相应的逻辑判断
             * 以下只是一个示例，具体业务具体实现
             */
            JSONObject jsonObject = new JSONObject(responseData);
            final String msg = jsonObject.optString("msg", "");
            final int code = jsonObject.optInt("code", 0);
            String data = jsonObject.optString("info", "");
            switch (code) {
                case 200:
                    /**
                     * code = 200 代表成功，默认实现了Gson解析成相应的实体Bean返回，可以自己替换成fastjson等
                     * 对于返回参数，先支持 String，然后优先支持class类型的字节码，最后支持type类型的参数
                     */
                    if (clazz == String.class) return (T) data;
                    if (clazz != null) return new Gson().fromJson(data, clazz);
                    if (type != null) return new Gson().fromJson(data, type);
                    break;

                case 3017:
                    //比如：用户收取信息已过期，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                    throw new IllegalStateException("操作失败");
                case 3020:
                    //比如：用户账户被禁用，在此实现相应的逻辑，弹出对话或者跳转到其他页面等,该抛出错误，会在onError中回调。
                    throw new IllegalStateException("参数不完整");
                default:
                    throw new IllegalStateException("错误代码：" + code + "，错误信息：" + msg);
            }
            //如果要更新UI，需要使用handler，可以如下方式实现，也可以自己写handler
            OkHttpUtils.getInstance().getDelivery().post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OkHttpUtils.getContext(), "错误代码：" + code + "，错误信息：" + msg, Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
    }



    /** popupWindow控件 */
    private PopupWindow mPopupWindow;

    private LinearLayout mLlPopupClose;
    private ImageView mIvPopupPicUpload;
    private LinearLayout mLlPopupPic;
    private ImageView mIvPopupPic;
    private ImageView mIvPopupPicDelete;
    private TextView mDownloadSize;
    private NumberProgressBar mPbProgress;
    private TextView mTvProgress;
    private Button mBtnPopupDone;
    /**---------------------------PoputWindow--------------------------------*/
    /**
     * 初始化popWindow
     * */
    private void initPopWindow(View popView,PopupWindow popupWindow) {

        //popupWindow = new PopupWindow(popView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        //popupWindow.setBackgroundDrawable(getResources().getDrawable(R.color.white));
        //popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        //popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);

        mLlPopupClose = (LinearLayout) popView.findViewById(R.id.ll_popup_close);
        mIvPopupPicUpload = (ImageView) popView.findViewById(R.id.iv_popup_pic_upload);
        mLlPopupPic = (LinearLayout) popView.findViewById(R.id.ll_popup_pic);
        mIvPopupPic = (ImageView) popView.findViewById(R.id.iv_popup_pic);
        mIvPopupPicDelete = (ImageView) popView.findViewById(R.id.iv_popup_pic_delete);
        mDownloadSize = (TextView) popView.findViewById(R.id.downloadSize);
        mPbProgress = (NumberProgressBar) popView.findViewById(R.id.pbProgress);
        mTvProgress = (TextView) popView.findViewById(R.id.tvProgress);
        mBtnPopupDone = (Button) popView.findViewById(R.id.btn_popup_done);

    }

    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha,float bgDim)
    {
        WindowManager.LayoutParams lp = _mActivity.getWindow().getAttributes();
        lp.dimAmount = bgDim;
        lp.alpha = bgAlpha; //0.0-1.0
        _mActivity.getWindow().setAttributes(lp);

        _mActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //_mActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

    }
    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     *
     */
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            backgroundAlpha(1f,0.1f);
        }
    }


    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View popView = LayoutInflater.from(_mActivity).inflate(R.layout.popup_auxilary_sign_for, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //初始化
        initPopWindow(popView,mPopupWindow);

        // 设置按钮的点击事件
        mLlPopupClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        mIvPopupPicUpload.setVisibility(View.VISIBLE);
        mLlPopupPic.setVisibility(View.GONE);

        mIvPopupPicUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(RequestCode.ButtonOnePhoto);
            }
        });
        mIvPopupPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhotoPreview.builder()
                        .setPhotos(selectedPhotos)
                        .start(_mActivity,DetailSelectionList_Auxilary.this);
            }
        });
        mIvPopupPicDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIvPopupPicUpload.setVisibility(View.VISIBLE);
                mLlPopupPic.setVisibility(View.GONE);
            }
        });



        mBtnPopupDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mIvPopupPicUpload.getVisibility() == View.VISIBLE) {
                    Toast.makeText(_mActivity, "请选择要上传的签收单！", Toast.LENGTH_SHORT).show();
                    return;
                }

                ArrayList<File> files = new ArrayList<>();
                if (mPhotoPaths != null && mPhotoPaths.size() > 0) {
                    for (int i = 0; i < mPhotoPaths.size(); i++) {
                        files.add(new File(mPhotoPaths.get(i)));
                    }
                }

                String url = HttpMethods.BASE_URL + "App/Public/upload_imgs";
                //拼接参数
                OkHttpUtils.post(url)//
                        .tag(this)//
                        .headers("header1", "headerValue1")//
                        .headers("header2", "headerValue2")//
                        .params("param1", "paramValue1")//
                        .params("param2", "paramValue2")//
//                .params("file1",new File("文件路径"))   //这种方式为一个key，对应一个文件
//                .params("file2",new File("文件路径"))
//                .params("file3",new File("文件路径"))
                        .addFileParams("file", files)           // 这种方式为同一个key，上传多个文件
                        .execute(new ProgressUpCallBack<>(_mActivity, UploadImgResult.class));


            }
        });


        /** 禁止点击外部区域取消popup windows*/
        LinearLayout layouttemp = (LinearLayout) popView
                .findViewById(R.id.popup_auxi_sign_for);
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

        backgroundAlpha(0.3f,1f);//透明度
        mPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //添加pop窗口关闭事件
        mPopupWindow.setOnDismissListener(new poponDismissListener());

        mPopupWindow.update();
        if (!mPopupWindow.isShowing()) {
            //设置显示位置
            mPopupWindow.showAtLocation(view, Gravity.CENTER ,0,0);
        }

    }



    /** -------------------------------------------------------------------------------------*/
}
