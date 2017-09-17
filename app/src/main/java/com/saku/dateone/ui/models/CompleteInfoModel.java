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
        map.put("token", UserInfoManager.getInstance().getMyShowingInfo().token);
        map.put("username", UserInfoManager.getInstance().getMyShowingInfo().name);
        map.put("location", UserInfoManager.getInstance().getMyShowingInfo().bornLocation);
        map.put("password", "");
        addToComposition(mApi.saveSimpleInfo(map), mPresenter.getSaveInfoObserver());
    }

    @Override
    public void saveCompleteInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", UserInfoManager.getInstance().getMyShowingInfo().token);
        map.put("company", UserInfoManager.getInstance().getMyShowingInfo().company);
        map.put("position", UserInfoManager.getInstance().getMyShowingInfo().position);
        map.put("income", UserInfoManager.getInstance().getMyShowingInfo().income);
        map.put("height", UserInfoManager.getInstance().getMyShowingInfo().height);
        map.put("income", UserInfoManager.getInstance().getMyShowingInfo().income);
        map.put("house", UserInfoManager.getInstance().getMyShowingInfo().house);
        map.put("car", UserInfoManager.getInstance().getMyShowingInfo().car);
        map.put("schoolType", UserInfoManager.getInstance().getMyShowingInfo().schoolType);
        map.put("school", UserInfoManager.getInstance().getMyShowingInfo().school);
        map.put("hobby", UserInfoManager.getInstance().getMyShowingInfo().hobby);
        map.put("moreIntroduce", UserInfoManager.getInstance().getMyShowingInfo().moreIntroduce);
        addToComposition(mApi.saveCompleteInfo(map), mPresenter.getSaveInfoObserver());
    }
}
