package com.saku.dateone.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.lmlib.utils.ImageUtils;

/**
 * Created by liumin on 2017/9/13.
 * 查看大图
 */

public class BigPicActivity extends BaseActivity {
    public static final String PIC_PATH = "pic_path";
    public static final String PIC_PATH_DELETE = "pic_path_delete";
    private static final int DELETE_RESULT = 10;
    public static final int FROM_OPPO_USER_INFO = 1;  // 对方子女信息页
    public static final int FROM_COMPLETE_INFO = 2;  // 补充信息页
    public static final String FROM_PAGE = "from_page";  // 从哪个页面查看大图
    private TextView deleteTv;
    private ImageView bigPicIv;
    private String mPicPath;

    @Override
    protected View getContentView() {
        return LayoutInflater.from(this).inflate(R.layout.s_big_pic_activity, mRoot, false);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initView();
        setDeleteBtnState();

        mPicPath = getIntent().getStringExtra(PIC_PATH);
        ImageUtils.loadImageWithGlide(this, mPicPath, 0, bigPicIv);
    }

    private void setDeleteBtnState() {
        if (getIntent() != null) {
            final int from = getIntent().getIntExtra(FROM_PAGE, 0);
            if (from == FROM_OPPO_USER_INFO) {
                deleteTv.setVisibility(View.GONE);
            } else {
                deleteTv.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initView() {
        deleteTv = (TextView) findViewById(R.id.delete_tv);
        bigPicIv = (ImageView) findViewById(R.id.big_pic_iv);

        deleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra(PIC_PATH_DELETE, mPicPath);
                setResult(DELETE_RESULT, i);
                finish();
            }
        });
    }
}
