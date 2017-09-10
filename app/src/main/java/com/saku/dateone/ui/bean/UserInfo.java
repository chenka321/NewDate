package com.saku.dateone.ui.bean;

import java.util.List;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 15:43
 * Description: 用户信息
*/
public class UserInfo {
    public int childGender; // 1 男， 2 女
//    public LocationInfo yourLocation = new LocationInfo();
//    public LocationInfo childLocation = new LocationInfo();

//    public boolean isLogin;
    public String token;

    //******* 基本信息

    public long userId;
    public String name;
    // TODO: 2017/9/10 用户图片
    public String image;  // 缺字段
    public String bornLocation; // 省市县
    public String currentLocation; // 省市县
    public String birthday;
    public String education; // 学历
    public String company;  // 所在公司
    public String position;
    public String income;
    public boolean isCollected;

    public boolean hasMoreInfo = true;

    //****** 更多信息
    public String height;
    public String house;
    // TODO: 2017/9/10 房产位置
    public String estateLocation;
    public String car;
    public String school;
    public String schoolType;
    public String hobby;
    public String moreIntroduce;
    public List<String> pics;

}
