package com.antsfamily.sexcalendar.presentation.home

import java.time.YearMonth

sealed class HomeUiState {
    data object Loading: HomeUiState()
    data class Content(
        val yearMonth: YearMonth,
        val isNavigationBackVisible: Boolean,
        val isNavigationForwardVisible: Boolean
    ): HomeUiState()
}