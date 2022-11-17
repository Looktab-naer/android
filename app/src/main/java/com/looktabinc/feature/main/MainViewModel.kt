package com.looktabinc.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.looktabinc.base.BaseViewModel
import com.looktabinc.base.Event
import com.looktabinc.data.source.KaKaoRepository

class MainViewModel (private val reviewDataSource: KaKaoRepository) : BaseViewModel() {

    val close = "close"

    private val _reviewHistoryEvent = MutableLiveData<Event<String>>()
    val reviewHistoryEvent: LiveData<Event<String>> get() = _reviewHistoryEvent

    private val _airDropEvent = MutableLiveData<Event<String>>()
    val airDropEvent: LiveData<Event<String>> get() = _airDropEvent

    enum class MainFlow { NEXT, BACK, GONE }
    private val _mainFlow = MutableLiveData<MainFlow>()
    val mainFlow: LiveData<MainFlow> = _mainFlow

    enum class ViewFlow { HOME,MYPAGE, MYCHECKIN, AIRDROP_SETTING,REVIEW_HISTORY,REVIEW_WRITE }
    private val _flow = MutableLiveData<ViewFlow>()
    val flow: LiveData<ViewFlow> = _flow

    fun onClickFragmentEvent(text: String) {
        _reviewHistoryEvent.value = Event(text)
    }
    fun onClickAirDropFragmentEvent(text: String) {
        _airDropEvent.value = Event(text)
    }
    fun setMainFlow(state: MainFlow) {
        _mainFlow.value = state
    }


}