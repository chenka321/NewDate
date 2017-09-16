package com.saku.dateone.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
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
    private List<String> mCompressedPaths;
    private List<String> mUploadedPaths;  // 已经上传过的

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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        List<String> picList = intent.getStringArrayListExtra(UPLOAD_PICS);
        final String picCacheFolder = FileUtils.getExternalCacheFolder(DateApplication.getAppContext()) + File.separator + "morePics";
        compressPics(picCacheFolder, (ArrayList<String>) picList);

        return START_NOT_STICKY;
    }

    protected void add(Disposable disposable) {
        if (mComDisposable == null || mComDisposable.isDisposed()) {
            mComDisposable = new CompositeDisposable();
        }
        mComDisposable.add(disposable);
    }

    private void clearDisposable() {
        if (mComDisposable != null && !mComDisposable.isDisposed()) {
            mComDisposable.dispose();
        }
        mComDisposable = null;
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
                    if (new File(destFilePath).exists()) {
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
                        LLog.d("lm", "CompleteInfoPresenter ------ accept: ---s:" + resultList.toArray() + " resultSize = " + resultList.size());
                        prepareUploadRequest(resultList);
                    }
                });


        mComDisposable.add(compressDsp);

        return mCompressedPaths;
    }

    private void prepareUploadRequest(final List<String> compressedList) {
        if (compressedList == null || compressedList.size() == 0) {
            return;
        }

        final String token = UserInfoManager.getInstance().getMyShowingInfo().token;
        if (TextUtils.isEmpty(token)) {
            return;
        }

        removeAlreadyUploaded(compressedList);

        final Disposable uploadDsp = Observable.fromIterable(compressedList)
                .flatMap(new Function<String, ObservableSource<ApiResponse<String>>>() {
                    @Override
                    public ObservableSource<ApiResponse<String>> apply(@NonNull String path) throws Exception {
                        Log.d("lm", "flatMap ------ apply: " + path);
                        return doUploadPic(path, token);
                    }
                }).zipWith(Observable.range(0, compressedList.size()), new BiFunction<ApiResponse<String>, Integer, String>() {
                    @Override
                    public String apply(@NonNull ApiResponse<String> stringApiResponse, @NonNull Integer integer) throws Exception {
                        Log.d("lm", "zipWith ------ response: " + stringApiResponse.getCode() + "， int = " + integer);
                        return stringApiResponse.getMsg();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d("lm", "UploadPicService ------ onNext: " + s);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("lm", "UploadPicService ------ throwable: " + throwable.getMessage());
                        stopSelf();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        Log.d("lm", "UploadPicService ------ complete: ");
                        mUploadedPaths.addAll(compressedList);
                        stopSelf();
                    }
                });

        mComDisposable.add(uploadDsp);
    }

    private void removeAlreadyUploaded(List<String> compressedList) {
        if (mUploadedPaths == null) {
            mUploadedPaths = new ArrayList<>();
            return;
        }

        final Iterator<String> iterator = compressedList.iterator();
        while (iterator.hasNext()) {
            final String next = iterator.next();
            if (mUploadedPaths.contains(next)) {
                iterator.remove();
            }
        }
    }

    @Nullable
    private ObservableSource<ApiResponse<String>> doUploadPic(@NonNull String path, String token) {
        if (TextUtils.isEmpty(path) || TextUtils.isEmpty(token)) {
            return null;
        }
        File picFile = new File(path);
        if (!picFile.exists()) {
            return null;
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), picFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("image", picFile.getName(), requestBody);
        RequestBody tokenBody = RequestBody.create(MediaType.parse("multipart/form-data"), token);
        return mApi.uploadImage(tokenBody, part);
    }

}
