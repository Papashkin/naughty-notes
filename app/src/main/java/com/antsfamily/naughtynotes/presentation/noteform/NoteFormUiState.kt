package com.antsfamily.naughtynotes.presentation.noteform

import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.presentation.noteform.model.NoteFormType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

sealed class NoteFormUiState {
    data object Loading : NoteFormUiState()
    data class Content(
        val formType: NoteFormType,
        val date: LocalDate,
        val type: PracticeType,
        val location: PracticeLocation,
        val isProtected: Boolean,
        val hasOrgasm: Boolean,
        val hasPartnerOrgasm: Boolean,
        val pleasureRate: Int,
        val painRate: Int,
        val note: String,
        val isSaveButtonEnabled: Boolean,
        val isSaveButtonLoadingVisible: Boolean
    ) : NoteFormUiState() {

        companion object {
            val Default = Content(
                formType = NoteFormType.CREATE,
                date = LocalDate.now(),
                type = PracticeType.UNKNOWN,
                location = PracticeLocation.UNKNOWN,
                isProtected = false,
                hasOrgasm = false,
                hasPartnerOrgasm = false,
                pleasureRate = 0,
                painRate = 0,
                note = "",
                isSaveButtonEnabled = false,
                isSaveButtonLoadingVisible = false
            )
        }

        val isValid: Boolean
            get() {
                return this.pleasureRate > 0 && this.type != PracticeType.UNKNOWN && location != PracticeLocation.UNKNOWN
            }
    }
}

fun LocalDate.formatToString(): String = this.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))