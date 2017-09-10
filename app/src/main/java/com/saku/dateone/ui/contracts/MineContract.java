package com.saku.dateone.ui.contracts;


import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

/**
 * 发现列表
 */
public interface MineContract {
    interface V extends UserInfoContract.V {
        void refreshInfoView();

    }

    interface P extends UserInfoContract.P {
    }

    interface M extends UserInfoContract.M {

    }
}
