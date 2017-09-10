package com.saku.dateone.ui.presenters;

import android.os.Bundle;

import com.saku.dateone.ui.activities.MainTabsActivity;
import com.saku.dateone.ui.contracts.CompleteInfoContract;
import com.saku.dateone.ui.models.CompleteInfoModel;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.utils.PreferenceUtil;

import java.util.List;

public class CompleteInfoPresenter extends ABasePresenter<CompleteInfoContract.V, CompleteInfoContract.M> implements CompleteInfoContract.P {

    @Override
    protected CompleteInfoContract.M getModel() {
        return new CompleteInfoModel(this);
    }

    public CompleteInfoPresenter(CompleteInfoContract.V view) {
        super(view);
    }

    @Override
    public void onChooseImages(List<String> imgsChosen) {

    }

    @Override
    public void onMatchSimpleClicked() {
        mModel.startSimpleMatch();
    }

    @Override
    public void onMatchSimpleResult(int code, String msg) {
        if (mView == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(Consts.SHOW_MAIN_TAB_PAGE, PageManager.RECOMMEND_LIST);
        bundle.putInt(Consts.REFRESH_RECOMMEND, Consts.REFRESH_RECOMMEND_LOGIN);

        UserInfoManager.getInstance().setFirstLogin(false);
        UserInfoManager.getInstance().copyPendingToShowing();

        mView.toActivity(MainTabsActivity.class, bundle, true);
    }

    @Override
    public void onMatchCompleteClicked() {
        mModel.startCompleteMatch();
    }

    @Override
    public void onMatchCompleteResult(int code, String msg) {
        if (mView == null) {
            return;
        }

        UserInfoManager.getInstance().copyPendingToShowing();

//        b.putInt(Consts.COMPLETE_FROM_PAGE_NAME, Consts.COMPLETE_INFO_MY_SIMPLE_INFO);
//        mView.toActivity(MainTabsActivity.class, null, true);
        mView.goNextOnCompleteInfo();
    }
}
