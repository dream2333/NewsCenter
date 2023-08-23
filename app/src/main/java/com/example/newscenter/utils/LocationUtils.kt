package com.example.newscenter.utils


import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager


class LocationUtils
private constructor(private val mContext: Context) {
    // 定位回调
    private var mLocationCallBack: LocationCallBack? = null

    // 定位管理实例
    var mLocationManager: LocationManager? = null

    /**
     * 获取定位
     * @param mLocationCallBack 定位回调
     * @return
     */

    fun getLocation(mLocationCallBack: LocationCallBack?) {

        this.mLocationCallBack = mLocationCallBack
        if (mLocationCallBack == null) return
        // 定位管理初始化
        mLocationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        // 通过GPS定位
        val gpsProvider = mLocationManager!!.getProviderProperties(LocationManager.GPS_PROVIDER)
        // 通过网络定位
        val netProvider = mLocationManager!!.getProviderProperties(LocationManager.NETWORK_PROVIDER)
        // 优先考虑GPS定位，其次网络定位。
        if (gpsProvider != null) {
            gpsLocation()
        } else if (netProvider != null) {
            netWorkLocation()
        } else {
            mLocationCallBack.setLocation(null)
        }
    }

    /**
     * GPS定位
     * @return
     */

    @SuppressLint("MissingPermission")
    private fun gpsLocation() {
        mLocationManager!!.requestLocationUpdates(
            GPS_LOCATION, MIN_TIME, MIN_DISTANCE, mLocationListener
        )
    }

    /**
     * 网络定位
     */

    @SuppressLint("MissingPermission")
    private fun netWorkLocation() {
        mLocationManager!!.requestLocationUpdates(
            NETWORK_LOCATION, MIN_TIME, MIN_DISTANCE, mLocationListener
        )
    }

    // 定位监听
    private val mLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            if (mLocationCallBack != null) {
                mLocationCallBack!!.setLocation(location)
            }
        }

        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {
            // 如果gps定位不可用,改用网络定位
            if (provider == LocationManager.GPS_PROVIDER) {
                netWorkLocation()
            }
        }
    }

    /**
     * @className: LocationCallBack
     * @classDescription: 定位回调
     */
    interface LocationCallBack {
        fun setLocation(location: Location?)

    }

    companion object {
        // GPS定位
        private const val GPS_LOCATION = LocationManager.GPS_PROVIDER

        // 网络定位
        private const val NETWORK_LOCATION = LocationManager.NETWORK_PROVIDER

        // 解码经纬度最大结果数目
        private const val MAX_RESULTS = 1

        // 时间更新间隔，单位：ms
        private const val MIN_TIME: Long = 1000

        // 位置刷新距离，单位：m
        private const val MIN_DISTANCE = 0.01.toFloat()

        // singleton
        @SuppressLint("StaticFieldLeak")
        private var instance: LocationUtils? = null

        /**
         * singleton
         * @param mContext 上下文
         * @return
         */
        fun getInstance(mContext: Context): LocationUtils? {
            if (instance == null) {
                instance = LocationUtils(mContext)
            }
            return instance
        }
    }
}
