package com.saku.dateone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * view相关的公共方法抽取，便于在presenter中使用
 */
public interface ViewUnion {
    void showLoading();

    void dismissLoading();

    Context getViewContext();

    void toActivity(Intent intent, boolean finishSelf);

    void addFragment(Fragment newFrag);
}
