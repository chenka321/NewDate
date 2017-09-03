package com.saku.dateone.ui.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.saku.dateone.R;
import com.saku.dateone.ViewUnion;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.dateone.ui.views.TitleLayout;
import com.saku.lmlib.fragment.backpress.BackPressHandler;
import com.saku.lmlib.fragment.backpress.BackPressListener;
import com.saku.lmlib.utils.LLog;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liumin on 2017/8/15.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements ViewUnion, BackPressHandler {
    protected LinearLayout mRoot;
    protected TitleLayout mTitleLayout;
    protected P mPresenter;
    private ProgressDialog mLoadingDialog;
    private List<WeakReference<BackPressListener>> backPressListeners = new LinkedList<>();
    private List<WeakReference<Fragment>> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LLog.d("lm", "onCreate: " + this.getComponentName());
        setContentView(R.layout.s_base_activity);
        initView();
        View contentView = getContentView();
        mRoot.addView(contentView);

        setPresenter(getPresenter());

        this.getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentAttached(FragmentManager fm, Fragment f, Context context) {
                mFragments.add(new WeakReference<Fragment>(f));
            }

            @Override
            public void onFragmentDetached(FragmentManager fm, Fragment f) {
                final Iterator<WeakReference<Fragment>> iterator = mFragments.iterator();
                if (iterator.hasNext()) {
                    final WeakReference<Fragment> frag = iterator.next();
                    if (frag != null && frag.get() == f) {
                        mFragments.remove(frag);
                    }
                }
            }
        }, false);
    }

    private void initView() {
        mRoot = (LinearLayout) findViewById(R.id.root);
        mTitleLayout = (TitleLayout) findViewById(R.id.title_bar);

        // 设置默认的点击事件
        mTitleLayout.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 是否展示标题栏
     */
    protected void showTitle(boolean show) {
        if (show) {
            mTitleLayout.setVisibility(View.VISIBLE);
        } else {
            mTitleLayout.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LLog.d("lm", "onResume: " + this.getComponentName());
    }

    @Override
    protected void onPause() {
        super.onPause();
        LLog.d("lm", "onPause: " + this.getComponentName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LLog.d("lm", "onDestroy: " + this.getComponentName());

        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

    public void toActivity(Class<? extends BaseActivity> clz, Bundle bundle) {
        Intent i = new Intent(this, clz);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        startActivity(i);
    }

    protected void setPresenter(P p) {
        this.mPresenter = p;
    }


    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new ProgressDialog(this);
            mLoadingDialog.setCancelable(false);
            mLoadingDialog.setMessage("正在加载...");
        }
        mLoadingDialog.show();
    }

    @Override
    public void dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }

    @Override
    public void toActivity(Intent intent, boolean finishSelf) {
        startActivity(intent);
        if (finishSelf) {
            finish();
        }
    }

    @Override
    public void addFragment(Fragment newFrag) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (newFrag.isAdded()) {
            transaction.show(newFrag).commit();
        } else {
            transaction.add(newFrag, newFrag.getClass().getName())
                    .addToBackStack(newFrag.getClass().getName())
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        Log.d("lm", "baseactivity onBackPressed: getSupportFragmentManager = " + getSupportFragmentManager());
//        final List<Fragment> fragments = getSupportFragmentManager().getFragments();
//        if (fragments != null && fragments.size() > 0) {

            if (mFragments != null && mFragments.size() > 0) {
                for (WeakReference<BackPressListener> backListener : backPressListeners) {
                    if (backListener.get() != null) {
                        final boolean handledByFragment = backListener.get().onBackPressed();
                        if (handledByFragment) {
                            return;
                        }
                    }
                }
            }
            super.onBackPressed();
    }

    @Override
    public void addBackPressListener(BackPressListener backListener) {
        backPressListeners.add(new WeakReference<>(backListener));
    }

    @Override
    public void removeBackPressListener(BackPressListener backListener) {
        for (Iterator<WeakReference<BackPressListener>> iterator = backPressListeners.iterator();
             iterator.hasNext(); ) {
            WeakReference<BackPressListener> weakRef = iterator.next();
            if (weakRef.get() == backListener) {
                iterator.remove();
            }
        }
    }

    protected abstract View getContentView();

    protected abstract P getPresenter();

}
