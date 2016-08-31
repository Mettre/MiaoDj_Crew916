package cn.chenhai.miaodj_monitor.model.entity;

import java.io.Serializable;
import java.util.List;

/**
 * <付款信息实体类>
 * <功能详细描述>
 *
 * @author Allen
 * @version [版本号, 2016/8/30 17:18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BargainPayEntity implements Serializable {


    /**
     * keep_msg :
     * bargain_pay : [{"title":"预付款","pay_num":1,"percent":0.3,"status":8,"pay":"26248.50"},{"title":"第二次付款","pay_num":2,"percent":0.4,"status":8,"pay":"15749.10"},{"title":"第三次付款","pay_num":3,"percent":0.25,"status":8,"pay":"7874.55"},{"title":"尾款","pay_num":4,"percent":0.05,"status":8,"pay":"2624.85"}]
     */

    private String keep_msg;
    /**
     * title : 预付款
     * pay_num : 1
     * percent : 0.3
     * status : 8
     * pay : 26248.50
     */

    private List<BargainPayBean> bargain_pay;

    public String getKeep_msg() {
        return keep_msg;
    }

    public void setKeep_msg(String keep_msg) {
        this.keep_msg = keep_msg;
    }

    public List<BargainPayBean> getBargain_pay() {
        return bargain_pay;
    }

    public void setBargain_pay(List<BargainPayBean> bargain_pay) {
        this.bargain_pay = bargain_pay;
    }

    public static class BargainPayBean {
        private String title;
        private int pay_num;
        private double percent;
        private int status;
        private String pay;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getPay_num() {
            return pay_num;
        }

        public void setPay_num(int pay_num) {
            this.pay_num = pay_num;
        }

        public double getPercent() {
            return percent;
        }

        public void setPercent(double percent) {
            this.percent = percent;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getPay() {
            return pay;
        }

        public void setPay(String pay) {
            this.pay = pay;
        }
    }
}
