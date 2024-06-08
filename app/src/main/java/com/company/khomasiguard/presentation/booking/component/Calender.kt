package com.company.khomasiguard.presentation.booking.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.company.khomasiguard.presentation.booking.BookingUiState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import org.threeten.bp.format.TextStyle
import java.util.Locale


@Composable
fun Header(data: CalendarUiModel) {
    Row {
        val currentDate = LocalDate.now()

        val currentDaysList = remember {
            (0..20).map { day -> (currentDate).plusDays(day.toLong()) }
        }

        val selectedMonth = remember { mutableStateOf(currentDaysList[0].month) }
        val selectedYear = remember { mutableIntStateOf(currentDate.year) }
            // show "Today" if user selects today's date
            // else, show the full format of the date
            Text(
                text = "${
                    selectedMonth.value.getDisplayName(
                        TextStyle.FULL, Locale.getDefault()
                    )
                } ${selectedYear.intValue}",
//            text = if (data.selectedDate.isToday) {
//                "Today"
//            } else {
//                data.selectedDate.date.format(
//                    DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
//                )
//            },
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start,
        )
    }
}

@Composable
fun Content(
    uiStateFlow: StateFlow<BookingUiState>,
    date: CalendarUiModel,
    // callback should be registered from outside
    onDateClickListener: (CalendarUiModel.Date) -> Unit,
) {
    val currentDate = LocalDate.now()
//    val currentDay = currentDate.dayOfMonth
    val currentDaysList = remember {
        (0..20).map { day -> (currentDate).plusDays(day.toLong()) }
    }


    val uiState by uiStateFlow.collectAsStateWithLifecycle()
    var selected by remember {
        mutableStateOf(0)
    }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    SnappingLazyRow(
        scaleCalculator = { offset, halfRowWidth ->
            (1f - minOf(1f, StrictMath.abs(offset).toFloat() / halfRowWidth) * 0.4f)
        },
        key = { index, item ->
            item//or any id
        },
        items = MutableList(20) { it },
        itemWidth = 68.dp,
        onSelect = {
            selected = it
        },
        listState = listState
    ) { index, item, scale ->
        val dayNum = currentDaysList[index].dayOfMonth.toString()
        val dayName = currentDaysList[index].dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        Card(
            modifier = Modifier
                .width(60.dp)
                .height(74.dp)
                .background(shape = MaterialTheme.shapes.small, color = Color.Transparent)
                .clickable {
                    scope.launch {
                        listState.animateScrollToItem(index)
                        // selectedDate.value = date
                    }
                }
                .scale(scale.coerceAtLeast(0.8f))
                .alpha(scale.coerceAtLeast(0.1f)),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        )
        {
            CalendarItem(
                dayNum = dayNum,
                dayName = dayName
            )
        }
    }
}


@Composable
fun CalendarItem(
    dayNum: String, dayName: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = dayNum,
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.bodyLarge,
        )
        Text(
            text = dayName,
            color = MaterialTheme.colorScheme.background,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
        )
    }
}

//@Composable
//fun ContentItem(
//    date: CalendarUiModel.Date,
//   onClickListener: (CalendarUiModel.Date) -> Unit, // still, callback should be registered from outside
//     )
//    {
//        val coroutineScope = rememberCoroutineScope()
//        Column(
//            modifier = Modifier
//                .fillMaxSize(),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = date.date.toString(),
//                color = MaterialTheme.colorScheme.background,
//                style = MaterialTheme.typography.bodyLarge,
//                )
//
//        }
//    }

