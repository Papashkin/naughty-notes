package com.antsfamily.naughtynotes.presentation.home

import com.antsfamily.naughtynotes.presentation.home.model.HomeNoteCardModel
import java.time.LocalDate
import java.time.YearMonth

sealed class HomeUiState {
    data object Loading: HomeUiState()
    data class Content(
        val yearMonth: YearMonth,
        val isCurrentMonth: Boolean,
        val datesWithNotes: List<LocalDate>,
        val daysSinceLastNote: Int,
        val recentActivities: List<HomeNoteCardModel>,
    ): HomeUiState()
}