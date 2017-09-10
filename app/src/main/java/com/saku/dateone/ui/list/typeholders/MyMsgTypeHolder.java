package com.saku.dateone.ui.list.typeholders;

import android.content.Context;

import com.saku.dateone.ui.list.viewprocessors.MyMsgVProcessor;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.list.typeHolder.BaseTypeHolder;

import java.util.List;

public class MyMsgTypeHolder extends BaseTypeHolder<List<ItemData>> {

    public MyMsgTypeHolder(Context context, OnRecyclerClickCallBack itemListener) {
        super(context);

        mViewPros.put(1, new MyMsgVProcessor(mContext, itemListener));
    }


}
