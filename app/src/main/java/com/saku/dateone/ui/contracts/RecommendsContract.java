package com.saku.dateone.ui.contracts;

import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.List;

public interface RecommendsContract {
    interface V extends UserInfoContract.V {
        void setRecyclerViewData(List<ItemData> data);

        void refreshRecyclerView();

        void setIsLoadingMore(boolean loadingState);
    }

    interface P extends UserInfoContract.P {

        OnRecyclerClickCallBack getItemClickListener();

        void loadNotLoginData();
        void loadLoginData();

        void clearDataList();

        /**
         * 重新刷新页面时，需要重置请求参数为0
         */
        void setCurrentPage(int index);

        RespObserver<ApiResponse<List<UserInfo>>, List<UserInfo>> getUnLoginObserver();
        RespObserver<ApiResponse<List<UserInfo>>,List<UserInfo>> getLoginObserver();

        void loadCollectionList();  // 加载收藏列表

        RespObserver<ApiResponse<List<UserInfo>>, List<UserInfo>> getCollectionObserver();
    }

    interface M extends UserInfoContract.M {
        void loadNotLoginData(int currPage);
        void loadLoginData(int currPage);

        void loadCollectionList();
    }
}
