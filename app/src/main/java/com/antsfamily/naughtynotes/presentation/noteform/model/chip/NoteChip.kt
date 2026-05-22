package com.antsfamily.naughtynotes.presentation.noteform.model.chip

data class NoteChip(
    val type: ChipType,
    val isSelected: Boolean,
) {
    companion object {
        val allVariants: List<NoteChip>
            get() = ChipType.entries.map { NoteChip(it, false) }
    }
}
