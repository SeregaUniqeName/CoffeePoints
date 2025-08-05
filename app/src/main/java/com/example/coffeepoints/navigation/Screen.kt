package com.example.coffeepoints.navigation

import com.example.coffeepointslist.models.CoffeePointEntity
import com.google.gson.Gson

sealed class Screen(
    val route: String
) {

    data object Registration : Screen(NAV_REGISTRATION)
    data object LogIn : Screen(NAV_LOGIN)
    data object CoffeeShopList : Screen(NAV_COFFEE_SHOP_LIST)
    data object CoffeeShopMap : Screen(NAV_COFFEE_SHOP_MAP)
    data object CoffeePointMenu : Screen(NAV_COFFEE_POINT_MENU) {

        private const val ROUTE_FOR_ARGS = "coffeePointMenu"

        fun getRouteWithArgs(item: CoffeePointEntity): String {
            val itemJson = Gson().toJson(item)
            return "$ROUTE_FOR_ARGS/${itemJson}"
        }
    }

    companion object {

        const val KEY_COFFEE_POINT = "keyCoffeePoint"

        const val NAV_REGISTRATION = "registration"
        const val NAV_LOGIN = "logIn"
        const val NAV_COFFEE_SHOP_LIST = "coffeeShopList"
        const val NAV_COFFEE_SHOP_MAP = "coffeeShopMap"
        const val NAV_COFFEE_POINT_MENU = "coffeePointMenu/{$KEY_COFFEE_POINT}"

    }
}