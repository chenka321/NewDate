package com.saku.lmlib.views;

import android.graphics.Canvas;
import android.graphics.Paint;

public class WheelItem {
    public static final int TEXT_ALIGN_LEFT = 0x00000001;
    public static final int TEXT_ALIGN_RIGHT = 0x00000010;
    public static final int TEXT_ALIGN_CENTER_VERTICAL = 0x00000100;
    public static final int TEXT_ALIGN_CENTER_HORIZONTAL = 0x00001000;
    public static final int TEXT_ALIGN_TOP = 0x00010000;
    public static final int TEXT_ALIGN_BOTTOM = 0x00100000;
    /**
     * 默认icon和文字之间间距
     */
    public static final int MARGIN = 5;
    private Paint mPaint;
    private int mHeight;
    private float mLeft;
    private float mWidth;
    private float mTop;

    /**
     * 文本中轴线X坐标
     */
    private float textCenterX;
    /**
     * 文本baseline线Y坐标
     */
    private float textBaselineY;
    private String mText;
    /**
     * 左右边距
     */
    private int mSideMargin;

    public WheelItem(int viewWidth, int viewHeight, int sideMargin) {
        mWidth = viewWidth;
        mHeight = viewHeight;
        mSideMargin = sideMargin;
    }

    public void setPaint(Paint paint) {
        mPaint = paint;
    }

    public String getTitle() {
        return mText;
    }

    public void setTitle(String title) {
        this.mText = title;
    }

    public float getTextCenterX() {
        return textCenterX;
    }

    public void setTextCenterX(float textCenterX) {
        this.textCenterX = textCenterX;
    }

    public float getTextBaselineY() {
        return textBaselineY;
    }

    public void setTextBaselineY(float textBaselineY) {
        this.textBaselineY = textBaselineY;
    }

    /**
     * 控件的宽度
     */
    public float getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public float getLeft() {
        return mLeft;
    }

    public void setLeft(float mLeft) {
        this.mLeft = mLeft;
    }

    public float getRight() {
        return mLeft + mWidth;
    }

    public float getTop() {
        return mTop;
    }

    public void setTop(float top) {
        mTop = top;
    }

    public void draw(Canvas canvas) {
        canvas.drawText(mText, textCenterX, mTop + textBaselineY, mPaint);
    }
}
