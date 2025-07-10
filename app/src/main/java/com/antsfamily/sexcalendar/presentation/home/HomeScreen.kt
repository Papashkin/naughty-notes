package com.antsfamily.sexcalendar.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            yearMonth = uiState.yearMonth,
            isNavigationBackVisible = uiState.isNavigationBackVisible,
            isNavigationForwardVisible = uiState.isNavigationForwardVisible,
            onPreviousMonthClick = { viewModel.onPreviousMonthClick() },
            onNextMonthClick = { viewModel.onNextMonthClick() }
        )
    }
}

@Composable
fun HomeContent(
    yearMonth: YearMonth,
    isNavigationBackVisible: Boolean,
    isNavigationForwardVisible: Boolean,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit
) {

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Journal",
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
        }

        Spacer(Modifier.height(40.dp))

        CalendarView(
            modifier = Modifier.padding(horizontal = 16.dp),
            yearMonth = yearMonth,
            isNavigationBackVisible = isNavigationBackVisible,
            isNavigationForwardVisible = isNavigationForwardVisible,
            onPreviousMonthClick = { onPreviousMonthClick.invoke() },
            onNextMonthClick = { onNextMonthClick.invoke() }
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeContentPreview() {
    HomeContent(YearMonth.now(), true, false, {}, {})
}