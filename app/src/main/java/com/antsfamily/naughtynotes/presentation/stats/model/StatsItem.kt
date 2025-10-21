package com.antsfamily.naughtynotes.presentation.stats.model

import androidx.compose.ui.graphics.Color

data class StatsItem(
    val color: Color,
    val data: Pair<String, Int>
) {

    fun percent(totalSum: Float): Float {
        return (360 * data.second / totalSum)
    }
}

fun List<StatsItem>.getTotalSum() = this.sumOf { it.data.second }.toFloat()