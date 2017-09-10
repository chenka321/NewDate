package com.saku.dateone.ui.list.viewprocessors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.ui.bean.TagString;
import com.saku.dateone.ui.list.data.RecommendItemData;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.list.listeners.OnRecyclerViewClickListener;
import com.saku.lmlib.list.viewprocessor.ListViewPorcessor;
import com.saku.lmlib.utils.ImageUtils;
import com.saku.lmlib.utils.ShowUtils;
import com.saku.lmlib.utils.TvCacheManager;
import com.saku.lmlib.utils.UIUtils;
import com.saku.lmlib.views.TagLayout;

import java.util.List;

public class RecommendVProcessor extends ListViewPorcessor<RecommendVProcessor.VHolder, RecommendItemData, ItemData> {

    private OnRecyclerClickCallBack itemListener;
    private Context mContext;
    private int tagMargin;
    private TvCacheManager<TagString, List<TagString>> tvCacheManager;
    private int tagPadding;
    private int tagTop;

    public RecommendVProcessor(Context context, OnRecyclerClickCallBack listener) {
        itemListener = listener;
        mContext = context;
    }

    @Override
    public VHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.s_recommend_item, parent, false);

        setUpTvCacheManager();
        tagMargin = UIUtils.convertDpToPx(5, mContext);
        tagPadding = UIUtils.convertDpToPx(3, mContext);
        tagTop = UIUtils.convertDpToPx(1, mContext);
        final VHolder holder = new VHolder(view);
        holder.itemView.setOnClickListener(new OnRecyclerViewClickListener(holder, itemListener));
//        Log.d("lm", "onCreateViewHolder: itemListener = " + itemListener + " , viewholder = " + holder);
        return holder;
    }

    @Override
    public boolean matchesViewType(int position, List<ItemData> data, ItemData item) {
        return item instanceof RecommendItemData;
    }

    @Override
    public void onBindViewHolder(VHolder viewHolder, List<ItemData> mData, int position, RecommendItemData frontPageData) {
//        itemListener.setHolder(viewHolder);

        ImageUtils.loadImageWithGlide(mContext, frontPageData.userImg, 0, viewHolder.userIv);
        StringBuilder sb = new StringBuilder();
        sb.append(frontPageData.name).append("  ")
                .append(frontPageData.birthday).append(" ")
                .append(frontPageData.ocupation);

        final SpannableStringBuilder ssb = ShowUtils.setTextSizeSpan(sb.toString(), 14,
                TextUtils.isEmpty(frontPageData.name) ? 0 : frontPageData.name.length(), sb.toString().length());
        viewHolder.nameAgeOccupationTv.setText(ssb);

        viewHolder.locationTv.setText(viewHolder.locationTv.getText().toString().concat(frontPageData.currentLocation));
        viewHolder.residenceLocTv.setText(viewHolder.residenceLocTv.getText().toString().concat(frontPageData.bornLocation));

        tvCacheManager.showTextView(frontPageData.tags, viewHolder.tagsTl);
    }


    private void setUpTvCacheManager() {
        tvCacheManager = new TvCacheManager<TagString, List<TagString>>(mContext) {
            @Override
            public void setTextView(TagString s, TextView textView) {
                textView.setText(s.text);
                GradientDrawable tagBg = new GradientDrawable();
                tagBg.setShape(GradientDrawable.RECTANGLE);
                tagBg.mutate();
                tagBg.setCornerRadius(tagMargin);
                try {
                    tagBg.setColor(Color.parseColor(s.rgbValue));
                } catch (Exception e) {
                    Log.e("lm", e.getMessage());
                }
                if (Build.VERSION.SDK_INT < 16) {
                    textView.setBackgroundDrawable(tagBg);
                } else {
                    textView.setBackground(tagBg);
                }
            }
        };

        tvCacheManager.setMargin(tagMargin, 0, 0, 0)
                .setTextColor(Color.WHITE)
                .setTextSize(14)
                .setPadding(tagPadding, tagTop, tagPadding, tagTop);
    }


    public class VHolder extends RecyclerView.ViewHolder {
        public ImageView userIv;
        public TextView nameAgeOccupationTv;
        public TextView residenceLocTv;
        public TextView locationTv;
        public TagLayout tagsTl;

        public VHolder(View itemView) {
            super(itemView);

            this.userIv = (ImageView) itemView.findViewById(R.id.user_iv);
            this.nameAgeOccupationTv = (TextView) itemView.findViewById(R.id.name_age_occupation_tv);
            this.residenceLocTv = (TextView) itemView.findViewById(R.id.residence_loc_tv);
            this.locationTv = (TextView) itemView.findViewById(R.id.location_tv);
            this.tagsTl = (TagLayout) itemView.findViewById(R.id.tags_ll);
        }
    }
}
