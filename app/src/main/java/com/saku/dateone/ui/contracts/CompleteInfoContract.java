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
        void chooseDegree();
    }

    interface P extends BasePresenter{
        /** 选择照片返回 */
        void onChooseImages(List<String> imgsChosen);
        void onSaveInfoClicked();
    }

    interface M extends BaseModel{
        void saveInfo(); // 保存所有信息
        void saveImage();
    }
}
