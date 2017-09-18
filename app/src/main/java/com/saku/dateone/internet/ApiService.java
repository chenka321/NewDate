package com.saku.dateone.internet;

import com.saku.dateone.bean.Article;
import com.saku.dateone.bean.LoginData;
import com.saku.dateone.bean.MyMsg;
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
import retrofit2.http.FormUrlEncoded;
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

    @FormUrlEncoded
    @POST("/api/commonuser/login")
    Observable<ApiResponse<LoginData>> login(@Field("phone") String phone);

    /**  未登录时的信息推荐*/
    @FormUrlEncoded
    @POST("/api/recommend/show/unlogin")
    Observable<ApiResponse<List<UserInfo>>> getNotLoginRecommend(@FieldMap Map<String, Object> map);

    /**  登录时的信息推荐*/
    @FormUrlEncoded
    @POST("/api/recommend/show/login")
    Observable<ApiResponse<List<UserInfo>>> getLoginRecommend(@Field("token") String token, @Field("page") String page);

    /**  用户详情 */
    @POST("/api/commonuser/getDetail")
    @FormUrlEncoded
    Observable<ApiResponse<UserInfo>> getUserInfo(@Field("token") String token, @Field("targetUserId") long userId);

    /**  保存用户信息*/
    @POST("/api/commonuser/save")
    @FormUrlEncoded
    Observable<ApiResponse<String>> saveUserInfo(@FieldMap Map<String, Object> map);

    /**  保存子女基本信息、补充信息*/
    @POST("api/children/save")
    @FormUrlEncoded
    Observable<ApiResponse<String>> saveUserChildInfo(@FieldMap Map<String, Object> map);

    /**  删除或添加收藏*/
    @POST("/api/collect/save")
    @FormUrlEncoded
    Observable<ApiResponse<Boolean>> saveCollection(@Field("token") String token, @Field("targetUserId") long userId);

    /**  发现列表*/
    @POST("/api/collect/save")
    @FormUrlEncoded
    Observable<ApiResponse<List<Article>>> discover(@Field("userId") long userId, @Field("lastArticleId") String lastArticleId);

    /** 发现详情 */
    @POST("/api/article/getDetail")
    @FormUrlEncoded
    Observable<ApiResponse> getArticleDetail(@Field("articleId") String articleId);

    /**  收藏列表展示 */
    @POST("/api/collect/getAll")
    @FormUrlEncoded
    Observable<ApiResponse<List<UserInfo>>> getCollectionList(@Field("token") String token);

    /**  消息提醒 */
    // TODO: 2017/9/19 我的消息接口名称
    @POST("/api/collect/getAll")
    @FormUrlEncoded
    Observable<ApiResponse<List<MyMsg>>> getMessage(@Field("userId") long userId);

    /**  字典列表 */
    @GET("/api/dict/query")
    Observable<ApiResponse<TypeConfig>> getTypeConfig();

    /**  意见反馈 */
    @POST("/api/feedback/save")
    @FormUrlEncoded
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
