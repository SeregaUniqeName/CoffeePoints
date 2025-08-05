package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.core.di.ApplicationScope
import com.example.data.api.AuthorizationRepository
import com.example.data.api.CoffeePointsRepository
import com.example.data.impl.AuthorizationRepositoryImpl
import com.example.data.impl.CoffeePointsRepositoryImpl
import com.example.data.local.TokenLifetimeStore
import com.example.data.local.TokenLifetimeStoreImpl
import com.example.data.network.AuthApiFactory
import com.example.data.network.AuthApiService
import com.example.data.network.CoffeePointsApiFactory
import com.example.data.network.CoffeePointsApiService
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindAuthRepository(impl: AuthorizationRepositoryImpl) : AuthorizationRepository

    @ApplicationScope
    @Binds
    fun bindCoffeePointsRepository(impl: CoffeePointsRepositoryImpl) : CoffeePointsRepository

    @ApplicationScope
    @Binds
    fun bindTokenLifetimeStore(impl: TokenLifetimeStoreImpl) : TokenLifetimeStore


    companion object {

        @ApplicationScope
        @Provides
        fun provideAuthApiService() : AuthApiService {
            return AuthApiFactory.authApiService
        }

        @ApplicationScope
        @Provides
        fun provideCoffeePointsApiService(
            tokenLifetimeStore: TokenLifetimeStore
        ) : CoffeePointsApiService {
            return CoffeePointsApiFactory(tokenLifetimeStore).coffeePointsApiService
        }

        @ApplicationScope
        @Provides
        fun provideSharedPreferences(context: Context) : SharedPreferences {
            return context.getSharedPreferences(SHARED_TOKEN_LIFETIME, Context.MODE_PRIVATE)
        }

        private const val SHARED_TOKEN_LIFETIME = "tokenLifetime"
    }
}