package com.antsfamily.naughtynotes.presentation.noteform.model

import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceRate.Companion.getDefault

enum class ExperienceType(
    val value: Float,
) {
    EMPTY( 0f),
    BAD( 1f),
    BELOW_AVERAGE( 2f),
    OKAY( 3f),
    GOOD( 4f),
    AMAZING( 5f),
    ;

    companion object {
        fun toClosedRange() = EMPTY.value..AMAZING.value

        fun getTypeByValue(value: Float): ExperienceType =
            ExperienceType.entries.firstOrNull {
                value.toInt() == it.value.toInt()
            } ?: getDefault().type
    }
}

data class ExperienceRate(
    val type: ExperienceType,
    val value: Float
) {
    companion object {
        fun getDefault(): ExperienceRate = ExperienceRate(ExperienceType.EMPTY, 0f)
    }
}