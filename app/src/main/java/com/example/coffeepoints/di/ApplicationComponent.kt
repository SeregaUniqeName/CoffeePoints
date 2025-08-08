package com.example.coffeepoints.di

import android.content.Context
import com.example.authorization.di.AuthComponent
import com.example.coffeepointslist.di.CoffeePointsComponent
import com.example.coffeepointsmap.di.MapItemsComponent
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
    fun coffeePointsScreenComponentFactory() : CoffeePointsComponent.Factory
    fun coffeePointsMapScreenComponentFactory() : MapItemsComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ) : ApplicationComponent
    }
}