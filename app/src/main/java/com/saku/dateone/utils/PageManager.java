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
    private HashMap<String, Class> pageMap = new HashMap<>();

    public static String LOGIN = "login"; // 登录
    public static String RECOMMEND_LIST = "RecommendList"; // 历史聊天列表
    public static String SIMPLE_INFO = "SimpleInfo";
    public static String COMPLETE_INFO = "CompleteInfo";
    public static String CHAT_LIST = "ChatList"; // 历史聊天列表
    public static String DISCOVER_LIST = "DiscoverList"; // 发现列表
    public static String MINE = "Mine"; // 我的

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
    public Class<? extends Activity> getPageClass(String pageName) {
        return pageMap.get(pageName);
    }
}
