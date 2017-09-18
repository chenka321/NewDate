package com.saku.dateone.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.saku.dateone.R;
import com.saku.dateone.bean.Article;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.lmlib.utils.LLog;

/**
 * 发现
 */
public class DiscoverDetailFragment extends ChildBaseFragment<BasePresenter>{

    private WebView discoverWv;

    public static DiscoverDetailFragment newInstance(Bundle bundle) {
        DiscoverDetailFragment f = new DiscoverDetailFragment();
        f.setArguments(bundle);
        return f;
    }

    @Override
    protected View getContentView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.s_discover_detail, mFragmentRoot, false);
        this.discoverWv = (WebView) view.findViewById(R.id.discover_wv);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LLog.d("lm", "discoverDetailFragment onViewCreated: ");

        showTitle(true);
        mTitleLayout.setTitleContent("发现详情");

        loadPageUrl();
    }

    @Override
    public void onResume() {
        super.onResume();
        LLog.d("lm", "discoverDetailFragment onResume: ");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        LLog.d("lm", "DiscoversFragment setUserVisibleHint: " + isVisibleToUser);
    }
    @Override
    public void onDestroy() {
        if (discoverWv != null) {
            ((ViewGroup) discoverWv.getParent()).removeView(discoverWv);
            discoverWv.destroy();
            discoverWv = null;
        }
        super.onDestroy();
    }

    @Override
    public boolean onBackPressed() {
        getFragmentManager().popBackStack();  // 得到管理当前fragment的fragmentmanager，如果要在该fragment中操作下层的fragment，则需要用getChildFragmentManager
        return true;
    }


    private void loadPageUrl() {
        if (getArguments() == null ) {
            return;
        }
        final Article article = (Article) getArguments().get(DiscoversFragment.ARTICLE);
        if (article == null || TextUtils.isEmpty(article.contentUrl)) {
            return;
        }

        discoverWv.loadUrl(article.contentUrl);

        WebSettings webSettings = discoverWv.getSettings();
        webSettings.setAllowFileAccess(false); //设置启用或禁止访问文件数据
        webSettings.setBuiltInZoomControls(true);//设置是否支持缩放
        webSettings.setDefaultFontSize(5); //设置默认的字体大小
        webSettings.setJavaScriptEnabled(true); //设置是否支持JavaScript
        webSettings.setSupportZoom(true); //设置是否支持变焦

        discoverWv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);// 使用当前WebView处理跳转
                return false;//true表示此事件在此处被处理，不需要再广播
            }
        });
    }

}
