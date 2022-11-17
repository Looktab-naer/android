package com.looktabinc.feature.webview

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.looktabinc.feature.webview.bridge.WebAPIControllerInterface
import com.looktabinc.*
import com.looktabinc.ApplicationUnit.Empty
import com.looktabinc.databinding.ActivityWebviewBinding
import com.looktabinc.feature.ar.ArActivity
import com.looktabinc.feature.webview.bridge.WebAPIController

class WebViewActivity : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview) {

    private val loadUrl by lazy {
        intent?.getStringExtra(Config.LOAD_URL) ?: String.Empty
    }
    val context = this

    override fun initViews() {
        super.initViews()
        setStatusBarColor()
        Log.e("1loadUrl", loadUrl)
        binding.webview.setJavaScriptInterface(mListener)
        binding.webview.loadUrl(loadUrl)
    }

    fun reload() {
        binding.webview.reload()
    }

    fun firstLoginWeb() {
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.dialog_container, JoinCompleteFragment.newInstance(Config.KEY_CART_WEB))
//        }.commit()
    }

    val mListener = object : WebAPIControllerInterface {
        override fun onEventFunction(functionName: String) {
            when (functionName) {
                WebAPIController.FunctionName.START_HOME -> {
                    val intent = Intent(this@WebViewActivity, ArActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                WebAPIController.FunctionName.ON_BACK_BUTTON_PRESSED -> {
                    onBackPressed()
                }
                WebAPIController.FunctionName.PURCHASE_COMPLETE -> {

                }
            }
        }

        override fun onEventWriteReview(
            functionName: String,
            orderDetailSeq: Int?,
            seq: Int?,
            orderNo: Int?,
            thumImgDir: Int?,
            productName: Int?,
            productOptionEa: Int?,
            productOptionName: Int?,
            authdate: Int?
        ) {
            {
            }
        }

        override fun close() {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        ApplicationUnit.isFirst?.let {
            firstLoginWeb()
            ApplicationUnit.removeIsFirst()
        }
        reload()
    }

    private fun setStatusBarColor() {
        val colorResId = R.color.white
        window?.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = ContextCompat.getColor(this@WebViewActivity, colorResId)
        }
    }

    companion object {
        fun intent(context: Context): Intent {
            return Intent(context, WebViewActivity::class.java)
        }

        fun start(context: Context?, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, WebViewActivity::class.java).apply(action)
            context?.startActivity(intent)
        }

        fun start(context: Context?, loadUrl: String, action: Intent.() -> Unit = {}) {
            val intent = Intent(context, WebViewActivity::class.java).apply(action)
            intent.putExtra(Config.LOAD_URL, loadUrl)
            context?.startActivity(intent)
        }
    }


    override fun onBackPressed() {
        if (binding.webview.canGoBack()) {
            binding.webview.goBack()
        } else {
            super.onBackPressed()
        }
    }
}