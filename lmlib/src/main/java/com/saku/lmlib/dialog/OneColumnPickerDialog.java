package com.saku.lmlib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.saku.lmlib.R;
import com.saku.lmlib.model.SingleWheel;
import com.saku.lmlib.views.Wheel;

import java.util.ArrayList;
import java.util.List;


/**
 *  单列滚轮选择弹框
 */
public class OneColumnPickerDialog extends Dialog {
    private OneColumnPickerDialog(Context context) {
        super(context);
    }

    private OneColumnPickerDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    private OneColumnPickerDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public interface SelectListener<D extends SingleWheel> {
        void onSelect(D typeValue);
    }

    public static class Builder<T extends SingleWheel> extends BaseDialogBuilder<Builder> {
        private String mTitleString;

        private TextView mTvwTitle;
        private SelectListener mConfirmListenr;
        private SelectListener mCancelCallback;
        private List<T> mTypeList = new ArrayList<>();

        private Wheel mPicker;

        private RelativeLayout pickerOutsideLayout;
        private int mSelectedIndex;

        public Builder(Context context) {
            super(context);
            v = inflater.inflate(R.layout.dialog_single_pop, null, false);
            initView(v);
        }

        public Builder setConfirmListener(SelectListener callBack) {
            this.mConfirmListenr = callBack;
            return this;
        }

        public Builder setCancelListener(SelectListener callBack) {
            this.mCancelCallback = callBack;
            return this;
        }

        public Builder setTitle(String title) {
            this.mTvwTitle.setText(title);
            return this;
        }

        public Builder setData(List<T> list) {
            mTypeList = list;
            return this;
        }

        public Builder setSelectedIndex(int index) {
            mSelectedIndex = index;
            return this;
        }

        public OneColumnPickerDialog build() {
            mDialog = new OneColumnPickerDialog(mContext, R.style.PopDialog);
            initData();
            if (null != v) {
                mDialog.setContentView(v);
            }
            mDialog.setCancelable(false);
            return (OneColumnPickerDialog) mDialog;
        }

        /**
         * 初始化View 绑定ID 和 点击事件
         *
         * @param rootView
         */
        private void initView(View rootView) {
            rootView.findViewById(R.id.equipment_yes).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (handleTimeSelected()) {
                                mDialog.dismiss();
                            }
                        }
                    });
            rootView.findViewById(R.id.equipment_cancel).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                            if (mCancelCallback != null) {
                                mCancelCallback.onSelect(null);
                            }

                        }
                    });
            pickerOutsideLayout = (RelativeLayout) rootView.findViewById(R.id.picker_outside_layout);
            mTvwTitle = (TextView) rootView.findViewById(R.id.equipment_title);
            mPicker = (Wheel) rootView.findViewById(R.id.single_wheel);
            mPicker.setOnItemSelectedListener(new Wheel.OnItemChangedListener() {
                @Override
                public void onItemChanged(int i) {
                }
            });

            pickerOutsideLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog.dismiss();
                    if (mCancelCallback != null) {
                        mCancelCallback.onSelect(null);
                    }
                }
            });
        }

        /**
         * 处理选中时间
         */
        private boolean handleTimeSelected() {
            mPicker.getSelectedValue();
            if (mConfirmListenr != null) {
                int position = mPicker.getSelectedIndex();
                T selectedType = mTypeList.get(position);
                mConfirmListenr.onSelect(selectedType);
            }
            return true;
        }

        List<String> list = new ArrayList<>();

        private void initData() {
            for (T type : mTypeList) {
                list.add(type.textShowing);
            }
            mPicker.setData(list);
            if (mSelectedIndex < mTypeList.size()) {
                mPicker.setSelectedIndex(mSelectedIndex);
            }
        }
    }
}
