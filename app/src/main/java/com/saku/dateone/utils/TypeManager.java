package com.saku.dateone.utils;

import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.JsonSyntaxException;
import com.saku.dateone.DateApplication;
import com.saku.dateone.bean.Dict;
import com.saku.dateone.bean.TypeConfig;
import com.saku.dateone.storage.FileUtils;
import com.saku.lmlib.utils.PreferenceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liumin on 2017/9/6.
 */

public class TypeManager {
    private static final String DICT_TYPES = "dict_types";
    private static TypeManager sManager;

    private SparseArray<String> incomeMap;
    private SparseArray<String> gendersMap;
    private SparseArray<String> companyTypeMap; // key是显示的id// , value是type 的name， 用于请求或者显示
    private SparseArray<String> housesMap;
    private SparseArray<String> carsMap;
    private SparseArray<String> schoolTypesMap;
    private SparseArray<String> educationMap;
    private SparseArray<String> userTypeMap;
    private TypeConfig mTypeConfig; // 从后端拿到的字典类型列表
    private Map<String, List<Dict>> mAllTypes;  // key = dictValue

    private TypeManager() {
    }

    public static TypeManager getInstance() {
        if (sManager == null) {
            synchronized (TypeManager.class) {
                sManager = new TypeManager();
            }
        }
        return sManager;
    }

