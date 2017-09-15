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
//        mView.openUploadService(mCompressedPaths);  // 压缩完成后直接上传
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
//
//    /**
//     * 保存选择的图片路径
//     */
//    public void setPicList(ArrayList<String> picList) {
//        this.mPicList = picList;
//        UserInfoManager.getInstance().getMyPendingInfo().photo = mPicList;
//        final String picCacheFolder = FileUtils.getExternalCacheFolder(mView.getViewContext()) + File.separator + "morePics";
//
//        UserInfoManager.getInstance().setUploadPicPaths(compressPics(picCacheFolder, mPicList));
//    }

//    /**
//     * 压缩图片
//     */
//    private List<String> compressPics(final String picCacheFolder, ArrayList<String> picList) {
//        if (picList == null) {
//            return null;
//        }
//        File cacheFolder = new File(picCacheFolder);
//        if (!cacheFolder.exists()) {
//            cacheFolder.mkdir();
//        }
//        final int destSize = UIUtils.convertDpToPx(240, mView.getViewContext());
//        if (mCompressedPaths == null) {
//            mCompressedPaths = new ArrayList<>();
//        }
//
//        if (mPicCompressCmp == null) {
//            mPicCompressCmp = new CompositeDisposable();
//        }
//
//        final Disposable disposable = Observable.fromArray(picList).flatMap(new Function<ArrayList<String>, ObservableSource<List<String>>>() {
//            @Override
//            public ObservableSource<List<String>> apply(@NonNull ArrayList<String> strings) throws Exception {
//                for (String srcPath : strings) {
//                    final String destFilePath = picCacheFolder + File.separator + srcPath.substring(srcPath.lastIndexOf("/") + 1);
//                    if (mCompressedPaths.contains(destFilePath)) {
//                        Log.d("lm", "CompleteInfoPresenter ------ apply: 已经压缩过了 ");
//                        continue;
//                    }
//                    final Bitmap bitmap = ImageUtils.compressImage(srcPath, destSize, destSize);
//                    ImageUtils.saveBitmapToFile(bitmap, destFilePath);
//                    mCompressedPaths.add(destFilePath);
//                }
//
//                return Observable.fromArray(mCompressedPaths);
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<List<String>>() {
//                    @Override
//                    public void accept(List<String> strings) throws Exception {
//                        LLog.d("lm", "CompleteInfoPresenter ------ accept: ---s:" + strings.toArray() + " resultSize = " + mCompressedPaths.size());
//                        if (mView != null) {
//                            mView.openUploadService(mCompressedPaths);
//                        }
//                    }
//                });
//
//
//        mPicCompressCmp.add(disposable);
//
//        return mCompressedPaths;
//    }

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
