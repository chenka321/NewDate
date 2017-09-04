package com.saku.dateone.ui.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.ui.bean.OppoBasicInfo;
import com.saku.dateone.ui.bean.OppoMoreInfo;
import com.saku.dateone.ui.contracts.OppoInfoContract;
import com.saku.dateone.ui.list.adapters.OppoPicAdapter;
import com.saku.dateone.ui.presenters.OppoInfoPresenter;
import com.saku.lmlib.dialog.CommonDialog;
import com.saku.lmlib.utils.ImageUtils;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

public class OppoInfoActivity extends BaseActivity<OppoInfoPresenter> implements OppoInfoContract.V, View.OnClickListener {

    public static final String USER_ID = "user_id";
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
    private LinearLayout moreInfoLl;
    private boolean isMoreInfoOpen;
    private TextView oppHeightTv;
    private TextView oppEstateTv;
    private TextView oppEstateLocTv;
    private TextView oppCarsTv;
    private TextView oppHobbyTv;
    private TextView oppSchoolTv;
    private TextView oppSchoolNameTv;
    private TextView oppOtherTv;
    private RecyclerView picRv;
    private ScrollView contentSv;
    private int mMoreInfoLlHeight;
    private TextView chatTv;

    @Override
    protected View getContentView() {
        final View view = LayoutInflater.from(this).inflate(R.layout.s_opp_info_activity, mRoot, false);
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
        if (0 == userId) {
            return;
        }
        mPresenter.loadPage(userId);
    }

    private void initView() {

        contentSv = (ScrollView) findViewById(R.id.content_sv);
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
        chatTv = (TextView) findViewById(R.id.chat_btn);

        viewMoreTv.setOnClickListener(this);
        collectionTv.setOnClickListener(this);
        chatTv.setOnClickListener(this);
    }

    @Override
    public void updateUserNameIv(String imgUrl, String name) {
        nameTv.setText(name);
        ImageUtils.loadImageWithGlide(this, imgUrl, 0, userIv);
    }

    @Override
    public void updateBasicInfo(OppoBasicInfo oppoBasicInfo) {
        residenceTv.setText(getString(R.string.residence, oppoBasicInfo.bornLocation));
        locationTv.setText(getString(R.string.currentLocation, oppoBasicInfo.currentLocation));
        birthdayTv.setText(getString(R.string.birthday, oppoBasicInfo.birthday));
        degreeTv.setText(getString(R.string.degree, oppoBasicInfo.degree));
        occupationTv.setText(getString(R.string.occupation, oppoBasicInfo.position));
        fieldworkTv.setText(getString(R.string.industry, oppoBasicInfo.company));
        salaryTv.setText(getString(R.string.salary, oppoBasicInfo.income));
    }

    @Override
    public void displayMoreInfoView(OppoMoreInfo oppoMoreInfo) {
        if (!isMoreInfoOpen) {
            if (moreInfoLl == null) {
                moreInfoLl = (LinearLayout) moreInfoVs.inflate();
                initMoreViews(moreInfoLl, oppoMoreInfo);
            } else {
                moreInfoLl.setVisibility(View.VISIBLE);
            }
            isMoreInfoOpen = true;
            viewMoreTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);

        } else {
            isMoreInfoOpen = false;
            moreInfoLl.setVisibility(View.GONE);
            viewMoreTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_down, 0);
        }
        scrollMoreInfo();
        viewMoreTv.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.view_more_drawable_padding));
    }

    /**
     * 查看更多信息被点击时滚动到相应位置
     */
    private void scrollMoreInfo() {
        if (mMoreInfoLlHeight != 0) {
            moreInfoLl.post(new Runnable() {
                @Override
                public void run() {
                    if (isMoreInfoOpen) {
                        contentSv.smoothScrollTo(0, contentSv.getBottom());
                    } else {
                        contentSv.smoothScrollTo(0, 0);
                    }
                }
            });
        }
    }

    private void initMoreViews(final LinearLayout moreInfoLl, OppoMoreInfo oppoMoreInfo) {
        oppHeightTv = (TextView) moreInfoLl.findViewById(R.id.opp_height_tv);
        oppEstateTv = (TextView) moreInfoLl.findViewById(R.id.opp_estate_tv);
        oppEstateLocTv = (TextView) moreInfoLl.findViewById(R.id.opp_estate_loc_tv);
        oppCarsTv = (TextView) moreInfoLl.findViewById(R.id.opp_cars_tv);
        oppHobbyTv = (TextView) moreInfoLl.findViewById(R.id.opp_hobby_tv);
        oppSchoolTv = (TextView) moreInfoLl.findViewById(R.id.opp_school_tv);
        oppSchoolNameTv = (TextView) moreInfoLl.findViewById(R.id.opp_school_name_tv);
        oppOtherTv = (TextView) moreInfoLl.findViewById(R.id.opp_other_tv);
        picRv = (RecyclerView) moreInfoLl.findViewById(R.id.pic_rv);

        if (oppoMoreInfo == null) {
            return;
        }

        oppHeightTv.setText(getString(R.string.height_info, oppoMoreInfo.height));
        oppEstateTv.setText(getString(R.string.has_house, oppoMoreInfo.house));
        oppEstateLocTv.setText(getString(R.string.house_loc, oppoMoreInfo.estateLocation));
        oppCarsTv.setText(getString(R.string.has_car, oppoMoreInfo.car));
        oppHobbyTv.setText(getString(R.string.hobby_info, oppoMoreInfo.hobby));
        oppSchoolTv.setText(getString(R.string.grad_school, oppoMoreInfo.schoolType));
        oppSchoolNameTv.setText(getString(R.string.school_name_info, oppoMoreInfo.school));
        oppOtherTv.setText(getString(R.string.addition_info, oppoMoreInfo.additionInfo));

        if (oppoMoreInfo.pics == null || oppoMoreInfo.pics.size() == 0) {
            return;
        }
        picRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        picRv.setAdapter(new OppoPicAdapter(this, oppoMoreInfo.pics));

        moreInfoLl.post(new Runnable() {
            @Override
            public void run() {
                mMoreInfoLlHeight = moreInfoLl.getHeight();
                scrollMoreInfo();
            }
        });
    }

    @Override
    public void markCollection() {
        // TODO: 2017-9-4 删除收藏
        collectionTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_collection, 0, 0);
        collectionTv.setCompoundDrawablePadding(getResources().getDimensionPixelOffset(R.dimen.view_more_drawable_padding));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chat_btn:
//                mPresenter.onChatClicked();
//                startActivity();
                break;
            case R.id.view_more_tv:
                if (!mPresenter.hasMoreInfo()) {
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
                                    LLog.d("showCompleteMoreInfoDialog cancel");
                                }
                            });
                } else {
                    displayMoreInfoView(mPresenter.getMoreInfo());
                }
                break;
            case R.id.collection_tv:
                mPresenter.onCollectionClicked();
                break;
        }
    }
}
