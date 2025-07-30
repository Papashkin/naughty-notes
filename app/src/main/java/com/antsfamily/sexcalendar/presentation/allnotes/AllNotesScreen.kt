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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import com.antsfamily.sexcalendar.R
import com.antsfamily.sexcalendar.presentation.allnotes.view.NoteCardExtended
import com.antsfamily.sexcalendar.presentation.createnote.formatToString
import com.antsfamily.sexcalendar.presentation.home.TopBar
import com.antsfamily.sexcalendar.presentation.home.view.FullScreenLoading
import com.antsfamily.sexcalendar.ui.theme.Padding
import java.time.LocalDate

@Composable
fun AllNotesScreen(
    snackbarHostState: SnackbarHostState,
    epoch: Long,
    viewModel: AllNotesViewModel = hiltViewModel<AllNotesViewModel, AllNotesViewModel.Factory>() {
        it.create(epoch)
    },
    navigateBack: () -> Unit,
) {
    val state = viewModel.state.collectAsState()

    when (val uiState = state.value) {
        is AllNotesUiState.Loading -> FullScreenLoading()
        is AllNotesUiState.Content -> ContentView(
            state = uiState,
            onNavigateBack = { navigateBack() },
            onEdit = { viewModel.onEditSwipe(it) },
            onDelete = { viewModel.onDeleteSwipe(it) }
        )

        is AllNotesUiState.Error -> TODO()
    }

    LaunchedEffect(Unit) {
        viewModel.navigationBackFlow.collect {
            navigateBack()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.deleteNoteFlow.collect {
            val snackbarResult = snackbarHostState
                .showSnackbar(
                    message = "Note deleted",
                    actionLabel = "Undo",
                    duration = SnackbarDuration.Long
                )
            when (snackbarResult) {
                SnackbarResult.Dismissed -> viewModel.onDeleteNoteSuccess()
                SnackbarResult.ActionPerformed -> viewModel.onDeleteNoteReverted()
            }
        }
    }
}

@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    state: AllNotesUiState.Content,
    onEdit: (NoteModel) -> Unit,
    onDelete: (NoteModel) -> Unit,
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
                title = stringResource(R.string.all_notes_screen_title),
                onNavigationBack = { onNavigateBack() }
            )

            Text(
                modifier = Modifier.padding(
                    start = 48.dp,
                    top = Padding.small,
                    bottom = Padding.regular
                ),
                text = stringResource(
                    R.string.all_notes_screen_subtitle,
                    state.date.formatToString()
                )
            )

            LazyColumn(modifier = Modifier.padding(Padding.regular)) {
                items(state.notes) { note ->
                    NoteCardExtended(
                        note = note,
                        onEditClick = { onEdit(it) },
                        onDeleteClick = { onDelete(it) }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AllNotesWithIconPreview() {
    ContentView(
        state = AllNotesUiState.Content(
            date = LocalDate.of(2025, 2, 20),
            notes = listOf(
                NoteModel(
                    356363,
                    LocalDate.now(),
                    SexType.TRIBADISM,
                    true,
                    1,
                    "something really-really long has written here just to see how it looks",
                    5
                ),
                NoteModel(
                    58578,
                    LocalDate.now(),
                    SexType.VAGINAL,
                    true,
                    2,
                    "something really-really long has written here just to see how it looks",
                    4
                ),
                NoteModel(
                    -1231,
                    LocalDate.now(),
                    SexType.MASTURBATION,
                    true,
                    2,
                    "something really-really long has written here just to see how it looks",
                    5
                )
            )
        ),
        onNavigateBack = {},
        onEdit = {},
        onDelete = {}
    )
}
