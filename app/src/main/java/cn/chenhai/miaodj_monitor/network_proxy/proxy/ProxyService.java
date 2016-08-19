package cn.chenhai.miaodj_monitor.network_proxy.proxy;

import android.content.Context;

/**
 * Created by ChenHai--霜华 on 2016/5/3. 12:55
 * 邮箱：248866527@qq.com
 */
public class ProxyService {
    //private DataLocal dataService;
    private DataRemoting dataService;

    public ProxyService(DataRemoting data){
        dataService = data;
    }

    public static ProxyService newInstance(){
        DataRemoting dataService = new DataRemoting();

        ProxyService mProxy = new ProxyService(dataService);
        return mProxy;
    }

    /**上传头像*/
    public void UploadAvatar(Context context,String user_token,String avatar){
        dataService.UploadAvatar(context,user_token,avatar);
    }

    /**获取验证码*/
    public void GetAuthCode(Context context, String phone){
        dataService.GetAuthCode(context,phone);
    }

    /**注册*/
    public void Register(Context context,String phone, String password, String code){
        dataService.Register(context,phone,password,code);
    }

    /**登录*/
    public void Login(Context context,String phone, String password){
        dataService.Login(context,phone,password);
    }

    /**忘记密码*/
    public void ForgetPass(Context context,String password,String phone,String phone_code){
        dataService.ForgetPass(context,password,phone,phone_code);
    }

    /**修改密码*/
    public void ResetPass(Context context,String user_token,String password,String password_new){
        dataService.ResetPass(context,user_token,password,password_new);
    }


    /**刷新登录token*/
    public void RefreshToken(Context context,String user_token){
        dataService.RefreshToken(context,user_token);
    }

    /**获取省列表*/
    public void GetProvince(Context context,String user_token){
        dataService.GetProvince(context,user_token);
    }

    /**获取城市列表*/
    public void GetCity(Context context,String user_token,String province_id){
        dataService.GetCity(context,user_token,province_id);
    }

    /**获取区列表*/
    public void GetDistrict(Context context,String user_token,String city_id){
        dataService.GetDistrict(context,user_token,city_id);
    }

    /**获取省列表*/
    public void GetProvince2(Context context,String user_token){
        dataService.GetProvince2(context,user_token);
    }

    /**获取城市列表*/
    public void GetCity2(Context context,String user_token,String province_id){
        dataService.GetCity2(context,user_token,province_id);
    }

    /**获取区列表*/
    public void GetDistrict2(Context context,String user_token,String city_id){
        dataService.GetDistrict2(context,user_token,city_id);
    }

    /**获取货物类型*/
    public void GetMerchandiseCategory(Context context,String user_token){
        dataService.GetMerchandiseCategory(context,user_token);
    }

    /**获取货车类型*/
    public void GetVanType(Context context,String user_token){
        dataService.GetVanType(context,user_token);
    }

    /**查询符合条件的厂家订单*/
    public void GetFactoryOrders(Context context,String user_token,String originateposition,String destinationposition,
                                 String merchandiseweight,String merchandisecategoryids){
        dataService.GetFactoryOrders(context,user_token,originateposition,destinationposition,merchandiseweight,merchandisecategoryids);
    }

    /**查询厂家订单详情*/
    public void GetOrderDetail(Context context,String user_token,String ordercode){
        dataService.GetOrderDetail(context,user_token,ordercode);
    }

    /**第三方报价*/
    public void SetOrderOffer(Context context,String user_token,String ordercode,String apply_price,String message){
        dataService.SetOrderOffer(context,user_token,ordercode,apply_price,message);
    }

    /**分单*/
    public void SetOrderSplit(Context context,String user_token,String ordercode,String merchandiseweight,String specid,String verifyphone){
        dataService.SetOrderSplit(context,user_token,ordercode,merchandiseweight,specid,verifyphone);
    }

    /**分单开始，接受司机申请*/
    public void SetOrderSplitBegin(Context context,String user_token,String ordercode,String tempcode){
        dataService.SetOrderSplitBegin(context,user_token,ordercode,tempcode);
    }

    /**取消此条分单*/
    public void SetOrderSplitDelete(Context context,String user_token,String branchcode,String ordercode){
        dataService.SetOrderSplitDelete(context,user_token,branchcode,ordercode);
    }

    /**分单列表*/
    public void GetOrderSplitList(Context context,String user_token,String ordercode){
        dataService.GetOrderSplitList(context,user_token,ordercode);
    }

    /**临时分单列表*/
    public void GetOrderSplitTemList(Context context,String user_token,String ordercode){
        dataService.GetOrderSplitTemList(context,user_token,ordercode);
    }

    /**剩余重量吨数*/
    public void GetOrderSplitSurplusWeight(Context context,String user_token,String ordercode){
        dataService.GetOrderSplitSurplusWeight(context,user_token,ordercode);
    }

