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
     * message : [{"id":"433","user_code":"WOR201608000001","user_type":"worker","title":"停工通知","content":"幸福10幢102室项目因业主未及时付清款项已停工！","redirect_url":"","createtime":"2016-09-10 15:34:48","type":"28"},{"id":"434","user_code":"WOR201608000001","user_type":"worker","title":"停工通知","content":"幸福10幢102室项目因业主未及时付清款项已停工！","redirect_url":"","createtime":"2016-09-10 15:34:48","type":"28"},{"id":"435","user_code":"WOR201608000001","user_type":"worker","title":"停工通知","content":"幸福10幢102室项目因业主未及时付清款项已停工！","redirect_url":"","createtime":"2016-09-10 15:34:48","type":"28"},{"id":"436","user_code":"WOR201608000001","user_type":"worker","title":"停工通知","content":"幸福10幢102室项目因业主未及时付清款项已停工！","redirect_url":"","createtime":"2016-09-10 15:34:48","type":"28"},{"id":"437","user_code":"WOR201608000001","user_type":"worker","title":"停工通知","content":"幸福10幢102室项目因业主未及时付清款项已停工！","redirect_url":"","createtime":"2016-09-10 15:34:48","type":"28"}]
     */

    private String keep_msg;
    /**
     * id : 433
     * user_code : WOR201608000001
     * user_type : worker
     * title : 停工通知
     * content : 幸福10幢102室项目因业主未及时付清款项已停工！
     * redirect_url :
     * createtime : 2016-09-10 15:34:48
     * type : 28
     */

    private List<MessageBean> message;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public List<MessageBean> getMessage() {
        return message;
    }

    public void setMessage(List<MessageBean> message) {
        this.message = message;
    }

    public static class MessageBean {
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
