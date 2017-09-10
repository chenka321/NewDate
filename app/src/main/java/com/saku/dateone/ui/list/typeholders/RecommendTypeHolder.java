package com.saku.dateone.ui.list.typeholders;

import android.content.Context;

import com.saku.dateone.ui.list.viewprocessors.RecommendVProcessor;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.list.typeHolder.BaseTypeHolder;

import java.util.List;

public class RecommendTypeHolder extends BaseTypeHolder<List<ItemData>> {

    public RecommendTypeHolder(Context context, OnRecyclerClickCallBack listener) {
        super(context);

        mViewPros.put(1, new RecommendVProcessor(mContext, listener));
    }


}
