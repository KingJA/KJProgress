package com.kingja.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Parcelable;
import android.util.AttributeSet;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/7/21 14:16
 * 修改备注：
 */
public class KJProgress extends BaseKJProgress {


    public KJProgress(Context context) {
        super(context);
    }

    public KJProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KJProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        mPaint = new Paint();
        mPaint.setStrokeCap(mSrokeCap == 0 ? Paint.Cap.ROUND : Paint.Cap.SQUARE);
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mProgressTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (heightMode != MeasureSpec.EXACTLY) {
            float textHeight = (mPaint.descent() - mPaint.ascent());
            int exceptHeight = (int) (getPaddingTop() + getPaddingBottom() + Math
                    .max(Math.max(mReachWidth,
                            mUnreachWidth), Math.abs(textHeight)));

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight,
                    MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float offsetLeft = mSrokeCap == 0 ? mReachWidth * 0.5f : 0f;
        float offsetRight = mSrokeCap == 0 ? mReachWidth * 0.5f : 0f;
        float marginLeft = mProgressTextMargin;
        float marginRight = mProgressTextMargin;
        canvas.save();
        if (getProgress() == 0) {
            offsetLeft = 0;
        }
        canvas.translate(getPaddingLeft() + offsetLeft, mHeight * 0.5f);
        String progressText = mProgress + "%";
        float textWidth = mPaint.measureText(progressText);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;

        //绘制完成进度条
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachWidth);


        float radio = getProgress() * 1.0f / mProgressMax * 1.0f;
        float progressX = radio * (mWidth - textWidth - mProgressTextMargin - offsetLeft * 0.5f - offsetRight);
        float reachX = progressX;
        if (reachX > 0) {
            canvas.drawLine(0, 0, reachX, 0, mPaint);
        }


        //绘制文本

        mPaint.setColor(mProgressTextColor);
        if (getProgress() == 0) {
            marginLeft = 0;
            offsetRight=offsetRight+marginLeft;
        }
        float textX = progressX + marginLeft;
        canvas.drawText(progressText, textX, -textHeight, mPaint);
        //绘制未完成进度条

        if (mProgress < mProgressMax) {
            mPaint.setColor(mUnreachColor);
            float unReachX = textX + textWidth + marginRight;
            canvas.drawLine(unReachX, 0, mWidth - 2.0f * offsetRight, 0, mPaint);
        }
        canvas.restore();
    }


}
