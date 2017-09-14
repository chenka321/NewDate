package com.saku.dateone.ui.presenters;

import android.util.Log;

import com.saku.dateone.bean.LoginData;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.contracts.LoginContract;
import com.saku.dateone.ui.contracts.LoginContract.P;
import com.saku.dateone.ui.models.LoginModel;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.utils.LLog;

public class LoginPresenter extends ABasePresenter<LoginContract.V, LoginContract.M> implements P {

    public LoginPresenter(LoginContract.V mView) {
        super(mView);
    }

    @Override
    protected LoginContract.M getModel() {
        return new LoginModel(this);
    }

    @Override
    public void onLoginBtnClicked(String phoneNumber, String veriCode) {
        Log.d("lm", "onLoginBtnClicked: " + phoneNumber + " , veriCode = " + veriCode);
        mView.showLoading();
        mModel.login(phoneNumber, veriCode);
    }

    @Override
    public void onGetVeriCodeClicked(String phoneNumber) {
        Log.d("lm", "onGetVeriCodeClicked: " + phoneNumber);

    }

    @Override
    public void onGetVeriCode(int code, String msg) {

    }

    @Override
    public RespObserver<ApiResponse<LoginData>, LoginData> getLoginObserver() {
        return new RespObserver<ApiResponse<LoginData>, LoginData>() {
            @Override
            public void onSuccess(LoginData data) {
                if (mView != null) {
                    mView.dismissLoading();
                }
                LLog.d("lm", "onSuccess: token" + data.token + " , firstLogin " + data.ifFirstLogin);
                UserInfoManager.getInstance().setLoginState(data.token, data.ifFirstLogin);
                mView.goToNext();
            }

            @Override
            public void onFail(int code, String msg) {
                if (mView != null) {
                    mView.dismissLoading();
                }
                LLog.d("lm", "onSuccess: onFail" + msg);

            }
        };
    }
}
