package com.company.khomasiguard.presentation.navigation

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.company.khomasiguard.navigation.Screens
import com.company.khomasiguard.navigation.listOfNavItems
import com.company.khomasiguard.presentation.components.connectionStates.LossConnection
import com.company.khomasiguard.presentation.navigation.components.BottomNavigationBar
import com.company.khomasiguard.presentation.navigation.navigator.authNavigator
import com.company.khomasiguard.presentation.navigation.navigator.khomasiNavigator
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.company.khomasiguard.util.ConnectivityObserver

@Composable
fun MyApp(
    startDestination: String,
    isNetworkAvailable: ConnectivityObserver.Status
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Log.d("MyApp", "startDestination: $startDestination")

    if (isNetworkAvailable == ConnectivityObserver.Status.Unavailable ||
        isNetworkAvailable == ConnectivityObserver.Status.Lost
    ) {
        LossConnection()
        return
    }
    Scaffold(
        bottomBar =
        {
            BottomNavigationBar(
                navController = navController,
                bottomBarState = navBackStackEntry?.destination?.route
                        in listOfNavItems.map { it.route }
            )
        }
    )
    { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {

            authNavigator(navController)
            khomasiNavigator(navController)
        }
    }
}
@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    KhomasiGuardTheme {
        MyApp(
            startDestination = Screens.KhomasiNavigation.route,
            isNetworkAvailable = ConnectivityObserver.Status.Unavailable
        )
    }
}