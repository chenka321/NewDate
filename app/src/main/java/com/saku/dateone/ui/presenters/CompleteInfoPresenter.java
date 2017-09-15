package com.saku.dateone.ui.presenters;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.format.DateFormat;
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
import com.saku.lmlib.utils.FileUtils;
import com.saku.lmlib.utils.ImageUtils;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CompleteInfoPresenter extends ABasePresenter<CompleteInfoContract.V, CompleteInfoContract.M> implements CompleteInfoContract.P {

    private ArrayList<String> mPicList;
    private List<String> mCompressedPaths;
    private CompositeDisposable mPicCompressCmp;

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
        mModel.uploadPics(mPicList);
    }

    @Override
    public void onMatchCompleteResult(int code, String msg) {
        if (mView == null) {
            return;
        }

        onFillResult(Consts.REFRESH_RECOMMEND_LOGIN);

//        UserInfoManager.getInstance().copyPendingToShowing();
//        mView.goNextOnCompleteInfo();
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
     * 保存选择的图片路径
     */
    public void setPicList(ArrayList<String> picList) {
        this.mPicList = picList;
        UserInfoManager.getInstance().getMyPendingInfo().photo = mPicList;
        final String picCacheFolder = FileUtils.getExternalCacheFolder(mView.getViewContext()) + File.separator + "morePics";

        UserInfoManager.getInstance().setUploadPicPaths(compressPics(picCacheFolder, mPicList));
    }

    /**
     * 压缩图片
     */
    private List<String> compressPics(final String picCacheFolder, ArrayList<String> picList) {
        if (picList == null) {
            return null;
        }
        File folder = new File(picCacheFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }
        final int destSize = UIUtils.convertDpToPx(80, mView.getViewContext());
        if (mCompressedPaths == null) {
            mCompressedPaths = new ArrayList<>();
        }

        if (mPicCompressCmp == null) {
            mPicCompressCmp = new CompositeDisposable();
        }
//        String name = new DateFormat().format("yyyyMMdd_hhmmss",
//                Calendar.getInstance(Locale.CHINA)) + ".jpg";
//        final String destFilePath = picCacheFolder + File.separator + name;
//        if (mCompressedPaths.contains(destFilePath)) {
//            Log.d("lm", "CompleteInfoPresenter ------ compressPics: 已经压缩过了");
//            return mCompressedPaths;
//        }

//        for (String picPath : picList) {
//            final File file = new File(picPath);
//            if (!file.exists()) {
//                continue;
//            }
//            final String destFilePath = picCacheFolder + File.separator + file.getName();
//            final Bitmap bitmap = ImageUtils.compressImage(picPath, destSize, destSize);
//
//            final Disposable disposable = Flowable.just(destFilePath).map(new Function<String, String>() {
//                @Override
//                public String apply(@NonNull String s) throws Exception {
//                    ImageUtils.saveBitmapToFile(bitmap, s);
//                    compressedPaths.add(destFilePath);
//                    return "1----1";
//                }
//            }).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Consumer<String>() {
//                @Override
//                public void accept(String s) throws Exception {
//                    LLog.d("lm", "CompleteInfoPresenter ------ accept: " + s);
//                    LLog.d("lm", "CompleteInfoPresenter ------ accept: --- resultSize = " + compressedPaths.size());
//                }
//            });
//            compressCmp.add(disposable);
//
////            ImageUtils.saveBitmapToFile(bitmap, destFilePath);
////            compressedPaths.add(destFilePath);
//        }

        final Disposable d = Observable.fromIterable(picList)
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String srcPath) throws Exception {

//                        final File destFile = new File(picCacheFolder, name);
                        final String destFilePath = picCacheFolder + File.separator + srcPath.substring(srcPath.lastIndexOf("/") + 1);
//                        final String destFilePath = picCacheFolder + File.separator + in.incrementAndGet()+".jpg";

                        if (mCompressedPaths.contains(destFilePath)) {
                            return " 已经压缩过了";
                        }
                        final Bitmap bitmap = ImageUtils.compressImage(srcPath, destSize, destSize);
                        ImageUtils.saveBitmapToFile(bitmap, destFilePath);
                        mCompressedPaths.add(destFilePath);
                        return destFilePath;
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LLog.d("lm", "CompleteInfoPresenter ------ accept: " + s);
                        LLog.d("lm", "CompleteInfoPresenter ------ accept: --- resultSize = " + mCompressedPaths.size());
                    }
                });
        mPicCompressCmp.add(d);

        return mCompressedPaths;
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
