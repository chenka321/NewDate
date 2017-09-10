package com.saku.dateone.ui.presenters;

import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.contracts.BaseView;
import com.saku.dateone.ui.contracts.MineContract;
import com.saku.dateone.ui.contracts.UserInfoContract;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.utils.UserInfoManager;

/**
 * Created by liumin on 2017/9/10.
 */

public abstract class UserInfoPresenter<V1 extends UserInfoContract.V, M1 extends UserInfoContract.M> extends ABasePresenter<V1, M1>
 implements UserInfoContract.P {

    public UserInfoPresenter(V1 mView) {
        super(mView);
    }


    @Override
    public boolean checkUserInfo() {
        final UserInfo userInfo = UserInfoManager.getInstance().getMyShowingInfo();
        if (userInfo == null) {
            mModel.loadUserInfo();
            return true;
        } else if (!UserInfoManager.getInstance().hasSimpleLocal()) {
            mView.showFillBasicInfoDialog();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void loadUserInfo() {
        mModel.loadUserInfo();
    }

    @Override
    public void onLoadUserInfoReult(int code, String msg, UserInfo userInfo) {
        UserInfoManager.getInstance().saveShowingInfo(userInfo);
        if (mView != null && !UserInfoManager.getInstance().hasSimpleLocal() ) {
            mView.showFillBasicInfoDialog();

        } else if (mView != null){
            mView.hideErrorView();
        }
    }
}
