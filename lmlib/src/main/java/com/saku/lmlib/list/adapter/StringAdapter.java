package com.saku.lmlib.list.adapter;

import com.saku.lmlib.list.typeHolder.BaseTypeHolder;

import java.util.List;

/**
 * list的数据类型是string的
 */
public class StringAdapter extends BaseCarAdapter<List<String>> {

    public StringAdapter(List<String> data, BaseTypeHolder<List<String>> typeHolder) {
        super(data, typeHolder);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }
}
