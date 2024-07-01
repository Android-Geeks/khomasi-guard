package com.company.khomasiguard.presentation.home

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasiguard.R
import com.company.khomasiguard.domain.DataState
import com.company.khomasiguard.domain.model.MessageResponse
import com.company.khomasiguard.presentation.components.BookingCardDetails
import com.company.khomasiguard.presentation.components.BookingCardStatus
import com.company.khomasiguard.presentation.components.BottomSheetWarning
import com.company.khomasiguard.presentation.components.ShortBookingCard
import com.company.khomasiguard.presentation.components.UserRatingSheet
import com.company.khomasiguard.presentation.components.connectionStates.ThreeBounce
import com.company.khomasiguard.presentation.home.component.EmptyScreen
import com.company.khomasiguard.presentation.home.component.TopCard
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.company.khomasiguard.util.navigateToCall
import com.company.khomasiguard.util.parseTimestamp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiStateFlow: StateFlow<HomeUiState>,
    ratingStatus: StateFlow<DataState<MessageResponse>>,
    cancelStatus: StateFlow<DataState<MessageResponse>>,
    getHomeScreenBooking: () -> Unit,
    onClickDialog: (Bookings) -> Unit,
    review: (String) -> Unit,
    onRateChange: (Int) -> Unit,
    cancelBooking: (Int) -> Unit,
    onLogout: () -> Unit,
    clearStates: () -> Unit
) {
    val uiState by uiStateFlow.collectAsStateWithLifecycle()
    val ratingState by ratingStatus.collectAsStateWithLifecycle()
    val cancelState by cancelStatus.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        getHomeScreenBooking()
    }
    LaunchedEffect(cancelState) {
        when (val cancel = cancelState) {
            is DataState.Success -> {
                Toast.makeText(context, cancel.data.message, Toast.LENGTH_SHORT).show()
            }
            is DataState.Error -> {
                Toast.makeText(context, cancel.message, Toast.LENGTH_SHORT).show()
            }
            DataState.Empty -> {}
            DataState.Loading -> {}
        }
        delay(1000)
        clearStates()
    }
    LaunchedEffect(ratingState) {
        when (val rating = ratingState) {
            is DataState.Success -> {
                Toast.makeText(context, rating.data.message, Toast.LENGTH_SHORT).show()
            }
            is DataState.Error -> {
                Toast.makeText(context, rating.message, Toast.LENGTH_SHORT).show()
            }
            DataState.Empty -> {}
            DataState.Loading -> {}
        }
        delay(1000)
        clearStates()
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

        TopCard(uiState, onLogout)

        if (uiState.isLoading) {
            ThreeBounce(modifier = Modifier.fillMaxSize())
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                if (uiState.bookings.isNotEmpty()) {
                    items(uiState.bookings, key = { it.bookingDetails.bookingNumber }) { item ->
                        ShortBookingCard(
                            bookingDetails = item.bookingDetails,
                            playgroundName = item.playgroundName,
                            onClickViewBooking = {
                                onClickDialog(
                                    Bookings(
                                        item.playgroundName,
                                        item.bookingDetails
                                    )
                                )
                                openDialog = true
                            },
                            onClickCall = {
                                context.navigateToCall(uiState.dialogDetails.bookingDetails.phoneNumber)
                            }
                        )

                    }
                } else {
                    item { EmptyScreen() }
                }
            }
            if (openDialog) {
                Dialog(onDismissRequest = { openDialog = false }) {
                    BookingCardDetails(
                        bookingDetails = uiState.dialogDetails.bookingDetails,
                        onClickCall = { context.navigateToCall(uiState.dialogDetails.bookingDetails.phoneNumber) },
                        playgroundName = uiState.dialogDetails.playgroundName,
                        onClickCancelBooking = {
                            openDialog = false
                            isOpen = true
                        },
                        status = if (parseTimestamp(uiState.dialogDetails.bookingDetails.bookingTime) > LocalDateTime.now()) BookingCardStatus.CANCEL else BookingCardStatus.RATING,
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
                    userName = uiState.dialogDetails.bookingDetails.userName,
                    onClickCancel = {
                        isOpen = false
                        cancelBooking(uiState.dialogDetails.bookingDetails.bookingNumber)
                    },
                    mainTextId = R.string.confirm_cancel_booking,
                    subTextId = R.string.action_will_cancel_booking,
                    mainButtonTextId = R.string.cancel_booking,
                    subButtonTextId = R.string.back
                )
            }
            if (isRate) {
                UserRatingSheet(
                    rate = uiState.ratingValue,
                    onRateChange = onRateChange,
                    bookingDetails = uiState.dialogDetails.bookingDetails,
                    playgroundName = uiState.dialogDetails.playgroundName,
                    sheetState = rateSheetState,
                    onDismissRequest = { isRate = false },
                    onClickButtonRate = {
                        isRate = false
                        review(uiState.dialogDetails.bookingDetails.email)
                    }
                )
            }
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
            ratingStatus = mockViewModel.cancelState,
            cancelStatus = mockViewModel.cancelState,
            getHomeScreenBooking = {},
            onClickDialog = {},
            review = {},
            onRateChange = {},
            cancelBooking = {},
            onLogout = {},
            clearStates = {}
        )
    }
}