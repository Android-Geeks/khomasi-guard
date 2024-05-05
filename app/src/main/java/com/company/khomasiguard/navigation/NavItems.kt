package com.company.khomasiguard.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.company.app.R

data class NavItem(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val route: String
)

//val listOfNavItems = listOf(
//
//)