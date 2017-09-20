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

import com.lljjcoder.citylist.Toast.ToastUtils;
import com.mob.MobSDK;
import com.saku.dateone.R;
import com.saku.dateone.ui.adapters.TextWatcherAdapter;
import com.saku.dateone.ui.contracts.LoginContract;
import com.saku.dateone.ui.presenters.LoginPresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.utils.UIUtils;

import org.reactivestreams.Subscription;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.V, View.OnClickListener {
    // 默认使用中国区号
    private static final String DEFAULT_COUNTRY_ID = "42";
    private static final long TOTAL_TIME = 60;
    public static final int LOGIN_OK = 11;
    private AppCompatEditText phoneEt;
    private ImageView phoneCloseIv;
    private Button verifyBtn;
    private AppCompatEditText verifyCodeEt;
    private Button loginBtn;
    private Disposable mTimerDisposable;
    private EventHandler eventHandler;
    private String currCountryCode;

    @Override
    protected View getContentView() {
        final View view = LayoutInflater.from(this).inflate(R.layout.s_login_activity, mRoot, false);
        return view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showTitle(true);
        mTitleLayout.setTitleContent("登录");
        initView();

        MobSDK.init(this, "211ebf1dea086", "a1bc1f4422b293ed980c35a06aa2bd98");   // 相亲啦 app的mob 短信sdk appkey和secret

        createMobSdkHandler();
    }

    private void createMobSdkHandler() {
        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
//        SMSSDK.setAskPermisionOnReadContact(true);

        // 创建EventHandler对象
        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    try {
                        Throwable throwable = (Throwable) data;
                        final String msg = throwable.getMessage();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (final Throwable e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {  //获取验证码
                        if (result == SMSSDK.RESULT_COMPLETE) {  // 成功
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {  // 验证码校验成功
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (mTimerDisposable != null && !mTimerDisposable.isDisposed()) {
                                        mTimerDisposable.dispose();
                                    }
                                    mPresenter.onLoginBtnClicked(phoneEt.getText().toString(), verifyCodeEt.getText().toString());
                                }
                            });
                        }
                    }
                }
            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
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
                if (!UIUtils.isEditTextEmpty(verifyCodeEt) && !TextUtils.isEmpty(s)) {
                    loginBtn.setEnabled(true);
                }

                if (!TextUtils.isEmpty(s.toString()) && (mTimerDisposable == null || mTimerDisposable.isDisposed())) {
                    verifyBtn.setEnabled(true);
                }
            }
        });

        verifyCodeEt.addTextChangedListener(new TextWatcherAdapter() {
            @Override
            public void afterTextChanged(Editable s) {

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
                final String phoneText = phoneEt.getText().toString().trim();
                if (phoneText != null && phoneText.length() < 11) {
                    ToastUtils.showShortToast(this, "手机号码不正确");
                    return;
                }
                final String[] country = SMSSDK.getCountry(DEFAULT_COUNTRY_ID);
                if (country != null) {
                    currCountryCode = "+" + country[1];
                    SMSSDK.getVerificationCode(currCountryCode, phoneText);
                    countDown();
                }
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
                final String phoneText1 = phoneEt.getText().toString().trim();
                if (phoneText1 != null && phoneText1.length() < 11) {
                    ToastUtils.showShortToast(this, "手机号码不正确");
                    return;
                }
                if (currCountryCode == null) {
                    final String[] country1 = SMSSDK.getCountry(DEFAULT_COUNTRY_ID);
                    if (country1 != null) {
                        currCountryCode = country1[1];
                    }

                }
                if (currCountryCode != null && currCountryCode.startsWith("+")) {
                    currCountryCode = currCountryCode.substring(1);
                }
                SMSSDK.submitVerificationCode(currCountryCode, phoneEt.getText().toString().trim(),
                        verifyCodeEt.getText().toString().trim());
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
                    }

                    @Override
                    public void onError(Throwable t) {
                        verifyBtn.setEnabled(true);
                        verifyBtn.setText(LoginActivity.this.getString(R.string.aquire_again));
                    }

                    @Override
                    public void onComplete() {
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
            mTimerDisposable = null;
        }

        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public void goToNext() {
        final int fromPage = getIntent().getIntExtra(Consts.LOGIN_FROM_PAGE_NAME, 0);

        final boolean firstLogin = UserInfoManager.getInstance().isFirstLogin();
        if (firstLogin) {
            toActivity(SimpleInfoActivity.class, null, true);
        } else {
            switch (fromPage) {
                case 21: // PageManager.RECOMMEND_LIST
                    Bundle bundle = new Bundle();
                    bundle.putLong(OppoInfoActivity.USER_ID, getIntent().getLongExtra(OppoInfoActivity.USER_ID, 0));
                    toActivity(OppoInfoActivity.class, bundle, true);
                    break;
                case 24:  // PageManager.CHAT_LIST
                case 25:  // PageManager.DISCOVER_LIST
                case 26:  // PageManager.MINE
                    setResult(LOGIN_OK);  // 聊天列表，发现，我的
                    finish();
                    break;
            }
        }
    }
}
