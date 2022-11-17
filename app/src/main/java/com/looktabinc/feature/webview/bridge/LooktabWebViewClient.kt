package com.looktabinc.feature.webview.bridge

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import java.net.URISyntaxException

class LooktabWebViewClient(val context: Context) : WebViewClient() {

    sealed class Action {
        object RemoveLoadingView : Action()
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        action.trySend(Action.RemoveLoadingView)
    }

    companion object {
        val actionFlow: Flow<Action>
            get() = action.receiveAsFlow()
        private val action = Channel<Action>(Channel.BUFFERED)
    }

    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        val intent = parse(url)
        return if (isIntent(url)) {
            if (isExistInfo(intent) or isExistPackage(intent))
                start(intent)
            else
                gotoMarket(intent)
        } else if (isMarket(url))
            start(intent)
        else
            url.contains("https://bootpaymark")
    }

    private fun isIntent(url: String?) = url?.matches(Regex("^intent:?\\w*://\\S+$")) ?: false

    private fun isMarket(url: String?) = url?.matches(Regex("^market://\\S+$")) ?: false

    private fun isExistInfo(intent: Intent?): Boolean {
        return try {
            intent != null && intent.`package`?.let {
                context.packageManager.getPackageInfo(
                    it,
                    PackageManager.GET_ACTIVITIES
                )
            } != null
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }

    }


    private fun isExistPackage(intent: Intent?): Boolean =
        intent != null && intent.`package`?.let {
            context.packageManager.getLaunchIntentForPackage(
                it
            )
        } != null

    private fun parse(url: String): Intent? {
        return try {
            Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
        } catch (e: URISyntaxException) {
            null
        }

    }

    private fun start(intent: Intent?): Boolean {
        intent?.let { context.startActivity(it) }
        return true
    }

    private fun gotoMarket(intent: Intent?): Boolean {
        intent?.let {
            start(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("market://details?id=${it.`package`}")
            })
        }
        return true
    }
}