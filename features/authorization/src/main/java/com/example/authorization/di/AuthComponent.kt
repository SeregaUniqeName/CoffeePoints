package com.example.authorization.di

import com.example.core.presentation.ViewModelsFactory
import dagger.Subcomponent

@Subcomponent(
    modules = [RegistrationViewModelModule::class, LogInViewModelModule::class]
)
interface AuthComponent {

    fun getViewModelsFactory() : ViewModelsFactory

    @Subcomponent.Factory
    interface Factory {

        fun create() : AuthComponent
    }
}