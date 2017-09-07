package com.saku.dateone.ui.presenters;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.ui.bean.TagString;
import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.contracts.RecommendsContract;
import com.saku.dateone.ui.activities.OppoInfoActivity;
import com.saku.dateone.ui.list.data.FrontPageData;
import com.saku.dateone.ui.models.RecommendsModel;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.ArrayList;
import java.util.List;

import static com.saku.dateone.ui.activities.OppoInfoActivity.USER_ID;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 10:56
 * Description: 推荐列表页面
*/
public class RecommendsPresenter extends ABasePresenter<RecommendsContract.V, RecommendsContract.M> implements RecommendsContract.P {

    private List<ItemData> mData;

    public RecommendsPresenter(RecommendsContract.V mView) {
        super(mView);
    }



    @Override
    public OnRecyclerClickCallBack getItemClickListener() {
        return new OnRecyclerClickCallBack() {
            @Override
            public void onClick(int position, View view) {
                Log.d("lm", "onClick: position = " + position);
                if (mView == null || mView.getViewContext() == null) {
                    return;
                }
                UserInfo.getInstance().isLogin = false;
                Intent intent;
                if (!UserInfo.getInstance().isLogin) {
                    intent = new Intent(mView.getViewContext(), LoginActivity.class);
                } else {
                    intent = new Intent(mView.getViewContext(), OppoInfoActivity.class);

//                    mView.showLoading();
//                    mView.addFragment(OppoInfoActivity.newInstance(null));
                }

                final ItemData itemData = mData.get(position);
                if (itemData instanceof FrontPageData) {
                    final long userId = ((FrontPageData) itemData).userId;
                    intent.putExtra(USER_ID, userId);
                }
                mView.toActivity(intent, false);
            }
        };
    }

    @Override
    protected RecommendsContract.M getModel() {
        return new RecommendsModel(this);
    }

    @Override
    public void loadData() {
        mModel.loadData();

        mData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            FrontPageData pageData = new FrontPageData();
            pageData.birthday = "30";
            pageData.currentLocation = "上海 " + i;
            pageData.name = "贝贝" +i;
            pageData.ocupation = "作家" +i;
            pageData.userId = i+1;
//            pageData.userImg
            pageData.bornLocation = "海南" + i;
            pageData.tags = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                TagString ts = new TagString();
                ts.text = "才华横溢" + j;
                ts.rgbValue = "#B266FF";
                pageData.tags.add(ts);
            }
            mData.add(pageData);
        }

        mView.refreshRecyclerView(mData);
    }

}
