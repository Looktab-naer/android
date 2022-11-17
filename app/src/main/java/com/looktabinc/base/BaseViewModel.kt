/*
 * Created by Leo on 2021. 09. 26 ..
 */
package com.hashshopinc.hashshop.base

import android.content.Context
import androidx.lifecycle.*
import com.hashshopinc.hashshop.feature.webview.WebViewActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseViewModel : ViewModel() {

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    val compositeDisposable = CompositeDisposable()


    fun showLoading() {
        _isLoading.value = true
    }

    fun hideLoading() {
        _isLoading.value = false
    }

    fun onClickUserAgreement(context: Context, link: String) {
        WebViewActivity.start(
            context = context,
            loadUrl = link
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
        hideLoading()
    }

//    fun checkAccessToken(): String {
//        if (ApplicationUnit.accessToken.isNullOrEmpty()) {
////           TODO :: refrash token
//            Log.e("123", "token을 재발급 받아주세요")
//        }
//        return ApplicationUnit.accessToken ?: ""
//    }
}


inline fun <T> LiveData<Event<T>>.eventObserve(
    owner: LifecycleOwner,
    crossinline onChanged: (T) -> Unit
): Observer<Event<T>> {
    val wrappedObserver = Observer<Event<T>> { t ->
        t.getContentIfNotHandled()?.let {
            onChanged.invoke(it)
        }
    }
    observe(owner, wrappedObserver)
    return wrappedObserver
}
