package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/8. 19:38
 * 邮箱：248866527@qq.com
 */
public class RecommendWorkerDetailEntity {

    /**
     * keep_msg :
     * recommend : {"headimg":"/Uploads/images/worker/201607/577f564a80b53.png","worker_code":"WOR201607000004","telephone":"15895916797","real_name":"kkkk","worker_type":[{"worker_type":"101","worker_type_name":"拆墙工"}],"order_count":1,"commission_all":589,"recommend_log":[{"id":"16","user_code":"CRE201606000001","recommended_code":"WOR201607000004","commission":"1.00","project_code":null,"commission_code":"YJSZ2016070766564","title":"注册新用户","working_start":null,"working_end":null,"type":"recommend","createtime":"2016-07-07 16:27:55"},{"id":"17","user_code":"CRE201606000001","recommended_code":"WOR201607000004","commission":"588.00","project_code":"CP320501201606250001","commission_code":"YJSZ2016070754654","title":"放线项目","working_start":"2016-07-07","working_end":"2016-07-07","type":"decorate","createtime":"2016-07-07 17:27:40"}]}
     */

    private String keep_msg;
    /**
     * headimg : /Uploads/images/worker/201607/577f564a80b53.png
     * worker_code : WOR201607000004
     * telephone : 15895916797
     * real_name : kkkk
     * worker_type : [{"worker_type":"101","worker_type_name":"拆墙工"}]
     * order_count : 1
     * commission_all : 589
     * recommend_log : [{"id":"16","user_code":"CRE201606000001","recommended_code":"WOR201607000004","commission":"1.00","project_code":null,"commission_code":"YJSZ2016070766564","title":"注册新用户","working_start":null,"working_end":null,"type":"recommend","createtime":"2016-07-07 16:27:55"},{"id":"17","user_code":"CRE201606000001","recommended_code":"WOR201607000004","commission":"588.00","project_code":"CP320501201606250001","commission_code":"YJSZ2016070754654","title":"放线项目","working_start":"2016-07-07","working_end":"2016-07-07","type":"decorate","createtime":"2016-07-07 17:27:40"}]
     */

    private RecommendBean recommend;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public RecommendBean getRecommend() {
        return recommend;
    }

    public void setRecommend(RecommendBean recommend) {
        this.recommend = recommend;
    }

    public static class RecommendBean {
        private String headimg;
        private String worker_code;
        private String telephone;
        private String real_name;
        private int order_count;
        private int commission_all;
        /**
         * worker_type : 101
         * worker_type_name : 拆墙工
         */

        private List<WorkerTypeBean> worker_type;
        /**
         * id : 16
         * user_code : CRE201606000001
         * recommended_code : WOR201607000004
         * commission : 1.00
         * project_code : null
         * commission_code : YJSZ2016070766564
         * title : 注册新用户
         * working_start : null
         * working_end : null
         * type : recommend
         * createtime : 2016-07-07 16:27:55
         */

        private List<RecommendLogBean> recommend_log;

        public String getHeadimg() {
            return headimg;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public String getWorker_code() {
            return worker_code;
        }

        public void setWorker_code(String worker_code) {
            this.worker_code = worker_code;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public int getOrder_count() {
            return order_count;
        }

        public void setOrder_count(int order_count) {
            this.order_count = order_count;
        }

        public int getCommission_all() {
            return commission_all;
        }

        public void setCommission_all(int commission_all) {
            this.commission_all = commission_all;
        }

        public List<WorkerTypeBean> getWorker_type() {
            return worker_type;
        }

        public void setWorker_type(List<WorkerTypeBean> worker_type) {
            this.worker_type = worker_type;
        }

        public List<RecommendLogBean> getRecommend_log() {
            return recommend_log;
        }

        public void setRecommend_log(List<RecommendLogBean> recommend_log) {
            this.recommend_log = recommend_log;
        }

        public static class WorkerTypeBean {
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

        public static class RecommendLogBean {
            private String id;
            private String user_code;
            private String recommended_code;
            private String commission;
            private String project_code;
            private String commission_code;
            private String title;
            private String working_start;
            private String working_end;
            private String type;
            private String createtime;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUser_code() {
                return user_code;
            }

            public void setUser_code(String user_code) {
                this.user_code = user_code;
            }

            public String getRecommended_code() {
                return recommended_code;
            }

            public void setRecommended_code(String recommended_code) {
                this.recommended_code = recommended_code;
            }

            public String getCommission() {
                return commission;
            }

            public void setCommission(String commission) {
                this.commission = commission;
            }

            public String getProject_code() {
                return project_code;
            }

            public void setProject_code(String project_code) {
                this.project_code = project_code;
            }

            public String getCommission_code() {
                return commission_code;
            }

            public void setCommission_code(String commission_code) {
                this.commission_code = commission_code;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getWorking_start() {
                return working_start;
            }

            public void setWorking_start(String working_start) {
                this.working_start = working_start;
            }

            public String getWorking_end() {
                return working_end;
            }

            public void setWorking_end(String working_end) {
                this.working_end = working_end;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }
        }
    }
}
