package com.example.coffeepoints.navigation

import com.example.coffeepoints.R

sealed class NavigationItem(
    val screen: Screen,
    val titleId: Int,
    val hasBackButton: Boolean
) {

    object Registration : NavigationItem(
        screen = Screen.Registration,
        titleId = R.string.registration,
        hasBackButton = false,
    )

    object Auth : NavigationItem(
        screen = Screen.LogIn,
        titleId = R.string.log_in,
        hasBackButton = false,
    )

    object CoffeePoints : NavigationItem(
        screen = Screen.CoffeeShopList,
        titleId = R.string.coffee_shops,
        hasBackButton = true,
    )

    object CoffeePointsMap : NavigationItem(
        screen = Screen.CoffeeShopMap,
        titleId = R.string.map,
        hasBackButton = true,
    )

    object CoffeePointsMenu : NavigationItem(
        screen = Screen.CoffeePointMenu,
        titleId = R.string.coffee_shop_menu,
        hasBackButton = true,
    )

    object ConfirmOrder : NavigationItem(
        screen = Screen.CoffeeShopList,
        titleId = R.string.order_confirm,
        hasBackButton = true,
    )

}