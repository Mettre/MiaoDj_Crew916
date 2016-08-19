package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/7. 14:21
 * 邮箱：248866527@qq.com
 */
public class BuildPhotoEntity {

    /**
     * keep_msg :
     * drawings : []
     * */

    private String keep_msg;
    /**
     * id : 0
     * project_code : CP320501201606270003
     * worker_code :
     * pic_one :
     * pic_two :
     * pic_three :
     * pic_four :
     * pic_five :
     * after_start : 11
     * createtime : 2016-07-07
     * worker_name :
     * worker_headimg :
     * worker_type_name :
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
        private String worker_code;
        private String pic_one;
        private String pic_two;
        private String pic_three;
        private String pic_four;
        private String pic_five;
        private String thumb_pic_one;
        private String thumb_pic_two;
        private String thumb_pic_three;
        private String thumb_pic_four;
        private String thumb_pic_five;

        private int after_start;
        private String createtime;
        private String worker_name;
        private String worker_headimg;
        private String worker_type_name;
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

        public String getWorker_code() {
            return worker_code;
        }

        public void setWorker_code(String worker_code) {
            this.worker_code = worker_code;
        }

        public String getPic_one() {
            return pic_one;
        }

        public void setPic_one(String pic_one) {
            this.pic_one = pic_one;
        }

        public String getPic_two() {
            return pic_two;
        }

        public void setPic_two(String pic_two) {
            this.pic_two = pic_two;
        }

        public String getPic_three() {
            return pic_three;
        }

        public void setPic_three(String pic_three) {
            this.pic_three = pic_three;
        }

        public String getPic_four() {
            return pic_four;
        }

        public void setPic_four(String pic_four) {
            this.pic_four = pic_four;
        }

        public String getPic_five() {
            return pic_five;
        }

        public void setPic_five(String pic_five) {
            this.pic_five = pic_five;
        }

        public String getThumb_pic_one() {
            return thumb_pic_one;
        }

        public void setThumb_pic_one(String thumb_pic_one) {
            this.thumb_pic_one = thumb_pic_one;
        }

        public String getThumb_pic_two() {
            return thumb_pic_two;
        }

        public void setThumb_pic_two(String thumb_pic_two) {
            this.thumb_pic_two = thumb_pic_two;
        }

        public String getThumb_pic_three() {
            return thumb_pic_three;
        }

        public void setThumb_pic_three(String thumb_pic_three) {
            this.thumb_pic_three = thumb_pic_three;
        }

        public String getThumb_pic_four() {
            return thumb_pic_four;
        }

        public void setThumb_pic_four(String thumb_pic_four) {
            this.thumb_pic_four = thumb_pic_four;
        }

        public String getThumb_pic_five() {
            return thumb_pic_five;
        }

        public void setThumb_pic_five(String thumb_pic_five) {
            this.thumb_pic_five = thumb_pic_five;
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

        public String getWorker_name() {
            return worker_name;
        }

        public void setWorker_name(String worker_name) {
            this.worker_name = worker_name;
        }

        public String getWorker_headimg() {
            return worker_headimg;
        }

        public void setWorker_headimg(String worker_headimg) {
            this.worker_headimg = worker_headimg;
        }

        public String getWorker_type_name() {
            return worker_type_name;
        }

        public void setWorker_type_name(String worker_type_name) {
            this.worker_type_name = worker_type_name;
        }

        public String getCan_edit() {
            return can_edit;
        }

        public void setCan_edit(String can_edit) {
            this.can_edit = can_edit;
        }
    }
}
