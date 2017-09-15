package com.saku.dateone.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.saku.dateone.DateApplication;
import com.saku.dateone.internet.ApiManager;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.ApiService;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.utils.FileUtils;
import com.saku.lmlib.utils.ImageUtils;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadPicService extends Service {
    public static final String UPLOAD_PICS = "upload_pics";
    private CompositeDisposable mComDisposable;
    private ApiService mApi;
    private List<String> mPicList;
    private List<String> mCompressedPaths;

    @Override
    public void onCreate() {
        super.onCreate();
        mApi = ApiManager.getAllApis().getDateApis();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearDisposable();
        LLog.d("lm", "UploadPicService ------ onDestroy: ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPicList = intent.getStringArrayListExtra(UPLOAD_PICS);
        final String picCacheFolder = FileUtils.getExternalCacheFolder(DateApplication.getAppContext()) + File.separator + "morePics";
        compressPics(picCacheFolder, (ArrayList<String>) mPicList);

        return START_NOT_STICKY;
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
        if (mComDisposable != null ) {
            mComDisposable.clear();
            mComDisposable = null;
        }

    }

    protected <T, D> RespObserver<T, D> subscribeWith(Observable<T> observable, RespObserver<T, D> observer) {
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
        return null;
    }

    /**
     * 压缩图片
     */
    private List<String> compressPics(final String picCacheFolder, ArrayList<String> picList) {
        if (picList == null) {
            return null;
        }
        File cacheFolder = new File(picCacheFolder);
        if (!cacheFolder.exists()) {
            cacheFolder.mkdir();
        }
        final int destSize = UIUtils.convertDpToPx(240, DateApplication.getAppContext());
        if (mCompressedPaths == null) {
            mCompressedPaths = new ArrayList<>();
        }

        if (mComDisposable == null) {
            mComDisposable = new CompositeDisposable();
        }

        final Disposable compressDsp = Observable.fromArray(picList).flatMap(new Function<ArrayList<String>, ObservableSource<List<String>>>() {
            @Override
            public ObservableSource<List<String>> apply(@NonNull ArrayList<String> strings) throws Exception {
                for (String srcPath : strings) {
                    final String destFilePath = picCacheFolder + File.separator + srcPath.substring(srcPath.lastIndexOf("/") + 1);
                    if (mCompressedPaths.contains(destFilePath)) {
                        Log.d("lm", "CompleteInfoPresenter ------ apply: 已经压缩过了 ");
                        continue;
                    }
                    final Bitmap bitmap = ImageUtils.compressImage(srcPath, destSize, destSize);
                    ImageUtils.saveBitmapToFile(bitmap, destFilePath);
                    mCompressedPaths.add(destFilePath);
                }

                return Observable.fromArray(mCompressedPaths);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> resultList) throws Exception {
                        LLog.d("lm", "CompleteInfoPresenter ------ accept: ---s:" + resultList.toArray() + " resultSize = " + mCompressedPaths.size());
                        prepareUploadRequest(resultList);
                    }
                });


        mComDisposable.add(compressDsp);

        return mCompressedPaths;
    }


    private void prepareUploadRequest(List<String> compressedList) {
        if (compressedList == null) {
            return;
        }
        final String token = UserInfoManager.getInstance().getMyShowingInfo().token;
//        final RespObserver<ApiResponse<String>, String> picObserver = getUploadPicObserver(0);

        final Disposable uploadDsp = Observable.fromIterable(compressedList)
                .flatMap(new Function<String, ObservableSource<ApiResponse<String>>>() {
                    @Override
                    public ObservableSource<ApiResponse<String>> apply(@NonNull String path) throws Exception {
                        File picFile = new File(path);
                        if (!picFile.exists()) {
                            return null;
                        }
                        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), picFile);
                        MultipartBody.Part part = MultipartBody.Part.createFormData("image", picFile.getName(), requestBody);
                        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
//                        add(subscribeWith(mApi.uploadImage(tokenBody, part), getUploadPicObserver()));

                        return mApi.uploadImage(tokenBody, part);
                    }
                }).concatMap(new Function<ApiResponse<String>, ObservableSource<Integer>>() {
                    @Override
                    public ObservableSource<Integer> apply(@NonNull ApiResponse<String> stringApiResponse) throws Exception {
                        Log.d("lm", "UploadPicService ------ concatMap : " + stringApiResponse.getMsg());
                        return Observable.just(stringApiResponse.getCode());
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer s) throws Exception {
                        Log.d("lm", "UploadPicService ------ onnext : " + s);
//                        clear();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("lm", "UploadPicService ------ onerr : " + throwable.getMessage());
//                        clear();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("lm", "UploadPicService ------ onComplete : ");
//                        clear();
                    }
                });


        mComDisposable.add(uploadDsp);
    }

    private void clear() {
        stopSelf();
        if (mComDisposable != null && !mComDisposable.isDisposed()) {
            mComDisposable.dispose();
            mComDisposable = null;
        }

    }


}
