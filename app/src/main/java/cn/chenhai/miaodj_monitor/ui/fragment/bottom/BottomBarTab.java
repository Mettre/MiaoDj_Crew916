package cn.chenhai.miaodj_monitor.ui.fragment.bottom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.chenhai.miaodj_monitor.R;

/**
 * Created by ChenHai--霜华 on 2016/6/6. 11:10
 * 邮箱：248866527@qq.com
 */
public class BottomBarTab extends LinearLayout {
    private ImageView mIcon;
    private TextView mText;
    private Context mContext;
    private int mTabPosition = -1;

    public BottomBarTab(Context context, @DrawableRes int icon, @StringRes String text) {
        this(context, null, icon, text);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int icon ,String text) {
        this(context, attrs, 0, icon ,text);
    }

    public BottomBarTab(Context context, AttributeSet attrs, int defStyleAttr, int icon ,String text) {
        super(context, attrs, defStyleAttr);
        init(context, icon ,text);
    }

    private void init(Context context, int icon, String text) {
        this.setOrientation(VERTICAL);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.selectableItemBackgroundBorderless});
        Drawable drawable = typedArray.getDrawable(0);
        //Drawable drawable = new ColorDrawable(getResources().getColor(R.color.black));
        setBackground(drawable);
        typedArray.recycle();

        mIcon = new ImageView(context);
        int size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 23, getResources().getDisplayMetrics());
        LayoutParams params = new LayoutParams(size, size);
        params.setMargins(0,15,0,2);
        params.gravity = Gravity.CENTER;
        mIcon.setImageResource(icon);
        mIcon.setLayoutParams(params);
        mIcon.setColorFilter(ContextCompat.getColor(context, R.color.tab_unselect));
        addView(mIcon);

        //文字
        mText = new TextView(context);
        LayoutParams textParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textParams.setMargins(10,5,10,2);
        textParams.gravity = Gravity.CENTER;
        mText.setText(text);
        mText.setTextSize(11);
        mText.setLayoutParams(textParams);
        mText.setTextColor(ContextCompat.getColor(context, R.color.tab_unselect));
        addView(mText);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.colorPrimary1));
            mText.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary1));
//            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.attr.colorPrimary));
//            mText.setTextColor(ContextCompat.getColor(mContext, R.attr.colorPrimary));
        } else {
            mIcon.setColorFilter(ContextCompat.getColor(mContext, R.color.tab_unselect));
            mText.setTextColor(ContextCompat.getColor(mContext, R.color.tab_unselect));
        }
    }

    public void setTabPosition(int position) {
        mTabPosition = position;
        if (position == 0) {
            setSelected(true);
        }
    }

    public int getTabPosition() {
        return mTabPosition;
    }
}
