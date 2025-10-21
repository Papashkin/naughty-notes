package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.stats.model.getTotalSum
import com.antsfamily.naughtynotes.presentation.util.degreeToAngle
import com.antsfamily.naughtynotes.ui.theme.Padding
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun StatsChart(
    modifier: Modifier = Modifier,
    animDuration: Int = 1000,
    items: List<StatsItem>
) {
    val rotationAnim = remember { Animatable(initialValue = -90f) }
    val totalStatsSum = items.getTotalSum()
    val finalRotationValue = 270f

    LaunchedEffect(rotationAnim) {
        rotationAnim.animateTo(
            targetValue = finalRotationValue,
            animationSpec = tween(
                durationMillis = animDuration,
                delayMillis = 100,
                easing = LinearEasing
            )
        )
    }

    val currentSweepAngle = rotationAnim.value

    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.padding(Padding.medium),
            contentAlignment = Alignment.Center
        ) {

            val textMeasurer = rememberTextMeasurer()
            val textMeasureResults = remember(items) {
                items.map {
                    textMeasurer.measure(
                        text = "${it.data.second}",
                        style = TextStyle(fontSize = 18.sp)
                    )
                }
            }

            Canvas(
                modifier = Modifier
                    .size(300.dp)
                    .padding(Padding.medium)
            ) {
                val width = size.width
                val radius = width / 2f
                val strokeWidth = radius * .25f

                var startAngle = -90f

                items.forEachIndexed { index, item ->
                    val sweepAngle = item.percent(totalStatsSum)
                    val angleInRadians = (startAngle + sweepAngle / 2).degreeToAngle

                    if (startAngle <= currentSweepAngle) {
                        drawArc(
                            color = item.color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle.coerceAtMost(currentSweepAngle - startAngle),
                            useCenter = false,
                            topLeft = Offset(strokeWidth / 2, strokeWidth / 2),
                            size = Size(width - strokeWidth, width - strokeWidth),
                            style = Stroke(strokeWidth)
                        )
                    }

                    val textMeasureResult = textMeasureResults[index]
                    val textSize = textMeasureResult.size
                    val textCenter = textSize.center

                    if (currentSweepAngle == finalRotationValue) {
                        drawText(
                            textLayoutResult = textMeasureResult,
                            color = Color.Black,
                            topLeft = Offset(
                                -textCenter.x + center.x + (radius + strokeWidth / 2) * cos(
                                    angleInRadians
                                ),
                                -textCenter.y + center.y + (radius + strokeWidth / 2) * sin(
                                    angleInRadians
                                )
                            )
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
            StatsItem(Color.Red, "A" to 35),
            StatsItem(Color.Blue, "B" to 84),
            StatsItem(Color.Green, "C" to 11),
            StatsItem(Color.Yellow, "D" to 63),
        )
    )
}