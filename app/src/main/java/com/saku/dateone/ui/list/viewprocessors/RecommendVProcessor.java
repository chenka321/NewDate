package com.saku.dateone.ui.list.viewprocessors;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.bean.TagString;
import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.utils.TypeManager;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.list.listeners.OnRecyclerViewClickListener;
import com.saku.lmlib.list.viewprocessor.ListViewPorcessor;
import com.saku.lmlib.utils.ImageUtils;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.ShowUtils;
import com.saku.lmlib.utils.TvCacheManager;
import com.saku.lmlib.utils.UIUtils;
import com.saku.lmlib.views.TagLayout;

import java.util.Calendar;
import java.util.List;

public class RecommendVProcessor extends ListViewPorcessor<RecommendVProcessor.VHolder, UserInfo, ItemData> {

    public static final int TYPE_RECOMMEND = 1;   // 推荐信息
    public static final int TYPE_COLLECTION = 2;  // 收藏信息
    private OnRecyclerClickCallBack itemListener;
    private Context mContext;
    private int tagMargin;
    private TvCacheManager<TagString, List<TagString>> tvCacheManager;
    private int tagPadding;
    private int tagTop;
    private int which;

    /**
     * @param which 1： 推荐列表， 2： 收藏列表
     */
    public RecommendVProcessor(Context context, int which, OnRecyclerClickCallBack itemClick) {
        itemListener = itemClick;
        mContext = context;
        this.which = which;
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
        return holder;
    }

    @Override
    public boolean matchesViewType(int position, List<ItemData> data, ItemData item) {
        return item instanceof UserInfo;
    }

    @Override
    public void onBindViewHolder(VHolder viewHolder, List<ItemData> mData, int position, UserInfo frontPageData) {
//        itemListener.setHolder(viewHolder);

        ImageUtils.loadImageWithGlide(mContext, frontPageData.userImage, 0, viewHolder.userIv);
        int age = 0;
        if (!TextUtils.isEmpty(frontPageData.birthday) && frontPageData.birthday.length() > 4) {
            String birthYear = frontPageData.birthday.substring(0, 4);
            final int thisYearInt = Calendar.getInstance().get(Calendar.YEAR);
            final int birthYearInt = Integer.parseInt(birthYear);
            age = thisYearInt - birthYearInt;
        }
        StringBuilder sb = new StringBuilder();

        sb.append(frontPageData.name).append("  ");
        if (age > 0) {
            sb.append("" + age).append("  ");
        }
        final String education = TypeManager.getInstance().getMapValue(TypeManager.getInstance().getEducationMap(), frontPageData.education);
        if (!TextUtils.isEmpty(education)) {
            sb.append(education);
        }

        final SpannableStringBuilder ssb = ShowUtils.setTextSizeSpan(sb.toString(), 14,
                TextUtils.isEmpty(frontPageData.name) ? 0 : frontPageData.name.length(), sb.toString().length());
        viewHolder.nameAgeOccupationTv.setText(ssb);

        viewHolder.locationTv.setText(mContext.getString(R.string.currentLocation, frontPageData.currentLocation));
        viewHolder.residenceLocTv.setText(mContext.getString(R.string.residence, frontPageData.bornLocation));
        tvCacheManager.showTextView(frontPageData.label, viewHolder.tagsTl);

        if (which == 2) {
            viewHolder.collectionTv.setVisibility(View.VISIBLE);
            viewHolder.nameAgeOccupationTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);

        } else {
            viewHolder.collectionTv.setVisibility(View.GONE);
            viewHolder.nameAgeOccupationTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
            viewHolder.nameAgeOccupationTv.setCompoundDrawablePadding(15);
        }

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
                    LLog.e("lm", e.getMessage());
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
        public final TextView collectionTv;

        public VHolder(View itemView) {
            super(itemView);

            this.userIv = (ImageView) itemView.findViewById(R.id.user_iv);
            this.nameAgeOccupationTv = (TextView) itemView.findViewById(R.id.name_age_occupation_tv);
            this.residenceLocTv = (TextView) itemView.findViewById(R.id.residence_loc_tv);
            this.locationTv = (TextView) itemView.findViewById(R.id.location_tv);
            this.tagsTl = (TagLayout) itemView.findViewById(R.id.tags_ll);
            this.collectionTv = (TextView) itemView.findViewById(R.id.collection_tv);
        }
    }
}
