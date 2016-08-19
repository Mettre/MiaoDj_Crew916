package cn.chenhai.miaodj_monitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.info.HomePageInfo;

/**
 * Home里的子Fragment  Adapter
 * Created by ChenHai--霜华 on 2016/5/28. 3:21
 * 邮箱：248866527@qq.com
 */
public class HomePagerAdapter extends RecyclerView.Adapter<HomePagerAdapter.MyViewHolder> {
    private List<HomePageInfo> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private int mFrom;
    private OnItemClickListener mClickListener;
    private OnItemClickListener mBtnClickListener;

    public HomePagerAdapter(Context context , int temFrom) {
        this.mInflater = LayoutInflater.from(context);
        this.mFrom = temFrom;
    }

    public void setDatas(List<HomePageInfo> items) {
        mdataList.clear();
        mdataList.addAll(items);
    }
    public void loadToAddDatas(List<HomePageInfo> items) {
        mdataList.addAll(items);
    }
    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (mFrom == 0) {
            view = mInflater.inflate(R.layout.item_home_cardview_no, parent, false);
        } else if (mFrom == 1) {
            view = mInflater.inflate(R.layout.item_home_cardview, parent, false);
        } else if (mFrom == 2) {
            view = mInflater.inflate(R.layout.item_home_cardview_ok, parent, false);
        } else {
            view = mInflater.inflate(R.layout.item_home_cardview_refuse, parent, false);
        }

        final MyViewHolder holder = new MyViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        HomePageInfo item = mdataList.get(position);
        if (item == null) return;

        holder.tvTitleName.setText(item.getItemName());
        holder.tvOwnerName.setText(item.getOwnerName());
        holder.tvProgress.setText(item.getWorkProgress());
        if(item.getWorkProgress().equals("施工节点业主不确认")){
            holder.tvProgress.setTextColor(0xffff6600);
        }

        holder.mStatus = item.getStatus();
        if(holder.mStatus.equals("0") || holder.mStatus.equals("3")){
            holder.ll_daily_click.setVisibility(View.INVISIBLE);
        }else {
            holder.ll_daily_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (mBtnClickListener != null) {
                        mBtnClickListener.onItemClick(position, v);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mdataList.size();
    }
    public HomePageInfo getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitleName ,tvOwnerName,tvProgress;
        private FrameLayout ll_daily_click;
        private String mStatus;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);
            tvTitleName = (TextView) itemView.findViewById(R.id.home_title);
            tvOwnerName = (TextView) itemView.findViewById(R.id.home_name);
            tvProgress = (TextView) itemView.findViewById(R.id.home_progress);
            ll_daily_click = (FrameLayout) itemView.findViewById(R.id.home_layout_daily_click);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setOnItemBtnClickListener(OnItemClickListener itemClickListener) {
        this.mBtnClickListener = itemClickListener;
    }
}
