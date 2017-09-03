package com.saku.lmlib.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;

import com.saku.lmlib.dialog.CommonDialog;
import com.saku.lmlib.dialog.CustomDialog;


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


    public static void showTwoBtnDialog(Context context, int resId, String title, String content,
                                        String confirmTxt, String cancelTxt,
                                        CommonDialog.OnCloseListener confirmL, CommonDialog.OnCloseListener cancelL) {
        new CommonDialog.Builder(context)
                .cancelTouchout(true)
                .setIcon(resId)
                .setTitle(title)
                .setContent(content)
                .setNegativeButton(cancelTxt)
                .setPositiveButton(confirmTxt)
                .setConfirmListener(confirmL)
                .setCancelListener(cancelL)
                .build()
                .show();
    }


    public static void showOneBtnDialog(Context context, int resId, String title, String content,
                                        String confirmTxt, CommonDialog.OnCloseListener confirmL) {
        new CommonDialog.Builder(context)
                .cancelTouchout(true)
                .setIcon(resId)
                .setTitle(title)
                .setContent(content)
                .setPositiveButton(confirmTxt)
                .setConfirmListener(confirmL)
                .build()
                .show();
    }


}
