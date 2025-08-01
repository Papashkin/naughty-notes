package com.antsfamily.naughtynotes.presentation.home

import java.time.LocalDate
import java.time.YearMonth

sealed class HomeUiState {
    data object Loading: HomeUiState()
    data class Content(
        val yearMonth: YearMonth,
        val isCurrentMonth: Boolean,
        val isNavigationBackVisible: Boolean,
        val isNavigationForwardVisible: Boolean,
        val datesWithNotes: List<LocalDate>,
        val daysSinceLastNote: Int,
    ): HomeUiState()
}