package cn.chenhai.miaodj_monitor.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.info.BuildPhoto_Info;
import cn.chenhai.miaodj_monitor.ui.module.preview.ImageInfo;
import cn.chenhai.miaodj_monitor.ui.module.preview.ImagePreviewActivity;

/**
 * Created by ChenHai--霜华 on 2016/6/24. 17:38
 * 邮箱：248866527@qq.com
 */
public class DetailBuildDiaryPager2Adapter extends RecyclerView.Adapter<DetailBuildDiaryPager2Adapter.MyViewHolder> {
    private List<BuildPhoto_Info> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClickListener mClickListener;
    private OnItemClickListener mBtnClickListener;

    Uri photoUri1 = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.ic_miaodj);
    Uri photoUri2 = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.ic_miaodj);
    Uri photoUri3 = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.ic_miaodj);
    Uri photoUri4 = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.ic_miaodj);
    Uri photoUri5 = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.ic_miaodj);

    public DetailBuildDiaryPager2Adapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void refreshDatas(List<BuildPhoto_Info> items) {
        mdataList.clear();
        mdataList.addAll(items);
    }
    public void addDatas(List<BuildPhoto_Info> items) {
        //mdataList.clear();
        mdataList.addAll(items);
    }
    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_build_photo, parent, false);

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
        BuildPhoto_Info item = mdataList.get(position);
        if (item == null) return;

        Uri imageUri = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");
        if(item.getImg_portraitPath()!=null && !item.getImg_portraitPath().equals("")){
            imageUri = Uri.parse(item.getImg_portraitPath());
        }
        holder.sdvDiaryPortrait.setImageURI(imageUri);

        holder.tvDiaryName.setText(item.getWorker_name());
        if(item.getWorker_type() == null || item.getWorker_type().equals("")){
            holder.tvDiaryType.setVisibility(View.GONE);
        }else {
            holder.tvDiaryType.setText(item.getWorker_type());
        }

        holder.tvDiaryDateNum.setText(item.getDayNum());
        holder.tvDiaryTime.setText(item.getDate());


        photoUri1 = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.ic_miaodj);
        photoUri2 = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.ic_miaodj);
        photoUri3 = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.ic_miaodj);
        photoUri4 = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.ic_miaodj);
        photoUri5 = Uri.parse("res://cn.chenhai.miaodj_monitor/"+R.drawable.ic_miaodj);
        //Uri photoUri1 = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");

        if(item.getPhoto_path1()!=null && !item.getPhoto_path1().equals("")){
            photoUri1 = Uri.parse(item.getPhoto_path1());
        }
        if(item.getPhoto_path2()!=null && !item.getPhoto_path2().equals("")){
            photoUri2 = Uri.parse(item.getPhoto_path2());
        }
        if(item.getPhoto_path3()!=null && !item.getPhoto_path3().equals("")){
            photoUri3 = Uri.parse(item.getPhoto_path3());
        }
        if(item.getPhoto_path4()!=null && !item.getPhoto_path4().equals("")){
            photoUri4 = Uri.parse(item.getPhoto_path4());
        }
        if(item.getPhoto_path5()!=null && !item.getPhoto_path5().equals("")){
            photoUri5 = Uri.parse(item.getPhoto_path5());
        }
        holder.sdvDiaryPhoto1.setImageURI(photoUri1);
        holder.sdvDiaryPhoto2.setImageURI(photoUri2);
        holder.sdvDiaryPhoto3.setImageURI(photoUri3);
        holder.sdvDiaryPhoto4.setImageURI(photoUri4);
        holder.sdvDiaryPhoto5.setImageURI(photoUri5);


        int statusHeight = getStatusHeight(mContext);

        holder.sdvDiaryPhoto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;
                List<ImageInfo> imageInfoList = item.getImageurls();
                for (int i = 0; i < imageInfoList.size(); i++) {
                    ImageInfo info = imageInfoList.get(i);
                    View imageView;
                    //if (i < nineGridView.getMaxSize()) {
                    if (i < 4) {
                        imageView = holder.sdvDiaryPhoto1;
                    } else {
                        //如果图片的数量大于显示的数量，则超过部分的返回动画统一退回到最后一个图片的位置
                        imageView = holder.sdvDiaryPhoto4;
                    }
                    info.imageViewWidth = imageView.getWidth();
                    info.imageViewHeight = imageView.getHeight();
                    int[] points = new int[2];
                    imageView.getLocationInWindow(points);
                    info.imageViewX = points[0];
                    info.imageViewY = points[1] - statusHeight;
                }

                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfoList);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
            }
        });
        holder.sdvDiaryPhoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 1;
                List<ImageInfo> imageInfoList = item.getImageurls();
                for (int i = 0; i < imageInfoList.size(); i++) {
                    ImageInfo info = imageInfoList.get(i);
                    View imageView;
                    //if (i < nineGridView.getMaxSize()) {
                    if (i < 4) {
                        imageView = holder.sdvDiaryPhoto2;
                    } else {
                        //如果图片的数量大于显示的数量，则超过部分的返回动画统一退回到最后一个图片的位置
                        imageView = holder.sdvDiaryPhoto4;
                    }
                    info.imageViewWidth = imageView.getWidth();
                    info.imageViewHeight = imageView.getHeight();
                    int[] points = new int[2];
                    imageView.getLocationInWindow(points);
                    info.imageViewX = points[0];
                    info.imageViewY = points[1] - statusHeight;
                }

                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfoList);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
            }
        });
        holder.sdvDiaryPhoto3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 2;
                List<ImageInfo> imageInfoList = item.getImageurls();
                for (int i = 0; i < imageInfoList.size(); i++) {
                    ImageInfo info = imageInfoList.get(i);
                    View imageView;
                    //if (i < nineGridView.getMaxSize()) {
                    if (i < 4) {
                        imageView = holder.sdvDiaryPhoto3;
                    } else {
                        //如果图片的数量大于显示的数量，则超过部分的返回动画统一退回到最后一个图片的位置
                        imageView = holder.sdvDiaryPhoto4;
                    }
                    info.imageViewWidth = imageView.getWidth();
                    info.imageViewHeight = imageView.getHeight();
                    int[] points = new int[2];
                    imageView.getLocationInWindow(points);
                    info.imageViewX = points[0];
                    info.imageViewY = points[1] - statusHeight;
                }

                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfoList);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
            }
        });
        holder.sdvDiaryPhoto4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 3;
                List<ImageInfo> imageInfoList = item.getImageurls();
                for (int i = 0; i < imageInfoList.size(); i++) {
                    ImageInfo info = imageInfoList.get(i);
                    View imageView;
                    //if (i < nineGridView.getMaxSize()) {
                    if (i < 4) {
                        imageView = holder.sdvDiaryPhoto4;
                    } else {
                        //如果图片的数量大于显示的数量，则超过部分的返回动画统一退回到最后一个图片的位置
                        imageView = holder.sdvDiaryPhoto4;
                    }
                    info.imageViewWidth = imageView.getWidth();
                    info.imageViewHeight = imageView.getHeight();
                    int[] points = new int[2];
                    imageView.getLocationInWindow(points);
                    info.imageViewX = points[0];
                    info.imageViewY = points[1] - statusHeight;
                }

                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfoList);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
            }
        });
        holder.sdvDiaryPhoto5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 4;
                List<ImageInfo> imageInfoList = item.getImageurls();
                for (int i = 0; i < imageInfoList.size(); i++) {
                    ImageInfo info = imageInfoList.get(i);
                    View imageView;
                    //if (i < nineGridView.getMaxSize()) {
                    if (i < 4) {
                        imageView = holder.sdvDiaryPhoto5;
                    } else {
                        //如果图片的数量大于显示的数量，则超过部分的返回动画统一退回到最后一个图片的位置
                        imageView = holder.sdvDiaryPhoto4;
                    }
                    info.imageViewWidth = imageView.getWidth();
                    info.imageViewHeight = imageView.getHeight();
                    int[] points = new int[2];
                    imageView.getLocationInWindow(points);
                    info.imageViewX = points[0];
                    info.imageViewY = points[1] - statusHeight;
                }

                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfoList);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
            }
        });
