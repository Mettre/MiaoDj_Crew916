package cn.chenhai.miaodj_monitor.model.entity;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/6. 18:37
 * 邮箱：248866527@qq.com
 */
public class WorkerTypesEntity {

    /**
     * keep_msg :
     * worktype : []
     * */

    private String keep_msg;
    /**
     * id : 100
     * title : 放线员
     * source : 第三方
     * ico : /Public/Img/worker_def_@3x.png
     */

    private List<WorktypeBean> worktype;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public List<WorktypeBean> getWorktype() {
        return worktype;
    }

    public void setWorktype(List<WorktypeBean> worktype) {
        this.worktype = worktype;
    }

    public static class WorktypeBean {
        private String id;
        private String title;
        private String source;
        private String ico;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getIco() {
            return ico;
        }

        public void setIco(String ico) {
            this.ico = ico;
        }
    }
}
