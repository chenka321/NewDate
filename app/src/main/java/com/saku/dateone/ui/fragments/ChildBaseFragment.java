package com.saku.dateone.ui.fragments;

import com.saku.dateone.ui.presenters.BasePresenter;

/**
 * User: liumin
 * Date: 2017-9-1
 * Time: 20:20
 * Description: 在fragment中打开的framgent
*/
public abstract class ChildBaseFragment<P extends BasePresenter> extends BaseFragment<P> {

    @Override
    public boolean onBackPressed() {
        getFragmentManager().popBackStack();  // 得到管理当前fragment的fragmentmanager，如果要在该fragment中操作下层的fragment，则需要用getChildFragmentManager
        return true;
    }
}
