package com.saku.lmlib.list.adapter;

import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.typeHolder.BaseTypeHolder;

import java.util.List;

/**
 * User: liumin
 * Date: 2017-7-31
 * Time: 19:43
 * Description: list类型的recyclerView的adapter类
*/
public class BaseListAdapter extends BaseCarAdapter<List<ItemData>> {

    public BaseListAdapter(List<ItemData> data, BaseTypeHolder<List<ItemData>> typeHolder) {
        super(data, typeHolder);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<ItemData> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

}
