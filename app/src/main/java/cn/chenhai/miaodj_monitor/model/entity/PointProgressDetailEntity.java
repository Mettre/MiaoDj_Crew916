package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/8. 11:07
 * 邮箱：248866527@qq.com
 */
public class PointProgressDetailEntity {

    /**
     * keep_msg :
     * node : {"id":"320","node_id":"19","title":"隐蔽工程验收","project_code":"CP320501201606250001","expect_start_date":"2016-07-13","expect_end_date":"2016-07-13","actual_start_date":"2016-06-29","actual_end_date":"2016-06-29 20:00:44","customer_code":"CF201606000003","store_code":"S320501","crew_code":"CRE201606000001","status":"100","worker_type":"质检员","crew_start_date":null,"worker_start_date":"2016-06-29 19:54:37","worker_end_date":"2016-06-29 19:54:41","crew_end_date":"2016-06-29 19:54:51","customer_end_date":"2016-06-29 20:00:44","disagree_reason":null,"assess":null,"score":null,"evaluated_date":null,"worker_name":"丁楠","worker_code":"WOR201606000003","worker_source":null,"working_action_role":"worker","need_crew_check":"Y","need_customer_check":"Y","logs":[{"title":"工人进场施工","type":"1","reason":"","createtime":"2016-06-28 22:58:20","customer_name":"小吴","telephone":"18151113752"},{"title":"工人施工完成","type":"1","reason":"","createtime":"2016-06-28 22:58:24","customer_name":"小吴","telephone":"18151113752"},{"title":"施工员确认施工完成","type":"1","reason":"","createtime":"2016-06-28 23:00:29","customer_name":"小吴","telephone":"18151113752"},{"title":"业主验收不通过","type":"2","reason":"我不","createtime":"2016-06-28 23:01:13","customer_name":"小吴","telephone":"18151113752"},{"title":"施工员确认施工完成","type":"1","reason":"","createtime":"2016-06-28 23:01:26","customer_name":"小吴","telephone":"18151113752"},{"title":"业主验收通过","type":"1","reason":"","createtime":"2016-06-28 23:01:31","customer_name":"小吴","telephone":"18151113752"},{"title":"业主已评价","type":"1","reason":"","createtime":"2016-06-28 23:02:26","customer_name":"小吴","telephone":"18151113752"},{"title":"工人进场施工","type":"1","reason":"","createtime":"2016-06-29 19:54:37","customer_name":"小吴","telephone":"18151113752"},{"title":"工人施工完成","type":"1","reason":"","createtime":"2016-06-29 19:54:41","customer_name":"小吴","telephone":"18151113752"},{"title":"施工员确认施工完成","type":"1","reason":"","createtime":"2016-06-29 19:54:51","customer_name":"小吴","telephone":"18151113752"},{"title":"业主验收通过","type":"1","reason":"","createtime":"2016-06-29 20:00:44","customer_name":"小吴","telephone":"18151113752"}]}
     */

    private String keep_msg;
    /**
     * id : 320
     * node_id : 19
     * title : 隐蔽工程验收
     * project_code : CP320501201606250001
     * expect_start_date : 2016-07-13
     * expect_end_date : 2016-07-13
     * actual_start_date : 2016-06-29
     * actual_end_date : 2016-06-29 20:00:44
     * customer_code : CF201606000003
     * store_code : S320501
     * crew_code : CRE201606000001
     * status : 100
     * worker_type : 质检员
     * crew_start_date : null
     * worker_start_date : 2016-06-29 19:54:37
     * worker_end_date : 2016-06-29 19:54:41
     * crew_end_date : 2016-06-29 19:54:51
     * customer_end_date : 2016-06-29 20:00:44
     * disagree_reason : null
     * assess : null
     * score : null
     * evaluated_date : null
     * worker_name : 丁楠
     * worker_code : WOR201606000003
     * worker_source : null
     * working_action_role : worker
     * need_crew_check : Y
     * need_customer_check : Y
     * logs : [{"title":"工人进场施工","type":"1","reason":"","createtime":"2016-06-28 22:58:20","customer_name":"小吴","telephone":"18151113752"},{"title":"工人施工完成","type":"1","reason":"","createtime":"2016-06-28 22:58:24","customer_name":"小吴","telephone":"18151113752"},{"title":"施工员确认施工完成","type":"1","reason":"","createtime":"2016-06-28 23:00:29","customer_name":"小吴","telephone":"18151113752"},{"title":"业主验收不通过","type":"2","reason":"我不","createtime":"2016-06-28 23:01:13","customer_name":"小吴","telephone":"18151113752"},{"title":"施工员确认施工完成","type":"1","reason":"","createtime":"2016-06-28 23:01:26","customer_name":"小吴","telephone":"18151113752"},{"title":"业主验收通过","type":"1","reason":"","createtime":"2016-06-28 23:01:31","customer_name":"小吴","telephone":"18151113752"},{"title":"业主已评价","type":"1","reason":"","createtime":"2016-06-28 23:02:26","customer_name":"小吴","telephone":"18151113752"},{"title":"工人进场施工","type":"1","reason":"","createtime":"2016-06-29 19:54:37","customer_name":"小吴","telephone":"18151113752"},{"title":"工人施工完成","type":"1","reason":"","createtime":"2016-06-29 19:54:41","customer_name":"小吴","telephone":"18151113752"},{"title":"施工员确认施工完成","type":"1","reason":"","createtime":"2016-06-29 19:54:51","customer_name":"小吴","telephone":"18151113752"},{"title":"业主验收通过","type":"1","reason":"","createtime":"2016-06-29 20:00:44","customer_name":"小吴","telephone":"18151113752"}]
     */

