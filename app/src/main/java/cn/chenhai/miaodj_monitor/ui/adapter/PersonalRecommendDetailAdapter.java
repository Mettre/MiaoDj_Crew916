package cn.chenhai.miaodj_monitor.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.info.RecommendDetailInfo;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by ChenHai--霜华 on 2016/6/28. 14:23
 * 邮箱：248866527@qq.com
 */
public class PersonalRecommendDetailAdapter extends RecyclerView.Adapter<PersonalRecommendDetailAdapter.MyViewHolder> {
    private List<RecommendDetailInfo> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private SupportActivity mActivity;
    private OnItemClickListener mClickListener;

    public PersonalRecommendDetailAdapter(Context context) {
        mActivity = (SupportActivity)context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void refreshDatas(List<RecommendDetailInfo> items) {
        mdataList.clear();
        mdataList.addAll(items);
    }
    public void addDatas(List<RecommendDetailInfo> items) {
        //mdataList.clear();
        mdataList.addAll(items);
    }
    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recommend_money, parent, false);

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
        RecommendDetailInfo item = mdataList.get(position);
        if (item == null) return;

        holder.mTvMoneyOrderNum.setText(item.getOrderNum());
        holder.mTvMoneyType.setText(item.getProjectType());
        holder.mTvMoneyDateStart.setText(item.getBuildStartDate());
        holder.mTvMoneyDateEnd.setText(item.getBuildEndDate());
        holder.mTvMoneyMoney.setText(item.getMoneyCount());
        holder.mTvMoneyTime.setText(item.getGiveTime());

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

    public RecommendDetailInfo getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvMoneyOrderNum;
        private TextView mTvMoneyType;
        private TextView mTvMoneyDateStart;
        private TextView mTvMoneyDateEnd;
        private TextView mTvMoneyMoney;
        private TextView mTvMoneyTime;


        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

            mTvMoneyOrderNum = (TextView) itemView.findViewById(R.id.tv_money_orderNum);
            mTvMoneyType = (TextView) itemView.findViewById(R.id.tv_money_type);
            mTvMoneyDateStart = (TextView) itemView.findViewById(R.id.tv_money_dateStart);
            mTvMoneyDateEnd = (TextView) itemView.findViewById(R.id.tv_money_dateEnd);
            mTvMoneyMoney = (TextView) itemView.findViewById(R.id.tv_money_money);
            mTvMoneyTime = (TextView) itemView.findViewById(R.id.tv_money_time);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
