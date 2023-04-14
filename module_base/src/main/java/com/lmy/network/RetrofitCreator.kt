package com.lmy.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.util.concurrent.TimeUnit

/**
 * 网络请求超时时间毫秒
 */
const val DEFAULT_TIMEOUT = 20000L

/**
 * @Description 创建retrofit示例
 *
 * @author lei.yang
 * @date 2022/4/6
 */
object RetrofitCreator {
    
    private val mOkClient = OkHttpClient.Builder()
        .callTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
        .retryOnConnectionFailure(true)
        // .addInterceptor(HeaderIntercept())
        .followRedirects(false)
        .apply {
            // if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor() { message ->
                try {
                    val text: String = URLDecoder.decode(message, "utf-8")
                    Log.e("OKHttp-----", text)
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                    Log.e("OKHttp-----", message)
                }
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addNetworkInterceptor(loggingInterceptor)
            // }
        }.build()
    
    /**
     * Retrofit.Builder
     * @param baseUrl String
     * @return Retrofit.Builder
     */
    fun getRetrofitBuilder(baseUrl: String): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(mOkClient)
            .addConverterFactory(GsonConverterFactory.create())
    }
    
    /**
     * 获取api代理
     * @param baseUrl String
     * @return T
     */
    inline fun <reified T> getApiService(baseUrl: String): T {
        val retrofit = getRetrofitBuilder(
            baseUrl
        ).build()
        return retrofit.create(T::class.java)
    }
}
