package cn.chenhai.miaodj_monitor.model.entity;

/**
 * Created by ChenHai--霜华 on 2016/7/10. 13:18
 * 邮箱：248866527@qq.com
 */
public class UserInfoEntity {

    /**
     * keep_msg :
     * crew : {"id":"27","username":"18068633989","password":"c85c140f92b8e359894d4e338d656671","real_name":"永晓","telephone":"18068633989","code_ym":"201606","serial_no":"1","code":"CRE201606000001","headimg":"/Uploads/images/201607/5781cd671d2a1.jpg","province":null,"city":null,"area":null,"address":null,"gender":"男","weixin_name":null,"openid":null,"user_code":null,"source":"APP","createtime":"2016-06-25 09:07:41","updatetime":"2016-06-25 09:07:41","status":"1","work_province":null,"work_city":null,"push_nodes_message":"1","push_material_message":"1","province_name":null,"city_name":null,"area_name":null}
     */

    private String keep_msg;
    /**
     * id : 27
     * username : 18068633989
     * password : c85c140f92b8e359894d4e338d656671
     * real_name : 永晓
     * telephone : 18068633989
     * code_ym : 201606
     * serial_no : 1
     * code : CRE201606000001
     * headimg : /Uploads/images/201607/5781cd671d2a1.jpg
     * province : null
     * city : null
     * area : null
     * address : null
     * gender : 男
     * weixin_name : null
     * openid : null
     * user_code : null
     * source : APP
     * createtime : 2016-06-25 09:07:41
     * updatetime : 2016-06-25 09:07:41
     * status : 1
     * work_province : null
     * work_city : null
     * push_nodes_message : 1
     * push_material_message : 1
     * province_name : null
     * city_name : null
     * area_name : null
     */

    private CrewBean crew;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public CrewBean getCrew() {
        return crew;
    }

    public void setCrew(CrewBean crew) {
        this.crew = crew;
    }

    public static class CrewBean {
        private String id;
        private String username;
        private String password;
        private String real_name;
        private String telephone;
        private String code_ym;
        private String serial_no;
        private String code;
        private String headimg;
        private String province;
        private String city;
        private String area;
        private String address;
        private String gender;
        private String weixin_name;
        private String openid;
        private String user_code;
        private String source;
        private String createtime;
        private String updatetime;
        private String status;
        private String work_province;
        private String work_city;
        private String push_nodes_message;
        private String push_material_message;
        private String province_name;
        private String city_name;
        private String area_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getCode_ym() {
            return code_ym;
        }

        public void setCode_ym(String code_ym) {
            this.code_ym = code_ym;
        }

        public String getSerial_no() {
            return serial_no;
        }

        public void setSerial_no(String serial_no) {
            this.serial_no = serial_no;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
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

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getWeixin_name() {
            return weixin_name;
        }

        public void setWeixin_name(String weixin_name) {
            this.weixin_name = weixin_name;
        }

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getUser_code() {
            return user_code;
        }

        public void setUser_code(String user_code) {
            this.user_code = user_code;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWork_province() {
            return work_province;
        }

        public void setWork_province(String work_province) {
            this.work_province = work_province;
        }

        public String getWork_city() {
            return work_city;
        }

        public void setWork_city(String work_city) {
            this.work_city = work_city;
        }

        public String getPush_nodes_message() {
            return push_nodes_message;
        }

        public void setPush_nodes_message(String push_nodes_message) {
            this.push_nodes_message = push_nodes_message;
        }

        public String getPush_material_message() {
            return push_material_message;
        }

        public void setPush_material_message(String push_material_message) {
            this.push_material_message = push_material_message;
        }

        public String getProvince_name() {
            return province_name;
        }

        public void setProvince_name(String province_name) {
            this.province_name = province_name;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }
    }
}
