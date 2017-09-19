package com.saku.dateone.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saku.lmlib.list.data.ItemData;

import java.io.Serializable;
import java.util.List;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 15:43
 * Description: 用户信息
 */
public class UserInfo implements ItemData, Serializable{
    public int childGender; // 1 男， 2 女
//    public LocationInfo yourLocation = new LocationInfo();
//    public LocationInfo childLocation = new LocationInfo();

    //    public boolean isLogin;
    @SerializedName("token")
    @Expose
    public String token;

    //******* 基本信息

    @SerializedName("hasMoreInfo")
    @Expose
    public boolean hasMoreInfo = true;

    @SerializedName("userId")
    @Expose
    public long userId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("parentId")
    @Expose
    public int parentId;
    @SerializedName("gender")
    @Expose
    public int gender;
    @SerializedName("bornLocation")
    @Expose
    public String bornLocation;
    @SerializedName("currentLocation")
    @Expose
    public String currentLocation;
    @SerializedName("birthday")
    @Expose
    public String birthday;
    @SerializedName("height")
    @Expose
    public int height;
    @SerializedName("education")
    @Expose
    public int education;
    @SerializedName("profession")  // 职业
    @Expose
    public String profession;
    @SerializedName("company")
    @Expose
    public int company;
    @SerializedName("position")
    @Expose
    public String position;
    @SerializedName("schoolType")
    @Expose
    public int schoolType;
    @SerializedName("school")
    @Expose
    public String school;
    @SerializedName("income")
    @Expose
    public int income;
    @SerializedName("car")
    @Expose
    public int car;
    @SerializedName("house")
    @Expose
    public int house;
    @SerializedName("photo")
    @Expose
    public List<String> photo;  // 更多照片
    @SerializedName("userImage")
    @Expose
    public String userImage;  // 用户头像
    @SerializedName("hobby")
    @Expose
    public String hobby;
    @SerializedName("score")
    @Expose
    public int score;
    @SerializedName("label")
    @Expose
    public List<TagString> label;  // 企业高管，多才多艺
    @SerializedName("createdate")
    @Expose
    public int createdate;
    @SerializedName("lastmodified")
    @Expose
    public int lastmodified;
    @SerializedName("parent")
    @Expose
    public UserInfo parent;

    @SerializedName("children")
    @Expose
    public UserInfo children;
    @SerializedName("isCollected")
    @Expose
    public boolean isCollected;
    //****** 更多信息
    @SerializedName("moreIntroduce")
    @Expose
    public String moreIntroduce;

    private final static long serialVersionUID = 6174277449626158915L;

}
