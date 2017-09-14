package com.saku.dateone.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.saku.dateone.R;
import com.saku.dateone.ui.contracts.MainTabsContract;
import com.saku.dateone.ui.fragments.ChatListFragment;
import com.saku.dateone.ui.fragments.DiscoversFragment;
import com.saku.dateone.ui.fragments.MineFragment;
import com.saku.dateone.ui.fragments.RecommendsFragment;
import com.saku.dateone.ui.presenters.MainTabsPresenter;
import com.saku.dateone.ui.views.DisabledScrollViewPager;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.lmlib.utils.LLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liumin on 2017/8/15.
 */

public class MainTabsActivity extends BaseActivity<MainTabsPresenter> implements
        RadioGroup.OnCheckedChangeListener, MainTabsContract.V {

    private DisabledScrollViewPager mMainTabsVp;
    private RadioGroup mTabRg;
    private RadioButton mFrontpageRb;
    private RadioButton mMsgRb;
    private RadioButton mDiscoverRb;
    private RadioButton mMineRb;
    private MainPageAdapter mMainPagerAdapter;

    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showTitle(false);

        initView();

        mTabRg.setOnCheckedChangeListener(this);
        mMainPagerAdapter = new MainPageAdapter(getSupportFragmentManager());
        fragments = new ArrayList<>();

        fragments.add(RecommendsFragment.newInstance(null));
        fragments.add(ChatListFragment.newInstance(null));
        fragments.add(DiscoversFragment.newInstance(null));
        fragments.add(MineFragment.newInstance(null));
        mMainPagerAdapter.setFragments(fragments);
        mMainTabsVp.setAdapter(mMainPagerAdapter);
        mMainTabsVp.setOffscreenPageLimit(3);
        mFrontpageRb.setChecked(true);
        mMainTabsVp.setPagingEnabled(false);

        showPageOnIntent(getIntent());
    }


    private void showPageOnIntent(Intent intent) {
        if (intent != null) {
            final int page = intent.getIntExtra(Consts.SHOW_MAIN_TAB_PAGE, 0);
            if (page == PageManager.RECOMMEND_LIST) {
                ((RecommendsFragment) fragments.get(0)).refresh();
                mMainTabsVp.setCurrentItem(0);
                mTabRg.check(mFrontpageRb.getId());

            } else if (page == PageManager.CHAT_LIST) {
                mMainTabsVp.setCurrentItem(1);
                mTabRg.check(mMsgRb.getId());
            } else if (page == PageManager.DISCOVER_LIST) {
                mMainTabsVp.setCurrentItem(2);
                mTabRg.check(mDiscoverRb.getId());
            } else if (page == PageManager.MINE) {
                mMainTabsVp.setCurrentItem(3);
                mTabRg.check(mMineRb.getId());
            } else {
                mMainTabsVp.setCurrentItem(0);
                mTabRg.check(mFrontpageRb.getId());
            }

        } else {
            mMainTabsVp.setCurrentItem(0);
            mTabRg.check(mFrontpageRb.getId());

        }
    }

    public int getCurrentPageSelected() {
        return mMainTabsVp.getCurrentItem();
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(this).inflate(R.layout.s_main_activity, mRoot, false);
    }

    @Override
    protected MainTabsPresenter getPresenter() {

        return new MainTabsPresenter(this);
    }

    private void initView() {
        mMainTabsVp = (DisabledScrollViewPager) findViewById(R.id.main_tabs_vp);
        mTabRg = (RadioGroup) findViewById(R.id.tab_rg);
        mFrontpageRb = (RadioButton) findViewById(R.id.frontpage_rb);
        mMsgRb = (RadioButton) findViewById(R.id.msg_rb);
        mDiscoverRb = (RadioButton) findViewById(R.id.discover_rb);
        mMineRb = (RadioButton) findViewById(R.id.mine_rb);
    }

    /**
     * 从entryinfoActicity\ simpleInfoActivity\ CompleteInfoActivity 开始匹配\ mineFragment# 修改信息
     * 跳转到该页面，刷新数据
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LLog.d("mainTabActivity ...onNewIntent ");

        showPageOnIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.frontpage_rb:
                mMainTabsVp.setCurrentItem(0);
                break;
            case R.id.msg_rb:
                mMainTabsVp.setCurrentItem(1);
                break;
            case R.id.discover_rb:
                mMainTabsVp.setCurrentItem(2);
                break;
            case R.id.mine_rb:
                mMainTabsVp.setCurrentItem(3);
                break;
        }
    }

    private class MainPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public MainPageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setFragments(List<Fragment> fragments) {
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

}
