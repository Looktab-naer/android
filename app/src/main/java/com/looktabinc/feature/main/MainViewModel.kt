package com.looktabinc.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.looktabinc.base.BaseViewModel
import com.looktabinc.base.Event
import com.looktabinc.data.source.KaKaoRepository
import com.looktabinc.data.source.KaKaoSource

class MainViewModel (private val reviewDataSource: KaKaoSource) : BaseViewModel() {

    val close = "close"

    private val _reviewWriteEvent = MutableLiveData<Event<String>>()
    val reviewWriteEvent: LiveData<Event<String>> get() = _reviewWriteEvent

    private val _reviewHistoryEvent = MutableLiveData<Event<String>>()
    val reviewHistoryEvent: LiveData<Event<String>> get() = _reviewHistoryEvent

    private val _airDropEvent = MutableLiveData<Event<String>>()
    val airDropEvent: LiveData<Event<String>> get() = _airDropEvent

    enum class ViewFlow { HOME,MYPAGE, MYCHECKIN, AIRDROP_SETTING,REVIEW_HISTORY,REVIEW_WRITE }
    private val _flow = MutableLiveData<ViewFlow>()
    val flow: LiveData<ViewFlow> = _flow

    fun onClickReviewHistoryFragmentEvent(text: String) {
        _reviewHistoryEvent.value = Event(text)
    }
    fun onClickAirDropFragmentEvent(text: String) {
        _airDropEvent.value = Event(text)
    }
    fun onClickReviewWriteFragmentEvent(text: String) {
        _reviewWriteEvent.value = Event(text)
    }
//    fun setMainFlow(state: MainFlow) {
//        _mainFlow.value = state
//    }
    fun setViewFlow(state: ViewFlow) {
        _flow.value = state
    }


}