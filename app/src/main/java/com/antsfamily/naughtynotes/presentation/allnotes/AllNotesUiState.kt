package com.antsfamily.naughtynotes.presentation.allnotes

import com.antsfamily.domain.model.NoteModel

//TODO fix error state later, adding error Type and it's handler
sealed class AllNotesUiState {
    data object Loading : AllNotesUiState()
    data class Content(val notes: List<NoteModel>) : AllNotesUiState()
    data object EmptyContent : AllNotesUiState()
    data class Error(val type: String) : AllNotesUiState()
}
