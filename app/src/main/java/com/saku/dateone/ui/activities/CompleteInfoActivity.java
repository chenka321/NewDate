package com.saku.dateone.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
 * Description: 补充信息
 */
public class CompleteInfoActivity extends BaseActivity<CompleteInfoPresenter> implements View.OnClickListener,
        CompleteInfoContract.V {

    private EditText heightEt;
    private TextView companyTv;
    private EditText occupationTv;
    private EditText incomeEt;
    private TextView estateTv;
    private EditText estateLocEt;
    private TextView carTv;
    private TextView schoolTv;
    private EditText schoolNameEt;
    private GridView hobbyGv;
    private EditText moreInfoEt;
    private RecyclerView uploadPicRv;
    private Button matchBtn;
    private Button uploadPicBtn;
    private DialogHelper dialogHelper;
    private Type mCurrComType;
    private Type mCurrEstateType;
    private Type mCurrCarType;
    private Type mSchoolType;

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
        return LayoutInflater.from(this).inflate(R.layout.s_input_user_info_more_activity, mRoot, false);
    }

    @Override
    protected CompleteInfoPresenter getPresenter() {
        return new CompleteInfoPresenter(this);
    }

    private void initView() {
        heightEt = (EditText) findViewById(R.id.input_height_et);
        companyTv = (TextView) findViewById(R.id.input_company_tv);
        occupationTv = (EditText) findViewById(R.id.input_position_et);
        incomeEt = (EditText) findViewById(R.id.input_income_et);
        estateTv = (TextView) findViewById(R.id.input_estate_tv);
        estateLocEt = (EditText) findViewById(R.id.input_estate_loc_et);
        carTv = (TextView) findViewById(R.id.input_car_tv);
        schoolTv = (TextView) findViewById(R.id.input_school_tv);
        schoolNameEt = (EditText) findViewById(R.id.input_school_name_et);
        hobbyGv = (GridView) findViewById(R.id.hobby_gv);
        moreInfoEt = (EditText) findViewById(R.id.more_info_et);
        uploadPicRv = (RecyclerView) findViewById(R.id.upload_pic_rv);
        uploadPicBtn = (Button) findViewById(R.id.upload_pic_btn);
        matchBtn = (Button) findViewById(R.id.match_btn);

        companyTv.setOnClickListener(this);
        estateTv.setOnClickListener(this);
        carTv.setOnClickListener(this);
        schoolTv.setOnClickListener(this);
        matchBtn.setOnClickListener(this);
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
        // validate
        String et = heightEt.getText().toString().trim();
        if (TextUtils.isEmpty(et)) {
            Toast.makeText(this, "请填入身高, 单位（厘米）", Toast.LENGTH_SHORT).show();
            return fillAll |= false;
        }

        String tv = occupationTv.getText().toString().trim();
        if (TextUtils.isEmpty(tv)) {
            Toast.makeText(this, "请填入职位", Toast.LENGTH_SHORT).show();
            return fillAll |= false;
        }

        String income = incomeEt.getText().toString().trim();
        if (TextUtils.isEmpty(income)) {
            Toast.makeText(this, "请填入收入", Toast.LENGTH_SHORT).show();
            return fillAll |= false;

        }

        String estate = estateLocEt.getText().toString().trim();
        if (TextUtils.isEmpty(estate)) {
            Toast.makeText(this, "请填入房产位置", Toast.LENGTH_SHORT).show();
            return fillAll |= false;

        }

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
            case R.id.input_company_tv:
                dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getCompanyTypes(), mCurrComType, new OneColumnPickerDialog.SelectListener<Type>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Type typeValue) {
                        mCurrComType = typeValue;
                        companyTv.setText(typeValue.textShowing);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.input_estate_tv:
                dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getHouses(), mCurrEstateType, new OneColumnPickerDialog.SelectListener<Type>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Type typeValue) {
                        mCurrEstateType = typeValue;
                        estateTv.setText(typeValue.textShowing);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.input_car_tv:
                dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getCars(), mCurrCarType, new OneColumnPickerDialog.SelectListener<Type>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Type typeValue) {
                        mCurrCarType = typeValue;
                        carTv.setText(typeValue.textShowing);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.input_school_tv:
                dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getSchoolTypes(), mSchoolType, new OneColumnPickerDialog.SelectListener<Type>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Type typeValue) {
                        mSchoolType = typeValue;
                        schoolTv.setText(typeValue.textShowing);
                        dialog.dismiss();
                    }
                });

                break;
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

}
