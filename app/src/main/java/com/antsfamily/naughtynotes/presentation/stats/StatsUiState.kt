package com.antsfamily.naughtynotes.presentation.stats

import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem

sealed class StatsUiState {
    data object Loading: StatsUiState()
    data class Content(val data: List<StatsItem>): StatsUiState()
    data class Error(val string: String): StatsUiState()
}