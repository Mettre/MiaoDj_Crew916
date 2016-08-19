package cn.chenhai.miaodj_monitor.DropDownMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.chenhai.miaodj_monitor.R;
import cn.chenhai.miaodj_monitor.view_custom.RatingBar;

/**
 * Created by ChenHai--霜华 on 2016/6/22. 12:45
 * 邮箱：248866527@qq.com
 */
public class StarsDropAdapter extends BaseAdapter {

    private Context context;
    private List<String> list;
    private int checkItemPosition = 0;

    public void setCheckItem(int position) {
        checkItemPosition = position;
        notifyDataSetChanged();
    }

    public StarsDropAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.drop_item_stars_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        fillValue(position, viewHolder);
        return convertView;
    }

    private void fillValue(int position, ViewHolder viewHolder) {
        if(list.get(position).equals("不限")){
            viewHolder.mText.setText(list.get(position));
            viewHolder.mRatingBar.setVisibility(View.INVISIBLE);
        }else {
            String tem = list.get(position)+"星:";
            viewHolder.mText.setText(tem);
            viewHolder.mRatingBar.setStar(Float.parseFloat(list.get(position)));
        }

        if (checkItemPosition != -1) {
            if (checkItemPosition == position) {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_selected));
                //viewHolder.mText.setBackgroundResource(R.drawable.drop_check_bg);
                viewHolder.mlinearLayout.setBackgroundResource(R.color.check_bg_trans);
            } else {
                viewHolder.mText.setTextColor(context.getResources().getColor(R.color.drop_down_unselected));
                //viewHolder.mText.setBackgroundResource(R.drawable.drop_uncheck_bg);
                viewHolder.mlinearLayout.setBackgroundResource(R.color.unCheck_bg_trans);
            }
        }
    }

    static class ViewHolder {
        //@BindView(R.id.text)
        TextView mText;

        LinearLayout mlinearLayout;
        RatingBar mRatingBar;

        ViewHolder(View view) {
            //ButterKnife.bind(this, view);
            mText = (TextView) view.findViewById(R.id.text);

            mlinearLayout = (LinearLayout) view.findViewById(R.id.item_linearLayout);
            mRatingBar = (RatingBar) view.findViewById(R.id.stars_ratingBar);
        }
    }
}
