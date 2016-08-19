package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/6. 16:39
 * 邮箱：248866527@qq.com
 */
public class ProjectWorkersInfoEntity {

    /**
     * keep_msg :
     * worker : {"real_name":"Jan","headimg":"/Uploads/images/201606/5773b2faa6caa.png","telephone":"18068633989","createtime":"2016-06-25 09:09:58","score":"1","age":0,"work_age":0,"worker_types":[{"worker_type":"100","worker_type_name":"放线员"},{"worker_type":"101","worker_type_name":"拆墙工"},{"worker_type":"102","worker_type_name":"普瓦"}],"order":"1"}
     */

    private String keep_msg;
    /**
     * real_name : Jan
     * headimg : /Uploads/images/201606/5773b2faa6caa.png
     * telephone : 18068633989
     * createtime : 2016-06-25 09:09:58
     * score : 1
     * age : 0
     * work_age : 0
     * worker_types : [{"worker_type":"100","worker_type_name":"放线员"},{"worker_type":"101","worker_type_name":"拆墙工"},{"worker_type":"102","worker_type_name":"普瓦"}]
     * order : 1
     */

    private WorkerBean worker;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public WorkerBean getWorker() {
        return worker;
    }

    public void setWorker(WorkerBean worker) {
        this.worker = worker;
    }

    public static class WorkerBean {
        private String real_name;
        private String headimg;
        private String telephone;
        private String createtime;
        private String score;
        private int age;
        private int work_age;
        private String order;
        /**
         * worker_type : 100
         * worker_type_name : 放线员
         */

        private List<WorkerTypesBean> worker_types;

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getWork_age() {
            return work_age;
        }

        public void setWork_age(int work_age) {
            this.work_age = work_age;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }

        public List<WorkerTypesBean> getWorker_types() {
            return worker_types;
        }

        public void setWorker_types(List<WorkerTypesBean> worker_types) {
            this.worker_types = worker_types;
        }

        public static class WorkerTypesBean {
            private String worker_type;
            private String worker_type_name;

            public String getWorker_type() {
                return worker_type;
            }

            public void setWorker_type(String worker_type) {
                this.worker_type = worker_type;
            }

            public String getWorker_type_name() {
                return worker_type_name;
            }

            public void setWorker_type_name(String worker_type_name) {
                this.worker_type_name = worker_type_name;
            }
        }
    }
}
