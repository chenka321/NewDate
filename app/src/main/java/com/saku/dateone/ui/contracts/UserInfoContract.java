package com.saku.dateone.ui.contracts;


import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

/**
 * 用户信息相关的
 */
public interface UserInfoContract {
    interface V extends BaseView<P> {

        void showFillBasicInfoDialog();

        void showErrorLogin();
        void showErrorFillSimple();

        void hideErrorView();
    }

    interface P extends BasePresenter{
        /**
         * 检查是否有缓存用户信息以及是否有 基本的信息
         * @return true 正在加载用户信息，或者弹框让用户填基本信息, false 有基本信息；
         * <p>
         *
         *
         * 填写详细信息完成的接口返回成功，savePendingInfo to showingInfo, preference_user_info,
         * 如果此处 showinginfo == null 就请求接口返回showingInfo and save to preference_user_info
         */
        boolean checkUserInfo();

        void loadUserInfo();
        void onLoadUserInfoReult(int code, String msg, UserInfo userInfo);
    }

    interface M extends BaseModel{

        /**
         * 加载当前用户子女信息
         */
        void loadUserInfo();
    }
}
