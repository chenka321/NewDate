package com.saku.dateone;

import android.app.Application;
import android.content.Context;

import com.saku.dateone.utils.TypeManager;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.SystemUtil;

public class DateApplication extends Application {

    private static final String TAG = "DateApplication";

    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    public void onCreate() {
        super.onCreate();
        LLog.setAllowLog(BuildConfig.DEBUG);
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
