package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.RecommendsContract;
import com.saku.dateone.ui.contracts.RecommendsContract.M;
import com.saku.dateone.utils.UserInfoManager;

import java.util.HashMap;
import java.util.Map;

public class RecommendsModel extends UserInfoModel<RecommendsContract.P> implements M {

    public RecommendsModel(RecommendsContract.P p) {
        super(p);
    }

    @Override
    public void loadNotLoginData(int currPage) {

        Map<String, Object> requestMap = new HashMap<>();
        requestMap.put("location", UserInfoManager.getInstance().getMyShowingInfo().bornLocation);
        requestMap.put("gender", UserInfoManager.getInstance().getMyShowingInfo().gender);
        requestMap.put("currentLocation", UserInfoManager.getInstance().getMyShowingInfo().currentLocation);
        requestMap.put("page", currPage);
        add(subscribeWith(mApi.getUnloginRecommend(requestMap), mPresenter.getUnLoginObserver()));
    }

    @Override
    public void loadLoginData(int currPage) {
        String token = UserInfoManager.getInstance().getToken();
        add(subscribeWith(mApi.getLoginRecommend(token, String.valueOf(currPage)), mPresenter.getLoginObserver()));

    }
}
