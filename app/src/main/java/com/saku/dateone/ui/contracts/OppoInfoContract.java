package com.saku.dateone.ui.contracts;


import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

public interface OppoInfoContract {
    interface V extends BaseView<P> {

        void updateUserNameIv(String imgUrl, String name);
        void updateBasicInfo(UserInfo userInfo);
        void displayMoreInfoView(UserInfo userInfo);

        void markCollection(boolean isCollected);

//        void showCompleteMoreInfoDialog();
    }

    interface P extends BasePresenter{

        void loadPage(long userId);
        void onLoadPage(String code, String msg, UserInfo oppoInfo);
        UserInfo getUserInfo();
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
