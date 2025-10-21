package com.antsfamily.naughtynotes.presentation.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.stats.view.StatsChart

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel<StatsViewModel>()
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        StatsChart(
            items = listOf(
                StatsItem(Color.Red, "A" to 35),
                StatsItem(Color.Blue, "B" to 84),
                StatsItem(Color.Green, "C" to 20),
                StatsItem(Color.Yellow, "D" to 73),
            )
        )
    }
}