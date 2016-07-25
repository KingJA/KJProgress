package com.kingja.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/7/22 16:10
 * 修改备注：
 */
public class KJProgressScale extends BaseKJProgress {
    private static final int DEFAULT_SIZE = 100;
    private float mScaleWidth;
    private float mScaleLength;
    private int mScaleNum;
    private int mRadius;
    private Paint mPaint;
    private int rotateX;
    private int rotateY;
    private Paint mTextPaint;
    private int mScaleCap;

    public KJProgressScale(Context context) {
        super(context);
    }

    public KJProgressScale(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KJProgressScale(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttached() {

    }


    @Override
    protected void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.KJProgress);
        mRadius = (int) typedArray.getDimension(
                R.styleable.KJProgress_radius, dp2px(DEFAULT_SIZE));
        mScaleNum = typedArray.getInteger(
                R.styleable.KJProgress_scaleNum, 100);
        mScaleWidth = typedArray.getDimension(
                R.styleable.KJProgress_scaleWidth, dp2px(2));
        mScaleLength = typedArray.getDimension(
                R.styleable.KJProgress_scaleLength, dp2px(10));

        mScaleCap = typedArray.getInteger(R.styleable.KJProgress_scaleCap, 0);
        typedArray.recycle();
    }

    @Override
    protected void initVariable() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mProgressTextSize);
        mPaint.setStrokeWidth(mScaleWidth);

        mTextPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(mProgressTextColor);
        mPaint.setTextSize(mProgressTextSize);

    }

    @Override
    protected void setBetterSize(int width, int height) {

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int expect = mRadius * 2 + getPaddingLeft() + getPaddingRight();
        int width = resolveSize(expect, widthMeasureSpec);
        int height = resolveSize(expect, heightMeasureSpec);
        int realWidth = Math.min(width, height);
        mRadius = (realWidth - getPaddingLeft() - getPaddingRight()) / 2;
        setMeasuredDimension(realWidth, realWidth);
        rotateX = realWidth / 2;
        rotateY = realWidth / 2;
        mProgressTextSize = Math.min(mRadius * 0.5f, mProgressTextSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawProgressScale(canvas);
        drawProgressText(canvas);
    }

    private void drawProgressScale(Canvas canvas) {
        mPaint.setColor(mUnreachColor);
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());
        for (int i = 0; i < mScaleNum; i++) {
            mPaint.setColor(i < getProgress() ? mReachColor : mUnreachColor);
            if (mScaleCap == 0) {
                canvas.drawLine(rotateX, 0, rotateX, mScaleLength, mPaint);
            } else {
                canvas.drawCircle(rotateX, mScaleWidth, mScaleWidth, mPaint);
            }

            canvas.rotate(360f / mScaleNum, rotateX, rotateY);
        }
        canvas.restore();
    }


    private void drawProgressText(Canvas canvas) {
        String progressText = getProgress() + "%";
        float textWidth = mPaint.measureText(progressText);
        float offsetY = -(mPaint.ascent() + mPaint.descent()) / 2;
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextSize(mProgressTextSize);
        canvas.drawText(progressText, rotateX - textWidth * 0.5f, rotateY + offsetY, mTextPaint);
    }
}
