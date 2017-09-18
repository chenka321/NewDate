package com.saku.dateone.ui.presenters;

import android.os.Bundle;

import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.activities.MainTabsActivity;
import com.saku.dateone.ui.contracts.CompleteInfoContract;
import com.saku.dateone.ui.models.CompleteInfoModel;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.UserInfoManager;

public class CompleteInfoPresenter extends ABasePresenter<CompleteInfoContract.V, CompleteInfoContract.M> implements CompleteInfoContract.P {

    @Override
    protected CompleteInfoContract.M getModel() {
        return new CompleteInfoModel(this);
    }

    public CompleteInfoPresenter(CompleteInfoContract.V view) {
        super(view);
    }

    @Override
    public void onMatchSimpleClicked() {
        mView.showLoading();
        mModel.saveSimpleInfo();
    }

    @Override
    public void onMatchCompleteClicked() {
        mView.showLoading();
        mModel.saveCompleteInfo();
    }

    @Override
    public RespObserver<ApiResponse<String>, String> getSaveInfoObserver() {
        return new RespObserver<ApiResponse<String>, String>() {
            @Override
            public void onSuccess(String data) {
                mView.dismissLoading();
                onFillResult(Consts.REFRESH_RECOMMEND_LOGIN);
            }

            @Override
            public void onFail(int code, String msg) {
                mView.dismissLoading();
            }
        };
    }

    /**
     * 上传填写的内容成功回调
     *
     * @param refreshWhich 下个页面要请求的数据，未登录推荐数据{@link Consts#REFRESH_RECOMMEND_NOT_LOGIN}， 已登录推荐数据{@link Consts#REFRESH_RECOMMEND_LOGIN}
     */
    private void onFillResult(int refreshWhich) {
        Bundle bundle = new Bundle();
        bundle.putInt(Consts.SHOW_MAIN_TAB_PAGE, PageManager.RECOMMEND_LIST);
        bundle.putInt(Consts.REFRESH_RECOMMEND, refreshWhich);
        UserInfoManager.getInstance().copyPendingToShowing();   // 保存用户子女基本信息，用户子女基本信息.
        mView.toActivity(MainTabsActivity.class, bundle, true);
    }


}
