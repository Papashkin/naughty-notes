package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.stats.model.getTotalSum
import com.antsfamily.naughtynotes.presentation.util.COLORS_LIST
import com.antsfamily.naughtynotes.presentation.util.STATS_ANIMATION_DURATION
import java.math.BigDecimal


@Composable
fun StatsChart(
    modifier: Modifier = Modifier,
    items: List<StatsItem>
) {
    val rotationAnim = remember { Animatable(initialValue = -90f) }
    val totalStatsSum = items.getTotalSum()
    val finalRotationValue = 90f

    LaunchedEffect(items) {
        rotationAnim.snapTo(-90f)

        rotationAnim.animateTo(
            targetValue = finalRotationValue,
            animationSpec = tween(
                durationMillis = STATS_ANIMATION_DURATION,
                easing = LinearEasing
            )
        )
    }

    val currentSweepAngle = rotationAnim.value

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.aspectRatio(1f)
        ) {
            Canvas(modifier = Modifier.fillMaxWidth()) {
                val width = size.width
                val radius = width
                val strokeWidth = radius * .2f

                var startAngle = -90f

                items.forEachIndexed { index, item ->
                    val sweepAngle = item.percent(totalStatsSum) / 2f

                    if (startAngle <= currentSweepAngle) {
                        drawArc(
                            color = COLORS_LIST[index],
                            startAngle = startAngle,
                            sweepAngle = sweepAngle.coerceAtMost(currentSweepAngle - startAngle),
                            useCenter = false,
                            topLeft = Offset(-strokeWidth, strokeWidth / 2),
                            size = Size(width - strokeWidth, width - strokeWidth),
                            style = Stroke(strokeWidth)
                        )
                    }
                    startAngle += sweepAngle
                }
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatsChartPreview() {
    StatsChart(
        items = listOf(
            StatsItem(PracticeType.ANAL, 15, BigDecimal(100)),
            StatsItem(PracticeType.ORAL, 84, BigDecimal(100)),
            StatsItem(PracticeType.TRIBADISM, 63, BigDecimal(100)),
        )
    )
}