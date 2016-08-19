package cn.chenhai.miaodj_monitor.model.info;

/**
 * Created by ChenHai--霜华 on 2016/6/24. 15:36
 * 邮箱：248866527@qq.com
 */
public class BuildDiary_Info {

    private boolean ifContent;
    private boolean ifEdit;
    private String img_portraitPath;
    private String worker_name;
    private String worker_type;
    private String diary_content;
    private String dayNum;
    private String date;


    public boolean isIfContent() {
        return ifContent;
    }
    public void setIfContent(boolean ifContent) {
        this.ifContent = ifContent;
    }

    public boolean isIfEdit() {
        return ifEdit;
    }
    public void setIfEdit(boolean ifEdit) {
        this.ifEdit = ifEdit;
    }

    public String getImg_portraitPath() {
        return img_portraitPath;
    }
    public void setImg_portraitPath(String img_portraitPath) {
        this.img_portraitPath = img_portraitPath;
    }

    public String getWorker_name() {
        return worker_name;
    }
    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getWorker_type() {
        return worker_type;
    }
    public void setWorker_type(String worker_type) {
        this.worker_type = worker_type;
    }

    public String getDiary_content() {
        return diary_content;
    }
    public void setDiary_content(String diary_content) {
        this.diary_content = diary_content;
    }

    public String getDayNum() {
        return dayNum;
    }
    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

}
