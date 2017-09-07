package com.saku.dateone.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.saku.dateone.R;
import com.saku.dateone.ui.bean.Type;
import com.saku.dateone.ui.contracts.CompleteInfoContract;
import com.saku.dateone.ui.presenters.CompleteInfoPresenter;
import com.saku.dateone.utils.TypeManager;
import com.saku.lmlib.dialog.DialogHelper;
import com.saku.lmlib.dialog.OneColumnPickerDialog;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

import java.util.List;

/**
 * Created by liumin on 2017/8/15.
 */

/**
 * User: liumin
 * Date: 2017-9-4
 * Time: 16:43
 * Description: 基础信息
 */
public class SimpleInfoActivity extends BaseActivity<CompleteInfoPresenter> implements View.OnClickListener,
        CompleteInfoContract.V {

    private TextView schoolTv;
    private EditText schoolNameEt;
    private Button matchBtn;
    private DialogHelper dialogHelper;
    private Type mSchoolType;
    private EditText inputNameEt;
    private TextView inputEducationTv;
    private TextView inputBirthdayTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        showTitle(true);
        setTitle("补充信息");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  // 隐藏软键盘
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(this).inflate(R.layout.s_input_user_info_activity, mRoot, false);
    }

    @Override
    protected CompleteInfoPresenter getPresenter() {
        return new CompleteInfoPresenter(this);
    }

    private void initView() {
        this.inputNameEt = (EditText) findViewById(R.id.input_name_et);
        this.inputEducationTv = (TextView) findViewById(R.id.input_education_tv);
        this.inputBirthdayTv = (TextView) findViewById(R.id.input_birthday_tv);
        this.matchBtn = (Button) findViewById(R.id.match_btn);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void chooseDegree() {

    }

    private boolean checkOnSubmit() {
        boolean fillAll = true;
//        // validate
//        String et = heightEt.getText().toString().trim();
//        if (TextUtils.isEmpty(et)) {
//            Toast.makeText(this, "请填入身高, 单位（厘米）", Toast.LENGTH_SHORT).show();
//            return fillAll |= false;
//        }
//
//        String tv = occupationTv.getText().toString().trim();
//        if (TextUtils.isEmpty(tv)) {
//            Toast.makeText(this, "请填入职位", Toast.LENGTH_SHORT).show();
//            return fillAll |= false;
//        }
//
//        String income = incomeEt.getText().toString().trim();
//        if (TextUtils.isEmpty(income)) {
//            Toast.makeText(this, "请填入收入", Toast.LENGTH_SHORT).show();
//            return fillAll |= false;
//
//        }
//
//        String estate = estateLocEt.getText().toString().trim();
//        if (TextUtils.isEmpty(estate)) {
//            Toast.makeText(this, "请填入房产位置", Toast.LENGTH_SHORT).show();
//            return fillAll |= false;
//
//        }

//        String moreInfo = moreInfoEt.getText().toString().trim();
//        if (TextUtils.isEmpty(moreInfo)) {
//            Toast.makeText(this, "请输入你的宝贵意见", Toast.LENGTH_SHORT).show();
//            return fillAll |= false;
//        }
        return true;
    }

    @Override
    public void onClick(View v) {
        UIUtils.hideSoftKeyboard(this, v);
        if (dialogHelper == null) {
            dialogHelper = new DialogHelper();
        }
        switch (v.getId()) {
//            case R.id.input_company_tv:
//                dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getCompanyTypes(), mCurrComType, new OneColumnPickerDialog.SelectListener<Type>() {
//                    @Override
//                    public void onSelect(OneColumnPickerDialog dialog, Type typeValue) {
//                        mCurrComType = typeValue;
//                        companyTv.setText(typeValue.textShowing);
//                        dialog.dismiss();
//                    }
//                });
//                break;
//            case R.id.input_estate_tv:
//                dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getHouses(), mCurrEstateType, new OneColumnPickerDialog.SelectListener<Type>() {
//                    @Override
//                    public void onSelect(OneColumnPickerDialog dialog, Type typeValue) {
//                        mCurrEstateType = typeValue;
//                        estateTv.setText(typeValue.textShowing);
//                        dialog.dismiss();
//                    }
//                });
//                break;
//            case R.id.input_car_tv:
//                dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getCars(), mCurrCarType, new OneColumnPickerDialog.SelectListener<Type>() {
//                    @Override
//                    public void onSelect(OneColumnPickerDialog dialog, Type typeValue) {
//                        mCurrCarType = typeValue;
//                        carTv.setText(typeValue.textShowing);
//                        dialog.dismiss();
//                    }
//                });
//                break;
//            case R.id.input_school_tv:
//                dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getSchoolTypes(), mSchoolType, new OneColumnPickerDialog.SelectListener<Type>() {
//                    @Override
//                    public void onSelect(OneColumnPickerDialog dialog, Type typeValue) {
//                        mSchoolType = typeValue;
//                        schoolTv.setText(typeValue.textShowing);
//                        dialog.dismiss();
//                    }
//                });
//
//                break;
            case R.id.upload_pic_btn:
                break;
            case R.id.match_btn:

                if (checkOnSubmit()) {
                    LLog.d("fill all");
                    mPresenter.onStartMatchClicked();
                }

                break;
        }
    }

    /**
     * 弹出type类型的选择框
     *
     * @param sourceTypes 数据源
     * @param currType    当前选择的type
     * @param showTv      要展示选择的textview
     * @return 选择的type
     */
    private Type showDialog(List<Type> sourceTypes, Type currType, final TextView showTv) {
        final Type[] newSelType = new Type[1];
        dialogHelper.showSingleListDialog(this, sourceTypes, currType, new OneColumnPickerDialog.SelectListener<Type>() {
            @Override
            public void onSelect(OneColumnPickerDialog dialog, Type typeValue) {
                newSelType[0] = typeValue;
                showTv.setText(typeValue.textShowing);
                dialog.dismiss();
            }
        });
        return newSelType.length > 0 ? newSelType[0] : null;
    }

    private void submit() {
        // validate
//        String et = input_name_et.getText().toString().trim();
//        if (TextUtils.isEmpty(et)) {
//            Toast.makeText(this, "请填入姓名", Toast.LENGTH_SHORT).show();
//            return;
//        }

        // TODO validate success, do something


    }

}
