package com.antsfamily.naughtynotes.presentation.home

import com.antsfamily.domain.model.NoteModel
import java.time.LocalDate
import java.time.YearMonth

sealed class HomeUiState {
    data object Loading: HomeUiState()
    data class Content(
        val yearMonth: YearMonth,
        val isCurrentMonth: Boolean,
        val datesWithNotes: List<LocalDate>,
        val daysSinceLastNote: Int,
        val lastThreeNotes: List<NoteModel>,
    ): HomeUiState()
}