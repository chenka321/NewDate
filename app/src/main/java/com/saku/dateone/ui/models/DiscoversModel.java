package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.DiscoversContract;

public class DiscoversModel extends UserInfoModel<DiscoversContract.P> implements DiscoversContract.M {

    public DiscoversModel(DiscoversContract.P p) {
        super(p);
    }

    @Override
    public void loadDiscoverList(String lastArticleId) {

        mPresenter.onLoadResult(0, "succes");

    }
}
