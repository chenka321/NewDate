package com.saku.dateone.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.saku.dateone.R;
import com.saku.dateone.ViewUnion;
import com.saku.dateone.ui.activities.BaseActivity;
import com.saku.dateone.ui.activities.LoginActivity;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.dateone.ui.views.TitleLayout;
import com.saku.dateone.utils.Consts;
import com.saku.lmlib.fragment.backpress.BackPressListener;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements ViewUnion, BackPressListener {
    protected TitleLayout mTitleLayout;
    protected P mPresenter;
    protected LinearLayout mFragmentRoot;
    public Context mContext;
    private ProgressDialog mLoadingDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("lm", "onCreateView: " + this.getTag());
        mFragmentRoot = (LinearLayout) inflater.inflate(R.layout.s_base_activity, container, false);
        mFragmentRoot.addView(getContentView());
        initView();
        return mFragmentRoot;
    }

    private void initView() {
        mTitleLayout = (TitleLayout) mFragmentRoot.findViewById(R.id.title_bar);

        // 设置默认的点击事件
        mTitleLayout.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        Log.d("lm", "onAttach: " + this.getTag());

        super.onAttach(context);
        mContext = context;

        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).addBackPressListener(this);
            }
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("lm", "onDetach: " + this.getTag());

        if (getActivity() != null) {
            if (getActivity() instanceof BaseActivity) {
                ((BaseActivity) getActivity()).removeBackPressListener(this);
            }
        }
    }

    protected void showTitle(boolean show) {
        if (show) {
            mTitleLayout.setVisibility(View.VISIBLE);
        } else {
            mTitleLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("lm", "onDestroy: " + this.getTag());

        if (mPresenter != null) {
            mPresenter.destroy();
            mPresenter = null;
        }
    }

    @Override
    public Context getViewContext() {
        return mContext;
    }

    @Override
    public void showLoading() {
        if (mLoadingDialog == null) {
            mLoadingDialog = new ProgressDialog(mContext);
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
    public void toActivity(Class<? extends BaseActivity> clz, Bundle bundle, boolean finishSelf) {
        Intent i = new Intent(mContext, clz);
        if (bundle != null) {
            i.putExtras(bundle);
        }
        startActivity(i);
        if (finishSelf) {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void toActivity(Intent intent, boolean finishSelf) {
        startActivity(intent);
        if (finishSelf) {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void addFragment(Fragment newFrag) {
        final FragmentTransaction transaction = getChildFragmentManager().beginTransaction();  // 要在fragment中打开framgnet，需要使用child。。。
//        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if (newFrag.isAdded()) {
            transaction.show(newFrag).commit();
        } else {
            transaction.add(getContainerId(), newFrag, newFrag.getClass().getName())
                    .addToBackStack(newFrag.getClass().getName())
                    .commit();
        }
    }

    /**
     * 需要在当前fragment打开新的fragment，需要实现此方法
     *
     * @return 获取当前fragment的layout root id
     */
    public int getContainerId() {
        return 0;
    }

    @Override
    public void gotoLogin(int pageName, int requestCode) {
        gotoLogin(pageName, requestCode, null);
    }

    @Override
    public void gotoLogin(int pageName, int requestCode, Bundle bundle) {
        Intent i = new Intent(mContext, LoginActivity.class);
        if(bundle != null) {
            i.putExtras(bundle);
        }
        i.putExtra(Consts.LOGIN_FROM_PAGE_NAME, pageName);
        startActivityForResult(i, requestCode);
    }


    @Override
    public boolean onBackPressed() {
        return false;   // 被activity添加的那一层fragment不需要处理返回事件
    }

    protected void setPresenter(P p) {
        this.mPresenter = p;
    }

    protected abstract View getContentView();

}
