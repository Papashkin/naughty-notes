package com.antsfamily.sexcalendar.presentation.createnote

import com.antsfamily.domain.model.SexType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

sealed class CreateNoteUiState {
    data object Loading : CreateNoteUiState()
    data class Content(
        val date: LocalDate,
        val type: SexType,
        val isProtected: Boolean,
        val rate: Int,
        val painRate: Int,
        val note: String,
        val isSaveButtonEnabled: Boolean,
        val isSaveButtonLoadingVisible: Boolean
    ) : CreateNoteUiState() {

        companion object {
            val Default = Content(
                date = LocalDate.now(),
                type = SexType.UNKNOWN,
                isProtected = false,
                rate = 0,
                painRate = 0,
                note = "",
                isSaveButtonEnabled = false,
                isSaveButtonLoadingVisible = false
            )
        }

        val isValid: Boolean
            get() {
                return this.rate > 0 && this.type != SexType.UNKNOWN
            }
    }
}

fun LocalDate.formatToString(): String = this.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))