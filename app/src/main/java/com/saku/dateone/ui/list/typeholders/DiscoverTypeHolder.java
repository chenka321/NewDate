package com.saku.dateone.ui.list.typeholders;

import android.content.Context;

import com.saku.dateone.ui.list.viewprocessors.DiscoverVProcessor;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.list.typeHolder.BaseTypeHolder;

import java.util.List;

public class DiscoverTypeHolder extends BaseTypeHolder<List<ItemData>> {

    public DiscoverTypeHolder(Context context, OnRecyclerClickCallBack itemClick) {
        super(context);
        mViewPros.put(1, new DiscoverVProcessor(mContext, itemClick));
    }
}
