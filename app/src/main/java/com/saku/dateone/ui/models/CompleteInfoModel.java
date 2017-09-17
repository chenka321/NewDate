package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.CompleteInfoContract;
import com.saku.dateone.utils.UserInfoManager;

import java.util.HashMap;
import java.util.Map;

public class CompleteInfoModel extends ABaseModel<CompleteInfoContract.P> implements CompleteInfoContract.M{

    public CompleteInfoModel(CompleteInfoContract.P presenter) {
        super(presenter);
    }

    @Override
    public void saveSimpleInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("token", UserInfoManager.getInstance().getToken());
        map.put("username", UserInfoManager.getInstance().getMyPendingInfo().name);
        map.put("location", UserInfoManager.getInstance().getMyPendingInfo().bornLocation);
        map.put("password", "");
        addToComposition(mApi.saveSimpleInfo(map), mPresenter.getSaveInfoObserver());
    }

    @Override
    public void saveCompleteInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", UserInfoManager.getInstance().getToken());
        map.put("company", UserInfoManager.getInstance().getMyPendingInfo().company);
        map.put("position", UserInfoManager.getInstance().getMyPendingInfo().position);
        map.put("income", UserInfoManager.getInstance().getMyPendingInfo().income);
        map.put("height", UserInfoManager.getInstance().getMyPendingInfo().height);
        map.put("income", UserInfoManager.getInstance().getMyPendingInfo().income);
        map.put("house", UserInfoManager.getInstance().getMyPendingInfo().house);
        map.put("car", UserInfoManager.getInstance().getMyPendingInfo().car);
        map.put("schoolType", UserInfoManager.getInstance().getMyPendingInfo().schoolType);
        map.put("school", UserInfoManager.getInstance().getMyPendingInfo().school);
//        map.put("hobby", UserInfoManager.getInstance().getMyPendingInfo().hobby == null ? );
        map.put("moreIntroduce", UserInfoManager.getInstance().getMyPendingInfo().moreIntroduce);

        add(subscribeWith(mApi.saveCompleteInfo(map), mPresenter.getSaveInfoObserver()));
//        addToComposition(mApi.saveCompleteInfo(map), mPresenter.getSaveInfoObserver());
    }
}
