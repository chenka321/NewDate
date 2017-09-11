package com.saku.dateone.ui.presenters;

import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.ui.contracts.MineContract;
import com.saku.dateone.ui.models.MineModel;
import com.saku.lmlib.list.data.ItemData;

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
    public void onLoadUserInfoReult(int code, String msg, UserInfo userInfo) {
        super.onLoadUserInfoReult(code, msg, userInfo);
        if (mView != null) {
            mView.refreshInfoView();
        }
    }
}


