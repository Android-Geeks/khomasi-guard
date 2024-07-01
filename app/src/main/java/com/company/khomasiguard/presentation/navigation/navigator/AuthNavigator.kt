package com.company.khomasiguard.presentation.navigation.navigator

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.company.khomasiguard.navigation.Screens
import com.company.khomasiguard.presentation.login.LoginScreen
import com.company.khomasiguard.presentation.login.LoginViewModel

fun NavGraphBuilder.authNavigator() {
    navigation(
        route = Screens.AuthNavigation.route,
        startDestination = Screens.AuthNavigation.Login.route
    ) {
        composable(route = Screens.AuthNavigation.Login.route) {
            val loginViewModel: LoginViewModel = hiltViewModel()
            LoginScreen(
                loginUiState = loginViewModel.uiState,
                loginState = loginViewModel.loginState,
                updatePassword = loginViewModel::updatePassword,
                updateEmail = loginViewModel::updateEmail,
                login = loginViewModel::login,
                contactUs = loginViewModel::contactUs,
                ourApp = loginViewModel::ourApp
            )

        }

    }
}