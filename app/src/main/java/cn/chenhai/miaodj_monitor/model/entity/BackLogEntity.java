package cn.chenhai.miaodj_monitor.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/8. 08:36
 * 邮箱：248866527@qq.com
 */
public class BackLogEntity implements Serializable {

    /**
     * keep_msg :
     * todo : []
     * */

    private String keep_msg;
    /**
     * id : 111
     * user_code : CRE201606000001
     * user_type : crew
     * title : 工人拒绝被添加为项目工人的结果提醒
     * content : 铂悅府5幢201室项目放线员指派已被拒绝，请及时重新指派！
     * project_code : CP320501201606270003
     * node_id : 0
     * redirect_url :
     * type : 16
     * createtime : 2016-06-30 15:10:09
     * status : 1
     */

    private List<TodoBean> todo;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public List<TodoBean> getTodo() {
        return todo;
    }

    public void setTodo(List<TodoBean> todo) {
        this.todo = todo;
    }

    public static class TodoBean implements Serializable{
        private String id;
        private String user_code;
        private String user_type;
        private String title;
        private String content;
        private String project_code;
        private String node_id;
        private String redirect_url;
        private String type;
        private String createtime;
        private String status;

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

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getProject_code() {
            return project_code;
        }

        public void setProject_code(String project_code) {
            this.project_code = project_code;
        }

        public String getNode_id() {
            return node_id;
        }

        public void setNode_id(String node_id) {
            this.node_id = node_id;
        }

        public String getRedirect_url() {
            return redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
