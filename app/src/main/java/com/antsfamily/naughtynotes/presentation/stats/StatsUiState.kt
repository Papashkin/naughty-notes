package com.antsfamily.naughtynotes.presentation.stats

import com.antsfamily.domain.model.ErrorType
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import java.math.BigDecimal

sealed class StatsUiState {
    data object Loading : StatsUiState()
    data class Content(
        val statItems: List<StatsItem>,
        val averageRate: BigDecimal,
        val mostActiveMonth: String?,
        val mostPopularActivity: PracticeType?,
        val mostPopularLocation: PracticeLocation?,
        val activitiesByMonth: Map<String, Int>,
    ) : StatsUiState()

    data class Error(val type: ErrorType) : StatsUiState()
}