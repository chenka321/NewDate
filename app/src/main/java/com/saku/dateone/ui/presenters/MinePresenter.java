package com.saku.dateone.ui.presenters;

import android.util.Log;
import android.view.View;

import com.saku.dateone.ui.contracts.MineContract;
import com.saku.dateone.ui.models.MineModel;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 10:56
 * Description: 我的页面
*/
public class MinePresenter extends ABasePresenter<MineContract.V, MineContract.M> implements MineContract.P {

    private List<ItemData> mData;

    public MinePresenter(MineContract.V mView) {
        super(mView);
    }

    @Override
    protected MineContract.M getModel() {
        return new MineModel(this);
    }

}


