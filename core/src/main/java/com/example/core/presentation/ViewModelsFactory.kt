package com.example.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import jakarta.inject.Inject
import jakarta.inject.Provider

class ViewModelsFactory @Inject constructor(
    private val viewModelsProvider: @JvmSuppressWildcards Map<Class<out ViewModel>, Provider<ViewModel>>,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModelsProvider[modelClass]?.get() as T
    }

}