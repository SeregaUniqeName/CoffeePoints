package com.example.coffeepoints.di

import android.content.Context
import com.example.authorization.di.AuthComponent
import com.example.coffeepointslist.di.CoffeePointsComponent
import com.example.coffeepointsmap.di.MapItemsComponent
import com.example.coffeepointsmenu.di.CoffeeMenuComponent
import com.example.confermscreen.di.ConfirmScreenComponent
import com.example.core.di.ApplicationScope
import com.example.data.di.DataModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [DataModule::class]
)
interface ApplicationComponent {

    fun getAuthScreenComponentFactory() : AuthComponent.Factory
    fun getCoffeePointsScreenComponentFactory() : CoffeePointsComponent.Factory
    fun getCoffeePointsMapScreenComponentFactory() : MapItemsComponent.Factory
    fun getCoffeePointMenuComponentFactory() : CoffeeMenuComponent.Factory

    fun getConfirmOrderComponentFactory() : ConfirmScreenComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ) : ApplicationComponent
    }
}