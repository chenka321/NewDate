package com.saku.dateone.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.ui.activities.MainTabsActivity;
import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.contracts.MineContract;
import com.saku.dateone.ui.presenters.MinePresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.utils.ImageUtils;
import com.saku.lmlib.utils.LLog;

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
    protected View getContentView() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.s_mine_fragment, mFragmentRoot, false);
        initViews(view);
        return view;
    }

    @Override
    public int getContainerId() {
        return R.id.mine_root_ll;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        if (!TextUtils.isEmpty(userInfo.image)) {
            ImageUtils.loadImageWithGlide(mContext, userInfo.image, 0, userIv);
        }
        mineUserIdTv.setText(getString(R.string.user_id, "" + userInfo.userId));

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
                    fillViews();
                } else {
                    mPresenter.checkUserInfo();
                }
            } else {
                gotoLogin(PageManager.MINE, Consts.LOGIN_RQST_MINE);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        LLog.d("lm", "MineFragment onPause: ");

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        LLog.d("lm", "MineFragment onHiddenChanged: ");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LLog.d("lm", "MineFragment onActivityResult: ");
        if (requestCode == Consts.LOGIN_RQST_MINE && resultCode == LoginActivity.LOGIN_OK) {
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
                break;
            case R.id.collection_tv:
                break;
            case R.id.my_msg_tv:
                break;
            case R.id.feedBack_tv:
                break;
            case R.id.logout_tv:
                break;


        }
    }
}
