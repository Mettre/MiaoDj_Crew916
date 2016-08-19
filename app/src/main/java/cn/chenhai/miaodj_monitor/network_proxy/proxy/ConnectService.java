package cn.chenhai.miaodj_monitor.network_proxy.proxy;

/**
 * Created by south on 2016/4/11.
 */

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import cn.chenhai.miaodj_monitor.network_proxy.constant.IntentConstants;
import cn.chenhai.miaodj_monitor.network_proxy.constant.URLServiceConstants;
import cn.chenhai.miaodj_monitor.network_proxy.proxy.HttpUtil;


public class ConnectService extends IntentService {

    public static boolean isPrintLog = true;
    public static final String BASE_URL = "http://test3.emplus.cn/index.php";

    public ConnectService() {
        super("Connect_IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /**
         * 经测试，IntentService里面是可以进行耗时的操作的
         * IntentService使用队列的方式将请求的Intent加入队列，
         * 然后开启一个worker thread(线程)来处理队列中的Intent
         * 对于异步的startService请求，IntentService会处理完成一个之后再处理第二个
         */
        String strResult = "";
        String methodname = intent.getStringExtra("method_name");
        //通过intent获取主线程传来的用户名和密码字符串
        String phone ,password ,password_new ,code ,user_token ,province_id ,city_id;

        String originateposition,destinationposition,merchandiseweight,merchandisecategoryids;

        String ordercode,apply_price,message,specid,vid,branchcode ,tempcode,avatar;

        String title,address,latitude,longitude;

        String verifyname,verifyphone,verifycid,license;

        String vansspecid,city,page,pagesize,verify_info;

        String originateposition_city,originateposition_district,destinationposition_city,destinationposition_district,
                merchandisecategoryid,merchandisevolume,vansspec,endtime,ocitylongitude,ocitylatitude,dcitylongitude,dcitylatitude,memo,
                fictitious_ordercode, odetailaddress,ddetailaddress;


        Intent broadcastIntent = new Intent();

        //判断调用接口
        switch (methodname)
        {
            case "Register":
                phone = intent.getStringExtra("phone");
                password = intent.getStringExtra("password");
                code = intent.getStringExtra("code");
                strResult = doRegister(phone, password, code);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_REGISTER);
                if(isPrintLog) {
                    Log.d("注册，服务器返回", strResult);
                }
                break;
            case "GetAuthCode":
                phone = intent.getStringExtra("phone");
                strResult = doSendCode(phone);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_AUTH_CODE);
                if(isPrintLog) {
                    Log.d("验证，服务器返回", strResult);
                }
                break;
            case "Login":
                phone = intent.getStringExtra("phone");
                password = intent.getStringExtra("password");
                strResult = doLogin(phone,password);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_LOGIN);
                if(isPrintLog) {
                    Log.d("登录，服务器返回", strResult);
                }
                break;
            case "ForgetPass":
                password = intent.getStringExtra("password");
                phone = intent.getStringExtra("phone");
                code = intent.getStringExtra("phone_code");
                strResult = ForgetPass(password,phone,code);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_PASSWORD_FORGET);
                if(isPrintLog) {
                    Log.d("忘记密码，服务器返回", strResult);
                }
                break;
            case "ResetPass":
                user_token = intent.getStringExtra("user_token");
                password = intent.getStringExtra("password");
                password_new = intent.getStringExtra("password_new");
                strResult = ResetPass(user_token,password,password_new);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_PASSWORD_EDIT);
                if(isPrintLog) {
                    Log.d("修改密码，服务器返回", strResult);
                }
                break;
            case "RefreshToken":
                user_token = intent.getStringExtra("user_token");
                strResult = RefreshToken(user_token);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_REFRESH_TOKEN);
                if(isPrintLog) {
                    Log.d("刷新登录token，服务器返回", strResult);
                }
                break;
            case "GetProvince":
                user_token = intent.getStringExtra("user_token");
                strResult = GetProvince(user_token);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_PROVINCE);
                if(isPrintLog) {
                    Log.d("获取省列表，服务器返回", strResult);
                }
                break;
            case "GetCity":
                user_token = intent.getStringExtra("user_token");
                province_id = intent.getStringExtra("province_id");
                strResult = GetCity(user_token,province_id);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_CITY);
                if(isPrintLog) {
                    Log.d("获取市列表，服务器返回", strResult);
                }
                break;
            case "GetDistrict":
                user_token = intent.getStringExtra("user_token");
                city_id = intent.getStringExtra("city_id");
                strResult = GetDistrict(user_token,city_id);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_DISTRICT);
                if(isPrintLog) {
                    Log.d("获取区列表，服务器返回", strResult);
                }
                break;
            case "GetProvince2":
                user_token = intent.getStringExtra("user_token");
                strResult = GetProvince2(user_token);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_PROVINCE2);
                if(isPrintLog) {
                    Log.d("获取收货省列表，服务器返回", strResult);
                }
                break;
            case "GetCity2":
                user_token = intent.getStringExtra("user_token");
                province_id = intent.getStringExtra("province_id");
                strResult = GetCity2(user_token,province_id);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_CITY2);
                if(isPrintLog) {
                    Log.d("获取收货市列表，服务器返回", strResult);
                }
                break;
            case "GetDistrict2":
                user_token = intent.getStringExtra("user_token");
                city_id = intent.getStringExtra("city_id");
                strResult = GetDistrict2(user_token,city_id);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_DISTRICT2);
                if(isPrintLog) {
                    Log.d("获取收货区列表，服务器返回", strResult);
                }
                break;
            case "GetMerchandiseCategory":
                user_token = intent.getStringExtra("user_token");
                strResult = GetMerchandiseCategory(user_token);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_CATEGORY);
                if(isPrintLog) {
                    Log.d("获取货物类型，服务器返回", strResult);
                }
                break;
            case "GetVanType":
                user_token = intent.getStringExtra("user_token");
                strResult = GetVanType(user_token);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_VANS_TYPE);
                if(isPrintLog) {
                    Log.d("获取货车类型，服务器返回", strResult);
                }
                break;
            case "GetFactoryOrders":
                user_token = intent.getStringExtra("user_token");
                originateposition = intent.getStringExtra("originateposition");
                destinationposition = intent.getStringExtra("destinationposition");
                merchandiseweight = intent.getStringExtra("merchandiseweight");
                merchandisecategoryids = intent.getStringExtra("merchandisecategoryids");
                strResult = GetFactoryOrders(user_token,originateposition,destinationposition,
                        merchandiseweight,merchandisecategoryids);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_FACTORY_ORDERS);
                if(isPrintLog) {
                    Log.d("查询厂家订单，服务器返回", strResult);
                }
                break;
            case "GetOrderDetail":
                user_token = intent.getStringExtra("user_token");
                ordercode = intent.getStringExtra("ordercode");
                strResult = GetOrderDetail(user_token,ordercode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_DETAIL);
                if(isPrintLog) {
                    Log.d("查询订单详情，服务器返回", strResult);
                }
                break;
            case "SetOrderOffer":
                user_token = intent.getStringExtra("user_token");
                ordercode = intent.getStringExtra("ordercode");
                apply_price = intent.getStringExtra("apply_price");
                message = intent.getStringExtra("message");
                strResult = SetOrderOffer(user_token,ordercode,apply_price,message);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_OFFER);
                if(isPrintLog) {
                    Log.d("第三方报价，服务器返回", strResult);
                }
                break;
            case "SetOrderSplit":
                user_token = intent.getStringExtra("user_token");
                ordercode = intent.getStringExtra("ordercode");
                merchandiseweight = intent.getStringExtra("merchandiseweight");
                specid = intent.getStringExtra("specid");
                verifyphone = intent.getStringExtra("verifyphone");
                strResult = SetOrderSplit(user_token,ordercode,merchandiseweight,specid,verifyphone);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_SPLIT);
                if(isPrintLog) {
                    Log.d("分单，服务器返回", strResult);
                }
                break;
            case "SetOrderSplitBegin":
                user_token = intent.getStringExtra("user_token");
                ordercode = intent.getStringExtra("ordercode");
                tempcode = intent.getStringExtra("tempcode");
                strResult = SetOrderSplitBegin(user_token,ordercode,tempcode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_SPLIT_BEGIN);
                if(isPrintLog) {
                    Log.d("分单开始接受司机申请，服务器返回", strResult);
                }
                break;
            case "SetOrderSplitDelete":
                user_token = intent.getStringExtra("user_token");
                branchcode = intent.getStringExtra("branchcode");
                ordercode = intent.getStringExtra("ordercode");
                strResult = SetOrderSplitDelete(user_token,branchcode,ordercode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_SPLIT_DELETE);
                if(isPrintLog) {
                    Log.d("此条分单取消，服务器返回", strResult);
                }
                break;
            case "GetOrderSplitList":
                user_token = intent.getStringExtra("user_token");
                ordercode = intent.getStringExtra("ordercode");
                strResult = GetOrderSplitList(user_token,ordercode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_SPLIT_LIST);
                if(isPrintLog) {
                    Log.d("分单列表，服务器返回", strResult);
                }
                break;
            case "GetOrderSplitTemList":
                user_token = intent.getStringExtra("user_token");
                ordercode = intent.getStringExtra("ordercode");
                strResult = GetOrderSplitTemList(user_token,ordercode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_SPLIT_TEM_LIST);
                if(isPrintLog) {
                    Log.d("临时分单列表，服务器返回", strResult);
                }
                break;
            case "GetOrderSplitSurplusWeight":
                user_token = intent.getStringExtra("user_token");
                ordercode = intent.getStringExtra("ordercode");
                strResult = GetOrderSplitSurplusWeight(user_token,ordercode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_SPLIT_SURPLUS_WEIGHT);
                if(isPrintLog) {
                    Log.d("分单列表剩余吨数，服务器返回", strResult);
                }
                break;
            case "OrderSplitEdit":
                user_token = intent.getStringExtra("user_token");
                ordercode = intent.getStringExtra("ordercode");
                merchandiseweight = intent.getStringExtra("merchandiseweight");
                specid = intent.getStringExtra("specid");
                verifyphone = intent.getStringExtra("verifyphone");
                tempcode = intent.getStringExtra("tempcode");
                strResult = OrderSplitEdit(user_token,ordercode,merchandiseweight,specid,verifyphone,tempcode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_SPLIT_EDIT);
                if(isPrintLog) {
                    Log.d("分单修改，服务器返回", strResult);
                }
                break;
            case "OrderDeliverBegin":
                user_token = intent.getStringExtra("user_token");
                ordercode = intent.getStringExtra("ordercode");
                strResult = OrderDeliverBegin(user_token,ordercode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_DELIVER_BEGIN);
                if(isPrintLog) {
                    Log.d("开始发货，服务器返回", strResult);
                }
                break;
            case "GetOrderVansInfo":
                user_token = intent.getStringExtra("user_token");
                branchcode = intent.getStringExtra("branchcode");
                strResult = GetOrderVansInfo(user_token,branchcode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ORDER_VANS_INFO);
                if(isPrintLog) {
                    Log.d("查看订单车主信息，服务器返回", strResult);
                }
                break;
            case "GetUserInfo":
                user_token = intent.getStringExtra("user_token");
                strResult = GetUserInfo(user_token);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_USER_INFO);
                if(isPrintLog) {
                    Log.d("用户基本资料，服务器返回", strResult);
                }
                break;
            case "UserAvatarEdit":
                user_token = intent.getStringExtra("user_token");
                avatar = intent.getStringExtra("avatar");
                strResult = UserAvatarEdit(user_token,avatar);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_AVATAR_EDIT);
                if(isPrintLog) {
                    Log.d("头像修改，服务器返回", strResult);
                }
                break;
            case "FactoryInfoEdit":
                user_token = intent.getStringExtra("user_token");
                title = intent.getStringExtra("title");
                address = intent.getStringExtra("address");
                latitude = intent.getStringExtra("latitude");
                longitude = intent.getStringExtra("longitude");
                verifyname = intent.getStringExtra("verifyname");
                verifyphone = intent.getStringExtra("verifyphone");
                strResult = FactoryInfoEdit(user_token,title,address,latitude,longitude,verifyname,verifyphone);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_FACTORY_INFO_EDIT);
                if(isPrintLog) {
                    Log.d("用户公司资料修改，服务器返回", strResult);
                }
                break;
            case "UserVerifyInfoEdit":
                user_token = intent.getStringExtra("user_token");
                verifyname = intent.getStringExtra("verifyname");
                verifyphone = intent.getStringExtra("verifyphone");
                verifycid = intent.getStringExtra("verifycid");
                license = intent.getStringExtra("license");
                strResult = UserVerifyInfoEdit(user_token,verifyname,verifyphone,verifycid,license);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_VERIFY_INFO_EDIT);
                if(isPrintLog) {
                    Log.d("用户验证信息修改，服务器返回", strResult);
                }
                break;
            case "Feedback":
                user_token = intent.getStringExtra("user_token");
                message = intent.getStringExtra("message");
                strResult = Feedback(user_token,message);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_FEED_BACK);
                if(isPrintLog) {
                    Log.d("用户反馈，服务器返回", strResult);
                }
                break;
            case "GetVansInfo":
                user_token = intent.getStringExtra("user_token");
                vansspecid = intent.getStringExtra("vansspecid");
                city = intent.getStringExtra("city");
                page = intent.getStringExtra("page");
                pagesize = intent.getStringExtra("pagesize");
                strResult = GetVansInfo(user_token,vansspecid,city,page,pagesize);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_VANS_INFO);
                if(isPrintLog) {
                    Log.d("获取车主信息，服务器返回", strResult);
                }
                break;
            case "GetCollectVans":
                user_token = intent.getStringExtra("user_token");
                page = intent.getStringExtra("page");
                pagesize = intent.getStringExtra("pagesize");
                strResult = GetCollectVans(user_token,page,pagesize);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_COLLECT_VANS);
                if(isPrintLog) {
                    Log.d("获取收藏车主，服务器返回", strResult);
                }
                break;
            case "SearchVans":
                user_token = intent.getStringExtra("user_token");
                verify_info = intent.getStringExtra("verify_info");
                strResult = SearchVans(user_token,verify_info);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_SEARCH_VAN);
                if(isPrintLog) {
                    Log.d("按条件搜索车主，服务器返回", strResult);
                }
                break;
            case "SearchMyOrders":
                user_token = intent.getStringExtra("user_token");
                strResult = SearchMyOrders(user_token);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_MY_ORDERS);
                if(isPrintLog) {
                    Log.d("查询我的相关订单，服务器返回", strResult);
                }
                break;
            case "GetOrderSplitFormal":
                user_token = intent.getStringExtra("user_token");
                branchcode = intent.getStringExtra("branchcode");
                strResult = GetOrderSplitFormal(user_token,branchcode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_ORDER_SPLIT_FORMAL);
                if(isPrintLog) {
                    Log.d("查询订单正式分单详情，服务器返回", strResult);
                }
                break;
            case "GetOrderSplitTemp":
                user_token = intent.getStringExtra("user_token");
                tempcode = intent.getStringExtra("tempcode");
                strResult = GetOrderSplitTemp(user_token,tempcode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_ORDER_SPLIT_TEMP);
                if(isPrintLog) {
                    Log.d("查询订单临时分单详情，服务器返回", strResult);
                }
                break;
            case "DeleteOrderSplitTemp":
                user_token = intent.getStringExtra("user_token");
                tempcode = intent.getStringExtra("tempcode");
                strResult = DeleteOrderSplitTemp(user_token,tempcode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_GET_ORDER_SPLIT_TEMP_DELETE);
                if(isPrintLog) {
                    Log.d("删除订单临时分单，服务器返回", strResult);
                }
                break;
            case "ChangeSplitStatus":
                user_token = intent.getStringExtra("user_token");
                ordercode = intent.getStringExtra("ordercode");
                strResult = ChangeSplitStatus(user_token,ordercode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_CHANGE_SPLIT_STATUS);
                if(isPrintLog) {
                    Log.d("改变订单状态开始分单，服务器返回", strResult);
                }
                break;
            case "GetVirtualOrderList":
                user_token = intent.getStringExtra("user_token");
                strResult = GetVirtualOrderList(user_token);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_SEARCH_VIRTUAL_ORDER_LIST);
                if(isPrintLog) {
                    Log.d("虚拟订单列表，服务器返回", strResult);
                }
                break;
            case "GetVirtualOrderDetail":
                user_token = intent.getStringExtra("user_token");
                fictitious_ordercode = intent.getStringExtra("fictitious_ordercode");
                strResult = GetVirtualOrderDetail(user_token,fictitious_ordercode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_SEARCH_VIRTUAL_ORDER_DETAIL);
                if(isPrintLog) {
                    Log.d("虚拟订单详情，服务器返回", strResult);
                }
                break;
            case "AddVirtualOrder":
                user_token = intent.getStringExtra("user_token");
                originateposition = intent.getStringExtra("originateposition");
                originateposition_city = intent.getStringExtra("originateposition_city");
                originateposition_district = intent.getStringExtra("originateposition_district");
                destinationposition = intent.getStringExtra("destinationposition");
                destinationposition_city = intent.getStringExtra("destinationposition_city");
                destinationposition_district = intent.getStringExtra("destinationposition_district");
                merchandisecategoryid = intent.getStringExtra("merchandisecategoryid");
                merchandiseweight = intent.getStringExtra("merchandiseweight");
                merchandisevolume = intent.getStringExtra("merchandisevolume");
                vansspec = intent.getStringExtra("vansspec");
                endtime = intent.getStringExtra("endtime");
                ocitylongitude = intent.getStringExtra("ocitylongitude");
                ocitylatitude = intent.getStringExtra("ocitylatitude");
                dcitylongitude = intent.getStringExtra("dcitylongitude");
                dcitylatitude = intent.getStringExtra("dcitylatitude");
                memo = intent.getStringExtra("memo");
                phone = intent.getStringExtra("phone");
                odetailaddress = intent.getStringExtra("odetailaddress");
                ddetailaddress = intent.getStringExtra("ddetailaddress");
                strResult = AddVirtualOrder(user_token,originateposition,originateposition_city,originateposition_district,destinationposition,destinationposition_city,destinationposition_district,
                        merchandisecategoryid,merchandiseweight,merchandisevolume,vansspec,endtime,ocitylongitude,ocitylatitude,dcitylongitude,dcitylatitude,memo,phone,odetailaddress,ddetailaddress);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_ADD_VIRTUAL_ORDER);
                if(isPrintLog) {
                    Log.d("增加虚拟订单，服务器返回", strResult);
                }
                break;
            case "SearchApplyDriverInfo":
                user_token = intent.getStringExtra("user_token");
                fictitious_ordercode = intent.getStringExtra("fictitious_ordercode");
                strResult = SearchApplyDriverInfo(user_token,fictitious_ordercode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_SEARCH_APPLY_DRIVER_INFO);
                if(isPrintLog) {
                    Log.d("查询申请车主信息，服务器返回", strResult);
                }
                break;
            case "DeleteVirtualOrder":
                user_token = intent.getStringExtra("user_token");
                fictitious_ordercode = intent.getStringExtra("fictitious_ordercode");
                strResult = DeleteVirtualOrder(user_token,fictitious_ordercode);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_DELETE_VIRTUAL_ORDER);
                if(isPrintLog) {
                    Log.d("删除虚拟订单，服务器返回", strResult);
                }
                break;
            case "UploadAvatar":
                user_token = intent.getStringExtra("user_token");
                avatar = intent.getStringExtra("avatar");
                strResult = UploadAvatar(user_token, avatar);
                broadcastIntent.setAction(IntentConstants.ACTION__MSG_UPLOAD_IMG);
                if(isPrintLog) {
                    Log.d("上传头像，服务器返回", strResult);
                }
                break;
            default:
                return;
        }


        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra("result", strResult);
        sendBroadcast(broadcastIntent);

