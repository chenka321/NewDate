package com.saku.dateone.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.ui.activities.MainTabsActivity;
import com.saku.dateone.ui.activities.SimpleInfoActivity;
import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.contracts.MineContract;
import com.saku.dateone.ui.contracts.UserInfoContract;
import com.saku.dateone.ui.presenters.MinePresenter;
import com.saku.dateone.ui.presenters.UserInfoPresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.dialog.CommonDialog;
import com.saku.lmlib.utils.ImageUtils;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

public abstract class UserInfoFragment<P extends UserInfoContract.P> extends BaseFragment<P> implements UserInfoContract.V {

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LLog.d("lm", "UserInfoFragment onActivityResult: requestCode = " + requestCode);
//        if (requestCode == Consts.LOGIN_RQST_MINE && resultCode == LoginActivity.LOGIN_OK) {
//            mPresenter.checkUserInfo();
//        }
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
}
