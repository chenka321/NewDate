package com.saku.lmlib.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.saku.lmlib.R;

public class SingleColumnView extends LinearLayout{
    public SingleColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(getContext()).inflate(R.layout.single_column_picker, this);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        int height = getMeasuredHeight();

        Wheel picker = (Wheel) getChildAt(0);
        float textHeight = picker.getMaxTextHeight();
        float startY = (height - textHeight) / 2;

        Paint paint = new Paint();
        //设置阴影颜色
        paint.setColor(getResources().getColor(R.color.wheelView_shadow));
        //绘制上半部分矩形阴影
        canvas.drawRect(0, 0, getMeasuredWidth(), startY, paint);
        //绘制下半部分矩形阴影
        canvas.drawRect(0, startY + textHeight, getMeasuredWidth(), startY * 2 + textHeight, paint);

        float density = getResources().getDisplayMetrics().density;
        int margin = (int) (10 * density);
        //设置横线颜色
        paint.setColor(getResources().getColor(R.color.light_gray_s));
        //绘制上横线
        canvas.drawLine(0, startY - margin, getMeasuredWidth(), startY - margin, paint);
        //绘制下横线
        canvas.drawLine(0, startY + textHeight + margin, getMeasuredWidth(), startY + textHeight + margin, paint);
    }
}
