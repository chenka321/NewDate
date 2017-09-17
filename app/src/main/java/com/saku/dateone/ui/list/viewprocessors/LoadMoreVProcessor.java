package com.saku.dateone.ui.list.viewprocessors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saku.dateone.R;
import com.saku.dateone.ui.list.data.EmptyData;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.viewprocessor.ListViewPorcessor;

import java.util.List;

public class LoadMoreVProcessor extends ListViewPorcessor<LoadMoreVProcessor.LoadMoreViewHolder, EmptyData, ItemData> {

    private Context mContext;

    public LoadMoreVProcessor(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.s_load_more_item, parent, false);

        return new LoadMoreViewHolder(view);
    }

    @Override
    public boolean matchesViewType(int position, List<ItemData> data, ItemData item) {
        return item instanceof EmptyData;
    }

    @Override
    public void onBindViewHolder(LoadMoreViewHolder viewHolder, List<ItemData> mData, int position, EmptyData emptyData) {

    }

    class LoadMoreViewHolder extends RecyclerView.ViewHolder {

        public LoadMoreViewHolder(View itemView) {
            super(itemView);
        }
    }
}
