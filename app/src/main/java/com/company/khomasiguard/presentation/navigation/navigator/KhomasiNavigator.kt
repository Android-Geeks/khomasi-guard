package com.company.khomasiguard.presentation.navigation.navigator

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.company.khomasiguard.navigation.Screens


fun NavGraphBuilder.khomasiNavigator(
    navController: NavHostController
) {
    navigation(
        route = Screens.KhomasiNavigation.route,
        startDestination = Screens.KhomasiNavigation.Home.route
    ) {
        composable(route = Screens.KhomasiNavigation.Home.route) {

        }

        composable(route = Screens.KhomasiNavigation.Bookings.route) {


        }

        composable(route = Screens.KhomasiNavigation.Playgrounds.route) {

        }
    }

}