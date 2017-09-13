package com.saku.lmlib.dialog;

import android.content.Context;
import android.widget.Toast;

import com.saku.lmlib.model.SingleWheelData;
import com.saku.lmlib.utils.LLog;

import java.util.List;

public class DialogHelper {

    private BirthDayPickerDialog birthDayDiaolog;

    public <T extends SingleWheelData> void showSingleListDialog(Context context,
                                                                 List<T> displayList,
                                                                 T current,
                                                                 OneColumnPickerDialog.SelectListener<T> confirmListener) {
        LLog.d("searchSources: " + displayList);
        if (displayList == null || displayList.size() == 0) {
            Toast.makeText(context, "没有数据源！", Toast.LENGTH_SHORT).show();
            return;
        }
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
                    public void onSelect(OneColumnPickerDialog dialog, T type) {
                        dialog.dismiss();
                    }

                }).build().show();
    }


    public <T extends SingleWheelData> void showBirthdayPicker(Context context,
                                                                 long selectTime, String title,
                                                                 BirthDayPickerDialog.DateCallback confirmL) {
        LLog.d("selectTime: " + selectTime);

        if (birthDayDiaolog != null && birthDayDiaolog.isShowing()) {
            birthDayDiaolog.dismiss();
        }
        birthDayDiaolog = new BirthDayPickerDialog(context, selectTime, "", confirmL, null);
        birthDayDiaolog.show();
    }

}
