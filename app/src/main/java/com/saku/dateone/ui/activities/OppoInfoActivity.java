package com.saku.dateone.ui.activities;

import android.app.Dialog;
import android.content.Intent;
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
import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.ui.contracts.OppoInfoContract;
import com.saku.dateone.ui.list.adapters.OppoPicAdapter;
import com.saku.dateone.ui.presenters.OppoInfoPresenter;
import com.saku.dateone.utils.IconUploadHelper;
import com.saku.dateone.utils.TypeManager;
import com.saku.lmlib.dialog.CommonDialog;
import com.saku.lmlib.helper.PermissionHelper;
import com.saku.lmlib.utils.ImageUtils;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

import java.util.ArrayList;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * 对方子女信息查看页
 */
public class OppoInfoActivity extends BaseActivity<OppoInfoPresenter> implements OppoInfoContract.V, View.OnClickListener {
    private static final int REQUEST_ALBUM = 3; // 选择相册照片
    public static final String USER_ID = "user_id";
    private ImageView userIv;
    private TextView nameTv;
    private TextView collectionTv;
    private TextView residenceTv;
    private TextView locationTv;
    private TextView birthdayTv;
    private TextView degreeTv;
//    private TextView fieldworkTv;
    private TextView occupationTv;
    private TextView salaryTv;
    private TextView viewMoreTv;
    private ViewStub moreInfoVs;
    private LinearLayout moreInfoLl;
    private boolean isMoreInfoOpen;
    private TextView oppHeightTv;
    private TextView oppEstateTv;
//    private TextView oppEstateLocTv;
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
//        fieldworkTv = (TextView) findViewById(R.id.opp_fieldwork_tv);
        occupationTv = (TextView) findViewById(R.id.opp_occupation_tv);
        salaryTv = (TextView) findViewById(R.id.opp_salary_tv);
        viewMoreTv = (TextView) findViewById(R.id.view_more_tv);
        moreInfoVs = (ViewStub) findViewById(R.id.more_info_ll);
        chatTv = (TextView) findViewById(R.id.chat_btn);

        userIv.setOnClickListener(this);
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
    public void updateBasicInfo(UserInfo userinfo) {
        residenceTv.setText(getString(R.string.residence, userinfo.bornLocation));
        locationTv.setText(getString(R.string.currentLocation, userinfo.currentLocation));
        birthdayTv.setText(getString(R.string.birthday, userinfo.birthday));
        final String educationType = TypeManager.getInstance().getMapValue(TypeManager.getInstance().getEducationMap(), userinfo.education);
        degreeTv.setText(getString(R.string.degree, educationType));
        occupationTv.setText(getString(R.string.occupation, userinfo.profession));
        final String incomeType = TypeManager.getInstance().getMapValue(TypeManager.getInstance().getIncomeMap(), userinfo.income);
//        fieldworkTv.setText(getString(R.string.industry, TypeManager.getInstance().getCompanyTypeMap().get(userinfo.company)));
        salaryTv.setText(getString(R.string.salary, incomeType));

        collectionTv.setCompoundDrawablesWithIntrinsicBounds(0,
                userinfo.isCollected ? R.drawable.ic_collection : R.drawable.ic_collection_unselected, 0, 0);
        collectionTv.setCompoundDrawablePadding(UIUtils.convertDpToPx(5, this));
    }

