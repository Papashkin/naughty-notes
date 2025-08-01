package com.antsfamily.naughtynotes.design.navigation

import kotlinx.serialization.Serializable

sealed class Route

@Serializable
data object Splash: Route()

@Serializable
data object Home: Route()

@Serializable
data class CreateNote(val dateEpoch: Long): Route()

@Serializable
data class AllNotes(val dateEpoch: Long): Route()