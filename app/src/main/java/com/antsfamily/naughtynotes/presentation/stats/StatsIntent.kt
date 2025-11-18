package com.antsfamily.naughtynotes.presentation.stats

import com.antsfamily.naughtynotes.presentation.stats.model.StatChipType
import com.antsfamily.naughtynotes.presentation.stats.model.TimeFrameItem

sealed class StatsIntent {
    data class ShowByType(val type: StatChipType): StatsIntent()
    data class ShowByTimeframe(val timeFrameItem: TimeFrameItem): StatsIntent()
}