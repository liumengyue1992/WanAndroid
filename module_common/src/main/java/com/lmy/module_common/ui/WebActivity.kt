package com.lmy.module_common.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.alibaba.android.arouter.facade.annotation.Route
import com.lmy.base.BaseActivity
import com.lmy.base.BaseApplication
import com.lmy.ext.setGone
import com.lmy.module_common.PATH_WEB
import com.lmy.module_common.R
import com.lmy.module_common.WEB_LINK
import com.lmy.module_common.WEB_TITLE
import com.lmy.module_common.databinding.ActivityWebBinding
import com.lmy.uitl.LogUtil


/**
 * @author: mengyue.liu
 * @date: 2023/5/6
 * @description：
 */
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = PATH_WEB)
class WebActivity : BaseActivity<ActivityWebBinding>() {

    private val TAG = WebActivity::class.java.simpleName

    private lateinit var webView: WebView

    override fun getLayoutId(): Int = R.layout.activity_web

    override fun initData() {
        binding.ivBack.setOnClickListener { finish() }
        if (intent == null) {
            return
        }
        binding.txWebTitle.text = intent.getStringExtra(WEB_TITLE)
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        val webLink = intent.getStringExtra(WEB_LINK) ?: ""

        webView = WebView(BaseApplication.mContext)
        // webView添加到container中
        binding.webContainer.addView(webView)
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.domStorageEnabled = true

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                LogUtil.dWithTag(TAG, "onPageStarted: ")
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                LogUtil.dWithTag(TAG, "onPageFinished: ")
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                LogUtil.dWithTag(TAG, "shouldOverrideUrlLoading: =====1")
                return true
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                LogUtil.dWithTag(TAG, "shouldOverrideUrlLoading: =====2")
                // 很多 H5 页面会恶意跳转淘宝等，可以在 WebView 的 shouldOverrideUrlLoading 中做一下拦截，非常影响用户体验。
//                if (!webLink.equals(request?.url)) {
//                    LogUtil.dWithTag(TAG, "shouldOverrideUrlLoading: =====3")
//                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(request?.url.toString())))
//                    return true
//                }
//                return false

                if (!request?.url.toString().startsWith("http")) {
                    return true
                }
                return false
            }
        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                LogUtil.dWithTag(TAG, "onProgressChanged:$newProgress")
                binding.progressBar.progress = newProgress
                if (newProgress == 100) {
                    binding.progressBar.setGone(false)
                }
            }
        }
        webView.loadUrl(webLink)
    }

    override fun onDestroy() {
        binding.webContainer.removeAllViews()
        webView.clearHistory()
        webView.destroy()
        super.onDestroy()
    }
}