package com.saku.dateone.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.ui.activities.CompleteInfoActivity;
import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.ui.contracts.MineContract;
import com.saku.dateone.ui.list.typeholders.RecommendTypeHolder;
import com.saku.dateone.ui.list.viewprocessors.RecommendVProcessor;
import com.saku.dateone.ui.presenters.MinePresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.dialog.CommonDialog;
import com.saku.lmlib.utils.ImageUtils;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

public class MineFragment extends UserInfoFragment<MinePresenter> implements MineContract.V, View.OnClickListener {
    public TextView mineUserIdTv;
    public ImageView userIv;
    public TextView mineUserNameTv;
    public TextView mineUserBirthdayTv;
    public TextView mineEditTv;
    public TextView collectionTv;
    public TextView myMsgTv;
    public TextView feedBackTv;
    public TextView logoutTv;

    public static MineFragment newInstance(Bundle bundle) {
        MineFragment f = new MineFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected View getNormalContent() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.s_mine_fragment, mFragmentRoot, false);
        initViews(view);
        return view;
    }

    //    @Override
//    public int getContainerId() {
//        return R.id.mine_root_fl;
//    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showTitle(false);
        Log.d("lm", "MineFragment onViewCreated: ");
        setPresenter(new MinePresenter(this));

        if (UserInfoManager.getInstance().hasSimpleLocal()) {
            fillViews();
        }
    }

    private void initViews(View rootView) {
        this.mineUserIdTv = (TextView) rootView.findViewById(R.id.mine_user_id_Tv);
        this.userIv = (ImageView) rootView.findViewById(R.id.user_iv);
        this.mineUserNameTv = (TextView) rootView.findViewById(R.id.mine_user_name_tv);
        this.mineUserBirthdayTv = (TextView) rootView.findViewById(R.id.mine_user_birthday_tv);
        this.mineEditTv = (TextView) rootView.findViewById(R.id.mine_edit_tv);
        this.collectionTv = (TextView) rootView.findViewById(R.id.collection_tv);
        this.myMsgTv = (TextView) rootView.findViewById(R.id.my_msg_tv);
        this.feedBackTv = (TextView) rootView.findViewById(R.id.feedBack_tv);
        this.logoutTv = (TextView) rootView.findViewById(R.id.logout_tv);

        mineEditTv.setOnClickListener(this);
        collectionTv.setOnClickListener(this);
        myMsgTv.setOnClickListener(this);
        feedBackTv.setOnClickListener(this);
        logoutTv.setOnClickListener(this);

    }

    private void fillViews() {
        final UserInfo userInfo = UserInfoManager.getInstance().getMyShowingInfo();
        if (userInfo == null) {
            return;
        }
        mineUserNameTv.setText(userInfo.name);
        mineUserBirthdayTv.setText(userInfo.birthday);
        if (!TextUtils.isEmpty(userInfo.userImage)) {
            ImageUtils.loadImageWithGlide(mContext, userInfo.userImage, 0, userIv);
        }
        mineUserIdTv.setText(getString(R.string.user_id, "" + userInfo.id));

    }


    @Override
    public void onResume() {
        super.onResume();
        LLog.d("lm", "MineFragment onResume: ");

//        checkUserInfo();  // 造成登录页面 没登录就返回 又进入登录页
    }

    private void checkUserInfo() {
        if (getCurrentTab() == 3) {
            if (UserInfoManager.getInstance().isLogin()) {
                if (UserInfoManager.getInstance().hasSimpleLocal()) {
                    hideErrorView();
                    fillViews();
                } else {
                    if (mPresenter.checkUserInfo()) {
                        showErrorFillSimple();
                    } else {
                        hideErrorView();
                    }
                }
            } else {
                showErrorLogin();
            }
        }
    }

    @Override
    protected void login() {
        gotoLogin(PageManager.MINE, Consts.LOGIN_RQST_MINE);
    }

    @Override
    public void onPause() {
        super.onPause();
        LLog.d("lm", "MineFragment onPause: ");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LLog.d("lm", "MineFragment onActivityResult: ");
        if (requestCode == Consts.LOGIN_RQST_MINE) {
            checkUserInfo();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LLog.d("lm", "MineFragment setUserVisibleHint: " + isVisibleToUser);

        if (isVisibleToUser) {
            checkUserInfo();
        }
    }


    @Override
    public void refreshInfoView() {
        fillViews();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mine_edit_tv:
                toActivity(CompleteInfoActivity.class, null, false);
                break;
            case R.id.collection_tv:
                Bundle b = new Bundle();
                b.putInt(Consts.USERINFO_LIST_TYPE, RecommendVProcessor.TYPE_COLLECTION);
                Fragment recommendF = RecommendsFragment.newInstance(b);
                addFragment(recommendF);
                break;
            case R.id.my_msg_tv:
                Bundle msgBundle = new Bundle();
                final long userId = UserInfoManager.getInstance().getMyShowingInfo().id;
                msgBundle.putLong(Consts.MY_USER_ID, userId);
                Fragment myMsgF = MyMsgFragment.newInstance(msgBundle);
                addFragment(myMsgF);
                break;
            case R.id.feedBack_tv:
                break;
            case R.id.logout_tv:
                UIUtils.showTwoBtnDialog(mContext, 0,
                        getString(R.string.confirm_logout),
                        getString(R.string.delete_info_if_logout),
                        getString(R.string.confirm),
                        getString(R.string.cancel),
                        new CommonDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog) {
                                mPresenter.onLogoutClicked();
                            }
                        }, null);

                break;


        }
    }
}
