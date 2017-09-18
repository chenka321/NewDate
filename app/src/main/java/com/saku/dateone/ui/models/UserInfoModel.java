package com.saku.dateone.ui.models;

import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.ui.contracts.UserInfoContract;
import com.saku.dateone.utils.UserInfoManager;

public class UserInfoModel<P extends UserInfoContract.P> extends ABaseModel<P> implements UserInfoContract.M {

    public UserInfoModel(P p) {
        super(p);
    }


    @Override
    public void loadUserInfo() {
        String token = UserInfoManager.getInstance().getToken();
        final UserInfo myShowingInfo = UserInfoManager.getInstance().getMyShowingInfo();
        long userId;
        if (myShowingInfo == null) {
            userId = 0;
        } else {
            userId = myShowingInfo.id;
        }
        addToComposition(mApi.getUserInfo(token, userId), mPresenter.getCurrUserInfoObserver());
    }
}
