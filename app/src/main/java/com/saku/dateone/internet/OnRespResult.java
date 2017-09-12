package com.saku.dateone.internet;

/**
 * Created by liumin on 2017/9/13.
 */

public interface OnRespResult<T> {
    void onSucess(T data);
    void onFail(int code, String msg);
}