//        holder.linearDiaryEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = holder.getAdapterPosition();
//                if (mBtnClickListener != null) {
//                    mBtnClickListener.onItemClick(position, v);
//                }
//            }
//        });
    }

    /**
     * 获得状态栏的高度
     */
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    @Override
    public int getItemCount() {
        return mdataList.size();
    }
    public BuildPhoto_Info getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView sdvDiaryPortrait;
        TextView tvDiaryName;
        TextView tvDiaryType;
        TextView tvDiaryDateNum;
        TextView tvDiaryTime;

        SimpleDraweeView sdvDiaryPhoto1;
        SimpleDraweeView sdvDiaryPhoto2;
        SimpleDraweeView sdvDiaryPhoto3;
        SimpleDraweeView sdvDiaryPhoto4;
        SimpleDraweeView sdvDiaryPhoto5;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

            sdvDiaryPortrait = (SimpleDraweeView) itemView.findViewById(R.id.sdv_diary_portrait);
            tvDiaryName = (TextView) itemView.findViewById(R.id.tv_diary_name);
            tvDiaryType = (TextView) itemView.findViewById(R.id.tv_diary_type);
            tvDiaryDateNum = (TextView) itemView.findViewById(R.id.tv_diary_dateNum);
            tvDiaryTime = (TextView) itemView.findViewById(R.id.tv_diary_time);

            sdvDiaryPhoto1 = (SimpleDraweeView) itemView.findViewById(R.id.sdv_diary_photo1);
            sdvDiaryPhoto2 = (SimpleDraweeView) itemView.findViewById(R.id.sdv_diary_photo2);
            sdvDiaryPhoto3 = (SimpleDraweeView) itemView.findViewById(R.id.sdv_diary_photo3);
            sdvDiaryPhoto4 = (SimpleDraweeView) itemView.findViewById(R.id.sdv_diary_photo4);
            sdvDiaryPhoto5 = (SimpleDraweeView) itemView.findViewById(R.id.sdv_diary_photo5);

        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setOnItemBtnClickListener(OnItemClickListener itemClickListener) {
        this.mBtnClickListener = itemClickListener;
    }
}