    private NodeBean node;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public NodeBean getNode() {
        return node;
    }

    public void setNode(NodeBean node) {
        this.node = node;
    }

    public static class NodeBean {
        private String id;
        private String node_id;
        private String title;
        private String project_code;
        private String expect_start_date;
        private String expect_end_date;
        private String actual_start_date;
        private String actual_end_date;
        private String customer_code;
        private String store_code;
        private String crew_code;
        private String status;
        private String worker_type;
        private String crew_start_date;
        private String worker_start_date;
        private String worker_end_date;
        private String crew_end_date;
        private String customer_end_date;
        private String disagree_reason;
        private String assess;
        private String score;
        private String evaluated_date;
        private String worker_name;
        private String worker_code;
        private String worker_source;
        private String working_action_role;
        private String need_crew_check;
        private String need_customer_check;
        /**
         * title : 工人进场施工
         * type : 1
         * reason :
         * createtime : 2016-06-28 22:58:20
         * customer_name : 小吴
         * telephone : 18151113752
         */

        private List<LogsBean> logs;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNode_id() {
            return node_id;
        }

        public void setNode_id(String node_id) {
            this.node_id = node_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getProject_code() {
            return project_code;
        }

        public void setProject_code(String project_code) {
            this.project_code = project_code;
        }

        public String getExpect_start_date() {
            return expect_start_date;
        }

        public void setExpect_start_date(String expect_start_date) {
            this.expect_start_date = expect_start_date;
        }

        public String getExpect_end_date() {
            return expect_end_date;
        }

        public void setExpect_end_date(String expect_end_date) {
            this.expect_end_date = expect_end_date;
        }

        public String getActual_start_date() {
            return actual_start_date;
        }

        public void setActual_start_date(String actual_start_date) {
            this.actual_start_date = actual_start_date;
        }

        public String getActual_end_date() {
            return actual_end_date;
        }

        public void setActual_end_date(String actual_end_date) {
            this.actual_end_date = actual_end_date;
        }

        public String getCustomer_code() {
            return customer_code;
        }

        public void setCustomer_code(String customer_code) {
            this.customer_code = customer_code;
        }

        public String getStore_code() {
            return store_code;
        }

        public void setStore_code(String store_code) {
            this.store_code = store_code;
        }

        public String getCrew_code() {
            return crew_code;
        }

        public void setCrew_code(String crew_code) {
            this.crew_code = crew_code;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getWorker_type() {
            return worker_type;
        }

        public void setWorker_type(String worker_type) {
            this.worker_type = worker_type;
        }

        public String getCrew_start_date() {
            return crew_start_date;
        }

        public void setCrew_start_date(String crew_start_date) {
            this.crew_start_date = crew_start_date;
        }

        public String getWorker_start_date() {
            return worker_start_date;
        }

        public void setWorker_start_date(String worker_start_date) {
            this.worker_start_date = worker_start_date;
        }

        public String getWorker_end_date() {
            return worker_end_date;
        }

        public void setWorker_end_date(String worker_end_date) {
            this.worker_end_date = worker_end_date;
        }

        public String getCrew_end_date() {
            return crew_end_date;
        }

        public void setCrew_end_date(String crew_end_date) {
            this.crew_end_date = crew_end_date;
        }

        public String getCustomer_end_date() {
            return customer_end_date;
        }

        public void setCustomer_end_date(String customer_end_date) {
            this.customer_end_date = customer_end_date;
        }

        public String getDisagree_reason() {
            return disagree_reason;
        }

        public void setDisagree_reason(String disagree_reason) {
            this.disagree_reason = disagree_reason;
        }

        public String getAssess() {
            return assess;
        }

        public void setAssess(String assess) {
            this.assess = assess;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }

        public String getEvaluated_date() {
            return evaluated_date;
        }

        public void setEvaluated_date(String evaluated_date) {
            this.evaluated_date = evaluated_date;
        }

        public String getWorker_name() {
            return worker_name;
        }

        public void setWorker_name(String worker_name) {
            this.worker_name = worker_name;
        }

        public String getWorker_code() {
            return worker_code;
        }

        public void setWorker_code(String worker_code) {
            this.worker_code = worker_code;
        }

        public String getWorker_source() {
            return worker_source;
        }

        public void setWorker_source(String worker_source) {
            this.worker_source = worker_source;
        }

        public String getWorking_action_role() {
            return working_action_role;
        }

        public void setWorking_action_role(String working_action_role) {
            this.working_action_role = working_action_role;
        }

        public String getNeed_crew_check() {
            return need_crew_check;
        }

        public void setNeed_crew_check(String need_crew_check) {
            this.need_crew_check = need_crew_check;
        }

        public String getNeed_customer_check() {
            return need_customer_check;
        }

        public void setNeed_customer_check(String need_customer_check) {
            this.need_customer_check = need_customer_check;
        }

        public List<LogsBean> getLogs() {
            return logs;
        }

        public void setLogs(List<LogsBean> logs) {
            this.logs = logs;
        }

        public static class LogsBean {
            private String title;
            private String type;
            private String reason;
            private String createtime;
            private String customer_name;
            private String telephone;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String reason) {
                this.reason = reason;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getCustomer_name() {
                return customer_name;
            }

            public void setCustomer_name(String customer_name) {
                this.customer_name = customer_name;
            }

            public String getTelephone() {
                return telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }
        }
    }
}
