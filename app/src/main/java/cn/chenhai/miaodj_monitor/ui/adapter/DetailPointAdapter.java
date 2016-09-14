package cn.chenhai.miaodj_monitor.ui.adapter;

import android.content.Context;
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
import cn.chenhai.miaodj_monitor.model.info.DetailPointInfo;
import cn.chenhai.miaodj_monitor.ui.view_custom.AutoCardView;

/**
 * Created by ChenHai--霜华 on 2016/6/13. 21:50
 * 邮箱：248866527@qq.com
 */
public class DetailPointAdapter extends RecyclerView.Adapter<DetailPointAdapter.MyViewHolder> {
    private List<DetailPointInfo> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private int mCount = 0;

    public DetailPointAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setDatas(List<DetailPointInfo> items, int count) {
        //mdataList.clear();
        mdataList.addAll(items);
        mCount = count;
    }

    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_detail_point, parent, false);

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
        DetailPointInfo item = mdataList.get(position);
        if (item == null) return;


        holder.tvItemIndex.setText(item.getItemIndex());
        holder.tvItemName.setText(item.getItemName());

//        if(item.getWorkProgress().equals("施工节点业主不确认")){
//            holder.tvProgress.setTextColor(0xffff6600);
//        }
        holder.tvStartDate.setText(item.getStartDate());
        holder.tvStartDate.setVisibility(View.VISIBLE);
        holder.ivPointCircle.setColorFilter(R.color.colorAccent1);
        //holder.ivStatusBtn.setText(item.getBtnStatus());

        boolean showLineUp = false;
        boolean showLineDown = false;
        boolean showLineOut = false;
        boolean showArrow = false;

        holder.tvStatus.setText(item.getBtnStatus());

        switch (item.getBtnStatus()) {
            case "已完成":
                showLineUp = true;
                showLineDown = true;
                showLineOut = true;
                showArrow = true;
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_green);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_ok);
                break;
            case "待施工":
                showArrow = true;
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_blue);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_wait1);
                break;
            case "待进场":
                showArrow = true;
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_blue);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_wait1);
                break;
            case "施工中":
                showArrow = true;
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_blue);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_wait1);
                break;
            case "待施工员验收":
                showArrow = true;
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_blue);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_wait1);
                break;
            case "待业主验收":
                showArrow = true;
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_blue);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_wait1);
                break;
            case "施工员验收不通过":
                showArrow = true;
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_red);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_red);
                break;
            case "业主验收不通过":
                showArrow = true;
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_red);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_red);
                break;
            case "停工":
                showArrow = true;
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_red);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_red);
                holder.tvStartDate.setVisibility(View.GONE);
                break;
            case "后场加工":
                showArrow = true;
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_yellow);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_wait2);
                break;
            case "未开始":
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_gray);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_nostart);
                break;
            default:
                holder.tvStatus.setBackgroundResource(R.drawable.text_bg_gray);
                holder.ivPointCircle.setBackgroundResource(R.drawable.ic_point_nostart);
                break;
        }

        if (showLineUp && position != 0) holder.isShowLineUp.setVisibility(View.VISIBLE);
        else holder.isShowLineUp.setVisibility(View.INVISIBLE);

        if (showLineDown && position != (mCount - 1))
            holder.isShowLineDown.setVisibility(View.VISIBLE);
        else holder.isShowLineDown.setVisibility(View.INVISIBLE);

        if (showLineOut && position != (mCount - 1))
            holder.isShowLineOut.setVisibility(View.VISIBLE);
        else holder.isShowLineOut.setVisibility(View.INVISIBLE);

        if (showArrow) holder.ivArrow.setVisibility(View.VISIBLE);
        else holder.ivArrow.setVisibility(View.GONE);


        holder.tvEvaluate.setText(item.getEvaluate());
        if (item.getEvaluate().equals("已评价")) {
            holder.tvEvaluate.setTextColor(0xFF84D133);
        } else holder.tvEvaluate.setTextColor(0xFF5f5f60);

        if (showArrow) {
            holder.mHomeCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (mClickListener != null) {
                        mClickListener.onItemClick(position, v);
                    }
                }
            });
        } else {
            holder.mHomeCardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mdataList.size();
    }

    public DetailPointInfo getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItemIndex, tvItemName, tvStartDate, tvEvaluate;
        //private Button btnStatus;
        private TextView tvStatus;
        private ImageView ivArrow, ivPointCircle; //,ivStatusBtn
        private View isShowLineUp, isShowLineDown, isShowLineOut;
        private AutoCardView mHomeCardview;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

            mHomeCardview = (AutoCardView) itemView.findViewById(R.id.home_cardview);

            tvItemIndex = (TextView) itemView.findViewById(R.id.point_circle_num);
            tvItemName = (TextView) itemView.findViewById(R.id.point_name);
            //ivStatusBtn = (ImageView) itemView.findViewById(R.id.point_iv_status);
            tvStatus = (TextView) itemView.findViewById(R.id.point_tv_status);
            tvStartDate = (TextView) itemView.findViewById(R.id.point_time);
            tvEvaluate = (TextView) itemView.findViewById(R.id.point_evaluate);
            ivArrow = (ImageView) itemView.findViewById(R.id.point_arrow);

            ivPointCircle = (ImageView) itemView.findViewById(R.id.point_circle);
            isShowLineUp = (View) itemView.findViewById(R.id.point_line_up);
            isShowLineDown = (View) itemView.findViewById(R.id.point_line_down);
            isShowLineOut = (View) itemView.findViewById(R.id.point_line_out);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}
