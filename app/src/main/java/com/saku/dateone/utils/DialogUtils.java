package com.saku.dateone.utils;

import android.content.Context;

import com.saku.dateone.ui.bean.Type;
import com.saku.lmlib.dialog.DialogHelper;
import com.saku.lmlib.dialog.OneColumnPickerDialog;
import com.saku.lmlib.model.SingleWheelData;

import java.util.List;

/**
 * Created by liumin on 2017/9/6.
 */

public class DialogUtils {
    private DialogHelper dialogHelper = new DialogHelper();


    public void popSingleColumnDialog(Context context, List<Type> source, Type current,
                                      OneColumnPickerDialog.SelectListener<Type> listener) {
        dialogHelper.showSingleListDialog(context, source, current, listener);
    }

}
