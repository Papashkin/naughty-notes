package com.antsfamily.sexcalendar.design.navigation

import kotlinx.serialization.Serializable

sealed class Route

@Serializable
data object Splash: Route()

@Serializable
data object Home: Route()

@Serializable
data object CreateNote: Route()