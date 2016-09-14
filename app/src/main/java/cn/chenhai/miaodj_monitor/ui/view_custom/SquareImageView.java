package cn.chenhai.miaodj_monitor.ui.view_custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * <方形ImageView>
 * <功能详细描述>
 *
 * @author Allen
 * @version [版本号, 2016/8/5 10:56]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SquareImageView extends ImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}
