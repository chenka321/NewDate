package com.saku.dateone.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import com.saku.dateone.R;
import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.ui.bean.UserInfo;
import com.saku.dateone.ui.contracts.MineContract;
import com.saku.dateone.ui.presenters.MinePresenter;
import com.saku.dateone.utils.PageManager;

import java.util.List;

public class MineFragment extends BaseFragment<MinePresenter> implements MineContract.V {
    private static final int LOGIN_REQUEST = 1;

    public static MineFragment newInstance(Bundle bundle) {
        MineFragment f = new MineFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected View getContentView() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.s_mine_fragment, mFragmentRoot, false);

        return view;
    }

    @Override
    public int getContainerId() {
        return R.id.frontpage_container_fl;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setPresenter(new MinePresenter(this));

    }

    @Override
    public void onResume() {
        super.onResume();

        if (UserInfo.getInstance().isLogin) {
            gotoLogin(PageManager.DISCOVER_LIST, LOGIN_REQUEST);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == LOGIN_REQUEST && resultCode == LoginActivity.LOGIN_OK) {
//            mPresenter.loadData();
        }
    }
}
