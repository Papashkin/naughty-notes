package com.antsfamily.sexcalendar.presentation.allnotes

import com.antsfamily.domain.model.NoteModel

sealed class AllNotesUiState {
    data object Loading : AllNotesUiState()
    data class Content(
        val year: Int,
        val month: String,
        val notes: List<NoteModel>
    ) : AllNotesUiState()
    data class Error(val type: String) : AllNotesUiState() //TODO fix that later, adding error Type and it's handler
}
