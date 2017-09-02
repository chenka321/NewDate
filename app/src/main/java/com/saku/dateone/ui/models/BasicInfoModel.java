package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.BasicInfoContract;

public class BasicInfoModel extends ABaseModel<BasicInfoContract.P> implements BasicInfoContract.M{

    public BasicInfoModel(BasicInfoContract.P presenter) {
        super(presenter);
    }

    @Override
    public void startMatch() {
        mPresenter.onStartMatchResult(0, "success");
    }
}
