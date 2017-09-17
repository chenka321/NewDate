package com.saku.dateone.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.saku.dateone.R;
import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.ui.contracts.DiscoversContract;
import com.saku.dateone.ui.list.typeholders.RecommendTypeHolder;
import com.saku.dateone.ui.list.viewprocessors.RecommendVProcessor;
import com.saku.dateone.ui.presenters.DiscoversPresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.list.adapter.BaseListAdapter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.itemdecoration.SpaceDividerDecoration;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

import java.util.List;

/**
 * 发现
 */
public class DiscoversFragment extends UserInfoFragment<DiscoversPresenter> implements DiscoversContract.V {

    public RecyclerView listRv;
    private BaseListAdapter listAdapter;
    private boolean isLoadingDiscover = false;

    public static DiscoversFragment newInstance(Bundle bundle) {
        DiscoversFragment f = new DiscoversFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected View getNormalContent() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.s_list_fragment, mFragmentRoot, false);
        this.listRv = (RecyclerView) view.findViewById(R.id.list);
        return view;
    }


    @Override
    protected void login() {
        gotoLogin(PageManager.DISCOVER_LIST, Consts.LOGIN_RQST_DISCOVER);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LLog.d("lm", "discoverFragment onViewCreated: ");

        setPresenter(new DiscoversPresenter(this));

        showTitle(true);
        mTitleLayout.setTitleContent("发现");

        listRv.setLayoutManager(new LinearLayoutManager(getContext()));
        RecommendTypeHolder typeHolder = new RecommendTypeHolder(getContext(), RecommendVProcessor.TYPE_RECOMMEND, mPresenter.getItemClickListener());
        listAdapter = new BaseListAdapter(null, typeHolder);
        listRv.setAdapter(listAdapter);
        listRv.addItemDecoration(new SpaceDividerDecoration(UIUtils.convertDpToPx(5, getContext())));

        if (UserInfoManager.getInstance().isLogin()) {
            mPresenter.loadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LLog.d("lm", "discoverFragment onResume: ");  // // 造成登录页面 没登录就返回 又进入登录页
//        if (getCurrentTab() != 2) {
//            return;
//        }
//        if (!isLoadingDiscover && !UserInfoManager.getInstance().isLogin()) {
//            gotoLogin(PageManager.DISCOVER_LIST, Consts.LOGIN_RQST_DISCOVER);
//        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LLog.d("lm", "discoverFragment onHiddenChanged: ");

    }

    @Override
    public void refreshRecyclerView(List<ItemData> data) {
        listAdapter.setData(data);
    }

    @Override
    public void setIsLoadingDiscover(boolean isLoading) {
        isLoadingDiscover = isLoading;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            if (isLoadingDiscover) {
                return;
            }
            if (!UserInfoManager.getInstance().isLogin()) {
                showErrorLogin();
            } else {
                hideErrorView();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LLog.d("lm", "discoverFragment onActivityResult: ");
        if (requestCode == Consts.LOGIN_RQST_DISCOVER) {
            mPresenter.loadData();
        }
    }
}
