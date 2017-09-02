package com.saku.dateone.ui.contracts;

import android.os.Bundle;

import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

public interface FrontpageContract {
    interface V extends BaseView<P> {
        void refreshRecyclerView(List<ItemData> data);
    }

    interface P extends BasePresenter {


        OnRecyclerClickCallBack getItemClickListener();

        void loadData();
    }

    interface M extends BaseModel {
        void loadData();
    }
}
