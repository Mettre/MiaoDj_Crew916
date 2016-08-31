package cn.chenhai.miaodj_monitor.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.entity.BargainEntity;

/**
 * <合同信息>
 * <功能详细描述>
 *
 * @author Allen
 * @version [版本号, 2016/8/30 17:27]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class BargainAdapter extends BaseQuickAdapter<BargainEntity.BargainBean> {

    public BargainAdapter(List<BargainEntity.BargainBean> data) {
        super(R.layout.item_detail_bargain, data);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, BargainEntity.BargainBean project) {

        baseViewHolder.setText(R.id.idb_titleTv, "《" + project.getStreet() + project.getResidential() + project.getApartment() + "幢" + project.getRoom() + "室装修合同" + "》")
                .setText(R.id.idb_dataTv, project.getUpdatetime());


    }

    public void updateDatas(List<BargainEntity.BargainBean> commonDataResults) {
        this.mData = commonDataResults;
        notifyDataSetChanged();
    }
}
