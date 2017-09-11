package com.saku.dateone.ui.list.viewprocessors;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.bean.MyMsg;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.list.listeners.OnRecyclerViewClickListener;
import com.saku.lmlib.list.viewprocessor.ListViewPorcessor;
import com.saku.lmlib.utils.DateUtils;

import java.util.List;

public class MyMsgVProcessor extends ListViewPorcessor<MyMsgVProcessor.VHolder, MyMsg, ItemData> {

    private OnRecyclerClickCallBack itemListener;
    private Context mContext;

    public MyMsgVProcessor(Context context, OnRecyclerClickCallBack itemClick) {
        itemListener = itemClick;
        mContext = context;
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.s_my_msg_item, parent, false);
        final VHolder holder = new VHolder(view);
        holder.itemView.setOnClickListener(new OnRecyclerViewClickListener(holder, itemListener));
        return holder;
    }

    @Override
    public boolean matchesViewType(int position, List<ItemData> data, ItemData item) {
        return item instanceof  MyMsg;
    }

    @Override
    public void onBindViewHolder(VHolder viewHolder, List<ItemData> mData, int position, MyMsg myMsg) {
        viewHolder.titleTv.setText(myMsg.content);
        viewHolder.timeTv.setText(DateUtils.long2Date(myMsg.time));
    }

    public class VHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public TextView timeTv;

        public VHolder(View rootView) {
            super(rootView);
            this.titleTv = (TextView) rootView.findViewById(R.id.msg_title_tv);
            this.timeTv = (TextView) rootView.findViewById(R.id.msg_time_tv);
        }

    }
}
