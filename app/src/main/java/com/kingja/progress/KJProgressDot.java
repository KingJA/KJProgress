package com.kingja.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/7/22 16:10
 * 修改备注：
 */
public class KJProgressDot extends BaseKJProgress {
    private static final int DEFAULT_SIZE = 100;
    private float mMaxPaintWidth;
    private int mRadius;
    private Paint mTextPaint;

    public KJProgressDot(Context context) {
        this(context, null);
    }

    public KJProgressDot(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KJProgressDot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.KJProgress);
        mRadius = (int) typedArray.getDimension(
                R.styleable.KJProgress_radius, dp2px(DEFAULT_SIZE));
        Log.e(TAG, "mRadius1: " + mRadius);
        typedArray.recycle();
    }

    @Override
    protected void init() {
        mPaint = new Paint();
        mPaint.setStrokeCap(mSrokeCap == 0 ? Paint.Cap.ROUND : Paint.Cap.SQUARE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(mProgressTextSize);
        mPaint.setColor(mUnreachColor);
        mPaint.setStrokeWidth(mReachWidth);

    }

    private boolean hasDrawBg;
    private void drawBlackground(Canvas canvas) {
        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop()+ mMaxPaintWidth / 2);
//        for (int i = 0; i <36; i++) {
//            canvas.drawLine(0.5f*mWidth,0,0.5f*mWidth,mUnreachWidth,mPaint);
//            canvas.rotate(-10,mWidth*0.5f,mWidth*0.5f);
//        }
//        hasDrawBg=true;
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), -90, 360, false, mPaint);
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMaxPaintWidth = Math.max(mReachWidth,
                mUnreachWidth);
        int expect = (int) (mRadius * 2 + mMaxPaintWidth + getPaddingLeft()
                + getPaddingRight());
        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);
        Log.e(TAG, "width: " + width + "height: " + height);
        int realWidth = Math.min(width, height);
        mRadius = (int) ((realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2);
        setMeasuredDimension(realWidth, realWidth);
        Log.e(TAG, "mRadius2: " + mRadius);
        mProgressTextSize = Math.min(mRadius * 0.5f, mProgressTextSize);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (!hasDrawBg) {
            drawBlackground(canvas);
        }


    }
}
