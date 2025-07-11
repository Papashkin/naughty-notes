package com.antsfamily.sexcalendar.presentation.createnote

sealed class CreateNoteUiState {
    data object Loading: CreateNoteUiState()
    data class Content(val data: String): CreateNoteUiState() //TODO replace data type with proper one for DB
}