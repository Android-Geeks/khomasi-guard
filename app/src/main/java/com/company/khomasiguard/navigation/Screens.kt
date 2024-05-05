package com.company.khomasiguard.navigation

import androidx.navigation.NamedNavArgument

sealed class Screens(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    data object AuthNavigation : Screens("AuthNavigation") {
        data object Login : Screens("Login")
    }

    data object KhomasiNavigation : Screens("KhomasiNavigation") {
        data object Home : Screens("Home")
        data object Bookings : Screens("Bookings")
        data object Playgrounds : Screens("Playgrounds")

    }
}