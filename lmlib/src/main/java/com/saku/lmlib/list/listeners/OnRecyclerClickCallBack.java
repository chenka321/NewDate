package com.saku.lmlib.list.listeners;

import android.view.View;

/**
 * User: liumin
 * Date: 2017-7-15
 * Time: 18:42
 * Description: recyclerView item以及item中子view的点击事件回调
*/
public interface OnRecyclerClickCallBack {
    void onClick(int position, View view);
}
