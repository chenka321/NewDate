package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.LoginContract;

public class LoginModel extends ABaseModel<LoginContract.P> implements LoginContract.M {

    public LoginModel(LoginContract.P p) {
        super(p);
    }


    @Override
    public void login(String phoneNumber, String veriCode) {
//        mPresenter.onLogin(0, "success");
        subscribeWith(mApi.login(phoneNumber), mPresenter.getLoginObserver());

    }

    @Override
    public void getVeriCode(String phoneNumber) {
        mPresenter.onGetVeriCode(0, "success");
    }
}
