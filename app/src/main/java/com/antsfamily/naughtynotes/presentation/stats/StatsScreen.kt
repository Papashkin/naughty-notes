package com.antsfamily.naughtynotes.presentation.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.common.FullScreenError
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.home.view.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.stats.model.StatChipType
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.stats.model.TimeFrameItem
import com.antsfamily.naughtynotes.presentation.stats.view.ChipList
import com.antsfamily.naughtynotes.presentation.stats.view.StatsChart
import com.antsfamily.naughtynotes.presentation.stats.view.StatsChartLegend
import com.antsfamily.naughtynotes.presentation.stats.view.TimeDropdownItem
import com.antsfamily.naughtynotes.ui.theme.Padding


@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel<StatsViewModel>(),
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        TopBar(
            title = stringResource(R.string.statistic_screen_title),
            onNavigationBack = { onNavigateBack() }
        )

        SubHeader(
            modifier = Modifier.padding(Padding.large),
            onChipClick = { viewModel.onChipChange(it) },
            onTimeframeSelect = { viewModel.onTimeframeChange(it) }
        )

        val state = viewModel.state.collectAsState()

        Column(modifier = Modifier.fillMaxHeight()) {
            when (val uiState = state.value) {
                is StatsUiState.Loading -> FullScreenLoading()
                is StatsUiState.Error -> FullScreenError(uiState.string)
                is StatsUiState.Content -> StatsContentView(
                    modifier = Modifier.padding(top = Padding.medium),
                    items = uiState.statItems
                )
            }
        }
    }
}

@Composable
fun StatsContentView(
    modifier: Modifier = Modifier,
    items: List<StatsItem>
) {
    Column {
        StatsChart(
            modifier = modifier,
            items = items
        )
        StatsChartLegend(items = items)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SubHeader(
    modifier: Modifier = Modifier,
    onChipClick: (StatChipType) -> Unit = {},
    onTimeframeSelect: (TimeFrameItem) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.statistic_screen_subtitle),
            style = MaterialTheme.typography.bodyMedium
        )
        ChipList(
            chips = StatChipType.entries,
            modifier = Modifier.padding(vertical = Padding.small)
        ) {
            onChipClick(it)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "for:",
                style = MaterialTheme.typography.bodyMedium
            )
            Box {
                TimeDropdownItem {
                    onTimeframeSelect(it)
                }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatsScreenPreview1() {
    StatsScreen {}
}