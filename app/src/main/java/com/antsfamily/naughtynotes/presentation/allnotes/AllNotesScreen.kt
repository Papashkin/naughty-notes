package com.antsfamily.naughtynotes.presentation.allnotes

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.allnotes.view.NoteCardExtended
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.home.view.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.noteform.formatToString
import com.antsfamily.naughtynotes.presentation.util.PREVIEW_NOTES
import com.antsfamily.naughtynotes.ui.theme.Padding
import java.time.LocalDate

@Composable
fun AllNotesScreen(
    snackbarHostState: SnackbarHostState,
    epoch: Long,
    viewModel: AllNotesViewModel = hiltViewModel<AllNotesViewModel, AllNotesViewModel.Factory> {
        it.create(epoch)
    },
    navigateBack: () -> Unit,
    navigateToNoteForm: (Int?) -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    when (val uiState = state.value) {
        is AllNotesUiState.Loading -> FullScreenLoading()
        is AllNotesUiState.Content -> ContentView(
            state = uiState,
            onNavigateBack = { navigateBack() },
            onEdit = { viewModel.onEditClick(it) },
            onDelete = { viewModel.onDeleteClick(it) },
            onAddNoteClick = { viewModel.onAddNoteClick() }
        )

        is AllNotesUiState.Error -> TODO()
    }

    LaunchedEffect(Unit) {
        viewModel.navigationBackFlow.collect {
            navigateBack()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.navigationToNoteFormFlow.collect {
            navigateToNoteForm(it)
        }
    }
    LaunchedEffect(Unit) {
        viewModel.deleteNoteFlow.collect {
            val snackbarResult = snackbarHostState
                .showSnackbar(
                    message = "Note deleted",
                    actionLabel = "Undo",
                    duration = SnackbarDuration.Short
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
    onAddNoteClick: () -> Unit,
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
                title = stringResource(R.string.all_notes_screen_title),
                onNavigationBack = { onNavigateBack() },
                actions = {
                    IconButton(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.surfaceContainer,
                            shape = RoundedCornerShape(bottomStart = 20.dp, topStart = 20.dp)
                        ),
                        onClick = { onAddNoteClick() }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "Add"
                        )
                    }
                }
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AllNotesWithIconPreview() {
    ContentView(
        state = AllNotesUiState.Content(
            date = LocalDate.of(2025, 6, 20),
            notes = PREVIEW_NOTES
        ),
        onNavigateBack = {},
        onEdit = {},
        onDelete = {},
        onAddNoteClick = {}
    )
}
