package com.company.khomasiguard.presentation.venues.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasiguard.presentation.components.PlaygroundCard
import com.company.khomasiguard.theme.KhomasiGuardTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasiguard.R
import com.company.khomasiguard.presentation.components.BottomSheetWarning
import com.company.khomasiguard.presentation.venues.MockVenuesViewModel
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Activated(
    uiState: StateFlow<VenuesUiState>,
) {
    val state = uiState.collectAsStateWithLifecycle().value
    val sheetState = rememberModalBottomSheetState()
    var isOpen by remember { mutableStateOf(false) }
    if (isOpen){
        BottomSheetWarning(
            sheetState = sheetState,
            onDismissRequest = { isOpen=false },
            userName = state.playgroundName,
            onClickCancel = { },
            mainTextId = R.string.confirm_deactivate_playground,
            subTextId = R.string.deactivate_confirmation_message,
            mainButtonTextId =R.string.deactivate ,
            subButtonTextId = R.string.back
        )
    }
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        itemsIndexed(state.activated) {_ ,playground ->
            PlaygroundCard(
                playground = playground,
                onViewPlaygroundClick = {},
                onClickActive = {},
                onClickDeActive = {isOpen=true}
            )

        }
    }
}


@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActivatedPreview() {
    KhomasiGuardTheme {
        val mockViewModel: MockVenuesViewModel = viewModel()

        Activated(
            uiState = mockViewModel.uiState
        )
    }
}
