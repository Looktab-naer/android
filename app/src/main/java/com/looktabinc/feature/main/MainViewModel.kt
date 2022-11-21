package com.looktabinc.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.looktabinc.Config
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

    private val _arFlow = MutableLiveData<Config.ArType>()
    val arFlow: LiveData<Config.ArType> = _arFlow
    fun setArwFlow(state: Config.ArType) {
        _arFlow.value = state
    }

    private val _historyList = MutableLiveData<List<ReviewHistory>>()
    val historyList: LiveData<List<ReviewHistory>> = _historyList

    fun getHistory() {
        _historyList.value = listOf(
           ReviewHistory(
               id = 1,
               image = listOf(
                   "https://raw.githubusercontent.com/Looktab-naer/imgs/main/a-1.png",
                   "https://raw.githubusercontent.com/Looktab-naer/imgs/main/a-2.png",
                   "https://raw.githubusercontent.com/Looktab-naer/imgs/main/a-3.png",
                   "https://raw.githubusercontent.com/Looktab-naer/imgs/main/a-4.png"),
               category = "CAFE",
               storeName = "Overflow",
               content = "The cafe owner is so kind. The coffee is so good\uD83D\uDE18",
               date = "2021.11.03"
           ),
           ReviewHistory(
               id = 2,
               image = listOf(
                   "https://raw.githubusercontent.com/Looktab-naer/imgs/main/b-1.png",
                   "https://raw.githubusercontent.com/Looktab-naer/imgs/main/b-2.png",
                   ),
               category = "BAR",
               storeName = "Lamm",
               content ="If you enjoy cocktails and single malt, make sure to visit!\n" +
                   "The atmosphere of the store was good, and it was great that they made drinks " +
                   "according to their taste.Other than that, simple finger food or coffee is also the best! \uD83D\uDC4D\uD83C\uDFFB\n",
               date = "2022.08.01"
           )
       )
    }

    private val _checkinList = MutableLiveData<List<Checkin>>()
    val checkinList: LiveData<List<Checkin>> = _checkinList

    fun getCheckin() {
        _checkinList.value = listOf(
            Checkin(
                id = 1,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/c-1.jpg",
                category = "CAFE",
                storeName = "Overflow",
                date = "2022.11.03"
            ),
            Checkin(
                id = 2,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/c-2.jpg",
                category = "LAMM",
                storeName = "BAR",
                date = "2022.09.13"
            ),
            Checkin(
                id = 3,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/c-3.jpg",
                category = "FITNESS",
                storeName = "MMT FITNESS",
                date = "2022.08.11"
            ),
            Checkin(
                id = 4,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/c-4.jpg",
                category = "RESTAURANT",
                storeName = "Mokro",
                date = "2022.08.03"
            ),
            Checkin(
                id = 5,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/b-1.png",
                category = "BAR",
                storeName = "Toroya exs",
                date = "2022.08.01"
            )
        )
    }

    val checkInItem = MutableLiveData<Checkin>()
    fun selectCheckIn(id:Int) {
        checkInItem.value = checkinList.value?.first{
            it.id == id
        }
    }

    private val _neighborhoodList = MutableLiveData<List<Neighborhood>>()
    val neighborhoodList: LiveData<List<Neighborhood>> = _neighborhoodList

    val neighborhoodItem = MutableLiveData<Neighborhood>()

    fun getNeighborhoodItem(int: Int){
        neighborhoodItem.value = neighborhoodList.value?.get(int)
    }
    fun getNeighborhood() {
        _neighborhoodList.value = listOf(
            Neighborhood(
                id = 1,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/shorts-0.png",
                category = "BAR",
                storeName = "Lamm",
                content = "If you enjoy cocktails and single malt, make sure to visit!\n" +
                    "The atmosphere of the store was good, and it was great that they made drinks according to their taste.Other than that, simple finger food or coffee is also the best! \uD83D\uDC4D\uD83C\uDFFB\n",
                status = 1,
                distance = "50m",
                date = "2022.02.03"
            ),
            Neighborhood(
                id = 2,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/shorts-1.png",
                category = "FITNESS",
                storeName = "MMT FITNESS",
                content = "I started with body type imbalance and health problems, but now the pain is really relieved and my body shape is being taken! It is systematically carried out according to your physical condition, and you can get a good(?) pain every day after class because you set each movement in detail. :)",
                status = 3,
                distance = "125m",
                date = "2022.02.03"
            ),
            Neighborhood(
                id = 3,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/shorts-2.png",
                category = "CAFE",
                storeName = "OVER FLOW",
                content = "The cafe is very clean and pretty\n" +
                    "Coffee and dessert are good, too\n" +
                    "The staff are so kind. I love it\n" +
                    "It's a very pleasant cafe that's rare in Gangnam. :)",
                status = 2,
                distance = "5m",
                date = "2022.02.03"
            ),
            Neighborhood(
                id = 4,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/shorts-3.png",
                category = "RESTAURANT",
                storeName = "Mokro",
                content = "Just like the name, the store is so pretty The menu is so delicious and it's not the price of Gangnam Station, so it's very cost-effective. The plates and cups are so cute It's full of emotions\n" +
                    "It's refreshing and delicious to eat with karaake rice. The food is cooked right away, so it's warm and the food is clean",
                status = 1,
                distance = "300m",
                date = "2022.02.03"
            ),
        )
    }

}