//        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
//        LocalBroadcastManager.sendBroadcast(broadcastIntent);
    }

    /**-------------------------------------------------------------------------*/

    /**发送HTTP*/
    private String doHttpPost(String url, JSONObject json){
        String strResult = "";

        try {
            // 发送请求
            strResult = HttpUtil.doPost(url,json);   //POST方式
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isPrintLog) {
            Log.d("url", url);
            Log.d("Json", json.toString());
        }
        return strResult;
    }

    /**-------------------------------------------------------------------------*/









    /**登录*/
    private String doLogin(String phone, String password)
    {
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("phone",phone);
            json.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // 定义发送请求的URL
        //String url = BASE_URL + "queryOrder?un=" + username + "&pw=" + password;  //GET方式
        String url = URLServiceConstants.URL_API_LOGIN; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**发送验证码*/
    private String doSendCode(String phone)
    {
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("phone",phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_AUTH_CODE; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**注册*/
    private String doRegister(String phone, String password, String code)
    {
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("phone",phone);
            json.put("password",password);
            json.put("code",code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_REGISTER; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**忘记密码*/
    public String ForgetPass(String password,String phone,String phone_code){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("password",password);
            json.put("phone",phone);
            json.put("phone_code",phone_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_PASSWORD_FORGET; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**修改密码*/
    public String ResetPass(String user_token,String password,String password_new){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("password",password);
            json.put("password_new",password_new);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_PASSWORD_EDIT; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }


    /**刷新登录token*/
    public String RefreshToken(String user_token){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_REFRESH_TOKEN; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取省列表*/
    public String GetProvince(String user_token){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_PROVINCE; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取城市列表*/
    public String GetCity(String user_token,String province_id){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("province_id",province_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_CITY; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取区列表*/
    public String GetDistrict(String user_token,String city_id){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("city_id",city_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_DISTRICT; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取省列表*/
    public String GetProvince2(String user_token){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_PROVINCE2; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取城市列表*/
    public String GetCity2(String user_token,String province_id){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("province_id",province_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_CITY2; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取区列表*/
    public String GetDistrict2(String user_token,String city_id){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("city_id",city_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_DISTRICT2; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取货物类型*/
    public String GetMerchandiseCategory(String user_token){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_CATEGORY; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取货车类型*/
    public String GetVanType(String user_token){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_VANS_TYPE; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**查询符合条件的厂家订单*/
    public String GetFactoryOrders(String user_token,String originateposition,String destinationposition,
                                 String merchandiseweight,String merchandisecategoryids){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("originateposition",originateposition);
            json.put("destinationposition",destinationposition);
            json.put("merchandiseweight",merchandiseweight);
            json.put("merchandisecategoryids",merchandisecategoryids);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_FACTORY_ORDERS; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**查询厂家订单详情*/
    public String GetOrderDetail(String user_token,String ordercode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("ordercode",ordercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_DETAIL; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**第三方报价*/
    public String SetOrderOffer(String user_token,String ordercode,String apply_price,String message){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("ordercode",ordercode);
            json.put("apply_price",apply_price);
            json.put("message",message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_OFFER; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**分单*/
    public String SetOrderSplit(String user_token,String ordercode,String merchandiseweight,String specid,String verifyphone){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("ordercode",ordercode);
            json.put("merchandiseweight",merchandiseweight);
            json.put("specid",specid);
            json.put("verifyphone",verifyphone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_SPLIT; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**分单开始，接受司机申请*/
    public String SetOrderSplitBegin(String user_token,String ordercode,String tempcode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("ordercode",ordercode);
            json.put("tempcode",tempcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_SPLIT_BEGIN; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**取消此条分单*/
    public String SetOrderSplitDelete(String user_token,String branchcode,String ordercode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("branchcode",branchcode);
            json.put("ordercode",ordercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_SPLIT_DELETE; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**分单列表*/
    public String GetOrderSplitList(String user_token,String ordercode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("ordercode",ordercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_SPLIT_LIST; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**临时分单列表*/
    public String GetOrderSplitTemList(String user_token,String ordercode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("ordercode",ordercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_SPLIT_TEM_LIST; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**剩余重量吨数*/
    public String GetOrderSplitSurplusWeight(String user_token,String ordercode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("ordercode",ordercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_SPLIT_SURPLUS_WEIGHT; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**分单修改*/
    public String OrderSplitEdit(String user_token,String ordercode,String merchandiseweight,
                               String specid,String verifyphone,String tempcode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("ordercode",ordercode);
            json.put("merchandiseweight",merchandiseweight);
            json.put("specid",specid);
            json.put("verifyphone",verifyphone);
            json.put("tempcode",tempcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_SPLIT_EDIT; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**开始发货*/
    public String OrderDeliverBegin(String user_token,String ordercode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("ordercode",ordercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_DELIVER_BEGIN; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**查看订单车主信息*/
    public String GetOrderVansInfo(String user_token,String branchcode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("branchcode",branchcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ORDER_VANS_INFO; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取用户基本资料*/
    public String GetUserInfo(String user_token){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_USER_INFO; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**用户头像修改*/
    public String UserAvatarEdit(String user_token,String avatar){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("avatar",avatar);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_AVATAR_EDIT; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**用户公司资料修改*/
    public String FactoryInfoEdit(String user_token,String title,String address,String latitude,String longitude,String verifyname,String verifyphone){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("title",title);
            json.put("address",address);
            json.put("latitude",latitude);
            json.put("longitude",longitude);
            json.put("verifyname",verifyname);
            json.put("verifyphone",verifyphone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_FACTORY_INFO_EDIT; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**用户验证信息修改*/
    public String UserVerifyInfoEdit(String user_token,String verifyname,String verifyphone,String verifycid,String license){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("verifyname",verifyname);
            json.put("verifyphone",verifyphone);
            json.put("verifycid",verifycid);
            json.put("license",license);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_VERIFY_INFO_EDIT; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }


    /**意见反馈*/
    public String Feedback(String user_token,String message){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("message",message);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_FEED_BACK; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取符合条件的所有车主信息*/
    public String GetVansInfo(String user_token,String vansspecid,String city,String page,String pagesize){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("vansspecid",vansspecid);
            json.put("city",city);
            json.put("page",page);
            json.put("pagesize",pagesize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_VANS_INFO; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**获取收藏的车主*/
    public String GetCollectVans(String user_token,String page,String pagesize){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("page",page);
            json.put("pagesize",pagesize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_COLLECT_VANS; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**按手机号 或 姓名 搜索车主*/
    public String SearchVans(String user_token,String verify_info){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("verify_info",verify_info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_SEARCH_VAN; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**查询我的所有相关订单*/
    public String SearchMyOrders(String user_token){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_MY_ORDERS; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**查询正式分单详情*/
    public String GetOrderSplitFormal(String user_token,String branchcode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("branchcode",branchcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_ORDER_SPLIT_FORMAL; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**查询临时分单详情*/
    public String GetOrderSplitTemp(String user_token,String tempcode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("tempcode",tempcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_ORDER_SPLIT_TEMP; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**删除临时分单*/
    public String DeleteOrderSplitTemp(String user_token,String tempcode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("tempcode",tempcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_GET_ORDER_SPLIT_TEMP_DELETE; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**改变订单状态，开始分单*/
    public String ChangeSplitStatus(String user_token,String ordercode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("ordercode",ordercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_CHANGE_SPLIT_STATUS; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**查看虚拟订单列表*/
    public String GetVirtualOrderList(String user_token){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_SEARCH_VIRTUAL_ORDER_LIST; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**查看虚拟订单详情*/
    public String GetVirtualOrderDetail(String user_token,String fictitious_ordercode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("fictitious_ordercode",fictitious_ordercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_SEARCH_VIRTUAL_ORDER_DETAIL; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**添加虚拟订单*/
    public String AddVirtualOrder(String user_token,String originateposition,String originateposition_city,String originateposition_district,String destinationposition
            ,String destinationposition_city,String destinationposition_district,String merchandisecategoryid,String merchandiseweight,String merchandisevolume,String vansspec
            ,String endtime,String ocitylongitude,String ocitylatitude,String dcitylongitude,String dcitylatitude,String memo,String phone,String odetailaddress,String ddetailaddress){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("originateposition", originateposition);
            json.put("originateposition_city", originateposition_city);
            json.put("originateposition_district", originateposition_district);
            json.put("destinationposition", destinationposition);
            json.put("destinationposition_city", destinationposition_city);
            json.put("destinationposition_district", destinationposition_district);
            json.put("merchandisecategoryid", merchandisecategoryid);
            json.put("merchandiseweight", merchandiseweight);
            json.put("merchandisevolume", merchandisevolume);
            json.put("vansspec", vansspec);
            json.put("endtime", endtime);
            json.put("ocitylongitude", ocitylongitude);
            json.put("ocitylatitude", ocitylatitude);
            json.put("dcitylongitude", dcitylongitude);
            json.put("dcitylatitude", dcitylatitude);
            json.put("memo", memo);
            json.put("phone", phone);
            json.put("odetailaddress", odetailaddress);
            json.put("ddetailaddress", ddetailaddress);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_ADD_VIRTUAL_ORDER; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**查看报价车主*/
    public String SearchApplyDriverInfo(String user_token,String fictitious_ordercode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("fictitious_ordercode",fictitious_ordercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_SEARCH_APPLY_DRIVER_INFO; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**删除虚拟订单*/
    public String DeleteVirtualOrder(String user_token,String fictitious_ordercode){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("fictitious_ordercode",fictitious_ordercode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String url = URLServiceConstants.URL_API_DELETE_VIRTUAL_ORDER; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

    /**上传头像*/
    public String UploadAvatar(String user_token,String avatar){
        String strResult = "";

        JSONObject json = new JSONObject();
        try {
            json.put("user_token",user_token);
            json.put("avatar",avatar);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = URLServiceConstants.URL_API_UPLOAD_IMG; //POST方式

        strResult = doHttpPost(url,json);

        return strResult;
    }

}
