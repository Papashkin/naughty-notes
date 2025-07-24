package com.antsfamily.sexcalendar.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.sexcalendar.R
import com.antsfamily.sexcalendar.presentation.home.view.CalendarView
import com.antsfamily.sexcalendar.presentation.home.view.FullScreenLoading
import com.antsfamily.sexcalendar.presentation.home.view.NotesListView
import com.antsfamily.sexcalendar.ui.theme.Padding
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToCreateNote: (Long) -> Unit,
    navigateToAllNotes: (Month) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.navigateToCreateNoteEvent.collect {
            navigateToCreateNote(it)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.navigateToAllNotesEvent.collect {
            navigateToAllNotes(it)
        }
    }

    val state = viewModel.state.collectAsState()

    when (val uiState = state.value) {
        is HomeUiState.Loading -> FullScreenLoading()
        is HomeUiState.Content -> HomeContent(
            yearMonth = uiState.yearMonth,
            notes = uiState.notes,
            isNavigationBackVisible = uiState.isNavigationBackVisible,
            isNavigationForwardVisible = uiState.isNavigationForwardVisible,
            onPreviousMonthClick = { viewModel.onPreviousMonthClick() },
            onNextMonthClick = { viewModel.onNextMonthClick() },
            onCreateNoteClick = { viewModel.onCreateNoteClick() },
            onShowAllClick = { viewModel.onShowAllClick() },
            onDayClick = { viewModel.onDayClick(it) }
        )
    }
}

@Composable
fun HomeContent(
    yearMonth: YearMonth,
    notes: List<NoteModel>,
    isNavigationBackVisible: Boolean,
    isNavigationForwardVisible: Boolean,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onCreateNoteClick: () -> Unit,
    onShowAllClick: () -> Unit,
    onDayClick: (LocalDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = Padding.large)
            .navigationBarsPadding()
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

        Text(
            text = stringResource(R.string.home_screen_subtitle),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(Padding.medium))

        CalendarView(
            yearMonth = yearMonth,
            notes = notes,
            isNavigationBackVisible = isNavigationBackVisible,
            isNavigationForwardVisible = isNavigationForwardVisible,
            onPreviousMonthClick = { onPreviousMonthClick() },
            onNextMonthClick = { onNextMonthClick() },
            onDayClick = { onDayClick(it) }
        )

        Spacer(Modifier.height(Padding.medium))

        NotesListView(
            notes = notes,
            onCreateNoteClick = onCreateNoteClick,
            onShowAllClick = onShowAllClick
        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeContentPreview() {
    HomeContent(
        YearMonth.now(),
        listOf(),
        isNavigationBackVisible = true,
        isNavigationForwardVisible = false,
        {},
        {},
        {},
        {},
        {}
    )
}