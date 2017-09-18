package com.saku.dateone.ui.models;

import com.saku.dateone.ui.contracts.EntryInfoContract;

public class BasicInfoModel extends ABaseModel<EntryInfoContract.P> implements EntryInfoContract.M{

    public BasicInfoModel(EntryInfoContract.P presenter) {
        super(presenter);
    }
}
