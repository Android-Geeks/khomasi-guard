package com.company.khomasiguard.presentation.booking

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasiguard.R
import com.company.khomasiguard.presentation.booking.component.CalendarDataSource
import com.company.khomasiguard.presentation.booking.component.Content
import com.company.khomasiguard.presentation.booking.component.Header
import com.company.khomasiguard.theme.KhomasiGuardTheme
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BookingScreen(
    uiStateFlow: StateFlow<BookingUiState>,
    getBooking: (String) -> Unit,
) {
    val uiState by uiStateFlow.collectAsStateWithLifecycle()
    val dataSource = CalendarDataSource()
    // we use `mutableStateOf` and `remember` inside composable function to schedules recomposition
    var calendarUiModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = { TopBar() },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Header(
                data = calendarUiModel,
            )
            Content(
                uiStateFlow =uiStateFlow ,
                data = calendarUiModel,
                onDateClickListener = { date ->
                    // refresh the CalendarUiModel with new data
                    // by changing only the `selectedDate` with the date selected by User
                    calendarUiModel = calendarUiModel.copy(
                        selectedDate = date,
                        visibleDates = calendarUiModel.visibleDates.map {
                            it.copy(
                                isSelected = it.date.isEqual(date.date)
                            )
                        }
                    )
                },
                onView = {
                    LaunchedEffect(Unit) {
                        getBooking(calendarUiModel.selectedDate.toString())
                    }
                    },
        )
    }
}

}
//                onView = {
//                    LaunchedEffect(Unit) {
//                        getBooking(calendarUiModel.selectedDate.toString())
//                    }
//                    LazyColumn(
//                        verticalArrangement = Arrangement.spacedBy(16.dp),
//                        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
//                    ) {
//                        itemsIndexed(uiState.bookingList) { _, item ->
//                            ShortBookingCard(
//                                bookingDetails = item,
//                                playgroundName = "playgroundName",
//                                onClickViewBooking = {},
//                                onClickCall = {}
//                            )
//                        }
//                    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
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
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.sortascending),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
    )
    HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
}

@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun BookingScreenPreview() {
    KhomasiGuardTheme {
        val mockViewModel: MockBookingViewModel = viewModel()
        BookingScreen(
            uiStateFlow = mockViewModel.uiState,
            getBooking = mockViewModel::getBooking
        )
    }
}
