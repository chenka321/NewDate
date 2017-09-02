package com.saku.dateone.ui.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saku.dateone.R;

/**
 * User: liumin
 * Date: 2017-8-29
 * Time: 17:25
 * Description: 标题栏
*/
public class TitleLayout extends LinearLayout {
    private TextView mTitleLeftTv;
    private TextView mTitleTv;
    private TextView mTitleRightTv;
    private ImageView mTitleRightIv;

    public TitleLayout(Context context) {
        super(context);
        init(context);
    }

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TitleLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public TitleLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        final View view = LayoutInflater.from(context).inflate(R.layout.s_titlebar, this, true);

        mTitleLeftTv = (TextView) view.findViewById(R.id.title_left_tv);
        mTitleTv = (TextView) view.findViewById(R.id.title_tv);
        mTitleRightTv = (TextView) view.findViewById(R.id.title_right_tv);
        mTitleRightIv = (ImageView) view.findViewById(R.id.title_right_iv);

        mTitleRightTv.setVisibility(View.GONE);
        mTitleRightIv.setVisibility(View.GONE);
        mTitleLeftTv.setVisibility(GONE);
    }

    public void showLeftBtn(boolean show) {
        mTitleLeftTv.setVisibility(show ? VISIBLE : GONE);
    }

    public void setTitleContent(String title) {
        mTitleTv.setText(title);
    }

    /**
     * 返回按钮的点击事件
     */
    public void setLeftClickListener(OnClickListener listener) {
        mTitleLeftTv.setOnClickListener(listener);
    }

    public void setRightTitle(String text) {
        mTitleRightTv.setVisibility(View.VISIBLE);
        mTitleRightTv.setText(text);
    }

    public void setRightIv(int resId) {
        mTitleRightIv.setVisibility(View.VISIBLE);
        mTitleRightIv.setImageResource(resId);
    }

}
