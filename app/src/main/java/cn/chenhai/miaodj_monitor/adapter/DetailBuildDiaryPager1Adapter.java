package cn.chenhai.miaodj_monitor.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.info.BuildDiary_Info;

/**
 * Created by ChenHai--霜华 on 2016/6/24. 15:33
 * 邮箱：248866527@qq.com
 */
public class DetailBuildDiaryPager1Adapter extends RecyclerView.Adapter<DetailBuildDiaryPager1Adapter.MyViewHolder> {
    private List<BuildDiary_Info> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private OnItemClickListener mBtnClickListener;

    public DetailBuildDiaryPager1Adapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void refreshDatas(List<BuildDiary_Info> items) {
        mdataList.clear();
        mdataList.addAll(items);
    }

    public void addDatas(List<BuildDiary_Info> items) {
        //mdataList.clear();
        mdataList.addAll(items);
    }
    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_build_diary, parent, false);

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
        BuildDiary_Info item = mdataList.get(position);
        if (item == null) return;

        Uri imageUri = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");
        if(item.getImg_portraitPath()!=null && !item.getImg_portraitPath().equals("")){
            imageUri = Uri.parse(item.getImg_portraitPath());
        }
        holder.sdvDiaryPortrait.setImageURI(imageUri);

        holder.tvDiaryName.setText(item.getWorker_name());
        holder.tvDiaryType.setText(item.getWorker_type());
        holder.tvDiaryDateNum.setText(item.getDayNum());
        holder.tvDiaryTime.setText(item.getDate());

        holder.mIfContent = item.isIfContent();
        holder.mIfEdit = item.isIfEdit();

        if(!holder.mIfEdit){
            holder.linearDiaryEdit.setVisibility(View.INVISIBLE);
        }else {
            holder.linearDiaryEdit.setVisibility(View.VISIBLE);
            holder.linearDiaryEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (mBtnClickListener != null) {
                        mBtnClickListener.onItemClick(position, v);
                    }
                }
            });
        }

        if(holder.mIfContent){
            holder.tvDiaryContent.setVisibility(View.VISIBLE);
            holder.tvDiaryNotWrite.setVisibility(View.GONE);

            holder.tvDiaryContent.setText(item.getDiary_content());

        }else {
            holder.tvDiaryContent.setVisibility(View.GONE);
            holder.tvDiaryNotWrite.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mdataList.size();
    }
    public BuildDiary_Info getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private boolean mIfContent;
        private boolean mIfEdit;

        SimpleDraweeView sdvDiaryPortrait;
        TextView tvDiaryName;
        TextView tvDiaryType;
        TextView tvDiaryDateNum;
        TextView tvDiaryContent;
        TextView tvDiaryNotWrite;
        TextView tvDiaryTime;
        FrameLayout linearDiaryEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

            sdvDiaryPortrait = (SimpleDraweeView) itemView.findViewById(R.id.sdv_diary_portrait);
            tvDiaryName = (TextView) itemView.findViewById(R.id.tv_diary_name);
            tvDiaryType = (TextView) itemView.findViewById(R.id.tv_diary_type);
            tvDiaryDateNum = (TextView) itemView.findViewById(R.id.tv_diary_dateNum);
            tvDiaryContent = (TextView) itemView.findViewById(R.id.tv_diary_content);
            tvDiaryNotWrite = (TextView) itemView.findViewById(R.id.tv_diary_notWrite);
            tvDiaryTime = (TextView) itemView.findViewById(R.id.tv_diary_time);
            linearDiaryEdit = (FrameLayout) itemView.findViewById(R.id.linear_diary_edit);

        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setOnItemBtnClickListener(OnItemClickListener itemClickListener) {
        this.mBtnClickListener = itemClickListener;
    }
}
