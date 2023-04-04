package com.lmy.wanandroid.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.provider.Settings
import com.lmy.BaseApplication

/**
 * 同步获取网络状态工具类
 */
object NetworkUtils {
    /**
     * 注册异步网络监听
     */
    fun setNetListener() {
        var request = NetworkRequest.Builder().build()
        var connMgr = BaseApplication.mContext
            ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connMgr.registerNetworkCallback(request, NetworkCallback.instance)
    }
    
    /**
     * 注册异步网络监听
     */
    fun registNetListener(
        networkCallback: ConnectivityManager.NetworkCallback,
    ) {
        var request = NetworkRequest.Builder().build()
        var connMgr = BaseApplication.mContext
            ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connMgr.registerNetworkCallback(request, networkCallback)
    }
    
    /**
     * 取消注册异步网络监听
     */
    fun unregistNetListener(
        networkCallback: ConnectivityManager.NetworkCallback,
    ) {
        var connMgr = BaseApplication.mContext
            ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connMgr.unregisterNetworkCallback(networkCallback)
    }
    
    /**
     * 判断网络是否连接
     */
    fun isNetworkConnected(): Boolean {
        if (BaseApplication.mContext != null) {
            val mConnectivityManager =
                BaseApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val mNetworkInfo = mConnectivityManager.activeNetworkInfo
                if (mNetworkInfo != null) {
                    return mNetworkInfo.isAvailable
                }
            } else {
                val network = mConnectivityManager.activeNetwork ?: return false
                val status = mConnectivityManager.getNetworkCapabilities(network) ?: return false
                if (status.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    return true
                }
            }
        }
        return false
    }
    
    /**
     * 判断是否是WiFi连接
     */
    fun isWifiConnected(): Boolean {
        if (BaseApplication.mContext != null) {
            val mConnectivityManager =
                BaseApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val mWiFiNetworkInfo =
                    mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (mWiFiNetworkInfo != null) {
                    return mWiFiNetworkInfo.isAvailable
                }
            } else {
                val network = mConnectivityManager.activeNetwork ?: return false
                val status = mConnectivityManager.getNetworkCapabilities(network) ?: return false
                if (status.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                }
            }
        }
        return false
    }
    
    /**
     * 判断是否是数据网络连接
     */
    fun isMobileConnected(): Boolean {
        if (BaseApplication.mContext != null) {
            val mConnectivityManager =
                BaseApplication.mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                val mMobileNetworkInfo =
                    mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                if (mMobileNetworkInfo != null) {
                    return mMobileNetworkInfo.isAvailable
                }
            } else {
                val network = mConnectivityManager.activeNetwork ?: return false
                val status = mConnectivityManager.getNetworkCapabilities(network) ?: return false
                if (status.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                }
            }
        }
        return false
    }
    
    /**
     * 跳转WLAN设置页
     */
    fun startWlanSetting() {
        var intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        BaseApplication.mContext?.startActivity(intent)
    }
}
