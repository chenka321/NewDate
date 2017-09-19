package com.saku.dateone.ui.presenters;

import android.util.Log;
import android.view.View;

import com.saku.dateone.bean.Collect;
import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.contracts.OppoInfoContract;
import com.saku.dateone.ui.models.OppoModel;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

public class OppoInfoPresenter extends ABasePresenter<OppoInfoContract.V, OppoInfoContract.M> implements OppoInfoContract.P {

    private UserInfo mOppoInfo;

    public OppoInfoPresenter(OppoInfoContract.V mView) {
        super(mView);
    }

    @Override
    public void loadPage(long userId) {
        mView.showLoading();
        mModel.loadPageData(userId);
    }

    @Override
    public RespObserver<ApiResponse<UserInfo>, UserInfo> getCurrUserInfoObserver() {
        return new RespObserver<ApiResponse<UserInfo>, UserInfo>() {
            @Override
            public void onSuccess(UserInfo data) {
                mView.dismissLoading();
                mOppoInfo = data;
                if (mOppoInfo == null) {
                    return;
                }
                mView.updateUserNameIv(mOppoInfo.userImage, mOppoInfo.name);
                mView.updateBasicInfo(mOppoInfo);
            }

            @Override
            public void onFail(int code, String msg) {
                mView.dismissLoading();
            }
        };
    }

    @Override
    public boolean hasMoreInfo() {
        if (mOppoInfo != null) {
            return mOppoInfo.hasMoreInfo;
        }
        return false;
    }

    @Override
    public void onCollectionClicked() {
        if (mOppoInfo == null) {
            UIUtils.showToast(mView.getViewContext(), "没有用户数据");
            return;
        }
        mView.showLoading();
        mModel.saveCollection(mOppoInfo.userId);
    }


    @Override
    public RespObserver<ApiResponse<Collect>, Collect> getCollectionObserver() {
        return new RespObserver<ApiResponse<Collect>, Collect>() {
            @Override
            public void onSuccess(Collect data) {
                mView.dismissLoading();
                mView.markCollection(data.ifAdd);
//                mOppoInfo.isCollected = !isCollected;
            }

            @Override
            public void onFail(int code, String msg) {
                mView.dismissLoading();
            }
        };
    }

    @Override
    public UserInfo getUserInfo() {
        if (mOppoInfo != null) {
            return mOppoInfo;
        }
        return null;
    }

    @Override
    public void onChatClicked() {
//        mModel.getChatInfo
    }

    @Override
    protected OppoInfoContract.M getModel() {
        return new OppoModel(this);
    }

    @Override
    public OnRecyclerClickCallBack getPicItemClickListener() {
        return new OnRecyclerClickCallBack() {
            @Override
            public void onClick(int position, View view) {
                if (mOppoInfo != null && mOppoInfo.photo != null && mOppoInfo.photo.size() > position) {
                    mView.startPicActivity(mOppoInfo.photo.get(position));
                }
            }
        };
    }
}
