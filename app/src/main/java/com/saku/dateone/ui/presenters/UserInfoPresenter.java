package com.saku.dateone.ui.presenters;

import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.contracts.UserInfoContract;
import com.saku.dateone.utils.UserInfoManager;

/**
 * Created by liumin on 2017/9/10.
 */

public abstract class UserInfoPresenter<V1 extends UserInfoContract.V, M1 extends UserInfoContract.M> extends ABasePresenter<V1, M1>
 implements UserInfoContract.P {

    public UserInfoPresenter(V1 mView) {
        super(mView);
    }


    @Override
    public boolean checkUserInfo() {
        final UserInfo userInfo = UserInfoManager.getInstance().getMyShowingInfo();
        if (userInfo == null) {
            mView.showLoading();
            mModel.loadUserInfo();
            return false;
        } else if (!UserInfoManager.getInstance().hasSimpleLocal()) {
            mView.showFillBasicInfoDialog();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public RespObserver<ApiResponse<UserInfo>, UserInfo> getCurrUserInfoObserver() {
        return new RespObserver<ApiResponse<UserInfo>, UserInfo>() {
            @Override
            public void onSuccess(UserInfo userInfo) {
                onGetUserInfoSuccess(userInfo);
            }

            @Override
            public void onFail(int code, String msg) {
                mView.dismissLoading();
            }
        };
    }

    protected void onGetUserInfoSuccess(UserInfo userInfo) {
        mView.dismissLoading();
        UserInfoManager.getInstance().saveShowingInfo(userInfo);
        if (mView != null && !UserInfoManager.getInstance().hasSimpleLocal() ) {
            mView.showFillBasicInfoDialog();

        } else if (mView != null){
            mView.hideErrorView();
        }
    }
}
