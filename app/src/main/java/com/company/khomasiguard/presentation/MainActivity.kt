package com.company.khomasiguard.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.company.khomasiguard.presentation.navigation.MyApp
import com.company.khomasiguard.util.ConnectivityManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().apply {
            setKeepOnScreenCondition(condition = { mainViewModel.splashCondition.value })
        }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            if (!mainViewModel.splashCondition.value) {
                MyApp(
                    mainViewModel.startDestination.value,
                    connectivityManager.isNetworkAvailable
                )
            }
        }
    }
}
