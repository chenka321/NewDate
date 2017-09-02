package com.saku.lmlib.list.itemdecoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SpaceDividerDecoration extends RecyclerView.ItemDecoration {
    private final int spacing;
    private Paint mPaint;

    public SpaceDividerDecoration(int spacing) {
        this.spacing = spacing;
//        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mPaint.setColor(ContextCompat.getColor(context, R.color.rtc_cg_1));
//        mPadding = context.getResources().getDimensionPixelSize(R.dimen.rtc_carlist_byhour_margin_left);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//        if (parent.getChildViewHolder(view) instanceof BasicInfoViewHolder &&
//                parent.getChildAdapterPosition(view) != 0) {
//            outRect.top = spacing;
//        } else {
//            outRect.top = 0;
//        }
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = 0;
        } else {
            outRect.top = spacing;
        }
    }

    Rect outRect = new Rect();

//    @Override
//    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
//        final int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            final View child = parent.getChildAt(i);
//            final float translationY = child.getTranslationY();
//            final RecyclerView.ViewHolder holder = parent.getChildViewHolder(child);
//            final int itemViewType = holder.getItemViewType();
//            switch (itemViewType) {
//                case 5: // 时间地址卡片
////                    mPaint.setColor(Color.YELLOW);
//
//                    c.drawRect(0, 0,
//                            parent.getWidth(),
//                            child.getBottom() + translationY + 60,
//                            mPaint);
//                    break;
//                case 4:  // bottom
////                    mPaint.setColor(Color.RED);
//                    c.drawRect(0,
//                            child.getTop() + translationY,
//                            parent.getWidth(),
//                            child.getBottom() + translationY + 30,
//                            mPaint);
//                    if (parent.getChildAdapterPosition(child) == parent.getChildCount() - 1) {
////                        DLog.d("lm", "child.getBottom()= " + child.getBottom() + " , parent.bottom = " + parent.getBottom());
//                        c.drawRect(0,
//                                parent.getBottom() - 300,
//                                parent.getWidth(),
//                                parent.getBottom() + translationY,
//                                mPaint);
//                    }
//                    break;
//                case 1:  //TYPE_BASE_INFO
////                    mPaint.setColor(Color.BLUE);
//                    getItemOffsets(outRect, child, parent, state);
//                    if (outRect.top != 0) {
//                        c.drawRect(0,
//                                child.getTop() - outRect.top + translationY,
//                                parent.getWidth(),
//                                child.getTop() + translationY,
//                                mPaint);
//                    }
//                    if (parent.getChildAdapterPosition(child) == 0 || parent.getChildAdapterPosition(child) == 1) {
////                        mPaint.setColor(Color.CYAN);
//                        c.drawRect(0,
//                                0,
//                                parent.getWidth(),
//                                child.getTop() + translationY + 12,
//                                mPaint);
//                    }
//                    break;
//            }
//        }
//    }
}
