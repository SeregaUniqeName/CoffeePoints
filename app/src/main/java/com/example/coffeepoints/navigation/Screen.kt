package com.example.coffeepoints.navigation

import com.example.coffeepoints.R
import com.google.gson.Gson

sealed class Screen(
    val route: String,
    val titleId: Int,
    val hasBackButton: Boolean,
) {

    data object Registration : Screen(
        route = NAV_REGISTRATION,
        titleId = R.string.registration,
        hasBackButton = false,
    )
    data object LogIn : Screen(
        route =NAV_LOGIN,
        titleId = R.string.log_in,
        hasBackButton = false,
    )
    data object CoffeeShopList : Screen(
        route = NAV_COFFEE_SHOP_LIST,
        titleId = R.string.coffee_shops,
        hasBackButton = true,
    )
    data object CoffeeShopMap : Screen(
        route = NAV_COFFEE_SHOP_MAP,
        titleId = R.string.map,
        hasBackButton = true,
    )
    data object CoffeePointMenu : Screen(
        route = NAV_COFFEE_POINT_MENU,
        titleId = R.string.coffee_shop_menu,
        hasBackButton = true,
    ) {

        private const val ROUTE_FOR_ARGS = "coffeePointMenu"

        fun getRouteWithArgs(id: Int): String {
            val itemJson = Gson().toJson(id)
            return "$ROUTE_FOR_ARGS/${itemJson}"
        }
    }

    data object CoffeePointConfirm : Screen(
        route = NAV_COFFEE_POINT_CONFIRM,
        titleId = R.string.order_confirm,
        hasBackButton = true,
    ) {

        private const val ROUTE_FOR_ARGS = "coffeePointConfirm"

        fun getRouteWithArgs(id: Int): String {
            val itemJson = Gson().toJson(id)
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

        const val NAV_COFFEE_POINT_CONFIRM = "coffeePointConfirm/{$KEY_COFFEE_POINT}"

    }
}