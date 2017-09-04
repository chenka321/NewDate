package com.saku.lmlib.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.saku.lmlib.R;
import com.saku.lmlib.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

public class Wheel extends View implements
        GestureDetector.OnGestureListener {
    private static final String TAG = "Wheel";
    /**
     * 抬手滑动到目的地所需时间
     */
    private static final int DURATION = 200;

    private GestureDetector mDetector;
    /**
     * 绘制的文本大小
     */
    private int mTextSize;
    /**
     * 绘制的文本颜色
     */
    private int mTextColor;
    private List<WheelItem> mItemList;
    /**
     * item控件的上下margin值。item高度= 最大文本高度 + mMargin * 2
     */
    private int mMargin = 40;
    private Paint mPaint;
    /**
     * item控件的高度
     */
    private int mItemHeight;
    /**
     * 控件可显示的item个数
     */
    private float mVisibleCount = 5f;
    /**
     * 要显示的原始数据
     */
    private List<String> mData;
    /**
     * 选中item的y坐标
     */
    private float mCenterItemY;

    private float mTextBaselineY;

    private boolean mIsFirt = true;
    /**
     * 选中item的索引
     */
    private int mSelectedIndex;
    /**
     * 最大文本高度
     */
    private int mMaxTextHeight;
    /**
     * 小时：mSuffix=点     分钟：mSuffix=分
     */
    private String mSuffix = "";
    /**
     * item被选中回调
     */
    private OnItemChangedListener mOnItemSelectedListener;

    private FlingRunnable mRunnable;
    /**
     * 左边距
     */
    private int mLeftMargin;
    /**
     * 右边距
     */
    private int mRightMargin;

    public int getLength() {
        return mData == null ? 0 : mData.size();
    }


    public Wheel(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.Wheel);
        mTextColor = a.getColor(R.styleable.Wheel_WheelTextColor,
                Color.BLACK);
        mTextSize = a.getDimensionPixelOffset(R.styleable.Wheel_WheelTextSize, 48);

        a.recycle();

        setBackgroundColor(getResources().getColor(R.color.white));
        mDetector = new GestureDetector(getContext(), this);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);

        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);

        Paint.FontMetrics mFontMe = mPaint.getFontMetrics();
        Rect rect = new Rect();
        mPaint.getTextBounds("秦", 0, 1, rect);
        mMaxTextHeight = rect.height();
        mItemHeight = mMaxTextHeight + (mMargin << 1);

        mTextBaselineY = mItemHeight / 2 - mFontMe.descent
                + (mFontMe.descent - mFontMe.ascent) / 2;
        mLeftMargin = UIUtils.convertDpToPx(7, getContext());
        mRightMargin = mLeftMargin;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int textSize) {
        this.mTextSize = textSize;
        mPaint.setTextSize(mTextSize);
    }

    public void setSuffix(String suffix) {
        mSuffix = suffix;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
    }

    public void setOnItemSelectedListener(
            OnItemChangedListener onItemSelectedListener) {
        mOnItemSelectedListener = onItemSelectedListener;
    }

    public float getMaxTextHeight() {
        return mMaxTextHeight;
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        //滑动到合适的位置
        mSelectedIndex = selectedIndex;
        invalidate();
    }

    public List<String> getData() {
        return mData;
    }

    public void setData(List<String> data) {
        mIsFirt = true;
        mData = data;
        mSelectedIndex = 0;
        if (mRunnable != null) {
            removeCallbacks(mRunnable);
        }
        invalidate();
    }

    public void setData(List<String> data, int selectedIndex) {
        mIsFirt = true;
        mData = data;
        mSelectedIndex = selectedIndex;
        if (mRunnable != null) {
            removeCallbacks(mRunnable);
        }
        invalidate();
    }

    public String getSelectedValue() {
        if (mData == null || mData.isEmpty()) {
            return "";
        }
        return mData.get(mSelectedIndex);
    }

    /**
     * 初始化mItemList
     * 并根据mSelectedIndex item的位置，计算mSelectedIndex之前和之后元素的位置
     */
    private void initEachItemToP() {
        if (mData == null || mData.isEmpty() || getMeasuredWidth() == 0 || mSelectedIndex >= mData.size()) {
            return;
        }

        mItemList = new ArrayList<WheelItem>(mData.size());

        int width = getMeasuredWidth();
        float textCenterX = width / 2f;
        int size = mData.size();
        WheelItem item = null;


        int margin = mLeftMargin + mRightMargin;
        //mSelectedIndex之前的元素位置
        for (int i = 0; i <= mSelectedIndex - 1; i++) {
            item = new WheelItem(width, mItemHeight, margin);
            item.setTop(mCenterItemY - (mSelectedIndex - i) * mItemHeight);
            item.setTitle(mData.get(i) + mSuffix);
            item.setTextCenterX(textCenterX);
            item.setPaint(mPaint);
            item.setTextBaselineY(mTextBaselineY);
            mItemList.add(item);
        }

        for (int i = mSelectedIndex; i < size; i++) {
            item = new WheelItem(width, mItemHeight, margin);
            item.setTop(mCenterItemY + (i - mSelectedIndex) * mItemHeight);
            item.setTitle(mData.get(i) + mSuffix);
            item.setTextCenterX(textCenterX);
            item.setPaint(mPaint);
            item.setTextBaselineY(mTextBaselineY);
            mItemList.add(item);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mIsFirt) {
            mIsFirt = false;
            initEachItemToP();
        }
        if (mItemList == null) {
            return;
        }

        for (int i = 0; i < mItemList.size(); i++) {
            WheelItem item = mItemList.get(i);

            mItemList.get(i).draw(canvas);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int height = getMeasuredHeight();

        float textHeight = getMaxTextHeight();
        float startY = (height - textHeight) / 2;

        Paint paint = new Paint();
        //设置阴影颜色
        paint.setColor(getResources().getColor(R.color.wheelView_shadow));
        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (10 * density);
        //绘制上半部分矩形阴影
        canvas.drawRect(0, 0, getMeasuredWidth(), startY - margin, paint);
        //绘制下半部分矩形阴影
        canvas.drawRect(0, startY + textHeight + margin, getMeasuredWidth(), startY * 2 + textHeight, paint);

        //设置横线颜色
        paint.setColor(getResources().getColor(R.color.light_gray_s));
        //绘制上横线
        canvas.drawLine(0, startY - margin, getMeasuredWidth(), startY - margin, paint);
        //绘制下横线
        canvas.drawLine(0, startY + textHeight + margin, getMeasuredWidth(), startY + textHeight + margin, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        boolean handled = mDetector.onTouchEvent(event);
//        ULog.d(TAG, "handled = " + handled);
        switch (action) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                ULog.d(TAG, "ACTION_UP");
                if (handled) {

                } else {
                    snapToDestination();
                }
                return true;
            case MotionEvent.ACTION_MOVE:
//                ULog.d(TAG, "ACTION_MOVE");
                return true;
            case MotionEvent.ACTION_DOWN:
//                ULog.d(TAG, "ACTION_DOWN");
                return true;
        }

        return super.onTouchEvent(event);
    }

    /**
     * 抬手后，计算滑动的距离
     */
    private void snapToDestination() {
        if (mItemList == null || mItemList.isEmpty()) {
            return;
        }
        int size = mItemList.size();
        int mCenterY = getMeasuredHeight() >> 1;

        WheelItem item = null;
        float preBottom = 0;
        float delta = 0;
        for (int i = 0; i < size; i++) {
            item = mItemList.get(i);

            float top = item.getTop();
            if (top + item.getHeight() < 0) {
                continue;
            }
            if (top <= mCenterY && top + mItemHeight >= mCenterY) {
                mSelectedIndex = i;
                delta = top - mCenterItemY;
                break;
            } else if (top > mCenterY && preBottom < mCenterY) {
                if (Math.abs(top - mCenterY) > Math.abs(preBottom - mCenterY)) {
                    delta = preBottom - mItemHeight - mCenterItemY;
                    mSelectedIndex = i - 1;
                } else {
                    delta = top - mCenterItemY;
                    mSelectedIndex = i;
                }

                mSelectedIndex = i;
                break;
            } else if (i == size - 1 && top + mItemHeight <= mCenterY) {
                delta = top - mCenterItemY;
                mSelectedIndex = i;
            } else {
                preBottom = top + mItemHeight;
            }
        }
        Log.e("dd", mSelectedIndex + "");
        scrollToDestination(delta);
    }

    private void scrollToDestination(float delta) {
        mRunnable = new FlingRunnable();
        mRunnable.flingUp((int) -delta, DURATION);
    }

    /**
     * 每个item滑动delta的距离
     */
    private void scrollEacheItem(float delta) {
        if (mItemList == null) {
            return;
        }
        for (int i = 0; i < mItemList.size(); i++) {
            WheelItem item = mItemList.get(i);
            item.setTop(item.getTop() + delta);
        }

        if (delta < 0) {
            float bottomLimit = (getMeasuredHeight() - mMaxTextHeight) >> 1;
            if (mItemList.get(mItemList.size() - 1).getTop() + mItemHeight < bottomLimit) {
                restTop();
                removeCallbacks(mRunnable);
                return;
            }
        }

        if (delta > 0) {
            //下滑
            float topLimit = (getMeasuredHeight() + mMaxTextHeight) >> 1;
            if (mItemList.get(0).getTop() > topLimit) {
                //上滑
                restBottom();
                removeCallbacks(mRunnable);
                return;
            }
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
        Log.e("xxxxx", "onScroll   " + distanceY);
        handleScroll(distanceY);
        return true;
    }

    private void restTop() {
        if (mItemList == null) {
            return;
        }

        float baseLine = (getMeasuredHeight() - mMaxTextHeight) >> 1;
        float lastItemTop = baseLine - mItemHeight;
        for (int i = mItemList.size() - 1; i >= 0; i--) {
            WheelItem item = mItemList.get(i);
            item.setTop(lastItemTop);
            lastItemTop = lastItemTop - mItemHeight;
        }
        invalidate();
    }

    private void restBottom() {
        if (mItemList == null) {
            return;
        }

        float baseLine = (getMeasuredHeight() + mMaxTextHeight) >> 1;
        float lastItemTop = baseLine;
        int size = mItemList.size();
        for (int i = 0; i < size; i++) {
            WheelItem item = mItemList.get(i);
            item.setTop(lastItemTop);
            lastItemTop = lastItemTop + mItemHeight;
        }
        invalidate();
    }

    private void handleScroll(float distanceY) {
        if (mItemList == null) {
            return;
        }
        int size = mItemList.size();
        WheelItem item;

        if (distanceY > 0) {
            item = mItemList.get(size - 1);
            float bottomLimit = (getMeasuredHeight() - mMaxTextHeight) >> 1;
            if (item.getTop() + mItemHeight <= bottomLimit) {
                //最后一个item高度划出选中的上横线
                restTop();
                return;
            }
        } else {
            item = mItemList.get(0);
            float topLimit = (getMeasuredHeight() + mMaxTextHeight) >> 1;
            if (item.getTop() >= topLimit) {
                //第一个item top 滑出选中的上=下横线
                restBottom();
                return;
            }
        }

        //每个item的移动-distanceY的距离
        for (int i = 0; i < size; i++) {
            item = mItemList.get(i);
            item.setTop(item.getTop() - distanceY);
        }
        invalidate();
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {
        // TODO Auto-generated method stub
        // e1：第1个ACTION_DOWN MotionEvent
        // e2：最后一个ACTION_MOVE MotionEvent
        // velocityX：X轴上的移动速度（像素/秒）
        // velocityY：Y轴上的移动速度（像素/秒）

        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒
        //向
        if (e1.getY() - e2.getY() > 100
                && Math.abs(velocityY) > 100) {

        }
        float dealta = e1.getY() - e2.getY();
        if (Math.abs(dealta) > 50
                && Math.abs(velocityX) > 200) {
            if (dealta * 2 < (mData.size() - mSelectedIndex - 1) * mItemHeight) {
                handleScroll(dealta);
            }
        }
        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;

        // Get measureSpec mode and size values.
        final int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
//        final int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
//        final int measureHeight = MeasureSpec.getSize(heightMeasureSpec);

        // The RangeBar width should be as large as possible.
        if (measureWidthMode == MeasureSpec.AT_MOST) {
            width = measureWidth;
        } else if (measureWidthMode == MeasureSpec.EXACTLY) {
            width = measureWidth;
        } else {
            width = measureWidth;
        }

        float height = mItemHeight * mVisibleCount;
        setMeasuredDimension(width, (int) (height));

        mCenterItemY = (height - mItemHeight) / 2f;
    }

    /**
     * <p>在末尾添加List</p>
     * <ul>当前选中的@link mSelectedIndex  小于添加List的长度 则直接添加在末尾</ul>
     * <ul>当前选中的@link mSelectedIndex  超过添加List的长度  则移除前面等长的List 再添加到末尾</ul>
     *
     * @param endList
     */
    public void setDataAtEnd(List<String> endList) {
        if (endList == null || endList.isEmpty()) {
            return;
        }
        if (mData == null) {
            mData = endList;
        } else {
            int endListLength = endList.size();
            if (mSelectedIndex < endListLength) {
                mData.addAll(endList);
            } else {
                int length = mData.size();
                mData.addAll(endList);
                mData = mData.subList(endListLength, length + endListLength);
                mSelectedIndex -= endListLength;
            }
        }
        setData(mData, mSelectedIndex);
        setSelectedIndex(mSelectedIndex);
    }

    /**
     * <p>在头部添加List</p>
     * <ul>当前选中的@link mSelectedIndex  未显示完的长度小于添加List的长度 则直接添加在开头</ul>
     * <ul>当前选中的@link mSelectedIndex  超过添加List的长度  则移除index后面等长的List 再添加到末尾</ul>
     *
     * @param startList
     */
    public void setDataAtStart(List<String> startList) {
        if (startList == null || startList.isEmpty()) {
            return;
        }
        if (mData == null) {
            mData = startList;
        } else {
            int startListLength = startList.size();
            if (mData.size() - mSelectedIndex < startListLength) {
                mData.addAll(0, startList);
            } else {
                mData.addAll(0, startList);
                mData = mData.subList(0, mData.size() - startListLength);
                mSelectedIndex += startListLength;
            }
        }
        setData(mData, mSelectedIndex);
        setSelectedIndex(mSelectedIndex);
    }

    public interface OnItemChangedListener {
        void onItemChanged(int index);
    }


    public class FlingRunnable implements Runnable {
        private Scroller mScroller;
        private int mLastFingY;

        public FlingRunnable() {
            mScroller = new Scroller(getContext());
        }

        @Override
        public void run() {
            boolean flag = mScroller.computeScrollOffset();
            if (flag) {
                int delta = mScroller.getCurrY() - mLastFingY;
                scrollEacheItem(delta);
                invalidate();
                mLastFingY = mScroller.getCurrY();
                post(this);
            } else {
                removeCallbacks(this);
                if (mOnItemSelectedListener != null) {
                    mOnItemSelectedListener.onItemChanged(mSelectedIndex);
                }
            }
        }

        private void flingUp(int distance, int duration) {
            removeCallbacks(this);
            mLastFingY = 0;
            mScroller.startScroll(0, 0, 0, distance, duration);
            post(this);
        }
    }
}
