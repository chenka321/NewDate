package com.saku.dateone.internet;

import com.saku.dateone.bean.Article;
import com.saku.dateone.bean.TypeConfig;
import com.saku.dateone.bean.UserInfo;

import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * 接口定义集合
 */
public interface ApiService {
    long CACHE_MAX_SIZE =  1024*1024*100;
    long READ_TIME_OUT = 10;
    long CONNECT_TIME_OUT = 10;
    String BASE_URL = "http://119.23.220.163:8080";


    @POST("/api/commonuser/login")
    Observable<ApiResponse<String>> login(@Field("phone") String phone);

//
//
//    @POST("/api/commonuser/login")
//    Flowable<ApiResponse<String>> login(@Field("phone") String phone);
//
//

    /**  未登录时的信息推荐*/
    @POST("/api/recommend/show/unlogin")
    Observable<ApiResponse<List<UserInfo>>> unloginRecommend(@FieldMap Map<String, Object> map);

    /**  登录时的信息推荐*/
    @POST("/api/recommend/show/login")
    Observable<ApiResponse<List<UserInfo>>> loginRecommend(@Field("token") String token, @Field("page") String page);

    /**  用户详情 */
    @POST("/api/commonuser/getDetail")
    Observable<ApiResponse<UserInfo>> getUserInfo(@Field("token") String token, @Field("targetUserId") String userId);

    /**  保存基本信息*/
    @POST("/api/commonuser/save")
    Observable<ApiResponse<String>> saveSimpleInfo(@FieldMap Map<String, String> map);

    /**  保存补充信息*/
    @POST("api/children/save")
    Observable<ApiResponse<String>> saveCompleteInfo(@FieldMap Map<String, String> map);

    /**  删除或添加收藏*/
    @POST("/api/collect/save")
    Observable<ApiResponse<Boolean>> saveCollection(@Field("token") String token, @Field("targetUserId") String userId);

    /**  发现列表*/
    @POST("/api/collect/save")
    Observable<ApiResponse<List<Article>>> discover(@Field("userId") long userId, @Field("lastArticleId") String lastArticleId);

    /** 发现详情 */
    @POST("/api/article/getDetail")
    Observable<ApiResponse> getArticleDetail(@Field("articleId") String articleId);

    /**  收藏列表展示 */
    @POST("/api/collect/getAll")
    Observable<ApiResponse<UserInfo>> getCollectionList(@Field("token") String token);

    /**  消息提醒 */
//    @POST("/api/collect/getAll")
//    Observable<ApiResponse> getMessage(@Field("token") String token);

    /**  字典列表 */
    @GET("/api/dict/query")
    Observable<ApiResponse<TypeConfig>> getTypeConfig();

    /**  意见反馈 */
    @POST("/api/feedback/save")
    Observable<ApiResponse<String>> submitComment(@Field("token") String token, @Field("content") String content);

    /**  上传照片 */
    @Multipart
    @POST("/api/upload/image")
    Observable<ApiResponse<String>> uploadImage(@Part("token")RequestBody token, @Part MultipartBody.Part imageBody);

    /**  用户头像上传 */
    @Multipart
    @POST("/api/upload/icon")
    Observable<ApiResponse<String>> uploadIcon(@Part("token")RequestBody token, @Part MultipartBody.Part imageBody);



}