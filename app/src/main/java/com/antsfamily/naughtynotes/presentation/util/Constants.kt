package com.antsfamily.naughtynotes.presentation.util

import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import java.time.LocalDate
import java.time.Month

val PREVIEW_NOTES = listOf(
    NoteModel(
        3643,
        LocalDate.of(2025, Month.JULY, 12),
        PracticeType.ANAL,
        PracticeLocation.CAR,
        isProtected = true,
        hasOrgasm = false,
        painRate = 2,
        rate = 4,
        personalNote = ""
    ),
    NoteModel(
        2452,
        LocalDate.of(2025, Month.JULY, 22),
        PracticeType.VAGINAL,
        PracticeLocation.SHOWER,
        isProtected = true,
        hasOrgasm = false,
        painRate = 2,
        rate = 4,
        personalNote = "That was something crazy"
    ),
    NoteModel(
        1231,
        LocalDate.of(2025, Month.JULY, 15),
        PracticeType.ANAL,
        PracticeLocation.CHANGING_ROOM,
        isProtected = false,
        hasOrgasm = false,
        painRate = 2,
        rate = 4,
        personalNote = ""
    ),
    NoteModel(
        75765,
        LocalDate.of(2025, Month.JULY, 20),
        PracticeType.THREESOME,
        PracticeLocation.HOTEL,
        isProtected = true,
        hasOrgasm = true,
        painRate = 2,
        rate = 4,
        personalNote = "That was something crazy"
    )
)