package com.saku.dateone.ui.presenters;

import android.util.Log;

import com.saku.dateone.ui.activities.SimpleInfoActivity;
import com.saku.dateone.ui.contracts.LoginContract;
import com.saku.dateone.ui.contracts.LoginContract.P;
import com.saku.dateone.ui.models.LoginModel;
import com.saku.lmlib.utils.PageHelper;

public class LoginPresenter extends ABasePresenter<LoginContract.V, LoginContract.M>  implements P{

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
        mModel.login(phoneNumber, veriCode);
    }

    @Override
    public void onGetVeriCodeClicked(String phoneNumber) {
        Log.d("lm", "onGetVeriCodeClicked: " + phoneNumber);

    }

    @Override
    public void onLogin(int code, String msg) {
//        PageHelper.getInstance().
        if (mView != null) {
            mView.toActivity(SimpleInfoActivity.class, null, true);
        }
    }

    @Override
    public void onGetVeriCode(int code, String msg) {

    }
}
