package com.company.khomasiguard.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.company.khomasiguard.navigation.Screens
import com.company.khomasiguard.presentation.components.connectionStates.LossConnection
import com.company.khomasiguard.presentation.login.TestDataStoreViewModel
import com.company.khomasiguard.theme.KhomasiGuardTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MyApp(
    startDestination: String,
    networkAvailable: MutableStateFlow<Boolean>
) {
    Log.d("MyApp", "startDestination: $startDestination")
    val isNetworkAvailable by networkAvailable.collectAsStateWithLifecycle()
    KhomasiGuardTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable(Screens.KhomasiNavigation.route) {
                    if (isNetworkAvailable) {
                       Text(text = "Connected",
                           modifier = Modifier.padding(16.dp))
                    } else {
                        LossConnection {
                            Log.d("MyApp", "not connected")
                        }
                    }
                }
                composable(Screens.AuthNavigation.route) {
                    val testDataStoreViewModel: TestDataStoreViewModel = hiltViewModel()
                }
            }
        }
    }
}