package com.example.coffeepointsmap.di

import com.example.core.presentation.ViewModelsFactory
import dagger.Subcomponent

@Subcomponent(modules = [MapItemsViewModelModule::class])
interface MapItemsComponent {

    fun getViewModelsFactory() : ViewModelsFactory

    @Subcomponent.Factory
    interface Factory {

        fun create() : MapItemsComponent
    }
}