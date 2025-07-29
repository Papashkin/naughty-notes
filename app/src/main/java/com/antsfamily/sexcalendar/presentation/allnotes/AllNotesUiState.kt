package com.antsfamily.sexcalendar.presentation.allnotes

import com.antsfamily.domain.model.NoteModel
import java.time.LocalDate

sealed class AllNotesUiState {
    data object Loading : AllNotesUiState()
    data class Content(
        val date: LocalDate,
        val notes: List<NoteModel>
    ) : AllNotesUiState()
    data class Error(val type: String) : AllNotesUiState() //TODO fix that later, adding error Type and it's handler
}
