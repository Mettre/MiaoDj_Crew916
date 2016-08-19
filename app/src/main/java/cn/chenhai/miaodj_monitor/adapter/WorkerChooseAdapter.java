package cn.chenhai.miaodj_monitor.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.info.WorkerChooseInfo;
import cn.chenhai.miaodj_monitor.view_custom.RatingBar;

/**
 * Created by ChenHai--霜华 on 2016/6/20. 11:08
 * 邮箱：248866527@qq.com
 */
public class WorkerChooseAdapter extends RecyclerView.Adapter<WorkerChooseAdapter.MyViewHolder> {
    private List<WorkerChooseInfo> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private OnItemClickListener mClickListener;
    private OnItemClickListener mBtnClickListener;

    private Context mContext;

    public WorkerChooseAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setDatas(List<WorkerChooseInfo> items) {
        //mdataList.clear();
        mdataList.addAll(items);
    }
    public void refreshDatas(List<WorkerChooseInfo> items) {
        mdataList.clear();
        mdataList.addAll(items);
    }
    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_worker_choose, parent, false);

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
        WorkerChooseInfo item = mdataList.get(position);
        if (item == null) return;

        Uri imageUri = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");
        if(item.getImg_portraitPath()!=null && !item.getImg_portraitPath().equals("")){
            imageUri = Uri.parse(item.getImg_portraitPath());
        }

//        //初始化圆角圆形参数对象
//        RoundingParams rp = new RoundingParams();
//        //设置图像是否为圆形
//        rp.setRoundAsCircle(true);
//        rp.setBorder(Color.BLACK, 0);
//        rp.setRoundingMethod(RoundingParams.RoundingMethod.OVERLAY_COLOR);
//
//        //获取GenericDraweeHierarchy对象
//        GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(mContext.getResources())
//                //设置圆形圆角参数
//                .setRoundingParams(rp)
//                //设置淡入淡出动画持续时间(单位：毫秒ms)
//                .setFadeDuration(5000)
//                //构建
//                .build();
//
//        //设置Hierarchy
//        holder.mWorkerSdvPortrait.setHierarchy(hierarchy);

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

        holder.mWorkerTvName.setText(item.getWorkerName());
        holder.mWorkerTvType.setText(item.getWorkerAllTypes());
//        if(item.getWorkProgress().equals("施工节点业主不确认")){
//            holder.tvProgress.setTextColor(0xffff6600);
//        }

        holder.mWorkerTvYearCount.setText(item.getWorkerYear());
        holder.mWorkerRbRatingBar.setStar(Float.parseFloat(item.getWorkerStars()));

        holder.mWorkerBtnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (mBtnClickListener != null) {
                    mBtnClickListener.onItemClick(position, v);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mdataList.size();
    }

    public WorkerChooseInfo getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView mWorkerSdvPortrait;
        private TextView mWorkerTvName;
        private TextView mWorkerTvType;
        private TextView mWorkerTvYearCount;
        private RatingBar mWorkerRbRatingBar;
        private Button mWorkerBtnChoose;

        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

            mWorkerSdvPortrait = (SimpleDraweeView) itemView.findViewById(R.id.worker_sdv_portrait);
            mWorkerTvName = (TextView) itemView.findViewById(R.id.worker_tv_name);
            mWorkerTvType = (TextView) itemView.findViewById(R.id.worker_tv_type);
            mWorkerTvYearCount = (TextView) itemView.findViewById(R.id.worker_tv_yearCount);
            mWorkerRbRatingBar = (RatingBar) itemView.findViewById(R.id.worker_rb_ratingBar);
            mWorkerBtnChoose = (Button) itemView.findViewById(R.id.worker_btn_choose);

        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setOnItemBtnClickListener(OnItemClickListener itemClickListener) {
        this.mBtnClickListener = itemClickListener;
    }
}
