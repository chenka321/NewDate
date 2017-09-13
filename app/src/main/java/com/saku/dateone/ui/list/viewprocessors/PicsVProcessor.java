package com.saku.dateone.ui.list.viewprocessors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.saku.dateone.R;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.list.listeners.OnRecyclerViewClickListener;
import com.saku.lmlib.list.viewprocessor.ListViewPorcessor;
import com.saku.lmlib.utils.ImageUtils;

import java.util.List;

/**
 * 相册选择照片展示
 */
public class PicsVProcessor extends ListViewPorcessor<PicsVProcessor.VHolder, String, String> {

    private OnRecyclerClickCallBack itemListener;
    private Context mContext;

    public PicsVProcessor(Context context, OnRecyclerClickCallBack itemClick) {
        itemListener = itemClick;
        mContext = context;
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.s_pics_item, parent, false);
        final VHolder holder = new VHolder(view);
        holder.itemView.setOnClickListener(new OnRecyclerViewClickListener(holder, itemListener));
        return holder;
    }

    @Override
    public boolean matchesViewType(int position, List<String> data, String item) {
        return true;
    }

    @Override
    public void onBindViewHolder(VHolder viewHolder, List<String> mData, int position, String s) {
        ImageUtils.loadImageWithGlide(mContext, s, R.drawable.add_pic_default, viewHolder.picIv);
    }


    public class VHolder extends RecyclerView.ViewHolder {
        public ImageView picIv;

        public VHolder(View itemView) {
            super(itemView);

            this.picIv = (ImageView) itemView.findViewById(R.id.more_pic);
        }
    }
}
