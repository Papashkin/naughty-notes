package com.antsfamily.sexcalendar.presentation.createnote

import com.antsfamily.sexcalendar.presentation.createnote.model.SexType

sealed class CreateNoteUiState {
    data object Loading: CreateNoteUiState()
    data class Content(
        val type: SexType,
        val isProtected: Boolean,
        val rate: Int,
        val painRate: Int,
        val note: String,
        val isSaveButtonEnabled: Boolean,
        val isSaveButtonLoadingVisible: Boolean
    ): CreateNoteUiState() {

        companion object{
            val Default = Content(
                type = SexType.UNKNOWN,
                isProtected = false,
                rate = 0,
                painRate = 0,
                note = "",
                isSaveButtonEnabled = false,
                isSaveButtonLoadingVisible = false
            )
        }
    }
}