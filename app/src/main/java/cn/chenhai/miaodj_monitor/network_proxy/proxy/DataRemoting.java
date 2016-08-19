package cn.chenhai.miaodj_monitor.network_proxy.proxy;

import android.content.Context;
import android.content.Intent;

/**
 * Created by ChenHai--霜华 on 2016/5/3. 13:26
 * 邮箱：248866527@qq.com
 */
public class DataRemoting {

    //private Context mContext;
    Intent msgIntent;

    public DataRemoting(){
    }

    /**上传头像*/
    public void UploadAvatar(Context context,String user_token,String avatar){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","UploadAvatar");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("avatar", avatar.trim());
        context.startService(msgIntent);
    }

    /**获取验证码*/
    public void GetAuthCode(Context context,String phone){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetAuthCode");
        msgIntent.putExtra("phone", phone.trim());
//        msgIntent.putExtra("password", et_password.getText().toString().trim());
        context.startService(msgIntent);
    }

    /**注册*/
    public void Register(Context context,String phone, String password, String code){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","Register");
        msgIntent.putExtra("phone", phone.trim());
        msgIntent.putExtra("password", password.trim());
        msgIntent.putExtra("code", code.trim());
        context.startService(msgIntent);
    }

    /**登录*/
    public void Login(Context context,String phone, String password){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","Login");
        msgIntent.putExtra("phone", phone.trim());
        msgIntent.putExtra("password", password.trim());
        context.startService(msgIntent);
    }

    /**忘记密码*/
    public void ForgetPass(Context context,String password,String phone,String phone_code){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","ForgetPass");
        msgIntent.putExtra("password", password.trim());
        msgIntent.putExtra("phone", phone.trim());
        msgIntent.putExtra("phone_code", phone_code.trim());
        context.startService(msgIntent);
    }

    /**修改密码*/
    public void ResetPass(Context context,String user_token,String password,String password_new){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","ResetPass");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("password", password.trim());
        msgIntent.putExtra("password_new", password_new.trim());
        context.startService(msgIntent);
    }


    /**刷新登录token*/
    public void RefreshToken(Context context,String user_token){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","RefreshToken");
        msgIntent.putExtra("user_token", user_token.trim());
        context.startService(msgIntent);
    }

    /**获取省列表*/
    public void GetProvince(Context context,String user_token){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetProvince");
        msgIntent.putExtra("user_token", user_token.trim());
        context.startService(msgIntent);
    }

    /**获取城市列表*/
    public void GetCity(Context context,String user_token,String province_id){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetCity");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("province_id",province_id.trim());
        context.startService(msgIntent);
    }

    /**获取区列表*/
    public void GetDistrict(Context context,String user_token,String city_id){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetDistrict");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("city_id",city_id.trim());
        context.startService(msgIntent);
    }

    /**获取省列表*/
    public void GetProvince2(Context context,String user_token){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetProvince2");
        msgIntent.putExtra("user_token", user_token.trim());
        context.startService(msgIntent);
    }

    /**获取城市列表*/
    public void GetCity2(Context context,String user_token,String province_id){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetCity2");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("province_id",province_id.trim());
        context.startService(msgIntent);
    }

    /**获取区列表*/
    public void GetDistrict2(Context context,String user_token,String city_id){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetDistrict2");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("city_id",city_id.trim());
        context.startService(msgIntent);
    }

    /**获取货物类型*/
    public void GetMerchandiseCategory(Context context,String user_token){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetMerchandiseCategory");
        msgIntent.putExtra("user_token", user_token.trim());
        context.startService(msgIntent);
    }

    /**获取货车类型*/
    public void GetVanType(Context context,String user_token){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetVanType");
        msgIntent.putExtra("user_token", user_token.trim());
        context.startService(msgIntent);
    }

    /**查询符合条件的厂家订单*/
    public void GetFactoryOrders(Context context,String user_token,String originateposition,String destinationposition,
                                 String merchandiseweight,String merchandisecategoryids){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetFactoryOrders");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("originateposition", originateposition.trim());
        msgIntent.putExtra("destinationposition", destinationposition.trim());
        msgIntent.putExtra("merchandiseweight", merchandiseweight.trim());
        msgIntent.putExtra("merchandisecategoryids", merchandisecategoryids.trim());
        context.startService(msgIntent);
    }

    /**查询厂家订单详情*/
    public void GetOrderDetail(Context context,String user_token,String ordercode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetOrderDetail");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        context.startService(msgIntent);
    }

    /**第三方报价*/
    public void SetOrderOffer(Context context,String user_token,String ordercode,String apply_price,String message){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","SetOrderOffer");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        msgIntent.putExtra("apply_price", apply_price.trim());
        msgIntent.putExtra("message", message.trim());
        context.startService(msgIntent);
    }

