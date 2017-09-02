package com.saku.lmlib.fragment.backpress;

/**
 * User: liumin
 * Date: 2017-9-1
 * Time: 19:38
 * Description: activity实现此接口处理返回按钮，以便在fragment中可以监听到framgnet
*/
public interface BackPressHandler {
    void addBackPressListener(BackPressListener backListener);
    void removeBackPressListener(BackPressListener backListener);
}
