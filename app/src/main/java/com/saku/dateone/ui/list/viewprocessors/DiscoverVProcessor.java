package com.saku.dateone.ui.list.viewprocessors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.bean.Article;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.list.listeners.OnRecyclerViewClickListener;
import com.saku.lmlib.list.viewprocessor.ListViewPorcessor;
import com.saku.lmlib.utils.ImageUtils;

import java.util.List;

public class DiscoverVProcessor extends ListViewPorcessor<DiscoverVProcessor.VHolder, Article, ItemData> {

    private OnRecyclerClickCallBack itemListener;
    private Context mContext;

    public DiscoverVProcessor(Context context, OnRecyclerClickCallBack itemClick) {
        itemListener = itemClick;
        mContext = context;
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.s_discover_item, parent, false);
        final VHolder holder = new VHolder(view);
        holder.itemView.setOnClickListener(new OnRecyclerViewClickListener(holder, itemListener));
        return holder;
    }

    @Override
    public boolean matchesViewType(int position, List<ItemData> data, ItemData item) {
        return item instanceof Article;
    }

    @Override
    public void onBindViewHolder(VHolder viewHolder, List<ItemData> mData, int position, Article article) {
        viewHolder.discoverTitleTv.setText(article.title);
        viewHolder.discoverDescTv.setText(article.content);
        ImageUtils.loadImageWithGlide(mContext, article.image, 0, viewHolder.discoverIv);
    }

    public static class VHolder extends RecyclerView.ViewHolder {
        public ImageView discoverIv;
        public TextView discoverTitleTv;
        public TextView discoverDescTv;

        public VHolder(View itemView) {
            super(itemView);

            this.discoverIv = (ImageView) itemView.findViewById(R.id.discover_iv);
            this.discoverTitleTv = (TextView) itemView.findViewById(R.id.discover_title_tv);
            this.discoverDescTv = (TextView) itemView.findViewById(R.id.discover_desc_tv);
        }

    }
}
