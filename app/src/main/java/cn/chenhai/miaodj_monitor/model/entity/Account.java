package cn.chenhai.miaodj_monitor.model.entity;

import java.io.Serializable;

/**
 * Created by ChenHai--霜华 on 2016/7/1. 11:05
 * 邮箱：248866527@qq.com
 */
public class Account {

    /**
     * id : 27
     * username : 18068633989
     * password : c85c140f92b8e359894d4e338d656671
     * real_name : 永晓
     * telephone : 18068633989
     * code_ym : 201606
     * serial_no : 1
     * code : CRE201606000001
     * headimg : /Uploads/images/201606/57738afe4a649.png
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
     * work_province : 北京市
     * work_city : 北京市
     * todo_cnt : 3
     */

    private CrewBean crew;

    public CrewBean getCrew() {
        return crew;
    }
    public void setCrew(CrewBean crew) {
        this.crew = crew;
    }

    public static class CrewBean implements Serializable {
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
        private String todo_cnt;

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

        public String getTodo_cnt() {
            return todo_cnt;
        }

        public void setTodo_cnt(String todo_cnt) {
            this.todo_cnt = todo_cnt;
        }
    }
}
