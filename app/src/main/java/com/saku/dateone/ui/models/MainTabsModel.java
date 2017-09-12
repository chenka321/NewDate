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
        subscribeWith(mApi.getTypeConfig(), new RespObserver<ApiResponse<TypeConfig>, TypeConfig>() {

            @Override
            public void onSuccess(TypeConfig data) {
                mPresenter.getOnTypeConfigResult().onSucess(data);
            }

            @Override
            public void onFail(int code, String msg) {
                mPresenter.getOnTypeConfigResult().onFail(code, msg);
            }
        });
    }


}
