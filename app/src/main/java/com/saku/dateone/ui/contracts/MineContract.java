package com.saku.dateone.ui.contracts;


import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

/**
 * 发现列表
 */
public interface MineContract {
    interface V extends BaseView<P> {

    }

    interface P extends BasePresenter{

    }

    interface M extends BaseModel{

    }
}
