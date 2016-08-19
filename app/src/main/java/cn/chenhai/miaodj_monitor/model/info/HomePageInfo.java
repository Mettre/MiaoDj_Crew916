package cn.chenhai.miaodj_monitor.model.info;

/**
 * Created by ChenHai--霜华 on 2016/5/28. 15:38
 * 邮箱：248866527@qq.com
 */
public class HomePageInfo {

    private String projectCode;

    private String itemName;

    private String ownerName;
    private String workProgress;
    private String status;

    public String getProjectCode() {
        return projectCode;
    }
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getWorkProgress() {
        return workProgress;
    }
    public void setWorkProgress(String workProgress) {
        this.workProgress = workProgress;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
