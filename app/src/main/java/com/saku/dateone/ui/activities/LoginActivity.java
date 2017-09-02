package com.saku.dateone.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.saku.dateone.R;
import com.saku.dateone.ui.adapters.TextWatcherAdapter;
import com.saku.dateone.ui.contracts.LoginContract;
import com.saku.dateone.ui.presenters.LoginPresenter;
import com.saku.lmlib.utils.UIUtils;

import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.V, View.OnClickListener {

    private static final long TOTAL_TIME = 10;
    private AppCompatEditText phoneEt;
    private ImageView phoneCloseIv;
    private Button verifyBtn;
    private AppCompatEditText verifyCodeEt;
    private Button loginBtn;
    private Disposable mTimerDisposable;

    @Override
    protected View getContentView() {
        final View view = LayoutInflater.from(this).inflate(R.layout.s_login_activity, mRoot, false);
        return view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    @Override
    public void onInternetFail(String msg) {

    }

    @Override
    protected LoginPresenter getPresenter() {
        return new LoginPresenter(this);
    }

    private void initView() {
        phoneEt = (AppCompatEditText) findViewById(R.id.input_phonenumber_et);
        phoneCloseIv = (ImageView) findViewById(R.id.input_phone_close_iv);
        verifyCodeEt = (AppCompatEditText) findViewById(R.id.input_verify_code_et);
        verifyBtn = (Button) findViewById(R.id.input_verify_btn);
        loginBtn = (Button) findViewById(R.id.login_btn);

        verifyBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);

        loginBtn.setEnabled(!(UIUtils.isEditTextEmpty(phoneEt) && UIUtils.isEditTextEmpty(verifyCodeEt)));
        verifyBtn.setEnabled(!UIUtils.isEditTextEmpty(verifyCodeEt));

        phoneEt.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("lm", "phoneEt afterTextChanged: s " + s.toString());
                if (!UIUtils.isEditTextEmpty(verifyCodeEt) && !TextUtils.isEmpty(s)) {
                    loginBtn.setEnabled(true);
                }
//                if (!UIUtils.isEditTextEmpty(verifyCodeEt) && !TextUtils.isEmpty(s)) {
//                    loginBtn.setEnabled(true);
//                }

                if (!TextUtils.isEmpty(s.toString())) {
                    verifyBtn.setEnabled(true);
                }
            }
        });

        verifyCodeEt.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {
                Log.d("lm", "verifyCodeEt afterTextChanged: s " + s.toString());

//                if (!TextUtils.isEmpty(s.toString())) {
//                    verifyBtn.setEnabled(true);
//                }
                if (!UIUtils.isEditTextEmpty(verifyCodeEt) && !TextUtils.isEmpty(s)) {
                    loginBtn.setEnabled(true);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.input_verify_btn:
                mPresenter.onGetVeriCodeClicked(phoneEt.getText().toString());
                countDown();
                break;
            case R.id.login_btn:
                if (TextUtils.isEmpty(phoneEt.getText().toString())) {
                    Toast.makeText(this, "请填入电话号码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(verifyCodeEt.getText().toString())) {
                    Toast.makeText(this, "请填入验证码！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mPresenter.onLoginBtnClicked(phoneEt.getText().toString(), verifyCodeEt.getText().toString());
                break;
        }
    }

    /**
     * 倒计时
     */
    private void countDown() {
        mTimerDisposable = Flowable.interval(0, 1, TimeUnit.SECONDS)
                .take(TOTAL_TIME + 1)
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(@NonNull Long aLong) throws Exception {
                        Log.d("lm", "map --- apply: along = " + aLong);
                        return TOTAL_TIME - aLong;
                    }
                }).subscribeOn(Schedulers.computation())
                .doOnSubscribe(new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        verifyBtn.setEnabled(false);
                        Log.d("lm", "doOnSubscribe --- accept: ");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<Long>() {

                    @Override
                    public void onNext(Long aLong) {
                        verifyBtn.setText(aLong + " s");
                        Log.d("lm", "onNext --- accept: " + aLong);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d("lm", "onErr --- accept: " + t.getMessage());

                        verifyBtn.setEnabled(true);
                        verifyBtn.setText(LoginActivity.this.getString(R.string.aquire_again));
                    }

                    @Override
                    public void onComplete() {
                        Log.d("lm", "onComplete: ");
                        verifyBtn.setEnabled(true);
                        verifyBtn.setText(LoginActivity.this.getString(R.string.aquire_again));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimerDisposable != null && !mTimerDisposable.isDisposed()) {
            mTimerDisposable.dispose();
        }
    }
}
