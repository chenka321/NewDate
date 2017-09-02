package com.saku.lmlib.list.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.saku.lmlib.list.typeHolder.BaseTypeHolder;
import com.saku.lmlib.list.viewprocessor.BaseViewProcessor;

/**
 * User: liumin
 * Date: 2017-7-31
 * Time: 19:42
 * Description: recyclerView的adapter抽象类
*/
public abstract class BaseCarAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected T mData;
    protected BaseTypeHolder<T> mTypeHolder;

    public BaseCarAdapter(T data, BaseTypeHolder<T> typeHolder) {
        this.mData = data;
        this.mTypeHolder = typeHolder;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseViewProcessor<T> vp = mTypeHolder.getViewProcessor(viewType);
        return vp.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        final BaseViewProcessor<T> vp = mTypeHolder.getViewProcessor(viewType);
        vp.onBindViewHolder(holder, mData, position);
    }

    @Override
    public int getItemViewType(int position) {
       return mTypeHolder.getItemType(position, mData);
    }

}
