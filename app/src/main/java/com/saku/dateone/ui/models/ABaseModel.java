package com.saku.dateone.ui.models;

import com.saku.dateone.internet.ApiManager;
import com.saku.dateone.internet.ApiResponse;
import com.saku.dateone.internet.ApiService;
import com.saku.dateone.internet.RespObserver;
import com.saku.dateone.ui.presenters.BasePresenter;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public abstract class ABaseModel<P extends BasePresenter> implements BaseModel {
    protected P mPresenter;
    protected ApiService mApi;
    protected CompositeDisposable mComDisposable;

    public ABaseModel(P p) {
        mPresenter = p;
        mApi = ApiManager.getAllApis().getDateApis();
    }

    protected void add(Disposable disposable) {
        if (mComDisposable == null || mComDisposable.isDisposed()) {
            mComDisposable = new CompositeDisposable();
        }
        mComDisposable.add(disposable);
    }

    protected <T, D>  void addToComposition(Observable<T> observable, RespObserver<T,D> observer) {
        if (mComDisposable == null || mComDisposable.isDisposed()) {
            mComDisposable = new CompositeDisposable();
        }
        mComDisposable.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    protected void remove(Disposable d) {
        if (mComDisposable != null) {
            mComDisposable.remove(d);
        }
    }

    @Override
    public void destroy() {
        mPresenter = null;
        clearDisposable();
        mApi = null;
    }

    private void clearDisposable() {
        if (mComDisposable != null) {
            mComDisposable.clear();
            mComDisposable = null;
        }
    }

    private <T> ObservableTransformer<T, T> defaultSchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 订阅网络请求返回的被观察者，传入观察者，对接这一对关系，并且完成线程切换，返回观察者
     * @param observable 被观察者
     * @param observer 观察者
     * @param <T> 网络请求返回的完整数据
     * @param <D> 网络请求返回的数据中的data类型
     * @return 观察者
     */
    protected  <T, D> RespObserver<T, D> subscribeWith(Observable<T> observable, RespObserver<T,D> observer) {
        return observable.compose(this.<T>defaultSchedulers())
                .subscribeWith(observer);
    }

}
