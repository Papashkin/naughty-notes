package com.antsfamily.naughtynotes.presentation.home.model

import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceType
import java.time.LocalDate

data class HomeNoteCardModel(
    val date: LocalDate,
    val types: List<PracticeType>,
    val experienceType: ExperienceType,
)