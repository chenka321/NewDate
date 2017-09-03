package com.saku.dateone.ui.bean;

/**
 * User: liumin
 * Date: 2017-9-1
 * Time: 18:23
 * Description: 和用户聊天人的信息 展示基本信息的bean
*/
public class OppoBasicInfo {
    public long targetUserId;
    public String name;
    public String bornLocation; // 省市县
    public String currentLocation; // 省市县
    public String birthday;
    public String degree;
    public String company;
    public String position;
    public String income;

    public boolean hasMoreInfo;

}
