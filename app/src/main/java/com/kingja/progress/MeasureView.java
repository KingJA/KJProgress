package com.kingja.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 项目名称：常用工具类
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/7/2120:23
 * 修改备注：
 */
public class MeasureView extends View {

    private int measuredHeight;
    private int measuredWidth;
    private Paint paint;

    public MeasureView(Context context) {
        this(context, null);
    }

    public MeasureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MeasureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0XFF3F51B5);
//        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(dp2px(20));
    }

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(dp2px(5) / 2, 0, measuredWidth - dp2px(5) / 2, 0, paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        measuredHeight = getMeasuredHeight();
        measuredWidth = getMeasuredWidth();
    }
}
