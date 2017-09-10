package com.saku.dateone.utils;

/**
 * Created by liumin on 2017/9/6.
 */

public class Consts {
    /** 地址，性别 */
    public static final String HAS_BASIC_INFO = "has_basic_info";
    /**
     * {@link PageManager}，   从哪个页面进入登录页
     */
    public static final String LOGIN_FROM_PAGE_NAME = "login_from";
    /**  True表示要填写基本信息， false不要填写 仅做跳转页面的判断 */
    public static final String IS_FIRST_LOGIN = "isFristLogin";
    public static final String MY_TOKEN = "my_token";
    public static final String MY_USER_ID = "my_user_id";
    public static final String MY_USER_INFO = "myUserInfo";

    /** 从哪个页面进入填写详细信息页
     * {@link PageManager#COMPLETE_INFO_MY_SIMPLE_INFO} 等
     * */
    public static final String COMPLETE_FROM_PAGE_NAME = "complete_from";

    /** 回到mainTab需要显示哪个页面 */
    public static final String SHOW_MAIN_TAB_PAGE = "show_main_tab_page";

    /** 回到mainTab的推荐信息刷新已登录接口还是未登录接口 */
    public static final String REFRESH_RECOMMEND = "refresh_recommend";


    /* ***** 打开到登录页面的请求code START******/

    /** 从发现页出发 */
    public static final int LOGIN_RQST_DISCOVER = 101;
    /** 我的 */
    public static final int LOGIN_RQST_MINE = 102;
    /** 聊天列表 */
    public static final int LOGIN_RQST_CHAT_LIST = 103;
    /** 推荐列表 */
    public static final int LOGIN_RQST_RECOMMEND_LIST = 104;
    /** 对方子女信息展示页 */
    public static final int LOGIN_RQST_OPPO_INFO = 105;

    /* ***** 打开到登录页面的请求code END******/

    public static final int REFRESH_RECOMMEND_NOT_LOGIN = 10;
    public static final int REFRESH_RECOMMEND_LOGIN = 11;

    /** 收藏列表破还是推荐列表 1 是推荐， 2是收藏*/
    public static final String RECOMMEND_TYPE = "recommend_type";
}
