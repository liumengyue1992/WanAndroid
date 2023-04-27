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
const val DEFAULT_TIMEOUT = 5000L

/**
 * @Description
 *
 * @author mengyue.liu
 * @date 2022/4/6
 */
object RetrofitManager {
    private const val BASE_URL = "https://www.wanandroid.com/"
    
    private var retrofit: Retrofit
    
    init {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
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
        
         retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    fun <T> getService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
