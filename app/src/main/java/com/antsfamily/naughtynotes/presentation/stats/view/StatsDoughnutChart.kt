package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.Other
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.util.STATS_ANIMATION_DURATION
import kotlinx.coroutines.delay
import java.math.BigDecimal

@Composable
fun StatsDoughnutChart(
    modifier: Modifier = Modifier,
    items: List<StatsItem>,
) {
    var visibleItemCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(items) {
        visibleItemCount = 0
        delay(50)
        items.indices.forEach { i ->
            visibleItemCount = i + 1
            delay(50)
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(max = 300.dp)
        ,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        StatsChart(
            Modifier
                .weight(1f),
            items = items
        )

        LazyColumn(
            Modifier.weight(1f),
        ) {
            itemsIndexed(items) { index, item ->
                AnimatedVisibility(
                    visible = index < visibleItemCount,
                    enter = fadeIn(animationSpec = tween(STATS_ANIMATION_DURATION))
                            + slideInVertically(initialOffsetY = { it / 2 }),
                ) {
                    StatsLegendCard(
                        index = index,
                        item = item,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun StatsDoughnutChart_Preview() {
    val items = listOf(
        StatsItem(PracticeType.ANAL, 15, BigDecimal(100 / 6)),
        StatsItem(PracticeType.ORAL, 84, BigDecimal(100 / 7)),
        StatsItem(PracticeType.BDSM, 63, BigDecimal(54)),
        StatsItem(Other, 15, BigDecimal(10)),
    )
    StatsDoughnutChart(items = items)
}