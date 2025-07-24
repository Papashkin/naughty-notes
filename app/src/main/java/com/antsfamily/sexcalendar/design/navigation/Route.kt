package com.antsfamily.sexcalendar.design.navigation

import kotlinx.serialization.Serializable
import java.time.Month

sealed class Route

@Serializable
data object Splash: Route()

@Serializable
data object Home: Route()

@Serializable
data class CreateNote(val dateEpoch: Long): Route()

@Serializable
data class AllNotes(val month: Month): Route()