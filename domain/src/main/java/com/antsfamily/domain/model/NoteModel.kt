package com.antsfamily.domain.model

import java.time.LocalDate

data class NoteModel(
    val id: Int,
    val date: LocalDate,
    val types: List<PracticeType>,
    val location: PracticeLocation,
    val isProtected: Boolean,
    val hasOrgasm: Boolean,
    val hasPartnerOrgasm: Boolean,
    val experienceRate: Float,
    val personalNote: String,
)
