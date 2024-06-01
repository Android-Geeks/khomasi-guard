package com.company.khomasiguard.presentation.booking.component
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberLazyListSnapperLayoutInfo
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlin.math.abs
@OptIn(ExperimentalFoundationApi::class, ExperimentalSnapperApi::class)
@Composable
fun <T> SnappingLazyRow(
    items: List<T>,
    itemWidth: Dp,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    reverseLayout: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal =
        if (!reverseLayout)
            Arrangement.Start
        else
            Arrangement.End,
    verticalAlignment: Alignment.Vertical = Alignment.Top,
    userScrollEnabled: Boolean = true,
    key: ((index: Int, item: T) -> Any)? = null,
    scaleCalculator: (Int, Float) -> Float = { offset, halfRowWidth ->
        (1f - minOf(1f, abs(offset).toFloat() / halfRowWidth) * 0.5f)
    },
    onSelect: (Int) -> Unit,
    item: @Composable (Int, T, Float) -> Unit
) {
    val layoutInfo = rememberLazyListSnapperLayoutInfo(listState)
    var apparentCurrentItem by remember {
        mutableStateOf(-1)
    }
    LaunchedEffect(listState.isScrollInProgress, apparentCurrentItem) {
        if (!listState.isScrollInProgress) {
            val d = layoutInfo.currentItem
            d?.let {
                if (apparentCurrentItem > -1) {
                    onSelect(apparentCurrentItem)
                }
            }
        }
    }
    BoxWithConstraints(
        modifier = modifier
    ) {
        val full = LocalConfiguration.current.screenWidthDp.dp
        val pad = (full - itemWidth) / 2
        val halfRowWidth = constraints.maxWidth / 2f
        CompositionLocalProvider(
            LocalOverscrollConfiguration provides null
        ) {
            LazyRow(
                userScrollEnabled = userScrollEnabled,
                horizontalArrangement = horizontalArrangement,
                reverseLayout = reverseLayout,
                verticalAlignment = verticalAlignment,
                state = listState,
                modifier = Modifier
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = pad),
                flingBehavior = rememberSnapperFlingBehavior(listState)
            ) {
                itemsIndexed(
                    items,
                    key = key
                ) { i, item ->
                    val opacity by remember {
                        derivedStateOf {
                            val currentItemInfo = listState
                                .layoutInfo.visibleItemsInfo
                                .firstOrNull { it.index == i }
                                ?: return@derivedStateOf 0f
                            val offset = currentItemInfo.offset
                            scaleCalculator(offset, halfRowWidth)

                        }
                    }
                    LaunchedEffect(key1 = opacity) {
                        if (1f - opacity <= 0.1f) {
                            apparentCurrentItem = i
                        }
                    }
                    item(i, item, opacity)
                }
            }
        }
    }
}