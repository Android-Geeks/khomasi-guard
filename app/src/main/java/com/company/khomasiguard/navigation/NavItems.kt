package com.company.khomasiguard.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.company.app.R

data class NavItem(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val route: String
)

val listOfNavItems = listOf(
    NavItem(
        label = R.string.home,
        icon = R.drawable.housesimple,
        route = Screens.KhomasiNavigation.Home.route
    ),
    NavItem(
        label = R.string.bookings,
        icon = R.drawable.ticket,
        route = Screens.KhomasiNavigation.Bookings.route
    ),
    NavItem(
        label = R.string.playgrounds,
        icon = R.drawable.soccerball,
        route = Screens.KhomasiNavigation.Playgrounds.route
    )
)