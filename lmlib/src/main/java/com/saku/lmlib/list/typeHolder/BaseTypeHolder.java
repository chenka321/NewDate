package com.saku.lmlib.list.typeHolder;

import android.content.Context;
import android.util.SparseArray;

import com.saku.lmlib.list.viewprocessor.BaseViewProcessor;


/**
 * 处理recyclerView的type对应的viewholder，和数据绑定
 * 如果是list， 则T 应该是List< ItemData>
 */
public abstract class BaseTypeHolder<T> {
    protected Context mContext;

    protected SparseArray<BaseViewProcessor<T>> mViewPros;

    public BaseTypeHolder(Context context) {
        this.mContext = context;
        mViewPros = new SparseArray<>();
//        addViewProcessor();
    }

//    public abstract void addViewProcessor();

    public BaseViewProcessor<T> getViewProcessor(int type) {
        return mViewPros.get(type);
    }

    public SparseArray<BaseViewProcessor<T>> getViewProcessors() {
        return mViewPros;
    }

    /**
     * 遍历集合找出当前数据集position的数据对应的view类型
     */
    public int getItemType(int position, T data) {
        for (int i = 0; i < mViewPros.size(); i++) {
            final int key = mViewPros.keyAt(i);
            final BaseViewProcessor<T> viewProcessor = mViewPros.get(key);
            boolean matched = viewProcessor.matchesViewType(position, data);
            if (matched) {
                return key;
            }
        }
        throw new RuntimeException("itemModel type is wrong");
    }


}
