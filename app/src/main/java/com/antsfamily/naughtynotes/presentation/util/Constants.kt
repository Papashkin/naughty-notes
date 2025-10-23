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

const val STATS_ANIMATION_DURATION = 1000

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

val COLORS_LIST = listOf(
    Color(0xFF8E7CC3),  // lavender purple
    Color(0xFFFFB74D),  // warm amber
    Color(0xFF4FC3F7),  // light aqua blue
    Color(0xFFFF8A65),  // coral orange
    Color(0xFFA1887F),  // taupe brown
    Color(0xFF7986CB),  // periwinkle blue
    Color(0xFFDCE775),  // lime yellow
    Color(0xFFBA68C8),  // violet
    Color(0xFFF06292),  // soft pink
    Color(0xFF4DB6AC),  // teal
    Color(0xFFFFCC80),  // sand orange
    Color(0xFF81C784),  // green
    Color(0xFF9E9E9E),  // neutral gray
    Color(0xFFAED581),  // light green
    Color(0xFF64B5F6),  // soft blue
    Color(0xFF90A4AE),  // gray-blue
    Color(0xFFFFB300),  // warm gold
    Color(0xFF9575CD),  // light violet
    Color(0xFF4DD0E1),  // turquoise
    Color(0xFF7986CB),  // blue-gray
    Color(0xFFE57373),  // soft red
)