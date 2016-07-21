package com.kingja.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/7/21 14:16
 * 修改备注：
 */
public class KJProgress extends View {

    private static final int PROGRESS_MAX = 100;
    private static final int REACH_WIDTH = 4;
    private static final int REACH_COLOR = 0XFF3F51B5;
    private static final int UNREACH_COLOR = 0XFFC6C6C6;
    private static final int UNREACH_WIDTH = 4;
    private static final int PROGRESS_TEXT_SIZE = 14;
    private static final int PROGRESS_TEXT_COLOR = 0XFF3F51B5;
    private static final int PROGRESS_TEXT_MARGIN = 4;
    private static final String TAG = "KJProgress";

    private int mProgress;
    private int mProgressMax;
    private float mReachWidth;
    private float mUnreachWidth;
    private float mProgressTextSize;
    private int mReachColor;
    private int mUnreachColor;
    private int mProgressTextColor;
    private Paint mPaint;
    private float mMaxWidth;
    private int mHeight;
    private float mProgressTextMargin;

    public KJProgress(Context context) {
        this(context, null);
    }

    public KJProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KJProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KJProgress);
        mProgress = typedArray.getInteger(R.styleable.KJProgress_progress, 0);
        mProgressMax = typedArray.getInteger(R.styleable.KJProgress_progressMax, PROGRESS_MAX);
        mReachWidth = typedArray.getDimension(R.styleable.KJProgress_reachWidth, dp2px(REACH_WIDTH));
        mProgressTextMargin = typedArray.getDimension(R.styleable.KJProgress_progressTextMargin, dp2px(PROGRESS_TEXT_MARGIN));
        mUnreachWidth = typedArray.getDimension(R.styleable.KJProgress_unreachWidth, dp2px(UNREACH_WIDTH));
        mProgressTextSize = typedArray.getDimension(R.styleable.KJProgress_progressTextSize, sp2px(PROGRESS_TEXT_SIZE));
        mReachColor = typedArray.getColor(R.styleable.KJProgress_reachColor, REACH_COLOR);
        mUnreachColor = typedArray.getColor(R.styleable.KJProgress_unreachColor, UNREACH_COLOR);
        mProgressTextColor = typedArray.getColor(R.styleable.KJProgress_progressTextColor, PROGRESS_TEXT_COLOR);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
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
        canvas.save();
        canvas.translate(getPaddingLeft() + mReachWidth * 0.5f, mHeight * 0.5f);
        String progressText = mProgress + "%";
        float textWidth = mPaint.measureText(progressText);
        float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;


        //绘制完成进度条
        mPaint.setColor(mReachColor);
        mPaint.setStrokeWidth(mReachWidth);


        float radio = getProgress() * 1.0f / mProgressMax * 1.0f;
        float progressX = radio * (mMaxWidth - textWidth - mProgressTextMargin - mReachWidth * 0.5f - mUnreachWidth);
        float reachX = progressX;
        canvas.drawLine(0, 0, reachX, 0, mPaint);
        //绘制文本

        mPaint.setColor(mProgressTextColor);
        float textX = progressX + mProgressTextMargin;
        canvas.drawText(progressText, textX, -textHeight, mPaint);

        if (mProgress < mProgressMax) {
            mPaint.setColor(mUnreachColor);
            float unReachX = textX + textWidth + mProgressTextMargin;
            canvas.drawLine(unReachX, 0, mMaxWidth - mUnreachWidth, 0, mPaint);
        }
        canvas.restore();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mMaxWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public int sp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, getResources().getDisplayMetrics());
    }

    public int getProgress() {
        return mProgress;
    }

    public void setProgress(int mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }
}
