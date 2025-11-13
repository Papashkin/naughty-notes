package com.antsfamily.naughtynotes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.view.CalendarView
import com.antsfamily.naughtynotes.presentation.common.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.home.view.InfoCard
import com.antsfamily.naughtynotes.ui.theme.Padding
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToNoteForm: (Long) -> Unit,
    navigateToAllNotes: (Long) -> Unit,
    navigateToProfile: () -> Unit
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
        viewModel.navigateToProfileEvent.collect {
            navigateToProfile()
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
            onProfileClick = { viewModel.onProfileClick() }
        )
    }
}

@Composable
fun PortraitHomeContent(
    state: HomeUiState.Content,
    onMonthChanged: (YearMonth) -> Unit,
    onTodayButtonClick: () -> Unit,
    onDayClick: (LocalDate) -> Unit,
    onProfileClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
            .padding(horizontal = Padding.large)
    ) {
        HomeHeader()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp),
            horizontalArrangement = Arrangement.End
        ) {
            if (!state.isCurrentMonth) {
                TextButton(onClick = { onTodayButtonClick() }) {
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
            onMonthChanged = { onMonthChanged(it) },
            onDayClick = { onDayClick(it) }
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
                        .clickable { onProfileClick() },
                    shape = RoundedCornerShape(Padding.medium),
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
                            imageVector = ImageVector.vectorResource(R.drawable.ic_profile),
                            contentDescription = null
                        )
                        Text(
                            modifier = Modifier.padding(top = Padding.x_small),
                            style = MaterialTheme.typography.labelMedium,
                            text = stringResource(R.string.home_screen_banner_profile)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeHeader() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.home_screen_title),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )

    Spacer(Modifier.height(Padding.medium))

    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(R.string.home_screen_subtitle),
        style = MaterialTheme.typography.bodyMedium
    )
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