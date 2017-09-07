package com.saku.dateone.ui.contracts;


import android.os.Bundle;
import android.view.View;

import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

public interface ChatListContract {
    interface V extends BaseView<P> {

    }

    interface P extends BasePresenter{
        void onStartMatchClicked();
        void onMatchResult(int code, String msg);
    }

    interface M extends BaseModel{
        void startMatch(); // 开始网络请求
    }
}
