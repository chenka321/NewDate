package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.ChatListContract;

public class ChatListModel extends UserInfoModel<ChatListContract.P> implements ChatListContract.M {

    public ChatListModel(ChatListContract.P p) {
        super(p);
    }

}
