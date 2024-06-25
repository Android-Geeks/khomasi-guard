package com.company.khomasiguard.presentation.home

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasiguard.R
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.booking.Booking
import com.company.khomasiguard.domain.model.booking.BookingsResponse
import com.company.khomasiguard.presentation.components.BookingCardDetails
import com.company.khomasiguard.presentation.components.BookingCardStatus
import com.company.khomasiguard.presentation.components.BottomSheetWarning
import com.company.khomasiguard.presentation.components.ShortBookingCard
import com.company.khomasiguard.presentation.components.UserRatingSheet
import com.company.khomasiguard.presentation.components.connectionStates.ThreeBounce
import com.company.khomasiguard.presentation.home.component.EmptyScreen
import com.company.khomasiguard.presentation.home.component.TopCard
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.company.khomasiguard.util.parseTimestamp
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    responseState: StateFlow<DataState<BookingsResponse>>,
    uiStateFlow: StateFlow<HomeUiState>,
    getHomeScreenBooking: () -> Unit,
    review: () -> Unit,
    onClickDialog: (DialogBooking) -> Unit
) {
    val bookingPlaygrounds by responseState.collectAsStateWithLifecycle()
    var showLoading by remember { mutableStateOf(false) }
    var responseData by remember { mutableStateOf(listOf<Booking>()) }
    val uiState = uiStateFlow.collectAsStateWithLifecycle().value

    LaunchedEffect(bookingPlaygrounds) {
        showLoading =
            bookingPlaygrounds is DataState.Loading || bookingPlaygrounds is DataState.Empty
        responseData = if (bookingPlaygrounds is DataState.Success) {
            val successData = bookingPlaygrounds as DataState.Success<BookingsResponse>
            val guardBookings = successData.data.guardBookings
            if (guardBookings.size > 2) {
                guardBookings[2].bookings
            } else {
                emptyList()
            }
        } else {
            emptyList()
        }

        Log.d("HomeScreenState", "HomeScreenState: $bookingPlaygrounds")
    }
//    val date = extractDateFromTimestamp(
//        parseTimestamp(uiState.date.toString()),
//        format = "dd MMMM yyyy"
//    )
    LaunchedEffect(Unit) {
        getHomeScreenBooking()
    }
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(
            MaterialTheme.colorScheme.background
        )
    ) {
        var openDialog by remember { mutableStateOf(false) }
        var isOpen by remember { mutableStateOf(false) }
        var isRate by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState()
        val rateSheetState = rememberModalBottomSheetState()
        TopCard(uiStateFlow)
        if (showLoading) {
            ThreeBounce(
                color = MaterialTheme.colorScheme.primary,
                size = DpSize(75.dp, 75.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
        ) {
            if (uiState.guardBookings.isNotEmpty()) {
                itemsIndexed(uiState.bookings) { _, item ->
                    item.currentBookings.forEach { booking ->
                        ShortBookingCard(
                            bookingDetails = booking,
                            playgroundName = item.playgroundName,
                            onClickViewBooking = {
                                onClickDialog(DialogBooking(item.playgroundName, booking))
                                openDialog = true
                            },
                            onClickCall = {}
                        )
                    }
                }
            } else {
                item { EmptyScreen() }
            }

        }
        if (openDialog) {
            Dialog(onDismissRequest = { openDialog = false }) {
                BookingCardDetails(
                    bookingDetails = uiState.bookingDetails.booking,
                    onClickCall = { },
                    playgroundName = uiState.bookingDetails.playgroundName,
                    onClickCancelBooking = {
                        openDialog = false
                        isOpen = true
                    },
                    status = if (parseTimestamp(uiState.bookingDetails.booking.bookingTime) < LocalDateTime.now()) BookingCardStatus.CANCEL else BookingCardStatus.RATING,
                    toRate = {
                        openDialog = false
                        isRate = true
                    }
                )

            }
        }
        if (isOpen) {
            BottomSheetWarning(
                sheetState = sheetState,
                onDismissRequest = { isOpen = false },
                userName = uiState.bookingDetails.booking.userName,
                onClickCancel = { },
                mainTextId = R.string.confirm_cancel_booking,
                subTextId = R.string.action_will_cancel_booking,
                mainButtonTextId = R.string.cancel_booking,
                subButtonTextId = R.string.back
            )
        }
        if (isRate) {
            UserRatingSheet(
                bookingDetails = uiState.bookingDetails.booking,
                playgroundName = "",
                sheetState = rateSheetState,
                onDismissRequest = { isRate = false },
                onClickButtonRate = {
                    isRate = false
                    review()
                }
            )

        }

    }
}


@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HomePreview() {
    KhomasiGuardTheme {
        val mockViewModel: HomeMockViewModel = viewModel()
        HomeScreen(
            uiStateFlow = mockViewModel.uiState,
            getHomeScreenBooking = {},
            review = mockViewModel::review,
            responseState = mockViewModel.responseState,
            onClickDialog = {}
        )
    }
}