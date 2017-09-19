package com.saku.dateone.ui.models;

import com.saku.dateone.bean.MyMsg;
import com.saku.dateone.ui.contracts.MyMsgContract;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.list.data.ItemData;

import java.util.ArrayList;
import java.util.List;

public class MyMsgModel extends ABaseModel<MyMsgContract.P> implements MyMsgContract.M {

    public MyMsgModel(MyMsgContract.P p) {
        super(p);
    }

    @Override
    public void loadPageData() {
        addToComposition(mApi.getMessage(UserInfoManager.getInstance().getMyPendingInfo().userId), mPresenter.getMyMsgListObserver());
    }

}
