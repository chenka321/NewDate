package com.saku.dateone.ui.contracts;


import android.os.Bundle;
import android.view.View;

import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

public interface BasicInfoContract {
    interface V extends BaseView<P> {

        /**
         * @param whoseCity  1 使用者城市，2 小孩的城市
         */
        void showCityDialog(int whoseCity);
    }

    interface P extends BasePresenter{
        void onArguments(Bundle bundle);

        /**
         * @param who 1 使用者，2 小孩
         */
        View.OnClickListener getChooseCityListener(int who);
        /**
         * @param who 1 使用者，2 小孩
         */
        void onCityChosen(int who, ProvinceBean province, CityBean city, DistrictBean district);

        void onGenderClicked(int gender);
        void onMatchBtnClicked();

        void onStartMatchResult(int code, String msg);

    }

    interface M extends BaseModel{
        void startMatch(); // 开始网络请求
    }
}
