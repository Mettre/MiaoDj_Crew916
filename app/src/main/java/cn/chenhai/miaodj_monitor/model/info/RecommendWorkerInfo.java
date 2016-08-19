package cn.chenhai.miaodj_monitor.model.info;

/**
 * Created by ChenHai--霜华 on 2016/6/28. 10:02
 * 邮箱：248866527@qq.com
 */
public class RecommendWorkerInfo {
    private boolean ifRegister;

    private String img_portraitPath;
    private String workerName;
    private String workerPhone;
    private String money;

    private String workerID;

    public boolean isIfRegister() {
        return ifRegister;
    }
    public void setIfRegister(boolean ifRegister) {
        this.ifRegister = ifRegister;
    }

    public String getImg_portraitPath() {
        return img_portraitPath;
    }
    public void setImg_portraitPath(String img_portraitPath) {
        this.img_portraitPath = img_portraitPath;
    }

    public String getWorkerName() {
        return workerName;
    }
    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getWorkerPhone() {
        return workerPhone;
    }
    public void setWorkerPhone(String workerPhone) {
        this.workerPhone = workerPhone;
    }

    public String getMoney() {
        return money;
    }
    public void setMoney(String money) {
        this.money = money;
    }

    public String getWorkerID() {
        return workerID;
    }
    public void setWorkerID(String workerID) {
        this.workerID = workerID;
    }
}
