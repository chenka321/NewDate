package com.saku.dateone.ui.contracts;

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
        void onLoadNotLoginDatResult(int code, String msg);

        void loadLoginData();
        void onLoadLoginDataResult(int code, String msg);

        void clearDataList();

        /**
         * 重新刷新页面时，需要重置请求参数为0
         */
        void setCurrentPage(int index);
    }

    interface M extends UserInfoContract.M {
        void loadNotLoginData();
        void loadLoginData();
    }
}
