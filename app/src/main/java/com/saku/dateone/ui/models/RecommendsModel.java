package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.RecommendsContract;
import com.saku.dateone.ui.contracts.RecommendsContract.M;

public class RecommendsModel extends ABaseModel<RecommendsContract.P> implements M {

    public RecommendsModel(RecommendsContract.P p) {
        super(p);
    }

    @Override
    public void loadData() {

    }
}
