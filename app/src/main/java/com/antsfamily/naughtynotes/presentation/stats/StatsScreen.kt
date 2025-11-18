package com.antsfamily.naughtynotes.presentation.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.antsfamily.domain.model.Other
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.common.FullScreenError
import com.antsfamily.naughtynotes.presentation.common.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.stats.view.StatsChart
import com.antsfamily.naughtynotes.presentation.stats.view.StatsChartLegend
import com.antsfamily.naughtynotes.presentation.stats.view.StatsSubHeader
import com.antsfamily.naughtynotes.presentation.stats.view.TrendView
import com.antsfamily.naughtynotes.ui.theme.Padding
import java.math.BigDecimal


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

        HorizontalDivider(
            thickness = Padding.x_large,
            color = MaterialTheme.colorScheme.surface
        )
        StatsSubHeader(
            modifier = Modifier.padding(horizontal = Padding.medium),
            onIntentCreated = { viewModel.onIntentCreated(it) },
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
                is StatsUiState.Content -> StatsContentView(uiState)
            }
        }
    }
}

@Composable
fun StatsContentView(
    state: StatsUiState.Content
) {
    Column {
        Row(
            modifier = Modifier.height(240.dp),
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
        HorizontalDivider(
            thickness = Padding.x_large,
            color = MaterialTheme.colorScheme.surface
        )
        TrendView(
            modifier = Modifier.padding(vertical = Padding.medium),
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
            trends = listOf(5f, 16f, 8f, 10f, 12f, 14f, 10f)
        )

    )
}