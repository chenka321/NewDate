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
import com.saku.dateone.bean.Dict;
import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.ui.contracts.CompleteInfoContract;
import com.saku.dateone.ui.presenters.CompleteInfoPresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.TypeManager;
import com.saku.dateone.utils.UserInfoManager;
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
    private TextView companyTypeTv;
    private EditText occupationTv;
    private TextView incomeTv;
    private TextView estateTv;
    private EditText estateLocEt;
    private TextView carTv;
    private TextView schoolTv;
    private EditText schoolNameEt;
//    private TextView educationTv;
    private GridView hobbyGv;
    private EditText moreInfoEt;
    private RecyclerView uploadPicRv;
    private Button matchBtn;
    private Button uploadPicBtn;
    private DialogHelper dialogHelper;
    private Dict mCurrComDict;
    private Dict mCurrIncomeDict;
    private Dict mCurrHouseDict;
    private Dict mCurrCarDict;
    private Dict mSchoolDict;
//    private Dict mEducationDict;

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
        companyTypeTv = (TextView) findViewById(R.id.input_company_tv);
        occupationTv = (EditText) findViewById(R.id.input_position_et);
        incomeTv = (TextView) findViewById(R.id.input_income_tv);
        estateTv = (TextView) findViewById(R.id.input_estate_tv);
//        estateLocEt = (EditText) findViewById(R.id.input_estate_loc_et);
        carTv = (TextView) findViewById(R.id.input_car_tv);
        schoolTv = (TextView) findViewById(R.id.input_school_tv);
        schoolNameEt = (EditText) findViewById(R.id.input_school_name_et);
//        educationTv = (TextView) findViewById(R.id.input_degree_tv);
        hobbyGv = (GridView) findViewById(R.id.hobby_gv);
        moreInfoEt = (EditText) findViewById(R.id.more_info_et);
        uploadPicRv = (RecyclerView) findViewById(R.id.upload_pic_rv);
        uploadPicBtn = (Button) findViewById(R.id.upload_pic_btn);
        matchBtn = (Button) findViewById(R.id.match_btn);

        companyTypeTv.setOnClickListener(this);
        estateTv.setOnClickListener(this);
        carTv.setOnClickListener(this);
        schoolTv.setOnClickListener(this);
        incomeTv.setOnClickListener(this);
//        educationTv.setOnClickListener(this);
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
            return false;
        }

        String tv = occupationTv.getText().toString().trim();
        if (TextUtils.isEmpty(tv)) {
            Toast.makeText(this, "请填入职位", Toast.LENGTH_SHORT).show();
            return false;
        }

        String income = incomeTv.getText().toString().trim();
        if (TextUtils.isEmpty(income)) {
            Toast.makeText(this, "请选择收入", Toast.LENGTH_SHORT).show();
            return false;
        }

