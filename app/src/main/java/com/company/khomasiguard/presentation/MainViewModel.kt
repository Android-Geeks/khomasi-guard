package com.company.khomasiguard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.company.khomasiguard.domain.use_case.app_entry.AppEntryUseCases
import com.company.khomasiguard.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    private val _startDestination = MutableStateFlow(Screens.KhomasiNavigation.route)
    val startDestination: StateFlow<String> = _startDestination

    init {
        appEntryUseCases.readAppEntry().onEach { startingRoute ->
            when (startingRoute) {
                Screens.KhomasiNavigation -> _startDestination.value =
                    Screens.KhomasiNavigation.route
                Screens.AuthNavigation -> _startDestination.value = Screens.AuthNavigation.route
                else -> {
                    _startDestination.value = Screens.KhomasiNavigation.route}
            }
            delay(200)
        }.launchIn(viewModelScope)
    }
}