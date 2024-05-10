package com.company.khomasiguard.presentation.venues.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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

@Composable
fun NotActivated(
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
    ) {
        item{
            PlaygroundCard(
                playground = Playground(
                    playgroundInfo = PlaygroundInfo(
                        playground = PlaygroundX(
                            id = 1,
                            name = "ZSC Playground",
                            feesForHour = 50,
                            address = "Nile Street, Zsc District, Cairo.",
                            isBookable = false
                        ),
                        picture = ""
                    ),
                    newBookings = 10,
                    finishedBookings = 23,
                    totalBookings = 33

                ),
                onViewPlaygroundClick = {},
                onClickActive = {},
                onClickDeActive = {}
            )

        }
    }
}


@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NotActivatedPreview() {
    KhomasiGuardTheme {
        NotActivated()
    }
}
