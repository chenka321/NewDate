package com.saku.dateone.ui.contracts;


import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

public interface ChatListContract {
    interface V extends UserInfoContract.V {

    }

    interface P extends UserInfoContract.P {
        void loadData();

        OnRecyclerClickCallBack getItemClickListener();
    }

    interface M extends UserInfoContract.M{
    }
}
