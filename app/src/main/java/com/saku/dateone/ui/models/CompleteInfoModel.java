package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.BasicInfoContract;
import com.saku.dateone.ui.contracts.CompleteInfoContract;

public class CompleteInfoModel extends ABaseModel<CompleteInfoContract.P> implements CompleteInfoContract.M{

    public CompleteInfoModel(CompleteInfoContract.P presenter) {
        super(presenter);
    }

    @Override
    public void saveInfo() {

    }

    @Override
    public void saveImage() {

    }
}
