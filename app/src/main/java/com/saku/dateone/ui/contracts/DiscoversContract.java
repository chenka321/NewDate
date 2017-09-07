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
    interface V extends BaseView<P> {
        void refreshRecyclerView(List<ItemData> data);

    }

    interface P extends BasePresenter{
        void loadData();
        void onLoadResult(int code, String msg);

        OnRecyclerClickCallBack getItemClickListener();

    }

    interface M extends BaseModel{
        void loadDiscoverList(String lastArticleId);
    }
}
