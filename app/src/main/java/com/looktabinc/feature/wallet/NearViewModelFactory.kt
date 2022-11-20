package com.looktabinc.feature.wallet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class NearViewModelFactory : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NearViewModel() as T
    }
}