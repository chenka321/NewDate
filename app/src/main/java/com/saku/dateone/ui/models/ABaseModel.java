package com.saku.dateone.ui.models;

import com.saku.dateone.ui.presenters.BasePresenter;

public abstract class ABaseModel<P extends BasePresenter> implements BaseModel {
    protected P mPresenter;

    public ABaseModel(P p) {
        mPresenter = p;
    }

    @Override
    public void destroy() {
        mPresenter = null;
    }
}
