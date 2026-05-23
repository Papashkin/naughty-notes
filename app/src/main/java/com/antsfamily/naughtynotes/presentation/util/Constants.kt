package com.antsfamily.naughtynotes.presentation.util

import androidx.compose.ui.graphics.Color
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import java.time.LocalDate
import java.time.Month

const val PIN_CODE_SIZE = 4

const val PIN_CODE_KEY_BULLET = "\u25CF"

const val CREATE_NOTE_NOTE_LENGTH_MAX = 60

const val CALENDAR_VIEW_MONTH_AMOUNT = 12L

const val STATS_ANIMATION_DURATION = 500

val PREVIEW_NOTES = listOf(
    NoteModel(
        3643,
        LocalDate.of(2025, Month.JULY, 12),
        listOf(
            PracticeType.ANAL,
            PracticeType.ORAL,
        ),
        location = PracticeLocation.CAR,
        isProtected = true,
        hasOrgasm = false,
        hasPartnerOrgasm = false,
        experienceRate = 29f,
        personalNote = "",
    ),
    NoteModel(
        2452,
        LocalDate.of(2025, Month.JULY, 22),
        listOf(
            PracticeType.ANAL,
            PracticeType.ORAL,
        ),
        PracticeLocation.SHOWER,
        isProtected = true,
        hasOrgasm = false,
        hasPartnerOrgasm = true,
        experienceRate = 45f,
        personalNote = "That was something crazy"
    ),
    NoteModel(
        1231,
        LocalDate.of(2025, Month.JULY, 15),
        listOf(
            PracticeType.MASTURBATION,
            PracticeType.ORAL,
        ),
        PracticeLocation.CHANGING_ROOM,
        isProtected = false,
        hasOrgasm = false,
        hasPartnerOrgasm = true,
        experienceRate = 15f,
        personalNote = ""
    ),
    NoteModel(
        75765,
        LocalDate.of(2025, Month.JULY, 20),
        listOf(
            PracticeType.ANAL,
            PracticeType.THREESOME,
        ),
        PracticeLocation.HOTEL,
        isProtected = true,
        hasOrgasm = true,
        hasPartnerOrgasm = false,
        experienceRate = 66f,
        personalNote = "That was something crazy"
    )
)

val COLORS_LIST = listOf(
    Color(0xFFC9207A),
    Color(0xFFD94C96),
    Color(0xFFE57FB3),
    Color(0xFFF2B7D3),
    Color(0xFFF7D3E5),
    Color(0xFFFBEAF3),
)