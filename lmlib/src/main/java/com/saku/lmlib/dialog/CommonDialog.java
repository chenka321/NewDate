package com.saku.lmlib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.saku.lmlib.R;
import com.saku.lmlib.utils.ImageUtils;

/**
 * Created by liumin on 2017/9/2.
 */

public class CommonDialog extends Dialog implements View.OnClickListener{
    private TextView contentTv;
    private TextView titleTv;
    private TextView submitTv;
    private TextView cancelTv;

    private String content;
    private String positiveName;
    private String negativeName;
    private String title;
    public int iconResId;

    private Context context;
    private boolean cancelTouchout;

    private OnCloseListener confirmListener;
    private OnCloseListener cancelListener;
    private ImageView iconIv;


    private CommonDialog(Builder builder) {
        super(builder.context);
        setBuilder(builder);
    }

    private CommonDialog(Builder builder, int resStyle) {
        super(builder.context, resStyle);
        setBuilder(builder);
    }

    private void setBuilder(Builder builder) {
        this.title = builder.title;
        this.iconResId = builder.iconResId;
        this.content = builder.content;
        this.positiveName = builder.positiveName;
        this.negativeName = builder.negativeName;
        this.context = builder.context;
        this.cancelTouchout = builder.cancelTouchout;

        this.confirmListener = builder.confirmListener;
        this.cancelListener = builder.cancelListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_common);

        setCanceledOnTouchOutside(cancelTouchout);

        initView();
    }

    private void initView() {
        iconIv = (ImageView) findViewById(R.id.icon_iv);
        contentTv = (TextView) findViewById(R.id.icon);
        titleTv = (TextView) findViewById(R.id.title);
        submitTv = (TextView) findViewById(R.id.submit);
        submitTv.setOnClickListener(this);
        cancelTv = (TextView) findViewById(R.id.cancel);
        cancelTv.setOnClickListener(this);

        contentTv.setText(content);
        if (!TextUtils.isEmpty(positiveName)) {
            submitTv.setText(positiveName);
        }

        if (!TextUtils.isEmpty(negativeName)) {
            cancelTv.setText(negativeName);
        }

        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }

        if (iconResId == 0) {
            iconIv.setVisibility(View.GONE);
        } else {
            iconIv.setVisibility(View.VISIBLE);
            iconIv.setImageResource(iconResId);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel) {
            if(cancelListener != null){
                cancelListener.onClick(this);
            }
            this.dismiss();
        } else if (v.getId() == R.id.submit) {
            if(confirmListener != null){
                confirmListener.onClick(this);
            }
        }
    }


    public interface OnCloseListener {
        void onClick(Dialog dialog);
    }

    public static final class Builder {

        Context context;
        int resStyle = -1;
        boolean cancelTouchout;

        String content;
        String positiveName;
        String negativeName;
        String title;
        int iconResId;

        private OnCloseListener confirmListener;
        private OnCloseListener cancelListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder style(int resStyle) {
            this.resStyle = resStyle;
            return this;
        }

        public Builder cancelTouchout(boolean val) {
            cancelTouchout = val;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setIcon(int resId) {
            this.iconResId = resId;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }


        public Builder setPositiveButton(String name) {
            this.positiveName = name;
            return this;
        }

        public Builder setNegativeButton(String name) {
            this.negativeName = name;
            return this;
        }

        public Builder setConfirmListener(OnCloseListener confirmListener) {
            this.confirmListener = confirmListener;
            return this;
        }

        public Builder setCancelListener(OnCloseListener cancelListener) {
            this.cancelListener = cancelListener;
            return this;
        }


        public CommonDialog build() {
            if (resStyle != -1) {
                return new CommonDialog(this, resStyle);
            } else {
                return new CommonDialog(this);
            }
        }
    }
}