package com.company.khomasiguard.presentation.venues.components

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasiguard.R
import com.company.khomasiguard.presentation.components.BottomSheetWarning
import com.company.khomasiguard.presentation.components.PlaygroundCard
import com.company.khomasiguard.presentation.components.connectionStates.ThreeBounce
import com.company.khomasiguard.presentation.venues.MockVenuesViewModel
import com.company.khomasiguard.presentation.venues.VenuesUiState
import com.company.khomasiguard.theme.KhomasiGuardTheme
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Activated(
    uiState: StateFlow<VenuesUiState>,
    cancel: (Int,Boolean) -> Unit
) {
    val state = uiState.collectAsStateWithLifecycle().value
    val sheetState = rememberModalBottomSheetState()
    var isOpen by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var selectedPlaygroundId by remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(state) {
        state.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    if (state.isLoading) {
        ThreeBounce(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
           items(state.activated, key = {it.playgroundInfo.playground.id}) { playground ->
                PlaygroundCard(
                    playground = playground,
                    onViewPlaygroundClick = {},
                    onClickActive = {},
                    onClickDeActive = {
                        isOpen = true
                        selectedPlaygroundId = playground.playgroundInfo.playground.id
                    },
                    modifier = Modifier.animateItemPlacement(
                        animationSpec = TweenSpec(
                            durationMillis = 300,
                            easing = FastOutLinearInEasing
                        )
                    ),
                )
            }
        }
    }

    if (isOpen&& selectedPlaygroundId != null) {
        BottomSheetWarning(
            sheetState = sheetState,
            onDismissRequest = { isOpen = false },
            userName = state.activated.firstOrNull { it.playgroundInfo.playground.id == selectedPlaygroundId }?.playgroundInfo?.playground?.name.orEmpty(),
            onClickCancel = {
                cancel(
                    selectedPlaygroundId!!,false
                )
                isOpen = false

            },
            mainTextId = R.string.confirm_deactivate_playground,
            subTextId = R.string.deactivate_confirmation_message,
            mainButtonTextId = R.string.deactivate,
            subButtonTextId = R.string.back
        )
    }
}

@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActivatedPreview() {
    KhomasiGuardTheme {
        val mockViewModel: MockVenuesViewModel = viewModel()
        Activated(
            uiState = mockViewModel.uiState,
            cancel = mockViewModel::cancel
        )
    }
}
