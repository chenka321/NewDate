package com.saku.lmlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.saku.lmlib.dialog.CommonDialog;


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
                                        String confirmTxt, boolean canTouchOut, CommonDialog.OnCloseListener confirmL) {
        new CommonDialog.Builder(context)
                .cancelTouchout(true)
                .setIcon(resId)
                .setTitle(title)
                .setContent(content)
                .setCanCancelOut(canTouchOut)
                .setShowOneBtn(true)
                .setPositiveButton(confirmTxt)
                .setConfirmListener(confirmL)
                .build()
                .show();
    }

    /**
     * 隐藏edittext上的软键盘
     */
    public static void hideSoftKeyboard(Activity activity, EditText et) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
    }

    /**
     * 隐藏页面上的软键盘
     */
    public static void hideSoftKeyboard(Activity activity, View view) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            LLog.d("hideSoftKeyboard is active");
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * @return 是否显示Toast
     */
    public static boolean showToast(Context context, String text, String errorText) {
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public static void showToast(Context context, String errorText) {
            Toast.makeText(context, errorText, Toast.LENGTH_SHORT).show();
    }
}
