package com.company.khomasiguard.presentation.booking.component


import android.util.DisplayMetrics
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.company.khomasiguard.theme.KhomasiGuardTheme
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.format.TextStyle
import java.util.Locale
import kotlin.math.absoluteValue
import androidx.compose.foundation.pager.HorizontalPager


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarPager(updateSelectedDay: (Int) -> Unit) {
    val coroutineScope = rememberCoroutineScope()

    val screenWidth = getScreenWidth()

    val currentDate = LocalDate.now()
    val currentDaysList = remember {
        (-20..20).map { day -> (currentDate).plusDays(day.toLong()) }
    }
    val selectedMonth = remember { mutableStateOf(currentDaysList[0].month) }
    val selectedYear = remember { mutableIntStateOf(currentDate.year) }

    val pagerState = rememberPagerState(pageCount = { currentDaysList.size - 1 }, initialPage = 0)

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage in currentDaysList.indices) {
            selectedMonth.value = currentDaysList[pagerState.currentPage].month
            selectedYear.intValue = currentDaysList[pagerState.currentPage].year
            updateSelectedDay(pagerState.currentPage)
        }
    }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "${
                selectedMonth.value.getDisplayName(
                    TextStyle.FULL, Locale.getDefault()
                )
            } ${selectedYear.intValue}",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            textAlign = TextAlign.Start,
        )
        Spacer(modifier = Modifier.height(4.dp))

        HorizontalPager(
            state = pagerState,
            pageSize = PageSize.Fixed(60.dp),
            pageSpacing = if (pagerState.currentPage == 0) (-2).dp else (0).dp,
            contentPadding = PaddingValues(
                start = (screenWidth / 2).dp - 30.dp,
                end = (screenWidth / 2).dp - 30.dp
            ),

            //snapPosition = SnapPosition.Start,
        ) { page ->
            val dayNum = currentDaysList[page].dayOfMonth.toString()
            val dayName =
                currentDaysList[page].dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

            // Calculate the absolute offset for the current page from the \scroll position. We use the absolute value which allows us to mirror\ any effects for both directions
            val pageOffset = calculatePageOffset(pagerState, page)

            Card(
                modifier = Modifier
                    .width(60.dp)
                    .height(74.dp)
                    .graphicsLayer {
                        // We animate the alpha, between 50% and 100%
                        alpha = lerp(
                            start = 0.3f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        // Scale the size of the page based on its distance from the current page \ The current page will have a scale of 1 (original size), and other pages will have a smaller scale
                        scaleY = lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                        scaleX = lerp(
                            start = 0.9f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
                    .background(shape = MaterialTheme.shapes.small, color = Color.Transparent)
                    .clickable { // Add clickable modifier to the Visa
                        coroutineScope.launch { // Launch a coroutine
                            pagerState.animateScrollToPage(page) // Scroll to the clicked page
                        }
                    },
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(if (page == pagerState.currentPage) 1.dp else (0).dp),
            ) {
                CalendarItem(
                    dayNum = dayNum,
                    dayName = dayName
                )
            }
        }
    }
}
@Composable
fun getScreenWidth(): Float {
    val displayMetrics: DisplayMetrics =
        LocalContext.current.resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}
@OptIn(ExperimentalFoundationApi::class)
fun calculatePageOffset(pagerState: PagerState, page: Int): Float {
    return ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
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

@Preview(showSystemUi = true)
@Composable
fun CalendarPreview() {
    KhomasiGuardTheme { CalendarPager(updateSelectedDay = {}) }
}