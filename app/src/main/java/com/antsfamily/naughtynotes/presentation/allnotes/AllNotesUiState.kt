package com.antsfamily.naughtynotes.presentation.allnotes

import com.antsfamily.domain.model.ErrorType
import com.antsfamily.domain.model.NoteModel

sealed class AllNotesUiState {
    data object Loading : AllNotesUiState()
    data class Content(val notes: List<NoteModel>) : AllNotesUiState()
    data object EmptyContent : AllNotesUiState()
    data class Error(val type: ErrorType) : AllNotesUiState()
}
