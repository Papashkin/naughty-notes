package com.antsfamily.sexcalendar.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import java.time.Month
import java.time.YearMonth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()

    when (val uiState = state.value) {
        is HomeUiState.Loading -> FullScreenLoading()
        is HomeUiState.Content -> HomeContent(
            year = uiState.yearMonth.year,
            month = uiState.yearMonth.month,
            isNavigationBackVisible = uiState.isNavigationBackVisible,
            isNavigationForwardVisible = uiState.isNavigationForwardVisible,
            onNavigationBackClick = {
                viewModel.onPreviousMonthClick()
            },
            onNavigationForwardClick = {
                viewModel.onNextMonthClick()
            }
        )
    }
}

@Composable
fun HomeContent(
    year: Int,
    month: Month,
    isNavigationBackVisible: Boolean,
    isNavigationForwardVisible: Boolean,
    onNavigationBackClick: () -> Unit,
    onNavigationForwardClick: () -> Unit
) {

    Column {
        TopBar(
            modifier = Modifier.statusBarsPadding(),
            title = month.name.plus(" ").plus(year).lowercase(),
            isNavigationBackVisible = isNavigationBackVisible,
            isNavigationForwardVisible = isNavigationForwardVisible,
            onNavigationBack = { onNavigationBackClick.invoke() },
            onNavigationForward = { onNavigationForwardClick.invoke() }
        )

        Box(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            CalendarView(year = year, month = month)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeContentPreview() {
    HomeContent(YearMonth.now().year, Month.APRIL, true, false, {}, {})
}