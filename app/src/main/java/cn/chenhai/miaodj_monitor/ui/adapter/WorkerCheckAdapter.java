package cn.chenhai.miaodj_monitor.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.info.WorkerCheckInfo;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by ChenHai--霜华 on 2016/6/20. 14:36
 * 邮箱：248866527@qq.com
 */
public class WorkerCheckAdapter extends RecyclerView.Adapter<WorkerCheckAdapter.MyViewHolder> {
    private List<WorkerCheckInfo> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private SupportActivity mActivity;
    private OnItemClickListener mClickListener;
    private OnItemClickListener mBtnClickListener;

    public WorkerCheckAdapter(Context context) {
        mActivity = (SupportActivity)context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void addDatas(List<WorkerCheckInfo> items) {
        //mdataList.clear();
        mdataList.addAll(items);
    }
    public void refreshDatas(List<WorkerCheckInfo> items) {
        mdataList.clear();
        mdataList.addAll(items);
    }
    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_worker_check, parent, false);

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
        WorkerCheckInfo item = mdataList.get(position);
        if (item == null) return;


        holder.mWorkerTvType.setText(item.getWorkerNowTypes());
//        if(item.getWorkProgress().equals("施工节点业主不确认")){
//            holder.tvProgress.setTextColor(0xffff6600);
//        }

        boolean ifAddedWorker = Boolean.parseBoolean(item.getIfAddedWorker());
        boolean ifAgree = Boolean.parseBoolean(item.getIfAgree());

        if(ifAddedWorker){
            holder.mWorkerSdvPortrait.setVisibility(View.VISIBLE);
            holder.mWorkerTvName.setVisibility(View.VISIBLE);
            holder.mWorkerTvNoAdd.setVisibility(View.GONE);
            holder.mWorkerTvStatus.setVisibility(View.GONE);
            holder.mWorkerAddAlter.setVisibility(View.GONE);
            holder.mWorkerArrow.setVisibility(View.GONE);

            Uri imageUri = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");
            if(item.getImg_portraitPath()!=null && !item.getImg_portraitPath().equals("")){
                imageUri = Uri.parse(item.getImg_portraitPath());
            }
            //创建DraweeController
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    //加载的图片URI地址
                    .setUri(imageUri)
                    //设置点击重试是否开启
                    .setTapToRetryEnabled(true)
                    //设置旧的Controller,在指定一个新的controller的时候，使用setOldController，这可节省不必要的内存分配。
                    .setOldController(holder.mWorkerSdvPortrait.getController())
                    //构建
                    .build();

            //设置DraweeController
            holder.mWorkerSdvPortrait.setController(controller);
            //开始下载
            //holder.mWorkerSdvPortrait.setImageURI(imageUri);
            holder.mWorkerSdvPortrait.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (mBtnClickListener != null) {
                        mBtnClickListener.onItemClick(position, v);
                    }
                }
            });

            holder.mWorkerTvName.setText(item.getWorkerName());

            //添加下划线
            holder.mWorkerTvName.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG ); //下划线
            holder.mWorkerTvName.getPaint().setAntiAlias(true);//抗锯齿
            //或者这样写
            //holder.mWorkerTvName.setText(Html.fromHtml("<u>"+item.getWorkerName()+"</u>"));

            holder.mWorkerTvName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getAdapterPosition();
                    if (mBtnClickListener != null) {
                        mBtnClickListener.onItemClick(position, v);
                    }
                }
            });
            if(!ifAgree){
                holder.mWorkerTvNoAdd.setVisibility(View.GONE);
                holder.mWorkerTvStatus.setVisibility(View.VISIBLE);
                holder.mWorkerAddAlter.setVisibility(View.VISIBLE);
                holder.mWorkerArrow.setVisibility(View.VISIBLE);
                holder.mWorkerAddAlter.setText("修改");
                holder.itemView.setEnabled(true);
            }else {
                holder.itemView.setEnabled(false);
            }

        } else {
            holder.itemView.setEnabled(true);
            holder.mWorkerSdvPortrait.setVisibility(View.GONE);
            holder.mWorkerTvName.setVisibility(View.GONE);
            holder.mWorkerTvNoAdd.setVisibility(View.VISIBLE);
            holder.mWorkerTvStatus.setVisibility(View.GONE);
            holder.mWorkerAddAlter.setVisibility(View.VISIBLE);
            holder.mWorkerArrow.setVisibility(View.VISIBLE);

            holder.mWorkerTvNoAdd.setText("未添加人员");
            holder.mWorkerAddAlter.setText("添加");
        }

    }

    @Override
    public int getItemCount() {
        return mdataList.size();
    }

    public WorkerCheckInfo getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mWorkerTvType;
        private SimpleDraweeView mWorkerSdvPortrait;
        private TextView mWorkerTvName;
        private TextView mWorkerTvNoAdd;
        private TextView mWorkerTvStatus;
        private TextView mWorkerAddAlter;
        private ImageView mWorkerArrow;


        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

            mWorkerTvType = (TextView) itemView.findViewById(R.id.worker_tv_type);
            mWorkerSdvPortrait = (SimpleDraweeView) itemView.findViewById(R.id.worker_sdv_portrait);
            mWorkerTvName = (TextView) itemView.findViewById(R.id.worker_tv_name);
            mWorkerTvNoAdd = (TextView) itemView.findViewById(R.id.worker_tv_noAdd);
            mWorkerTvStatus = (TextView) itemView.findViewById(R.id.worker_tv_status);
            mWorkerAddAlter = (TextView) itemView.findViewById(R.id.worker_addAlter);
            mWorkerArrow = (ImageView) itemView.findViewById(R.id.worker_arrow);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setOnItemBtnClickListener(OnItemClickListener itemClickListener) {
        this.mBtnClickListener = itemClickListener;
    }



}
