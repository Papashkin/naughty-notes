package com.antsfamily.naughtynotes.presentation.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.antsfamily.domain.model.Other
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.common.FullScreenError
import com.antsfamily.naughtynotes.presentation.common.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.home.view.InfoCard
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.stats.view.StatsChart
import com.antsfamily.naughtynotes.presentation.stats.view.StatsChartLegend
import com.antsfamily.naughtynotes.presentation.stats.view.StatsSubHeader
import com.antsfamily.naughtynotes.presentation.stats.view.TrendView
import com.antsfamily.naughtynotes.ui.theme.Padding
import java.math.BigDecimal
import java.time.YearMonth


@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel<StatsViewModel>(),
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.surface),
        verticalArrangement = Arrangement.Center
    ) {
        TopBar(
            title = stringResource(R.string.statistic_screen_title),
            onNavigationBack = { onNavigateBack() }
        )

        val state = viewModel.state.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = Padding.medium)
        ) {
            when (val uiState = state.value) {
                is StatsUiState.Loading -> FullScreenLoading()
                is StatsUiState.Error -> FullScreenError(uiState.type)
                is StatsUiState.Content -> StatsContentView(uiState) {
                    viewModel.onIntentCreated(it)
                }
            }
        }
    }
}

@Composable
fun StatsContentView(
    state: StatsUiState.Content,
    onIntentCreated: (StatsIntent) -> Unit,
) {
    Column {
        Row(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = Padding.medium)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Padding.medium)
        ) {
            InfoCard(
                modifier = Modifier.weight(1f),
                value = state.averageRate.toString(),
                valueStyle = MaterialTheme.typography.titleMedium,
                descriptionText = R.string.statistic_screen_card_average_rate,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
            InfoCard(
                modifier = Modifier.weight(1f),
                value = state.mostActiveMonth.orEmpty(),
                valueStyle = MaterialTheme.typography.titleMedium,
                descriptionText = R.string.statistic_screen_card_most_active_month,
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        }

        StatsSubHeader(
            modifier = Modifier.weight(0.7f)
        ) { onIntentCreated(it) }

        Row(
            modifier = Modifier.weight(1.8f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatsChart(
                modifier = Modifier.weight(1f),
                items = state.statItems
            )
            StatsChartLegend(
                modifier = Modifier.weight(1f),
                items = state.statItems
            )
        }
        TrendView(
            modifier = Modifier
                .padding(vertical = Padding.medium)
                .weight(1.5f),
            trends = state.trends
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatsContentViewPreview() {
    StatsContentView(
        StatsUiState.Content(
            statItems = listOf(
                StatsItem(PracticeType.ANAL, 15, BigDecimal(100 / 6)),
                StatsItem(PracticeType.ORAL, 84, BigDecimal(100 / 7)),
                StatsItem(PracticeType.TRIBADISM, 63, BigDecimal(54)),
                StatsItem(Other, 15, BigDecimal(10)),
            ),
            averageRate = BigDecimal("3.76"),
            mostActiveMonth = YearMonth.now().month.toString(),
            trends = listOf(5f, 16f, 8f, 10f, 12f, 14f, 10f)
        )
    ) {}
}