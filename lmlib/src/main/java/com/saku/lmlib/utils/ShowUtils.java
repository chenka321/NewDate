package com.saku.lmlib.utils;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

/**
 * User: liumin
 * Date: 2017-9-1
 * Time: 16:37
 * Description: 视觉展示相关的
*/
public class ShowUtils {

    /**
     * 字体大小Span, 相对
     * @param proportion 比例 0~1
     */
    public static SpannableStringBuilder setTextSizeSpan(CharSequence text, float proportion, int from, int to) {
        SpannableStringBuilder ss1 = new SpannableStringBuilder(text);
        ss1.setSpan(new RelativeSizeSpan(proportion), from, to, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss1;
    }

    /**
     * 字体大小Span, 绝对
     */
    public static SpannableStringBuilder setTextSizeSpan(CharSequence text, int size, int from, int to) {
        SpannableStringBuilder ss1 = new SpannableStringBuilder(text);
        ss1.setSpan(new AbsoluteSizeSpan(size, true), from, to, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss1;
    }
    /**
     * 字体颜色Span
     */
    public static SpannableStringBuilder setColorSpan(CharSequence text, int color, int from, int to) {
        SpannableStringBuilder ss1 = new SpannableStringBuilder(text);
        ss1.setSpan(new ForegroundColorSpan(color), 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return ss1;
    }

}
