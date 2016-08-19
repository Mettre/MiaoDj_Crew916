package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/5. 12:34
 * 邮箱：248866527@qq.com
 */
public class CheckPictureEntity {


    /**
     * keep_msg :
     * drawing_status : 30
     * drawing : []
     * */

    private String keep_msg;
    private String drawing_status;
    /**
     * id : 80
     * customer_code : CF201606000008
     * house_code : HCF201606000008001
     * user_code : U32050120160026
     * imgurl : /Uploads/drawing/201606/source/577363056e52f.jpg
     * type : 1
     * status : 20
     * memo : null
     * createtime : 2016-06-29 13:56:43
     * updatetime : 2016-06-29 14:05:12
     * check_time : 2016-07-05 15:07:50
     * customer_name : 小刘
     * telephone : 18151113753
     * province_code : 320000
     * city_code : 320500
     * area_code : 320506
     * street : 现代大道
     * residential : 铂悅府
     * apartment : 5
     * room : 201
     */

    private List<DrawingBean> drawing;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public String getDrawing_status() {
        return drawing_status;
    }

    public void setDrawing_status(String drawing_status) {
        this.drawing_status = drawing_status;
    }

    public List<DrawingBean> getDrawing() {
        return drawing;
    }

    public void setDrawing(List<DrawingBean> drawing) {
        this.drawing = drawing;
    }

    public static class DrawingBean {
        private String id;
        private String customer_code;
        private String house_code;
        private String user_code;
        private String imgurl;
        private String type;
        private String status;
        private Object memo;
        private String createtime;
        private String updatetime;
        private String check_time;
        private String customer_name;
        private String telephone;
        private String province_code;
        private String city_code;
        private String area_code;
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

        public String getCustomer_code() {
            return customer_code;
        }

        public void setCustomer_code(String customer_code) {
            this.customer_code = customer_code;
        }

        public String getHouse_code() {
            return house_code;
        }

        public void setHouse_code(String house_code) {
            this.house_code = house_code;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public String getImgurl() {
            return imgurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getMemo() {
            return memo;
        }

        public void setMemo(Object memo) {
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

        public String getCheck_time() {
            return check_time;
        }

        public void setCheck_time(String check_time) {
            this.check_time = check_time;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
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
