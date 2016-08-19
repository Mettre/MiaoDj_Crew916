package cn.chenhai.miaodj_monitor.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.info.Backlog_Info;

/**
 * Created by ChenHai--霜华 on 2016/6/23. 15:39
 * 邮箱：248866527@qq.com
 */
public class PersonalBacklogPagerAdapter extends RecyclerView.Adapter<PersonalBacklogPagerAdapter.MyViewHolder> {
    private List<Backlog_Info> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private int mFrom;
    private OnItemClickListener mClickListener;
    private OnItemClickListener mBtnClickListener;

    public PersonalBacklogPagerAdapter(Context context , int temFrom) {
        this.mInflater = LayoutInflater.from(context);
        this.mFrom = temFrom;
    }

    public void refreshDatas(List<Backlog_Info> items) {
        mdataList.clear();
        mdataList.addAll(items);
    }
    public void addDatas(List<Backlog_Info> items) {
        //mdataList.clear();
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
            view = mInflater.inflate(R.layout.item_viewpager_backlog1, parent, false);
        }  else {
            view = mInflater.inflate(R.layout.item_viewpager_backlog2, parent, false);
        }

        final MyViewHolder holder = new MyViewHolder(view);
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                if (mClickListener != null) {
//                    mClickListener.onItemClick(position, v);
//                }
//            }
//        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Backlog_Info item = mdataList.get(position);
        if (item == null) return;

        holder.mTvBacklogTitle.setText(item.getMessageTitle());
        holder.mTvBacklogContent.setText(item.getMessageDetail());
        holder.mTvBacklogTime.setText(item.getMessageTime());
        holder.mIsNew = item.getIsNew();

        if (mFrom == 0) {
           holder.mLinearBacklogDetail.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   int position = holder.getAdapterPosition();
                   if (mBtnClickListener != null) {
                       mBtnClickListener.onItemClick(position, v);
                   }
               }
           });
        }

        if(holder.mIsNew){
            holder.mIvBacklogRedPoint.setVisibility(View.VISIBLE);
            holder.mTvBacklogTitle.setTextColor(0xffD95311);
            holder.mTvBacklogContent.setTextColor(0xff585858);
            holder.mTvBacklogTime.setTextColor(0xff585858);
        }else {
            holder.mIvBacklogRedPoint.setVisibility(View.GONE);
            holder.mTvBacklogTitle.setTextColor(0xff9C9C9C);
            holder.mTvBacklogContent.setTextColor(0xff9C9C9C);
            holder.mTvBacklogTime.setTextColor(0xff9C9C9C);
        }

    }

    @Override
    public int getItemCount() {
        return mdataList.size();
    }
    public Backlog_Info getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private boolean mIsNew;

        private ImageView mIvBacklogRedPoint;
        private TextView mTvBacklogTitle;
        private TextView mTvBacklogContent;
        private TextView mTvBacklogTime;
        private LinearLayout mLinearBacklogDetail;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

            mIvBacklogRedPoint = (ImageView) itemView.findViewById(R.id.iv_backlog_redPoint);
            mTvBacklogTitle = (TextView) itemView.findViewById(R.id.tv_backlog_title);
            mTvBacklogContent = (TextView) itemView.findViewById(R.id.tv_backlog_content);
            mLinearBacklogDetail = (LinearLayout) itemView.findViewById(R.id.linear_backlog_detail);
            mTvBacklogTime = (TextView) itemView.findViewById(R.id.tv_backlog_time);

        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setOnItemBtnClickListener(OnItemClickListener itemClickListener) {
        this.mBtnClickListener = itemClickListener;
    }
}
