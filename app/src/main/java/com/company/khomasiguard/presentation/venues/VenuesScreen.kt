package com.company.khomasiguard.presentation.venues

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.company.khomasiguard.R
import com.company.khomasiguard.presentation.venues.components.TabItem
import com.company.khomasiguard.presentation.venues.components.VenuesUiState
import com.company.khomasiguard.theme.KhomasiGuardTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun VenuesScreen(
    getGuardPlaygrounds: () -> Unit,
    uiState: StateFlow<VenuesUiState>,
    ) {
    LaunchedEffect(Unit) {
        getGuardPlaygrounds()
    }
    val list = listOf(TabItem.Activated, TabItem.NotActivated)
    val pagerState = rememberPagerState(initialPage = 0)
    Column(modifier = Modifier.fillMaxSize()) {
        Tabs(tabs = list, pagerState = pagerState)
        Image(
            painter = painterResource(R.drawable.view_pager_group),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )
        TabContent(
            tabs = list,
            pagerState = pagerState,
            uiState = uiState
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.background,
        indicator = @Composable {},
        divider = @Composable {},
    ) {
        tabs.forEachIndexed { index, tabItem ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = stringResource(id = tabItem.title),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.tertiary,
                enabled = true
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabContent(
    tabs: List<TabItem>,
    pagerState: PagerState,
    uiState: StateFlow<VenuesUiState>,


    ) {
    HorizontalPager(count = tabs.size, state = pagerState) { page ->
        tabs[page].screens(
           uiState
        )
    }
}
@Preview(name = "light", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun LoginPreview() {
    KhomasiGuardTheme {
        val mockViewModel: MockVenuesViewModel = viewModel()

        VenuesScreen(
            uiState = mockViewModel.uiState,
            getGuardPlaygrounds = mockViewModel::getGuardPlaygrounds
        )
    }
}

