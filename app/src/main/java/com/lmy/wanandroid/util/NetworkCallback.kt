package com.lmy.wanandroid.util

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities

private const val TAG = "NetworkUtils"

/**
 * 异步状态网络监听
 */
class NetworkCallback private constructor() : ConnectivityManager.NetworkCallback() {

    private object SingletonHolder {
        val holder = NetworkCallback()
    }

    companion object {
        val instance = SingletonHolder.holder
    }

    /**
     * 网络连接成功回调
     */
    override fun onAvailable(network: Network) {
        LogUtil.d(TAG, "网络连接成功")
        super.onAvailable(network)
    }

    /**
     * 网络连接超时或网络不可达
     */
    override fun onUnavailable() {
        LogUtil.d(TAG, "网络连接超时或者网络连接不可达")
        super.onUnavailable()
    }

    /**
     * 网络已断开连接
     */
    override fun onLost(network: Network) {
        LogUtil.d(TAG, "网络已断开连接")
        super.onLost(network)
    }

    /**
     * 网络正在丢失连接
     */
    override fun onLosing(network: Network, maxMsToLive: Int) {
        LogUtil.d(TAG, "网络正在断开连接")
        super.onLosing(network, maxMsToLive)
    }

    /**
     * 网络状态变化
     */
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        LogUtil.d(TAG, "net status change! 网络连接改变")
        super.onCapabilitiesChanged(network, networkCapabilities)
        // 表明此网络连接成功验证
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                // 使用WI-FI
                LogUtil.d(TAG, "当前在使用WiFi上网")
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                // 使用数据网络
                LogUtil.d(TAG, "当前在使用数据网络上网")
            } else {
                // 未知网络，包括蓝牙、VPN等
                LogUtil.d(TAG, "当前在使用其他网络")
            }
        }
    }

    /**
     * 网络连接属性变化
     */
    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        LogUtil.d(TAG, "网络连接成功")
        super.onLinkPropertiesChanged(network, linkProperties)
    }

    /**
     * 访问的网络阻塞状态发生变化
     */
    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
        LogUtil.d(TAG, "网络连接成功")
        super.onBlockedStatusChanged(network, blocked)
    }
}