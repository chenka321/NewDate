package com.saku.dateone.ui.models;

import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.ui.contracts.UserInfoContract;

public class UserInfoModel<P extends UserInfoContract.P> extends ABaseModel<P> implements UserInfoContract.M {

    public UserInfoModel(P p) {
        super(p);
    }


    @Override
    public void loadUserInfo() {

        UserInfo userInfo = new UserInfo();
        userInfo.name = "LMin";
        userInfo.birthday = "2008年8月";
        userInfo.education = 4;
        userInfo.id = 334344;
        userInfo.token = "ZYj7AvX30JDtjle7SYAn/g==";
        mPresenter.onLoadUserInfoReult(0, "sucess", userInfo);
    }
}
