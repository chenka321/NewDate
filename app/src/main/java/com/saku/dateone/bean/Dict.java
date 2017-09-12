package com.saku.dateone.bean;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.saku.lmlib.model.SingleWheelData;

/**
 * 字典bean， 继承singleWheelData为了显示在选择器上
 */
public class Dict extends SingleWheelData implements Serializable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("dictType")
    @Expose
    public String dictType;
    @SerializedName("dictValue")  // 类型名称： 车辆，学历等
    @Expose
    public String dictValue;
    @SerializedName("dictDesc")  // 用来显示的学历： 大专
    @Expose
    public String dictDesc;
    private final static long serialVersionUID = 958795250172449314L;

}