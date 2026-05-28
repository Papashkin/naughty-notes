package com.antsfamily.naughtynotes.presentation.util

import androidx.compose.ui.graphics.Color
import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceType


fun ExperienceType.toColor(): Color =
    when (this) {
        ExperienceType.EMPTY -> Color(0xFFE0E0E0)
        ExperienceType.BAD -> Color(0xFFE08E8E)
        ExperienceType.BELOW_AVERAGE -> Color(0xFFE6B98D)
        ExperienceType.OKAY -> Color(0xFFE8D27C)
        ExperienceType.GOOD -> Color(0xFF8FC9A8)
        ExperienceType.AMAZING -> Color(0xFF5DBB9A)
    }