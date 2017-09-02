package com.saku.dateone.ui.presenters;

import android.view.View;

import com.saku.dateone.ui.contracts.OppoInfoContract;

public class OppoInfoPresenter extends ABasePresenter<OppoInfoContract.V, OppoInfoContract.M> implements OppoInfoContract.P{

    public OppoInfoPresenter(OppoInfoContract.V mView) {
        super(mView);
    }

    @Override
    public View.OnClickListener displayMoreInfo() {
        return null;
    }

    @Override
    protected OppoInfoContract.M getModel() {
        return null;
    }
}
