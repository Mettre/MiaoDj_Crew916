package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;
import java.util.Objects;

/**
 * Created by ChenHai--霜华 on 2016/8/9. 17:18
 * 邮箱：248866527@qq.com
 */
public class SelectionListAuxilaryEntity {

    /**
     * keep_msg :
     * deliver_order : {"id":"23","deliver_code":"20160809000001","serial_no":"1","order_code":"CM320501201608060002","status":"2","start_deliver_time":"2016-08-09 15:58:10","expect_arrive_time":"2016-08-09","arrive_time":"2016-08-09 16:02:33","sign_pic":"/upload/sss.jpg","createtime":"2016-08-09 15:58:10","updatetime":"2016-08-09 16:02:33"}
     * order_list : [{"title":"砂纸","class_name":"瓦工油工材料","specs":"320#","specs2":"","model":"","brand_name":"全材","amount":"2","unit":"个","status":"7","thumb_image":"/Public/Img/logo_default.png","images":[]},{"title":"双联双控","class_name":"开关插座","specs":"86mm*86mm","specs2":"","model":"78001193","brand_name":"施耐德","amount":"2","unit":"个","status":"7","thumb_image":"/Public/Img/logo_default.png","images":[]}]
     */

    private String keep_msg;
    /**
     * id : 23
     * deliver_code : 20160809000001
     * serial_no : 1
     * order_code : CM320501201608060002
     * status : 2
     * start_deliver_time : 2016-08-09 15:58:10
     * expect_arrive_time : 2016-08-09
     * arrive_time : 2016-08-09 16:02:33
     * sign_pic : /upload/sss.jpg
     * createtime : 2016-08-09 15:58:10
     * updatetime : 2016-08-09 16:02:33
     */

    private DeliverOrderBean deliver_order;
    /**
     * title : 砂纸
     * class_name : 瓦工油工材料
     * specs : 320#
     * specs2 :
     * model :
     * brand_name : 全材
     * amount : 2
     * unit : 个
     * status : 7
     * thumb_image : /Public/Img/logo_default.png
     * images : []
     */

    private List<OrderListBean> order_list;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public DeliverOrderBean getDeliver_order() {
        return deliver_order;
    }

    public void setDeliver_order(DeliverOrderBean deliver_order) {
        this.deliver_order = deliver_order;
    }

    public List<OrderListBean> getOrder_list() {
        return order_list;
    }

    public void setOrder_list(List<OrderListBean> order_list) {
        this.order_list = order_list;
    }

    public static class DeliverOrderBean {
        private String id;
        private String deliver_code;
        private String serial_no;
        private String order_code;
        private String status;
        private String start_deliver_time;
        private String expect_arrive_time;
        private String arrive_time;
        private String sign_pic;
        private String createtime;
        private String updatetime;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDeliver_code() {
            return deliver_code;
        }

        public void setDeliver_code(String deliver_code) {
            this.deliver_code = deliver_code;
        }

        public String getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(String serial_no) {
            this.serial_no = serial_no;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStart_deliver_time() {
            return start_deliver_time;
        }

        public void setStart_deliver_time(String start_deliver_time) {
            this.start_deliver_time = start_deliver_time;
        }

        public String getExpect_arrive_time() {
            return expect_arrive_time;
        }

        public void setExpect_arrive_time(String expect_arrive_time) {
            this.expect_arrive_time = expect_arrive_time;
        }

        public String getArrive_time() {
            return arrive_time;
        }

        public void setArrive_time(String arrive_time) {
            this.arrive_time = arrive_time;
        }

        public String getSign_pic() {
            return sign_pic;
        }

        public void setSign_pic(String sign_pic) {
            this.sign_pic = sign_pic;
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
    }

    public static class OrderListBean {
        private String title;
        private String class_name;
        private String specs;
        private String specs2;
        private String model;
        private String brand_name;
        private String amount;
        private String unit;
        private String status;
        private String thumb_image;
        private List<?> images;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getSpecs() {
            return specs;
        }

        public void setSpecs(String specs) {
            this.specs = specs;
        }

        public String getSpecs2() {
            return specs2;
        }

        public void setSpecs2(String specs2) {
            this.specs2 = specs2;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getBrand_name() {
            return brand_name;
        }

        public void setBrand_name(String brand_name) {
            this.brand_name = brand_name;
        }

        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getThumb_image() {
            return thumb_image;
        }

        public void setThumb_image(String thumb_image) {
            this.thumb_image = thumb_image;
        }

        public List<?> getImages() {
            return images;
        }

        public void setImages(List<?> images) {
            this.images = images;
        }
    }
}
