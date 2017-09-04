package com.saku.lmlib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author dongfang
 * @date 16/5/7
 */
public abstract class BaseDialogBuilder<T> {

    protected Dialog mDialog;

    /**
     * logo
     */
    protected ImageView mIcon;
    /**
     * 标题
     */
    protected TextView mTVTitle, mTVContent;
    /**
     * 确认,取消,关闭 三按钮
     */
    protected TextView mTVPositive, mTVNegative, mTVClose;


    protected Context mContext;
    protected LayoutInflater inflater;
    protected View v = null;

    public BaseDialogBuilder(Context context) {
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public T setIcon(@DrawableRes int resid) {
        if (null != mIcon) {
            mIcon.setImageResource(resid);
        }
        return (T) this;
    }

    public T setIcon(@NonNull Drawable drawable) {
        if (null != mIcon) {
            mIcon.setImageDrawable(drawable);
        }
        return (T) this;
    }

    public T setTitleTextSize(@Size(min = 1) float size) {
        if (null != mTVTitle) {
            mTVTitle.setTextSize(size);
        }
        return (T) this;
    }

    public T setTitle(@StringRes int resid) {
        if (null != mTVTitle) {
            mTVTitle.setText(resid);
        }
        return (T) this;
    }

    public T setTitle(@NonNull String title) {
        if (null != mTVTitle) {
            mTVTitle.setText(title);
        }
        return (T) this;
    }

    public T setContentTextSize(@Size(min = 1) float size) {
        if (null != mTVContent) {
            mTVContent.setTextSize(size);
        }
        return (T) this;
    }


    public T setContent(@NonNull String content) {
        if (null != mTVContent) {
            mTVContent.setText(content);
        }
        return (T) this;
    }

    public T setContent(@StringRes int resid) {
        if (null != mTVContent) {
            mTVContent.setText(resid);
        }
        return (T) this;
    }


    public T setPositive(@StringRes int resid) {
        if (null != mTVPositive) {
            mTVPositive.setText(resid);
        }
        return (T) this;
    }

    public T setPositive(@NonNull String positive) {
        if (null != mTVPositive) {
            mTVPositive.setText(positive);
        }
        return (T) this;
    }

    public T setNegative(@StringRes int resid) {
        if (null != mTVNegative) {
            mTVNegative.setText(resid);
        }
        return (T) this;
    }

    public T setNegative(@NonNull String negative) {
        if (null != mTVNegative) {
            mTVNegative.setText(negative);
        }
        return (T) this;
    }


    public abstract Dialog build();
//    {
//        mDialog = new Dialog(mContext, R.style.dialog_common);
//        if (v != null) {
//            mDialog.setContentView(v, new LinearLayout.LayoutParams(-1, -1));// 设置布局,全屏
//        }
//        if (mTVTitle != null) {
//            mTVTitle.setVisibility(TextUtils.isEmpty(mTVTitle.getText()) ? View.GONE : View.VISIBLE);
//        }
//        return mDialog;
//    }
//
//    /**
//     * {@link #subcribe(IAction, IAction, IAction)}
//     */
//    public T subcribe(IAction action0) {
//        return subcribe(action0, null, null);
//    }
//
//    /**
//     * {@link #subcribe(IAction, IAction, IAction)}
//     */
//    public T subcribe(IAction action0, IAction action1) {
//        return subcribe(action0, action1, null);
//    }
//
//    /**
//     * 注册点击事件, 默认注册到 确认,取消,和关闭按钮上
//     */
//    public T subcribe(IAction action0, IAction action1, IAction action2) {
//        setOnAtion(mViews[0], action0);
//        setOnAtion(mViews[1], action1);
//        setOnAtion(mViews[2], action2);
//        return (T) this;
//    }
//
//
//    private View[] mViews = new View[3];
//
//    protected T bind(@NonNull View v0) {
//        return bind(v0, null, null);
//    }
//
//    protected T bind(@NonNull View v0, View v1) {
//        return bind(v0, v1, null);
//    }
//
//    protected T bind(@NonNull View v0, View v1, View v2) {
//        mViews[0] = v0;
//        mViews[1] = v1;
//        mViews[2] = v2;
//        return (T) this;
//    }
//
//    protected void setOnAtion(View v, final IAction action) {
//        if (null != v && null != action) {
//            v.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    action.call(mDialog, v);
//                }
//            });
//        }
//    }
}
