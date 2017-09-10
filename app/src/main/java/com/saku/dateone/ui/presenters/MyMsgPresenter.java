package com.saku.dateone.ui.presenters;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.ui.activities.OppoInfoActivity;
import com.saku.dateone.ui.bean.MyMsg;
import com.saku.dateone.ui.bean.TagString;
import com.saku.dateone.ui.contracts.MyMsgContract;
import com.saku.dateone.ui.contracts.RecommendsContract;
import com.saku.dateone.ui.list.data.RecommendItemData;
import com.saku.dateone.ui.models.MyMsgModel;
import com.saku.dateone.ui.models.RecommendsModel;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.ArrayList;
import java.util.List;

import static com.saku.dateone.ui.activities.OppoInfoActivity.USER_ID;

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
