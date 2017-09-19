package com.saku.dateone.ui.contracts;


/**
 * 发现列表
 */
public interface MineContract {
    interface V extends UserInfoContract.V {
        void refreshInfoView();

    }

    interface P extends UserInfoContract.P {
        /** 登出 */
        void onLogoutClicked();

        void uploadIcon(String iconPath);
    }

    interface M extends UserInfoContract.M {

        void uploadIcon(String iconPath);
    }
}
