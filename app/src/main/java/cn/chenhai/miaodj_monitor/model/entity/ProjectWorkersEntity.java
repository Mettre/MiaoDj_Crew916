package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/6. 15:10
 * 邮箱：248866527@qq.com
 */
public class ProjectWorkersEntity {

    /**
     * keep_msg :
     * workers : []
     * */

    private String keep_msg;
    /**
     * id : 21
     * worker_code : WOR201606000001
     * worker_role : 100
     * project_code : CP320501201606270003
     * createtime : 2016-06-28 11:54:46
     * updatetime : 2016-07-04 17:34:37
     * status : 3
     * real_name : Jan
     * telephone : 18068633989
     * role_name : 放线员
     * headimg : /Uploads/images/201606/5773b2faa6caa.png
     */

    private List<WorkersBean> workers;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public List<WorkersBean> getWorkers() {
        return workers;
    }

    public void setWorkers(List<WorkersBean> workers) {
        this.workers = workers;
    }

    public static class WorkersBean {
        private String id;
        private String worker_code;
        private String worker_role;
        private String project_code;
        private String createtime;
        private String updatetime;
        private String status;
        private String real_name;
        private String telephone;
        private String role_name;
        private String headimg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWorker_code() {
            return worker_code;
        }

        public void setWorker_code(String worker_code) {
            this.worker_code = worker_code;
        }

        public String getWorker_role() {
            return worker_role;
        }

        public void setWorker_role(String worker_role) {
            this.worker_role = worker_role;
        }

        public String getProject_code() {
            return project_code;
        }

        public void setProject_code(String project_code) {
            this.project_code = project_code;
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

        public String getRole_name() {
            return role_name;
        }

        public void setRole_name(String role_name) {
            this.role_name = role_name;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }
    }
}
