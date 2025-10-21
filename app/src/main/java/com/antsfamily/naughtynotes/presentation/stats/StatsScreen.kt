package com.antsfamily.naughtynotes.presentation.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.stats.model.StatChipType
import com.antsfamily.naughtynotes.presentation.stats.model.StatsItem
import com.antsfamily.naughtynotes.presentation.stats.view.ChipList
import com.antsfamily.naughtynotes.presentation.stats.view.StatsChart
import com.antsfamily.naughtynotes.presentation.stats.view.TimeDropdownItem
import com.antsfamily.naughtynotes.ui.theme.Padding


@Composable
fun StatsScreen(
    viewModel: StatsViewModel = hiltViewModel<StatsViewModel>(),
    onNavigateBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        TopBar(
            title = stringResource(R.string.statistic_screen_title),
            onNavigationBack = { onNavigateBack() }
        )

        SubHeader(Modifier.padding(Padding.large))

        StatsChart(
            modifier = Modifier.padding(top = Padding.x_large),
            //TODO implement proper items here
            items = listOf(
                StatsItem(Color.Red, "A" to 35),
                StatsItem(Color.Blue, "B" to 84),
                StatsItem(Color.Green, "C" to 20),
                StatsItem(Color.Yellow, "D" to 73),
            )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SubHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.statistic_screen_subtitle),
            style = MaterialTheme.typography.bodyMedium
        )
        ChipList(
            chips = StatChipType.entries,
            modifier = Modifier.padding(vertical = Padding.small)
        ) {}

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "for:",
                style = MaterialTheme.typography.bodyMedium
            )
            Box {
                TimeDropdownItem { }
            }
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatsScreenPreview1() {
    StatsScreen {}
}