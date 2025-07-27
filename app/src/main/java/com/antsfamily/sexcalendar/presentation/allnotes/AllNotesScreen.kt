package com.antsfamily.sexcalendar.presentation.allnotes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.sexcalendar.presentation.home.TopBar
import com.antsfamily.sexcalendar.presentation.home.view.FullScreenLoading
import com.antsfamily.sexcalendar.presentation.home.view.NoteCard
import com.antsfamily.sexcalendar.ui.theme.Padding
import java.time.Month

@Composable
fun AllNotesScreen(
    month: Month,
    year: Int,
    viewModel: AllNotesViewModel = hiltViewModel<AllNotesViewModel, AllNotesViewModel.Factory>() {
        it.create(month, year)
    },
    navigateBack: () -> Unit,
) {
    val state = viewModel.state.collectAsState()

    when (val uiState = state.value) {
        is AllNotesUiState.Loading -> FullScreenLoading()
        is AllNotesUiState.Content -> ContentView(notes = uiState.notes) {
            navigateBack()
        }

        is AllNotesUiState.Error -> TODO()
    }

    LaunchedEffect(Unit) {
        viewModel.navigationBackFlow.collect {
            navigateBack()
        }
    }
}

@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    notes: List<NoteModel>,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TopBar(
                modifier = Modifier
                    .padding(start = Padding.tiny)
                    .fillMaxWidth(),
                title = "All notes",
                onNavigationBack = {
                    onNavigateBack()
                }
            )

            LazyColumn(modifier = Modifier.padding(Padding.large)) {
                items(notes) {
                    NoteCard(it)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AllNotesWithIconPreview() {
    AllNotesScreen(year = 2025, month = Month.JULY) {}
}
