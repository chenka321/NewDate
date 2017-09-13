package com.saku.dateone.ui.presenters;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.activities.MainTabsActivity;
import com.saku.dateone.ui.contracts.CompleteInfoContract;
import com.saku.dateone.ui.models.CompleteInfoModel;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.utils.LLog;

import java.util.ArrayList;
import java.util.List;

public class CompleteInfoPresenter extends ABasePresenter<CompleteInfoContract.V, CompleteInfoContract.M> implements CompleteInfoContract.P {

    private ArrayList<String> mPicList;

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
        Bundle bundle = new Bundle();
        bundle.putInt(Consts.SHOW_MAIN_TAB_PAGE, PageManager.RECOMMEND_LIST);
        bundle.putInt(Consts.REFRESH_RECOMMEND, Consts.REFRESH_RECOMMEND_LOGIN);

//        UserInfoManager.getInstance().setFirstLogin(false);
        UserInfoManager.getInstance().copyPendingToShowing();

        mView.toActivity(MainTabsActivity.class, bundle, true);
    }

    @Override
    public void onMatchCompleteClicked() {
        mView.showLoading();
        mModel.startCompleteMatch();
        mModel.uploadPics(mPicList);
    }

    @Override
    public void onMatchCompleteResult(int code, String msg) {
        if (mView == null) {
            return;
        }

        UserInfoManager.getInstance().copyPendingToShowing();
        mView.goNextOnCompleteInfo();
    }

    /**
     * 照片点击事件
     */
    public OnRecyclerClickCallBack getPicItemClickListener() {
        return new OnRecyclerClickCallBack() {
            @Override
            public void onClick(int position, View view) {
                if (mPicList != null && mPicList.size() > position) {
                    mView.startPicActivity(mPicList.get(position));
                }
            }
        };
    }

    /**
     * 设置选择的图片
     */
    public void setPicList(ArrayList<String> picList) {
        this.mPicList = picList;
        UserInfoManager.getInstance().getMyPendingInfo().photo = mPicList;
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
