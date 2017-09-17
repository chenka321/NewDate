package com.saku.dateone.ui.list.typeholders;

import android.content.Context;

import com.saku.dateone.ui.list.viewprocessors.RecommendVProcessor;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.list.typeHolder.BaseTypeHolder;

import java.util.List;

public class RecommendTypeHolder extends BaseTypeHolder<List<ItemData>> {

    public static final int USERINFO = 1;

    /**
     * @param which  1： 推荐列表， 2： 收藏列表
     */
    public RecommendTypeHolder(Context context, int which, OnRecyclerClickCallBack itemListener) {
        super(context);

        mViewPros.put(USERINFO, new RecommendVProcessor(mContext, which, itemListener));
    }


}
