package com.example.confermscreen.di

import androidx.lifecycle.ViewModel
import com.example.confermscreen.presentation.ConfirmScreenViewModel
import com.example.core.di.ViewModelsKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ConfirmScreenViewModelModule {

    @ViewModelsKey(ConfirmScreenViewModel::class)
    @IntoMap
    @Binds
    fun bindConfirmScreenViewModel(impl: ConfirmScreenViewModel) : ViewModel
}