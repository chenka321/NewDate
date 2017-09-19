package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.DiscoversContract;
import com.saku.dateone.utils.UserInfoManager;

public class DiscoversModel extends UserInfoModel<DiscoversContract.P> implements DiscoversContract.M {

    public DiscoversModel(DiscoversContract.P p) {
        super(p);
    }

    @Override
    public void loadDiscoverList(String lastArticleId) {

        final long userId = UserInfoManager.getInstance().getMyShowingInfo().userId;
        addToComposition(mApi.discover(userId, lastArticleId), mPresenter.getDiscoverListObserver());
    }
}
