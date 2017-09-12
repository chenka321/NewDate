package com.saku.dateone.utils;

import android.content.Context;

import com.saku.dateone.bean.Dict;
import com.saku.lmlib.dialog.DialogHelper;
import com.saku.lmlib.dialog.OneColumnPickerDialog;

import java.util.List;

/**
 * Created by liumin on 2017/9/6.
 */

public class DialogUtils {
    private DialogHelper dialogHelper = new DialogHelper();


    public void popSingleColumnDialog(Context context, List<Dict> source, Dict current,
                                      OneColumnPickerDialog.SelectListener<Dict> listener) {
        dialogHelper.showSingleListDialog(context, source, current, listener);
    }

}