    /**分单*/
    public void SetOrderSplit(Context context,String user_token,String ordercode,String merchandiseweight,String specid,String verifyphone){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","SetOrderSplit");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        msgIntent.putExtra("merchandiseweight", merchandiseweight.trim());
        msgIntent.putExtra("specid", specid.trim());
        msgIntent.putExtra("verifyphone", verifyphone.trim());
        context.startService(msgIntent);
    }

    /**分单开始，接受司机申请*/
    public void SetOrderSplitBegin(Context context,String user_token,String ordercode,String tempcode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","SetOrderSplitBegin");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        msgIntent.putExtra("tempcode",tempcode);
        context.startService(msgIntent);
    }

    /**取消此条分单*/
    public void SetOrderSplitDelete(Context context,String user_token,String branchcode,String ordercode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","SetOrderSplitDelete");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("branchcode", branchcode.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        context.startService(msgIntent);
    }

    /**分单列表*/
    public void GetOrderSplitList(Context context,String user_token,String ordercode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetOrderSplitList");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        context.startService(msgIntent);
    }

    /**临时分单列表*/
    public void GetOrderSplitTemList(Context context,String user_token,String ordercode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetOrderSplitTemList");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        context.startService(msgIntent);
    }

    /**剩余重量吨数*/
    public void GetOrderSplitSurplusWeight(Context context,String user_token,String ordercode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetOrderSplitSurplusWeight");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        context.startService(msgIntent);
    }

    /**分单修改*/
    public void OrderSplitEdit(Context context,String user_token,String ordercode,String merchandiseweight,
                               String specid,String verifyphone,String tempcode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","OrderSplitEdit");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        msgIntent.putExtra("merchandiseweight", merchandiseweight.trim());
        msgIntent.putExtra("specid", specid.trim());
        msgIntent.putExtra("verifyphone", verifyphone.trim());
        msgIntent.putExtra("tempcode", tempcode.trim());
        context.startService(msgIntent);
    }

    /**开始发货*/
    public void OrderDeliverBegin(Context context,String user_token,String ordercode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","OrderDeliverBegin");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        context.startService(msgIntent);
    }

    /**查看订单车主信息*/
    public void GetOrderVansInfo(Context context,String user_token,String branchcode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetOrderVansInfo");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("branchcode", branchcode.trim());
        context.startService(msgIntent);
    }

    /**获取用户基本资料*/
    public void GetUserInfo(Context context,String user_token){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetUserInfo");
        msgIntent.putExtra("user_token", user_token.trim());
        context.startService(msgIntent);
    }

    /**用户头像修改*/
    public void UserAvatarEdit(Context context,String user_token,String avatar){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","UserAvatarEdit");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("avatar", avatar.trim());
        context.startService(msgIntent);
    }

    /**用户基本资料修改*/
    public void FactoryInfoEdit(Context context,String user_token,String title,String address,String latitude,String longitude,String verifyname,String verifyphone){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","FactoryInfoEdit");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("title", title.trim());
        msgIntent.putExtra("address", address.trim());
        msgIntent.putExtra("latitude", latitude.trim());
        msgIntent.putExtra("longitude", longitude.trim());
        msgIntent.putExtra("verifyname", verifyname.trim());
        msgIntent.putExtra("verifyphone", verifyphone.trim());
        context.startService(msgIntent);
    }

    /**用户验证信息修改*/
    public void UserVerifyInfoEdit(Context context,String user_token,String verifyname,String verifyphone,String verifycid,String license){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","UserVerifyInfoEdit");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("verifyname", verifyname.trim());
        msgIntent.putExtra("verifyphone", verifyphone.trim());
        msgIntent.putExtra("verifycid", verifycid.trim());
        msgIntent.putExtra("license", license.trim());
        context.startService(msgIntent);
    }


    /**意见反馈*/
    public void Feedback(Context context,String user_token,String message){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","Feedback");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("message", message.trim());
        context.startService(msgIntent);
    }

    /**获取符合条件的所有车主信息*/
    public void GetVansInfo(Context context,String user_token,String vansspecid,String city,String page,String pagesize){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetVansInfo");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("vansspecid", vansspecid.trim());
        msgIntent.putExtra("city", city.trim());
        msgIntent.putExtra("page", page.trim());
        msgIntent.putExtra("pagesize", pagesize.trim());
        context.startService(msgIntent);
    }

    /**获取收藏的车主*/
    public void GetCollectVans(Context context,String user_token,String page,String pagesize){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetCollectVans");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("page", page.trim());
        msgIntent.putExtra("pagesize", pagesize.trim());
        context.startService(msgIntent);
    }

