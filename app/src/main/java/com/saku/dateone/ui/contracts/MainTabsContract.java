package com.saku.dateone.ui.contracts;

import android.os.Bundle;

import com.saku.dateone.ui.models.BaseModel;
import com.saku.dateone.ui.presenters.BasePresenter;

public interface MainTabsContract {
    interface V extends BaseView<P> {

    }

    interface P extends BasePresenter {
        void onArguments(Bundle bundle);

    }

    interface M extends BaseModel {
    }
}
