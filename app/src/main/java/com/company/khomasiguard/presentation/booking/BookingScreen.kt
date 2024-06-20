//package com.company.khomasiguard.presentation.booking
//
//import android.content.res.Configuration
//import android.util.Log
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.itemsIndexed
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.company.khomasiguard.R
//import com.company.khomasiguard.presentation.booking.component.CalendarDataSource
//import com.company.khomasiguard.presentation.booking.component.Content
//import com.company.khomasiguard.presentation.booking.component.Header
//import com.company.khomasiguard.presentation.components.ShortBookingCard
//import com.company.khomasiguard.theme.KhomasiGuardTheme
//import kotlinx.coroutines.flow.StateFlow
//import androidx.compose.runtime.LaunchedEffect
//
//
//@Composable
//fun BookingScreen(
//    uiStateFlow: StateFlow<BookingUiState>,
//    getBooking: (String) -> Unit,
//) {
//    val uiState by uiStateFlow.collectAsStateWithLifecycle()
//    val dataSource = CalendarDataSource()
//    // we use `mutableStateOf` and `remember` inside composable function to schedules recomposition
//    var calendarUiModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }
//    LaunchedEffect(calendarUiModel.selectedDate) {
//        Log.d("BookingScreen", "LaunchedEffect triggered with date: ${calendarUiModel.selectedDate}")
//        try {
//            getBooking(calendarUiModel.selectedDate.toString())
//        } catch (e: Exception) {
//            Log.e("BookingScreen", "Error in getBooking: ${e.message}", e)
//        }
//    }
//    Scaffold(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(MaterialTheme.colorScheme.background),
//        topBar = { TopBar() },
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(MaterialTheme.colorScheme.background)
//                .padding(paddingValues)
//        ) {
//            Header(
//                data = calendarUiModel,
//            )
//            Content(
//                uiStateFlow =uiStateFlow ,
//                data = calendarUiModel,
//                onDateClickListener = { date ->
//                    // refresh the CalendarUiModel with new data
//                    // by changing only the `selectedDate` with the date selected by User
//                    calendarUiModel = calendarUiModel.copy(
//                        selectedDate = date,
//                        visibleDates = calendarUiModel.visibleDates.map {
//                            it.copy(
//                                isSelected = it.date.isEqual(date.date)
//                            )
//                        }
//                    )
//                },
//
//        )
//
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(16.dp),
//                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
//            ) {
//                itemsIndexed(uiState.bookingList) { _, item ->
//                    ShortBookingCard(
//                        bookingDetails = item,
//                        playgroundName = "playgroundName",
//                        onClickViewBooking = {},
//                        onClickCall = {}
//                    )
//                }
//            }
//    }
//}
//
//}
//
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopBar() {
//    TopAppBar(
//        title = {
//            Text(
//                text = stringResource(R.string.bookings),
//                style = MaterialTheme.typography.displayMedium,
//                modifier = Modifier
//                    .fillMaxSize()
//                    .wrapContentSize(Alignment.CenterStart)
//                    .padding(start = 16.dp)
//            )
//        },
//        actions = {
//            IconButton(onClick = {}) {
//                Icon(
//                    painter = painterResource(id = R.drawable.sortascending),
//                    contentDescription = null,
//                    tint = MaterialTheme.colorScheme.onSurfaceVariant
//                )
//            }
//        },
//        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.background)
//    )
//    HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
//}
//
//@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//fun BookingScreenPreview() {
//    KhomasiGuardTheme {
//        val mockViewModel: MockBookingViewModel = viewModel()
//        BookingScreen(
//            uiStateFlow = mockViewModel.uiState,
//            getBooking = mockViewModel::getBooking
//        )
//    }
//}




package com.company.khomasiguard.presentation.booking

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
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
import com.company.khomasiguard.presentation.booking.component.CalendarDataSource
import com.company.khomasiguard.presentation.booking.component.Content
import com.company.khomasiguard.presentation.booking.component.Header
import com.company.khomasiguard.presentation.components.ShortBookingCard
import com.company.khomasiguard.theme.KhomasiGuardTheme
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun BookingScreen(
    uiStateFlow: StateFlow<BookingUiState>,
    getBooking: (String) -> Unit,
) {
    val uiState by uiStateFlow.collectAsStateWithLifecycle()
    val dataSource = CalendarDataSource()
    var calendarUiModel by remember { mutableStateOf(dataSource.getData(lastSelectedDate = dataSource.today)) }

    LaunchedEffect(calendarUiModel.selectedDate.date) {
        Log.d("BookingScreen", "LaunchedEffect triggered with date: ${calendarUiModel.selectedDate.date}")
        try {
            val selectedDateString = calendarUiModel.selectedDate.date.format(DateTimeFormatter.ISO_LOCAL_DATE)
            Log.d("BookingScreen", "Calling getBooking with date: $selectedDateString")
            getBooking(selectedDateString)
        } catch (e: Exception) {
            Log.e("BookingScreen", "Error in getBooking: ${e.message}", e)
        }
    }

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
            Header(data = calendarUiModel)
            Content(uiStateFlow = uiStateFlow, date = calendarUiModel ,
            onDateClickListener = { date ->
                calendarUiModel = calendarUiModel.copy(
                    selectedDate = date,
                    visibleDates = calendarUiModel.visibleDates.map {
                        it.copy(
                            isSelected = it.date.isEqual(date.date)
                        )
                    }
                )
            })
            HorizontalDivider(
                thickness = 0.5.dp,
                modifier = Modifier.padding(top = 9.dp)
            )
            Text(
                text = stringResource(R.string.bookings_today),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top=22.dp, start = 16.dp, bottom = 12.dp)
                )
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                if (uiState.guardBookings.isNotEmpty()) {
                    itemsIndexed(uiState.guardBookings) { _, item ->
                        item.bookings.forEach { booking ->
                            ShortBookingCard(
                                bookingDetails = booking,
                                playgroundName = item.playgroundName,
                                onClickViewBooking = {
                                    booking.bookingNumber
                                },
                                onClickCall = {}
                            )
                        }
                    }
                }
            }
        }
    }
}
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
