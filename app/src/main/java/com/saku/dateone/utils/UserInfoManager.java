package com.saku.dateone.utils;

import android.text.TextUtils;
import android.util.Log;

import com.saku.dateone.DateApplication;
import com.saku.dateone.ui.bean.UserInfo;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.PreferenceUtil;

/**
 * Created by liumin on 2017/9/9.
 */

public class UserInfoManager {
    private static volatile UserInfoManager sInstance;

    private UserInfo mMyPendingInfo;  // 待填入的信息
    private UserInfo mOppoShowingInfo;  // 从服务端获取的用于显示对方信息的userInfo
    private UserInfo mMyShowingInfo;  // 从服务端获取的用于显示自己信息的userInfo, 逻辑判断

    private UserInfoManager() {

    }

    public static UserInfoManager getInstance() {
        if (sInstance == null) {
            synchronized (UserInfoManager.class) {
                sInstance = new UserInfoManager();
            }
        }
        return sInstance;
    }

    public UserInfo getMyPendingInfo() {
        if (mMyPendingInfo == null) {
            mMyPendingInfo = new UserInfo();
        }
        return mMyPendingInfo;
    }

    public UserInfo getOppoShowingInfo() {
        return mOppoShowingInfo;
    }

    public void setOppoShowingInfo(UserInfo oppoUserInfo) {
        this.mOppoShowingInfo = oppoUserInfo;
    }

    public UserInfo getMyShowingInfo() {

        return mMyShowingInfo;
    }

    public void setMyShowingInfo(UserInfo myUserInfo) {
        this.mMyShowingInfo = myUserInfo;
    }

    public void recoverMyInfoFromPreference() {
        final boolean firstLogin = PreferenceUtil.getBoolean(DateApplication.getAppContext(), Consts.IS_FIRST_LOGIN, true);
        final String myToken = PreferenceUtil.getString(DateApplication.getAppContext(), Consts.MY_TOKEN, "");
        final long myUserId = PreferenceUtil.getLong(DateApplication.getAppContext(), Consts.MY_USER_ID, 0);

        final String myInfoStr = PreferenceUtil.getString(DateApplication.getAppContext(), Consts.MY_USER_INFO, "");

        if (!TextUtils.isEmpty(myInfoStr)) {
            final UserInfo userInfo = GsonUtils.getInstance().getGson().fromJson(myInfoStr, UserInfo.class);
            setMyShowingInfo(userInfo);
        }
    }


    public boolean isLogin() {
        if (mMyShowingInfo != null && !TextUtils.isEmpty(mMyShowingInfo.token)) {
            return true;
        }
        final String myToken = PreferenceUtil.getString(DateApplication.getAppContext(), Consts.MY_TOKEN, "");
        return !TextUtils.isEmpty(myToken);
    }

    /**
     * @param isFirstLogin 用户是否是有基本信息
     */
    public void setLoginState(String myToken, boolean isFirstLogin) {
        PreferenceUtil.putBoolean(DateApplication.getAppContext(), Consts.IS_FIRST_LOGIN, isFirstLogin);
        PreferenceUtil.putString(DateApplication.getAppContext(), Consts.MY_TOKEN, myToken);
        if (mMyShowingInfo != null) {
            mMyShowingInfo.token = myToken;
        }
    }

    /**
     * 是否填写了基本信息
     */
    public boolean isFirstLogin() {
        if (hasSimpleLocal()) {
            Log.d("lm", " userInfoManager isFirstLogin hasSimpleInfo : ");
            return false;
        }
        Log.d("lm", "userInfoManager IS_FIRST_LOGIN : "+ PreferenceUtil.getBoolean(DateApplication.getAppContext(), Consts.IS_FIRST_LOGIN, true));

        return PreferenceUtil.getBoolean(DateApplication.getAppContext(), Consts.IS_FIRST_LOGIN, true);
    }

    /**
     * @param firstLogin 用户是否是有基本信息
     */
    public void setFirstLogin(boolean firstLogin) {
        PreferenceUtil.putBoolean(DateApplication.getAppContext(), Consts.IS_FIRST_LOGIN, firstLogin);
    }


    /**
     * 【已废弃】
     * 是否有基本信息（姓名，出生日期，学历）&& 后端是否有保存基本信息
     * return false, 需要请求用户信息接口
     */
    @Deprecated
    public boolean hasBasicInfo() {
        final boolean isFirstlogin = PreferenceUtil.getBoolean(DateApplication.getAppContext(), Consts.IS_FIRST_LOGIN, true);
        return  (!isFirstlogin && hasSimpleLocal());
    }


    /**
     * 本地 是否有基本信息（姓名，出生日期，学历）， 没有则先请求用户详情接口，再判断一次，没有基本信息则进入基本信息页面
     */
    public boolean hasSimpleLocal() {
        final UserInfo showingInfo = getMyShowingInfo();
        return showingInfo != null
                && !(TextUtils.isEmpty(showingInfo.name)
                && !TextUtils.isEmpty(showingInfo.birthday)
                && !TextUtils.isEmpty(showingInfo.education));
    }

    /**
     * 每次用户填写数据都保存到showing中
     */
    public void copyPendingToShowing() {
        LLog.d("lm", " ------------- copyPendingToShowing: ");
        if (mMyPendingInfo != null) {
            final String tojson = GsonUtils.getInstance().tojson(mMyPendingInfo);
            PreferenceUtil.putString(DateApplication.getAppContext(), Consts.MY_USER_INFO, tojson);
            setMyShowingInfo(mMyPendingInfo);
        }
    }

    /**
     * 拉取用户信息接口后保存showing
     */
    public void saveShowingInfo(UserInfo showingInfo) {
        LLog.d("lm", " -------------  saveShowingInfo: ");

        final String userInfoJson = GsonUtils.getInstance().tojson(showingInfo);
        PreferenceUtil.putString(DateApplication.getAppContext(), Consts.MY_USER_INFO, userInfoJson);
        setMyShowingInfo(showingInfo);
    }
}
