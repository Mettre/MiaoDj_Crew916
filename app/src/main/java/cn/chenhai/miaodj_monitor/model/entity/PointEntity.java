package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/4. 08:34
 * 邮箱：248866527@qq.com
 */
public class PointEntity {

    /**
     * show_msg : Y
     * show_id : 1
     * show_type : 成保员
     * nodes : []
     * start_date : 2016-06-23
     * start_days : 12
     * before_start_days : -12
     */

    private String show_msg;
    private String show_id;
    private String show_type;
    private String start_date;
    private int start_days;
    private int before_start_days;
    /**
     * id : 350
     * node_id : 1
     * title : 进场
     * project_code : CP320501201606270003
     * expect_start_date : null
     * expect_end_date : null
     * actual_start_date : null
     * actual_end_date : null
     * customer_code : CF201606000008
     * store_code : S320501
     * crew_code : CRE201606000001
     * status : 1
     * worker_type : 114
     * crew_start_date : null
     * worker_start_date : null
     * worker_end_date : null
     * crew_end_date : null
     * customer_end_date : null
     * disagree_reason : null
     * assess : null
     * score : null
     * evaluated_date : null
     * need_pay : N
     */

    private List<NodesBean> nodes;

    public String getShow_msg() {
        return show_msg;
    }
    public void setShow_msg(String show_msg) {
        this.show_msg = show_msg;
    }

    public String getShow_id() {
        return show_id;
    }
    public void setShow_id(String show_id) {
        this.show_id = show_id;
    }

    public String getShow_type() {
        return show_type;
    }
    public void setShow_type(String show_type) {
        this.show_type = show_type;
    }

    public String getStart_date() {
        return start_date;
    }
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public int getStart_days() {
        return start_days;
    }
    public void setStart_days(int start_days) {
        this.start_days = start_days;
    }

    public int getBefore_start_days() {
        return before_start_days;
    }
    public void setBefore_start_days(int before_start_days) {
        this.before_start_days = before_start_days;
    }

    public List<NodesBean> getNodes() {
        return nodes;
    }
    public void setNodes(List<NodesBean> nodes) {
        this.nodes = nodes;
    }

    public static class NodesBean {
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
        private String need_pay;

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

        public String getNeed_pay() {
            return need_pay;
        }

        public void setNeed_pay(String need_pay) {
            this.need_pay = need_pay;
        }
    }
}
