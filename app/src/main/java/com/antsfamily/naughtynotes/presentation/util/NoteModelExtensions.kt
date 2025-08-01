package com.antsfamily.naughtynotes.presentation.util

import com.antsfamily.domain.model.NoteModel
import com.kizitonwose.calendar.core.yearMonth
import java.time.LocalDate
import java.time.YearMonth

fun List<NoteModel>.getDatesForMonth(month: YearMonth): List<LocalDate> =
    this.filter { note -> note.date.yearMonth == month }
        .map { note -> note.date }
