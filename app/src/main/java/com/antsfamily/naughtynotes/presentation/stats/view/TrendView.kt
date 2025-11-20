package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun TrendView(
    modifier: Modifier = Modifier,
    trends: List<Float>
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceContainer,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        if (trends.count { it > 0 } < 3) {
            Text(
                modifier = Modifier
                    .padding(top = Padding.medium)
                    .align(Alignment.Center),
                text = stringResource(R.string.statistic_screen_trend_empty),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        } else {
            Column(modifier = Modifier.padding(Padding.medium)) {
                Text(
                    text = stringResource(R.string.statistic_screen_trend_title, trends.size),
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                TrendChart(
                    modifier = Modifier.fillMaxHeight(),
                    items = trends
                )
            }
        }
    }
}

@Preview
@Composable
private fun TrendView1Preview() {
    TrendView(Modifier.height(200.dp), trends = listOf(4f, 6f, 3f, 9f, 7f))
}


@Preview
@Composable
private fun TrendView2Preview() {
    TrendView(Modifier.height(200.dp), trends = listOf(4f, 6f))
}