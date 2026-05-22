package com.antsfamily.naughtynotes.presentation.noteform.model

import com.antsfamily.domain.model.PracticeLocation

data class PracticeLocationItem(
    val location: PracticeLocation,
    val isSelected: Boolean,
) {
    companion object {
        fun getDefaultLocations() = PracticeLocation.entries.map {
            PracticeLocationItem(it, false)
        }
    }
}
