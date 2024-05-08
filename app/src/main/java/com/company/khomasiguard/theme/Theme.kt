package com.company.khomasiguard.theme

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.company.khomasiguard.presentation.components.ConnectivityMonitor
import com.company.khomasiguard.presentation.components.GenericDialog
import com.company.khomasiguard.presentation.components.GenericDialogInfo
import java.util.Queue

private val lightThemeColors = lightColorScheme(
    primary = lightPrimary,
    onPrimary = lightBackground,
    secondary = lightSecondary,
    background = lightBackground,
    surface = lightCard,
    surfaceContainer = lightOverlay,
    tertiary = lightSubText,
    outline = lightHint,
    error = lightErrorColor
)

private val darkThemeColors = darkColorScheme(
    primary = darkPrimary,
    onPrimary = darkBackground,
    secondary = darkSecondary,
    background = darkBackground,
    surface = darkCard,
    surfaceContainer = darkOverlay,
    tertiary = darkSubText,
    outline = darkHint,
    error = darkErrorColor
)

data class UIState(
    val loading: Boolean = false,
    val offline: Boolean = false,
    val error: Boolean = false,
    val empty: Boolean = false
)

@Composable
fun KhomasiGuardTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    uiState: UIState = UIState(),
    dialogQueue: Queue<GenericDialogInfo>? = null,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkThemeColors
        else -> lightThemeColors
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            Column {
                if (uiState == UIState(offline = true)) {
                    ConnectivityMonitor()
                }
                content()
            }
            if (uiState == UIState(error = true)) {
                ProcessDialogQueue(dialogQueue = dialogQueue)
            }

        }
    }
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericDialogInfo>?,
) {
    dialogQueue?.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            positiveAction = dialogInfo.positiveAction,
            negativeAction = dialogInfo.negativeAction
        )
    }
}