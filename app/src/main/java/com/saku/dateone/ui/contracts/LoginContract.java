package com.saku.dateone.ui.contracts;

import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

public interface LoginContract {
    interface V extends BaseView<P> {
        void finish();
        void onInternetFail(String msg);
    }

    interface P extends BasePresenter {
        void onLoginBtnClicked(String phoneNumber, String veriCode);

        /**
         * 获取验证码
         * @param phoneNumber 手机号码
         */
        void onGetVeriCodeClicked(String phoneNumber);

        /** 登录接口回调 */
        void onLogin(int code, String msg);
        /** 验证码接口回调 */
        void onGetVeriCode(int code, String msg);
    }

    interface M extends BaseModel {
        void login(String phoneNumber, String veriCode);
        void getVeriCode(String phoneNumber);
    }
}
