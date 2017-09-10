package com.saku.dateone.ui.contracts;


import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

public interface MyMsgContract {
    interface V extends BaseView<P> {
        void refreshRecyclerView(List<ItemData> data);
        void goToNext(int msgType);
    }

    interface P extends BasePresenter{

        void loadPage(long userId);
        void onLoadPage(String code, String msg, List<ItemData> msgList);
        OnRecyclerClickCallBack getItemClick();  // 我的消息
    }

    interface M extends BaseModel{
        void loadPageData(long userId);
    }
}
