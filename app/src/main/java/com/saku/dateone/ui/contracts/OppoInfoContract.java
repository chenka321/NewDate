package com.saku.dateone.ui.contracts;


import android.os.Bundle;
import android.view.View;

import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.saku.dateone.ui.bean.OppoBasicInfo;
import com.saku.dateone.ui.bean.OppoInfo;
import com.saku.dateone.ui.bean.OppoMoreInfo;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

public interface OppoInfoContract {
    interface V extends BaseView<P> {

        void updateUserNameIv(String imgUrl, String name);
        void updateBasicInfo(OppoBasicInfo oppoBasicInfo);
        void updateMoreInfo(OppoMoreInfo oppoMoreInfo);

        void showCompleteMoreInfoDialog();
    }

    interface P extends BasePresenter{

        void loadPage(long userId);
        void onLoadPage(String code, String msg, OppoInfo oppoInfo);
        void displayMoreInfo();
    }

    interface M extends BaseModel{
        void loadPageData(long userId);
    }
}
