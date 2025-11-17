package com.antsfamily.naughtynotes.presentation.home

import java.time.LocalDate
import java.time.YearMonth

sealed class HomeIntent {
    data object ShowToday: HomeIntent()
    data class SelectDay(val date: LocalDate): HomeIntent()
    data object Settings: HomeIntent()
    data class ChangeMonth(val yearMonth: YearMonth): HomeIntent()
}