package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun TrendChart(
    modifier: Modifier = Modifier,
    items: List<Float> = emptyList()
) {
    val chartColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
    val maxValue = items.max()
    val minValue = items.min()

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        val width = size.width
        val height = size.height
        val stepSize = width / (items.size - 1)

        val points = items.mapIndexed { i, v ->
            val x = i * stepSize
            val yRatio =
                if (maxValue == minValue) 0.5f
                else (v - minValue) / (maxValue - minValue)

            val y = height - yRatio * height
            Offset(x, y)
        }.toMutableList()

        points += points.last()

        val smoothPath = Path().apply {
            moveTo(points.first().x, points.first().y)

            points.zipWithNext().forEach { (p0, p1) ->
                val midX = (p0.x + p1.x) / 2f
                val midY = (p0.y + p1.y) / 2f

                quadraticTo(
                    p0.x, p0.y,
                    midX, midY
                )
            }
        }

        drawPath(
            path = smoothPath,
            color = chartColor,
            style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}