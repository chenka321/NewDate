package com.saku.dateone.ui.contracts;


import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

/**
 * 发现列表
 */
public interface DiscoversContract {
    interface V extends UserInfoContract.V {
        void refreshRecyclerView(List<ItemData> data);
        void setIsLoadingDiscover(boolean isLoading);
    }

    interface P extends UserInfoContract.P{
        void loadData();
        void onLoadResult(int code, String msg);

        OnRecyclerClickCallBack getItemClickListener();

    }

    interface M extends UserInfoContract.M{
        void loadDiscoverList(String lastArticleId);
    }
}
