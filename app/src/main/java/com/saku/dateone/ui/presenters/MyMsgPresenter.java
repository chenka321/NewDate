package com.saku.dateone.ui.presenters;

import android.view.View;

import com.saku.dateone.bean.MyMsg;
import com.saku.dateone.ui.contracts.MyMsgContract;
import com.saku.dateone.ui.models.MyMsgModel;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 10:56
 * Description: 我的消息
 */
public class MyMsgPresenter extends ABasePresenter<MyMsgContract.V, MyMsgContract.M> implements MyMsgContract.P {

    private List<ItemData> mData;

    public MyMsgPresenter(MyMsgContract.V mView) {
        super(mView);
    }

    @Override
    protected MyMsgContract.M getModel() {
        return new MyMsgModel(this);
    }

    @Override
    public void loadPage(long userId) {
        mModel.loadPageData(userId);
    }

    @Override
    public void onLoadPage(String code, String msg, List<ItemData> msgList) {
        if (mView != null) {
            mView.refreshRecyclerView(msgList);
        }
        mData = msgList;
    }

    @Override
    public OnRecyclerClickCallBack getItemClick() {
        return new OnRecyclerClickCallBack() {
            @Override
            public void onClick(int position, View view) {
                if (mData != null && mData.size() > position) {
                    final ItemData itemData = mData.get(position);
                    if (itemData instanceof MyMsg) {
                        mView.goToNext(((MyMsg) itemData).type);
                    }
                }
            }
        };
    }
}
