package com.saku.dateone.ui.list.adapters;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

public class FrontPageAdapter extends RecyclerView.Adapter<FrontPageAdapter.VHolder> {

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class VHolder extends ViewHolder{

        public VHolder(View itemView) {
            super(itemView);
        }
    }
}
