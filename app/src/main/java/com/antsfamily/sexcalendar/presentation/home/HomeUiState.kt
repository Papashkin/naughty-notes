package com.antsfamily.sexcalendar.presentation.home

import com.antsfamily.domain.model.NoteModel
import java.time.YearMonth

sealed class HomeUiState {
    data object Loading: HomeUiState()
    data class Content(
        val yearMonth: YearMonth,
        val isCurrentMonth: Boolean,
        val isNavigationBackVisible: Boolean,
        val isNavigationForwardVisible: Boolean,
        val notes: List<NoteModel>
    ): HomeUiState()
}