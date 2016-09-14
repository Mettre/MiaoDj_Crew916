package cn.chenhai.miaodj_monitor.model.entity;

/**
 * Created by ChenHai--霜华 on 2016/9/13. 16:14
 * 邮箱：248866527@qq.com
 */
public class GetBargainDetailEntity {


    /**
     * keep_msg :
     * bargain : {"id":"30","code":"HT32050120160819001","code_ymd":"20160819","serial_no":"1","store_code":"S320501","user_code":"U32050120160026","check_code":"","customer_code":"CA201608000001","customer_name":"老毕爷","customer_phone":"13830531601","cert_type":"身份证","cert_no":"320586198809274414","house_code":"HCA201608000001001","package_code":"PK201605012","agreement_code":"CI32050120160818001","agreement_money":"0.00","standard_money":"40860.86","option_money":"7777.00","design_money":"1000.00","discount_money":"500.00","coupon_money":"0.00","coupon_code":"","sum_money":"49138","memo":"无","createtime":"2016-08-19 14:21:04","updatetime":"2016-08-19 14:21:04","pay_batch":"3","pay_batch_percent":"0.98","status":"20","project_address":"就在那","expect_time":"2016-09-01 14:20:54","need_create_project":"N","customer_price":"0.00","customer_memo":"","signtime":null,"bargain_pdf":"/Uploads/bargain/HT32050120160819001.pdf","old_name":"老毕爷","decoration_area":null,"street":"南榭雨街18号","residential":"精华公寓","apartment":"11","room":"504","province_code":"320000","city_code":"320500","area_code":"320506","hall":"3","kitchen":"1","sunplat":"1","bathroom":"2","liveroom":"3","province":"江苏省","city":"苏州市","area_name":"吴中区","manager_code":"U32050120160026","is_choose_kitchen_toilet":"Y","is_choose_heart":"Y"}
     */

    private String keep_msg;
    /**
     * id : 30
     * code : HT32050120160819001
     * code_ymd : 20160819
     * serial_no : 1
     * store_code : S320501
     * user_code : U32050120160026
     * check_code :
     * customer_code : CA201608000001
     * customer_name : 老毕爷
     * customer_phone : 13830531601
     * cert_type : 身份证
     * cert_no : 320586198809274414
     * house_code : HCA201608000001001
     * package_code : PK201605012
     * agreement_code : CI32050120160818001
     * agreement_money : 0.00
     * standard_money : 40860.86
     * option_money : 7777.00
     * design_money : 1000.00
     * discount_money : 500.00
     * coupon_money : 0.00
     * coupon_code :
     * sum_money : 49138
     * memo : 无
     * createtime : 2016-08-19 14:21:04
     * updatetime : 2016-08-19 14:21:04
     * pay_batch : 3
     * pay_batch_percent : 0.98
     * status : 20
     * project_address : 就在那
     * expect_time : 2016-09-01 14:20:54
     * need_create_project : N
     * customer_price : 0.00
     * customer_memo :
     * signtime : null
     * bargain_pdf : /Uploads/bargain/HT32050120160819001.pdf
     * old_name : 老毕爷
     * decoration_area : null
     * street : 南榭雨街18号
     * residential : 精华公寓
     * apartment : 11
     * room : 504
     * province_code : 320000
     * city_code : 320500
     * area_code : 320506
     * hall : 3
     * kitchen : 1
     * sunplat : 1
     * bathroom : 2
     * liveroom : 3
     * province : 江苏省
     * city : 苏州市
     * area_name : 吴中区
     * manager_code : U32050120160026
     * is_choose_kitchen_toilet : Y
     * is_choose_heart : Y
     */

    private BargainBean bargain;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public BargainBean getBargain() {
        return bargain;
    }

    public void setBargain(BargainBean bargain) {
        this.bargain = bargain;
    }

    public static class BargainBean {
        private String id;
        private String code;
        private String code_ymd;
        private String serial_no;
        private String store_code;
        private String user_code;
        private String check_code;
        private String customer_code;
        private String customer_name;
        private String customer_phone;
        private String cert_type;
        private String cert_no;
        private String house_code;
        private String package_code;
        private String agreement_code;
        private String agreement_money;
        private String standard_money;
        private String option_money;
        private String design_money;
        private String discount_money;
        private String coupon_money;
        private String coupon_code;
        private String sum_money;
        private String memo;
        private String createtime;
        private String updatetime;
        private String pay_batch;
        private String pay_batch_percent;
        private String status;
        private String project_address;
        private String expect_time;
        private String need_create_project;
        private String customer_price;
        private String customer_memo;
        private Object signtime;
        private String bargain_pdf;
        private String old_name;
        private Object decoration_area;
        private String street;
        private String residential;
        private String apartment;
        private String room;
        private String province_code;
        private String city_code;
        private String area_code;
        private String hall;
        private String kitchen;
        private String sunplat;
        private String bathroom;
        private String liveroom;
        private String province;
        private String city;
        private String area_name;
        private String manager_code;
        private String is_choose_kitchen_toilet;
        private String is_choose_heart;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getCode_ymd() {
            return code_ymd;
        }

        public void setCode_ymd(String code_ymd) {
            this.code_ymd = code_ymd;
        }

        public String getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(String serial_no) {
            this.serial_no = serial_no;
        }

        public String getStore_code() {
            return store_code;
        }

