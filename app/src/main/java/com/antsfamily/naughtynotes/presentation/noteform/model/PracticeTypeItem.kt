package com.antsfamily.naughtynotes.presentation.noteform.model

import com.antsfamily.domain.model.PracticeType

data class PracticeTypeItem(
    val type: PracticeType,
    val isSelected: Boolean,
) {
    companion object {
        fun getDefaultTypes() = PracticeType.entries.map {
            PracticeTypeItem(it, false)
        }
    }
}
