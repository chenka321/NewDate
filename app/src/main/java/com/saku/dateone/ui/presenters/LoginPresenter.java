package com.saku.dateone.ui.presenters;

import android.util.Log;

import com.saku.dateone.DateApplication;
import com.saku.dateone.bean.LoginData;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.contracts.LoginContract;
import com.saku.dateone.ui.contracts.LoginContract.P;
import com.saku.dateone.ui.models.LoginModel;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.PreferenceUtil;

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
                UserInfoManager.getInstance().copyPendingToShowing(); // 保存位置、性别
                PreferenceUtil.putBoolean(DateApplication.getAppContext(), Consts.HAS_BASIC_INFO, true);

//                mView.dismissLoading();
                LLog.d("lm", "onSuccess: token" + data.token + " , firstLogin " + data.ifFirstLogin);
                UserInfoManager.getInstance().setLoginState(data.token, data.ifFirstLogin);
                mModel.saveParentInfo();
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

    @Override
    public RespObserver<ApiResponse<String>, String> getUserInfoObserver() {
        return new RespObserver<ApiResponse<String>, String>() {
            @Override
            public void onSuccess(String data) {
                mView.dismissLoading();
                mView.goToNext();
            }

            @Override
            public void onFail(int code, String msg) {
                mView.dismissLoading();
                mView.goToNext();
            }
        };
    }
}
