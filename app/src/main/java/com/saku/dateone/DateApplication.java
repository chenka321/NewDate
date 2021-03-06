package com.saku.dateone;

import android.app.Application;
import android.content.Context;

import com.saku.dateone.internet.ApiManager;
import com.saku.dateone.utils.TypeManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.PreferenceUtil;
import com.saku.lmlib.utils.SystemUtil;

import io.reactivex.disposables.CompositeDisposable;

public class DateApplication extends Application {

    private static final String TAG = "DateApplication";

    public static Context mAppContext;

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    public void onCreate() {
        super.onCreate();
        LLog.setAllowLog(BuildConfig.DEBUG);

        mAppContext = this;

        UserInfoManager.getInstance().recoverMyInfoFromPreference();

    }

    public static Context getAppContext() {
        return mAppContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

}
