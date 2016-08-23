package cn.chenhai.miaodj_monitor.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.entity.PointProgressDetailEntity;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by ChenHai--霜华 on 2016/7/8. 11:52
 * 邮箱：248866527@qq.com
 */
public class ItemPointAdapter extends RecyclerView.Adapter<ItemPointAdapter.MyViewHolder> {
    private List<PointProgressDetailEntity.NodeBean.LogsBean> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private SupportActivity mActivity;
    private OnItemClickListener mClickListener;
    private OnItemClickListener mBtnClickListener;

    public ItemPointAdapter(Context context) {
        mActivity = (SupportActivity)context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void refreshDatas(List<PointProgressDetailEntity.NodeBean.LogsBean> items) {
        mdataList.clear();
        mdataList.addAll(items);
    }
    public void addDatas(List<PointProgressDetailEntity.NodeBean.LogsBean> items) {
        //mdataList.clear();
        mdataList.addAll(items);
    }
    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_detail_point_progress_recycler, parent, false);

        final MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        PointProgressDetailEntity.NodeBean.LogsBean item = mdataList.get(position);
        if (item == null) return;

        if(item.getType().equals("1")){
            holder.mItemIvWarn.setVisibility(View.GONE);
            holder.mItemTvContentDisAgree.setVisibility(View.GONE);
            holder.mItemTvContentCommon.setVisibility(View.VISIBLE);
            holder.mItemTvContentCommon.setText(item.getTitle());
        } else if (item.getType().equals("2")){
            holder.mItemIvWarn.setVisibility(View.VISIBLE);
            holder.mItemTvContentDisAgree.setVisibility(View.VISIBLE);
            holder.mItemTvContentCommon.setVisibility(View.GONE);
            holder.mItemTvContentDisAgree.setText(item.getTitle());

//            holder.mLogsBean = new PointProgressDetailEntity.NodeBean.LogsBean();
//            holder.mLogsBean.setCreatetime(item.getLogs().getCreatetime());
//            holder.mLogsBean.setCustomer_name(item.getLogs().getCustomer_name());
//            holder.mLogsBean.setReason(item.getLogs().getReason());
//            holder.mLogsBean.setTelephone(item.getLogs().getTelephone());
//            holder.mLogsBean.setTitle(item.getLogs().getTitle());
//            holder.mLogsBean.setType(item.getLogs().getType());
        }

        holder.mItemTvTime.setText(item.getCreatetime());

        holder.mItemTvContentDisAgree.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
        holder.mItemTvContentDisAgree.getPaint().setAntiAlias(true);//抗锯齿
        holder.mItemTvContentDisAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mBtnClickListener != null) {
                    mBtnClickListener.onItemClick(position, v);
                }
            }
        });

//            //添加下划线
//            holder.mWorkerTvName.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
//            holder.mWorkerTvName.getPaint().setAntiAlias(true);//抗锯齿
//            //或者这样写
//            //holder.mWorkerTvName.setText(Html.fromHtml("<u>"+item.getWorkerName()+"</u>"));

    }

    @Override
    public int getItemCount() {
        return mdataList.size();
    }

    public PointProgressDetailEntity.NodeBean.LogsBean getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView mItemIvWarn;
        private TextView mItemTvContentDisAgree;
        private TextView mItemTvContentCommon;
        private TextView mItemTvTime;
        //private PointProgressDetailEntity.NodeBean.LogsBean mLogsBean;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

            mItemIvWarn = (ImageView) itemView.findViewById(R.id.item_iv_warn);
            mItemTvContentDisAgree = (TextView) itemView.findViewById(R.id.item_tv_content_disAgree);
            mItemTvContentCommon = (TextView) itemView.findViewById(R.id.item_tv_content_common);
            mItemTvTime = (TextView) itemView.findViewById(R.id.item_tv_Time);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setOnItemBtnClickListener(OnItemClickListener itemClickListener) {
        this.mBtnClickListener = itemClickListener;
    }
}
