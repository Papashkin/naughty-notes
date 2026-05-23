package com.antsfamily.naughtynotes.presentation.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.formatToString(): String = this.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))