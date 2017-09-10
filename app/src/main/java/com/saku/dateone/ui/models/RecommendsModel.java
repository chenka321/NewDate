package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.RecommendsContract;
import com.saku.dateone.ui.contracts.RecommendsContract.M;

public class RecommendsModel extends UserInfoModel<RecommendsContract.P> implements M {

    public RecommendsModel(RecommendsContract.P p) {
        super(p);
    }

    @Override
    public void loadNotLoginData() {
        mPresenter.onLoadNotLoginDatResult(0, "sucess");
    }

    @Override
    public void loadLoginData() {
        mPresenter.onLoadLoginDataResult(0, "sucess");

    }
}
