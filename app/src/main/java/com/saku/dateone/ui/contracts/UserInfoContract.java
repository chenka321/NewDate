package com.saku.dateone.ui.contracts;


import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

/**
 * 用户信息相关的
 */
public interface UserInfoContract {
    interface V extends BaseView<P> {

        /**
         * 弹出填写基本信息的弹框
         */
        void showFillBasicInfoDialog();

        void showErrorLogin();
        void showErrorFillSimple();

        void hideErrorView();
    }

    interface P extends BasePresenter{
        /**
         * 检查是否有缓存用户信息以及是否有 基本的信息
         * @return true 显示错误界面，false 隐藏错误界面；
         * <p>
         *
         *
         * 填写详细信息完成的接口返回成功，savePendingInfo to showingInfo, preference_user_info,
         * 如果此处 showinginfo == null 就请求接口返回showingInfo and save to preference_user_info
         */
        boolean checkUserInfo();

        RespObserver<ApiResponse<UserInfo>, UserInfo> getCurrUserInfoObserver();
    }

    interface M extends BaseModel{

        /**
         * 加载当前用户子女信息
         */
        void loadUserInfo();
    }
}
