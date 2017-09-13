package com.saku.dateone.ui.contracts;


import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

import java.util.ArrayList;

public interface CompleteInfoContract {
    interface V extends BaseView<P> {
//        void chooseDegree();

        /** 完成详细信息页面的开始匹配后的跳转 */
        void goNextOnCompleteInfo();

        void startPicActivity(String picPath);
    }

    interface P extends BasePresenter{

         /** 点击简单信息填写页面的开始匹配 */
        void onMatchSimpleClicked();
        /** 点击简单信息填写页面的匹配请求回调 */
        void onMatchSimpleResult(int code, String msg);

        /** 点击补充信息填写页面的开始匹配 */
        void onMatchCompleteClicked();
        void onMatchCompleteResult(int code, String msg);

        RespObserver<ApiResponse<String>, String> getUploadPicObserver();
    }

    interface M extends BaseModel{
        void startSimpleMatch(); // 开始匹配
        void startCompleteMatch(); // 开始完整信息页面匹配

        void uploadPics(ArrayList<String> picList);  // 上传图片
    }
}
