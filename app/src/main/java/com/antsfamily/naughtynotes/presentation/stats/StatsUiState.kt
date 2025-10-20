package com.antsfamily.naughtynotes.presentation.stats

sealed class StatsUiState {
    data object Loading: StatsUiState()
    data class Content(val data: String): StatsUiState()
}