    /**
     * 请求字典接口成功或失败都需要调
     */
    public void setTypeConfig(TypeConfig config) {
        if (config == null) {
            String dictsCacheStr = PreferenceUtil.getString(DateApplication.getAppContext(), DICT_TYPES, "");
            TypeConfig typeConfig = null;
            if (TextUtils.isEmpty(dictsCacheStr)) {
                dictsCacheStr = FileUtils.getJsonFromAssets(DateApplication.getAppContext(), "config.json");
            }
            try {
                typeConfig = GsonUtils.getInstance().getGson().fromJson(dictsCacheStr, TypeConfig.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
            this.mTypeConfig = typeConfig == null ? new TypeConfig() : typeConfig;
        } else {
            final String dictsStr = GsonUtils.getInstance().tojson(config);
            PreferenceUtil.putString(DateApplication.getAppContext(), DICT_TYPES, dictsStr);
            this.mTypeConfig = config == null ? new TypeConfig() : config;
        }
        configToShowMap(mTypeConfig);
        configToSourceMap();
        setTypeConfigShowingText(mTypeConfig);
    }

    public TypeConfig getTypeConfig() {
        return mTypeConfig;
    }

    /**
     * 转化成以dictValue为键， list为值的map
     */
    private void configToSourceMap() {
        mAllTypes = new HashMap<>();
        putToSourceMap(mTypeConfig.car);
        putToSourceMap(mTypeConfig.income);
        putToSourceMap(mTypeConfig.companyType);
        putToSourceMap(mTypeConfig.education);
        putToSourceMap(mTypeConfig.gender);
        putToSourceMap(mTypeConfig.house);
        putToSourceMap(mTypeConfig.userType);
        putToSourceMap(mTypeConfig.schoolType);
    }

    private void putToSourceMap(List<Dict> source) {
        if (source != null && source.size() > 0) {
            mAllTypes.put(source.get(0).dictValue, source);
        }
    }


    /**
     * 转化成展示的map
     */
    private void configToShowMap(TypeConfig config) {
        if (config == null) {
            convertToIncomeMap(null);
            convertToCarsMap(null);
            convertToCompanyTypeMap(null);
            convertToEducationMap(null);
            convertToGendersMap(null);
            convertToHousesMap(null);
            convertToSchoolTypesMap(null);
            return;
        }
        convertToIncomeMap(config.income);
        convertToCarsMap(config.car);
        convertToCompanyTypeMap(config.companyType);
        convertToEducationMap(config.education);
        convertToGendersMap(config.gender);
        convertToHousesMap(config.house);
        convertToSchoolTypesMap(config.schoolType);
    }

    public SparseArray<String> getIncomeMap() {
        return incomeMap;
    }

    public void convertToIncomeMap(List<Dict> source) {
        this.incomeMap = convertToMap(source);
    }

    public SparseArray<String> getGendersMap() {
        return gendersMap;
    }

    public void convertToGendersMap(List<Dict> genderDicts) {
        this.gendersMap = convertToMap(genderDicts);
    }

    public SparseArray<String> getCompanyTypeMap() {
        return companyTypeMap;
    }

    public void convertToCompanyTypeMap(List<Dict> companyTypeDicts) {
        this.companyTypeMap = convertToMap(companyTypeDicts);
    }

    public SparseArray<String> getHousesMap() {
        return housesMap;
    }

    public void convertToHousesMap(List<Dict> housesDicts) {
        this.housesMap = convertToMap(housesDicts);
    }

    public SparseArray<String> getCarsMap() {
        return carsMap;
    }

    public void convertToCarsMap(List<Dict> carDicts) {
        this.carsMap = convertToMap(carDicts);
    }

    public SparseArray<String> getSchoolTypesMap() {
        return schoolTypesMap;
    }

    public void convertToSchoolTypesMap(List<Dict> schoolDicts) {
        this.schoolTypesMap = convertToMap(schoolDicts);
    }

    public SparseArray<String> getEducationMap() {
        return educationMap;
    }

    public void convertToEducationMap(List<Dict> educationDicts) {
        this.educationMap = convertToMap(educationDicts);
    }


    private List<Dict> setDialogShowingText(List<Dict> list) {
        if (list == null) {
            return new ArrayList<>();
        }
        for (int i = 0; i < list.size(); i++) {
            list.get(i).textShowing = list.get(i).dictDesc;
        }
        return list;
    }

    /**
     * 转化成展示数据
     *
     * @param dicts 数据源
     * @return key:字典id， value： 展示的文字
     */
    private SparseArray<String> convertToMap(List<Dict> dicts) {
        if (dicts == null) {
            return new SparseArray<>();
        }
        setDialogShowingText(dicts);

        SparseArray<String> map = new SparseArray<>();
        for (Dict dict : dicts) {
            map.put(dict.id, dict.dictDesc);
        }
        return map;
    }

    public String getMapValue(SparseArray<String> sourceMap, int key) {
        if (sourceMap != null) {
//            final int index = sourceMap.indexOfKey(key);
//            if (index < sourceMap.size()) {
//                return sourceMap.get()
//            }
            return TextUtils.isEmpty(sourceMap.get(key)) ? "" : sourceMap.get(key);
        }
        return "";
    }

    public int getMapKey(SparseArray<String> sourceMap, String value) {

        if (sourceMap != null && null != value) {
            for (int i = 0; i < sourceMap.size(); i++) {
                final String typeValue = sourceMap.valueAt(i);
                if (null == typeValue) {
                    continue;
                }
                if (typeValue.equals(value)) {
                    return sourceMap.keyAt(i);
                }
            }
            return -1;
        }
        return -1;
    }

    public Dict getDict(int id, List<Dict> source) {
        if (source == null) {
            return null;
        }
        for (Dict d : source) {
            if (d.id == id) {
                return d;
            }
        }
        return null;
    }

    /**
     * 添加显示到选择器中的文字
     */
    private void setTypeConfigShowingText(TypeConfig typeConfig) {
        typeConfig.house = setDialogShowingText(typeConfig.house);
        typeConfig.income = setDialogShowingText(typeConfig.income);
        typeConfig.car = setDialogShowingText(typeConfig.car);
        typeConfig.companyType = setDialogShowingText(typeConfig.companyType);
        typeConfig.education = setDialogShowingText(typeConfig.education);
        typeConfig.gender = setDialogShowingText(typeConfig.gender);
        typeConfig.schoolType = setDialogShowingText(typeConfig.schoolType);
        typeConfig.userType = setDialogShowingText(typeConfig.userType);
    }
}
