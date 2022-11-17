package com.looktabinc.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.looktabinc.data.source.KaKaoRepository

class MainViewModelFactory(
    private val repository: KaKaoRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}