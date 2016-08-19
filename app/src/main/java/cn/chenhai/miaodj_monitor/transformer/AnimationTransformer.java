package cn.chenhai.miaodj_monitor.transformer;

import android.graphics.Matrix;

/**
 * @author kakajika
 * @since 2015/11/30
 */
public interface AnimationTransformer {

    public void onInitialize(int width, int height, int parentWidth, int parentHeight);
    public Matrix getTransformationMatrix(float interpolatedTime);
    public float getTransformationAlpha();

}
