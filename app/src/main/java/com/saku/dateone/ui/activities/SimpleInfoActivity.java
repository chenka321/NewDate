package com.saku.dateone.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.saku.dateone.R;
import com.saku.dateone.bean.Dict;
import com.saku.dateone.ui.contracts.CompleteInfoContract;
import com.saku.dateone.ui.presenters.CompleteInfoPresenter;
import com.saku.dateone.utils.Consts;
import com.saku.dateone.utils.PageManager;
import com.saku.dateone.utils.TypeManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.dialog.BirthDayPickerDialog;
import com.saku.lmlib.dialog.DialogHelper;
import com.saku.lmlib.dialog.OneColumnPickerDialog;
import com.saku.lmlib.utils.DateUtils;
import com.saku.lmlib.utils.LLog;
import com.saku.lmlib.utils.UIUtils;

/**
 * Created by liumin on 2017/8/15.
 */

/**
 * User: liumin
 * Date: 2017-9-4
 * Time: 16:43
 * Description: 填写基础信息页， 子女姓名年月日
 */
public class SimpleInfoActivity extends BaseActivity<CompleteInfoPresenter> implements View.OnClickListener,
        CompleteInfoContract.V {

    private Button matchBtn;
    private DialogHelper dialogHelper;
    private EditText nameEt;
    private TextView educationTv;
    private TextView birthdayTv;
    private Dict mEducationDict;  // 学历
    private long mSelectBirthday;
    private TextView putMoreInfoTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        showTitle(false);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  // 隐藏软键盘
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(this).inflate(R.layout.s_input_user_info_activity, mRoot, false);
    }

    @Override
    protected CompleteInfoPresenter getPresenter() {
        return new CompleteInfoPresenter(this);
    }

    private void initView() {
        this.nameEt = (EditText) findViewById(R.id.input_name_et);
        this.educationTv = (TextView) findViewById(R.id.input_education_tv);
        this.birthdayTv = (TextView) findViewById(R.id.input_birthday_tv);
        this.matchBtn = (Button) findViewById(R.id.match_btn);
        this.putMoreInfoTv = (TextView) findViewById(R.id.go_put_more_info_tv);

        matchBtn.setOnClickListener(this);
        educationTv.setOnClickListener(this);
        birthdayTv.setOnClickListener(this);
        putMoreInfoTv.setOnClickListener(this);

        putMoreInfoTv.getPaint().setUnderlineText(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void goNextOnCompleteInfo() {

    }

    private boolean checkOnSubmit() {
        boolean fillAll = true;
        // validate
        String name = nameEt.getText().toString().trim();
        if (UIUtils.showToast(this, name, "请填入姓名")) return false;

        String education = educationTv.getText().toString().trim();
        if (UIUtils.showToast(this, education, "请选择学历")) return false;

        String birthday = birthdayTv.getText().toString().trim();
        if (UIUtils.showToast(this, birthday, "请选择出生日期")) return false;

        UserInfoManager.getInstance().getMyPendingInfo().name =name;
        UserInfoManager.getInstance().getMyPendingInfo().education = mEducationDict.id;
        UserInfoManager.getInstance().getMyPendingInfo().birthday =birthday;
        return true;
    }

    @Override
    public void onClick(View v) {
        UIUtils.hideSoftKeyboard(this, v);
        if (dialogHelper == null) {
            dialogHelper = new DialogHelper();
        }
        switch (v.getId()) {
            case R.id.input_education_tv:
                dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().education, mEducationDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mEducationDict = type;
                        educationTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.input_birthday_tv:
                dialogHelper.showBirthdayPicker(this, mSelectBirthday, "", new BirthDayPickerDialog.DateCallback() {
                    @Override
                    public void callBackDate(long time) {
                        mSelectBirthday = time;
                        birthdayTv.setText(DateUtils.long2Date(time));
                    }
                });

                break;
            case R.id.match_btn:

                if (checkOnSubmit()) {
                    LLog.d("fill all");

                    mPresenter.onMatchSimpleClicked();
                }

                break;
            case R.id.go_put_more_info_tv:
                if (checkOnSubmit()) {
                    Bundle b = new Bundle();
                    b.putInt(Consts.COMPLETE_FROM_PAGE_NAME, PageManager.COMPLETE_INFO_MY_SIMPLE_INFO);
                    toActivity(CompleteInfoActivity.class, b, true);
                }
                break;
        }
    }

}
