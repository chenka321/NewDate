package com.saku.dateone.ui.list.data;

import com.saku.dateone.ui.bean.TagString;
import com.saku.lmlib.list.data.ItemData;

import java.util.List;

public class FrontPageData implements ItemData {
    public String userId;
    public String name;
    public String bornLocation; // 省市县
    public String currentLocation; // 省市县
    public String birthday;
    public String education;

    public String userImg;
    public String ocupation;
    public List<TagString> tags; // 企业高管，多才多艺
}
