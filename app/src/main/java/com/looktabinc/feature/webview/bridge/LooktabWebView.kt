package com.looktabinc.feature.webview.bridge

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
import android.webkit.WebView

/** WebView Customizing **/
class LooktabWebView : WebView {

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    fun setJavaScriptInterface(mListener: WebAPIControllerInterface?) {
        val javaScriptInterface = JavaScriptInterface(this, mListener)
        addJavascriptInterface(javaScriptInterface, "hashshop_native_api")
        WebAPIController.jsInterface = javaScriptInterface
        clearCache(false)
        requestFocus()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {
        webChromeClient = LooktabWebChromeClient()
        webViewClient = LooktabWebViewClient(this.context)
        setWebContentsDebuggingEnabled(true)
        applySettings()
        setBackgroundColor(Color.argb(1, 0, 0, 0))
        //이니시
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(this, true)
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Suppress("deprecation")
    private fun applySettings() {
        settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            useWideViewPort = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            mediaPlaybackRequiresUserGesture = false
            allowFileAccess = true
            allowContentAccess = true
            allowFileAccessFromFileURLs = true
            allowUniversalAccessFromFileURLs = true
            savePassword = false
            domStorageEnabled = true
            //text size
            textZoom=(100)
            //이니시스
            mixedContentMode = MIXED_CONTENT_ALWAYS_ALLOW

            // https://stackoverflow.com/questions/7422427/android-webview-slow
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
            cacheMode = WebSettings.LOAD_DEFAULT
        }
    }
}
