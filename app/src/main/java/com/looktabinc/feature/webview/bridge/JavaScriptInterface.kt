package com.looktabinc.feature.webview.bridge

import android.os.Handler
import android.util.Log
import android.webkit.JavascriptInterface
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/** WebView 에 올라온 URL 의 JavaScript 와 통신하는 부분 **/
class JavaScriptInterface(
    private val webView: LooktabWebView,
    mListener: WebAPIControllerInterface?
) : JavaScriptInterfaceCallback {

    private val TAG = javaClass.simpleName
    private val handler = Handler()

    init {
        WebAPIController.setOnItemClickListener(mListener)
    }

    override fun onJavaScriptResponse(eventData: JsonObject) {
        val extra = eventData.toString()
        val loadUrlStr = "javascript:looktab.event(\'$extra\')"
        Log.d(TAG, "loadUrlMsg: $loadUrlStr")
        try {
            webView.loadUrl(loadUrlStr)
        } catch (e: Exception) {
            Log.e(TAG, "Uncaught Reference Error: " + e.message)
        }
    }

    /** Web 에서 함수 호출시 호출됨 **/
    @JavascriptInterface
    fun call(funcName: String, _options: String?) {
        Log.d(TAG, "[Web Call] API full name: $funcName / options: $_options")
//        val options =
//            if (_options.isNullOrEmpty()) null else JsonParser.parseString(_options).asJsonObject
//        handler.post {
//            WebAPIController.requestAPI(context = webView.context, funcName, options)
//        }
    }

    override fun onDestroy() {}
}
