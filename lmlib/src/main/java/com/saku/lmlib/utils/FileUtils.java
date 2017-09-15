package com.saku.lmlib.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtils {

    /**
     * //storage/emulated/0/Android/data/com.aa.bb/cache
     */
    public static String getExternalCacheFolder(Context context) {
        if (context == null) {
            return "";
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
                && context.getExternalCacheDir() != null) {
            return context.getExternalCacheDir().getAbsolutePath();  //storage/emulated/0/Android/data/com.aa.bb/cache
        }
        return "";
//        }else {
//            return context.getCacheDir().getAbsolutePath();   // /data/data/com.aa.bb/cache
//        }
    }

    public static String getJsonFromAssets(Context context, String fileName) {
        if (context == null) {
            return "";
        }
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(context.getAssets().open(fileName), "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStreamReader.close();
            bufferedReader.close();
            LLog.d("lm", "FileUtils ------ getJsonFromAssets: " + stringBuilder.toString());
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        File file = new File(filePath);
        return file.exists();
    }

    public static void closeIO(Closeable io) {
        try {
            io.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
