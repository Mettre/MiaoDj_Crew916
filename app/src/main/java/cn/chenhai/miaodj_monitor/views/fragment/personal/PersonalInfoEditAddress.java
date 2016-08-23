package cn.chenhai.miaodj_monitor.views.fragment.personal;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.view.WheelCurvedPicker;

import java.util.ArrayList;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.commonlib.utils.PreferencesUtils;
import cn.chenhai.miaodj_monitor.helper.UIHelper;
import cn.chenhai.miaodj_monitor.model.HttpResult;
import cn.chenhai.miaodj_monitor.model.bean.ProvinceCityDistrictBean;
import cn.chenhai.miaodj_monitor.network_proxy.HttpMethods;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.ProgressSubscriber;
import cn.chenhai.miaodj_monitor.network_proxy.subscribers.SubscriberOnSuccessListener;
import cn.chenhai.miaodj_monitor.views.base.BaseBackFragment_Swip;

/**
 * 更改详细地址页面
 * Created by ChenHai--霜华 on 2016/7/11. 12:03
 * 邮箱：248866527@qq.com
 */
public class PersonalInfoEditAddress extends BaseBackFragment_Swip {
    private String mStr_address;

    private SubscriberOnSuccessListener mOnSuccessGetAllProviceCity;
    private SubscriberOnSuccessListener mOnSuccessChangeAdress;

    private Toolbar mToolbar;
    private TextView mToolbarTitle;
    private WheelCurvedPicker mMainWheelProvince;
    private WheelCurvedPicker mMainWheelCity;
    private WheelCurvedPicker mMainWheelDistrict;
    private EditText mEtChange;
    private FrameLayout mFlDelete;
    private Button mButtonSave;

    private ProvinceCityDistrictBean mAddressInfo;
    private int provinceIndex = 0;
    private int cityIndex = 0;

    private String provinceCode = "";
    private String cityCode = "";
    private String districtCode = "";


    private ArrayList<String> provinceNameData = new ArrayList<>();
    private ArrayList<String> provinceCodeData = new ArrayList<>();
    private ArrayList<String> cityNameData = new ArrayList<>();
    private ArrayList<String> cityCodeData = new ArrayList<>();
    private ArrayList<String> districtNameData = new ArrayList<>();
    private ArrayList<String> districtCodeData = new ArrayList<>();


    public static PersonalInfoEditAddress newInstance(String str_address) {
        PersonalInfoEditAddress fragment = new PersonalInfoEditAddress();
        Bundle args = new Bundle();
        args.putString("str_address", str_address);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            mStr_address = args.getString("str_address");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_info_address, container, false);

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

        mMainWheelProvince = (WheelCurvedPicker) view.findViewById(R.id.main_wheel_province);
        mMainWheelCity = (WheelCurvedPicker) view.findViewById(R.id.main_wheel_city);
        mMainWheelDistrict = (WheelCurvedPicker) view.findViewById(R.id.main_wheel_district);
        mEtChange = (EditText) view.findViewById(R.id.et_change);
        mFlDelete = (FrameLayout) view.findViewById(R.id.fl_delete);
        mButtonSave = (Button) view.findViewById(R.id.button_save);

        mFlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtChange.setText("");
                hideSoftInput();
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strContent = mEtChange.getText().toString();
                if (TextUtils.isEmpty(strContent.trim())) {
                    Toast.makeText(_mActivity, "输入信息不能为空!", Toast.LENGTH_SHORT).show();
                    return;
                }

                String user_code = PreferencesUtils.getString(_mActivity, "user_code");
                String access_token = PreferencesUtils.getString(_mActivity, "access_token");

