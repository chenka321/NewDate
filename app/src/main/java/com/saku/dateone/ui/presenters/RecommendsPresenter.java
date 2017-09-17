package com.saku.dateone.ui.presenters;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.ui.contracts.RecommendsContract;
import com.saku.dateone.ui.activities.OppoInfoActivity;
import com.saku.dateone.ui.list.data.RecommendItemData;
import com.saku.dateone.ui.models.RecommendsModel;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.saku.dateone.ui.activities.OppoInfoActivity.USER_ID;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 10:56
 * Description: 推荐列表页面
 */
public class RecommendsPresenter extends UserInfoPresenter<RecommendsContract.V, RecommendsContract.M> implements RecommendsContract.P {

    private List<ItemData> mData = new ArrayList<>();
    private AtomicInteger mCurrPage = new AtomicInteger(0);

    public RecommendsPresenter(RecommendsContract.V mView) {
        super(mView);
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

                Intent intent;
                if (UserInfoManager.getInstance().isLogin()) {
                    if (checkUserInfo()) {  // 用户已经登录但没有填写基本信息
                        return;
                    }

                    intent = new Intent(mView.getViewContext(), OppoInfoActivity.class);
                    putUserIdInIntent(position, intent);
                    mView.toActivity(intent, false);

                } else {
                    intent = new Intent(mView.getViewContext(), LoginActivity.class);
                    putUserIdInIntent(position, intent);
                    intent.putExtra(Consts.LOGIN_FROM_PAGE_NAME, PageManager.RECOMMEND_LIST);
                    mView.toActivity(intent, false);
                }

            }
        };
    }

    private void putUserIdInIntent(int position, Intent intent) {
        if (mData == null || mData.size() - 1 < position) {
            return;
        }
        final ItemData itemData = mData.get(position);
        if (itemData instanceof RecommendItemData) {
            final long userId = ((RecommendItemData) itemData).userId;
            intent.putExtra(USER_ID, userId);
        }
    }

    @Override
    protected RecommendsContract.M getModel() {
        return new RecommendsModel(this);
    }

    @Override
    public void clearDataList() {
        if (mData != null) {
            mData.clear();
        }
    }

    @Override
    public void loadNotLoginData() {
        mModel.loadNotLoginData(mCurrPage.get());
    }

    @Override
    public void loadLoginData() {
        mModel.loadLoginData(mCurrPage.get());
    }

    @Override
    public RespObserver<ApiResponse<List<UserInfo>>, List<UserInfo>> getUnLoginObserver() {
        return new RespObserver<ApiResponse<List<UserInfo>>, List<UserInfo>>() {
            @Override
            public void onSuccess(List<UserInfo> data) {
                onLoadRecommendDataSuccess(data);
            }

            @Override
            public void onFail(int code, String msg) {
                onLoadDataFail(code, msg);
            }
        };
    }

    @Override
    public RespObserver<ApiResponse<List<UserInfo>>, List<UserInfo>> getLoginObserver() {
        return new RespObserver<ApiResponse<List<UserInfo>>, List<UserInfo>>() {
            @Override
            public void onSuccess(List<UserInfo> data) {
                onLoadRecommendDataSuccess(data);
            }

            @Override
            public void onFail(int code, String msg) {
                onLoadDataFail(code, msg);
            }
        };
    }

    private void onLoadDataFail(int code, String msg) {
        mView.dismissLoading();
        mView.setIsLoadingMore(false);
        UIUtils.showToast(mView.getViewContext(), msg);
    }

    private void onLoadRecommendDataSuccess(List<UserInfo> data) {
        if (mData.size() == 0) {
            mView.setRecyclerViewData(mData);
        }
        mView.dismissLoading();
        mCurrPage.addAndGet(1);
        Log.d("lm", "RecommendsPresenter ------ run: currPage = " + mCurrPage);
        mView.setIsLoadingMore(false);
        if (data == null || data.size() == 0) {
            return;
        }
        for (UserInfo item: data ) {
            mData.add((ItemData) item);
        }
        mView.refreshRecyclerView();
    }

    @Override
    public void setCurrentPage(int index) {
        mCurrPage.set(index);
    }

}
