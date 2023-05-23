package com.lmy.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lmy.wanandroid.util.DataStoreUtil
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl

/**
 * @author: mengyue.liu
 * @date: 2023/5/23
 * @description：Cookie本地缓存
 *
 * <-- 200 OK https://www.wanandroid.com/user/login (700ms)
 * Server: Apache-Coyote/1.1
 * Set-Cookie: JSESSIONID=801707F1BFCBB38EA6B658705A77DFC0; Path=/; Secure; HttpOnly
 * Set-Cookie: loginUserName=xxxxxxxxx; Expires=Thu, 22-Jun-2023 06:21:47 GMT; Path=/
 * Set-Cookie: token_pass=5d9b90bcb70640183e09d1e755ead823; Expires=Thu, 22-Jun-2023 06:21:47 GMT; Path=/
 * Set-Cookie: loginUserName_wanandroid_com=xxxxxxxxx; Domain=wanandroid.com; Expires=Thu, 22-Jun-2023 06:21:47 GMT; Path=/
 * Set-Cookie: token_pass_wanandroid_com=5d9b90bcb70640183e09d1e755ead823; Domain=wanandroid.com; Expires=Thu, 22-Jun-2023 06:21:47 GMT; Path=/
 * Content-Type: application/json;charset=UTF-8
 * Transfer-Encoding: chunked
 * Date: Tue, 23 May 2023 06:21:47 GMT

 * {"data":{"admin":false,"chapterTops":[],"coinCount":46,"collectIds":[9103],"email":"","icon":"","id":149158,"nickname":"liumengyue1992","password":"","publicName":"liumengyue1992","token":"","type":0,"username":"liumengyue1992"},"errorCode":0,"errorMsg":""}
 * <-- END HTTP (257-byte body)
 *
 *
 *
 * Cookie会包含如下信息：
 * name：cookie 的名字，用于标识不同的cookie
 * expires：过期时间。值是一个日期，一个时刻，而不是一个时长,即在什么时候应该删除该cookie。
 *          在OkHttp中，你可以使用该字段在端上建立逻辑，也可以忽略该字段依靠server实现过期的逻辑。
 * domain：cookie的作用域,这个个属性可以让同一个主域名下的子域名都可以访问该cookie。
 * path：cookie所属的路径。如果设置了该属性，则只有该路径下的页面可以访问该cookie。
 * secure：secure属性用来指定Cookie只能在加密协议HTTPS下发送到服务器。该属性只是一个开关，不需要指定值。如果通信是HTTPS协议，该开关自动打开。
 * HttpOnly：用于设置该Cookie不能被JavaScript读取.(即document.cookie不会返回这个Cookie的值），只用于向服务器发送。
 */
class MyCookie : CookieJar {
    private val gson = Gson()
    //本地可校验cookie，并根据需要存储
    private val cookieType: TypeToken<List<Cookie>> = object : TypeToken<List<Cookie>>() {}

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        //从本地拿取需要的cookie
        val cookies = DataStoreUtil.getData(COOKIE_LOGIN, "")
        if (cookies.isNotEmpty()) {
            return gson.fromJson(cookies, cookieType.type)
        }
        return arrayListOf()
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        DataStoreUtil.putData(COOKIE_LOGIN, gson.toJson(cookies, cookieType.type))
    }
}