package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.ui.theme.Padding

private const val ACTIVITY_BAR_CHART_ANIMATION_DURATION = 800

@Composable
fun ActivityBarChar(
    modifier: Modifier = Modifier,
    activities: Map<String, Int>,
    barColor: Color = MaterialTheme.colorScheme.primary,
) {
    var show by remember { mutableStateOf(false) }

    val values = activities.values.toList()
    val labels = activities.keys.toList()

    val maxValue = values.maxOrNull()?.toFloat() ?: 0f
    val textMeasurer = rememberTextMeasurer()

    val colorScheme = MaterialTheme.colorScheme

    val animatedProgress by animateFloatAsState(
        targetValue = if (show) 1f else 0f,
        animationSpec = tween(
            durationMillis = ACTIVITY_BAR_CHART_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        ),
        label = "chart_animation"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(vertical = Padding.large)
    ) {
        val progressOfAnimation = animatedProgress

        val leftPadding = 80f
        val bottomPadding = 80f

        val chartWidth = size.width - leftPadding
        val chartHeight = size.height - bottomPadding

        val barWidth = chartWidth / (values.size * 2)

        // Y AXIS LABELS
        val steps = if (maxValue > 16) 5 else 3

        for (i in 0..steps) {

            val value = (maxValue /steps) * i
            val y = chartHeight - (chartHeight / steps) * i

            drawLine(
                color = colorScheme.surfaceContainerHighest,
                start = Offset(leftPadding, y),
                end = Offset(size.width, y),
                strokeWidth = 1f
            )

            drawText(
                textMeasurer = textMeasurer,
                text = value.toInt().toString(),
                topLeft = Offset(16f, y - 20f),
                style = TextStyle(
                    color = colorScheme.onSurface,
                )
            )
        }

        // BARS + X LABELS
        values.withIndex().forEach { (index, value) ->
            val animatedValue = value * progressOfAnimation
            val barHeight = (animatedValue / maxValue) * chartHeight
            val x = leftPadding + index * barWidth * 2 + barWidth / 2

            drawRoundRect(
                color = barColor,
                topLeft = Offset(
                    x = x,
                    y = chartHeight - barHeight
                ),
                size = Size(
                    width = barWidth,
                    height = barHeight
                ),
                cornerRadius = CornerRadius(12f, 12f)
            )

            // Measure text
            val textLayoutResult = textMeasurer.measure(labels[index])

            // Center text under bar
            val textX = x + (barWidth / 2) - (textLayoutResult.size.width / 2)

            // X LABEL
            drawText(
                textMeasurer = textMeasurer,
                text = labels[index],
                topLeft = Offset(
                    x = textX,
                    y = chartHeight + 16f
                ),
                style = TextStyle(
                    color = colorScheme.onSurface,
                )
            )
        }
    }

    LaunchedEffect(activities) {
        show = activities.isNotEmpty()
    }
}

@Preview(showBackground = true)
@Composable
private fun ActivityBarChar_Preview() {
    val activities = mapOf(
        "Jan" to 10,
        "Feb" to 22,
        "Mar" to 15,
        "Apr" to 9,
    )
    ActivityBarChar(
        modifier = Modifier.padding(16.dp),
        activities = activities,
    )
}