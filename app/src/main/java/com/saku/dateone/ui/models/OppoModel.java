package com.saku.dateone.ui.models;

import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.contracts.OppoInfoContract;

import java.util.ArrayList;

public class OppoModel extends ABaseModel<OppoInfoContract.P> implements OppoInfoContract.M {

    public OppoModel(OppoInfoContract.P p) {
        super(p);
    }

    @Override
    public void loadPageData(long userId) {
        UserInfo oppoInfo = new UserInfo();
        oppoInfo.image = "http://img1.imgtn.bdimg.com/it/u=989341999,236255160&fm=27&gp=0.jpg";
        oppoInfo.hasMoreInfo = false;
        oppoInfo.birthday = "1990-2-15";
        oppoInfo.bornLocation = "黑龙江-松花江市";
        oppoInfo.currentLocation = "深圳市-深圳";
        oppoInfo.company = "马兰坡文化公司";
        oppoInfo.education = "博士";
        oppoInfo.income = "60k";
        oppoInfo.name = "小杜鹃";
        oppoInfo.position = "宇宙行行长";
        oppoInfo.userId = 10200;

        oppoInfo.hasMoreInfo = true;
        oppoInfo.height = "183cm";
        oppoInfo.car = "有车";
        oppoInfo.estateLocation = "黑龙江";
        oppoInfo.house = "有房";
        oppoInfo.hobby = "跆拳道";
        oppoInfo.school = "某常春藤名校";
        oppoInfo.schoolType = "海外名校";
        oppoInfo.pics = new ArrayList<>();
        oppoInfo.pics.add("http://img1.imgtn.bdimg.com/it/u=989341999,236255160&fm=27&gp=0.jpg");
        oppoInfo.pics.add("http://img1.imgtn.bdimg.com/it/u=989341999,236255160&fm=27&gp=0.jpg");
        oppoInfo.moreIntroduce = "傲娇的小公主";


        mPresenter.onLoadPage("0", "success", oppoInfo);
    }

    @Override
    public void saveCollection(long targetUserId) {
        mPresenter.onSaveCollection("000", "sucess");
    }
}
