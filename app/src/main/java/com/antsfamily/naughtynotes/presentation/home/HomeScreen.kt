package com.antsfamily.naughtynotes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
    navigateToAllNotes: (Long) -> Unit,
    navigateToSettings: () -> Unit
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
    LaunchedEffect(Unit) {
        viewModel.navigateToSettingsEvent.collect {
            navigateToSettings()
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    when (val uiState = state.value) {
        is HomeUiState.Loading -> FullScreenLoading()
        is HomeUiState.Content -> PortraitHomeContent(
            state = uiState,
            onMonthChanged = { viewModel.onMonthChanged(it) },
            onTodayButtonClick = { viewModel.onTodayButtonClick() },
            onDayClick = { viewModel.onDayClick(it) },
            onSettingsClick = { viewModel.onSettingsClick() }
        )
    }
}

@Composable
fun PortraitHomeContent(
    state: HomeUiState.Content,
    onMonthChanged: (YearMonth) -> Unit,
    onTodayButtonClick: () -> Unit,
    onDayClick: (LocalDate) -> Unit,
    onSettingsClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .statusBarsPadding()
            .padding(horizontal = Padding.large)
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
                            style = MaterialTheme.typography.labelLarge,
                            text = stringResource(R.string.home_screen_banner_total_notes_of_month)
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(Padding.regular)
                ) {
                    Card(
                        modifier = Modifier.weight(0.6f),
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
                                style = MaterialTheme.typography.displaySmall,
                            )
                            Text(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .padding(Padding.regular),
                                style = MaterialTheme.typography.labelMedium,
                                text = stringResource(R.string.home_screen_banner_days_since_last_note)
                            )
                        }
                    }

                    Card(
                        modifier = Modifier
                            .weight(0.4f)
                            .clickable { onSettingsClick() },
                        shape = RoundedCornerShape(Padding.medium),
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
                                modifier = Modifier.size(36.dp),
                                imageVector = Icons.Rounded.Settings,
                                contentDescription = null
                            )
                            Text(
                                style = MaterialTheme.typography.labelMedium,
                                text = stringResource(R.string.home_screen_banner_settings)
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
    PortraitHomeContent(state, {}, {}, {}, {})
}