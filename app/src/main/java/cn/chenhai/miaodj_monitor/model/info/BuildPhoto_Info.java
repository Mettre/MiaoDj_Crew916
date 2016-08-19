package cn.chenhai.miaodj_monitor.model.info;

import java.util.List;

import cn.chenhai.miaodj_monitor.views.preview.ImageInfo;

/**
 * Created by ChenHai--霜华 on 2016/6/24. 18:16
 * 邮箱：248866527@qq.com
 */
public class BuildPhoto_Info {
    private String img_portraitPath;
    private String worker_name;
    private String worker_type;
    private String dayNum;
    private String date;

    private String photo_path1;
    private String photo_path2;
    private String photo_path3;
    private String photo_path4;
    private String photo_path5;


    private List<ImageInfo> imageurls;

    public List<ImageInfo> getImageurls() {
        return imageurls;
    }
    public void setImageurls(List<ImageInfo> imageurls) {
        this.imageurls = imageurls;
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

    public String getPhoto_path1() {
        return photo_path1;
    }
    public void setPhoto_path1(String photo_path1) {
        this.photo_path1 = photo_path1;
    }

    public String getPhoto_path2() {
        return photo_path2;
    }
    public void setPhoto_path2(String photo_path2) {
        this.photo_path2 = photo_path2;
    }

    public String getPhoto_path3() {
        return photo_path3;
    }
    public void setPhoto_path3(String photo_path3) {
        this.photo_path3 = photo_path3;
    }

    public String getPhoto_path4() {
        return photo_path4;
    }
    public void setPhoto_path4(String photo_path4) {
        this.photo_path4 = photo_path4;
    }

    public String getPhoto_path5() {
        return photo_path5;
    }
    public void setPhoto_path5(String photo_path5) {
        this.photo_path5 = photo_path5;
    }
}
