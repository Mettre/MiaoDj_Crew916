package cn.chenhai.miaodj_monitor.model.info;

/**
 * Created by ChenHai--霜华 on 2016/6/13. 21:58
 * 邮箱：248866527@qq.com
 */
public class DetailPointInfo {
    private String pointID;
    private String itemIndex;
    private String itemName;
    private String btnStatus;
    private String startDate;
    private String evaluate;
//    private boolean isArrow;
//    private boolean isShowLineUp;
//    private boolean isShowLineDown;
//    private boolean isShowLineOut;

    public String getPointID() {
        return pointID;
    }
    public void setPointID(String pointID) {
        this.pointID = pointID;
    }

    public String getEvaluate() {
        return evaluate;
    }
    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }


    public String getItemIndex() {
        return itemIndex;
    }
    public void setItemIndex(String itemIndex) {
        this.itemIndex = itemIndex;
    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getBtnStatus() {
        return btnStatus;
    }
    public void setBtnStatus(String btnStatus) {
        this.btnStatus = btnStatus;
    }

    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
}
