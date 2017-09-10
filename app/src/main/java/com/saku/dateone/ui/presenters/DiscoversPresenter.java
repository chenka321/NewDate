package com.saku.dateone.ui.presenters;

import android.util.Log;
import android.view.View;

import com.saku.dateone.ui.contracts.DiscoversContract;
import com.saku.dateone.ui.contracts.UserInfoContract;
import com.saku.dateone.ui.models.DiscoversModel;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 10:56
 * Description: 发现
*/
public class DiscoversPresenter extends UserInfoPresenter<DiscoversContract.V, DiscoversContract.M> implements DiscoversContract.P {

    private List<ItemData> mData;

    public DiscoversPresenter(DiscoversContract.V mView) {
        super(mView);
    }

    @Override
    protected DiscoversContract.M getModel() {
        return new DiscoversModel(this);
    }


    @Override
    public void loadData() {

        mView.setIsLoadingDiscover(true);
        mModel.loadDiscoverList("0");
    }

    @Override
    public void onLoadResult(int code, String msg) {
        if (mView == null ) {
            return;
        }
        mView.setIsLoadingDiscover(false);

        mView.refreshRecyclerView(mData);

    }

    @Override
    public OnRecyclerClickCallBack getItemClickListener() {
        return new OnRecyclerClickCallBack() {
            @Override
            public void onClick(int position, View view) {
                Log.d("lm", "onClick: position = " + position);
                if (mView == null || mView.getViewContext() == null) {
                    return;
                }
//                UserInfo.getInstance().isLogin = false;
//                Intent intent;
//                if (!UserInfo.getInstance().isLogin) {
//                    intent = new Intent(mView.getViewContext(), LoginActivity.class);
//                } else {
//                    intent = new Intent(mView.getViewContext(), OppoInfoActivity.class);
//
////                    mView.showLoading();
////                    mView.addFragment(OppoInfoActivity.newInstance(null));
//                }
//
//                final ItemData itemData = mData.get(position);
//                if (itemData instanceof RecommendItemData) {
//                    final long userId = ((RecommendItemData) itemData).userId;
//                    intent.putExtra(USER_ID, userId);
//                }
//                mView.toActivity(intent, false);



//                mView.addFragment();
            }
        };
    }
}


