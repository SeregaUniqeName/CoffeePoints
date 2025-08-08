package com.example.coffeepoints.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.coffeepointslist.models.CoffeePointEntity
import com.example.coffeepointsmap.models.MapEntity

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(route: String) {
        navHostController.navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToMenu(id: Int) {
        navHostController.navigate(Screen.CoffeePointMenu.getRouteWithArgs(id))
    }

    fun navigateOnTokenExpire(route: String) {
        navHostController.popBackStack(route = route, inclusive = false)
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
) : NavigationState {
    return remember {
        NavigationState(navHostController)
    }
}