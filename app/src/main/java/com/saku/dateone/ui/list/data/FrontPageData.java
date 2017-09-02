package com.saku.dateone.ui.list.data;

import com.saku.dateone.ui.bean.TagString;
import com.saku.lmlib.list.data.ItemData;

import java.util.List;

public class FrontPageData implements ItemData {
    public String userImg;
    public String name;
    public int age;
    public String ocupation;
    public String residence; // 户籍
    public String city; // 现居城市
    public List<TagString> tags; // 企业高管，多才多艺
}
