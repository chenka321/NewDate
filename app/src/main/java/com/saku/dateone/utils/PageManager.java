package com.saku.dateone.utils;

import android.app.Activity;

import com.saku.dateone.ui.activities.CompleteInfoActivity;
import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.ui.activities.SimpleInfoActivity;
import com.saku.dateone.ui.fragments.DiscoversFragment;
import com.saku.dateone.ui.fragments.MineFragment;
import com.saku.dateone.ui.fragments.RecommendsFragment;

import java.util.HashMap;

/**
 * Created by liumin on 2017/9/8.
 */

public class PageManager {
    private static volatile PageManager sHelper;
    private HashMap<Integer, Class> pageMap = new HashMap<>();

    // 页面的名字对应的key
//    public static String LOGIN = "login"; // 登录
//    public static String RECOMMEND_LIST = "RecommendList"; // 历史聊天列表
//    public static String SIMPLE_INFO = "SimpleInfo";
//    public static String COMPLETE_INFO = "CompleteInfo";
//    public static String CHAT_LIST = "ChatList"; // 历史聊天列表
//    public static String DISCOVER_LIST = "DiscoverList"; // 发现列表
//    public static String MINE = "Mine"; // 我的

    /** 登录 */
    public static int LOGIN = 20;
    /** 推荐列表 */
    public static int RECOMMEND_LIST = 21;
    /** 填写基础信息页 */
    public static int SIMPLE_INFO = 22;
    /** 填写完整信息页 */
    public static int COMPLETE_INFO = 23;
    /** 历史聊天列表 */
    public static int CHAT_LIST = 24;
    /** 发现列表 */
    public static int DISCOVER_LIST = 25;
    /** 我的 */
    public static int MINE = 26;




   /* ***** 打开填写补充信息页的请求code START******/

    /** 我的-修改信息 */
    public static final int COMPLETE_INFO_MINE_MODIFY = 106;

    /** 我的-消息列表 */
    public static final int COMPLETE_INFO_MINE_MSG = 107;

    /** 对方子女信息展示页 */
    public static final int COMPLETE_INFO_OPPO_INFO = 108;

    /** 选照片 */
    public static final int COMPLETE_INFO_CHOOSE_PIC = 109;

    /** 我的基本信息填写页 */
    public static final int COMPLETE_INFO_MY_SIMPLE_INFO = 110;

    /** 首次进入性别位置填写页 */
    public static final int COMPLETE_INFO_GENDER_POSITOIN = 111;

   /* ***** 打开填写补充信息页的请求code END******/



    public static PageManager getInstance() {
        if (sHelper == null) {
            synchronized (PageManager.class) {
                sHelper = new PageManager();
            }
        }
        return sHelper;
    }

    public void addPage(String pageName) {
        pageMap.put(LOGIN, LoginActivity.class);
        pageMap.put(RECOMMEND_LIST, RecommendsFragment.class);
        pageMap.put(SIMPLE_INFO, SimpleInfoActivity.class);
        pageMap.put(COMPLETE_INFO, CompleteInfoActivity.class);
//        pageMap.put(CHAT_LIST, ChatListFragment.class);
        pageMap.put(DISCOVER_LIST, DiscoversFragment.class);
        pageMap.put(MINE, MineFragment.class);
    }
    public Class<? extends Activity> getPageClass(int pageName) {
        return pageMap.get(pageName);
    }
}
