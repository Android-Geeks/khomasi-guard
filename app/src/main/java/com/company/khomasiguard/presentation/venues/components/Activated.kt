package com.company.khomasiguard.presentation.venues.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasiguard.domain.model.playground.Playground
import com.company.khomasiguard.domain.model.playground.PlaygroundInfo
import com.company.khomasiguard.domain.model.playground.PlaygroundX
import com.company.khomasiguard.presentation.components.PlaygroundCard
import com.company.khomasiguard.theme.KhomasiGuardTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.rememberModalBottomSheetState
import com.company.khomasiguard.R
import com.company.khomasiguard.presentation.components.BottomSheetWarning

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Activated(
) {
    val sheetState = rememberModalBottomSheetState()
    var isOpen by remember { mutableStateOf(false) }
    if (isOpen){
        BottomSheetWarning(
            sheetState = sheetState,
            onDismissRequest = { isOpen=false },
            userName = "------",
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
        item {
            PlaygroundCard(
                playground = Playground(
                    playgroundInfo = PlaygroundInfo(
                        playground = PlaygroundX(
                            id = 1,
                            name = "ZSC Playground",
                            feesForHour = 50,
                            address = "Nile Street, Zsc District, Cairo.",
                            isBookable = true
                        ),
                        picture = ""
                    ),
                    newBookings = 10,
                    finishedBookings = 23,
                    totalBookings = 33

                ),
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
        Activated()
    }
}
