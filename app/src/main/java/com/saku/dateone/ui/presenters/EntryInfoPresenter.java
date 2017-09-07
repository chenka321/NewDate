package com.saku.dateone.ui.presenters;

import android.os.Bundle;
import android.view.View;

import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.saku.dateone.ui.activities.EntryInfoActivity;
import com.saku.dateone.ui.activities.MainTabsActivity;
import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.contracts.EntryInfoContract;
import com.saku.dateone.ui.models.BasicInfoModel;
import com.saku.dateone.utils.Consts;
import com.saku.lmlib.utils.PreferenceUtil;

public class EntryInfoPresenter extends ABasePresenter<EntryInfoContract.V, EntryInfoContract.M> implements EntryInfoContract.P {

    @Override
    protected EntryInfoContract.M getModel() {
        return new BasicInfoModel(this);
    }

    public EntryInfoPresenter(EntryInfoContract.V view) {
        super(view);
    }

    @Override
    public void onArguments(Bundle bundle) {

    }

    @Override
    public View.OnClickListener getChooseCityListener(final int who) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mView.showCityDialog(who);
            }
        };
    }

    @Override
    public void onCityChosen(int who, ProvinceBean province, CityBean city, DistrictBean district) {
        if (who == EntryInfoActivity.USER) {
            UserInfo.getInstance().yourLocation.province = province;
            UserInfo.getInstance().yourLocation.city = city;
            UserInfo.getInstance().yourLocation.district = district;
        } else {
            UserInfo.getInstance().childLocation.province = province;
            UserInfo.getInstance().childLocation.city = city;
            UserInfo.getInstance().childLocation.district = district;
        }

    }

    @Override
    public void onGenderClicked(int gender) {
        UserInfo.getInstance().childGender = gender;
    }

    @Override
    public void onMatchBtnClicked() {
        mModel.startMatch();
    }

    @Override
    public void onStartMatchResult(int code, String msg) {
//        onResult(code, msg);
        if (mView == null) {
            return;
        }

        PreferenceUtil.putBoolean(mView.getViewContext(), Consts.HAS_BASIC_INFO, true);
        mView.toActivity(MainTabsActivity.class, null, true);
    }
}
