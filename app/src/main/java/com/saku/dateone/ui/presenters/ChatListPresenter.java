package com.saku.dateone.ui.presenters;


import com.saku.dateone.ui.contracts.ChatListContract;
import com.saku.dateone.ui.models.ChatListModel;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 10:56
 * Description: 聊天
*/
public class ChatListPresenter extends UserInfoPresenter<ChatListContract.V, ChatListContract.M> implements ChatListContract.P {

    private List<ItemData> mData;

    public ChatListPresenter(ChatListContract.V mView) {
        super(mView);
    }

    @Override
    protected ChatListContract.M getModel() {
        return new ChatListModel(this);
    }

    @Override
    public void loadData() {

    }

    @Override
    public OnRecyclerClickCallBack getItemClickListener() {
        return null;
    }
}


