package com.saku.dateone.ui.presenters;

import android.view.View;

import com.saku.dateone.ui.bean.OppoInfo;
import com.saku.dateone.ui.contracts.OppoInfoContract;
import com.saku.dateone.ui.models.OppoModel;

public class OppoInfoPresenter extends ABasePresenter<OppoInfoContract.V, OppoInfoContract.M> implements OppoInfoContract.P{

    private OppoInfo mOppoInfo;

    public OppoInfoPresenter(OppoInfoContract.V mView) {
        super(mView);
    }

    @Override
    public void loadPage(long userId) {
        mModel.loadPageData(userId);
    }

    @Override
    public void onLoadPage(String code, String msg, OppoInfo oppoInfo) {
        if (mView == null) {
            return;
        }
        mOppoInfo = oppoInfo;
        if(oppoInfo.oppoBasicInfo == null) {
            return;
        }
        mView.updateBasicInfo(oppoInfo.oppoBasicInfo);
    }

    @Override
    public void displayMoreInfo() {
        if (mOppoInfo != null && mOppoInfo.oppoBasicInfo != null) {
            if (!mOppoInfo.oppoBasicInfo.hasMoreInfo) {
                mView.showCompleteMoreInfoDialog();
            } else {
                mView.updateMoreInfo(mOppoInfo.oppoMoreInfo);
            }
        }
    }

    @Override
    protected OppoInfoContract.M getModel() {
        return new OppoModel(this);
    }


}
