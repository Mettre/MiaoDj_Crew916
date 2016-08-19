package cn.chenhai.miaodj_monitor.model.info;

/**
 * Created by ChenHai--霜华 on 2016/6/20. 14:36
 * 邮箱：248866527@qq.com
 */
public class WorkerCheckInfo {
    private String ifAddedWorker;
    private String ifAgree;
    private String img_portraitPath;
    private String workerName;
    private String workerCode;

    private String workerNowTypes;

    public String getIfAddedWorker() {
        return ifAddedWorker;
    }
    public void setIfAddedWorker(String ifAddedWorker) {
        this.ifAddedWorker = ifAddedWorker;
    }

    public String getIfAgree() {
        return ifAgree;
    }
    public void setIfAgree(String ifAgree) {
        this.ifAgree = ifAgree;
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

    public String getWorkerCode() {
        return workerCode;
    }
    public void setWorkerCode(String workerCode) {
        this.workerCode = workerCode;
    }

    public String getWorkerNowTypes() {
        return workerNowTypes;
    }
    public void setWorkerNowTypes(String workerNowTypes) {
        this.workerNowTypes = workerNowTypes;
    }
}
