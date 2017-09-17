package com.saku.dateone.ui.models;

import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.contracts.CompleteInfoContract;
import com.saku.dateone.utils.UserInfoManager;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;

public class CompleteInfoModel extends ABaseModel<CompleteInfoContract.P> implements CompleteInfoContract.M{

    public CompleteInfoModel(CompleteInfoContract.P presenter) {
        super(presenter);
    }

    @Override
    public void startSimpleMatch() {
        mPresenter.onMatchSimpleResult(0, "success");
    }

    @Override
    public void startCompleteMatch() {
        mPresenter.onMatchCompleteResult(0, "success");
    }

}
