package cn.chenhai.miaodj_monitor.model.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

import cn.chenhai.miaodj_monitor.model.entity.BargainPayEntity;

/**
 * <付款信息实体类>
 * <功能详细描述>
 *
 * @author Allen
 * @version [版本号, 2016/8/31 10:00]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PayInfoBean extends SectionEntity<BargainPayEntity.BargainPayBean> {
    public PayInfoBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public PayInfoBean(BargainPayEntity.BargainPayBean bargainBean) {
        super(bargainBean);
    }
}
