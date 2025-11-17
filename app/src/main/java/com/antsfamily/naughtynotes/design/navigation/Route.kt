package com.antsfamily.naughtynotes.design.navigation

import kotlinx.serialization.Serializable

sealed class Route

@Serializable
data object Splash: Route()

@Serializable
data object Home: Route()

@Serializable
data class NoteForm(val dateEpoch: Long, val noteId: Int?): Route()

@Serializable
data class AllNotes(val dateEpoch: Long): Route()

@Serializable
data object Settings: Route()

@Serializable
data object PinCodeVerification: Route()

@Serializable
data object ChangeExistingPinCode: Route()

@Serializable
data object Stats: Route()
