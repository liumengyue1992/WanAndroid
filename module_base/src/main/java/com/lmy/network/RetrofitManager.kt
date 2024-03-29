package com.lmy.network

import android.util.Log
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.lmy.base.BaseApplication
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

    val cookieJar = PersistentCookieJar(
        SetCookieCache(), SharedPrefsCookiePersistor(BaseApplication.mContext)
    )

    init {
        val okHttpClient = OkHttpClient.Builder()
            .callTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            // .addInterceptor(HeaderIntercept())
            .followRedirects(false)
            .cookieJar(cookieJar)
            .apply {
                // if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor() { message ->
                    try {
                        // 由于接口返回数据中有些带有特殊字符，如%，decode会报错
                        // java.lang.IllegalArgumentException: URLDecoder: Illegal hex characters in escape (%) pattern : %界面
                        // val text: String = URLDecoder.decode(message, "utf-8")
                        Log.e("OKHttp-----", message)
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
