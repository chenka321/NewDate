package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.OppoInfoContract;
import com.saku.dateone.utils.UserInfoManager;

public class OppoModel extends ABaseModel<OppoInfoContract.P> implements OppoInfoContract.M {

    public OppoModel(OppoInfoContract.P p) {
        super(p);
    }

    @Override
    public void loadPageData(long targetUserId) {

        String token = UserInfoManager.getInstance().getToken();
        addToComposition(mApi.getUserInfo(token, targetUserId), mPresenter.getCurrUserInfoObserver());
    }

    @Override
    public void saveCollection(long targetUserId) {
        String token = UserInfoManager.getInstance().getToken();
        addToComposition(mApi.saveCollection(token, targetUserId), mPresenter.getCollectionObserver());
    }


}
