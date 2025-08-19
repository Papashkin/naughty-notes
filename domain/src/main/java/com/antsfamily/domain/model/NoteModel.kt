package com.antsfamily.domain.model

import java.time.LocalDate

data class NoteModel(
    val id: Int,
    val date: LocalDate,
    val type: PracticeType,
    val location: PracticeLocation,
    val isProtected: Boolean,
    val hasOrgasm: Boolean,
    val painRate: Int,
    val personalNote: String,
    val rate: Int
)
