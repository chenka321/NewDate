package com.saku.dateone.ui.presenters;

import android.os.Bundle;
import android.util.Log;

import com.saku.dateone.bean.TypeConfig;
import com.saku.dateone.internet.OnRespResult;
import com.saku.dateone.ui.contracts.MainTabsContract;
import com.saku.dateone.ui.models.MainTabsModel;

public class MainTabsPresenter extends ABasePresenter<MainTabsContract.V, MainTabsContract.M> implements MainTabsContract.P {

    public MainTabsPresenter(MainTabsContract.V mView) {
        super(mView);
        mModel.loadTypeConfig();
    }

    @Override
    protected MainTabsContract.M getModel() {
        return new MainTabsModel(this);
    }

    @Override
    public void onArguments(Bundle bundle) {

    }

    @Override
    public OnRespResult<TypeConfig> getOnTypeConfigResult() {
        return new OnRespResult<TypeConfig>() {
            @Override
            public void onSucess(TypeConfig data) {
                Log.d("lm", "TypeConfig onSucess: " + data);
            }

            @Override
            public void onFail(int code, String msg) {
                Log.d("lm", "TypeConfig onFail: " + msg);

            }
        };
    }
}
