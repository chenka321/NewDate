package com.saku.dateone.ui.presenters;

import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.ui.contracts.MineContract;
import com.saku.dateone.ui.models.MineModel;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.utils.LLog;

import java.util.List;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 10:56
 * Description: 我的页面
 */
public class MinePresenter extends UserInfoPresenter<MineContract.V, MineContract.M> implements MineContract.P {

    private List<ItemData> mData;

    public MinePresenter(MineContract.V mView) {
        super(mView);
    }

    @Override
    protected MineContract.M getModel() {
        return new MineModel(this);
    }

    @Override
    public boolean checkUserInfo() {

        if (!super.checkUserInfo()) {
            mView.refreshInfoView();
            return false;
        } else {
            return true;
        }
    }

    @Override
    protected void onGetUserInfoSuccess(UserInfo userInfo) {
        super.onGetUserInfoSuccess(userInfo);
        mView.refreshInfoView();
    }

    @Override
    public void onLogoutClicked() {
        UserInfoManager.getInstance().onLogout();
    }


    @Override
    public void uploadIcon(String iconPath) {
        LLog.d("lm", "MinePresenter ------ uploadIcon: " + iconPath);
        mModel.uploadIcon(iconPath);
    }
}


