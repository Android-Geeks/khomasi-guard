package com.company.khomasiguard.navigation


sealed class Screens(
    val route: String,
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