package com.saku.dateone.ui.models;

import com.saku.dateone.bean.TypeConfig;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.contracts.MainTabsContract;

public class MainTabsModel extends ABaseModel<MainTabsContract.P> implements MainTabsContract.M {

    public MainTabsModel(MainTabsContract.P p) {
        super(p);
    }

    @Override
    public void loadTypeConfig() {
        subscribeWith(mApi.getTypeConfig(),mPresenter.getTypeConfigObserver());
    }


}
