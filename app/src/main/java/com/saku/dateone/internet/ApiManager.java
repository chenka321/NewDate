package com.saku.dateone.internet;

import com.saku.dateone.DateApplication;
import com.saku.dateone.storage.FileUtils;
import com.saku.dateone.utils.GsonUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.saku.dateone.internet.ApiService.BASE_URL;
import static com.saku.dateone.internet.ApiService.CACHE_MAX_SIZE;
import static com.saku.dateone.internet.ApiService.CONNECT_TIME_OUT;
import static com.saku.dateone.internet.ApiService.READ_TIME_OUT;

public class ApiManager {
    private static volatile ApiManager sAllApis;
    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;

    private ApiService mApiService;

    private ApiManager() {}

    public static ApiManager getAllApis() {
        if (sAllApis == null) {
            synchronized (ApiManager.class) {
                if (sAllApis == null) {
                    sAllApis = new ApiManager();
                }
            }
        }
        return sAllApis;
    }


    public OkHttpClient getHttpClient() {
        if (mOkHttpClient != null) {
            return mOkHttpClient;
        }
        File file = new File(FileUtils.getCacheFolder(DateApplication.getAppContext()));
        Cache cache = new Cache(file, CACHE_MAX_SIZE);
//        CacheInterceptor cacheInterceptor = new CacheInterceptor(in);
        final HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new ApiCacheInterceptor())
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .cache(cache)
                .build();
        return mOkHttpClient;
    }

    public Retrofit getRetrofit() {
        if (mRetrofit != null) {
            return mRetrofit;
        }
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonUtils.getInstance().getGson()))
                .build();
        return mRetrofit;
    }

    public ApiService getDateApis() {
        if (mApiService == null) {
            mApiService = getRetrofit().create(ApiService.class);
        }
        return mApiService;
    }
}
