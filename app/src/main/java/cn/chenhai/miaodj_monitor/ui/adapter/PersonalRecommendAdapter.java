package cn.chenhai.miaodj_monitor.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.service.helper.OnItemClickListener;
import cn.chenhai.miaodj_monitor.model.info.RecommendWorkerInfo;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by ChenHai--霜华 on 2016/6/27. 19:46
 * 邮箱：248866527@qq.com
 */
public class PersonalRecommendAdapter extends RecyclerView.Adapter<PersonalRecommendAdapter.MyViewHolder> {
    private List<RecommendWorkerInfo> mdataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private SupportActivity mActivity;
    private OnItemClickListener mClickListener;
    private OnItemClickListener mBtnClickListener;

    public PersonalRecommendAdapter(Context context) {
        mActivity = (SupportActivity)context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void refreshDatas(List<RecommendWorkerInfo> items) {
        mdataList.clear();
        mdataList.addAll(items);
    }
    public void addDatas(List<RecommendWorkerInfo> items) {
        //mdataList.clear();
        mdataList.addAll(items);
    }
    public void removeAllDataList() {
        this.mdataList.removeAll(mdataList);
        //mdataList.clear();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_recommend_worker, parent, false);

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
        RecommendWorkerInfo item = mdataList.get(position);
        if (item == null) return;

        if(item.isIfRegister()){
            Uri imageUri = Uri.parse("http://img3.duitang.com/uploads/item/201409/24/20140924230301_rVPYh.jpeg");
            if(item.getImg_portraitPath()!=null && !item.getImg_portraitPath().equals("")){
                imageUri = Uri.parse(item.getImg_portraitPath());
            }
            //开始下载
            holder.mWorkerSdvPortrait.setImageURI(imageUri);

            holder.mTvWorkerName.setText(item.getWorkerName());
            holder.mTvWorkerPhone.setText(item.getWorkerPhone());
            holder.mTvWorkerMoney.setText(item.getMoney());
            holder.mIvWorkerArrow.setVisibility(View.VISIBLE);
            holder.itemView.setEnabled(true);
        } else {

            Uri imageUri = Uri.parse("");
            //开始下载
            holder.mWorkerSdvPortrait.setImageURI(imageUri);

            holder.mTvWorkerName.setText(item.getWorkerName());
            holder.mTvWorkerPhone.setText(item.getWorkerPhone());

            holder.mTvWorkerMoney.setText("还未注册");
            holder.mTvWorkerMoney.setTextColor(0xffFE1901);
            holder.mIvWorkerArrow.setVisibility(View.INVISIBLE);
            holder.itemView.setEnabled(false);

        }


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

    public RecommendWorkerInfo getItem(int position) {
        return mdataList.get(position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView mWorkerSdvPortrait;
        private TextView mTvWorkerName;
        private TextView mTvWorkerPhone;
        private TextView mTvWorkerMoney;
        private String mWorkerID;

        private ImageView mIvWorkerArrow;


        public MyViewHolder(View itemView) {
            super(itemView);
            AutoUtils.autoSize(itemView);

            mWorkerSdvPortrait = (SimpleDraweeView) itemView.findViewById(R.id.worker_sdv_portrait);
            mTvWorkerName = (TextView) itemView.findViewById(R.id.tv_worker_name);
            mTvWorkerPhone = (TextView) itemView.findViewById(R.id.tv_worker_phone);
            mTvWorkerMoney = (TextView) itemView.findViewById(R.id.tv_worker_money);
            mIvWorkerArrow = (ImageView) itemView.findViewById(R.id.iv_worker_arrow);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
    public void setOnItemBtnClickListener(OnItemClickListener itemClickListener) {
        this.mBtnClickListener = itemClickListener;
    }
}
