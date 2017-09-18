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
//        List<ItemData> msgs = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            MyMsg msg = new MyMsg();
//            msg.id = i;
//            msg.content = "又来了一大波美女，赶紧去聊一聊哟";
//            msg.time = System.currentTimeMillis();
//            msg.url = "http://www.baidu.com";
//            msg.type = i % 3 + 1;
//            msgs.add(msg);
//        }
//
//        mPresenter.onLoadPage("0", "success", msgs);
        addToComposition(mApi.getMessage(UserInfoManager.getInstance().getMyPendingInfo().id), mPresenter.getMyMsgListObserver());
    }

}
