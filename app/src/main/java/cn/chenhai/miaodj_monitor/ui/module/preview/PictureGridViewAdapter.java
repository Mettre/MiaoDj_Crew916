package cn.chenhai.miaodj_monitor.ui.module.preview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.model.entity.CheckPictureEntity;
import cn.chenhai.miaodj_monitor.presenter.HttpMethods;
import cn.chenhai.miaodj_monitor.utils.ImageLoader2;

/**
 * Created by ChenHai--霜华 on 2016/9/12. 16:42
 * 邮箱：248866527@qq.com
 */
public class PictureGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<CheckPictureEntity.DrawingBean> projects;
    private LayoutInflater layoutInflater;
    private ArrayList<String> pictures;//图片Uri

    public PictureGridViewAdapter(Context context, List<CheckPictureEntity.DrawingBean> projects) {
        this.mContext = context;
        this.projects = projects;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return projects == null ? 0 : projects.size();
    }

    @Override
    public Object getItem(int position) {
        return projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder1 holder;
        if (convertView == null) {
            holder = new ViewHolder1();
            convertView = layoutInflater.inflate(R.layout.adapter_picture_grid_view, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.adapter_picture_imageView);
            holder.textView = (TextView) convertView.findViewById(R.id.adapter_picture_textView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder1) convertView.getTag();

        }
        holder.textView.setText(projects.get(position).getResidential());

        ImageLoader2.displayImage(HttpMethods.BASE_ROOT_URL + projects.get(position).getImgurl(),holder.imageView);
        return convertView;
    }


    public class ViewHolder1 {
        private ImageView imageView;
        private TextView textView;

    }

}