    /**按手机号 或 姓名 搜索车主...verify_info:手机号或者姓名*/
    public void SearchVans(Context context,String user_token,String verify_info){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","SearchVans");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("verify_info", verify_info.trim());
        context.startService(msgIntent);
    }

    /**查询我的所有相关订单*/
    public void SearchMyOrders(Context context,String user_token){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","SearchMyOrders");
        msgIntent.putExtra("user_token", user_token.trim());
        context.startService(msgIntent);
    }

    /**查询正式分单详情*/
    public void GetOrderSplitFormal(Context context,String user_token,String branchcode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetOrderSplitFormal");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("branchcode", branchcode.trim());
        context.startService(msgIntent);
    }

    /**查询临时分单详情*/
    public void GetOrderSplitTemp(Context context,String user_token,String tempcode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetOrderSplitTemp");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("tempcode", tempcode.trim());
        context.startService(msgIntent);
    }

    /**删除临时分单*/
    public void DeleteOrderSplitTemp(Context context,String user_token,String tempcode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","DeleteOrderSplitTemp");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("tempcode", tempcode.trim());
        context.startService(msgIntent);
    }

    /**改变订单状态，开始分单*/
    public void ChangeSplitStatus(Context context,String user_token,String ordercode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","ChangeSplitStatus");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("ordercode", ordercode.trim());
        context.startService(msgIntent);
    }

    /**查看虚拟订单列表*/
    public void GetVirtualOrderList(Context context,String user_token){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetVirtualOrderList");
        msgIntent.putExtra("user_token", user_token.trim());
        context.startService(msgIntent);
    }

    /**查看虚拟订单详情*/
    public void GetVirtualOrderDetail(Context context,String user_token,String fictitious_ordercode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","GetVirtualOrderDetail");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("fictitious_ordercode", fictitious_ordercode.trim());
        context.startService(msgIntent);
    }

    /**添加虚拟订单*/
    public void AddVirtualOrder(Context context,String user_token,String originateposition,String originateposition_city,String originateposition_district,String destinationposition
                                ,String destinationposition_city,String destinationposition_district,String merchandisecategoryid,String merchandiseweight,String merchandisevolume,String vansspec
                                ,String endtime,String ocitylongitude,String ocitylatitude,String dcitylongitude,String dcitylatitude,String memo,String phone,String odetailaddress,String ddetailaddress){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","AddVirtualOrder");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("originateposition", originateposition.trim());
        msgIntent.putExtra("originateposition_city", originateposition_city.trim());
        msgIntent.putExtra("originateposition_district", originateposition_district.trim());
        msgIntent.putExtra("destinationposition", destinationposition.trim());
        msgIntent.putExtra("destinationposition_city", destinationposition_city.trim());
        msgIntent.putExtra("destinationposition_district", destinationposition_district.trim());
        msgIntent.putExtra("merchandisecategoryid", merchandisecategoryid.trim());
        msgIntent.putExtra("merchandiseweight", merchandiseweight.trim());
        msgIntent.putExtra("merchandisevolume", merchandisevolume.trim());
        msgIntent.putExtra("vansspec", vansspec.trim());
        msgIntent.putExtra("endtime", endtime.trim());
        msgIntent.putExtra("ocitylongitude", ocitylongitude.trim());
        msgIntent.putExtra("ocitylatitude", ocitylatitude.trim());
        msgIntent.putExtra("dcitylongitude", dcitylongitude.trim());
        msgIntent.putExtra("dcitylatitude", dcitylatitude.trim());
        msgIntent.putExtra("memo", memo.trim());
        msgIntent.putExtra("phone", phone.trim());
        msgIntent.putExtra("odetailaddress", odetailaddress.trim());
        msgIntent.putExtra("ddetailaddress", ddetailaddress.trim());
        context.startService(msgIntent);
    }

    /**查看报价车主*/
    public void SearchApplyDriverInfo(Context context,String user_token,String fictitious_ordercode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","SearchApplyDriverInfo");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("fictitious_ordercode", fictitious_ordercode.trim());
        context.startService(msgIntent);
    }

    /**删除虚拟订单*/
    public void DeleteVirtualOrder(Context context,String user_token,String fictitious_ordercode){
        msgIntent = new Intent(context, ConnectService.class);
        msgIntent.putExtra("method_name","DeleteVirtualOrder");
        msgIntent.putExtra("user_token", user_token.trim());
        msgIntent.putExtra("fictitious_ordercode", fictitious_ordercode.trim());
        context.startService(msgIntent);
    }
}
