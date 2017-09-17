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
import com.saku.lmlib.utils.LLog;

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
        mModel.startSimpleMatch();
    }

    @Override
    public void onMatchSimpleResult(int code, String msg) {
        if (mView == null) {
            return;
        }
        onFillResult(Consts.REFRESH_RECOMMEND_LOGIN);
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
        UserInfoManager.getInstance().copyPendingToShowing();
        mView.toActivity(MainTabsActivity.class, bundle, true);
    }

    @Override
    public void onMatchCompleteClicked() {
        mView.showLoading();
        mModel.startCompleteMatch();
    }

    @Override
    public void onMatchCompleteResult(int code, String msg) {
        if (mView == null) {
            return;
        }
        mView.dismissLoading();
        onFillResult(Consts.REFRESH_RECOMMEND_LOGIN);
    }

    @Override
    public RespObserver<ApiResponse<String>, String> getUploadPicObserver() {
        return new RespObserver<ApiResponse<String>, String>() {
            @Override
            public void onSuccess(String data) {
                mView.dismissLoading();
                LLog.d("lm", "----- upload image ---- onSuccess: ");
            }

            @Override
            public void onFail(int code, String msg) {
                mView.dismissLoading();

                LLog.d("lm", "----- upload image ---- onFail: " + msg);

            }
        };
    }

}
