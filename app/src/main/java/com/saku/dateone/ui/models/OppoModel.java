package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.OppoInfoContract;

public class OppoModel extends ABaseModel<OppoInfoContract.P> implements OppoInfoContract.M {

    public OppoModel(OppoInfoContract.P p) {
        super(p);
    }
}