    /**分单修改*/
    public void OrderSplitEdit(Context context,String user_token,String ordercode,String merchandiseweight,
                               String specid,String verifyphone,String tempcode){
        dataService.OrderSplitEdit(context,user_token,ordercode,merchandiseweight,specid,verifyphone,tempcode);
    }

    /**开始发货*/
    public void OrderDeliverBegin(Context context,String user_token,String ordercode){
        dataService.OrderDeliverBegin(context,user_token,ordercode);
    }

    /**查看订单车主信息*/
    public void GetOrderVansInfo(Context context,String user_token,String branchcode){
        dataService.GetOrderVansInfo(context,user_token,branchcode);
    }

    /**获取用户基本资料*/
    public void GetUserInfo(Context context,String user_token){
        dataService.GetUserInfo(context,user_token);
    }

    /**用户头像修改*/
    public void UserAvatarEdit(Context context,String user_token,String avatar){
        dataService.UserAvatarEdit(context,user_token,avatar);
    }

    /**用户基本资料修改*/
    public void FactoryInfoEdit(Context context,String user_token,String title,String address,String latitude,String longitude,String verifyname,String verifyphone){
        dataService.FactoryInfoEdit(context,user_token,title,address,latitude,longitude,verifyname,verifyphone);
    }

    /**用户验证信息修改*/
    public void UserVerifyInfoEdit(Context context,String user_token,String verifyname,String verifyphone,String verifycid,String license){
        dataService.UserVerifyInfoEdit(context,user_token,verifyname,verifyphone,verifycid,license);
    }


    /**意见反馈*/
    public void Feedback(Context context,String user_token,String message){
        dataService.Feedback(context,user_token,message);
    }

    /**获取符合条件的所有车主信息*/
    public void GetVansInfo(Context context,String user_token,String vansspecid,String city,String page,String pagesize){
        dataService.GetVansInfo(context,user_token,vansspecid,city,page,pagesize);
    }

    /**获取收藏的车主*/
    public void GetCollectVans(Context context,String user_token,String page,String pagesize){
        dataService.GetCollectVans(context,user_token,page,pagesize);
    }

    /**按手机号 或 姓名 搜索车主*/
    public void SearchVans(Context context,String user_token,String verify_info){
        dataService.SearchVans(context,user_token,verify_info);
    }

    /**查询我的所有相关订单*/
    public void SearchMyOrders(Context context,String user_token){
        dataService.SearchMyOrders(context,user_token);
    }

    /**查询正式分单详情*/
    public void GetOrderSplitFormal(Context context,String user_token,String branchcode){
        dataService.GetOrderSplitFormal(context,user_token,branchcode);
    }

    /**查询临时分单详情*/
    public void GetOrderSplitTemp(Context context,String user_token,String tempcode){
        dataService.GetOrderSplitTemp(context,user_token,tempcode);
    }

    /**删除临时分单*/
    public void DeleteOrderSplitTemp(Context context,String user_token,String tempcode){
        dataService.DeleteOrderSplitTemp(context,user_token,tempcode);
    }

    /**改变订单状态，开始分单*/
    public void ChangeSplitStatus(Context context,String user_token,String ordercode){
        dataService.ChangeSplitStatus(context,user_token,ordercode);
    }

    /**查看虚拟订单列表*/
    public void GetVirtualOrderList(Context context,String user_token){
        dataService.GetVirtualOrderList(context,user_token);
    }

    /**查看虚拟订单详情*/
    public void GetVirtualOrderDetail(Context context,String user_token,String fictitious_ordercode){
        dataService.GetVirtualOrderDetail(context,user_token,fictitious_ordercode);
    }

    /**添加虚拟订单*/
    public void AddVirtualOrder(Context context,String user_token,String originateposition,String originateposition_city,String originateposition_district,String destinationposition
            ,String destinationposition_city,String destinationposition_district,String merchandisecategoryid,String merchandiseweight,String merchandisevolume,String vansspec
            ,String endtime,String ocitylongitude,String ocitylatitude,String dcitylongitude,String dcitylatitude,String memo,String phone,String odetailaddress,String ddetailaddress){

        dataService.AddVirtualOrder(context,user_token,originateposition,originateposition_city,originateposition_district,destinationposition,destinationposition_city,
                destinationposition_district,merchandisecategoryid,merchandiseweight,merchandisevolume,vansspec,endtime,ocitylongitude,ocitylatitude,dcitylongitude,
                dcitylatitude,memo,phone,odetailaddress,ddetailaddress);
    }

    /**查看报价车主*/
    public void SearchApplyDriverInfo(Context context,String user_token,String fictitious_ordercode){
        dataService.SearchApplyDriverInfo(context,user_token,fictitious_ordercode);
    }

    /**删除虚拟订单*/
    public void DeleteVirtualOrder(Context context,String user_token,String fictitious_ordercode){
        dataService.DeleteVirtualOrder(context,user_token,fictitious_ordercode);
    }
}
