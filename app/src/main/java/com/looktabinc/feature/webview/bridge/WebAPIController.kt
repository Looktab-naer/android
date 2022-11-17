package com.looktabinc.feature.webview.bridge

import android.content.Context
import android.util.Log
import com.google.gson.JsonObject
import com.looktabinc.feature.webview.WebViewActivity
import com.looktabinc.ApplicationUnit

object WebAPIController {

    internal lateinit var jsInterface: JavaScriptInterface
    private var mListener: WebAPIControllerInterface? = null

    fun setOnItemClickListener(listener: WebAPIControllerInterface?) {
        mListener = listener
    }

    private const val CALLBACK_EVENT = "CALLBACK_EVENT"
    private const val BACK_BUTTON_EVENT = "BACK_BUTTON_EVENT"

    internal fun requestAPI(
        context: Context,
        functionName: String,
        options: JsonObject?,
    ) {
        val extra = JsonObject()
        var returnMsg = JsonObject()

        Log.e("functionName", functionName)
        when (functionName) {
            FunctionName.GET_SERVER_TOKEN -> {
                extra.addProperty(
                    "accessToken", ApplicationUnit.accessToken ?: "토큰이 없네.."
                )
                returnMsg = makeReturnMsg(200, resultMsg = "Success", extra)
            }

            FunctionName.CLOSE_WEB_VIEW -> {
                mListener?.close()
                extra.addProperty(FunctionName.CLOSE_WEB_VIEW, true)
                returnMsg = makeReturnMsg(200, resultMsg = "Success", extra)
            }

            FunctionName.START_WRITE_REVIEW -> {
                mListener?.onEventWriteReview(
                    functionName,
                    orderDetailSeq = if (options?.has("orderDetailSeq") == true)
                        options.get("orderDetailSeq").asInt else null,
                    seq = if (options?.has("seq") == true)
                        options.get("seq").asInt else null,
                    orderNo = if (options?.has("orderNo") == true)
                        options.get("orderNo").asInt else null,
                    thumImgDir = if (options?.has("thumImgDir") == true)
                        options.get("thumImgDir").asInt else null,
                    productName = if (options?.has("productName") == true)
                        options.get("productName").asInt else null,
                    productOptionEa = if (options?.has("productOptionEa") == true)
                        options.get("productOptionEa").asInt else null,
                    productOptionName = if (options?.has("productOptionName") == true)
                        options.get("productOptionName").asInt else null,
                    authdate = if (options?.has("authdate") == true)
                        options.get("authdate").asInt else null
                )
                returnMsg = makeReturnMsg(resultMsg = "Request Success", extra = extra)

            }
            FunctionName.START_WEBVIEW -> {
                if (options?.has("url") == true) {
                    WebViewActivity.start(
                        context = context,
                        loadUrl = options.get("url").asString.toString()
                    )
                }
                returnMsg = makeReturnMsg(resultMsg = "Request Success", extra = extra)
            }
            FunctionName.PURCHASE_COMPLETE,
            -> {
                mListener?.onEventFunction(functionName)
                returnMsg = makeReturnMsg(resultMsg = "Request Success", extra = extra)
            }
            FunctionName.ON_BACK_BUTTON_PRESSED -> {
                mListener?.onEventFunction(functionName)
                returnMsg = makeReturnMsg(eventType = BACK_BUTTON_EVENT)
            }
        }
        jsInterface.onJavaScriptResponse(returnMsg)
    }

    fun sendNativeEvent(
        functionName: String,
        extra: JsonObject?
    ) {
        var returnMsg = JsonObject()
        when (functionName) {
            FunctionName.ON_BACK_BUTTON_PRESSED -> {
                returnMsg = makeReturnMsg(eventType = BACK_BUTTON_EVENT)
            }
        }
        jsInterface.onJavaScriptResponse(returnMsg)
    }

    object FunctionName {
        const val GET_SERVER_TOKEN = "getServerToken"
        const val START_HOME = "startHome"

        const val ON_BACK_BUTTON_PRESSED = "onBackButtonPressed"
        const val CLOSE_WEB_VIEW = "closeWebView"

        const val START_WRITE_REVIEW = "startWriteReview"
        const val START_WEBVIEW = "startWebview"

        const val PURCHASE_COMPLETE = "purchaseDidCompleted"

    }

    /** Web 에게 전달할 callback 생성 **/
    private fun makeReturnMsg(
        resultCode: Int? = 200,
        resultMsg: String? = "Success",
        extra: JsonObject? = null,
        eventType: String? = CALLBACK_EVENT,
    ): JsonObject {
        return JsonObject().apply {
            addProperty("result_cd", resultCode)
            addProperty("result_msg", resultMsg)
            add("extra", extra)
            addProperty("eventType", eventType)
        }
    }

}
