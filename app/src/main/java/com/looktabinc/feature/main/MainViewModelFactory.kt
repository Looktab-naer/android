package com.looktabinc.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.looktabinc.data.source.KaKaoSource


class MainViewModelFactory(
    private val repository: KaKaoSource
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}