package com.saku.dateone.ui.presenters;

import android.content.Context;

import com.saku.dateone.ui.activities.BaseActivity;
import com.saku.dateone.ui.contracts.BaseView;
import com.saku.dateone.ui.fragments.BaseFragment;
import com.saku.dateone.ui.models.BaseModel;

public abstract class ABasePresenter<V extends BaseView<? extends BasePresenter>, M extends BaseModel> implements BasePresenter {
    protected V mView;
    protected M mModel;

    public ABasePresenter(V mView) {
        this.mView = mView;
        this.mModel = getModel();
    }

    @Override
    public void destroy() {
        mView = null;
        if (mModel != null) {
            mModel.destroy();
            mModel = null;
        }
    }

    protected void onResult(int code, String msg) {
        if (code == 0) {
            onSuccess();
        } else {
            onFail(code, msg);
        }
    }

    protected void onSuccess(){}

    protected void onFail(int code, String msg){}

    protected abstract M getModel();

}
