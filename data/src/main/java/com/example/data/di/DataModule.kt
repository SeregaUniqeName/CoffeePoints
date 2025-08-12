package com.example.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.core.di.ApplicationScope
import com.example.data.api.AuthorizationRepository
import com.example.data.api.CoffeePointMenuRepository
import com.example.data.api.CoffeePointsRepository
import com.example.data.api.MapPointsRepository
import com.example.data.impl.AuthorizationRepositoryImpl
import com.example.data.impl.CoffeePointMenuRepositoryImpl
import com.example.data.impl.CoffeePointsRepositoryImpl
import com.example.data.impl.MapPointsRepositoryImpl
import com.example.data.local.TokenLifetimeStore
import com.example.data.local.TokenLifetimeStoreImpl
import com.example.data.local.database.MapItemsDao
import com.example.data.local.database.MapItemsDatabase
import com.example.data.local.database.MenuItemsDao
import com.example.data.local.database.MenuItemsDatabase
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

    @ApplicationScope
    @Binds
    fun bindMapPointsRepository(impl: MapPointsRepositoryImpl) : MapPointsRepository

    @ApplicationScope
    @Binds
    fun bindCoffeePointMenuRepository(impl: CoffeePointMenuRepositoryImpl) : CoffeePointMenuRepository


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

        @ApplicationScope
        @Provides
        fun provideMapItemsDatabase(context: Context) : MapItemsDao {
            return MapItemsDatabase.getInstance(context).mapItemsDao()
        }

        @ApplicationScope
        @Provides
        fun provideMenuItemsDatabase(context: Context) : MenuItemsDao {
            return MenuItemsDatabase.getInstance(context).menuItemsDao()
        }

        private const val SHARED_TOKEN_LIFETIME = "tokenLifetime"
    }
}