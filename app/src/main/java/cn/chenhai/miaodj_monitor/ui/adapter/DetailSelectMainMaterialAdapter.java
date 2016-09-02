package cn.chenhai.miaodj_monitor.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.info.Material_main_Info;

/**
 * Created by ChenHai--霜华 on 2016/6/27. 10:54
 * 邮箱：248866527@qq.com
 */
public class DetailSelectMainMaterialAdapter extends RecyclerView.Adapter<DetailSelectMainMaterialAdapter.MyViewHolder> {
    private List<Material_main_Info> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickListener mClickListener;

    public DetailSelectMainMaterialAdapter(Context context, List<Material_main_Info> mData) {
        this.mInflater = LayoutInflater.from(context);
        this.mdataList = mData;
    }

    public void refreshDatas(List<Material_main_Info> items) {
        mdataList.clear();
        mdataList.addAll(items);
        notifyDataSetChanged();
    }

    public void setDatas(List<Material_main_Info> items) {
        //mdataList.clear();
        mdataList.addAll(items);
        notifyDataSetChanged();
    }

    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        notifyDataSetChanged();
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_material_main, parent, false);

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
        Material_main_Info item = mdataList.get(position);
//        if (item == null) return;

//        holder.mHomeCardview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                if (mClickListener != null) {
//                    mClickListener.onItemClick(position, v);
//                }
//            }
//        });

        holder.mTvMaterialName.setText(item.getMaterial_name());
        holder.mTvMaterialName2.setText(item.getMaterial_name2());

        //Uri imageUri = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");
        Uri imageUri = Uri.parse("res://cn.chenhai.miaodj_monitor/" + R.drawable.logo_color);
        if (item.getImg_portraitPath() != null && !item.getImg_portraitPath().equals("")) {
            imageUri = Uri.parse(item.getImg_portraitPath());
        }
        holder.mSdvMaterialPortrait.setImageURI(imageUri);

        holder.mTvMaterialNameDes.setText(item.getMaterial_name_describe());
        //holder.ivPointCircle.setColorFilter(R.color.colorAccent1);
        holder.mTvMaterialNameNumber.setText(item.getMaterial_number());
        holder.mTvMaterialBrand.setText(item.getMaterial_brand());
        holder.mEtMaterialCount.setText(item.getMaterial_count());

        holder.mTvMaterialRoom.setText(item.getMaterial_room());

    }

    @Override
    public int getItemCount() {
        return mdataList.size();
    }

    public Material_main_Info getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mTvMaterialName;
        private TextView mTvMaterialName2;
        private SimpleDraweeView mSdvMaterialPortrait;
        private TextView mTvMaterialNameDes;
        private TextView mTvMaterialNameNumber;
        private TextView mTvMaterialBrand;
        private TextView mEtMaterialCount;
        private TextView mTvMaterialRoom;


        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);


            mTvMaterialName = (TextView) itemView.findViewById(R.id.tv_material_name);
            mTvMaterialName2 = (TextView) itemView.findViewById(R.id.tv_material_name2);
            mSdvMaterialPortrait = (SimpleDraweeView) itemView.findViewById(R.id.sdv_material_portrait);
            mTvMaterialNameDes = (TextView) itemView.findViewById(R.id.tv_material_name_des);
            mTvMaterialNameNumber = (TextView) itemView.findViewById(R.id.tv_material_name_number);
            mTvMaterialBrand = (TextView) itemView.findViewById(R.id.tv_material_brand);
            mEtMaterialCount = (TextView) itemView.findViewById(R.id.et_material_count);
            mTvMaterialRoom = (TextView) itemView.findViewById(R.id.tv_material_room);

        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
