package com.antsfamily.naughtynotes.presentation.noteform.model

enum class ExperienceType(
    val minValue: Float,
    val maxValue: Float,
) {
    BAD(0f, 20f),
    BELOW_AVERAGE(20f, 40f),
    OKAY(40f, 60f),
    GOOD(60f, 80f),
    AMAZING(80f, 100f),
    ;
}

data class ExperienceRate(
    val type: ExperienceType,
    val value: Float
) {
    companion object {
        fun getDefault(): ExperienceRate = ExperienceRate(ExperienceType.OKAY, 50f)
    }
}