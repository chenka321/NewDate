package com.saku.dateone.ui.contracts;


import android.os.Bundle;
import android.view.View;

import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

import java.util.List;

public interface CompleteInfoContract {
    interface V extends BaseView<P> {
//        void chooseDegree();

        /** 完成详细信息页面的开始匹配后的跳转 */
        void goNextOnCompleteInfo();

        void startPicActivity(String picPath);
    }

    interface P extends BasePresenter{
        /** 选择照片返回 */
        void onChooseImages(List<String> imgsChosen);

         /** 点击简单信息填写页面的开始匹配 */
        void onMatchSimpleClicked();
        /** 点击简单信息填写页面的匹配请求回调 */
        void onMatchSimpleResult(int code, String msg);

        /** 点击补充信息填写页面的开始匹配 */
        void onMatchCompleteClicked();
        void onMatchCompleteResult(int code, String msg);
    }

    interface M extends BaseModel{
        void startSimpleMatch(); // 开始匹配
        void startCompleteMatch(); // 开始完整信息页面匹配
        void saveImage();
    }
}
