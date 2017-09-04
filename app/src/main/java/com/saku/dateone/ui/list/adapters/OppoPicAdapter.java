package com.saku.dateone.ui.list.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.saku.dateone.R;
import com.saku.lmlib.utils.ImageUtils;

import java.util.List;

public class OppoPicAdapter extends RecyclerView.Adapter<OppoPicAdapter.PicHolder>{
    private final List<String> mList;
    private Context mContext;

    public OppoPicAdapter(Context context, List<String> data) {
        this.mContext = context;
        this.mList = data;
    }

    @Override
    public PicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.s_opp_pic_item, parent, false);
        return new PicHolder(view);
    }

    @Override
    public void onBindViewHolder(PicHolder holder, int position) {
        ImageUtils.loadImageWithGlide(mContext, mList.get(position), 0, holder.picIv);
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class PicHolder extends RecyclerView.ViewHolder {
        private ImageView picIv;

        public PicHolder(View itemView) {
            super(itemView);
            picIv = (ImageView) itemView;
        }
    }
}
