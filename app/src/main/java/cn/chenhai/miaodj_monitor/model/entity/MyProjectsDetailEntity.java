package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/5. 21:14
 * 邮箱：248866527@qq.com
 */
public class MyProjectsDetailEntity {

    /**
     * keep_msg :
     * project : {}
     * */

    private String keep_msg;
    /**
     * id : 29
     * code_pre : 32050120160627
     * serial_no : 3
     * code : CP320501201606270003
     * store_code : S320501
     * user_code : U32050120160026
     * bargain_code : HT32050120160627001
     * customer_code : CF201606000008
     * house_code : HCF201606000008001
     * crew_code : CRE201606000001
     * design_code :
     * manager_code :
     * start_date : 2016-06-27
     * finish_date : null
     * createtime : 2016-06-27 14:33:49
     * updatetime : 2016-07-05 16:54:48
     * drawing_status : 46
     * status : 40
     * apply_start_date : null
     * disagree_reason : null
     * stop_time : null
     * house_province_name : 江苏省
     * house_city_name : 苏州市
     * house_area_name : 吴中区
     * street : 现代大道
     * residential : 铂悅府
     * apartment : 5
     * room : 201
     * area : 250
     * persons : 5
     * liveroom : 5
     * hall : 2
     * kitchen : 1
     * sunplat : 1
     * bathroom : 1
     * crew_name : 永晓
     * crew_telephone : 18068633989
     * design_name : null
     * design_telephone : null
     * manager_name : null
     * manager_telephone : null
     * customer_name : 小刘
     * customer_telephone : 18151113753
     * bargain_createtime : 2016-07-05 11:03:03
     * liveroom_chinese : 五
     * hall_chinese : 二
     * kitchen_chinese : 一
     * sunplat_chinese : 一
     * bathroom_chinese : 一
     * worker_project : []
     * show_worker_type : N
     * total_days : 9
     * delay_days : 0
     */

    private ProjectBean project;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public ProjectBean getProject() {
        return project;
    }

    public void setProject(ProjectBean project) {
        this.project = project;
    }

    public static class ProjectBean {
        private String id;
        private String code_pre;
        private String serial_no;
        private String code;
        private String store_code;
        private String user_code;
        private String bargain_code;
        private String customer_code;
        private String house_code;
        private String crew_code;
        private String design_code;
        private String manager_code;
        private String start_date;
        private String finish_date;
        private String createtime;
        private String updatetime;
        private String drawing_status;
        private String status;
        private String apply_start_date;
        private String disagree_reason;
        private String stop_time;
        private String house_province_name;
        private String house_city_name;
        private String house_area_name;
        private String street;
        private String residential;
        private String apartment;
        private String room;
        private String area;
        private String persons;
        private String liveroom;
        private String hall;
        private String kitchen;
        private String sunplat;
        private String bathroom;
        private String crew_name;
        private String crew_telephone;
        private String design_name;
        private String design_telephone;
        private String manager_name;
        private String manager_telephone;
        private String customer_name;
        private String customer_telephone;
        private String bargain_createtime;
        private String liveroom_chinese;
        private String hall_chinese;
        private String kitchen_chinese;
        private String sunplat_chinese;
        private String bathroom_chinese;
        private String show_worker_type;
        private int total_days;
        private int delay_days;
        private List<?> worker_project;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCode_pre() {
            return code_pre;
        }

