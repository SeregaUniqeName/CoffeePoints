package com.example.coffeepointsmenu.di

import com.example.core.presentation.ViewModelsFactory
import dagger.Subcomponent

@Subcomponent(modules = [CoffeeMenuViewModelModule::class])
interface CoffeeMenuComponent {

    fun getViewModelsFactory() : ViewModelsFactory

    @Subcomponent.Factory
    interface Factory {
        fun create() : CoffeeMenuComponent
    }
}