package com.saku.dateone.ui.list.typeholders;

import android.content.Context;

import com.saku.dateone.ui.list.viewprocessors.PicsVProcessor;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.list.typeHolder.BaseTypeHolder;

import java.util.List;

public class StringTypeHolder extends BaseTypeHolder<List<String>> {

    public StringTypeHolder(Context context, OnRecyclerClickCallBack itemClick) {
        super(context);
        mViewPros.put(1, new PicsVProcessor(mContext, itemClick));

    }


}
