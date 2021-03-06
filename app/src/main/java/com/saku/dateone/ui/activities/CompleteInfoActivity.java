package com.saku.dateone.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.saku.dateone.R;
import com.saku.dateone.bean.Dict;
import com.saku.dateone.bean.UserInfo;
import com.saku.dateone.services.UploadPicService;
import com.saku.dateone.ui.contracts.CompleteInfoContract;
import com.saku.dateone.ui.list.typeholders.StringTypeHolder;
import com.saku.dateone.ui.presenters.CompleteInfoPresenter;
import com.saku.dateone.utils.TypeManager;
import com.saku.dateone.utils.UserInfoManager;
import com.saku.lmlib.dialog.DialogHelper;
import com.saku.lmlib.dialog.OneColumnPickerDialog;
import com.saku.lmlib.helper.PermissionHelper;
import com.saku.lmlib.list.adapter.StringAdapter;
import com.saku.lmlib.list.itemdecoration.SpaceDividerDecoration;
import com.saku.lmlib.list.listeners.OnRecyclerClickCallBack;
import com.saku.lmlib.utils.UIUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * User: liumin
 * Date: 2017-9-4
 * Time: 16:43
 * Description: 补充信息
 */
public class CompleteInfoActivity extends BaseActivity<CompleteInfoPresenter> implements View.OnClickListener,
        CompleteInfoContract.V {
    private static final int REQUEST_ALBUM = 2; // 选择相册照片
    private static final int SEE_BIG_PIC = 20; // 查看大图
    private EditText heightEt;
    private TextView companyTypeTv;
    private EditText occupationTv;
    private TextView incomeTv;
    private TextView estateTv;
    private EditText estateLocEt;
    private TextView carTv;
    private TextView schoolTv;
    private EditText schoolNameEt;
    //    private TextView educationTv;
    private GridView hobbyGv;
    private EditText moreInfoEt;
    private RecyclerView uploadPicRv;
    private Button matchBtn;
    private Button uploadPicBtn;
    private DialogHelper dialogHelper;
    private Dict mCurrComDict;
    private Dict mCurrIncomeDict;
    private Dict mCurrHouseDict;
    private Dict mCurrCarDict;
    private Dict mSchoolDict;
    private StringAdapter picListAdapter;
    private File mImageFile;
    private ArrayList<String> mTotalPics;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        showTitle(true);
        setTitle("补充信息");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  // 隐藏软键盘
    }

    @Override
    protected View getContentView() {
        return LayoutInflater.from(this).inflate(R.layout.s_input_user_info_more_activity, mRoot, false);
    }

    @Override
    protected CompleteInfoPresenter getPresenter() {
        return new CompleteInfoPresenter(this);
    }

    private void initView() {
        heightEt = (EditText) findViewById(R.id.input_height_et);
        companyTypeTv = (TextView) findViewById(R.id.input_company_tv);
        occupationTv = (EditText) findViewById(R.id.input_position_et);
        incomeTv = (TextView) findViewById(R.id.input_income_tv);
        estateTv = (TextView) findViewById(R.id.input_estate_tv);
        carTv = (TextView) findViewById(R.id.input_car_tv);
        schoolTv = (TextView) findViewById(R.id.input_school_tv);
        schoolNameEt = (EditText) findViewById(R.id.input_school_name_et);
        hobbyGv = (GridView) findViewById(R.id.hobby_gv);
        moreInfoEt = (EditText) findViewById(R.id.more_info_et);
        uploadPicRv = (RecyclerView) findViewById(R.id.upload_pic_rv);
        uploadPicBtn = (Button) findViewById(R.id.upload_pic_btn);
        matchBtn = (Button) findViewById(R.id.match_btn);

        uploadPicRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        StringTypeHolder typeHolder = new StringTypeHolder(this, getPicItemClickListener());
        mTotalPics = new ArrayList<>();
        picListAdapter = new StringAdapter(mTotalPics, typeHolder);
        uploadPicRv.setAdapter(picListAdapter);
        uploadPicRv.addItemDecoration(new SpaceDividerDecoration(UIUtils.convertDpToPx(5, this)));

        companyTypeTv.setOnClickListener(this);
        estateTv.setOnClickListener(this);
        carTv.setOnClickListener(this);
        schoolTv.setOnClickListener(this);
        incomeTv.setOnClickListener(this);
//        educationTv.setOnClickListener(this);
        matchBtn.setOnClickListener(this);
        uploadPicBtn.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private boolean checkOnSubmit() {
        String et = heightEt.getText().toString().trim();
        if (TextUtils.isEmpty(et)) {
            Toast.makeText(this, "请填入身高, 单位（厘米）", Toast.LENGTH_SHORT).show();
            return false;
        }

        String tv = occupationTv.getText().toString().trim();
        if (TextUtils.isEmpty(tv)) {
            Toast.makeText(this, "请填入职位", Toast.LENGTH_SHORT).show();
            return false;
        }

        String income = incomeTv.getText().toString().trim();
        if (TextUtils.isEmpty(income)) {
            Toast.makeText(this, "请选择收入", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(companyTypeTv.getText())) {
            Toast.makeText(this, "请填入选择单位性质", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(estateTv.getText())) {
            Toast.makeText(this, "请填入选择房产", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(carTv.getText())) {
            Toast.makeText(this, "请填入选择车辆情况", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(schoolTv.getText())) {
            Toast.makeText(this, "请填入选择学校情况", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(incomeTv.getText())) {
            Toast.makeText(this, "请填入选择收入", Toast.LENGTH_SHORT).show();
            return false;
        }

        final UserInfo pendingInfo = UserInfoManager.getInstance().getMyPendingInfo();
        try {
            pendingInfo.height = Integer.valueOf(et);
        } catch (NumberFormatException e) {
            pendingInfo.height = 0;
        }
        pendingInfo.position = tv;
        pendingInfo.house = mCurrHouseDict.id;
        pendingInfo.car = mCurrCarDict.id;
        pendingInfo.schoolType = mSchoolDict.id;
        pendingInfo.income = mCurrIncomeDict.id;
        pendingInfo.company = mCurrComDict.id;
        pendingInfo.school = schoolTv.getText().toString();
        pendingInfo.moreIntroduce = moreInfoEt.getText().toString();
        if (pendingInfo.moreIntroduce  == null) {
            pendingInfo.moreIntroduce = "";
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        UIUtils.hideSoftKeyboard(this, v);
        if (dialogHelper == null) {
            dialogHelper = new DialogHelper();
        }
        switch (v.getId()) {
            case R.id.input_company_tv:
                showCompanyPicker();
                break;
            case R.id.input_estate_tv:
                showHousePicker();
                break;
            case R.id.input_car_tv:
                showCarPicker();
                break;
            case R.id.input_school_tv:
                showSchoolPicker();
                break;
            case R.id.input_income_tv:
                showIncomePicker();
                break;
            case R.id.upload_pic_btn:
                selectAlbum();
                break;
            case R.id.match_btn:
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);  // 隐藏软键盘
                if (checkOnSubmit()) {
                    mPresenter.onMatchCompleteClicked();
                }

                break;
        }
    }

    private void showIncomePicker() {
        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().income,
                mCurrIncomeDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mCurrIncomeDict = type;
                        incomeTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
    }

    private void showSchoolPicker() {
        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().schoolType,
                mCurrCarDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mSchoolDict = type;
                        schoolTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
    }

    private void showCarPicker() {
        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().car,
                mCurrCarDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mCurrCarDict = type;
                        carTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
    }

    private void showHousePicker() {
        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().house,
                mCurrHouseDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mCurrHouseDict = type;
                        estateTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
    }

    private void showCompanyPicker() {
        dialogHelper.showSingleListDialog(this, TypeManager.getInstance().getTypeConfig().companyType,
                mCurrComDict, new OneColumnPickerDialog.SelectListener<Dict>() {
                    @Override
                    public void onSelect(OneColumnPickerDialog dialog, Dict type) {
                        mCurrComDict = type;
                        companyTypeTv.setText(type.textShowing);
                        dialog.dismiss();
                    }
                });
    }

    private void selectAlbum() {
        PermissionHelper permissionH = new PermissionHelper();
        if (!permissionH.requestStoragePermission(this)) {
            return;
        }
        if (mTotalPics == null) {
            mTotalPics = new ArrayList<>();
        }
        MultiImageSelector.create()
                .showCamera(false) // show camera or not. true by default
                .count(8 - mTotalPics.size()) // max select image size, 9 by default. used width #.multi()
                .multi() // multi mode, default mode;   .single() // single mode
                .origin(null) // original select data set, used width #.multi()
                .start(this, REQUEST_ALBUM);
    }

    @Override
    public void startPicActivity(String picPath) {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        Intent i = new Intent(this, BigPicActivity.class);
        i.putExtra(BigPicActivity.PIC_PATH, picPath);
        i.putExtra(BigPicActivity.FROM_PAGE, BigPicActivity.FROM_COMPLETE_INFO);
        this.startActivityForResult(i, SEE_BIG_PIC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ALBUM && data != null) {

            ArrayList<String> newList = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            mTotalPics.addAll(newList);
//            mPresenter.setPicList(mTotalPics);

            openUploadService(mTotalPics);
            picListAdapter.notifyDataSetChanged();
        } else if (requestCode == SEE_BIG_PIC) {
            deletePicture(data);
        }
    }

    private void deletePicture(Intent data) {
        final String picDelete = data.getStringExtra(BigPicActivity.PIC_PATH_DELETE);
        if (TextUtils.isEmpty(picDelete)) {
            return;
        }

        for (Iterator<String> iterator = mTotalPics.iterator(); iterator.hasNext();) {
            String pic = iterator.next();
            if (picDelete.equals(pic)) {
                iterator.remove();
            }
        }
        picListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionHelper.READ_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectAlbum();
                } else {
                    finish();
                }
            }
        }
    }

    @Override
    public void openUploadService(List<String> originPics) {
        if (originPics == null || originPics.size() == 0) {
            return;
        }
        Intent uploadI = new Intent(this, UploadPicService.class);
        uploadI.putStringArrayListExtra(UploadPicService.UPLOAD_PICS, (ArrayList<String>) originPics);
        startService(uploadI);
    }

    public OnRecyclerClickCallBack getPicItemClickListener() {
        return new OnRecyclerClickCallBack() {
            @Override
            public void onClick(int position, View view) {
                if (mTotalPics != null && mTotalPics.size() > position) {
                    startPicActivity(mTotalPics.get(position));
                }
            }
        };
    }
}
