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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.sexcalendar.presentation.home.view.CalendarView
import com.antsfamily.sexcalendar.presentation.home.view.FullScreenLoading
import com.antsfamily.sexcalendar.presentation.home.view.NotesListView
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToCreateNote: (Long) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.navigateToCreateNoteEvent.collect {
            navigateToCreateNote(it)
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
            onDayClick = { viewModel.onCreateNoteClick(it) }
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
    onDayClick: (LocalDate) -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "Journal",
                maxLines = 1,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            "Psst! Want to add a naughty note? Just click on a date \uD83D\uDE09.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(16.dp))

        CalendarView(
            yearMonth = yearMonth,
            notes = notes,
            isNavigationBackVisible = isNavigationBackVisible,
            isNavigationForwardVisible = isNavigationForwardVisible,
            onPreviousMonthClick = { onPreviousMonthClick() },
            onNextMonthClick = { onNextMonthClick() },
            onDayClick = { onDayClick(it) }
        )

        Spacer(Modifier.height(16.dp))

        NotesListView(notes = notes, onCreateNoteClick = onCreateNoteClick)
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun HomeContentPreview() {
    HomeContent(YearMonth.now(), listOf(), true, false, {}, {}, {}, {})
}