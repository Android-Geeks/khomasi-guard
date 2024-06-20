package com.company.khomasiguard.presentation.venues.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.company.khomasiguard.R
import kotlinx.coroutines.flow.StateFlow

sealed class TabItem(
    @StringRes val title: Int,
    val screens: @Composable (
        uiState: StateFlow<VenuesUiState>,
        cancel:(Int)->Unit
        ) -> Unit,
) {
    data object Activated : TabItem(
        title = R.string.activated,
        screens = {uiState,cancel->
            Activated(uiState,cancel )
        }
    )
    data object NotActivated : TabItem(
        title = R.string.not_activated,
        screens = {uiState,_ ->
          NotActivated(uiState)
        }
    )
}

