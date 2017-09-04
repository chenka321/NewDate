package com.saku.lmlib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.saku.lmlib.R;
import com.saku.lmlib.views.Wheel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * User: liumin
 * Date: 2017-9-4
 * Time: 18:12
 * Description: 三列日期滚轮选择器
*/
public class DatePickerDialog extends Dialog {
    private final long selectedTime;
    private String mTitleString;
    private DateCallback mDateCallback;
    private DateCallback mCancelCallback;

    private Context mContext;

    private Wheel mYearPicker;
    private Wheel mMonthPicker;
    private Wheel mDayPicker;

    private Calendar mCurrentCalendar;

    private int[] years;
    private int[] months;
    private int[] days;
    private Calendar mOriginCalendar;

    public DatePickerDialog(Context context, long selectedTime, String title, DateCallback callback, DateCallback cancelCallback) {
        super(context, R.style.PopDialog);
        setCancelable(false);
        mContext = context;

        this.mCancelCallback = cancelCallback;
        mTitleString = title;
        mDateCallback = callback;
        mCancelCallback = cancelCallback;
        this.selectedTime = selectedTime;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_timepicker_pop);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.yes).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mDateCallback == null) {
                            return;
                        }
                        final int yearIndex = mYearPicker.getSelectedIndex();
                        final int monthIndex = mMonthPicker.getSelectedIndex();
                        final int dayIndex = mDayPicker.getSelectedIndex();

                        int year = years[yearIndex];
                        int month = months[monthIndex];
                        int day = days[dayIndex];
                        final Calendar result = Calendar.getInstance();
                        result.set(year, month - 1, day, 0, 0, 0);
                        dismiss();
                        mDateCallback.callBackDate(result.getTimeInMillis());
                    }
                });
        findViewById(R.id.cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                        if (mCancelCallback != null) {
                            mCancelCallback.callBackDate(0);
                        }
                    }
                });
        RelativeLayout pickerOutsideLayout = (RelativeLayout) findViewById(R.id.time_picker_layout);
        pickerOutsideLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (mCancelCallback != null) {
                    mCancelCallback.callBackDate(0);
                }
            }
        });
        TextView mTvwTitle = (TextView) findViewById(R.id.title);
        mTvwTitle.setText(mTitleString);

        mYearPicker = (Wheel) findViewById(R.id.day_picker);
        mYearPicker.setOnItemSelectedListener(new Wheel.OnItemChangedListener() {
            @Override
            public void onItemChanged(int i) {
                mCurrentCalendar.set(Calendar.YEAR, years[mYearPicker.getSelectedIndex()]);
                initMonthData();
                mCurrentCalendar.set(Calendar.MONTH, months[mMonthPicker.getSelectedIndex()] - 1);
                initDayData();
            }
        });

        mMonthPicker = (Wheel) findViewById(R.id.hour_picker);
        mMonthPicker.setOnItemSelectedListener(new Wheel.OnItemChangedListener() {
            @Override
            public void onItemChanged(int i) {
                mCurrentCalendar.set(Calendar.MONTH, months[mMonthPicker.getSelectedIndex()] - 1);
                initDayData();
            }
        });

        mDayPicker = (Wheel) findViewById(R.id.minute_picker);
    }

    private void initData() {
        mOriginCalendar = Calendar.getInstance();

        mCurrentCalendar = Calendar.getInstance();
        mCurrentCalendar.setTimeInMillis(selectedTime);
        initYearData();
        initMonthData();
        initDayData();
        final Calendar selectCalendar = Calendar.getInstance();
        selectCalendar.setTimeInMillis(selectedTime);
        final int selectYear = selectCalendar.get(Calendar.YEAR);
        for (int i = 0; i < years.length; i++) {
            if (years[i] == selectYear) {
                mYearPicker.setSelectedIndex(i);
                break;
            }
        }
        final int selectMonth = selectCalendar.get(Calendar.MONTH) + 1;
        for (int i = 0; i < months.length; i++) {
            if (months[i] == selectMonth) {
                mMonthPicker.setSelectedIndex(i);
                break;
            }
        }
        final int selectDay = selectCalendar.get(Calendar.DAY_OF_MONTH);
        for (int i = 0; i < days.length; i++) {
            if (days[i] == selectDay) {
                mDayPicker.setSelectedIndex(i);
                break;
            }
        }
    }

    private void initYearData() {
        years = new int[20];
        int year = mOriginCalendar.get(Calendar.YEAR);
        ArrayList<String> yearList = new ArrayList<>(years.length);
        for (int i = 0; i < years.length; i++) {
            years[i] = year + i;
            yearList.add(mContext.getString(R.string.year_format, years[i]));
        }
        mYearPicker.setData(yearList);
    }

    private void initMonthData() {
        int month;
        if (mOriginCalendar.get(Calendar.YEAR) == mCurrentCalendar.get(Calendar.YEAR)) { // 当前的月之前的月份不展示
            month = mOriginCalendar.get(Calendar.MONTH) + 1;
            months = new int[mOriginCalendar.getActualMaximum(Calendar.MONTH) + 1 - month + 1];
        } else {
            month = 1;
            months = new int[12];
        }
        ArrayList<String> monthList = new ArrayList<>(months.length);
        for (int i = 0; i < months.length; i++) {
            months[i] = month + i;
            if (months[i] >= 10) {
                monthList.add(mContext.getString(R.string.month_format, months[i]));
            } else {
                monthList.add(mContext.getString(R.string.month_format_pre_9, months[i]));
            }
        }
        mMonthPicker.setData(monthList);
    }

    private void initDayData() {
        int day;
        if (mOriginCalendar.get(Calendar.YEAR) == mCurrentCalendar.get(Calendar.YEAR)
                && mOriginCalendar.get(Calendar.MONTH) == mCurrentCalendar.get(Calendar.MONTH)) {   // 当前的日期之前的不展示
            day = mOriginCalendar.get(Calendar.DAY_OF_MONTH);
            days = new int[mOriginCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) - day + 1];
        } else {
            day = 1;
            days = new int[mCurrentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)];
        }
        ArrayList<String> dayList = new ArrayList<>(days.length);
        for (int i = 0; i < days.length; i++) {
            days[i] = day + i;
            if (days[i] >= 10) {
                dayList.add(mContext.getString(R.string.day_format, days[i]));
            } else {
                dayList.add(mContext.getString(R.string.day_format_pre_9, days[i]));

            }
        }
        mDayPicker.setData(dayList);
    }

    public interface DateCallback {
        void callBackDate(long time);
    }
}
