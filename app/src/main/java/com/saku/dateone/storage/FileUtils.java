package com.saku.dateone.storage;

import android.content.Context;
import android.os.Environment;

public class FileUtils {

    public static String getCacheFolder(Context context) {
        if (context == null) {
            return "";
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                && context.getExternalCacheDir() != null) {
            return context.getExternalCacheDir().getAbsolutePath();
        }
        return "";
//        }else {
//            return context.getCacheDir().getAbsolutePath();
//        }
    }
}
