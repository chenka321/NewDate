package com.saku.dateone.ui.presenters;

import android.os.Bundle;

import com.saku.dateone.ui.contracts.MainTabsContract;
import com.saku.dateone.ui.models.FrontpageModel;
import com.saku.dateone.ui.models.MainTabsModel;

public class MainTabsPresenter extends ABasePresenter<MainTabsContract.V, MainTabsContract.M> implements MainTabsContract.P {

    public MainTabsPresenter(MainTabsContract.V mView) {
        super(mView);
    }

    @Override
    protected MainTabsContract.M getModel() {
        return new MainTabsModel(this);
    }

    @Override
    public void onArguments(Bundle bundle) {

    }


}
