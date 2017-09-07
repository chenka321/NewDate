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
import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.contracts.DiscoversContract;
import com.saku.dateone.ui.list.typeholders.FrontPageTypeHolder;
import com.saku.dateone.ui.presenters.DiscoversPresenter;
import com.saku.dateone.ui.presenters.RecommendsPresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.lmlib.list.adapter.BaseListAdapter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.itemdecoration.SpaceDividerDecoration;
import com.saku.lmlib.utils.UIUtils;

import java.util.List;

public class DiscoversFragment extends BaseFragment<DiscoversPresenter> implements DiscoversContract.V {
    private static final int LOGIN_REQUEST = 1;
    public RecyclerView listRv;
    private BaseListAdapter listAdapter;

    public static DiscoversFragment newInstance(Bundle bundle) {
        DiscoversFragment f = new DiscoversFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected View getContentView() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.s_list_fragment, mFragmentRoot, false);
        this.listRv = (RecyclerView) view.findViewById(R.id.list);

        return view;
    }

    @Override
    public int getContainerId() {
        return R.id.frontpage_container_fl;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setPresenter(new DiscoversPresenter(this));

        showTitle(true);
        mTitleLayout.setTitleContent("发现");

        listRv.setLayoutManager(new LinearLayoutManager(getContext()));
        FrontPageTypeHolder typeHolder = new FrontPageTypeHolder(getContext(), mPresenter.getItemClickListener());
        listAdapter = new BaseListAdapter(null, typeHolder);
        listRv.setAdapter(listAdapter);
        listRv.addItemDecoration(new SpaceDividerDecoration(UIUtils.convertDpToPx(5, getContext())));

        if (UserInfo.getInstance().isLogin) {
            mPresenter.loadData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (UserInfo.getInstance().isLogin) {
            gotoLogin(PageManager.DISCOVER_LIST, LOGIN_REQUEST);
        }
    }

    @Override
    public void refreshRecyclerView(List<ItemData> data) {
        listAdapter.setData(data);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_REQUEST && resultCode == LoginActivity.LOGIN_OK) {
            mPresenter.loadData();
        }
    }
}
