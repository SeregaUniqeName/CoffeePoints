package com.example.confermscreen.di

import com.example.core.presentation.ViewModelsFactory
import dagger.Subcomponent

@Subcomponent(modules = [ConfirmScreenViewModelModule::class])
interface ConfirmScreenComponent {

    fun getViewModelsFactory() : ViewModelsFactory

    @Subcomponent.Factory
    interface Factory {

        fun create() : ConfirmScreenComponent
    }
}