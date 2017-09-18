package com.saku.dateone.ui.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lljjcoder.city_20170724.CityPickerView;
import com.lljjcoder.city_20170724.bean.CityBean;
import com.lljjcoder.city_20170724.bean.DistrictBean;
import com.lljjcoder.city_20170724.bean.ProvinceBean;
import com.saku.dateone.R;
import com.saku.dateone.ui.contracts.EntryInfoContract;
import com.saku.dateone.ui.presenters.EntryInfoPresenter;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.utils.UIUtils;

/**
 * Created by liumin on 2017/8/15.
 */
/**
 * User: liumin
 * Date: 2017-9-7
 * Time: 11:16
 * Description: 首次进入匹配姓名年龄和性别
*/
public class EntryInfoActivity extends BaseActivity<EntryInfoPresenter> implements View.OnClickListener, EntryInfoContract.V {

    public static final int USER = 1;
    public static final int USER_CHILD = 2;
    private RadioButton femaleRb;
    private RadioButton maleRb;
    private Button mStartBtn;
    private LinearLayout mYourLocLl;
    private LinearLayout mYourChildLocLl;
    private TextView mPickYourLocTv;
    private TextView mPickYourChildLocTv;
    private RadioGroup mGenderRg;
    private CityPickerView mUserCityPicker;
    private CityPickerView mChildCityPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        showTitle(false);
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(this).inflate(R.layout.s_entry_info_activity, mRoot, false);
    }

    @Override
    protected EntryInfoPresenter getPresenter() {
        return new EntryInfoPresenter(this);
    }

    private void initView() {
        mGenderRg = (RadioGroup) findViewById(R.id.gender_rg);
        mYourLocLl = (LinearLayout) findViewById(R.id.your_loc_ll);
        mYourChildLocLl = (LinearLayout) findViewById(R.id.your_child_loc_ll);
        mPickYourLocTv = (TextView) mYourLocLl.findViewById(R.id.choose_your_loc_tv);
        mPickYourChildLocTv = (TextView) mYourChildLocLl.findViewById(R.id.choose_your_loc_tv);
        femaleRb = (RadioButton) findViewById(R.id.female_rb);
        maleRb = (RadioButton) findViewById(R.id.male_rb);
        mStartBtn = (Button) findViewById(R.id.start_match_btn);

        mPickYourChildLocTv.setHint(getString(R.string.choose_your_child_location));
        mStartBtn.setOnClickListener(this);
//        mGenderRg.setOnCheckedChangeListener(this);
        mPickYourLocTv.setOnClickListener(mPresenter.getChooseCityListener(USER));
        mPickYourChildLocTv.setOnClickListener(mPresenter.getChooseCityListener(USER_CHILD));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_match_btn:
                if (checkSelection()) {
                    mPresenter.onMatchBtnClicked();
                }
                break;
        }
    }

    private boolean checkSelection() {
        if (TextUtils.isEmpty(mPickYourLocTv.getText())) {
            UIUtils.showToast(this, "请选择您的位置！");
            return false;
        }
        if (TextUtils.isEmpty(mPickYourChildLocTv.getText())) {
            UIUtils.showToast(this, "请选择您子女的位置！");
            return false;
        }

        if (mGenderRg.getCheckedRadioButtonId() == 0) {
            UIUtils.showToast(this, "请选择您子女的性别！");
            return false;
        }

        if (mGenderRg.getCheckedRadioButtonId() == R.id.female_rb) {
            UserInfoManager.getInstance().getMyPendingInfo().gender = 69;  // 和后台约定id = 69是女性
        } else if (mGenderRg.getCheckedRadioButtonId() == R.id.male_rb) {
            UserInfoManager.getInstance().getMyPendingInfo().gender = 68;
        }
        UserInfoManager.getInstance().getMyPendingInfo().bornLocation = mPickYourLocTv.getText().toString();
        UserInfoManager.getInstance().getMyPendingInfo().currentLocation = mPickYourChildLocTv.getText().toString();

        return true;
    }

//    @Override
//    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
////        if (checkedId == R.id.female_rb) {
////            mPresenter.onGenderClicked(69);  // 和后台约定id = 69是女性
////        } else if (checkedId == R.id.male_rb) {
////            mPresenter.onGenderClicked(68);
////        }
//    }

    @Override
    public void showCityDialog(final int whoseCity) {
        if (whoseCity == USER) {
            if (mUserCityPicker == null) {
                mUserCityPicker = createCityPicker(whoseCity);
            }
            if (!mUserCityPicker.isShow()) {
                mUserCityPicker.show();
            }
        } else {
            if (mChildCityPicker == null) {
                mChildCityPicker = createCityPicker(whoseCity);
            }
            if (!mChildCityPicker.isShow()) {
                mChildCityPicker.show();
            }
        }

    }

    private CityPickerView createCityPicker(final int who) {
        CityPickerView cityPicker = new CityPickerView.Builder(this)
                .textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#e6343d")
                .titleTextColor("#ffffff")
                .backgroundPop(0xa0000000)
                .confirTextColor("#ffffff")
                .cancelTextColor("#ffffff")
                .province("上海")
                .city("上海")
                .district("黄浦区")
                .textColor(Color.parseColor("#000000"))
                .provinceCyclic(true)
                .cityCyclic(false)
                .districtCyclic(false)
                .visibleItemsCount(7)
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPickerView.OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                StringBuilder sb = new StringBuilder();
                if (province != null) {
                    sb.append(province.getName());
                    if (city != null) {
                        sb.append("-");
                        sb.append(city.getName());
                        if (district != null) {
                            sb.append("-");
                            sb.append(district.getName());
                        }
                    }
                }

                if (who == USER) {
                    mPickYourLocTv.setText(sb.toString());
//                    mPresenter.onCityChosen(USER, province, city, district);
                } else if (who == USER_CHILD) {
                    mPickYourChildLocTv.setText(sb.toString());
//                    mPresenter.onCityChosen(USER_CHILD, province, city, district);
                }
            }

            @Override
            public void onCancel() {

            }
        });
        return cityPicker;
    }

}
