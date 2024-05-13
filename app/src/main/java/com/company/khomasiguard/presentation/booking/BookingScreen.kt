package com.company.khomasiguard.presentation.booking

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasiguard.R
import com.company.khomasiguard.presentation.components.ShortBookingCard
import com.company.khomasiguard.presentation.components.SortBookingsBottomSheet
import com.company.khomasiguard.theme.KhomasiGuardTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BookingScreen(
    uiStateFlow: StateFlow<BookingUiState>,
    getBooking: () -> Unit,
){
    LaunchedEffect(Unit) {
        getBooking()
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = { TopBar() },
    ){
        var openDialog by remember { mutableStateOf(false) }
        val uiState = uiStateFlow.collectAsStateWithLifecycle().value
        LazyColumn(
            contentPadding = it,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 8.dp, start = 16.dp,end = 16.dp)
        ) {
            itemsIndexed(uiState.bookingList) { _,item ->
                ShortBookingCard(
                    bookingDetails = item,
                    playgroundName = "playgroundName",
                    onClickViewBooking = {
                        item.bookingNumber
                        openDialog = true
                    },
                    onClickCall = {}
                )
            }

        }
    }

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    Column {
        val sheetState = rememberModalBottomSheetState()
        var isOpen by remember { mutableStateOf(false) }

        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.bookings),
                    style = MaterialTheme.typography.displayMedium,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.CenterStart)
                        .padding(start = 16.dp)
                )
            },
            actions = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.sortascending),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.clickable {
                            isOpen = true
                        }

                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(), thickness = 1.dp
        )
//        if (isOpen){
//            SortBookingsBottomSheet(
//                sheetState = sheetState,
//                onDismissRequest = { isOpen = false },
//                choice = ,
//                onChoiceChange =
//            )

        }

    }

@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BookingScreenPreview() {
    KhomasiGuardTheme {
        val mockViewModel:MockBookingViewModel= viewModel()
       BookingScreen(
           uiStateFlow = mockViewModel.uiState,
           getBooking = mockViewModel::getBooking
       )
    }
}