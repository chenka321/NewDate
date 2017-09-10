package com.saku.dateone.ui.contracts;

import android.os.Bundle;

import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

public interface RecommendsContract {
    interface V extends UserInfoContract.V {
        void refreshRecyclerView(List<ItemData> data);
    }

    interface P extends UserInfoContract.P {


        OnRecyclerClickCallBack getItemClickListener();

        void loadNotLoginData();
        void onLoadNotLoginDatResult(int code, String msg);

        void loadLoginData();
        void onLoadLoginDataResult(int code, String msg);
    }

    interface M extends UserInfoContract.M {
        void loadNotLoginData();
        void loadLoginData();
    }
}
