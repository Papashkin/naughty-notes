package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.naughtynotes.presentation.stats.StatsIntent
import com.antsfamily.naughtynotes.ui.theme.Padding

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StatsSubHeader(
    modifier: Modifier = Modifier,
    onIntentCreated: (StatsIntent) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        StatsChipList(
            modifier = Modifier.padding(vertical = Padding.tiny)
        ) {
            onIntentCreated(StatsIntent.ShowByType(it))
        }

        TimeframeView {
            onIntentCreated(StatsIntent.ShowByTimeframe(it))
        }
    }
}