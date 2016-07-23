package com.kingja.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.SystemClock;
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
public class KJProgressWheel extends View {

    private static final String TAG = "MeasureView";
    private final int barLength = 16;
    private final int barMaxLength = 270;
    private final long pauseGrowingTime = 200;
    private float mProgressRadius = dp2px(56);
    private float mProgressWidth = dp2px(6);
    private double timeStartGrowing = 0;
    private double barSpinCycleTime = 460;
    private float barExtraLength = 0;
    private boolean barGrowingFromFront = true;
    private long pausedTimeWithoutGrowing = 0;
    private int mProgressCap = 0;
    private int mProgressColor = 0XFF3F51B5;
    private int mBgColor = 0X00000000;
    private Paint mProgressPaint = new Paint();
    private Paint mBgPaint = new Paint();
    private RectF circleBounds = new RectF();
    private float spinSpeed = 230.0f;
    private long lastTimeAnimated = 0;
    private float mProgress = 0.0f;

    public KJProgressWheel(Context context) {
        this(context, null);
    }

    public KJProgressWheel(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KJProgressWheel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.ProgressWheel));
    }

    private void parseAttributes(TypedArray typedArray) {
        mProgressWidth = typedArray.getDimension(R.styleable.ProgressWheel_progressWidth, mProgressWidth);
        mProgressRadius = typedArray.getDimension(R.styleable.ProgressWheel_progressRadius, mProgressRadius);
        mProgressColor = typedArray.getColor(R.styleable.ProgressWheel_progressColor, mProgressColor);
        mBgColor = typedArray.getColor(R.styleable.ProgressWheel_bgColor, mBgColor);
        mProgressCap = typedArray.getInteger(R.styleable.ProgressWheel_progressCap, mProgressCap);
        typedArray.recycle();
        spin();
    }

    public void spin() {
        lastTimeAnimated = SystemClock.uptimeMillis();
        invalidate();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(circleBounds, 360, 360, false, mBgPaint);
        long deltaTime = (SystemClock.uptimeMillis() - lastTimeAnimated);
        float deltaNormalized = deltaTime * spinSpeed / 1000.0f;
        updateBarLength(deltaTime);
        mProgress += deltaNormalized;
        if (mProgress > 360) {
            mProgress -= 360f;
        }
        lastTimeAnimated = SystemClock.uptimeMillis();
        float from = mProgress - 90;
        float length = barLength + barExtraLength;
        if (isInEditMode()) {
            from = 0;
            length = 135;
        }
        canvas.drawArc(circleBounds, from, length, false, mProgressPaint);
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        setupPaints();
        circleBounds = new RectF(0.5f*mProgressWidth, 0.5f*mProgressWidth,
                w - 0.5f*mProgressWidth, h - 0.5f*mProgressWidth);
    }
    private void setupPaints() {
        mProgressPaint.setColor(mProgressColor);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mProgressWidth);

        mBgPaint.setColor(mBgColor);
        mBgPaint.setAntiAlias(true);
        mBgPaint.setStyle(Paint.Style.STROKE);
        mBgPaint.setStrokeWidth(mProgressWidth);
    }
    private void updateBarLength(long deltaTimeInMilliSeconds) {
        if (pausedTimeWithoutGrowing >= pauseGrowingTime) {
            timeStartGrowing += deltaTimeInMilliSeconds;

            if (timeStartGrowing > barSpinCycleTime) {
                // We completed a size change cycle
                // (growing or shrinking)
                timeStartGrowing -= barSpinCycleTime;
                //if(barGrowingFromFront) {
                pausedTimeWithoutGrowing = 0;
                //}
                barGrowingFromFront = !barGrowingFromFront;
            }

            float distance =
                    (float) Math.cos((timeStartGrowing / barSpinCycleTime + 1) * Math.PI) / 2 + 0.5f;
            float destLength = (barMaxLength - barLength);

            if (barGrowingFromFront) {
                barExtraLength = distance * destLength;
            } else {
                float newLength = destLength * (1 - distance);
                mProgress += (barExtraLength - newLength);
                barExtraLength = newLength;
            }
        } else {
            pausedTimeWithoutGrowing += deltaTimeInMilliSeconds;
        }
    }

    public int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
