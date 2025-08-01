package com.example.coffeepointslist.di

import com.example.core.presentation.ViewModelsFactory
import dagger.Subcomponent


@Subcomponent(modules = [CoffeePointsViewModelModule::class])
interface CoffeePointsComponent {

    fun getViewModelsFactory() : ViewModelsFactory

    @Subcomponent.Factory
    interface Factory {

        fun create() : CoffeePointsComponent
    }
}