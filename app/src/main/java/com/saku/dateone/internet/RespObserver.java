package com.saku.dateone.internet;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by liumin on 2017/9/12.
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
