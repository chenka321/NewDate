package com.saku.lmlib.list.viewprocessor;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * 处理recyclerView的adapter对应的view绑定，数据展示等的基类
 */
public interface BaseViewProcessor<T> {
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, T mData, int position);

    public boolean matchesViewType(int position, T data);
}
