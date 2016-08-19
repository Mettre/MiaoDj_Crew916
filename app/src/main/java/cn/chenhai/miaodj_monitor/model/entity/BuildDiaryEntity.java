package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/7. 11:43
 * 邮箱：248866527@qq.com
 */
public class BuildDiaryEntity {

    /**
     * keep_msg :
     * drawings : []
     * */

    private String keep_msg;
    /**
     * id : 0
     * project_code : CP320501201606270003
     * crew_code : CRE201606000001
     * info :
     * after_start : 11
     * createtime : 2016-07-07
     * crew_name : 永晓
     * crew_headimg : /Uploads/images/201606/57738afe4a649.png
     * can_edit : Y
     */

    private List<DrawingsBean> drawings;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public List<DrawingsBean> getDrawings() {
        return drawings;
    }

    public void setDrawings(List<DrawingsBean> drawings) {
        this.drawings = drawings;
    }

    public static class DrawingsBean {
        private int id;
        private String project_code;
        private String crew_code;
        private String info;
        private int after_start;
        private String createtime;
        private String crew_name;
        private String crew_headimg;
        private String can_edit;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProject_code() {
            return project_code;
        }

        public void setProject_code(String project_code) {
            this.project_code = project_code;
        }

        public String getCrew_code() {
            return crew_code;
        }

        public void setCrew_code(String crew_code) {
            this.crew_code = crew_code;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getAfter_start() {
            return after_start;
        }

        public void setAfter_start(int after_start) {
            this.after_start = after_start;
        }

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public String getCrew_name() {
            return crew_name;
        }

        public void setCrew_name(String crew_name) {
            this.crew_name = crew_name;
        }

        public String getCrew_headimg() {
            return crew_headimg;
        }

        public void setCrew_headimg(String crew_headimg) {
            this.crew_headimg = crew_headimg;
        }

        public String getCan_edit() {
            return can_edit;
        }

        public void setCan_edit(String can_edit) {
            this.can_edit = can_edit;
        }
    }
}
