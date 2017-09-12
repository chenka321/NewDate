package com.saku.dateone.internet;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 接口定义集合
 */
public interface ApiService {
    public static final long CACHE_MAX_SIZE =  1024*1024*100;
    public static final long READ_TIME_OUT = 10;
    public static final long CONNECT_TIME_OUT = 10;
    public static final String BASE_URL = "http://119.23.220.163:8080";


    /*** 这是一个示例
     * @param url 可以外部传入
     */
    @GET("{url}")
    Observable<ResponseBody> executeGet(
            @Path("url") String url,
            @QueryMap Map<String, String> maps);

    @POST("/api/commonuser/login")
    Observable<ApiResponse<String>> login(@Field("phone") String phone);

    /**
     * 未登录是的信息推荐
     */
    @GET("/api/recommend/show/unlogin")
    Observable<ApiResponse> unloginRecommend(@Field("phone") String phone);

}
