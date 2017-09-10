package com.saku.dateone.utils;

import android.util.SparseArray;

import com.saku.dateone.ui.bean.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liumin on 2017/9/6.
 */

public class TypeManager {
    private static TypeManager sManager;

    private List<Type> genders;
    private List<Type> companyTypes;
    private SparseArray<String> companyTypeMap; // key是显示的id// , value是type 的name， 用于请求或者显示
    private List<Type> houses;
    private List<Type> cars;
    private List<Type> schoolTypes;
    private List<Type> degrees;

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


    public List<Type> getDegrees() {
        if (degrees != null) {
            return degrees;
        }
        degrees = new ArrayList<>();
        Type doctor = new Type();
        doctor.id = 1;
        doctor.name = "博士";
        degrees.add(doctor);
        Type postGrad = new Type();
        postGrad.id = 2;
        postGrad.name = "硕士";
        degrees.add(postGrad);

        Type grad = new Type();
        grad.id = 2;
        grad.name = "本科";
        degrees.add(grad);

        Type highSchool = new Type();
        highSchool.id = 2;
        highSchool.name = "高中";
        degrees.add(highSchool);

        Type vocaccion = new Type();
        vocaccion.id = 2;
        vocaccion.name = "大专";
        degrees.add(vocaccion);

        setDialogShowingText(degrees);
        return degrees;
    }

    public void setDegrees(List<Type> degrees) {
        this.degrees = degrees;
    }



    public List<Type> getGenders() {
        if (genders != null) {
            return genders;
        }
        genders = new ArrayList<>();
        Type female = new Type();
        female.id = 1;
        female.name = "女";
        genders.add(female);
        Type male = new Type();
        male.id = 2;
        male.name = "男";
        genders.add(male);

        setDialogShowingText(genders);
        return genders;
    }

    private void setDialogShowingText(List<Type> list) {
        for (int i = 0; i < list.size(); i++) {
            list.get(i).textShowing = list.get(i).name;
        }
    }

    public void setGenders(List<Type> genders) {
        this.genders = genders;
    }

    public List<Type> getCompanyTypes() {
        if (companyTypes != null) {
            return companyTypes;
        }
        companyTypes = new ArrayList<>();
        Type gov = new Type();
        gov.id = 1;
        gov.name = "政府";
        companyTypes.add(gov);
        Type publicService = new Type();
        publicService.id = 2;
        publicService.name = "事业单位";
        companyTypes.add(publicService);
        Type foreighCompany = new Type();
        foreighCompany.id = 3;
        foreighCompany.name = "外企";
        companyTypes.add(foreighCompany);
        Type privateCompany = new Type();
        privateCompany.id = 4;
        privateCompany.name = "企业";
        companyTypes.add(privateCompany);
        return companyTypes;
    }

    public SparseArray<String> getCompanyTypeMap() {
        if (companyTypeMap != null) {
            return companyTypeMap;
        }
        companyTypes = new ArrayList<>();
        Type gov = new Type();
        gov.id = 1;
        gov.name = "政府";
        companyTypes.add(gov);
        Type publicService = new Type();
        publicService.id = 2;
        publicService.name = "事业单位";
        companyTypes.add(publicService);
        Type foreighCompany = new Type();
        foreighCompany.id = 3;
        foreighCompany.name = "外企";
        companyTypes.add(foreighCompany);
        Type privateCompany = new Type();
        privateCompany.id = 4;
        privateCompany.name = "企业";
        companyTypes.add(privateCompany);

        setDialogShowingText(companyTypes);

        companyTypeMap = new SparseArray<>();

        for (Type type : companyTypes) {
            companyTypeMap.put(type.id, type.name);
        }
        return companyTypeMap;
    }

    public void setCompanyTypes(List<Type> companyTypes) {
        this.companyTypes = companyTypes;
    }

    public List<Type> getHouses() {
        if (houses != null) {
            return houses;
        }
        houses = new ArrayList<>();
        Type noHouse = new Type();
        noHouse.id = 1;
        noHouse.name = "无房";
        houses.add(noHouse);
        Type hasHouse = new Type();
        hasHouse.id = 2;
        hasHouse.name = "有房";
        houses.add(hasHouse);
        Type multiHouse = new Type();
        multiHouse.id = 3;
        multiHouse.name = "多套";
        houses.add(multiHouse);

        setDialogShowingText(houses);

        return houses;
    }

    public void setHouses(List<Type> houses) {
        this.houses = houses;
    }

    public List<Type> getCars() {
        if (cars != null) {
            return cars;
        }
        cars = new ArrayList<>();
        Type noCar = new Type();
        noCar.id = 1;
        noCar.name = "无车";
        cars.add(noCar);
        Type hasCar = new Type();
        hasCar.id = 2;
        hasCar.name = "有车";
        cars.add(hasCar);

        setDialogShowingText(cars);


        return cars;
    }

    public void setCars(List<Type> cars) {
        this.cars = cars;
    }

    public List<Type> getSchoolTypes() {
        if (schoolTypes != null) {
            return schoolTypes;
        }
        schoolTypes = new ArrayList<>();
        Type firstLevel = new Type();
        firstLevel.id = 1;
        firstLevel.name = "一本";
        schoolTypes.add(firstLevel);
        Type two11 = new Type();
        two11.id = 2;
        two11.name = "211";
        schoolTypes.add(two11);

        Type nine85 = new Type();
        nine85.id = 3;
        nine85.name = "985";
        schoolTypes.add(nine85);
        Type overseaPrestige = new Type();
        overseaPrestige.id = 4;
        overseaPrestige.name = "国外名牌院校";
        schoolTypes.add(overseaPrestige);

        setDialogShowingText(schoolTypes);


        return schoolTypes;
    }

    public void setSchoolTypes(List<Type> schoolTypes) {
        this.schoolTypes = schoolTypes;
    }
}
