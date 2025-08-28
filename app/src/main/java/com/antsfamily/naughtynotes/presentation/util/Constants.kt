package com.antsfamily.naughtynotes.presentation.util

import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import java.time.LocalDate
import java.time.Month

const val PIN_CODE_SIZE = 4

const val PIN_CODE_KEY_BULLET = "\u25CF"

const val CREATE_NOTE_NOTE_LENGTH_MAX = 60

const val CALENDAR_VIEW_MONTH_AMOUNT = 12L

val PREVIEW_NOTES = listOf(
    NoteModel(
        3643,
        LocalDate.of(2025, Month.JULY, 12),
        PracticeType.ANAL,
        PracticeLocation.CAR,
        isProtected = true,
        hasOrgasm = false,
        hasPartnerOrgasm = false,
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
        hasPartnerOrgasm = true,
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
        hasPartnerOrgasm = true,
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
        hasPartnerOrgasm = false,
        painRate = 2,
        rate = 4,
        personalNote = "That was something crazy"
    )
)