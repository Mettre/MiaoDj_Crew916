package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/6. 18:44
 * 邮箱：248866527@qq.com
 */
public class ChooseWorkerEntity {

    /**
     * real_name : Jan
     * headimg : /Uploads/images/201606/5773b2faa6caa.png
     * score : 1
     * begin_worker_day : null
     * title : 放线员,拆墙工,普瓦
     * id : 100,101,102
     * worker_code : WOR201606000001
     * worker_age : 46年
     * type_id : ["100","101","102"]
     */

    private List<WorkerBean> worker;

    public List<WorkerBean> getWorker() {
        return worker;
    }

    public void setWorker(List<WorkerBean> worker) {
        this.worker = worker;
    }

    public static class WorkerBean {
        private String real_name;
        private String headimg;
        private String score;
        private String begin_worker_day;
        private String title;
        private String id;
        private String worker_code;
        private String worker_age;
        private List<String> type_id;

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

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getBegin_worker_day() {
            return begin_worker_day;
        }

        public void setBegin_worker_day(String begin_worker_day) {
            this.begin_worker_day = begin_worker_day;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

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

        public String getWorker_age() {
            return worker_age;
        }

        public void setWorker_age(String worker_age) {
            this.worker_age = worker_age;
        }

        public List<String> getType_id() {
            return type_id;
        }

        public void setType_id(List<String> type_id) {
            this.type_id = type_id;
        }
    }
}
