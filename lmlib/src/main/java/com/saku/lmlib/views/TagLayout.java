package com.saku.lmlib.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


import com.saku.lmlib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Missa
 * Date: 2016-12-20
 * Time: 15:06
 * Description: 可以添加多个类textview的布局类
 */
public class TagLayout extends ViewGroup {
    public static final String TAG = TagLayout.class.getSimpleName();

    private int mGravity;  // 默认居中平分剩余space， 值是left时，不平分剩余space。
    private List<Line> mLines = new ArrayList<>();
    private Line mLine;
    private float mMarginHor;  // 两个tag间的水平间距
    private float mMarginVer;  // 两个tag间的竖直间距

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagLayout);
        mGravity = ta.getInt(R.styleable.TagLayout_android_gravity, 0);
        mMarginHor = ta.getDimension(R.styleable.TagLayout_margin_horizontal, 0);
        mMarginVer = ta.getDimension(R.styleable.TagLayout_margin_vertical, 0);
        ta.recycle();
        init(context);
    }

    private void init(Context context) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int widthActual = width - getPaddingLeft() - getPaddingRight();

        int lineWidthUsed = 0;

        mLine = new Line();
        mLines.clear();

        // 添加行数， 每个view按照包裹内容来测量，并且view之间没有间距来测量有多少行
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            view.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(500, MeasureSpec.AT_MOST));   // 不用height来测量：在listview时得到的height一直都是0，所以给定一个具体值。

            lineWidthUsed += view.getMeasuredWidth();
            lineWidthUsed += mMarginHor;
            if (lineWidthUsed >= widthActual) {
                // 换行
                lineWidthUsed = 0;
                mLines.add(mLine);
                mLine = new Line();
                mLine.addView(view);
                lineWidthUsed += view.getMeasuredWidth();
            } else {
                mLine.addView(view);
            }
        }
        mLines.add(mLine);

        //测量高度
        int resultHeight = 0;
        for (int j = 0; j < mLines.size(); j++) {
            resultHeight += mLines.get(j).lineHeight;
        }
        resultHeight += getPaddingTop() + getPaddingBottom() + (mLines.size() - 1) * mMarginVer;
//        setMeasuredDimension(width, resultHeight);
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(resultHeight, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        super.onLayout(changed, l, t, r, b);

        int top = getPaddingTop();

        for (int i = 0; i < mLines.size(); i++) {
            Line line = mLines.get(i);
            line.layout(l, top, r, top + line.getLineHeight());
            top += line.getLineHeight() + mMarginVer;
        }
    }

    class Line {
        private int lineHeight;
        private List<View> views = new ArrayList<>();

        public Line() {
        }

        public void addView(View child) {
            views.add(child);

            if (lineHeight < child.getMeasuredHeight()) {
                lineHeight = child.getMeasuredHeight();
            }
        }

        public int getLineHeight() {
            return lineHeight;
        }

        public void layout(int left, int top, int right, int bottom) {
            if (views.isEmpty()) {
                return;
            }

            if (mGravity == Gravity.LEFT) {
                // 重新测量控件
                int leftPos = getPaddingLeft();
                for (int count = 0; count < views.size(); count++) {
                    View v = views.get(count);
                    v.measure(MeasureSpec.makeMeasureSpec(v.getMeasuredWidth(), MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(v.getMeasuredHeight(), MeasureSpec.AT_MOST));
                    int heightDelta = lineHeight - v.getMeasuredHeight();
                    v.layout(leftPos, top + heightDelta / 2, leftPos + v.getMeasuredWidth(), bottom - heightDelta / 2);
                    leftPos += v.getMeasuredWidth() + mMarginHor;
                }
            } else {
                int usedWidth = 0;
                int width = right - left - getPaddingLeft() - getPaddingRight();

                // 算出包裹内容时一行剩余的宽度， 均分放到view的pading中
                for (int i = 0; i < views.size(); i++) {
                    View v = views.get(i);
                    usedWidth += v.getMeasuredWidth();
                }
                int spaceRemaining = (int) (width - usedWidth - mMarginHor * (views.size() - 1));
                int evenSpace = spaceRemaining / views.size();   // 剩余平均空隙

                // 重新测量控件
                int leftPos = getPaddingLeft();
                for (int count = 0; count < views.size(); count++) {
                    View v = views.get(count);
                    v.measure(MeasureSpec.makeMeasureSpec(v.getMeasuredWidth() + evenSpace, MeasureSpec.EXACTLY),
                            MeasureSpec.makeMeasureSpec(v.getMeasuredHeight(), MeasureSpec.AT_MOST));
                    int heightDelta = lineHeight - v.getMeasuredHeight();
                    v.layout(leftPos, top + heightDelta / 2, leftPos + v.getMeasuredWidth(), bottom - heightDelta / 2);
                    leftPos += v.getMeasuredWidth() + mMarginHor;
                }
            }
        }
    }
}
