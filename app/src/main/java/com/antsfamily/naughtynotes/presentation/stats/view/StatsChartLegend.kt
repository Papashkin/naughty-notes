package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.Other
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.util.COLORS_LIST
import com.antsfamily.naughtynotes.presentation.util.STATS_ANIMATION_DURATION
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding
import kotlinx.coroutines.delay
import java.math.BigDecimal
import java.math.RoundingMode


@Composable
fun StatsChartLegend(
    modifier: Modifier = Modifier,
    items: List<StatsItem>
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

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
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

@Composable
fun StatsLegendCard(
    modifier: Modifier = Modifier,
    index: Int,
    item: StatsItem,
) {
    ListItem(
        modifier = modifier
            .padding(vertical = Padding.tiny)
            .height(52.dp)
            .clip(MaterialTheme.shapes.medium),
        colors = ListItemDefaults.colors(
            containerColor = COLORS_LIST[index].copy(alpha = 0.2f),
            headlineColor = MaterialTheme.colorScheme.onSurface,
            supportingColor = MaterialTheme.colorScheme.onSurface
        ),
        headlineContent = {
            Text(
                text = when (val type = item.info) {
                    is PracticeType -> stringResource(type.toStringId())
                    is PracticeLocation -> stringResource(type.toStringId())
                    is Other -> stringResource(R.string.statistic_screen_legend_other)
                    else -> throw IllegalArgumentException("Unknown type")
                },
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingContent = {
            Row(
                modifier = modifier.padding(vertical = Padding.tiny),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(Padding.small, Alignment.CenterHorizontally)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .background(
                            color = COLORS_LIST[index],
                            shape = CircleShape
                        )
                )
                Text(
                    text = "${item.percent}%",
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatsLegendCardPreview() {
    StatsLegendCard(
        index = 1,
        item = StatsItem(
            info = PracticeType.TRIBADISM,
            value = 35,
            percent = BigDecimal.valueOf(67.34535).setScale(2, RoundingMode.HALF_EVEN)
        ),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatsChartLegendPreview() {
    StatsChartLegend(
        items = listOf(
            StatsItem(
                info = PracticeType.TRIBADISM,
                value = 35,
                percent = BigDecimal.valueOf(67.34535).setScale(2, RoundingMode.HALF_EVEN)
            ),
            StatsItem(
                info = PracticeType.ANAL,
                value = 15,
                percent = BigDecimal.valueOf(67.34535).setScale(2, RoundingMode.HALF_EVEN)
            ),
            StatsItem(
                info = PracticeType.THREESOME,
                value = 44,
                percent = BigDecimal.valueOf(67.34535).setScale(2, RoundingMode.HALF_EVEN)
            ),
        )
    )
}