//        String estate = estateLocEt.getText().toString().trim();
//        if (TextUtils.isEmpty(estate)) {
//            Toast.makeText(this, "请填入房产位置", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (TextUtils.isEmpty(companyTypeTv.getText())) {
            Toast.makeText(this, "请填入选择单位性质", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(estateTv.getText())) {
            Toast.makeText(this, "请填入选择房产", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(carTv.getText())) {
            Toast.makeText(this, "请填入选择车辆情况", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(schoolTv.getText())) {
            Toast.makeText(this, "请填入选择学校情况", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(incomeTv.getText())) {
            Toast.makeText(this, "请填入选择收入", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (TextUtils.isEmpty(educationTv.getText())) {
//            Toast.makeText(this, "请填入选择学历", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        final UserInfo pendingInfo = UserInfoManager.getInstance().getMyPendingInfo();
        try {
            pendingInfo.height = Integer.valueOf(et);
        } catch (NumberFormatException e) {
            pendingInfo.height = 0;
        }
        pendingInfo.position = tv;
//        pendingInfo.income = TypeManager.getInstance().getMapKey(TypeManager.getInstance().getIncomeMap(), income);
//        pendingInfo.estateLocation = estate;
//        pendingInfo.company = TypeManager.getInstance().getMapKey(TypeManager.getInstance().getCompanyTypeMap(),
//                companyTypeTv.getText().toString());
//        pendingInfo.house = TypeManager.getInstance().getMapKey(TypeManager.getInstance().getHousesMap(),
//                estateTv.getText().toString());
//        pendingInfo.car = TypeManager.getInstance().getMapKey(TypeManager.getInstance().getCarsMap(),
//                carTv.getText().toString());
//        pendingInfo.schoolType = TypeManager.getInstance().getMapKey(TypeManager.getInstance().getSchoolTypesMap(),
//                schoolTv.getText().toString());
        pendingInfo.house = mCurrHouseDict.id;
        pendingInfo.car = mCurrCarDict.id;
        pendingInfo.schoolType = mSchoolDict.id;
        pendingInfo.income = mCurrIncomeDict.id;
        pendingInfo.company = mCurrComDict.id;
//        pendingInfo.education = mEducationDict.id;
        pendingInfo.school = schoolTv.getText().toString();
        pendingInfo.moreIntroduce = moreInfoEt.getText().toString();

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
                showCompanyPicker();
                break;
            case R.id.input_estate_tv:
                showHousePicker();
                break;
            case R.id.input_car_tv:
                showCarPicker();
                break;
            case R.id.input_school_tv:
                showSchoolPicker();
                break;
            case R.id.input_income_tv:
                showIncomePicker();
                break;
//            case R.id.input_degree_tv:
//                showEducationPicker();
//                break;
            case R.id.upload_pic_btn:
                break;
            case R.id.match_btn:

                if (checkOnSubmit()) {
                    LLog.d("fill all");
                    mPresenter.onMatchCompleteClicked();
                }
                break;
        }
    }
//
//    private void showEducationPicker() {
//        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().education,
//                mEducationDict, new OneColumnPickerDialog.SelectListener<Dict>() {
//                    @Override
//                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
//                        mEducationDict = type;
//                        educationTv.setText(type.textShowing);
//                        dialog.dismiss();
//                    }
//                });
//    }

    private void showIncomePicker() {
        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().income,
                mCurrIncomeDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mCurrIncomeDict = type;
                        schoolTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
    }

    private void showSchoolPicker() {
        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().schoolType,
                mCurrCarDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mSchoolDict = type;
                        schoolTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
    }

    private void showCarPicker() {
        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().car,
                mCurrCarDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mCurrCarDict = type;
                        carTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
    }

    private void showHousePicker() {
        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().house,
                mCurrHouseDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mCurrHouseDict = type;
                        estateTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
    }

    private void showCompanyPicker() {
        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().companyType,
                mCurrComDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mCurrComDict = type;
                        companyTypeTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
    }

    @Override
    public void goNextOnCompleteInfo() {

        if (getIntent() != null) {
            Bundle b = new Bundle();
            b.putInt(Consts.SHOW_MAIN_TAB_PAGE, PageManager.RECOMMEND_LIST);
//            final int fromPage = getIntent().getIntExtra(Consts.COMPLETE_FROM_PAGE_NAME, 0);
//            if (fromPage == PageManager.COMPLETE_INFO_MY_SIMPLE_INFO) {
//                b.putInt(Consts.REFRESH_RECOMMEND, Consts.REFRESH_RECOMMEND_NOT_LOGIN);
//
//            } else if (fromPage == PageManager.COMPLETE_INFO_MINE_MSG
//                    || fromPage == PageManager.COMPLETE_INFO_MINE_MODIFY) {
//                b.putInt(Consts.REFRESH_RECOMMEND, Consts.REFRESH_RECOMMEND_LOGIN);
//            }

            toActivity(MainTabsActivity.class, b, true);
        }
    }

}
