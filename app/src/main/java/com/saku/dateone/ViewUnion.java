package com.saku.dateone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.saku.dateone.ui.activities.BaseActivity;

/**
 * view相关的公共方法抽取，便于在presenter中使用
 */
public interface ViewUnion {
    void showLoading();
    void showLoading(String text);

    void dismissLoading();

    Context getViewContext();

    void toActivity(Intent intent, boolean finishSelf);

    void toActivity(Class<? extends BaseActivity> clz, Bundle bundle, boolean finishSelf);

    /**
     * 去登录页面
     * @param pageName  {@link com.saku.dateone.utils.PageManager} 中的页面的名字对应的key
     * @param requestCode  打开登录页面时的请求code {@link com.saku.dateone.utils.Consts}中的登录请求code
     */
    void gotoLogin(int pageName, int requestCode);

    /**
     * 去登录页面
     * @param pageName  {@link com.saku.dateone.utils.PageManager} 中的页面的名字对应的key
     * @param requestCode  打开登录页面时的请求code {@link com.saku.dateone.utils.Consts}中的登录请求code
     * @param bundle 使用putExtras方式， 取的时候直接getIntent.get
     */
    void gotoLogin(int pageName, int requestCode, Bundle bundle);
    void addFragment(Fragment newFrag);

}
