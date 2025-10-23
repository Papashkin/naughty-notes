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
import com.antsfamily.domain.model.PracticeType
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
                        text = "${it.value}",
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
                            color = COLORS_LIST[index],
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

private val COLORS_LIST = listOf(
    Color(0xFF9E9E9E),  // neutral gray
    Color(0xFF8E7CC3),  // lavender purple
    Color(0xFFFFB74D),  // warm amber
    Color(0xFF4FC3F7),  // light aqua blue
    Color(0xFFFF8A65),  // coral orange
    Color(0xFFA1887F),  // taupe brown
    Color(0xFF7986CB),  // periwinkle blue
    Color(0xFFDCE775),  // lime yellow
    Color(0xFFBA68C8),  // violet
    Color(0xFFF06292),  // soft pink
    Color(0xFF4DB6AC),  // teal
    Color(0xFFFFCC80),  // sand orange
    Color(0xFF81C784),  // green
    Color(0xFFAED581),  // light green
    Color(0xFF64B5F6),  // soft blue
    Color(0xFF90A4AE),  // gray-blue
    Color(0xFFFFB300),  // warm gold
    Color(0xFF9575CD),  // light violet
    Color(0xFF4DD0E1),  // turquoise
    Color(0xFF7986CB),  // blue-gray
    Color(0xFFE57373),  // soft red
)


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatsChartPreview() {
    StatsChart(
        items = listOf(
            StatsItem(PracticeType.ANAL, 35),
            StatsItem(PracticeType.ORAL, 84),
            StatsItem(PracticeType.TRIBADISM, 63),
        )
    )
}