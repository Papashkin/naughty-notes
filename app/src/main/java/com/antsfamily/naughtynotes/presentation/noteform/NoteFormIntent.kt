package com.antsfamily.naughtynotes.presentation.noteform

import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.presentation.noteform.model.LocationChipGridState
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.ChipType

sealed class NoteFormIntent {
    data class SetPracticeType(
        val type: PracticeType,
        val isSelected: Boolean,
    ) : NoteFormIntent()

    data class SetPracticeLocation(
        val location: PracticeLocation,
        val isSelected: Boolean,
    ) : NoteFormIntent()

    data class SetLocationGridState(
        val state: LocationChipGridState,
    ) : NoteFormIntent()

    data class SetNoteChipSelectionChanged(
        val type: ChipType,
        val isSelected: Boolean,
    ) : NoteFormIntent()

    data class SetNote(val note: String) : NoteFormIntent()
    data class SetExperienceRate(val rate: Float) : NoteFormIntent()
    data object SaveButtonClick : NoteFormIntent()
}
