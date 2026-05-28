package com.antsfamily.naughtynotes.presentation.util

import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceType
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

fun ExperienceType.toAlphaValue(): Float =
    when (this) {
        ExperienceType.EMPTY -> 0.1f
        ExperienceType.BAD -> 0.2f
        ExperienceType.BELOW_AVERAGE -> 0.4f
        ExperienceType.OKAY -> 0.6f
        ExperienceType.GOOD -> 0.8f
        ExperienceType.AMAZING -> 1.0f
    }