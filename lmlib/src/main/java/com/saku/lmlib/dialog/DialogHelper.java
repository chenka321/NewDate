package com.saku.lmlib.dialog;

import android.content.Context;

import com.saku.lmlib.model.SingleWheelData;
import com.saku.lmlib.utils.LLog;

import java.util.List;

public class DialogHelper {
    //    public void showSingleListDialog(List<? extends SingleWheelData> displayList,
//                                     OneColumnPickerDialog.SelectListener<? extends SingleWheelData> confirmListener) {
    public <T extends SingleWheelData> void showSingleListDialog(Context context, List<T> displayList, T current,
                                                                 OneColumnPickerDialog.SelectListener<T> confirmListener) {
        LLog.d("searchSources: " + displayList);
        int selectedIndex = 0;
        if (current != null && displayList.contains(current)) {
            selectedIndex = displayList.indexOf(current);
        }
        OneColumnPickerDialog.Builder<T> builder = new OneColumnPickerDialog.Builder<>(context);
        builder.setData(displayList)
                .setSelectedIndex(selectedIndex)
                .setConfirmListener(confirmListener)
                .setCancelListener(new OneColumnPickerDialog.SelectListener<T>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, T typeValue) {
                        dialog.dismiss();
                    }

                }).build().show();
    }
}
