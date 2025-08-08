package com.example.coffeepointsmap.di

import androidx.lifecycle.ViewModel
import com.example.coffeepointsmap.presentation.MapItemsViewModel
import com.example.core.di.ViewModelsKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MapItemsViewModelModule {

    @IntoMap
    @ViewModelsKey(MapItemsViewModel::class)
    @Binds
    fun bindCoffeePointsViewModel(impl: MapItemsViewModel) : ViewModel
}