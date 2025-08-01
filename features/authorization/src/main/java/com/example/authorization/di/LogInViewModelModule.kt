package com.example.authorization.di

import androidx.lifecycle.ViewModel
import com.example.authorization.presentation.LogInViewModel
import com.example.core.di.ViewModelsKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface LogInViewModelModule {

    @IntoMap
    @ViewModelsKey(LogInViewModel::class)
    @Binds
    fun bindLogInViewModel(viewModel: LogInViewModel) : ViewModel
}