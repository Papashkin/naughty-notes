package com.antsfamily.naughtynotes.presentation.stats.model

import com.antsfamily.domain.model.StatInfo

data class StatsItem(
    val info: StatInfo,
    val value: Int,
) {

    fun percent(totalSum: Float): Float {
        return (360 * value / totalSum)
    }
}

fun List<StatsItem>.getTotalSum() = this.sumOf { it.value }.toFloat()