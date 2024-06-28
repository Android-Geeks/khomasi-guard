package com.company.khomasiguard.presentation.navigation.navigator

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.company.khomasiguard.navigation.Screens
import com.company.khomasiguard.presentation.booking.BookingScreen
import com.company.khomasiguard.presentation.booking.BookingViewModel
import com.company.khomasiguard.presentation.home.HomeScreen
import com.company.khomasiguard.presentation.home.HomeViewModel
import com.company.khomasiguard.presentation.venues.VenuesScreen
import com.company.khomasiguard.presentation.venues.VenuesViewModel


fun NavGraphBuilder.khomasiNavigator(
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
                review = homeViewModel::review,
                cancelBooking = homeViewModel::cancelBooking,
                onLogout = homeViewModel::onLogout,
                onClickDialog = homeViewModel::onClickDialog,
            )

        }
        composable(route = Screens.KhomasiNavigation.Bookings.route) {
            val bookingViewModel: BookingViewModel = hiltViewModel()
            BookingScreen(
                uiStateFlow = bookingViewModel.uiState,
                getBooking = bookingViewModel::getBooking,
                updateSelectedDay = bookingViewModel::updateSelectedDay,
                review = bookingViewModel::review,
                cancelBooking = bookingViewModel::cancelBooking,
                onClickDialog = bookingViewModel::onClickDialog
            )
        }

        composable(route = Screens.KhomasiNavigation.Playgrounds.route) {
            val venuesViewModel: VenuesViewModel = hiltViewModel()
            VenuesScreen(
                getGuardPlaygrounds = venuesViewModel::getGuardPlaygrounds,
                uiState = venuesViewModel.uiState,
                cancel = venuesViewModel::cancel
            )
        }
    }

}