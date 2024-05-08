package com.company.khomasiguard.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.company.khomasiguard.R
import com.company.khomasiguard.theme.KhomasiGuardTheme

enum class SelectedFilter {
    TOP_RATING,
    BOOKING_FIRST;
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortBookingsBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    choice: Int,
    onChoiceChange: (Int) -> Unit,
    onSaveClicked: (SelectedFilter) -> Unit,

    ) {
    MyModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest
    ) {
        SortBookingsContent(
            choice = choice,
            onChoiceChange = onChoiceChange,
            onSaveClicked = onSaveClicked
        )
    }
}

@Composable
fun SortBookingsContent(
    choice: Int,
    onChoiceChange: (Int) -> Unit,
    onSaveClicked: (SelectedFilter) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val choices = listOf(
        stringResource(id = R.string.top_rating),
        stringResource(id = R.string.booking_first),
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {

        SelectedFilter.entries.forEach { filter ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        onChoiceChange(filter.ordinal)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = filter.ordinal == choice,
                    onClick = {
                        onChoiceChange(filter.ordinal)
                    }
                )
                Text(
                    text = choices[filter.ordinal],
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        MyButton(
            text = R.string.save,
            onClick = {
                onSaveClicked(SelectedFilter.entries[choice])
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RBSPreview() {
    KhomasiGuardTheme {
        var choice by remember { mutableIntStateOf(0) }
        SortBookingsBottomSheet(
            sheetState = rememberStandardBottomSheetState(),
            choice = choice,
            onChoiceChange = { choice = it },
            onSaveClicked = {},
            onDismissRequest = {}
        )
    }
}