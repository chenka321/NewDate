package com.saku.dateone.ui.presenters;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.bean.TagString;
import com.saku.dateone.ui.contracts.RecommendsContract;
import com.saku.dateone.ui.activities.OppoInfoActivity;
import com.saku.dateone.ui.list.data.EmptyData;
import com.saku.dateone.ui.list.data.RecommendItemData;
import com.saku.dateone.ui.models.RecommendsModel;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.saku.dateone.ui.activities.OppoInfoActivity.USER_ID;

/**
 * User: liumin
 * Date: 2017-8-31
 * Time: 10:56
 * Description: 推荐列表页面
 */
public class RecommendsPresenter extends UserInfoPresenter<RecommendsContract.V, RecommendsContract.M> implements RecommendsContract.P {

    private List<ItemData> mData = new ArrayList<>();
    private AtomicInteger mCurrPage = new AtomicInteger(0);

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

                Intent intent;
                if (UserInfoManager.getInstance().isLogin()) {
                    if (checkUserInfo()) {  // 用户已经登录但没有填写基本信息
                        return;
                    }

                    intent = new Intent(mView.getViewContext(), OppoInfoActivity.class);
                    putUserIdInIntent(position, intent);
                    mView.toActivity(intent, false);

                } else {
                    intent = new Intent(mView.getViewContext(), LoginActivity.class);
                    putUserIdInIntent(position, intent);
                    intent.putExtra(Consts.LOGIN_FROM_PAGE_NAME, PageManager.RECOMMEND_LIST);
                    mView.toActivity(intent, false);
                }

            }
        };
    }

    private void putUserIdInIntent(int position, Intent intent) {
        if (mData == null || mData.size() - 1 < position) {
            return;
        }
        final ItemData itemData = mData.get(position);
        if (itemData instanceof RecommendItemData) {
            final long userId = ((RecommendItemData) itemData).userId;
            intent.putExtra(USER_ID, userId);
        }
    }

    @Override
    protected RecommendsContract.M getModel() {
        return new RecommendsModel(this);
    }

    @Override
    public void clearDataList() {
        if (mData != null) {
            mData.clear();
        }
    }

    @Override
    public void addLoadMoreItem() {
        if (mData == null || mData.size() == 0) {
            return;
        }
        this.mData.add(new EmptyData());
        Log.d("lm", "RecommendsPresenter ------ addLoadMoreItem: ");
        mView.refreshRecyclerView();
    }

    @Override
    public void loadNotLoginData() {
        mModel.loadNotLoginData();
    }

    @Override
    public void onLoadNotLoginDatResult(int code, String msg) {
        if (mData.size() == 0) {
            mView.setRecyclerViewData(mData);
        }
//        if (mData.size()  > 0 && mData.get(mData.size() -1) instanceof EmptyData) {
//            mData.remove(mData.get(mData.size() -1));
//        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                SystemClock.sleep(3000);
                mView.dismissLoading();
                mCurrPage.addAndGet(1);
                Log.d("lm", "RecommendsPresenter ------ run: currPage = " + mCurrPage);
                mView.setIsLoadingMore(false);
                if (mData.size()  > 0 && mData.get(mData.size() -1) instanceof EmptyData) {
                    Log.d("lm", "RecommendsPresenter ------ run: remove loadingmore");
                    mData.remove(mData.get(mData.size() -1));
                }

                for (int i = 0; i < 5; i++) {
                    RecommendItemData pageData = new RecommendItemData();
                    pageData.birthday = "30";
                    pageData.currentLocation = "上海" + i;
                    pageData.name = "贝贝 notLogin-- " + i;
                    pageData.ocupation = "作家" + i;
                    pageData.userId = i + 1;
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
                ((Activity) mView.getViewContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.refreshRecyclerView();
                    }
                });
            }
        }).start();


//        mView.setRecyclerViewData(mData);
//        mView.refreshRecyclerView();
    }

    @Override
    public void loadLoginData() {
        mModel.loadLoginData();
    }

    @Override
    public void onLoadLoginDataResult(int code, String msg) {
        if (mData.size() == 0) {
            mView.setRecyclerViewData(mData);
        }
//        if (mData.size()  > 0 && mData.get(mData.size() -1) instanceof EmptyData) {
//            mData.remove(mData.get(mData.size() -1));
//        }
//        for (int i = 0; i < 5; i++) {
//            RecommendItemData pageData = new RecommendItemData();
//            pageData.birthday = "30";
//            pageData.currentLocation = "上海 " + i;
//            pageData.name = "贝贝 login" + i;
//            pageData.ocupation = "作家" + i;
//            pageData.userId = i + 1;
////            pageData.userImg
//            pageData.bornLocation = "海南" + i;
//            pageData.tags = new ArrayList<>();
//            for (int j = 0; j < 4; j++) {
//                TagString ts = new TagString();
//                ts.text = "才华横溢" + j;
//                ts.rgbValue = "#B266FF";
//                pageData.tags.add(ts);
//            }
//            mData.add(pageData);
//        }
//
////        mView.setRecyclerViewData(mData);
//        mView.refreshRecyclerView();

        new Thread(new Runnable() {
            @Override
            public void run() {

                SystemClock.sleep(3000);
                mView.dismissLoading();

                mCurrPage.addAndGet(1);
                Log.d("lm", "RecommendsPresenter ------ run: currPage = " + mCurrPage);
                mView.setIsLoadingMore(false);
                if (mData.size()  > 0 && mData.get(mData.size() -1) instanceof EmptyData) {
                    Log.d("lm", "RecommendsPresenter ------ run: remove loadingmore");
                    mData.remove(mData.get(mData.size() -1));
                }

                for (int i = 0; i < 5; i++) {
                    RecommendItemData pageData = new RecommendItemData();
                    pageData.birthday = "30";
                    pageData.currentLocation = "上海" + i;
                    pageData.name = "贝贝 login-- " + i;
                    pageData.ocupation = "作家" + i;
                    pageData.userId = i + 1;
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
                ((Activity) mView.getViewContext()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mView.refreshRecyclerView();
                    }
                });
            }
        }).start();
    }

    @Override
    public void setCurrentPage(int index) {
        mCurrPage.set(index);
    }

    @Override
    public boolean checkUserInfo() {
        if (!UserInfoManager.getInstance().hasSimpleLocal()) {
            mView.showFillBasicInfoDialog();
            return true;
        } else {
            return false;   // 这里没看到拉用户信息的必要。
        }
    }
}
