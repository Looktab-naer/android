package com.looktabinc

import android.webkit.WebSettings
import com.looktabinc.base.BaseActivity
import com.looktabinc.databinding.ActivityWebBinding
import com.wikitude.architect.ArchitectStartupConfiguration


class WebActivity : BaseActivity<ActivityWebBinding>(R.layout.activity_web) {

    val url = "file:///android_asset/index2.html"
    val url2 = "file:///android_asset/www/index.html"

    override fun initViews() {
        val settings: WebSettings = binding.architectView.settings
        settings.allowFileAccess = true
        binding.architectView.settings.javaScriptEnabled = true
        binding.architectView.loadUrl(url2)

//        binding.architectView.loadUrl("file:///android_asset/index2.html")


    }
}