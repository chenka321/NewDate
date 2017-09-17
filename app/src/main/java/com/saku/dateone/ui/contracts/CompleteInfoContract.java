package com.saku.dateone.ui.contracts;


import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

import java.util.ArrayList;
import java.util.List;

public interface CompleteInfoContract {
    interface V extends BaseView<P> {
//        void chooseDegree();

//        /** 完成详细信息页面的开始匹配后的跳转 */
//        void goNextOnCompleteInfo();

        void startPicActivity(String picPath);

        /**
         * 打开上传图片的service
         * @param compressedPaths 压缩过的图片路径
         */
        void openUploadService(List<String> compressedPaths);
    }

    interface P extends BasePresenter{

         /** 点击简单信息填写页面的开始匹配 */
        void onMatchSimpleClicked();

        /** 点击补充信息填写页面的开始匹配 */
        void onMatchCompleteClicked();

        /** 点击开始匹配保存基本信息和补充信息的请求回调 */
        RespObserver<ApiResponse<String>,String> getSaveInfoObserver();
    }

    interface M extends BaseModel{
        void saveSimpleInfo(); // 保存基本信息
        void saveCompleteInfo();  // 保存补充信息
    }
}
