package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.CompleteInfoContract;

public class CompleteInfoModel extends ABaseModel<CompleteInfoContract.P> implements CompleteInfoContract.M{

    public CompleteInfoModel(CompleteInfoContract.P presenter) {
        super(presenter);
    }

    @Override
    public void startSimpleMatch() {
        mPresenter.onMatchSimpleResult(0, "success");
    }

    @Override
    public void startCompleteMatch() {
        mPresenter.onMatchCompleteResult(0, "success");
    }

}
