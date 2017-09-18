package com.saku.dateone.bean;

import com.saku.lmlib.list.data.ItemData;

import java.io.Serializable;

/**
 * Created by liumin on 2017/9/12.
 * 发现 文章
 */
public class Article implements ItemData, Serializable {
    public String articleId;
    public String title;
    public String content;
    public String publishTime;
    public String contentUrl;
    public String image;
}
