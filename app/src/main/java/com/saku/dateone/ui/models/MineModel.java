package com.saku.dateone.ui.models;

import android.text.TextUtils;

import com.saku.dateone.DateApplication;
import com.saku.dateone.ui.contracts.MineContract;
import com.saku.dateone.utils.IconUploadHelper;
import com.saku.lmlib.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class MineModel extends UserInfoModel<MineContract.P> implements MineContract.M {

    private IconUploadHelper mIconUploadHelper;

    public MineModel(MineContract.P p) {
        super(p);
    }


    @Override
    public void uploadIcon(String iconPath) {
        if (TextUtils.isEmpty(iconPath)) {
            return;
        }
        final String picCacheFolder = FileUtils.getExternalCacheFolder(DateApplication.getAppContext()) + File.separator + "userIcon";
        mIconUploadHelper = new IconUploadHelper(mApi);
        final ArrayList<String> pics = new ArrayList<>();
        pics.add(iconPath);
        if (mComDisposable ==  null) {
            mComDisposable = new CompositeDisposable();
        }
        mIconUploadHelper.compressPics(picCacheFolder, pics, mComDisposable);
    }

    @Override
    public void destroy() {
        super.destroy();
        if (mIconUploadHelper != null) {
            mIconUploadHelper.clear();
            mIconUploadHelper = null;
        }
    }

}
