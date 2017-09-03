package com.saku.dateone.ui.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.ui.bean.OppoBasicInfo;
import com.saku.dateone.ui.bean.OppoMoreInfo;
import com.saku.dateone.ui.contracts.OppoInfoContract;
import com.saku.dateone.ui.presenters.OppoInfoPresenter;
import com.saku.lmlib.dialog.CommonDialog;
import com.saku.lmlib.utils.ImageUtils;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

public class OppoInfoActivity extends BaseActivity<OppoInfoPresenter> implements OppoInfoContract.V, View.OnClickListener {

    private static final String USER_ID = "user_id";
    private ImageView userIv;
    private TextView nameTv;
    private TextView collectionTv;
    private TextView residenceTv;
    private TextView locationTv;
    private TextView birthdayTv;
    private TextView degreeTv;
    private TextView fieldworkTv;
    private TextView occupationTv;
    private TextView salaryTv;
    private TextView viewMoreTv;
    private ViewStub moreInfoVs;

    @Override
    protected View getContentView() {
        final View view = LayoutInflater.from(this).inflate(R.layout.s_opp_info_fragment, mRoot, false);
        return view;
    }

    @Override
    protected OppoInfoPresenter getPresenter() {
        return new OppoInfoPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showTitle(true);
        mTitleLayout.showLeftBtn(true);

        initView();
        final long userId = getIntent().getLongExtra(USER_ID, 0);
        if(0 == userId) {
            return;
        }
        mPresenter.loadPage(userId);
    }

    private void initView() {
        userIv = (ImageView) findViewById(R.id.opp_user_iv);
        nameTv = (TextView) findViewById(R.id.opp_name_tv);
        collectionTv = (TextView) findViewById(R.id.collection_tv);
        residenceTv = (TextView) findViewById(R.id.opp_residence_tv);
        locationTv = (TextView) findViewById(R.id.opp_location_tv);
        birthdayTv = (TextView) findViewById(R.id.opp_birthday_tv);
        degreeTv = (TextView) findViewById(R.id.opp_education_tv);
        fieldworkTv = (TextView) findViewById(R.id.opp_fieldwork_tv);
        occupationTv = (TextView) findViewById(R.id.opp_occupation_tv);
        salaryTv = (TextView) findViewById(R.id.opp_salary_tv);
        viewMoreTv = (TextView) findViewById(R.id.view_more_tv);
        moreInfoVs = (ViewStub) findViewById(R.id.more_info_ll);

        viewMoreTv.setOnClickListener(this);
    }

    @Override
    public void updateUserNameIv(String imgUrl, String name) {
        nameTv.setText(name);
        ImageUtils.loadImageWithGlide(this, imgUrl, 0, userIv);
    }

    @Override
    public void updateBasicInfo(OppoBasicInfo oppoBasicInfo) {
        residenceTv.setText(oppoBasicInfo.bornLocation);
        locationTv.setText(oppoBasicInfo.currentLocation);
        birthdayTv.setText(oppoBasicInfo.birthday);
        degreeTv.setText(oppoBasicInfo.degree);
        fieldworkTv.setText(oppoBasicInfo.company);
        salaryTv.setText(oppoBasicInfo.income);
    }

    @Override
    public void updateMoreInfo(OppoMoreInfo oppoMoreInfo) {

    }

    @Override
    public void showCompleteMoreInfoDialog() {
        UIUtils.showTwoBtnDialog(this, 0, getString(R.string.complete_info),
                getString(R.string.complete_info_content),
                getString(R.string.confirm), getString(R.string.later),
                new CommonDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        LLog.d("showCompleteMoreInfoDialog confirm");
//                        startActivity();
                    }
                }, new CommonDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        LLog.d("showCompleteMoreInfoDialog confirm");
                    }
                });
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_more_tv) {
            mPresenter.displayMoreInfo();
        }
    }
}
