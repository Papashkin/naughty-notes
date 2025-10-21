package com.antsfamily.naughtynotes.presentation.util

import kotlin.time.Duration.Companion.milliseconds

fun Long.toMinutesString(): String {
    val duration = this.milliseconds
    return if (duration.inWholeMinutes < 1) {
        "less than a minute"
    } else {
        "%1d minutes".format(duration.inWholeMinutes.plus(1))
    }
}

val Float.degreeToAngle
    get() = (this * Math.PI / 180f).toFloat()