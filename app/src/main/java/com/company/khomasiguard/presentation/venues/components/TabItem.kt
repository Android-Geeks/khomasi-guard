package com.company.khomasiguard.presentation.venues.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.company.khomasiguard.R

sealed class TabItem(
    @StringRes val title: Int,
    val screens: @Composable (
        ) -> Unit,
) {
    data object Activated : TabItem(
        title = R.string.activated,
        screens = {
            Activated()
        }
    )
    data object NotActivated : TabItem(
        title = R.string.not_activated,
        screens = {
          NotActivated()
        }
    )
}

