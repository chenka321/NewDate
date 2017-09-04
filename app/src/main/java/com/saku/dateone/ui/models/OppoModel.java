package com.saku.dateone.ui.models;

import com.saku.dateone.ui.bean.BasicInfo;
import com.saku.dateone.ui.bean.OppoInfo;
import com.saku.dateone.ui.bean.MoreInfo;
import com.saku.dateone.ui.contracts.OppoInfoContract;

import java.util.ArrayList;

public class OppoModel extends ABaseModel<OppoInfoContract.P> implements OppoInfoContract.M {

    public OppoModel(OppoInfoContract.P p) {
        super(p);
    }

    @Override
    public void loadPageData(long userId) {
        OppoInfo oppoInfo = new OppoInfo();
        oppoInfo.oppoBasicInfo = new BasicInfo();
        oppoInfo.oppoBasicInfo.hasMoreInfo = false;
        oppoInfo.oppoBasicInfo.birthday = "1990-2-15";
        oppoInfo.oppoBasicInfo.bornLocation = "黑龙江-松花江市";
        oppoInfo.oppoBasicInfo.currentLocation = "深圳市-深圳";
        oppoInfo.oppoBasicInfo.company = "马兰坡文化公司";
        oppoInfo.oppoBasicInfo.degree = "博士";
        oppoInfo.oppoBasicInfo.income = "60k";
        oppoInfo.oppoBasicInfo.name = "小杜鹃";
        oppoInfo.oppoBasicInfo.position = "宇宙行行长";
        oppoInfo.oppoBasicInfo.targetUserId = 10200;

        oppoInfo.oppoMoreInfo = new MoreInfo();
        oppoInfo.oppoMoreInfo.height = "183cm";
        oppoInfo.oppoMoreInfo.car = "有车";
        oppoInfo.oppoMoreInfo.estateLocation = "黑龙江";
        oppoInfo.oppoMoreInfo.house = "有房";
        oppoInfo.oppoMoreInfo.hobby = "跆拳道";
        oppoInfo.oppoMoreInfo.school = "某常春藤名校";
        oppoInfo.oppoMoreInfo.schoolType = "海外名校";
        oppoInfo.oppoMoreInfo.pics = new ArrayList<>();
        oppoInfo.oppoMoreInfo.pics.add("http://img1.imgtn.bdimg.com/it/u=989341999,236255160&fm=27&gp=0.jpg");
        oppoInfo.oppoMoreInfo.pics.add("http://img1.imgtn.bdimg.com/it/u=989341999,236255160&fm=27&gp=0.jpg");
        oppoInfo.oppoMoreInfo.additionInfo = "傲娇的小公主";


        mPresenter.onLoadPage("0", "success", oppoInfo);
    }

    @Override
    public void saveCollection(long targetUserId) {
        mPresenter.onSaveCollection("000", "sucess");
    }
}
