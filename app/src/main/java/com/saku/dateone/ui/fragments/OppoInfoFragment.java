package com.saku.dateone.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saku.dateone.R;
import com.saku.dateone.ui.presenters.OppoInfoPresenter;

public class OppoInfoFragment extends ChildBaseFragment<OppoInfoPresenter> {


    public static OppoInfoFragment newInstance(Bundle bundle) {
        OppoInfoFragment f = new OppoInfoFragment();
        f.setArguments(bundle);
        return f;
    }


    @Override
    protected View getContentView() {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.s_opp_info_fragment, mFragmentRoot, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        showTitle(true);
        mTitleLayout.showLeftBtn(true);
    }
}
