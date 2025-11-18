package com.antsfamily.naughtynotes.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.common.DebouncedTextButton
import com.antsfamily.naughtynotes.presentation.common.ShimmerLoading
import com.antsfamily.naughtynotes.presentation.home.HomeIntent
import com.antsfamily.naughtynotes.presentation.home.HomeUiState
import com.antsfamily.naughtynotes.presentation.util.debouncedClickable
import com.antsfamily.naughtynotes.ui.theme.Padding
import java.time.YearMonth

@Composable
fun HomeContentView(
    state: HomeUiState.Content,
    onIntentChanged: (HomeIntent) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
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

        Spacer(Modifier.height(Padding.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Padding.medium)
        ) {
            InfoCard(
                modifier = Modifier.weight(1f),
                value = state.datesWithNotes.size.toString(),
                descriptionText = R.string.home_screen_banner_total_notes_of_month,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Padding.regular)
            ) {

                InfoCard(
                    modifier = Modifier.weight(0.6f),
                    value = state.daysSinceLastNote.toString(),
                    descriptionText = R.string.home_screen_banner_days_since_last_note,
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )

                Card(
                    modifier = Modifier
                        .weight(0.4f)
                        .debouncedClickable { onIntentChanged(HomeIntent.Settings) },
                    shape = MaterialTheme.shapes.large,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(
                        Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_settings),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(top = Padding.x_small),
                            style = MaterialTheme.typography.labelMedium,
                            text = stringResource(R.string.home_screen_banner_settings)
                        )
                    }
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

        Spacer(Modifier.height(Padding.medium))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Padding.medium)
        ) {
            ShimmerLoading(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .size(60.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.large
                    ),
                durationMillis = 1000
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Padding.regular)
            ) {

                ShimmerLoading(
                    modifier = Modifier
                        .weight(0.6f)
                        .fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = MaterialTheme.shapes.large
                        ),
                    durationMillis = 1000
                )

                ShimmerLoading(
                    modifier = Modifier
                        .weight(0.4f)
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
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeContentPreview() {
    val state = HomeUiState.Content(
        yearMonth = YearMonth.now().minusMonths(1),
        isCurrentMonth = false,
        datesWithNotes = listOf(),
        daysSinceLastNote = 5
    )
    HomeContentView(state, {})
}