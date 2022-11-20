package com.looktabinc.feature.wallet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.looktabinc.base.BaseViewModel

class NearViewModel : BaseViewModel() {
    private val _nftList = MutableLiveData<List<NftResponse>>()
    val nftList: LiveData<List<NftResponse>> = _nftList

    val selectItem = MutableLiveData<NftResponse>()

    var selectTokenId = ""
    fun postNft(a: List<NftResponse>) {
        _nftList.value = a
    }

    fun postNftItem(id: String) {
        selectTokenId = id
        selectItem.value = nftList.value?.first {
            it.token_id == id
        }
    }
}