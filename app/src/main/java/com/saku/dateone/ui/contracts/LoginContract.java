package com.saku.dateone.ui.contracts;

import com.saku.dateone.bean.LoginData;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

public interface LoginContract {
    interface V extends BaseView<P> {
        void goToNext(); // 登录成功到下一个页面
    }

    interface P extends BasePresenter {
        void onLoginBtnClicked(String phoneNumber, String veriCode);

        /**
         * 获取验证码
         * @param phoneNumber 手机号码
         */
        void onGetVeriCodeClicked(String phoneNumber);
        /** 验证码接口回调 */
        void onGetVeriCode(int code, String msg);

        RespObserver<ApiResponse<LoginData>, LoginData> getLoginObserver();

        RespObserver<ApiResponse<String>, String> getUserInfoObserver();  // 用户信息保存后的回调
    }

    interface M extends BaseModel {
        void login(String phoneNumber, String veriCode);
        void getVeriCode(String phoneNumber);

        void saveParentInfo();
    }
}
