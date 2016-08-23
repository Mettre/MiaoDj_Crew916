package cn.chenhai.miaodj_monitor.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.info.Material_auxiliary_Info;

/**
 * Created by ChenHai--霜华 on 2016/8/10. 09:56
 * 邮箱：248866527@qq.com
 */
public class DetailSelectAuxiDeliveryAdapter extends RecyclerView.Adapter<DetailSelectAuxiDeliveryAdapter.MyViewHolder> {
    private List<Material_auxiliary_Info> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickListener mClickListener;

    public DetailSelectAuxiDeliveryAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void refreshDatas(List<Material_auxiliary_Info> items) {
        mdataList.clear();
        mdataList.addAll(items);
    }
    public void setDatas(List<Material_auxiliary_Info> items) {
        //mdataList.clear();
        mdataList.addAll(items);
    }
    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_material_auxilary_detail, parent, false);

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
        Material_auxiliary_Info item = mdataList.get(position);
        if (item == null) return;

        //Uri imageUri = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");
        Uri imageUri = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.logo_color);
        if(item.getImg_portraitPath()!=null && !item.getImg_portraitPath().equals("")){
            imageUri = Uri.parse(item.getImg_portraitPath());
        }
        holder.mSdvAuxiliaryPortrait.setImageURI(imageUri);

        holder.mTvAuxiliaryNameDes.setText(item.getAuxiliaryNameDes());
        //holder.ivPointCircle.setColorFilter(R.color.colorAccent1);
        holder.mTvAuxiliarySpecs.setText(item.getAuxiliarySpecs());
        holder.mTvAuxiliaryBrand.setText(item.getAuxiliaryBrand());
        holder.mTvAuxiliarySpecs2.setText(item.getAuxiliarySpecs2());
        holder.mEtAuxiliaryCount.setText(item.getAuxiliaryCount());
        holder.mTvAuxiliaryCountUnit.setText(item.getAuxiliaryCountUnit());
        switch (item.getStatus()){
            case "6":
                holder.mCbAuxiliaryStatus.setVisibility(View.INVISIBLE);
                holder.mTvAuxiliaryStatus.setVisibility(View.VISIBLE);
                holder.mTvAuxiliaryStatus.setText("配送中");
                holder.mTvAuxiliaryStatus.setTextColor(0xffFE6131);
                break;
            case "7":
                holder.mCbAuxiliaryStatus.setVisibility(View.VISIBLE);
                holder.mTvAuxiliaryStatus.setVisibility(View.VISIBLE);
                holder.mTvAuxiliaryStatus.setText("已签收");
                holder.mTvAuxiliaryStatus.setTextColor(0xff84D133);
                break;
            default:
                holder.mCbAuxiliaryStatus.setVisibility(View.GONE);
                holder.mTvAuxiliaryStatus.setVisibility(View.GONE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mdataList.size();
    }
    public Material_auxiliary_Info getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView mSdvAuxiliaryPortrait;
        private TextView mTvAuxiliaryNameDes;
        private TextView mTvAuxiliarySpecs;
        private TextView mTvAuxiliaryBrand;
        private TextView mTvAuxiliarySpecs2;
        private TextView mEtAuxiliaryCount;
        private TextView mTvAuxiliaryCountUnit;
        private CheckBox mCbAuxiliaryStatus;
        private TextView mTvAuxiliaryStatus;


        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

            mSdvAuxiliaryPortrait = (SimpleDraweeView) itemView.findViewById(R.id.sdv_auxiliary_portrait);
            mTvAuxiliaryNameDes = (TextView) itemView.findViewById(R.id.tv_auxiliary_name_des);
            mTvAuxiliarySpecs = (TextView) itemView.findViewById(R.id.tv_auxiliary_specs);
            mTvAuxiliaryBrand = (TextView) itemView.findViewById(R.id.tv_auxiliary_brand);
            mTvAuxiliarySpecs2 = (TextView) itemView.findViewById(R.id.tv_auxiliary_specs2);
            mEtAuxiliaryCount = (TextView) itemView.findViewById(R.id.et_auxiliary_count);
            mTvAuxiliaryCountUnit = (TextView) itemView.findViewById(R.id.tv_auxiliary_count_unit);
            mCbAuxiliaryStatus = (CheckBox) itemView.findViewById(R.id.cb_auxiliary_status);
            mTvAuxiliaryStatus = (TextView) itemView.findViewById(R.id.tv_auxiliary_status);

        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
