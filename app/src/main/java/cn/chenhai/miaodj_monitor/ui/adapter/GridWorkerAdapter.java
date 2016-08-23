package cn.chenhai.miaodj_monitor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

import cn.chenhai.miaodj_monitor.R;

/**
 * Created by ChenHai--霜华 on 2016/6/22. 22:19
 * 邮箱：248866527@qq.com
 */
public class GridWorkerAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public GridWorkerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<String> data){
        this.list = data;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(context).inflate(R.layout.grid_worker_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            //注意添加这一行，即可在item上使用高度... AutoLinearlayout
            AutoUtils.autoSize(convertView);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        viewHolder.mText.setText(list.get(position));
        viewHolder.mText.setTextColor(context.getResources().getColor(R.color.white));
        viewHolder.mText.setBackgroundResource(R.drawable.btn_effect_bg_blue);
    }

    static class ViewHolder {
//        @BindView(R.id.text)
//        TextView mText;
        private TextView mText;

        ViewHolder(View view) {
            //ButterKnife.bind(this, view);
            mText = (TextView) view.findViewById(R.id.text);
        }
    }
}
