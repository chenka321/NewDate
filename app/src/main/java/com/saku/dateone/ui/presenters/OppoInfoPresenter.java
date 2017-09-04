package com.saku.dateone.ui.presenters;

import com.saku.dateone.ui.bean.OppoInfo;
import com.saku.dateone.ui.bean.MoreInfo;
import com.saku.dateone.ui.contracts.OppoInfoContract;
import com.saku.dateone.ui.models.OppoModel;

public class OppoInfoPresenter extends ABasePresenter<OppoInfoContract.V, OppoInfoContract.M> implements OppoInfoContract.P {

    private OppoInfo mOppoInfo;

    public OppoInfoPresenter(OppoInfoContract.V mView) {
        super(mView);
    }

    @Override
    public void loadPage(long userId) {
        mModel.loadPageData(userId);
    }

    @Override
    public void onLoadPage(String code, String msg, OppoInfo oppoInfo) {
        if (mView == null) {
            return;
        }
        mOppoInfo = oppoInfo;
        if (oppoInfo.oppoBasicInfo == null) {
            return;
        }
        mView.updateUserNameIv(oppoInfo.oppoBasicInfo.image, oppoInfo.oppoBasicInfo.name);
        mView.updateBasicInfo(oppoInfo.oppoBasicInfo);
    }

    @Override
    public boolean hasMoreInfo() {
        if (mOppoInfo != null && mOppoInfo.oppoBasicInfo != null) {
            return mOppoInfo.oppoBasicInfo.hasMoreInfo;
        }
        return false;
    }

    @Override
    public void onCollectionClicked() {
        if (mOppoInfo.oppoBasicInfo == null) {
            return;
        }
        mModel.saveCollection(mOppoInfo.oppoBasicInfo.targetUserId);
    }

    @Override
    public void onSaveCollection(String code, String msg) {
        if (mView != null) {
            mView.markCollection();
        }
    }

    @Override
    public MoreInfo getMoreInfo() {
        if (mOppoInfo != null && mOppoInfo.oppoBasicInfo != null) {
            return mOppoInfo.oppoMoreInfo;
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
