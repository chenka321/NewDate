package com.saku.dateone.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.saku.dateone.internet.ApiManager;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.ApiService;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.utils.LLog;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadPicService extends Service {
    private CompositeDisposable mComDisposable;
    private ApiService mApi;

    @Override
    public void onCreate() {
        super.onCreate();
        mApi = ApiManager.getAllApis().getDateApis();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearDisposable();
    }

    protected void add(Disposable disposable) {
        if (mComDisposable == null || mComDisposable.isDisposed()) {
            mComDisposable = new CompositeDisposable();
        }
        mComDisposable.add(disposable);
    }

    private <T> ObservableTransformer<T, T> defaultSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    private void clearDisposable() {
        if (mComDisposable != null) {
            mComDisposable.clear();
            mComDisposable = null;
        }
    }

    protected  <T, D> RespObserver<T, D> subscribeWith(Observable<T> observable, RespObserver<T,D> observer) {
        return observable.compose(this.<T>defaultSchedulers())
                .subscribeWith(observer);
    }

    public RespObserver<ApiResponse<String>, String> getUploadPicObserver(final int index) {
        return new RespObserver<ApiResponse<String>, String>() {
            @Override
            public void onSuccess(String data) {
                LLog.d("lm", "UploadBinder ------ onSuccess: " + index);
            }

            @Override
            public void onFail(int code, String msg) {
                LLog.d("lm", "UploadBinder ------ onFail: " + code + ", index = " + index);
            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new UploadBinder();
    }

    public class UploadBinder extends Binder {
        private List<String> picList;

        public UploadBinder() {
        }

        public void uploadList(List<String> list) {
//            mApi.uploadImage()
            picList = list;
            prepareUploadRequest();
        }

        private void prepareUploadRequest() {
            if (picList == null) {
                return;
            }
            final String token = UserInfoManager.getInstance().getMyShowingInfo().token;
            final RespObserver<ApiResponse<String>, String> picObserver = getUploadPicObserver(0);

            final String path1 = picList.get(0);
            File picFile = new File(path1);
            if (!picFile.exists()) {
                return;
            }
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), picFile);
            MultipartBody.Part part = MultipartBody.Part.createFormData("image", picFile.getName(), requestBody);
            RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
            add(subscribeWith(mApi.uploadImage(tokenBody, part), picObserver));
        }
    }
}
