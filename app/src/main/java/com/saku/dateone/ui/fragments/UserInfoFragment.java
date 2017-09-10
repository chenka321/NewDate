package com.saku.dateone.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.ui.activities.MainTabsActivity;
import com.saku.dateone.ui.activities.SimpleInfoActivity;
import com.saku.dateone.ui.contracts.UserInfoContract;
import com.saku.lmlib.dialog.CommonDialog;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

public abstract class UserInfoFragment<P extends UserInfoContract.P> extends BaseFragment<P> implements UserInfoContract.V {
    protected TextView loginUserBtn;
//    protected TextView fillSimpleInfoBtn;
    protected FrameLayout userInfoRootFl;
    protected FrameLayout errorFl;
    protected View mErrFL;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LLog.d("lm", "UserInfoFragment onActivityResult: requestCode = " + requestCode);
//        if (requestCode == Consts.LOGIN_RQST_MINE && resultCode == LoginActivity.LOGIN_OK) {
//            mPresenter.checkUserInfo();
//        }
    }

    @Override
    protected View getContentView() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.s_userinfo_fragment, mFragmentRoot, false);
        this.userInfoRootFl = (FrameLayout) view.findViewById(R.id.userinfo_fragment_root_fl);

        if (getNormalContent() != null) {
            userInfoRootFl.addView(getNormalContent());
            initErrorViews();
            userInfoRootFl.addView(mErrFL);
            mErrFL.setVisibility(View.GONE);
        }
        return view;
    }

    private void initErrorViews() {
        if (mErrFL != null) {
            return;
        }
        mErrFL = LayoutInflater.from(getContext()).inflate(R.layout.s_userinfo_error, mFragmentRoot, false);
        this.loginUserBtn = (TextView) mErrFL.findViewById(R.id.login_in_userinfo_btn);
//        this.fillSimpleInfoBtn = (TextView) mErrFL.findViewById(R.id.fill_simple_info_in_userinfo_btn);
        this.errorFl = (FrameLayout) mErrFL.findViewById(R.id.userinfo_error_fl);

        loginUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    public int getContainerId() {
        return R.id.userinfo_fragment_root_fl;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        Log.d("lm", "UserInfoFragment onViewCreated: ");
    }

    @Override
    public void showErrorLogin() {
        mErrFL.setVisibility(View.VISIBLE);
        loginUserBtn.setVisibility(View.VISIBLE);
//        fillSimpleInfoBtn.setVisibility(View.GONE);

    }

    @Override
    public void showErrorFillSimple() {
        mErrFL.setVisibility(View.VISIBLE);
//        fillSimpleInfoBtn.setVisibility(View.VISIBLE);
        loginUserBtn.setVisibility(View.GONE);
    }

    @Override
    public void hideErrorView() {
        mErrFL.setVisibility(View.GONE);
    }

    @Override
    public void showFillBasicInfoDialog() {
        UIUtils.showTwoBtnDialog(mContext, 0, getString(R.string.simple_info),
                getString(R.string.simple_info_content),
                getString(R.string.confirm), getString(R.string.later),
                new CommonDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        LLog.d("showFillBasicInfoDialog confirm");
                        toActivity(SimpleInfoActivity.class, null, false);
                    }
                }, new CommonDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog) {
                        LLog.d("showFillBasicInfoDialog cancel");
                    }
                });
    }

    protected int getCurrentTab() {
        if (getActivity() != null && getActivity() instanceof MainTabsActivity) {
            final MainTabsActivity activity = (MainTabsActivity) getActivity();
            return activity.getCurrentPageSelected();
        } else {
            return 0;
        }
    }

    /**
     * @return 正常显示的view内容， 子类可以不用实现getContentView（）了
     */
    protected View getNormalContent() {
        return null;
    };

    protected void login() {};

}
