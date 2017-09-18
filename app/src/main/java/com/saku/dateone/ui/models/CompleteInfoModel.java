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
        Map<String, Object> map = new HashMap<>();
        map.put("token", UserInfoManager.getInstance().getToken());
        putSimpleInfo(map);

        addToComposition(mApi.saveUserChildInfo(map), mPresenter.getSaveInfoObserver());
    }

    private void putSimpleInfo(Map<String, Object> map) {
        map.put("name", UserInfoManager.getInstance().getMyPendingInfo().name);
        map.put("education", UserInfoManager.getInstance().getMyPendingInfo().education);
        map.put("birthday", UserInfoManager.getInstance().getMyPendingInfo().birthday);

        // 进入应用填写的保存用户所在地和子女所在地，以及性别的信息在此传给后台
        map.put("gender", UserInfoManager.getInstance().getMyPendingInfo().gender);
        map.put("bornLocation", UserInfoManager.getInstance().getMyPendingInfo().bornLocation);
        map.put("currentLocation", UserInfoManager.getInstance().getMyPendingInfo().currentLocation);
    }

    @Override
    public void saveCompleteInfo() {
        Map<String, Object> map = new HashMap<>();
        map.put("token", UserInfoManager.getInstance().getToken());
        // 从用户子女 基本信息填写页跳到 补充信息页，需要把基本信息一并上传
        putSimpleInfo(map);

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

        add(subscribeWith(mApi.saveUserChildInfo(map), mPresenter.getSaveInfoObserver()));
    }
}
