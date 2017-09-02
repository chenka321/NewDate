package com.saku.lmlib.list.viewprocessor;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * @param <I> list data数据的具体类型
 * @param <D> list data数据的接口类型
 */
public abstract class ListViewPorcessor<VH extends RecyclerView.ViewHolder, I, D> implements BaseViewProcessor<List<D>> {

    public abstract boolean matchesViewType(int position, List<D> data, D item);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, List<D> mData, int position) {
        onBindViewHolder((VH) viewHolder, mData, position, (I) mData.get(position));
    }

    public abstract void onBindViewHolder(VH viewHolder, List<D> mData, int position, I i);

    @Override
    public boolean matchesViewType(int position, List<D> data) {
        if (null == data
                || data.isEmpty()
                || position < 0
                || position >= data.size()) {
            return false;
        }
        return matchesViewType(position, data, data.get(position));
    }

}