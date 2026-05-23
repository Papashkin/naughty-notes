package com.antsfamily.naughtynotes.presentation.noteform

import com.antsfamily.domain.model.ErrorType
import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceRate
import com.antsfamily.naughtynotes.presentation.noteform.model.NoteFormType
import com.antsfamily.naughtynotes.presentation.noteform.model.PracticeLocationItem
import com.antsfamily.naughtynotes.presentation.noteform.model.PracticeTypeItem
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.NoteChip
import java.time.LocalDate

sealed class NoteFormUiState {
    data object Loading : NoteFormUiState()
    data class Error(val type: ErrorType) : NoteFormUiState()
    data class Content(
        val formType: NoteFormType,
        val date: LocalDate,
        val types: List<PracticeTypeItem>,
        val locations: List<PracticeLocationItem>,
        val experienceRate: ExperienceRate,
        val note: String,
        val isSaveButtonEnabled: Boolean,
        val isSaveButtonLoadingVisible: Boolean,
        val chips: List<NoteChip>,
    ) : NoteFormUiState() {

        companion object {
            val Default = Content(
                formType = NoteFormType.CREATE,
                date = LocalDate.now(),
                types = PracticeTypeItem.getDefaultTypes(),
                locations = PracticeLocationItem.getDefaultLocations(),
                experienceRate = ExperienceRate.getDefault(),
                note = "",
                isSaveButtonEnabled = false,
                isSaveButtonLoadingVisible = false,
                chips = NoteChip.allVariants
            )
        }
    }
}
