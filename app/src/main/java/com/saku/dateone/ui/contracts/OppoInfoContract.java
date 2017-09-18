package com.saku.dateone.ui.contracts;


import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

public interface OppoInfoContract {
    interface V extends BaseView<P> {

        void updateUserNameIv(String imgUrl, String name);
        void updateBasicInfo(UserInfo userInfo);
        void displayMoreInfoView(UserInfo userInfo);

        void markCollection(boolean isCollected);

        void startPicActivity(String picPath);  // 查看大图

//        void showCompleteMoreInfoDialog();
    }

    interface P extends BasePresenter{

        void loadPage(long userId);
        UserInfo getUserInfo();
        boolean hasMoreInfo();

        void onCollectionClicked();  // 收藏被点击时

        public OnRecyclerClickCallBack getPicItemClickListener(); // 更多- 图片被点击

        void onChatClicked();  // 聊一聊

        RespObserver<ApiResponse<UserInfo>, UserInfo> getCurrUserInfoObserver();

        RespObserver<ApiResponse<Boolean>, Boolean> getCollectionObserver();

        void uploadIcon(String iconPath);  // 上传用户头像
    }

    interface M extends BaseModel{
        void loadPageData(long userId);

        void saveCollection(long targetUserId);

        void uploadIcon(String iconPath);
    }
}
