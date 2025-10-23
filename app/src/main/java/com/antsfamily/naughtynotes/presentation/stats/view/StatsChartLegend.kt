package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.util.COLORS_LIST
import com.antsfamily.naughtynotes.presentation.util.STATS_ANIMATION_DURATION
import com.antsfamily.naughtynotes.presentation.util.toStringId
import kotlinx.coroutines.delay


@Composable
fun StatsChartLegend(
    modifier: Modifier = Modifier,
    items: List<StatsItem>
) {
    var visibleItemCount by remember { mutableIntStateOf(0) }

    LaunchedEffect(items) {
        delay(STATS_ANIMATION_DURATION.toLong())
        items.indices.forEach { i ->
            visibleItemCount = i + 1
            delay(100)
        }
    }

    LazyColumn(modifier = modifier.fillMaxWidth()) {
        itemsIndexed(items) { index, item ->
            AnimatedVisibility(
                visible = index < visibleItemCount,
                enter = fadeIn(animationSpec = tween(300))
                        + slideInVertically(initialOffsetY = { it / 2 }),
                exit = fadeOut()
            ) {
                ListItem(
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    headlineContent = {
                        Text(
                            text = when (val type = item.info) {
                                is PracticeType -> stringResource(type.toStringId())
                                is PracticeLocation -> stringResource(type.toStringId())
                                else -> throw IllegalArgumentException("Unknown type")
                            },
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    leadingContent = {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(
                                    color = COLORS_LIST[index],
                                    shape = CircleShape
                                )
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatsChartLegendPreview() {
    StatsChartLegend(
        items = listOf(
            StatsItem(PracticeType.ANAL, 35),
            StatsItem(PracticeType.ORAL, 84),
            StatsItem(PracticeType.TRIBADISM, 63),
        )
    )
}