package com.saku.dateone.ui.contracts;

import android.os.Bundle;

import com.saku.dateone.bean.TypeConfig;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.OnRespResult;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

import java.util.List;

public interface MainTabsContract {
    interface V extends BaseView<P> {

    }

    interface P extends BasePresenter {
        void onArguments(Bundle bundle);
        RespObserver<ApiResponse<TypeConfig>, TypeConfig> getTypeConfigObserver();
    }

    interface M extends BaseModel {
        void loadTypeConfig();

    }
}
