package com.asia.paint.base.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import com.asia.paint.utils.utils.AppUtils;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;

import java.util.List;

/**
 * Created by Administrator on 2018/4/17.
 * 获取用户的地理位置
 */
public class GPSUtils {

    @SuppressLint("StaticFieldLeak")
    private static GPSUtils instance;
    private Context mContext;
    private Activity mActivity;
    private LocationManager locationManager;

    private GPSUtils(Activity activity) {
        this.mContext = activity.getApplicationContext();
        mActivity = activity;
    }

    public static GPSUtils getInstance(Activity activity) {
        try {
            if (instance == null) {
                instance = new GPSUtils(activity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 获取经纬度
     */
    public void getLngAndLat(OnLocationResultListener onLocationResultListener) {
        double latitude = 0.0;
        double longitude = 0.0;

        mOnLocationListener = onLocationResultListener;

        String locationProvider = null;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        if (locationManager == null) {
            //不为空,显示地理位置经纬度
            if (mOnLocationListener != null) {
                mOnLocationListener.onLocationResult(null);
            }
            return;
        }
        //获取所有可用的位置提供器
        List<String> providers = locationManager.getProviders(true);

        if (providers.contains(LocationManager.GPS_PROVIDER)) {
            //如果是GPS
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
            //如果是Network
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(i);
            return;
        }
        String finalLocationProvider = locationProvider;
        XXPermissions.with(mActivity)
                .permission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                .request(new OnPermission() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            Location location = locationManager.getLastKnownLocation(finalLocationProvider);
                            if (location != null) {
                                //不为空,显示地理位置经纬度
                                if (mOnLocationListener != null) {
                                    mOnLocationListener.onLocationResult(location);
                                }

                            }
                            //监视地理位置变化
                            locationManager.requestLocationUpdates(finalLocationProvider, 3000, 1, locationListener);
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        AppUtils.showMessage("不开启定位，无法获取预约服务");
                    }
                });

    }


    public LocationListener locationListener = new LocationListener() {

        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {

        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {

        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (mOnLocationListener != null) {
                mOnLocationListener.OnLocationChange(location);
            }
        }
    };

    public void removeListener() {
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
        mOnLocationListener = null;
    }

    private OnLocationResultListener mOnLocationListener;

    public interface OnLocationResultListener {
        void onLocationResult(Location location);

        void OnLocationChange(Location location);
    }
}