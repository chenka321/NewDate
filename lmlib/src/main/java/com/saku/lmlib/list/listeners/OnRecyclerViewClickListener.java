package com.saku.lmlib.list.listeners;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * User: liumin
 * Date: 2017-7-15
 * Time: 18:40
 * Description: RecyclerView点击事件的实现类，避免写onclick方法
*/
public class OnRecyclerViewClickListener implements View.OnClickListener {

    private RecyclerView.ViewHolder mHolder;
    private OnRecyclerClickCallBack mCallBack;

    public OnRecyclerViewClickListener(RecyclerView.ViewHolder holder, OnRecyclerClickCallBack cb) {
        this.mHolder = holder;
        this.mCallBack = cb;
    }

    @Override
    public void onClick(View v) {
        if(mCallBack == null ||
                mHolder == null || mHolder.getAdapterPosition() < 0) {
            return;
        }
        mCallBack.onClick(mHolder.getAdapterPosition(), v);
    }

    public <T extends RecyclerView.ViewHolder> void setHolder(T holder) {
        mHolder = holder;
    }
}
