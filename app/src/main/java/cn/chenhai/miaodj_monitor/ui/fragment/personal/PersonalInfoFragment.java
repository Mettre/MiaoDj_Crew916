package cn.chenhai.miaodj_monitor.ui.fragment.personal;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.JSONUtils;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.service.commonlib.utils.UploadUtil;
import cn.chenhai.miaodj_monitor.service.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.entity.ProvinceCityDistrictBean;
import cn.chenhai.miaodj_monitor.model.entity.UserInfoEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.presenter.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.presenter.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.ui.module.activityPhoto.ClipImageActivity;
import cn.chenhai.miaodj_monitor.ui.base.BaseBackFragment_Swip;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 个人信息页面
 * Created by ChenHai--霜华 on 2016/6/29. 13:08
 * 邮箱：248866527@qq.com
 */
public class PersonalInfoFragment extends BaseBackFragment_Swip implements View.OnClickListener, UploadUtil.OnUploadProcessListener, ChangeSexDialog.SubmitDoing {
    private static final String ARG_ITEM = "arg_item";
    private static final String TAG = "FragmentLib";

    private static final int REQ_TO_PHOTO_ACTIVITY = 108;
    private static final int REQ_TO_EDIT_FRAGMENT = 109;

    private String mProjectCode;

    private SubscriberOnSuccessListener mOnSuccessInit;
    private SubscriberOnSuccessListener mOnSuccessUploadHeadPic;
    private SubscriberOnSuccessListener mOnSuccessGetAllProviceCity;
    private SubscriberOnSuccessListener mOnSuccessChangeArea;
    private SubscriberOnSuccessListener mOnSuccessChangeSex;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;

    private SimpleDraweeView mSdvInfoPortrait;
    private TextView uploadImageResult;
    private TextView mTvInfoName;
    private TextView mTvInfoSex;
    private TextView mTvInfoPhone;
    private TextView mTvInfoDistrict;
    private TextView mTvInfoAddress;

    private LinearLayout mLlInfoClickPortrait;
    private LinearLayout mLlInfoClickName;
    private LinearLayout mLlInfoClickSex;
    private LinearLayout mLlInfoClickPhone;
    private LinearLayout mLlInfoClickChangePass;
    private LinearLayout mLlInfoClickBelongDistrict;
    private LinearLayout mLlInfoClickAddress;

    OptionsPickerView pvProviceCity;
    private ArrayList<ProvinceCityDistrictBean.ProvinceBean> address1Items = new ArrayList<ProvinceCityDistrictBean.ProvinceBean>();
    private ArrayList<ArrayList<ProvinceCityDistrictBean.ProvinceBean.CityBean>> address2Items = new ArrayList<ArrayList<ProvinceCityDistrictBean.ProvinceBean.CityBean>>();
    private ArrayList<ArrayList<ArrayList<ProvinceCityDistrictBean.ProvinceBean.CityBean.AreaBean>>> address3Items = new ArrayList<>();


