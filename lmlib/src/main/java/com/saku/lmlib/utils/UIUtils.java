package com.saku.lmlib.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.EditText;

public class UIUtils {

    public static int convertDpToPx(float dp, Context context) {
        if (context == null)
            return 0;
        final Resources resources = context.getResources();
        final DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        return (int) (dp * displayMetrics.density + 0.5);
    }

    public static boolean isEditTextEmpty(EditText et) {
//        if (TextUtils.isEmpty(et.getText().toString())) {
//            Toast.makeText(this, "et不能为空", Toast.LENGTH_SHORT).show();
//            return ;
//        }
        return TextUtils.isEmpty(et.getText().toString());
    }

}
