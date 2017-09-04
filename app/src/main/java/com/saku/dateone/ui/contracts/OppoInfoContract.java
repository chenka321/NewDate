package com.saku.dateone.ui.contracts;


import android.os.Bundle;
import android.view.View;

import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.saku.dateone.ui.bean.OppoBasicInfo;
import com.saku.dateone.ui.bean.OppoInfo;
import com.saku.dateone.ui.bean.OppoMoreInfo;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

public interface OppoInfoContract {
    interface V extends BaseView<P> {

        void updateUserNameIv(String imgUrl, String name);
        void updateBasicInfo(OppoBasicInfo oppoBasicInfo);
        void displayMoreInfoView(OppoMoreInfo oppoMoreInfo);

        void markCollection();

//        void showCompleteMoreInfoDialog();
    }

    interface P extends BasePresenter{

        void loadPage(long userId);
        void onLoadPage(String code, String msg, OppoInfo oppoInfo);
        OppoMoreInfo getMoreInfo();
        boolean hasMoreInfo();

        void onCollectionClicked();  // 收藏被点击时

        /** 收藏 */
        void onSaveCollection(String code, String msg);

        void onChatClicked();  // 聊一聊
    }

    interface M extends BaseModel{
        void loadPageData(long userId);

        void saveCollection(long targetUserId);
    }
}
