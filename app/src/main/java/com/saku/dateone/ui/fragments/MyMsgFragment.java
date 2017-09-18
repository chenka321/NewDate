package com.saku.dateone.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.saku.dateone.R;
import com.saku.dateone.bean.MyMsg;
import com.saku.dateone.ui.activities.CompleteInfoActivity;
import com.saku.dateone.ui.activities.MainTabsActivity;
import com.saku.dateone.ui.contracts.MyMsgContract;
import com.saku.dateone.ui.list.typeholders.MyMsgTypeHolder;
import com.saku.dateone.ui.presenters.MyMsgPresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.lmlib.list.adapter.BaseListAdapter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.itemdecoration.SpaceDividerDecoration;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

import java.util.List;

/**
 * 我的 - 我的消息 - 消息列表
 */
public class MyMsgFragment extends BaseFragment<MyMsgPresenter> implements MyMsgContract.V {
    public RecyclerView listRv;
    private BaseListAdapter listAdapter;

    public static MyMsgFragment newInstance(Bundle bundle) {
        MyMsgFragment f = new MyMsgFragment();
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

        setPresenter(new MyMsgPresenter(this));

        initRecyclerView();
        setTitle();
//        if (getArguments() != null) {
//            final long userId = getArguments().getLong(Consts.MY_USER_ID);
//        }
        mPresenter.loadPage();
    }

    private void initRecyclerView() {
        listRv.setLayoutManager(new LinearLayoutManager(getContext()));
        MyMsgTypeHolder typeHolder = new MyMsgTypeHolder(mContext, mPresenter.getItemClick());
        listAdapter = new BaseListAdapter(null, typeHolder);
        listRv.setAdapter(listAdapter);
        listRv.addItemDecoration(new SpaceDividerDecoration(UIUtils.convertDpToPx(5, getContext())));
    }

    private void setTitle() {
        showTitle(true);

        mTitleLayout.setTitleContent("我的消息");
        mTitleLayout.showLeftBtn(true);
    }

    @Override
    public void refreshRecyclerView(List<ItemData> data) {
        listAdapter.setData(data);
    }

    @Override
    public void goToNext(int msgType, MyMsg myMsg) {
        switch (msgType) {
            case 1:  // 填写 详细信息
                toActivity(CompleteInfoActivity.class, null, false);
                break;
            case 2:  // 聊天消息列表
                Bundle bundle = new Bundle();
                bundle.putInt(Consts.SHOW_MAIN_TAB_PAGE, PageManager.CHAT_LIST);
                toActivity(MainTabsActivity.class, bundle, false);
                break;
            case 3:
                Bundle rBundle = new Bundle();
                rBundle.putInt(Consts.SHOW_MAIN_TAB_PAGE, PageManager.RECOMMEND_LIST);
                toActivity(MainTabsActivity.class, rBundle, false);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LLog.d("lm", "MyMsgFragment onResume: ");

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LLog.d("lm", "MyMsgFragment setUserVisibleHint: " + isVisibleToUser);

    }

    @Override
    public boolean onBackPressed() {
        getFragmentManager().popBackStack();  // 得到管理当前fragment的fragmentmanager，如果要在该fragment中操作下层的fragment，则需要用getChildFragmentManager
        return true;
    }
}
