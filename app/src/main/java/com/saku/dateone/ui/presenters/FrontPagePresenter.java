package com.saku.dateone.ui.presenters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.ui.bean.TagString;
import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.contracts.FrontpageContract;
import com.saku.dateone.ui.fragments.OppoInfoFragment;
import com.saku.dateone.ui.list.data.FrontPageData;
import com.saku.dateone.ui.models.FrontpageModel;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.utils.PageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 10:56
 * Description: 推荐列表页面
*/
public class FrontPagePresenter extends ABasePresenter<FrontpageContract.V, FrontpageContract.M> implements FrontpageContract.P {

    public FrontPagePresenter(FrontpageContract.V mView) {
        super(mView);
    }



    @Override
    public OnRecyclerClickCallBack getItemClickListener() {
        return new OnRecyclerClickCallBack() {
            @Override
            public void onClick(int position, View view) {
                Log.d("lm", "onClick: position = " + position);
                if (mView == null) {
                    return;
                }
                UserInfo.getInstance().isLogin = true;
                if (!UserInfo.getInstance().isLogin) {
                    if (mView.getViewContext() != null) {
                        Intent intent = new Intent(mView.getViewContext(), LoginActivity.class);
                        mView.toActivity(intent, false);
                    }
                } else {
//                    mView.showLoading();
                    mView.addFragment(OppoInfoFragment.newInstance(null));
                }
            }
        };
    }

    @Override
    protected FrontpageContract.M getModel() {
        return new FrontpageModel(this);
    }

    @Override
    public void loadData() {
        mModel.loadData();

        List<ItemData> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FrontPageData pageData = new FrontPageData();
            pageData.age = 30;
            pageData.city = "上海 " + i;
            pageData.name = "贝贝" +i;
            pageData.ocupation = "作家" +i;
//            pageData.userImg
            pageData.residence = "海南" + i;
            pageData.tags = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                TagString ts = new TagString();
                ts.text = "才华横溢" + j;
                ts.rgbValue = "#B266FF";
                pageData.tags.add(ts);
            }
            data.add(pageData);
        }

        mView.refreshRecyclerView(data);
    }

}
