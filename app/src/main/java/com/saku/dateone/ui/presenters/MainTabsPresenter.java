package com.saku.dateone.ui.presenters;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.saku.dateone.bean.TypeConfig;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.OnRespResult;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.storage.FileUtils;
import com.saku.dateone.ui.contracts.MainTabsContract;
import com.saku.dateone.ui.models.MainTabsModel;
import com.saku.dateone.utils.GsonUtils;
import com.saku.dateone.utils.TypeManager;

public class MainTabsPresenter extends ABasePresenter<MainTabsContract.V, MainTabsContract.M> implements MainTabsContract.P {

    public MainTabsPresenter(MainTabsContract.V mView) {
        super(mView);
        mModel.loadTypeConfig();
    }

    @Override
    protected MainTabsContract.M getModel() {
        return new MainTabsModel(this);
    }

    @Override
    public void onArguments(Bundle bundle) {

    }

    @Override
    public RespObserver<ApiResponse<TypeConfig>, TypeConfig> getTypeConfigObserver() {
        return new RespObserver<ApiResponse<TypeConfig>, TypeConfig>() {
            @Override
            public void onSuccess(TypeConfig data) {
                Log.d("lm", "TypeConfig onSucess: " + data);
                TypeManager.getInstance().setTypeConfig(data);
            }

            @Override
            public void onFail(int code, String msg) {
                Log.d("lm", "TypeConfig onFail: " + msg);
                TypeManager.getInstance().setTypeConfig(null);

            }
        };
    }
}
