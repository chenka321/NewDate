package com.saku.lmlib.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class PageHelper {

    private static volatile PageHelper sInstance;

    private PageHelper() {

    }

    public static PageHelper getInstance() {
        if (sInstance == null) {
            synchronized (PageHelper.class) {
                sInstance = new PageHelper();
            }
        }
        return sInstance;
    }

    public void startActivity(Context context, Intent intent) {
        if (context == null) {
            return;
        }
        context.startActivity(intent);
    }

    public void addFragment(Context context, FragmentManager fm, Fragment newFrag) {
        if (context == null) {
            return;
        }

        final FragmentTransaction transaction = fm.beginTransaction();
        if (newFrag.isAdded()) {
            transaction.show(newFrag).commit();
        } else {
            transaction.add(newFrag, newFrag.getClass().getName())
                    .addToBackStack(newFrag.getClass().getName())
                    .commit();
        }
    }


    /**
     * 切换页面的，优化了fragment的切换， 这样就能做到多个Fragment切换不重新实例化
     */
    public void switchFragment(FragmentManager fm, Fragment from, Fragment to) {
        if (from == null || to == null) return;
//        FragmentTransaction transaction = fm.beginTransaction().setCustomAnimations(R.anim.tran_pre_in, R.anim.tran_pre_out);
        FragmentTransaction transaction = fm.beginTransaction();
        if (!to.isAdded()) {
            transaction
                    .hide(from)
                    .add(to, to.getClass().getName())
                    .addToBackStack(to.getClass().getName())
                    .commit();
        } else {
            transaction.hide(from).show(to).commit();
        }
    }
}
