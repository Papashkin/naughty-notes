package com.antsfamily.naughtynotes.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.common.DebouncedTextButton
import com.antsfamily.naughtynotes.presentation.common.ShimmerLoading
import com.antsfamily.naughtynotes.presentation.home.HomeIntent
import com.antsfamily.naughtynotes.presentation.home.HomeUiState
import com.antsfamily.naughtynotes.presentation.util.PREVIEW_NOTES
import com.antsfamily.naughtynotes.ui.theme.Padding
import java.time.YearMonth

@Composable
fun HomeContentView(
    state: HomeUiState.Content,
    onIntentChanged: (HomeIntent) -> Unit,
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.home_screen_subtitle),
            style = MaterialTheme.typography.bodyMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (!state.isCurrentMonth) {
                DebouncedTextButton(
                    onClick = { onIntentChanged(HomeIntent.ShowToday) }
                ) {
                    Text(
                        text = stringResource(R.string.home_screen_button_today),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        CalendarView(
            yearMonth = state.yearMonth,
            datesWithNotes = state.datesWithNotes,
            onMonthChanged = { onIntentChanged(HomeIntent.ChangeMonth(it)) },
            onDayClick = { onIntentChanged(HomeIntent.SelectDay(it)) }
        )

        if (state.lastThreeNotes.isNotEmpty()) {
            Spacer(Modifier.height(Padding.large))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Last three notes:",
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.bodyMedium
            )
            Column(
                modifier = Modifier.padding(vertical = Padding.small),
                verticalArrangement = Arrangement.spacedBy(Padding.x_small)
            ) {
                state.lastThreeNotes.forEach { note ->
                    HomeNoteCard(note)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeContentLoadingView(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(Modifier.height(36.dp))

        ShimmerLoading(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 420.dp, max = 440.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                    shape = MaterialTheme.shapes.large
                ),
        )

        Spacer(Modifier.height(Padding.large))

        Column(
            verticalArrangement = Arrangement.spacedBy(Padding.x_small)
        ) {
            ShimmerLoading(
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.large
                    ),
                durationMillis = 1000
            )
            ShimmerLoading(
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.large
                    ),
                durationMillis = 1000
            )
            ShimmerLoading(
                modifier = Modifier
                    .height(90.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.large
                    ),
                durationMillis = 1000
            )
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeContentPreview() {
    val state = HomeUiState.Content(
        yearMonth = YearMonth.now().minusMonths(1),
        isCurrentMonth = false,
        datesWithNotes = listOf(),
        daysSinceLastNote = 5,
        lastThreeNotes = PREVIEW_NOTES.take(3)
    )
    HomeContentView(state, {})
}