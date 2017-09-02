package com.saku.dateone.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.saku.dateone.R;
import com.saku.dateone.ui.contracts.FrontpageContract;
import com.saku.dateone.ui.list.typeholders.FrontPageTypeHolder;
import com.saku.dateone.ui.presenters.FrontPagePresenter;
import com.saku.lmlib.list.adapter.BaseListAdapter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.itemdecoration.LineDividerDecoration;
import com.saku.lmlib.list.itemdecoration.SpaceDividerDecoration;
import com.saku.lmlib.utils.UIUtils;

import java.util.List;

public class FrontpageFragment extends BaseFragment<FrontPagePresenter> implements FrontpageContract.V {
    public RecyclerView listRv;
    private BaseListAdapter listAdapter;

    public static FrontpageFragment newInstance(Bundle bundle) {
        FrontpageFragment f = new FrontpageFragment();
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

        setPresenter(new FrontPagePresenter(this));

        showTitle(true);
        mTitleLayout.setTitleContent("推荐列表");

        listRv.setLayoutManager(new LinearLayoutManager(getContext()));
        FrontPageTypeHolder typeHolder = new FrontPageTypeHolder(getContext(), mPresenter.getItemClickListener());
        listAdapter = new BaseListAdapter(null, typeHolder);
        listRv.setAdapter(listAdapter);
        listRv.addItemDecoration(new SpaceDividerDecoration(UIUtils.convertDpToPx(5, getContext())));

        mPresenter.loadData();
    }


    @Override
    public void refreshRecyclerView(List<ItemData> data) {
        listAdapter.setData(data);
    }
}
