package com.antsfamily.sexcalendar.presentation.createnote.model

import java.time.LocalDate

data class NoteModel(
    val date: LocalDate,
    val type: SexType,
    val isProtected: Boolean,
    val painRate: Int,
    val personalNote: String,
    val rate: Int
)
