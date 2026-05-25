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

        if (state.recentActivities.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Padding.medium)
                ,
                text = stringResource(R.string.home_screen_subtitle_recent_activity),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleSmall
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(Padding.regular)
            ) {
                state.recentActivities.forEach { note ->
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
            verticalArrangement = Arrangement.spacedBy(Padding.regular)
        ) {
            ShimmerLoading(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                durationMillis = 1000
            )
            ShimmerLoading(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                durationMillis = 1000
            )
            ShimmerLoading(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.extraLarge
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
        recentActivities = PREVIEW_NOTES.take(3)
    )
    HomeContentView(state, {})
}