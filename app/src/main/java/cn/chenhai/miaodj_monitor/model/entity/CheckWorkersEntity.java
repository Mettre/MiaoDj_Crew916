package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/6. 15:29
 * 邮箱：248866527@qq.com
 */
public class CheckWorkersEntity {

    /**
     * keep_msg :
     * worker_types : []
     * */

    private String keep_msg;
    /**
     * worker_type : 114
     * worker_type_name : 成保员
     * worker_code :
     * worker_name :
     * worker_headimg :
     * check_status :
     */

    private List<WorkerTypesBean> worker_types;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
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
        private String worker_code;
        private String worker_name;
        private String worker_headimg;
        private String check_status;

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

        public String getWorker_code() {
            return worker_code;
        }

        public void setWorker_code(String worker_code) {
            this.worker_code = worker_code;
        }

        public String getWorker_name() {
            return worker_name;
        }

        public void setWorker_name(String worker_name) {
            this.worker_name = worker_name;
        }

        public String getWorker_headimg() {
            return worker_headimg;
        }

        public void setWorker_headimg(String worker_headimg) {
            this.worker_headimg = worker_headimg;
        }

        public String getCheck_status() {
            return check_status;
        }

        public void setCheck_status(String check_status) {
            this.check_status = check_status;
        }
    }
}
