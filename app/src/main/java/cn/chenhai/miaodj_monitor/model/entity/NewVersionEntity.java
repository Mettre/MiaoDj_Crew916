package cn.chenhai.miaodj_monitor.model.entity;

/**
 * Created by ChenHai--霜华 on 2016/7/23. 10:53
 * 邮箱：248866527@qq.com
 */
public class NewVersionEntity {

    /**
     * keep_msg :
     * version : {"version":"1.0.1","description":"初始版本","enforceUpdate":"N","path":"http://api.miaodj.cn/Download/"}
     */

    private String keep_msg;
    /**
     * version : 1.0.1
     * description : 初始版本
     * enforceUpdate : N
     * path : http://api.miaodj.cn/Download/
     */

    private VersionBean version;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public VersionBean getVersion() {
        return version;
    }

    public void setVersion(VersionBean version) {
        this.version = version;
    }

    public static class VersionBean {
        private String version;
        private String description;
        private String enforceUpdate;
        private String path;



        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getEnforceUpdate() {
            return enforceUpdate;
        }

        public void setEnforceUpdate(String enforceUpdate) {
            this.enforceUpdate = enforceUpdate;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
