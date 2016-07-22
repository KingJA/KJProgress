package com.kingja.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 项目名称：
 * 类描述：TODO
 * 创建人：KingJA
 * 创建时间：2016/7/22 10:54
 * 修改备注：
 */
public abstract class BaseKJProgress extends View {
    protected String TAG = "BaseKJProgress";
    private static final int PROGRESS_MAX = 100;
    private static final int REACH_WIDTH = 4;
    private static final int REACH_COLOR = 0XFF3F51B5;
    private static final int UNREACH_COLOR = 0XFFC6C6C6;
    private static final int UNREACH_WIDTH = 4;
    private static final int PROGRESS_TEXT_SIZE = 14;
    private static final int PROGRESS_TEXT_MARGIN = 4;
    protected int mProgress;
    protected int mProgressMax;
    protected float mReachWidth;
    protected float mUnreachWidth;
    protected float mProgressTextSize;
    protected int mReachColor;
    protected int mUnreachColor;
    protected int mProgressTextColor;
    protected Paint mPaint;
    protected float mWidth;
    protected int mHeight;
    protected float mProgressTextMargin;
    protected int mSrokeCap;


    public BaseKJProgress(Context context) {
        this(context, null);
    }

    public BaseKJProgress(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseKJProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KJProgress);
        mProgress = typedArray.getInteger(R.styleable.KJProgress_progress, 0);
        mProgressMax = typedArray.getInteger(R.styleable.KJProgress_progressMax, PROGRESS_MAX);
        mReachWidth = typedArray.getDimension(R.styleable.KJProgress_reachWidth, dp2px(REACH_WIDTH));
        mUnreachWidth = typedArray.getDimension(R.styleable.KJProgress_unreachWidth, dp2px(UNREACH_WIDTH));
        mProgressTextMargin = typedArray.getDimension(R.styleable.KJProgress_progressTextMargin, dp2px(PROGRESS_TEXT_MARGIN));
        mProgressTextSize = typedArray.getDimension(R.styleable.KJProgress_progressTextSize, sp2px(PROGRESS_TEXT_SIZE));
        mReachColor = typedArray.getColor(R.styleable.KJProgress_reachColor, REACH_COLOR);
        mUnreachColor = typedArray.getColor(R.styleable.KJProgress_unreachColor, UNREACH_COLOR);
        mProgressTextColor = typedArray.getColor(R.styleable.KJProgress_progressTextColor, REACH_COLOR);
        mSrokeCap = typedArray.getInteger(R.styleable.KJProgress_strokeCap, 0);
        TAG = getClass().getSimpleName();
        typedArray.recycle();
        init();
    }

    protected abstract void init();

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        mHeight = getMeasuredHeight() - getPaddingTop() - getPaddingBottom();
    }

    protected int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    protected int sp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, dp, getResources().getDisplayMetrics());
    }

    protected int getProgress() {
        return mProgress;
    }

    protected void setProgress(int mProgress) {
        this.mProgress = mProgress;
        invalidate();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("BASE",super.onSaveInstanceState());//保持父类数据
        bundle.putInt("PROGRESS",mProgress);
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if(state instanceof  Bundle){
            Bundle bundle = (Bundle) state;
            mProgress = bundle.getInt("PROGRESS");
            super.onRestoreInstanceState(bundle.getParcelable("BASE"));
        }else {
            super.onRestoreInstanceState(state);
        }
    }
}
