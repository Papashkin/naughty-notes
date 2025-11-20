package com.antsfamily.naughtynotes.presentation.stats

import com.antsfamily.domain.model.ErrorType
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem

sealed class StatsUiState {
    data object Loading: StatsUiState()
    data class Content(
        val statItems: List<StatsItem>,
        val trends: List<Float>
    ): StatsUiState()
    data class Error(val type: ErrorType): StatsUiState()
}