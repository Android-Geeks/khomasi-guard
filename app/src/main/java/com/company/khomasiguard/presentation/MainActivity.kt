package com.company.khomasiguard.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasiguard.presentation.navigation.MyApp
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.company.khomasiguard.util.ConnectivityObserver
import com.company.khomasiguard.util.NetworkConnectivityObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var connectivityObserver: ConnectivityObserver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition(condition = { mainViewModel.splashCondition.value })
        enableEdgeToEdge()
        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val startDestination by mainViewModel.startDestination.collectAsStateWithLifecycle()
            val isNetworkAvailable by connectivityObserver.observe().collectAsStateWithLifecycle(
                initialValue = ConnectivityObserver.Status.Available
            )
            KhomasiGuardTheme {
                Surface {
                    Log.d("ActivityMain", startDestination)
                    MyApp(
                        startDestination = startDestination,
                        isNetworkAvailable = isNetworkAvailable
                    )
                }
            }
        }
    }
}