package com.saku.lmlib.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.saku.lmlib.R;


/**
 * 使用 {@link PorterDuffXfermode} 实现圆角 Layout 。
 * <p/>
 * Created by admin on 2016-05-06.
 */
public class RoundRectFrameLayout extends FrameLayout {
    private Paint mPaint;
    private Paint mStorePaint;
    private Paint mPaint2;

    private float mRadius;
    private float mTopLeftRadius;
    private float mTopRightRadius;
    private float mBottomLeftRadius;
    private float mBottomRightRadius;
    private float mTopLeftSize;
    private float mTopRightSize;
    private float mBottomLeftSize;
    private float mBottomRightSize;

    private Path mPath = new Path();
    private RectF mOvalRectF = new RectF();
    private RectF mRectF = new RectF();

    public RoundRectFrameLayout(Context context) {
        this(context, null);
    }

    public RoundRectFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundRectFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, R.style.RoundRectFrameLayout);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoundRectFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.RoundRectFrameLayout,
                defStyleAttr,
                defStyleRes);
        mRadius = a.getDimension(R.styleable.RoundRectFrameLayout_roundRadius, 0);
        mTopLeftRadius = a.getDimension(R.styleable.RoundRectFrameLayout_roundTopLeftRadius, mRadius);
        mTopRightRadius = a.getDimension(R.styleable.RoundRectFrameLayout_roundTopRightRadius, mRadius);
        mBottomLeftRadius = a.getDimension(R.styleable.RoundRectFrameLayout_roundBottomLeftRadius, mRadius);
        mBottomRightRadius = a.getDimension(R.styleable.RoundRectFrameLayout_roundBottomRightRadius, mRadius);
        float strokeWidth = a.getDimension(R.styleable.RoundRectFrameLayout_roundStrokeWidth, 0);
        int strokeColor = a.getColor(R.styleable.RoundRectFrameLayout_roundStrokeColor, 0);
        a.recycle();

        mTopLeftSize = mTopLeftRadius * 2;
        mTopRightSize = mTopRightRadius * 2;
        mBottomLeftSize = mBottomLeftRadius * 2;
        mBottomRightSize = mBottomRightRadius * 2;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

        mStorePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mStorePaint.setStyle(Paint.Style.STROKE);
        mStorePaint.setStrokeWidth(strokeWidth);
        mStorePaint.setColor(strokeColor);

        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint2.setXfermode(null);
    }

    @Override
    public void draw(Canvas canvas) {
        mRectF.set(0, 0, getWidth(), getHeight());
        canvas.saveLayer(mRectF, mPaint2, Canvas.ALL_SAVE_FLAG);
        super.draw(canvas);
        drawTopLeft(canvas);
        drawTopRight(canvas);
        drawBottomLeft(canvas);
        drawBottomRight(canvas);
        canvas.drawRoundRect(mRectF, mRadius, mRadius, mStorePaint);
        canvas.restore();
    }

    private void drawTopLeft(Canvas canvas) {
        if (mTopLeftRadius > 0) {
            mPath.reset();
            mPath.moveTo(0, mTopLeftRadius);
            mPath.lineTo(0, 0);
            mPath.lineTo(mTopLeftRadius, 0);
            mOvalRectF.set(0, 0, mTopLeftSize, mTopLeftSize);
            mPath.arcTo(mOvalRectF, -90, -90);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }
    }

    private void drawTopRight(Canvas canvas) {
        if (mTopRightRadius > 0) {
            int width = getWidth();
            mPath.reset();
            mPath.moveTo(width - mTopRightRadius, 0);
            mPath.lineTo(width, 0);
            mPath.lineTo(width, mTopRightRadius);
            mOvalRectF.set(width - mTopRightSize, 0, width, mTopRightSize);
            mPath.arcTo(mOvalRectF, 0, -90);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }
    }

    private void drawBottomLeft(Canvas canvas) {
        if (mBottomLeftRadius > 0) {
            int height = getHeight();
            mPath.reset();
            mPath.moveTo(0, height - mBottomLeftRadius);
            mPath.lineTo(0, height);
            mPath.lineTo(mBottomLeftRadius, height);
            mOvalRectF.set(0, height - mBottomLeftSize, mBottomLeftSize, height);
            mPath.arcTo(mOvalRectF, 90, 90);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }
    }

    private void drawBottomRight(Canvas canvas) {
        if (mBottomRightRadius > 0) {
            int height = getHeight();
            int width = getWidth();
            mPath.reset();
            mPath.moveTo(width - mBottomRightRadius, height);
            mPath.lineTo(width, height);
            mPath.lineTo(width, height - mBottomRightRadius);
            mOvalRectF.set(width - mBottomRightSize, height - mBottomRightSize, width, height);
            mPath.arcTo(mOvalRectF, 0, 90);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }
    }
}

