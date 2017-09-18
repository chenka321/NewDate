package com.saku.dateone.ui.presenters;

import android.util.Log;
import android.view.View;

import com.saku.dateone.bean.Article;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.contracts.DiscoversContract;
import com.saku.dateone.ui.contracts.UserInfoContract;
import com.saku.dateone.ui.models.DiscoversModel;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 10:56
 * Description: 发现
*/
public class DiscoversPresenter extends UserInfoPresenter<DiscoversContract.V, DiscoversContract.M> implements DiscoversContract.P {

    private List<ItemData> mData = new ArrayList<>();
    private String mLastArticleId = "";

    public DiscoversPresenter(DiscoversContract.V mView) {
        super(mView);
    }

    @Override
    protected DiscoversContract.M getModel() {
        return new DiscoversModel(this);
    }


    @Override
    public void loadData() {
        mView.showLoading();
        mView.setIsLoadingDiscover(true);
        mModel.loadDiscoverList(mLastArticleId);
    }

    @Override
    public RespObserver<ApiResponse<List<Article>>, List<Article>> getDiscoverListObserver() {
        return new RespObserver<ApiResponse<List<Article>>, List<Article>>() {
            @Override
            public void onSuccess(List<Article> data) {
                if (data == null && data.size() == 0) {
                    return;
                }
                mData.addAll(data);
                mView.dismissLoading();
                mView.setIsLoadingDiscover(false);
                mView.refreshRecyclerView(mData);
                mLastArticleId = data.get(data.size()-1).articleId;
            }

            @Override
            public void onFail(int code, String msg) {
                mView.dismissLoading();

            }
        };
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

                Article article = (Article) mData.get(position);
                mView.gotoDiscoverDetail(article);


            }
        };
    }
}


