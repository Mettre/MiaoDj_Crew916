package cn.chenhai.miaodj_monitor.network_proxy.constant;

/**
 * Created by ChenHai--霜华 on 2016/5/3. 17:26
 * 邮箱：248866527@qq.com
 */
public class URLServiceConstants {
    public static final String URL_BASE = "http://test3.emplus.cn/";
    //上传图片地址
    public static final String URL_UPLOAD = "http://test3.emplus.cn/index.php?m=Logistics&c=Uploads&a=autograph";
    public static final String URL_API_UPLOAD_IMG = "http://test3.emplus.cn/index.php?m=Logistics&c=Profile&a=avatar";

    public static final String URL_API_AUTH_CODE = "http://test3.emplus.cn/index.php?m=Logistics&c=login&a=sendmsg";
    public static final String URL_API_REGISTER = "http://test3.emplus.cn/index.php?m=Logistics&c=login&a=register";
    public static final String URL_API_LOGIN = "http://test3.emplus.cn/index.php?m=Logistics&c=login&a=login";
    public static final String URL_API_REFRESH_TOKEN = "http://test3.emplus.cn/index.php?m=Logistics&c=login&a=refresh_token";

    public static final String URL_API_GET_PROVINCE = "http://test3.emplus.cn/index.php?m=Logistics&c=common&a=get_province";
    public static final String URL_API_GET_CITY = "http://test3.emplus.cn/index.php?m=Logistics&c=common&a=get_city";
    public static final String URL_API_GET_DISTRICT = "http://test3.emplus.cn/index.php?m=Logistics&c=common&a=get_district";
    public static final String URL_API_GET_PROVINCE2 = "http://test3.emplus.cn/index.php?m=Logistics&c=common&a=get_province_2";
    public static final String URL_API_GET_CITY2 = "http://test3.emplus.cn/index.php?m=Logistics&c=common&a=get_city_2";
    public static final String URL_API_GET_DISTRICT2 = "http://test3.emplus.cn/index.php?m=Logistics&c=common&a=get_district_2";

    public static final String URL_API_GET_CATEGORY = "http://test3.emplus.cn/index.php?m=Logistics&c=common&a=get_category";
    public static final String URL_API_GET_VANS_TYPE = "http://test3.emplus.cn/index.php?m=Logistics&c=common&a=get_vansspec";

    public static final String URL_API_FACTORY_ORDERS = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_factory_orders";
    public static final String URL_API_ORDER_DETAIL = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_factory_order_detail";
    public static final String URL_API_ORDER_OFFER = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=offer_order";
    //public static final String URL_API_ORDER_SPLIT = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=dispatch_order_add";
    public static final String URL_API_ORDER_SPLIT = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=add_branch_order";
    public static final String URL_API_ORDER_SPLIT_BEGIN = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=begin_branch_order";
    public static final String URL_API_ORDER_SPLIT_DELETE = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=delete_branch_order";

    //public static final String URL_API_ORDER_SPLIT_LIST = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=dispatch_order_list";
    public static final String URL_API_ORDER_SPLIT_LIST = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_split_list";
    public static final String URL_API_ORDER_SPLIT_TEM_LIST = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_split_temp_list";
    public static final String URL_API_ORDER_SPLIT_SURPLUS_WEIGHT = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_surplus_weight";
    //public static final String URL_API_ORDER_SPLIT_EDIT = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=dispatch_order_edit";
    public static final String URL_API_ORDER_SPLIT_EDIT = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=add_order_split_verify";
    public static final String URL_API_ORDER_DELIVER_BEGIN = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=begin_order";
    public static final String URL_API_ORDER_VANS_INFO = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_order_vanses";

    public static final String URL_API_USER_INFO = "http://test3.emplus.cn/index.php?m=Logistics&c=profile&a=getuserinfo";
    public static final String URL_API_AVATAR_EDIT = "http://test3.emplus.cn/index.php?m=Logistics&c=profile&a=avatar";
    public static final String URL_API_FACTORY_INFO_EDIT = "http://test3.emplus.cn/index.php?m=Logistics&c=profile&a=profile";
    public static final String URL_API_VERIFY_INFO_EDIT = "http://test3.emplus.cn/index.php?m=Logistics&c=profile&a=verify_info";

    public static final String URL_API_PASSWORD_EDIT = "http://test3.emplus.cn/index.php?m=Logistics&c=profile&a=resetpassword";
    public static final String URL_API_PASSWORD_FORGET = "http://test3.emplus.cn/index.php?m=Logistics&c=Login&a=resetpasswordbycode";

    public static final String URL_API_FEED_BACK = "http://test3.emplus.cn/index.php?m=Logistics&c=profile&a=feedback";

    public static final String URL_API_GET_VANS_INFO = "http://test3.emplus.cn/index.php?m=Logistics&c=Logistics&a=get_van_info";
    public static final String URL_API_GET_COLLECT_VANS = "http://test3.emplus.cn/index.php?m=Logistics&c=Logistics&a=get_van_favorites";
    public static final String URL_API_SEARCH_VAN = "http://test3.emplus.cn/index.php?m=Logistics&c=Logistics&a=get_per_van";

    public static final String URL_API_MY_ORDERS = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=my_orders";

    public static final String URL_API_GET_ORDER_SPLIT_FORMAL = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_order_branch";
    public static final String URL_API_GET_ORDER_SPLIT_TEMP = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_temp_order_branch";
    public static final String URL_API_GET_ORDER_SPLIT_TEMP_DELETE = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=delete_branch_temp_order";

    public static final String URL_API_CHANGE_SPLIT_STATUS = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=change_branch_status";

    public static final String URL_API_SEARCH_VIRTUAL_ORDER_LIST = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_fictitious_order";
    public static final String URL_API_SEARCH_VIRTUAL_ORDER_DETAIL = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_fictitious_order_detail";
    public static final String URL_API_ADD_VIRTUAL_ORDER = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=add_fictitious_order";
    public static final String URL_API_SEARCH_APPLY_DRIVER_INFO = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=get_apply_price_list";
    public static final String URL_API_DELETE_VIRTUAL_ORDER = "http://test3.emplus.cn/index.php?m=Logistics&c=order&a=delete_fictitious_orders";
}
