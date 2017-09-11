package com.saku.dateone.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by liumin on 2017/9/9.
 */

public class GsonUtils {
    private static volatile GsonUtils sGsonUtil;

    private Gson gson;
    private GsonUtils() {
    }

    public static GsonUtils getInstance() {
        if (sGsonUtil == null) {
            synchronized (GsonUtils.class) {
                sGsonUtil = new GsonUtils();
            }
        }
        return sGsonUtil;
    }

    public <T> String tojson(T sourceBean) {
        if (gson == null) {
            gson = new Gson();
        }
        Type type = new TypeToken<T>() {}.getType();
        return gson.toJson(sourceBean, type);
    }

    public Gson getGson() {
        if (gson == null) {
//            gson = new Gson();
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        }
        return gson;
    }

}
