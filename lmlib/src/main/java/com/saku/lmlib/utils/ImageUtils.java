package com.saku.lmlib.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.saku.lmlib.R;

public class ImageUtils {

    public static void loadImageWithGlide(Context context, String imageUrl, int placeHolderResId, ImageView iv) {
//        final Drawable drawable = ContextCompat.getDrawable(context, R.drawable.my_logo);
        if (0 == placeHolderResId) {
            placeHolderResId = R.drawable.my_logo;
        }
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .error(placeHolderResId)
                .placeholder(placeHolderResId)
                .into(iv);
    }
}
