package com.company.khomasiguard.presentation.navigation.navigator

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.company.khomasiguard.navigation.Screens
import com.company.khomasiguard.presentation.home.HomeScreen
import com.company.khomasiguard.presentation.home.HomeViewModel


fun NavGraphBuilder.khomasiNavigator(
    navController: NavHostController
) {
    navigation(
        route = Screens.KhomasiNavigation.route,
        startDestination = Screens.KhomasiNavigation.Home.route
    ) {
        composable(route = Screens.KhomasiNavigation.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                uiStateFlow = homeViewModel.uiState,
                getHomeScreenBooking = homeViewModel::getHomeScreenBooking,
                review = homeViewModel::review
            )

        }

        composable(route = Screens.KhomasiNavigation.Bookings.route) {


        }

        composable(route = Screens.KhomasiNavigation.Playgrounds.route) {

        }
    }

}