                HttpMethods.getInstance().doChangeAddressDetail(new ProgressSubscriber(mOnSuccessChangeAdress, _mActivity), user_code, access_token, provinceCode, cityCode, districtCode, mEtChange.getText().toString());

            }
        });


        mOnSuccessGetAllProviceCity = new SubscriberOnSuccessListener<HttpResult<ProvinceCityDistrictBean>>() {
            @Override
            public void onSuccess(HttpResult<ProvinceCityDistrictBean> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {

                        //赋值省市区信息
                        mAddressInfo = result.getInfo();

                        provinceNameData.clear();
                        provinceCodeData.clear();
                        for (int i = 0; i < result.getInfo().getProvince().size(); i++) {
                            provinceNameData.add(result.getInfo().getProvince().get(i).getName());
                            provinceCodeData.add(result.getInfo().getProvince().get(i).getCode());
                        }
                        mMainWheelProvince.setData(provinceNameData);
                        mMainWheelProvince.setItemIndex(9);
                        //mMainWheelProvince.set


                        mMainWheelProvince.setOnWheelChangeListener(new AbstractWheelPicker.SimpleWheelChangeListener() {
                            @Override
                            public void onWheelScrollStateChanged(int state) {

                            }

                            @Override
                            public void onWheelSelected(int index, String data) {
                                provinceIndex = index;
                                provinceCode = mAddressInfo.getProvince().get(index).getCode();

                                cityNameData.clear();
                                cityCodeData.clear();
                                for (int i = 0; i < mAddressInfo.getProvince().get(index).getCity().size(); i++) {
                                    cityNameData.add(mAddressInfo.getProvince().get(index).getCity().get(i).getName());
                                    cityCodeData.add(mAddressInfo.getProvince().get(index).getCity().get(i).getCode());
                                }
                                mMainWheelCity.setData(cityNameData);
                                mMainWheelCity.setItemIndex(0);
                            }
                        });

                        mMainWheelCity.setOnWheelChangeListener(new AbstractWheelPicker.SimpleWheelChangeListener() {
                            @Override
                            public void onWheelScrollStateChanged(int state) {

                            }

                            @Override
                            public void onWheelSelected(int index, String data) {
                                cityIndex = index;
                                cityCode = mAddressInfo.getProvince().get(provinceIndex).getCity().get(index).getCode();

                                districtNameData.clear();
                                districtCodeData.clear();
                                for (int i = 0; i < mAddressInfo.getProvince().get(provinceIndex).getCity().get(index).getArea().size(); i++) {
                                    districtNameData.add(mAddressInfo.getProvince().get(provinceIndex).getCity().get(index).getArea().get(i).getName());
                                    districtCodeData.add(mAddressInfo.getProvince().get(provinceIndex).getCity().get(index).getArea().get(i).getCode());
                                }
                                mMainWheelDistrict.setData(districtNameData);
                                mMainWheelDistrict.setItemIndex(0);
                            }
                        });

                        mMainWheelDistrict.setOnWheelChangeListener(new AbstractWheelPicker.SimpleWheelChangeListener() {
                            @Override
                            public void onWheelScrollStateChanged(int state) {

                            }

                            @Override
                            public void onWheelSelected(int index, String data) {
                                districtCode = mAddressInfo.getProvince().get(provinceIndex).getCity().get(cityIndex).getArea().get(index).getCode();

                            }
                        });
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

        mOnSuccessChangeAdress = new SubscriberOnSuccessListener<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> result) {
                if (result.getCode() == 3015) {
                    Toast.makeText(_mActivity, "登录验证失效，请重新登录！！", Toast.LENGTH_SHORT).show();
                    UIHelper.showLoginErrorAgain(_mActivity);
                } else {
                    if (result.getCode() == 200) {

                        Bundle bundle = new Bundle();
                        bundle.putString("result", "已修改");
                        setFramgentResult(RESULT_OK, bundle);
                        pop();

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

        HttpMethods.getInstance().getAllProvinceCityDistrict(new ProgressSubscriber(mOnSuccessGetAllProviceCity, _mActivity));

    }

    private void initData() {
        mToolbarTitle.setText("更改详细地址");
        mEtChange.setText(mStr_address);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
