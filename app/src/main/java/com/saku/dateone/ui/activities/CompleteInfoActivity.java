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
import com.saku.dateone.ui.contracts.CompleteInfoContract;
import com.saku.dateone.ui.presenters.CompleteInfoPresenter;
import com.saku.lmlib.utils.UIUtils;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        showTitle(true);
        setTitle("补充信息");

        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  // 隐藏软键盘
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
        occupationTv = (EditText) findViewById(R.id.input_occupation_et);
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

    private void checkOnSubmit() {
        // validate
        String et = heightEt.getText().toString().trim();
        if (TextUtils.isEmpty(et)) {
            Toast.makeText(this, "请填入身高, 单位（厘米）", Toast.LENGTH_SHORT).show();
            return;
        }

        String tv = occupationTv.getText().toString().trim();
        if (TextUtils.isEmpty(tv)) {
            Toast.makeText(this, "请填入职位", Toast.LENGTH_SHORT).show();
            return;
        }

        String income = incomeEt.getText().toString().trim();
        if (TextUtils.isEmpty(income)) {
            Toast.makeText(this, "请填入收入", Toast.LENGTH_SHORT).show();
            return;
        }

        String estate = estateLocEt.getText().toString().trim();
        if (TextUtils.isEmpty(estate)) {
            Toast.makeText(this, "请填入房产位置", Toast.LENGTH_SHORT).show();
            return;
        }

        String moreInfo = moreInfoEt.getText().toString().trim();
        if (TextUtils.isEmpty(moreInfo)) {
            Toast.makeText(this, "请输入你的宝贵意见", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    @Override
    public void onClick(View v) {
        UIUtils.hideSoftKeyboard(this, v);
        switch (v.getId()) {
            case R.id.input_company_tv:
                break;
            case R.id.input_estate_tv:
                break;
            case R.id.input_car_tv:
                break;
            case R.id.input_school_tv:
                break;
            case R.id.upload_pic_btn:
                break;
            case R.id.match_btn:

                checkOnSubmit();

                break;
        }
    }

}
