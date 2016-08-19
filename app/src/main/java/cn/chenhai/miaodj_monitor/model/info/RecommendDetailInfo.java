package cn.chenhai.miaodj_monitor.model.info;

/**
 * Created by ChenHai--霜华 on 2016/6/28. 14:32
 * 邮箱：248866527@qq.com
 */
public class RecommendDetailInfo {

    private String orderNum;
    private String projectType;
    private String buildStartDate;
    private String buildEndDate;
    private String moneyCount;
    private String giveTime;

    public String getOrderNum() {
        return orderNum;
    }
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getProjectType() {
        return projectType;
    }
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getBuildStartDate() {
        return buildStartDate;
    }
    public void setBuildStartDate(String buildStartDate) {
        this.buildStartDate = buildStartDate;
    }

    public String getBuildEndDate() {
        return buildEndDate;
    }
    public void setBuildEndDate(String buildEndDate) {
        this.buildEndDate = buildEndDate;
    }

    public String getMoneyCount() {
        return moneyCount;
    }
    public void setMoneyCount(String moneyCount) {
        this.moneyCount = moneyCount;
    }

    public String getGiveTime() {
        return giveTime;
    }
    public void setGiveTime(String giveTime) {
        this.giveTime = giveTime;
    }
}
