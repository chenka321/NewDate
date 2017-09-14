package com.saku.dateone.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.saku.dateone.R;
import com.saku.dateone.ui.presenters.BasePresenter;
import com.saku.dateone.utils.Consts;
import com.saku.lmlib.helper.PermissionHelper;
import com.saku.lmlib.utils.PreferenceUtil;

/**
 * Created by liumin on 2017/8/15.
 */

public class SplashActivity extends BaseActivity implements LocationListener {

    private LocationManager mLocationManager;
    private PermissionHelper mPermissionHelper;
    private Location mLocation;
    private FrameLayout mFrameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mPermissionHelper = new PermissionHelper();

        showTitle(false);
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected View getContentView() {
        mFrameLayout = new FrameLayout(this);
        mFrameLayout.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mFrameLayout.setBackgroundColor(0xffbdcfed);
        TextView textView = new TextView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.tv_content_size));
        textView.setLayoutParams(params);
        textView.setText(R.string.app_name);
        mFrameLayout.addView(textView);
        return mFrameLayout;
    }

    @Override
    protected void onStart() {
        super.onStart();
        final boolean showSplash = PreferenceUtil.getBoolean(this, Consts.SHOW_SPLASH, true);
        if (mPermissionHelper.checkLocationPermission(this)) {
            catchLocation();
            if (showSplash) {
                mFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("lm", "run: location 111111 = " + mLocation);
                        PreferenceUtil.putBoolean(SplashActivity.this, Consts.SHOW_SPLASH, false);
                        goToNextScene();
                    }

                }, 2000);
            } else {
                goToNextScene();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void goToNextScene() {
        final boolean hasBasic = PreferenceUtil.getBoolean(this, Consts.HAS_BASIC_INFO, false);
        if (hasBasic) {
            startActivity(new Intent(SplashActivity.this, MainTabsActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, EntryInfoActivity.class));
        }

        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PermissionHelper.LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    catchLocation();
                    mFrameLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("lm", "run: location 222222 = " + mLocation);
                            goToNextScene();
                        }
                    }, 2000);
                } else {
                    finish();
                }
            }

        }
    }

    private void catchLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mLocationManager != null) {
                try {
                    final boolean gps_p = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                    final boolean netowrk_p = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                    if (!gps_p && !netowrk_p) {
                        Log.d("lm", "catchLocation: no provider");
                        return;
                    }
                    if (gps_p) {
                        Log.d("lm", "catchLocation: gps");
                        mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (mLocation != null) {
                            Log.d("lm", "catchLocation: lat = " + mLocation.getLatitude());
                        } else {
                            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
                        }
                    } else {
                        Log.d("lm", "catchLocation: network");
                        mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (mLocation != null) {
                            Log.d("lm", "catchLocation: lat = " + mLocation.getLatitude());
                        } else {
                            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
                        }
                    }

                } catch (IllegalArgumentException e) {
                    Log.d("lm", "catchLocation: exception = " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            mLocation = location;
            Log.d("lm", "onLocationChanged: location.lat = " + location.getLatitude()
                    + ",  location.long = " + location.getLongitude());
            mLocationManager.removeUpdates(this);
        } else {
            Log.d("lm", "onLocationChanged: location null");
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("lm", "onStatusChanged: provider = " + provider + " , status = " + status);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("lm", "onProviderEnabled: provider = " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("lm", "onProviderDisabled: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationManager.removeUpdates(this);
    }

}
