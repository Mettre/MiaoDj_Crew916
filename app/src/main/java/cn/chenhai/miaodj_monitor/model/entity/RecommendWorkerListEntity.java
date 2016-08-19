package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/8. 18:39
 * 邮箱：248866527@qq.com
 */
public class RecommendWorkerListEntity {

    /**
     * recommend : []
     * * sum : 2
     * sum_commission : 2
     */

    private int sum;
    private int sum_commission;
    /**
     * recommend_status : joined
     * recommended_name : kkkk
     * recommended_telephone : 15895916797
     * id : 38
     * username : 15895916797
     * password : null
     * real_name : kkkk
     * telephone : 15895916797
     * code_ym : 201607
     * serial_no : 4
     * code : WOR201607000004
     * headimg : /Uploads/images/worker/201607/577f564a80b53.png
     * province : 110000
     * city : 110100
     * area : null
     * address : null
     * gender : 男
     * weixin_name : null
     * openid : null
     * user_code : null
     * source : APP
     * createtime : 2016-07-08 15:28:19
     * updatetime : 2016-07-08 15:28:19
     * status : 10
     * work_province : null
     * work_city : null
     * parent_code : CRE201606000001
     * birthday : null
     * begin_worker_day : null
     * score : 1
     * id_card_back : /Uploads/images/worker/201607/577f5643b2276.png
     * id_card : /Uploads/images/worker/201607/577f563cad975.png
     * real_headimg : /Uploads/images/worker/201607/577f564a80b53.png
     * id_card_number : 230204199211022112
     * push_nodes_message : 1
     * push_material_message : 1
     * commission : 2.00
     */

    private List<RecommendBean> recommend;

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getSum_commission() {
        return sum_commission;
    }

    public void setSum_commission(int sum_commission) {
        this.sum_commission = sum_commission;
    }

    public List<RecommendBean> getRecommend() {
        return recommend;
    }

    public void setRecommend(List<RecommendBean> recommend) {
        this.recommend = recommend;
    }

    public static class RecommendBean {
        private String recommend_status;
        private String recommended_name;
        private String recommended_telephone;
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
        private String parent_code;
        private String birthday;
        private String begin_worker_day;
        private String score;
        private String id_card_back;
        private String id_card;
        private String real_headimg;
        private String id_card_number;
        private String push_nodes_message;
        private String push_material_message;
        private String commission;

        public String getRecommend_status() {
            return recommend_status;
        }

        public void setRecommend_status(String recommend_status) {
            this.recommend_status = recommend_status;
        }

        public String getRecommended_name() {
            return recommended_name;
        }

        public void setRecommended_name(String recommended_name) {
            this.recommended_name = recommended_name;
        }

        public String getRecommended_telephone() {
            return recommended_telephone;
        }

        public void setRecommended_telephone(String recommended_telephone) {
            this.recommended_telephone = recommended_telephone;
        }

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

        public String getParent_code() {
            return parent_code;
        }

        public void setParent_code(String parent_code) {
            this.parent_code = parent_code;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getBegin_worker_day() {
            return begin_worker_day;
        }

        public void setBegin_worker_day(String begin_worker_day) {
            this.begin_worker_day = begin_worker_day;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getId_card_back() {
            return id_card_back;
        }

        public void setId_card_back(String id_card_back) {
            this.id_card_back = id_card_back;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getReal_headimg() {
            return real_headimg;
        }

        public void setReal_headimg(String real_headimg) {
            this.real_headimg = real_headimg;
        }

        public String getId_card_number() {
            return id_card_number;
        }

        public void setId_card_number(String id_card_number) {
            this.id_card_number = id_card_number;
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

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }
    }
}