        public void setCode_pre(String code_pre) {
            this.code_pre = code_pre;
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

        public String getBargain_code() {
            return bargain_code;
        }

        public void setBargain_code(String bargain_code) {
            this.bargain_code = bargain_code;
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

        public String getCrew_code() {
            return crew_code;
        }

        public void setCrew_code(String crew_code) {
            this.crew_code = crew_code;
        }

        public String getDesign_code() {
            return design_code;
        }

        public void setDesign_code(String design_code) {
            this.design_code = design_code;
        }

        public String getManager_code() {
            return manager_code;
        }

        public void setManager_code(String manager_code) {
            this.manager_code = manager_code;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getFinish_date() {
            return finish_date;
        }

        public void setFinish_date(String finish_date) {
            this.finish_date = finish_date;
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

        public String getDrawing_status() {
            return drawing_status;
        }

        public void setDrawing_status(String drawing_status) {
            this.drawing_status = drawing_status;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getApply_start_date() {
            return apply_start_date;
        }

        public void setApply_start_date(String apply_start_date) {
            this.apply_start_date = apply_start_date;
        }

        public String getDisagree_reason() {
            return disagree_reason;
        }

        public void setDisagree_reason(String disagree_reason) {
            this.disagree_reason = disagree_reason;
        }

        public String getStop_time() {
            return stop_time;
        }

        public void setStop_time(String stop_time) {
            this.stop_time = stop_time;
        }

        public String getHouse_province_name() {
            return house_province_name;
        }

        public void setHouse_province_name(String house_province_name) {
            this.house_province_name = house_province_name;
        }

        public String getHouse_city_name() {
            return house_city_name;
        }

        public void setHouse_city_name(String house_city_name) {
            this.house_city_name = house_city_name;
        }

        public String getHouse_area_name() {
            return house_area_name;
        }

        public void setHouse_area_name(String house_area_name) {
            this.house_area_name = house_area_name;
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

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPersons() {
            return persons;
        }

        public void setPersons(String persons) {
            this.persons = persons;
        }

        public String getLiveroom() {
            return liveroom;
        }

        public void setLiveroom(String liveroom) {
            this.liveroom = liveroom;
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

        public String getCrew_name() {
            return crew_name;
        }

        public void setCrew_name(String crew_name) {
            this.crew_name = crew_name;
        }

        public String getCrew_telephone() {
            return crew_telephone;
        }

        public void setCrew_telephone(String crew_telephone) {
            this.crew_telephone = crew_telephone;
        }

        public String getDesign_name() {
            return design_name;
        }

        public void setDesign_name(String design_name) {
            this.design_name = design_name;
        }

        public String getDesign_telephone() {
            return design_telephone;
        }

        public void setDesign_telephone(String design_telephone) {
            this.design_telephone = design_telephone;
        }

        public String getManager_name() {
            return manager_name;
        }

        public void setManager_name(String manager_name) {
            this.manager_name = manager_name;
        }

        public String getManager_telephone() {
            return manager_telephone;
        }

        public void setManager_telephone(String manager_telephone) {
            this.manager_telephone = manager_telephone;
        }

        public String getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(String customer_name) {
            this.customer_name = customer_name;
        }

        public String getCustomer_telephone() {
            return customer_telephone;
        }

        public void setCustomer_telephone(String customer_telephone) {
            this.customer_telephone = customer_telephone;
        }

        public String getBargain_createtime() {
            return bargain_createtime;
        }

        public void setBargain_createtime(String bargain_createtime) {
            this.bargain_createtime = bargain_createtime;
        }

        public String getLiveroom_chinese() {
            return liveroom_chinese;
        }

        public void setLiveroom_chinese(String liveroom_chinese) {
            this.liveroom_chinese = liveroom_chinese;
        }

        public String getHall_chinese() {
            return hall_chinese;
        }

        public void setHall_chinese(String hall_chinese) {
            this.hall_chinese = hall_chinese;
        }

        public String getKitchen_chinese() {
            return kitchen_chinese;
        }

        public void setKitchen_chinese(String kitchen_chinese) {
            this.kitchen_chinese = kitchen_chinese;
        }

        public String getSunplat_chinese() {
            return sunplat_chinese;
        }

        public void setSunplat_chinese(String sunplat_chinese) {
            this.sunplat_chinese = sunplat_chinese;
        }

        public String getBathroom_chinese() {
            return bathroom_chinese;
        }

        public void setBathroom_chinese(String bathroom_chinese) {
            this.bathroom_chinese = bathroom_chinese;
        }

        public String getShow_worker_type() {
            return show_worker_type;
        }

        public void setShow_worker_type(String show_worker_type) {
            this.show_worker_type = show_worker_type;
        }

        public int getTotal_days() {
            return total_days;
        }

        public void setTotal_days(int total_days) {
            this.total_days = total_days;
        }

        public int getDelay_days() {
            return delay_days;
        }

        public void setDelay_days(int delay_days) {
            this.delay_days = delay_days;
        }

        public List<?> getWorker_project() {
            return worker_project;
        }

        public void setWorker_project(List<?> worker_project) {
            this.worker_project = worker_project;
        }
    }
}
