package com.saku.dateone.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.saku.dateone.R;
import com.saku.dateone.ui.contracts.RecommendsContract;
import com.saku.dateone.ui.list.typeholders.RecommendTypeHolder;
import com.saku.dateone.ui.list.viewprocessors.RecommendVProcessor;
import com.saku.dateone.ui.presenters.RecommendsPresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.list.adapter.BaseListAdapter;
import com.saku.lmlib.list.data.ItemData;
import com.saku.lmlib.list.itemdecoration.SpaceDividerDecoration;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

import java.util.List;

public class RecommendsFragment extends UserInfoFragment<RecommendsContract.P> implements RecommendsContract.V {
    public RecyclerView listRv;
    private BaseListAdapter listAdapter;
    private int pageType;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsLoadingMore;
    private boolean refreshData;  // 从其他页面进来是否需要刷新数据

    public static RecommendsFragment newInstance(Bundle bundle) {
        RecommendsFragment f = new RecommendsFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected View getNormalContent() {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.s_list_fragment, mFragmentRoot, false);
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

        setPresenter(new RecommendsPresenter(this));
        initRecyclerView();

        setTitle();

        if (pageType == RecommendVProcessor.TYPE_COLLECTION) {
            mPresenter.loadCollectionList();
        } else {
            loadRecommendData();
        }

    }

    private void initRecyclerView() {
        mLayoutManager = new LinearLayoutManager(mContext);
        listRv.setLayoutManager(mLayoutManager);
        pageType = getArguments() != null ? getArguments().getInt(Consts.USERINFO_LIST_TYPE,
                RecommendVProcessor.TYPE_RECOMMEND) : RecommendVProcessor.TYPE_RECOMMEND;
        RecommendTypeHolder typeHolder = new RecommendTypeHolder(mContext, pageType, mPresenter.getItemClickListener());
        listAdapter = new BaseListAdapter(null, typeHolder);
        listRv.setAdapter(listAdapter);
        listRv.addItemDecoration(new SpaceDividerDecoration(UIUtils.convertDpToPx(5, mContext)));

        setIsLoadingMore(false);
        listRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && !mIsLoadingMore) {
                    final int visibleCount = mLayoutManager.getChildCount();
                    final int totalCount = mLayoutManager.getItemCount();
                    final int pastCount = mLayoutManager.findFirstVisibleItemPosition();

                    if (visibleCount + pastCount >= totalCount) {
                        recyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                setIsLoadingMore(true);
                                loadRecommendData();
                            }
                        });
                    }
                }
            }
        });
    }

    private void setTitle() {
        showTitle(true);
        if (pageType == RecommendVProcessor.TYPE_COLLECTION) {
            mTitleLayout.setTitleContent("收藏");
            mTitleLayout.showLeftBtn(true);
            return;
        }
        final String bornLocation = UserInfoManager.getInstance().getMyPendingInfo().bornLocation;
        if (TextUtils.isEmpty(bornLocation)) {
            mTitleLayout.setTitleContent("推荐列表");
        } else {
            mTitleLayout.setTitleContent(bornLocation);
        }
    }

    /**
     * 根据是否登录来拉不同对应接口的推荐数据
     */
    private void loadRecommendData() {
        showLoading();
        if (UserInfoManager.getInstance().isLogin()) {
            mPresenter.loadLoginData();
        } else {
            mPresenter.loadNotLoginData();
        }
    }

    @Override
    public void setRecyclerViewData(List<ItemData> data) {
        listAdapter.setData(data);
    }

    @Override
    public void refreshRecyclerView() {
        listAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        LLog.d("lm", "RecommendFragment onResume: ");

        // 填完补充信息跳过来要拉数据, 填写简单信息， 补充信息， 我的-我的消息某一种类型
        if (refreshData) {
            refreshData = false;
            mPresenter.clearDataList();
            mPresenter.setCurrentPage(0);
            loadRecommendData();
            LLog.d("lm", "RecommendFragment onResume:  loading -------- ");
        }
    }

    @Override
    public void setIsLoadingMore(boolean loadingState) {
        this.mIsLoadingMore = loadingState;
    }

    /**
     * 设置刷新数据
     */
    public void refresh() {
        refreshData = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LLog.d("lm", "RecommendFragment setUserVisibleHint: " + isVisibleToUser);

    }


    @Override
    public boolean onBackPressed() {
        if (pageType == RecommendVProcessor.TYPE_COLLECTION) {
            getFragmentManager().popBackStack();  // 得到管理当前fragment的fragmentmanager，如果要在该fragment中操作下层的fragment，则需要用getChildFragmentManager
            return true;
        } else {
            return super.onBackPressed();
        }

    }
}
