package cn.chenhai.miaodj_monitor.model.info;

import java.util.List;

/**
 * Created by ChenHai--霜华 on 2016/7/8. 11:55
 * 邮箱：248866527@qq.com
 */
public class ItemPointInfo {

    /**
     * title : 业主验收不通过
     * type : 2
     * reason : 我不
     * createtime : 2016-06-28 23:01:13
     * customer_name : 小吴
     * telephone : 18151113752
     */

    private String title;
    private String type;
    private String reason;
    private String createtime;
    private String customer_name;
    private String telephone;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }


}
