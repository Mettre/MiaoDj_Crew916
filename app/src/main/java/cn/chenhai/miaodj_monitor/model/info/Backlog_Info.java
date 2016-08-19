package cn.chenhai.miaodj_monitor.model.info;

/**
 * Created by ChenHai--霜华 on 2016/6/23. 15:31
 * 邮箱：248866527@qq.com
 */
public class Backlog_Info {
    private boolean isNew;
    private String messageTitle;
    private String messageDetail;
    private String messageTime;

    private String project_code;
    private String node_id;
    private String redirect_url;
    private String type;

    public boolean getIsNew() {
        return isNew;
    }
    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public String getMessageTitle() {
        return messageTitle;
    }
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageDetail() {
        return messageDetail;
    }
    public void setMessageDetail(String messageDetail) {
        this.messageDetail = messageDetail;
    }

    public String getMessageTime() {
        return messageTime;
    }
    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
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
}
