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
        mPresenter.onMatchSimpleResult(0, "success");
    }

    @Override
    public void uploadPics(ArrayList<String> picList) {
        if (picList == null || picList.size() == 0) {
            return;
        }

        final String token = UserInfoManager.getInstance().getMyShowingInfo().token;
        if (mPresenter != null) {
            final RespObserver<ApiResponse<String>, String> picObserver = mPresenter.getUploadPicObserver();

            final String path1 = picList.get(0);
            File picFile = new File(path1);
            if (!picFile.exists()) {
                return ;
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), picFile);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", picFile.getName(), requestBody);
            RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
            subscribeWith(mApi.uploadImage(tokenBody, part), picObserver);
        }
    }
}
