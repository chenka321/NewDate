package com.saku.dateone.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 返回的所有字典类型
 */
public class TypeConfig implements Serializable {

    @SerializedName("income")
    @Expose
    public List<Dict> income;
    @SerializedName("schoolType")
    @Expose
    public List<Dict> schoolType;
    @SerializedName("education")
    @Expose
    public List<Dict> education;
    @SerializedName("gender")
    @Expose
    public List<Dict> gender;
    @SerializedName("companyType")
    @Expose
    public List<Dict> companyType;
    @SerializedName("car")
    @Expose
    public List<Dict> car;
    @SerializedName("userType")
    @Expose
    public List<Dict> userType;
    @SerializedName("house")
    @Expose
    public List<Dict> house;
    private final static long serialVersionUID = 4828538836181288831L;

}
