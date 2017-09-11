package com.saku.dateone.ui.contracts;


/**
 * 发现列表
 */
public interface MineContract {
    interface V extends UserInfoContract.V {
        void refreshInfoView();

    }

    interface P extends UserInfoContract.P {
    }

    interface M extends UserInfoContract.M {

    }
}
