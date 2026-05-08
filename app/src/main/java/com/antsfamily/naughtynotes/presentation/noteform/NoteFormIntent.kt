package com.antsfamily.naughtynotes.presentation.noteform

import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.ChipType

sealed class NoteFormIntent {
    data class SetPracticeType(val type: PracticeType) : NoteFormIntent()
    data class SetPracticeLocation(val location: PracticeLocation) : NoteFormIntent()
    data class SetNoteChipSelectionChanged(val type: ChipType, val isSelected: Boolean) : NoteFormIntent()
    data class SetPainRate(val rate: Int) : NoteFormIntent()
    data class SetPleasureRate(val rate: Int) : NoteFormIntent()
    data class SetNote(val note: String) : NoteFormIntent()
    data object SaveButtonClick : NoteFormIntent()
}
