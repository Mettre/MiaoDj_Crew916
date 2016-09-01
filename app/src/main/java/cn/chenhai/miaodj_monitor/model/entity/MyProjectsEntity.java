package cn.chenhai.miaodj_monitor.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/2. 17:48
 * 邮箱：248866527@qq.com
 */
public class MyProjectsEntity implements Serializable{


    /**
     * keep_msg :
     * projects : [{"project_code":"CP320501201608180002","project_status":"70","customer_code":"CA201608000004","house_province_name":"江苏省","house_city_name":"苏州市","house_area_name":"姑苏区","street":"彩香街道","residential":"彩香一村二区","apartment":"1幢1","room":"101","customer_name":"永慧","customer_telephone":"18706215466"},{"project_code":"CP320501201608180001","project_status":"50","customer_code":"CA201608000002","house_province_name":"江苏省","house_city_name":"苏州市","house_area_name":"吴中区","street":"星湖街若水路","residential":"精英公寓","apartment":"5","room":"502","customer_name":"孙婷婷","customer_telephone":"15190063784"}]
     * total_page : 1
     */

    private String keep_msg;
    private int total_page;
    /**
     * project_code : CP320501201608180002
     * project_status : 70
     * customer_code : CA201608000004
     * house_province_name : 江苏省
     * house_city_name : 苏州市
     * house_area_name : 姑苏区
     * street : 彩香街道
     * residential : 彩香一村二区
     * apartment : 1幢1
     * room : 101
     * customer_name : 永慧
     * customer_telephone : 18706215466
     */

    private List<ProjectsBean> projects;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<ProjectsBean> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectsBean> projects) {
        this.projects = projects;
    }

    public static class ProjectsBean {
        private String project_code;
        private String project_status;
        private String customer_code;
        private String house_province_name;
        private String house_city_name;
        private String house_area_name;
        private String street;
        private String residential;
        private String apartment;
        private String room;
        private String customer_name;
        private String customer_telephone;

        public String getProject_code() {
            return project_code;
        }

        public void setProject_code(String project_code) {
            this.project_code = project_code;
        }

        public String getProject_status() {
            return project_status;
        }

        public void setProject_status(String project_status) {
            this.project_status = project_status;
        }

        public String getCustomer_code() {
            return customer_code;
        }

        public void setCustomer_code(String customer_code) {
            this.customer_code = customer_code;
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
    }
}
