package com.example.coffeepointsmenu.di

import androidx.lifecycle.ViewModel
import com.example.coffeepointsmenu.presentation.CoffeeMenuViewModel
import com.example.core.di.ViewModelsKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface CoffeeMenuViewModelModule {

    @IntoMap
    @ViewModelsKey(CoffeeMenuViewModel::class)
    @Binds
    fun bindCoffeeMenuViewModel(impl: CoffeeMenuViewModel) : ViewModel
}