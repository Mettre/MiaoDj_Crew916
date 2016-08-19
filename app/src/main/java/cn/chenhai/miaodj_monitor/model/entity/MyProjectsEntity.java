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
     * projects : [{"project_code":"CP320501201606270002","project_status":"20","customer_code":"","house_province_name":null,"house_city_name":null,"house_area_name":null,"street":null,"residential":null,"apartment":null,"room":null,"customer_name":null,"customer_telephone":null}]
     */

    private String keep_msg;
    /**
     * project_code : CP320501201606270002
     * project_status : 20
     * customer_code :
     * house_province_name : null
     * house_city_name : null
     * house_area_name : null
     * street : null
     * residential : null
     * apartment : null
     * room : null
     * customer_name : null
     * customer_telephone : null
     */

    private List<ProjectsBean> projects;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public List<ProjectsBean> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectsBean> projects) {
        this.projects = projects;
    }

    public static class ProjectsBean implements Serializable{
        private String project_code;
        private String project_status;
        private String customer_code;
        private Object house_province_name;
        private Object house_city_name;
        private Object house_area_name;
        private Object street;
        private Object residential;
        private Object apartment;
        private Object room;
        private Object customer_name;
        private Object customer_telephone;

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

        public Object getHouse_province_name() {
            return house_province_name;
        }

        public void setHouse_province_name(Object house_province_name) {
            this.house_province_name = house_province_name;
        }

        public Object getHouse_city_name() {
            return house_city_name;
        }

        public void setHouse_city_name(Object house_city_name) {
            this.house_city_name = house_city_name;
        }

        public Object getHouse_area_name() {
            return house_area_name;
        }

        public void setHouse_area_name(Object house_area_name) {
            this.house_area_name = house_area_name;
        }

        public Object getStreet() {
            return street;
        }

        public void setStreet(Object street) {
            this.street = street;
        }

        public Object getResidential() {
            return residential;
        }

        public void setResidential(Object residential) {
            this.residential = residential;
        }

        public Object getApartment() {
            return apartment;
        }

        public void setApartment(Object apartment) {
            this.apartment = apartment;
        }

        public Object getRoom() {
            return room;
        }

        public void setRoom(Object room) {
            this.room = room;
        }

        public Object getCustomer_name() {
            return customer_name;
        }

        public void setCustomer_name(Object customer_name) {
            this.customer_name = customer_name;
        }

        public Object getCustomer_telephone() {
            return customer_telephone;
        }

        public void setCustomer_telephone(Object customer_telephone) {
            this.customer_telephone = customer_telephone;
        }
    }
}
