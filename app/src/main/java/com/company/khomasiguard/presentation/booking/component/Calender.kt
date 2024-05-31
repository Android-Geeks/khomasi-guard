package com.company.khomasiguard.presentation.booking.component

import android.util.DisplayMetrics
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasiguard.presentation.booking.BookingUiState
import com.company.khomasiguard.presentation.booking.BookingViewModel
import com.company.khomasiguard.presentation.components.ShortBookingCard
import kotlinx.coroutines.flow.StateFlow
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

@Composable
fun Header(data: CalendarUiModel) {
    Row {
        Text(
            // show "Today" if user selects today's date
            // else, show the full format of the date
            text = if (data.selectedDate.isToday) {
                "Today"
            } else {
                data.selectedDate.date.format(
                    DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                )
            },
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun Content(
    uiStateFlow: StateFlow<BookingUiState>,
    data: CalendarUiModel,
    // callback should be registered from outside
    onDateClickListener: (CalendarUiModel.Date) -> Unit,
    onView: @Composable (BookingViewModel) -> Unit
) {
    val screenWidth = getScreenWidth()
    val uiState by uiStateFlow.collectAsStateWithLifecycle()

    LazyRow(
        contentPadding = PaddingValues(
        start = (screenWidth / 2).dp - 30.dp,
        end = (screenWidth / 2).dp - 30.dp
    ),
        modifier = Modifier.padding(16.dp)
    ) {
        items(items = data.visibleDates) { date ->
            ContentItem(
                date = date,
                onDateClickListener,
                onView ={
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    ) {
                        itemsIndexed(uiState.bookingList) { _, item ->
                            ShortBookingCard(
                                bookingDetails = item,
                                playgroundName = "playgroundName",
                                onClickViewBooking = {},
                                onClickCall = {}
                            )
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun getScreenWidth(): Float {
    val displayMetrics: DisplayMetrics =
        LocalContext.current.resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}

@Composable
fun ContentItem(
    date: CalendarUiModel.Date,
    onClickListener: (CalendarUiModel.Date) -> Unit, // still, callback should be registered from outside
    onView : @Composable (CalendarUiModel.Date)->Unit
     )
    {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 4.dp)
            .width(60.dp)
            .height(74.dp)
            .background(shape = MaterialTheme.shapes.small, color = Color.Transparent)
            .clickable {
                   onClickListener(date)
                onView
            },
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        //  elevation = CardDefaults.cardElevation(if (page == pagerState.currentPage) 1.dp else (0).dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = date.date.toString(),
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyLarge,
                )
            Text(
                text = date.day,
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,            )
        }
    }
}
