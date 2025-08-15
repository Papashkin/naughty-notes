package com.antsfamily.naughtynotes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.view.CalendarView
import com.antsfamily.naughtynotes.presentation.home.view.FullScreenLoading
import com.antsfamily.naughtynotes.ui.theme.Padding
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNoteForm: (Long) -> Unit,
    navigateToAllNotes: (Long) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.navigateToNoteFormEvent.collect {
            navigateToNoteForm(it)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.navigateToAllNotesEvent.collect {
            navigateToAllNotes(it)
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    when (val uiState = state.value) {
        is HomeUiState.Loading -> FullScreenLoading()
        is HomeUiState.Content -> HomeContent(
            state = uiState,
            onMonthChanged = { viewModel.onMonthChanged(it) },
            onTodayButtonClick = { viewModel.onTodayButtonClick() },
            onDayClick = { viewModel.onDayClick(it) }
        )
    }
}

@Composable
fun HomeContent(
    state: HomeUiState.Content,
    onMonthChanged: (YearMonth) -> Unit,
    onTodayButtonClick: () -> Unit,
    onDayClick: (LocalDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = Padding.large)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.home_screen_title),
                    maxLines = 1,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Start
                )
            }

            Spacer(Modifier.height(Padding.medium))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.home_screen_subtitle),
                    style = MaterialTheme.typography.bodyMedium
                )
                if (!state.isCurrentMonth) {
                    TextButton(onClick = { onTodayButtonClick() }) {
                        Text(
                            text = stringResource(R.string.home_screen_button_today),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(Modifier.height(Padding.medium))

            CalendarView(
                yearMonth = state.yearMonth,
                datesWithNotes = state.datesWithNotes,
                onMonthChanged = { onMonthChanged(it) },
                onDayClick = { onDayClick(it) }
            )

            Spacer(Modifier.height(Padding.medium))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Padding.medium)
            ) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(Padding.medium),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Box(Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = state.datesWithNotes.size.toString(),
                            style = MaterialTheme.typography.displayLarge,
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(Padding.regular),
                            text = stringResource(R.string.home_screen_banner_total_notes_of_month)
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .height(200.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(Padding.medium),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Box(Modifier.fillMaxSize()) {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            text = state.daysSinceLastNote.toString(),
                            style = MaterialTheme.typography.displayLarge,
                        )
                        Text(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(Padding.regular),
                            text = "Days since last note"
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeContentPreview() {
    val state = HomeUiState.Content(
        yearMonth = YearMonth.now(),
        isCurrentMonth = false,
        datesWithNotes = listOf(),
        daysSinceLastNote = 5
    )
    HomeContent(state, {}, {}, {})
}