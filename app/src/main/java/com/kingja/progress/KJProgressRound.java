package com.kingja.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/7/22 10:50
 * 修改备注：
 */
public class KJProgressRound extends BaseKJProgress {
    private static final int DEFAULT_SIZE = 100;
    private float mMaxPaintWidth;
    private int mRadius;
    private Paint mTextPaint;
    private Paint mPaint;

    public KJProgressRound(Context context) {
        super(context);
    }

    public KJProgressRound(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KJProgressRound(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.KJProgress);
        mRadius = (int) typedArray.getDimension(
                R.styleable.KJProgress_radius, dp2px(DEFAULT_SIZE));
        typedArray.recycle();
    }

    @Override
    protected void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(mSrokeCap == 0 ? Paint.Cap.ROUND : Paint.Cap.SQUARE);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(mProgressTextSize);
        mReachWidth = mUnreachWidth * 2;

        mTextPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

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
        int realWidth = Math.min(width, height);
        mRadius = (int) ((realWidth - getPaddingLeft() - getPaddingRight() - mMaxPaintWidth) / 2);
        setMeasuredDimension(realWidth, realWidth);


        mProgressTextSize = Math.min(mRadius * 0.5f, mProgressTextSize);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(getPaddingLeft() + mMaxPaintWidth / 2, getPaddingTop()
                + mMaxPaintWidth / 2);
        String progressText = getProgress() + "%";
        float textWidth = mPaint.measureText(progressText);
        float offsetY = -(mPaint.ascent() + mPaint.descent()) / 2;
        //text
        mTextPaint.setColor(mProgressTextColor);
        mTextPaint.setTextSize(mProgressTextSize);
        canvas.drawText(progressText, mRadius - textWidth * 0.5f, mRadius + offsetY, mTextPaint);


        //unreach circle

        mPaint.setColor(mUnreachColor);
        mPaint.setStrokeWidth(mUnreachWidth);
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), -90, 360, false, mPaint);


        //reach circle
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachWidth);
        float angle = getProgress() * 1.0f / mProgressMax * 360;
        canvas.drawArc(new RectF(0, 0, mRadius * 2, mRadius * 2), -90, angle, false, mPaint);

        canvas.restore();


    }


}
