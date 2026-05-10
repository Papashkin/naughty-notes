package com.antsfamily.naughtynotes.presentation.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.antsfamily.domain.model.Other
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.common.FullScreenError
import com.antsfamily.naughtynotes.presentation.common.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.stats.model.StatInfoType
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.stats.view.ActivityBarChar
import com.antsfamily.naughtynotes.presentation.stats.view.StatInfoCard
import com.antsfamily.naughtynotes.presentation.stats.view.StatsChipList
import com.antsfamily.naughtynotes.presentation.stats.view.StatsDoughnutChart
import com.antsfamily.naughtynotes.presentation.util.toStringId
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
//                .padding(horizontal = Padding.medium)
        ) {
            when (val uiState = state.value) {
                is StatsUiState.Loading -> FullScreenLoading()
                is StatsUiState.Error -> FullScreenError(uiState.type)
                is StatsUiState.Content -> StatsContentView(
                    state = uiState,
                    onIntentCreated = viewModel::onIntentCreated,
                )
            }
        }
    }
}

@Composable
fun StatsContentView(
    state: StatsUiState.Content,
    onIntentCreated: (StatsIntent) -> Unit,
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(Padding.regular),
    ) {
        Text(
            modifier = Modifier
                .padding(top = Padding.medium)
                .fillMaxWidth(),
            style = MaterialTheme.typography.bodyMedium,
            text = "Activities for last ${state.activitiesByMonth.size} months",
            textAlign = TextAlign.Center
        )
        ActivityBarChar(
            modifier = Modifier.padding(horizontal = Padding.medium),
            activities = state.activitiesByMonth)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Padding.x_small, horizontal = Padding.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Padding.medium)
        ) {
            StatInfoCard(
                modifier = Modifier.weight(1f),
                value = state.averageRate.toString(),
                type = StatInfoType.AVERAGE_RATE,
            )
            StatInfoCard(
                modifier = Modifier.weight(1f),
                value = state.mostActiveMonth ?: "-",
                type = StatInfoType.MOST_ACTIVE_MONTH,
            )
        }

        if (state.mostPopularActivity != null && state.mostPopularLocation != null) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Padding.x_small, horizontal = Padding.medium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Padding.medium)
            ) {
                StatInfoCard(
                    modifier = Modifier.weight(1f),
                    value = stringResource(state.mostPopularActivity.toStringId()),
                    type = StatInfoType.MOST_POPULAR_ACTIVITY,
                )
                StatInfoCard(
                    modifier = Modifier.weight(1f),
                    value = stringResource(state.mostPopularLocation.toStringId()),
                    type = StatInfoType.MOST_POPULAR_LOCATION,
                )
            }
        }

        VerticalDivider()

        StatsChipList(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(vertical = Padding.x_small)
        ) {
            onIntentCreated(StatsIntent.ShowByType(it))
        }

        StatsDoughnutChart(
            modifier = Modifier
                .padding(top = Padding.small, bottom = Padding.small, end = Padding.medium)
                .fillMaxWidth(),
            items = state.statItems
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
            mostPopularActivity = PracticeType.ORAL,
            mostPopularLocation = PracticeLocation.ELEVATOR,
            activitiesByMonth = mapOf(
                "Jan" to 10,
                "Feb" to 14,
                "Mar" to 8,
                "Apr" to 18,
                "May" to 22,
                "Jun" to 16,
                "Jul" to 12,
            )
        )
    ) {}
}