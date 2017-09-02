package com.saku.lmlib.fragment.backpress;

/**
 * fragment 需要实现此接口可以监听返回按钮
 */
public interface BackPressListener {
    /**
     * 处理返回按钮
     * @return true 表示处理， false不处理
     */
    boolean onBackPressed();
}