    @Override
    public void displayMoreInfoView(UserInfo userInfo) {
        if (!isMoreInfoOpen) {
            if (moreInfoLl == null) {
                moreInfoLl = (LinearLayout) moreInfoVs.inflate();
                initMoreViews(moreInfoLl, userInfo);
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

    private void initMoreViews(final LinearLayout moreInfoLl, UserInfo userInfo) {
        oppHeightTv = (TextView) moreInfoLl.findViewById(R.id.opp_height_tv);
        oppEstateTv = (TextView) moreInfoLl.findViewById(R.id.opp_estate_tv);
//        oppEstateLocTv = (TextView) moreInfoLl.findViewById(R.id.opp_estate_loc_tv);
        oppCarsTv = (TextView) moreInfoLl.findViewById(R.id.opp_cars_tv);
        oppHobbyTv = (TextView) moreInfoLl.findViewById(R.id.opp_hobby_tv);
        oppSchoolTv = (TextView) moreInfoLl.findViewById(R.id.opp_school_tv);
        oppSchoolNameTv = (TextView) moreInfoLl.findViewById(R.id.opp_school_name_tv);
        oppOtherTv = (TextView) moreInfoLl.findViewById(R.id.opp_other_tv);
        picRv = (RecyclerView) moreInfoLl.findViewById(R.id.pic_rv);

        if (userInfo == null) {
            return;
        }

        oppHeightTv.setText(getString(R.string.height_info, "" + userInfo.height));
        final String houseType = TypeManager.getInstance().getMapValue(TypeManager.getInstance().getCarsMap(), userInfo.house);
        oppEstateTv.setText(getString(R.string.has_house, houseType));
//        oppEstateLocTv.setText(getString(R.string.house_loc, userInfo.estateLocation));
        final String carType = TypeManager.getInstance().getMapValue(TypeManager.getInstance().getCarsMap(), userInfo.car);
        oppCarsTv.setText(getString(R.string.has_car, carType));
        oppHobbyTv.setText(getString(R.string.hobby_info, userInfo.hobby));
        final String schoolType = TypeManager.getInstance().getMapValue(TypeManager.getInstance().getCarsMap(), userInfo.schoolType);
        oppSchoolTv.setText(getString(R.string.grad_school, schoolType));
        oppSchoolNameTv.setText(getString(R.string.school_name_info, userInfo.school));
        oppOtherTv.setText(getString(R.string.addition_info, userInfo.moreIntroduce));

        if (userInfo.photo == null || userInfo.photo.size() == 0) {
            return;
        }
        picRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        picRv.setAdapter(new OppoPicAdapter(this, userInfo.photo));

        moreInfoLl.post(new Runnable() {
            @Override
            public void run() {
                mMoreInfoLlHeight = moreInfoLl.getHeight();
                scrollMoreInfo();
            }
        });
    }

    @Override
    public void markCollection(boolean isCollected) {
        // TODO: 2017-9-4 删除收藏
        if (isCollected) {
            collectionTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_collection, 0, 0);
        } else {
            collectionTv.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_collection_unselected, 0, 0);
        }

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
                            getString(R.string.confirm_go_fill), getString(R.string.later),
                            new CommonDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog) {
                                    LLog.d("showCompleteMoreInfoDialog confirm");
                                    toActivity(CompleteInfoActivity.class, null, false);
                                }
                            }, new CommonDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog) {
                                    LLog.d("showCompleteMoreInfoDialog cancel");
                                }
                            });
                } else {
                    displayMoreInfoView(mPresenter.getUserInfo());
                }
                break;
            case R.id.collection_tv:
                mPresenter.onCollectionClicked();
                break;
            case R.id.opp_user_iv:
                selectAlbum();
                break;
        }
    }

    private void selectAlbum() {
        PermissionHelper permissionH = new PermissionHelper();
        if (!permissionH.requestStoragePermission(this)) {
            return;
        }
        MultiImageSelector.create()
                .showCamera(false) // show camera or not. true by default
                .count(1) // max select image size, 9 by default. used width #.multi()
                .single() // multi mode, default mode;   .single() // single mode
                .origin(null) // original select data set, used width #.multi()
                .start(this, REQUEST_ALBUM);
    }

    @Override
    public void startPicActivity(String picPath) {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent i = new Intent(this, BigPicActivity.class);
        i.putExtra(BigPicActivity.PIC_PATH, picPath);
        i.putExtra(BigPicActivity.FROM_PAGE, BigPicActivity.FROM_OPPO_USER_INFO);
        this.startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ALBUM && data != null) {

            ArrayList<String> newIcons = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);

            if (newIcons != null && newIcons.size() > 0) {
                ImageUtils.loadImageWithGlide(this, newIcons.get(0), 0, userIv);
                mPresenter.uploadIcon(newIcons.get(0));
            }
        }
    }
}
