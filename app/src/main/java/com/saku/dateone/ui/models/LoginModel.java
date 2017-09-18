package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.LoginContract;
import com.saku.dateone.utils.UserInfoManager;

import java.util.HashMap;
import java.util.Map;

public class LoginModel extends ABaseModel<LoginContract.P> implements LoginContract.M {

    public LoginModel(LoginContract.P p) {
        super(p);
    }


    @Override
    public void login(String phoneNumber, String veriCode) {
        add(subscribeWith(mApi.login(phoneNumber), mPresenter.getLoginObserver()));
    }

    @Override
    public void saveParentInfo() {
        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("token", UserInfoManager.getInstance().getToken());
        requestMap.put("location", UserInfoManager.getInstance().getMyPendingInfo().bornLocation);
        requestMap.put("gender", UserInfoManager.getInstance().getMyPendingInfo().gender);
        requestMap.put("currentLocation", UserInfoManager.getInstance().getMyPendingInfo().currentLocation);

        add(subscribeWith(mApi.saveUserInfo(requestMap), mPresenter.getUserInfoObserver()));
    }

    @Override
    public void getVeriCode(String phoneNumber) {
        mPresenter.onGetVeriCode(0, "success");
    }
}
