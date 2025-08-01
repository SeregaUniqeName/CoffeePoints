package com.example.authorization.di

import androidx.lifecycle.ViewModel
import com.example.authorization.presentation.RegistrationViewModel
import com.example.core.di.ViewModelsKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
interface RegistrationViewModelModule {

    @IntoMap
    @ViewModelsKey(RegistrationViewModel::class)
    @Binds
    fun bindRegistrationViewModel(viewModel: RegistrationViewModel) : ViewModel

}