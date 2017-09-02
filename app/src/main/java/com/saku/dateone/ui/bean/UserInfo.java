package com.saku.dateone.ui.bean;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 15:43
 * Description: 用户信息
*/
public class UserInfo {

    private static volatile UserInfo sInstance;

    private UserInfo() {

    }

    public static UserInfo getInstance() {
        if (sInstance == null) {
            synchronized (UserInfo.class) {
                sInstance = new UserInfo();
            }
        }
        return sInstance;
    }

    public int childGender; // 1 男， 2 女
    public LocationInfo yourLocation = new LocationInfo();
    public LocationInfo childLocation = new LocationInfo();

    public boolean isLogin;
}
