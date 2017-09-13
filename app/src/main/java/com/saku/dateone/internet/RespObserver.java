package com.saku.dateone.internet;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by liumin on 2017/9/12.
 */

/**
 *
 /**
 * 网络请求的返回观察者
 * @param <T> 网络请求返回的完整数据
 * @param <D> 网络请求返回的数据中的data类型
 */
public abstract class RespObserver<T, D> extends DisposableObserver<T> {
    @Override
    public void onNext(@NonNull T t) {
        if (t instanceof ApiResponse) {
            final ApiResponse response = (ApiResponse) t;
            if (response.getCode() == 0) {
                onSuccess((D) response.getData());
            } else {
                onFail(response.getCode(), response.getMsg());
            }
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        onFail(-2, e.getMessage());
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess( D data);
    public abstract void onFail(int code, String msg);
}
