package com.saku.dateone.bean;

import com.saku.lmlib.list.data.ItemData;

/**
 * Created by liumin on 2017/9/11.
 */

public class MyMsg implements ItemData {
    public long id;
    public long time;
    public int type; // 消息类型 消息的类型（推荐新用户消息、IM消息、系统消息）
    public String content;
    public String url;
}
