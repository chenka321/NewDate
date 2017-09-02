package com.saku.lmlib.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

/**
 * User: liumin
 * Date: 2017-7-18
 * Time: 18:56
 * Description: viewgroup 动态添加textview
*/
public abstract class TvCacheManager<D, T extends List<D>> {

    private final Context mContext;
    private final T list;
    private LinkedList<TextView> mViewCaches;
    private int textSize;
    private int color;
    private int paddingTop;
    private int paddingBottom;
    private int paddingLeft;
    private int paddingRight;
    private int marginRight;
    private int marginTop;
    private int marginBottom;
    private int marginLeft;

    public TvCacheManager(Context context) {
        this(context, null);
    }

    public TvCacheManager(Context context, T list) {
        mContext = context;
        this.list = list;
    }

    public void showTextView(T list, ViewGroup parent) {
        if (list == null || list.size() == 0) {
            return;
        }
        final int tvCount = parent.getChildCount();
        TextView tv;

        if (list.size() < tvCount) {
            for (int i = 0; i < tvCount; i++) {
                if (i < list.size()) {
                    tv = (TextView) parent.getChildAt(i);
//                    tv.setText(list.get(i));
                    setTextView(list.get(i), tv);
                } else {
                    TextView tvRemoved = (TextView) parent.getChildAt(list.size());
                    parent.removeViewAt(list.size());
                    add2Caches(tvRemoved, mViewCaches);
                }
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (i < tvCount) {
                    tv = (TextView) parent.getChildAt(i);
                } else {
                    tv = ventDescTextView();
                    parent.addView(tv);
                }
                setTextView(list.get(i), tv);
            }
        }
    }

    private void add2Caches(TextView tvRemoved, LinkedList<TextView> mViewCaches) {
        if (mViewCaches == null) {
            mViewCaches = new LinkedList<>();
        }
        mViewCaches.add(tvRemoved);
    }

    private TextView ventDescTextView() {
        if (mViewCaches == null) {
            mViewCaches = new LinkedList<>();
        }
        if (mViewCaches.size() > 0) {
            return mViewCaches.remove();
        }
        TextView tv = new TextView(mContext);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize == 0 ? 11 : textSize);
        tv.setTextColor(color == 0 ? Color.parseColor("#666666") : color);
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setMaxLines(1);
//        tv.setGravity(Gravity.CENTER);
        final LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.topMargin = marginTop;
        params.leftMargin = marginLeft;
        params.rightMargin = marginRight;
        params.bottomMargin = marginBottom;
        tv.setLayoutParams(params);
        tv.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);

        return tv;
    }

    public TvCacheManager<D, T> setPadding(int paddingTop, int paddingBottom, int paddingLeft, int paddingRight) {
        this.paddingTop = paddingTop;
        this.paddingBottom = paddingBottom;
        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        return this;
    }

    public TvCacheManager<D, T> setMargin(int marginTop, int marginBottom, int marginLeft, int marginRight) {
        this.marginTop = marginTop;
        this.marginBottom = marginBottom;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        return this;
    }

    /**
     *
     * @param size sp数值
     */
    public TvCacheManager<D, T> setTextSize(int size) {
        textSize = size;
        return this;
    }

    public TvCacheManager<D, T> setTextColor(int color) {
        this.color = color;
        return this;
    }

    /**
     * 设置textview的文字、颜色、字体、背景等
     * @param d  显示的文字数据源
     */
    public abstract void setTextView(D d, TextView textView);

}