        public void setStore_code(String store_code) {
            this.store_code = store_code;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public String getCheck_code() {
            return check_code;
        }

        public void setCheck_code(String check_code) {
            this.check_code = check_code;
        }

        public String getCustomer_code() {
            return customer_code;
        }

        public void setCustomer_code(String customer_code) {
            this.customer_code = customer_code;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getCustomer_phone() {
            return customer_phone;
        }

        public void setCustomer_phone(String customer_phone) {
            this.customer_phone = customer_phone;
        }

        public String getCert_type() {
            return cert_type;
        }

        public void setCert_type(String cert_type) {
            this.cert_type = cert_type;
        }

        public String getCert_no() {
            return cert_no;
        }

        public void setCert_no(String cert_no) {
            this.cert_no = cert_no;
        }

        public String getHouse_code() {
            return house_code;
        }

        public void setHouse_code(String house_code) {
            this.house_code = house_code;
        }

        public String getPackage_code() {
            return package_code;
        }

        public void setPackage_code(String package_code) {
            this.package_code = package_code;
        }

        public String getAgreement_code() {
            return agreement_code;
        }

        public void setAgreement_code(String agreement_code) {
            this.agreement_code = agreement_code;
        }

        public String getAgreement_money() {
            return agreement_money;
        }

        public void setAgreement_money(String agreement_money) {
            this.agreement_money = agreement_money;
        }

        public String getStandard_money() {
            return standard_money;
        }

        public void setStandard_money(String standard_money) {
            this.standard_money = standard_money;
        }

        public String getOption_money() {
            return option_money;
        }

        public void setOption_money(String option_money) {
            this.option_money = option_money;
        }

        public String getDesign_money() {
            return design_money;
        }

        public void setDesign_money(String design_money) {
            this.design_money = design_money;
        }

        public String getDiscount_money() {
            return discount_money;
        }

        public void setDiscount_money(String discount_money) {
            this.discount_money = discount_money;
        }

        public String getCoupon_money() {
            return coupon_money;
        }

        public void setCoupon_money(String coupon_money) {
            this.coupon_money = coupon_money;
        }

        public String getCoupon_code() {
            return coupon_code;
        }

        public void setCoupon_code(String coupon_code) {
            this.coupon_code = coupon_code;
        }

        public String getSum_money() {
            return sum_money;
        }

        public void setSum_money(String sum_money) {
            this.sum_money = sum_money;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getUpdatetime() {
            return updatetime;
        }

        public void setUpdatetime(String updatetime) {
            this.updatetime = updatetime;
        }

        public String getPay_batch() {
            return pay_batch;
        }

        public void setPay_batch(String pay_batch) {
            this.pay_batch = pay_batch;
        }

        public String getPay_batch_percent() {
            return pay_batch_percent;
        }

        public void setPay_batch_percent(String pay_batch_percent) {
            this.pay_batch_percent = pay_batch_percent;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getProject_address() {
            return project_address;
        }

        public void setProject_address(String project_address) {
            this.project_address = project_address;
        }

        public String getExpect_time() {
            return expect_time;
        }

        public void setExpect_time(String expect_time) {
            this.expect_time = expect_time;
        }

        public String getNeed_create_project() {
            return need_create_project;
        }

        public void setNeed_create_project(String need_create_project) {
            this.need_create_project = need_create_project;
        }

        public String getCustomer_price() {
            return customer_price;
        }

        public void setCustomer_price(String customer_price) {
            this.customer_price = customer_price;
        }

        public String getCustomer_memo() {
            return customer_memo;
        }

        public void setCustomer_memo(String customer_memo) {
            this.customer_memo = customer_memo;
        }

        public Object getSigntime() {
            return signtime;
        }

        public void setSigntime(Object signtime) {
            this.signtime = signtime;
        }

        public String getBargain_pdf() {
            return bargain_pdf;
        }

        public void setBargain_pdf(String bargain_pdf) {
            this.bargain_pdf = bargain_pdf;
        }

        public String getOld_name() {
            return old_name;
        }

        public void setOld_name(String old_name) {
            this.old_name = old_name;
        }

        public Object getDecoration_area() {
            return decoration_area;
        }

        public void setDecoration_area(Object decoration_area) {
            this.decoration_area = decoration_area;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getResidential() {
            return residential;
        }

        public void setResidential(String residential) {
            this.residential = residential;
        }

        public String getApartment() {
            return apartment;
        }

        public void setApartment(String apartment) {
            this.apartment = apartment;
        }

        public String getRoom() {
            return room;
        }

        public void setRoom(String room) {
            this.room = room;
        }

        public String getProvince_code() {
            return province_code;
        }

        public void setProvince_code(String province_code) {
            this.province_code = province_code;
        }

        public String getCity_code() {
            return city_code;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getHall() {
            return hall;
        }

        public void setHall(String hall) {
            this.hall = hall;
        }

        public String getKitchen() {
            return kitchen;
        }

        public void setKitchen(String kitchen) {
            this.kitchen = kitchen;
        }

        public String getSunplat() {
            return sunplat;
        }

        public void setSunplat(String sunplat) {
            this.sunplat = sunplat;
        }

        public String getBathroom() {
            return bathroom;
        }

        public void setBathroom(String bathroom) {
            this.bathroom = bathroom;
        }

        public String getLiveroom() {
            return liveroom;
        }

        public void setLiveroom(String liveroom) {
            this.liveroom = liveroom;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getManager_code() {
            return manager_code;
        }

        public void setManager_code(String manager_code) {
            this.manager_code = manager_code;
        }

        public String getIs_choose_kitchen_toilet() {
            return is_choose_kitchen_toilet;
        }

        public void setIs_choose_kitchen_toilet(String is_choose_kitchen_toilet) {
            this.is_choose_kitchen_toilet = is_choose_kitchen_toilet;
        }

        public String getIs_choose_heart() {
            return is_choose_heart;
        }

        public void setIs_choose_heart(String is_choose_heart) {
            this.is_choose_heart = is_choose_heart;
        }
    }
}
