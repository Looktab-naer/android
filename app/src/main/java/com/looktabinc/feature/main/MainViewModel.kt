package com.looktabinc.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.looktabinc.base.BaseViewModel
import com.looktabinc.base.Event
import com.looktabinc.data.source.KaKaoSource
import com.looktabinc.feature.model.Checkin
import com.looktabinc.feature.model.Neighborhood
import com.looktabinc.feature.model.ReviewHistory

class MainViewModel (private val reviewDataSource: KaKaoSource) : BaseViewModel() {

    val close = "close"

    private val _reviewWriteEvent = MutableLiveData<Event<String>>()
    val reviewWriteEvent: LiveData<Event<String>> get() = _reviewWriteEvent

    private val _reviewHistoryEvent = MutableLiveData<Event<String>>()
    val reviewHistoryEvent: LiveData<Event<String>> get() = _reviewHistoryEvent

    private val _airDropEvent = MutableLiveData<Event<String>>()
    val airDropEvent: LiveData<Event<String>> get() = _airDropEvent

    enum class ViewFlow { HOME,MYPAGE, MYCHECKIN, AIRDROP_SETTING,REVIEW_HISTORY,REVIEW_WRITE,
        WALLET,AR}
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

    fun setViewFlow(state: ViewFlow) {
        _flow.value = state
    }


    private val _historyList = MutableLiveData<List<ReviewHistory>>()
    val historyList: LiveData<List<ReviewHistory>> = _historyList

    fun getHistory() {
        val a = ReviewHistory(
            id = 1,
            image = listOf("", ""),
            category = "CAFE",
            storeName = "BABI BROUND",
            content = "This is the postscripted text.This is the postscripted text.This is the  w t h",
            date = "2022.02.03"
        )
        _historyList.value = listOf(a, a, a, a)
    }

    private val _checkinList = MutableLiveData<List<Checkin>>()
    val checkinList: LiveData<List<Checkin>> = _checkinList

    fun getCheckin() {
        val a = Checkin(
            id = 1,
            image = "",
            category = "CAFE",
            storeName = "BABI BROUND",
            date = "2022.02.03"
        )
        _checkinList.value = listOf(a, a, a, a)
    }

    private val _neighborhoodList = MutableLiveData<List<Neighborhood>>()
    val neighborhoodList: LiveData<List<Neighborhood>> = _neighborhoodList
    fun getNeighborhood() {
        val a = Neighborhood(
            id = 1,
            image = "",
            category = "CAFE",
            storeName = "BABI BROUND",
            status = 1,
            distance = "500m",
            date = "2022.02.03"
        )
        _neighborhoodList.value = listOf(a, a, a, a)
    }

}