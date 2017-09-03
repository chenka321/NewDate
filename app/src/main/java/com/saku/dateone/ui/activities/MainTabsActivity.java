package com.saku.dateone.ui.activities;

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
import com.saku.dateone.ui.fragments.FrontpageFragment;
import com.saku.dateone.ui.presenters.MainTabsPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liumin on 2017/8/15.
 */

public class MainTabsActivity extends BaseActivity<MainTabsPresenter> implements
        RadioGroup.OnCheckedChangeListener, MainTabsContract.V {

    private ViewPager mMainTabsVp;
    private RadioGroup mTabRg;
    private RadioButton mFrontpageRb;
    private RadioButton mMsgRb;
    private RadioButton mDiscoverRb;
    private RadioButton mMineRb;
    private MainPageAdapter mMainPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showTitle(false);

        initView();

        mTabRg.setOnCheckedChangeListener(this);
        mMainPagerAdapter = new MainPageAdapter(getSupportFragmentManager());
        mMainPagerAdapter.addFragment(FrontpageFragment.newInstance(null));
        mMainPagerAdapter.addFragment(FrontpageFragment.newInstance(null));
        mMainPagerAdapter.addFragment(FrontpageFragment.newInstance(null));
        mMainPagerAdapter.addFragment(FrontpageFragment.newInstance(null));

        mMainTabsVp.setAdapter(mMainPagerAdapter);
        mMainTabsVp.setOffscreenPageLimit(3);
        mMainTabsVp.setCurrentItem(0);
        mFrontpageRb.setChecked(true);
    }

    @Override
    protected View getContentView() {
        final View view = LayoutInflater.from(this).inflate(R.layout.s_main_activity, mRoot, false);
        return view;
    }

    @Override
    protected MainTabsPresenter getPresenter() {

        return new MainTabsPresenter(this);
    }

    private void initView() {
        mMainTabsVp = (ViewPager) findViewById(R.id.main_tabs_vp);
        mTabRg = (RadioGroup) findViewById(R.id.tab_rg);
        mFrontpageRb = (RadioButton) findViewById(R.id.frontpage_rb);
        mMsgRb = (RadioButton) findViewById(R.id.msg_rb);
        mDiscoverRb = (RadioButton) findViewById(R.id.discover_rb);
        mMineRb = (RadioButton) findViewById(R.id.mine_rb);
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

        private List<Fragment> fragments = new ArrayList<>();

        public MainPageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment) {
            fragments.add(fragment);
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