    public static PersonalInfoFragment newInstance(String projectCode) {

        Bundle args = new Bundle();
        args.putString(ARG_ITEM, projectCode);
        PersonalInfoFragment fragment = new PersonalInfoFragment();
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
        View view = inflater.inflate(R.layout.fragment_personal_info, container, false);
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

        mSdvInfoPortrait = (SimpleDraweeView) view.findViewById(R.id.sdv_info_portrait);
        uploadImageResult = (TextView) view.findViewById(R.id.upload_result);
        mTvInfoName = (TextView) view.findViewById(R.id.tv_info_name);
        mTvInfoSex = (TextView) view.findViewById(R.id.tv_info_sex);
        mTvInfoPhone = (TextView) view.findViewById(R.id.tv_info_phone);
        mTvInfoDistrict = (TextView) view.findViewById(R.id.tv_info_district);
        mTvInfoAddress = (TextView) view.findViewById(R.id.tv_info_address);

        mLlInfoClickPortrait = (LinearLayout) view.findViewById(R.id.ll_info_click_portrait);
        mLlInfoClickName = (LinearLayout) view.findViewById(R.id.ll_info_click_name);
        mLlInfoClickSex = (LinearLayout) view.findViewById(R.id.ll_info_click_sex);
        mLlInfoClickPhone = (LinearLayout) view.findViewById(R.id.ll_info_click_phone);
        mLlInfoClickChangePass = (LinearLayout) view.findViewById(R.id.ll_info_click_changePass);
        mLlInfoClickBelongDistrict = (LinearLayout) view.findViewById(R.id.ll_info_click_belongDistrict);
        mLlInfoClickAddress = (LinearLayout) view.findViewById(R.id.ll_info_click_address);


        mLlInfoClickPortrait.setOnClickListener(this);
        mLlInfoClickName.setOnClickListener(this);
        mLlInfoClickSex.setOnClickListener(this);
        mLlInfoClickPhone.setOnClickListener(this);
        mLlInfoClickChangePass.setOnClickListener(this);
        mLlInfoClickBelongDistrict.setOnClickListener(this);
        mLlInfoClickAddress.setOnClickListener(this);

        mOnSuccessInit = new SubscriberOnSuccessListener<HttpResult<UserInfoEntity>>() {
            @Override
            public void onSuccess(HttpResult<UserInfoEntity> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    UserInfoEntity.CrewBean beanInfo = result.getInfo().getCrew();

                    String headPath = "";
                    if (beanInfo.getHeadimg() != null) {
                        headPath = HttpMethods.BASE_ROOT_URL + beanInfo.getHeadimg();
                    }
                    mSdvInfoPortrait.setImageURI(Uri.parse(headPath));

                    mTvInfoName.setText(beanInfo.getReal_name());
                    mTvInfoSex.setText(beanInfo.getGender());
                    mTvInfoPhone.setText(beanInfo.getTelephone());

                    String district = (TextUtils.isEmpty(beanInfo.getWork_province()) ? "" : beanInfo.getWork_province()) + " " + (TextUtils.isEmpty(beanInfo.getWork_city()) ? "" : beanInfo.getWork_city());
                    mTvInfoDistrict.setText(district);

                    mTvInfoAddress.setText(beanInfo.getAddress());
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError() {

            }
        };

        mOnSuccessUploadHeadPic = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("头像已上传!")
                                .show();

                        refreshData();
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

        mOnSuccessGetAllProviceCity = new SubscriberOnSuccessListener<HttpResult<ProvinceCityDistrictBean>>() {
            @Override
            public void onSuccess(HttpResult<ProvinceCityDistrictBean> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {
                        //选项选择器
                        pvProviceCity = new OptionsPickerView(_mActivity);

                        //选项1
                        address1Items.addAll(result.getInfo().getProvince());

                        //选项2
                        ArrayList<ArrayList<ProvinceCityDistrictBean.ProvinceBean.CityBean>> list = new ArrayList<>();
                        for (int i = 0; i < result.getInfo().getProvince().size(); i++) {
                            ArrayList<ProvinceCityDistrictBean.ProvinceBean.CityBean> address2Items_item = new ArrayList<>();
                            address2Items_item.addAll(result.getInfo().getProvince().get(i).getCity());
                            list.add(address2Items_item);
                        }
                        address2Items.addAll(list);

                        //选项3


                        //二级联动效果
                        pvProviceCity.setPicker(address1Items, address2Items, true);
                        //设置选择的三级单位

                        //pvProviceCity.setLabels("省", "市");
                        //pvProviceCity.setTitle("选择城市");
                        pvProviceCity.setCyclic(false, false, true);
                        //设置默认选中的三级项目
                        //监听确定选择按钮
                        pvProviceCity.setSelectOptions(1, 1);
                        pvProviceCity.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                            @Override
                            public void onOptionsSelect(int options1, int option2, int options3) {
                                //返回的分别是三个级别的选中位置
                                String tx = address1Items.get(options1).getName()
                                        + "  " + address2Items.get(options1).get(option2).getName();

                                mTvInfoDistrict.setText(tx);

                                //上传到服务器
                                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                                String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                                HttpMethods.getInstance().doChangeArea(new ProgressSubscriber(mOnSuccessChangeArea, _mActivity), user_code, access_token,
                                        address1Items.get(options1).getCode(), address2Items.get(options1).get(option2).getCode());
                            }
                        });

                        pvProviceCity.show();
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

        mOnSuccessChangeArea = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("所属地已更改!")
                                .show();

                        refreshData();
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
        mOnSuccessChangeSex = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {
                        new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("提示")
                                .setContentText("性别已更改!")
                                .show();

                        refreshData();
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

        refreshData();

        //initWheelPicker();
    }

    private void refreshData() {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().getUserProfileInfo(new ProgressSubscriber(mOnSuccessInit, _mActivity), user_code, access_token);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_info_click_portrait:
//                Intent intent = new Intent(_mActivity, PhotoActivity.class);
//                startActivityForResult(intent, REQ_TO_PHOTO_ACTIVITY);

//                PhotoPop pop = new PhotoPop(_mActivity, mSdvInfoPortrait);
//                pop.show(view);
                showPopupWindow(view);

                break;
            case R.id.ll_info_click_name:
                startForResult(PersonalInfoEditFragment.newInstance("更改姓名", "输入姓名", mTvInfoName.getText().toString(), "title"), REQ_TO_EDIT_FRAGMENT);
                break;
            case R.id.ll_info_click_sex:

                ChangeSexDialog dialog = new ChangeSexDialog(_mActivity, new ChangeSexDialog.SubmitDoing() {
                    @Override
                    public void submitDoing(String strSex) {
                        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                        HttpMethods.getInstance().doChangeSex(new ProgressSubscriber(mOnSuccessChangeSex, _mActivity), user_code, access_token, strSex);
                    }
                });
                dialog.show();

                break;
            case R.id.ll_info_click_phone:
                startForResult(PersonalInfoEditPhone.newInstance("更改手机号码", mTvInfoPhone.getText().toString(), "verifyname"), REQ_TO_EDIT_FRAGMENT);
                break;
            case R.id.ll_info_click_changePass:
                startForResult(PersonalChangePass.newInstance(), REQ_TO_EDIT_FRAGMENT);
                break;
            case R.id.ll_info_click_belongDistrict:
                //startForResult(PersonalInfoEditFragment.newInstance("更改所属地区","输入所属地区",mTvInfoDistrict.getText().toString(),"verifyname"),REQ_TO_EDIT_FRAGMENT);
                //点击弹出地址选择器
                //pvOptions.show();
                HttpMethods.getInstance().getAllProvinceCityDistrict(new ProgressSubscriber(mOnSuccessGetAllProviceCity, _mActivity));

                break;
            case R.id.ll_info_click_address:
                //startForResult(PersonalInfoEditFragment.newInstance("更改我的地址","输入地址",mTvInfoAddress.getText().toString(),"verifyname"),REQ_TO_EDIT_FRAGMENT);
                startForResult(PersonalInfoEditAddress.newInstance(mTvInfoAddress.getText().toString()), REQ_TO_EDIT_FRAGMENT);
                break;

        }
    }

    private void initData() {
        mToolbarTitle.setText("个人信息");

        Uri imageUri = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");
        //Uri imageUri = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.logo_color);

        //开始下载
        mSdvInfoPortrait.setImageURI(imageUri);
    }


    private void refreshView() {
//        String token = PreferencesUtils.getString(_mActivity,"user_token");
//        ProxyService.newInstance().GetUserInfo(_mActivity,token);
    }


    @Override
    public void submitDoing(String strSex) {
        String user_code = PreferencesUtils.getString(_mActivity, "user_code");
        String access_token = PreferencesUtils.getString(_mActivity, "access_token");
        HttpMethods.getInstance().doChangeSex(new ProgressSubscriber(mOnSuccessChangeSex, _mActivity), user_code, access_token, mTvInfoSex.getText().toString());
    }


    /**--------------------------------------------------------------------------------------*/
    /**
     * 去上传文件
     */
    protected static final int TO_UPLOAD_FILE = 1;
    /**
     * 上传文件响应
     */
    protected static final int UPLOAD_FILE_DONE = 2;  //
    /**
     * 选择文件
     */
    public static final int TO_SELECT_PHOTO = 3;
    /**
     * 上传初始化
     */
    private static final int UPLOAD_INIT_PROCESS = 4;
    /**
     * 上传中
     */
    private static final int UPLOAD_IN_PROCESS = 5;

    /**
     * 这里的这个URL是我服务器的javaEE环境URL
     */
//    private static String requestURL = "http://172.31.8.6:8080/fileUpload/file_upload";
    private String picPath = null;
    private String uploadPath = null;

    /**
     * 上传服务器响应回调
     */
    @Override
    public void onUploadDone(int responseCode, String message) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_FILE_DONE;
        msg.arg1 = responseCode;
        msg.obj = message;
        handler.sendMessage(msg);


    }

    private void toUploadFile() {
        uploadImageResult.setText("上传中...");
//        progressDialog.setMessage("正在上传文件...");
//        progressDialog.show();
        String fileKey = "img";
        UploadUtil uploadUtil = UploadUtil.getInstance();
        uploadUtil.setOnUploadProcessListener(this);  //设置监听器监听上传状态

        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", "11111");
        //uploadUtil.uploadFile( picPath,fileKey, requestURL ,params);
        uploadUtil.uploadFile(picPath, fileKey, HttpMethods.BASE_ROOT_URL + "index.php/App/Public/upload_imgs", params);
    }

    @Override
    public void onUploadProcess(int uploadSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_IN_PROCESS;
        msg.arg1 = uploadSize;
        handler.sendMessage(msg);
    }

    @Override
    public void initUpload(int fileSize) {
        Message msg = Message.obtain();
        msg.what = UPLOAD_INIT_PROCESS;
        msg.arg1 = fileSize;
        handler.sendMessage(msg);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_UPLOAD_FILE:
                    toUploadFile();
                    break;
                case UPLOAD_INIT_PROCESS:
                    break;
                case UPLOAD_IN_PROCESS:
                    break;
                case UPLOAD_FILE_DONE:
                    String result = "响应码：" + msg.arg1 + "\n响应信息：" + msg.obj + "\n耗时：" + UploadUtil.getRequestTime() + "秒";
                    //uploadImageResult.setText(result);
                    uploadImageResult.setText("");
                    break;

                default:
                    break;
            }
            switch (msg.arg1) {
                case UploadUtil.UPLOAD_SERVER_ERROR_CODE:
                    Toast.makeText(_mActivity, "服务器出错", Toast.LENGTH_SHORT).show();
                    break;
                case UploadUtil.UPLOAD_FILE_NOT_EXISTS_CODE:
                    Toast.makeText(_mActivity, "文件不存在", Toast.LENGTH_SHORT).show();
                    break;
                case UploadUtil.UPLOAD_SUCCESS_CODE:
                    try {
                        JSONObject jsonObject = new JSONObject((String) msg.obj);
                        int code = JSONUtils.getInt(jsonObject, "code", 0);
                        // 如果成功
                        if (code == 200) {

                            JSONObject jsonInfo = JSONUtils.getJSONObject(jsonObject, "info", null);

                            uploadPath = JSONUtils.getString(jsonInfo, "picnameimg", null);

                            if (mPopupWindow.isShowing()) {
                                mPopupWindow.dismiss();
                            }

                            String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                            String access_token = PreferencesUtils.getString(_mActivity, "access_token");
                            HttpMethods.getInstance().doUpdateHeadimgPic(new ProgressSubscriber(mOnSuccessUploadHeadPic, _mActivity), user_code, access_token, uploadPath);
//                String token = PreferencesUtils.getString(PhotoActivity.this, "user_token");
//                ProxyService.newInstance().UploadAvatar(PhotoActivity.this, token,uploadPath);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return;
                    }
                    break;

                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * --------------------------------------------------------------------------------------
     */


    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_TO_EDIT_FRAGMENT && resultCode == RESULT_OK && data != null) {
            String result = data.getString("result");
            if (result != null && result.equals("已修改")) {
                //重新加载
                refreshData();
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        switch (requestCode) {
            case CUT:
                String path = data.getStringExtra(RESULT_PATH);
                Bitmap photo = BitmapFactory.decodeFile(path);
                //ImageView imageView = (ImageView) findViewById(R.id.iv_head);
                //imageView.setImageBitmap(photo);
                //在此处来做图片的上传处理
                String url = "file://" + path;
                mSdvInfoPortrait.setImageURI(Uri.parse(url));

                picPath = path;

//                new SweetAlertDialog(_mActivity, SweetAlertDialog.SUCCESS_TYPE)
//                        .setTitleText("提示")
//                        .setContentText("头像制作成功!")
//                        .show();
                if (picPath != null) {
                    handler.sendEmptyMessage(TO_UPLOAD_FILE);
                } else {
                    Toast.makeText(_mActivity, "上传的文件路径出错", Toast.LENGTH_LONG).show();
                }

                break;
            case LOCAL:
                startCropImageActivity(getFilePath(data.getData()));
                break;
            case CAMERA:
                // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                startCropImageActivity(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                break;
        }
    }

    private void startCropImageActivity(String path) {
        Intent intent = new Intent(_mActivity, ClipImageActivity.class);
        intent.putExtra(PASS_PATH, path);
        mPopupWindow.dismiss();
        startActivityForResult(intent, CUT);
    }

    /**
     * 通过uri获取文件路径
     *
     * @param mUri
     * @return
     */
    public String getFilePath(Uri mUri) {
        try {
            if (mUri.getScheme().equals("file")) {
                return mUri.getPath();
            } else {
                return getFilePathByUri(mUri);
            }
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    // 获取文件路径通过url
    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        Cursor cursor = _mActivity.getContentResolver().query(mUri, null, null, null, null);
        cursor.moveToFirst();
        return cursor.getString(1);
    }


    /**
     * ---------------------------PoputWindow--------------------------------
     */
    private PopupWindow mPopupWindow;
    private RelativeLayout mPopLayout;

    private final int LOCAL = 11;
    private final int CAMERA = 12;
    private final int CUT = 13;
    public static final String IMAGE_FILE_NAME = "clip_temp.jpg";
    public static final String RESULT_PATH = "result_path";
    public static final String PASS_PATH = "pass_path";

    private Button ppBtnCamera;
    private Button ppBtnGallery;
    private Button ppBtnCancel;

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


        mPopLayout = (RelativeLayout) popView.findViewById(R.id.pop_layout);

        ppBtnCamera = (Button) popView.findViewById(R.id.pp_btn_camera);
        ppBtnGallery = (Button) popView.findViewById(R.id.pp_btn_gallery);
        ppBtnCancel = (Button) popView.findViewById(R.id.pp_btn_cancel);

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

    /**
     * 获取file的时候如果没有路径就重新创建
     *
     * @return
     */
    private File getFile() {
        File file = new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        return file;
    }

    private void showPopupWindow(View view) {

        // 一个自定义的布局，作为显示的内容
        View popView = LayoutInflater.from(_mActivity).inflate(R.layout.pop_photo, null);
        mPopupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //初始化
        initPopWindow(popView, mPopupWindow);

        // 设置按钮的点击事件

        ppBtnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(getFile()));
                startActivityForResult(intent, CAMERA);
            }
        });
        ppBtnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFromGallery;
                if (android.os.Build.VERSION.SDK_INT >= 19) { // 判断是不是4.4
                    intentFromGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                } else {
                    intentFromGallery = new Intent(Intent.ACTION_GET_CONTENT);
                }
                intentFromGallery.setType("image/*"); // 设置文件类型
                startActivityForResult(intentFromGallery, LOCAL);
            }
        });

        ppBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        /** 禁止点击外部区域取消popup windows*/
        RelativeLayout layouttemp = (RelativeLayout) popView
                .findViewById(R.id.pop_layout);
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
            mPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }

    }


    /** -------------------------------------------------------------------------------------*/
}
