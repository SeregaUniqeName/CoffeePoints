package com.example.coffeepointslist.di

import androidx.lifecycle.ViewModel
import com.example.coffeepointslist.presentation.CoffeePointsViewModel
import com.example.core.di.ViewModelsKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CoffeePointsViewModelModule {

    @IntoMap
    @ViewModelsKey(CoffeePointsViewModel::class)
    @Binds
    fun bindCoffeePointsViewModel(impl: CoffeePointsViewModel) : ViewModel

}