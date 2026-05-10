package com.antsfamily.naughtynotes.presentation.stats

import com.antsfamily.naughtynotes.presentation.stats.model.StatChipType

sealed class StatsIntent {
    data class ShowByType(val type: StatChipType): StatsIntent()
}