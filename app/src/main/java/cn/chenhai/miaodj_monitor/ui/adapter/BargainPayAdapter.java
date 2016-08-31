package cn.chenhai.miaodj_monitor.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.bean.PayInfoBean;

/**
 * <付款信息适配器>
 * <功能详细描述>
 *
 * @author Allen
 * @version [版本号, 2016/8/31 09:36]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BargainPayAdapter extends BaseSectionQuickAdapter<PayInfoBean> {

    private Context mContext;

    public BargainPayAdapter(Context context, List<PayInfoBean> data) {
        super(R.layout.item_detail_bargain_pay_child, R.layout.item_detail_bargain_pay_group, data);
        this.mContext = context;
    }

    @Override
    protected void convertHead(BaseViewHolder baseViewHolder, PayInfoBean payInfoBean) {
        baseViewHolder.setText(R.id.idbpg_titleTv, payInfoBean.header);

    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, PayInfoBean payInfoBean) {
        double percent = payInfoBean.t.getPercent() * 100;

        TextView mMoney = baseViewHolder.getView(R.id.idbpc_moneyTv);

        baseViewHolder.setText(R.id.idbpc_numTv, payInfoBean.t.getPay_num() + "")
                .setText(R.id.idbpc_titleTv, payInfoBean.t.getTitle())
                .setText(R.id.idbpc_percentTv, percent + "%")
                .setText(R.id.idbpc_moneyTv, payInfoBean.t.getPay());

        if (payInfoBean.t.getStatus() == 1)//未支付
        {
            baseViewHolder.setBackgroundRes(R.id.idbpc_numTv, R.drawable.ic_point_nostart)
                    .setTextColor(R.id.idbpc_moneyTv, R.color.text_color_black_3)
                    .setBackgroundRes(R.id.idbpc_status, R.drawable.btn_effect_bg_gray)
                    .setText(R.id.idbpc_status, "未支付");
        } else if (payInfoBean.t.getStatus() == 8) //已支付
        {
            baseViewHolder.setBackgroundRes(R.id.idbpc_numTv, R.drawable.ic_point_ok)
                    .setTextColor(R.id.idbpc_moneyTv, R.color.red)
                    .setBackgroundRes(R.id.idbpc_status, R.drawable.btn_effect_bg_green_pop)
                    .setText(R.id.idbpc_status, "已支付");
            mMoney.setTextColor(mContext.getResources().getColor(R.color.color_green_ok));

        }
    }

    public void updateDatas(List<PayInfoBean> commonDataResults) {
        this.mData = commonDataResults;
        notifyDataSetChanged();
    }
}
