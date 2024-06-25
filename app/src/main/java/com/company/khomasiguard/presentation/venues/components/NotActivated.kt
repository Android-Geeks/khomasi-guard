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
import androidx.compose.material3.MaterialTheme
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
import com.company.khomasiguard.presentation.components.PlaygroundCard
import com.company.khomasiguard.presentation.components.connectionStates.ThreeBounce
import com.company.khomasiguard.presentation.venues.MockVenuesViewModel
import com.company.khomasiguard.presentation.venues.VenuesUiState
import com.company.khomasiguard.theme.KhomasiGuardTheme
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NotActivated(
    uiState: StateFlow<VenuesUiState>,
    cancel: (Int,Boolean) -> Unit

) {
    val state = uiState.collectAsStateWithLifecycle().value
    var showLoading by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var selectedPlaygroundId by remember { mutableStateOf<Int?>(null) }
    LaunchedEffect(state) {
        showLoading = state.isLoading
        state.errorMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    if (showLoading) {
        ThreeBounce(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp),
        ) {
           items(state.notActivated, key = {it.playgroundInfo.playground.id}) { playground ->
                PlaygroundCard(
                    playground = playground,
                    onViewPlaygroundClick = {},
                    onClickActive = {
                        selectedPlaygroundId = playground.playgroundInfo.playground.id
                        cancel(selectedPlaygroundId!!,true)

                    },
                    onClickDeActive = {},
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
}

@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NotActivatedPreview() {
    KhomasiGuardTheme {
        val mockViewModel: MockVenuesViewModel = viewModel()

        NotActivated(
            uiState = mockViewModel.uiState,
            cancel = mockViewModel::cancel
        )
    }
}
