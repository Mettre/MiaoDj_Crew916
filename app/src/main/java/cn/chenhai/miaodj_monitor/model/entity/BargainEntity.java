package cn.chenhai.miaodj_monitor.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <合同实体类>
 * <功能详细描述>
 *
 * @author Allen
 * @version [版本号, 2016/8/30 17:15]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BargainEntity implements Serializable{

    /**
     * keep_msg :
     * bargain : [{"id":"28","code":"HT32050120160818001","code_ymd":"20160818","serial_no":"1","store_code":"S320501","user_code":"U32050120160003","check_code":"U32040120160001","customer_code":"CA201608000004","customer_name":"永慧","customer_phone":"18706215466","cert_type":"身份证","cert_no":"","house_code":"HCA201608000004001","package_code":"PK201605012","agreement_code":"CI32050120160818003","agreement_money":"2000.00","standard_money":"41720.00","option_money":"7777.00","design_money":"5000.00","discount_money":"2000.00","coupon_money":"0.00","coupon_code":"","sum_money":"52497.00","memo":"","createtime":"2016-08-18 17:20:33","updatetime":"2016-08-18 17:20:33","pay_batch":"4","pay_batch_percent":"1.00","status":"20","project_address":"彩香街道彩香一村二区1幢1幢101室","expect_time":"2016-08-19 09:00:00","need_create_project":"N","customer_price":"0.00","customer_memo":"","street":"彩香街道","residential":"彩香一村二区","apartment":"1幢1","room":"101"}]
     */

    private String keep_msg;
    /**
     * id : 28
     * code : HT32050120160818001
     * code_ymd : 20160818
     * serial_no : 1
     * store_code : S320501
     * user_code : U32050120160003
     * check_code : U32040120160001
     * customer_code : CA201608000004
     * customer_name : 永慧
     * customer_phone : 18706215466
     * cert_type : 身份证
     * cert_no :
     * house_code : HCA201608000004001
     * package_code : PK201605012
     * agreement_code : CI32050120160818003
     * agreement_money : 2000.00
     * standard_money : 41720.00
     * option_money : 7777.00
     * design_money : 5000.00
     * discount_money : 2000.00
     * coupon_money : 0.00
     * coupon_code :
     * sum_money : 52497.00
     * memo :
     * createtime : 2016-08-18 17:20:33
     * updatetime : 2016-08-18 17:20:33
     * pay_batch : 4
     * pay_batch_percent : 1.00
     * status : 20
     * project_address : 彩香街道彩香一村二区1幢1幢101室
     * expect_time : 2016-08-19 09:00:00
     * need_create_project : N
     * customer_price : 0.00
     * customer_memo :
     * street : 彩香街道
     * residential : 彩香一村二区
     * apartment : 1幢1
     * room : 101
     */

    private List<BargainBean> bargain;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public List<BargainBean> getBargain() {
        return bargain;
    }

    public void setBargain(List<BargainBean> bargain) {
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
        private String street;
        private String residential;
        private String apartment;
        private String room;

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
    }
}
