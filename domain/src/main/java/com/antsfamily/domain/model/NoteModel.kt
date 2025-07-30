package com.antsfamily.domain.model

import java.time.LocalDate

data class NoteModel(
    val id: Int,
    val date: LocalDate,
    val type: SexType,
    val isProtected: Boolean,
    val painRate: Int,
    val personalNote: String,
    val rate: Int
)
