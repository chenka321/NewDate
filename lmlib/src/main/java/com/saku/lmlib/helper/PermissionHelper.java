package com.saku.lmlib.helper;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import com.saku.lmlib.R;

public class PermissionHelper {
    public static final int LOCATION_REQUEST_CODE = 101;
    public static final int READ_STORAGE_REQUEST_CODE = 102;

    public PermissionHelper() {

    }

    /**
     * 检查是否有位置权限，没有看是否要弹自定义框请求权限，还是直接请求权限
     */
    public boolean checkLocationPermission(final Activity context) {
        Log.d("lm", "checkLocationPermission: ");
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("lm", "checkLocationPermission: not granted");
            // 没有授权位置权限， 是否需要弹框提示用户同意
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.location_permission_title)
                        .setMessage(R.string.loc_permission_msg)
                        .setPositiveButton(R.string.permit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(context,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        LOCATION_REQUEST_CODE);
                            }
                        })
                        .setNegativeButton(R.string.not_permit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.finish();
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission. ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
            return false;
        }
        return true;
    }

    /**
     * @return true 有读取sd卡权限，没有弹框授予
     */
    public boolean requestStoragePermission(final Activity context) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Log.d("lm", "checkLocationPermission: not granted");
            // 没有读取权限， 是否需要弹框提示用户同意
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                new AlertDialog.Builder(context)
                        .setTitle(R.string.read_storage_permission_title)
                        .setMessage(R.string.read_storage_permission_msg)
                        .setPositiveButton(R.string.permit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(context,
                                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                        READ_STORAGE_REQUEST_CODE);
                            }
                        })
                        .setNegativeButton(R.string.not_permit, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                context.finish();
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission. READ_EXTERNAL_STORAGE},
                        READ_STORAGE_REQUEST_CODE);
            }
            return false;
        }
        return true;
    }


    /**
     *  请求权限的回调
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
     */
}
