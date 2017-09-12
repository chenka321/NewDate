package com.saku.dateone.ui.presenters;

import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.ui.contracts.OppoInfoContract;
import com.saku.dateone.ui.models.OppoModel;

public class OppoInfoPresenter extends ABasePresenter<OppoInfoContract.V, OppoInfoContract.M> implements OppoInfoContract.P {

    private UserInfo mOppoInfo;

    public OppoInfoPresenter(OppoInfoContract.V mView) {
        super(mView);
    }

    @Override
    public void loadPage(long userId) {
        mModel.loadPageData(userId);
    }

    @Override
    public void onLoadPage(String code, String msg, UserInfo userInfo) {
        if (mView == null) {
            return;
        }
        mOppoInfo = userInfo;
        if (mOppoInfo == null) {
            return;
        }
        mView.updateUserNameIv(mOppoInfo.userImage, mOppoInfo.name);
        mView.updateBasicInfo(mOppoInfo);
    }

    @Override
    public boolean hasMoreInfo() {
        if (mOppoInfo != null) {
            return mOppoInfo.hasMoreInfo;
        }
        return false;
    }

    @Override
    public void onCollectionClicked() {
        if (mOppoInfo == null) {
            return;
        }
        mModel.saveCollection(mOppoInfo.id);
    }

    @Override
    public void onSaveCollection(String code, String msg) {
        boolean isCollected = false;
        if (mView != null) {
            if (mOppoInfo != null) {
                isCollected = mOppoInfo.isCollected;
                mOppoInfo.isCollected = !isCollected;
            }
            mView.markCollection(!isCollected);
        }
    }

    @Override
    public UserInfo getUserInfo() {
        if (mOppoInfo != null) {
            return mOppoInfo;
        }
        return null;
    }

    @Override
    public void onChatClicked() {
//        mModel.getChatInfo
    }

    @Override
    protected OppoInfoContract.M getModel() {
        return new OppoModel(this);
    }


}
