package cn.chenhai.miaodj_monitor.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/8. 08:37
 * 邮箱：248866527@qq.com
 */
public class BackLogNewMsgEntity implements Serializable {

    /**
     * keep_msg :
     * single_message : []
     * * last_single_message_id : 74
     * common_message : [{"id":"45","user_code":"all","user_type":"customer","title":"施工员确认人选通知","content":"施工员已指派工人，您可以在施工工人列表中查看详情！","redirect_url":"","createtime":"2016-06-28 11:55:28","type":"4"},{"id":"43","user_code":"all","user_type":"customer","title":"施工员确认人选通知","content":"施工员已指派工人，您可以在施工工人列表中查看详情！","redirect_url":"","createtime":"2016-06-28 11:54:52","type":"4"}]
     * last_common_message_id : 45
     */

    private String keep_msg;
    private String last_single_message_id;
    private String last_common_message_id;
    /**
     * id : 74
     * user_code : CRE201606000001
     * user_type : crew
     * title : 业主反馈施工进场申请结果提醒（同意）
     * content : 业主已同意您的施工进场申请，请联系好相关人员按期进场！
     * redirect_url :
     * createtime : 2016-06-29 09:46:56
     * type : 15
     */

    private List<SingleMessageBean> single_message;
    /**
     * id : 45
     * user_code : all
     * user_type : customer
     * title : 施工员确认人选通知
     * content : 施工员已指派工人，您可以在施工工人列表中查看详情！
     * redirect_url :
     * createtime : 2016-06-28 11:55:28
     * type : 4
     */

    private List<CommonMessageBean> common_message;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public String getLast_single_message_id() {
        return last_single_message_id;
    }

    public void setLast_single_message_id(String last_single_message_id) {
        this.last_single_message_id = last_single_message_id;
    }

    public String getLast_common_message_id() {
        return last_common_message_id;
    }

    public void setLast_common_message_id(String last_common_message_id) {
        this.last_common_message_id = last_common_message_id;
    }

    public List<SingleMessageBean> getSingle_message() {
        return single_message;
    }

    public void setSingle_message(List<SingleMessageBean> single_message) {
        this.single_message = single_message;
    }

    public List<CommonMessageBean> getCommon_message() {
        return common_message;
    }

    public void setCommon_message(List<CommonMessageBean> common_message) {
        this.common_message = common_message;
    }

    public static class SingleMessageBean implements Serializable{
        private String id;
        private String user_code;
        private String user_type;
        private String title;
        private String content;
        private String redirect_url;
        private String createtime;
        private String type;

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

        public String getRedirect_url() {
            return redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class CommonMessageBean implements Serializable{
        private String id;
        private String user_code;
        private String user_type;
        private String title;
        private String content;
        private String redirect_url;
        private String createtime;
        private String type;

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

        public String getRedirect_url() {
            return redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
