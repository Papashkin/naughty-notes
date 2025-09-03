package com.antsfamily.naughtynotes.presentation.allnotes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
    val date: LocalDate = rememberSaveable { LocalDate.ofEpochDay(epoch) }

    val state = viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        TopBar(
            title = stringResource(R.string.all_notes_screen_title),
            onNavigationBack = { navigateBack() },
            actions = {
                IconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(bottomStart = 20.dp, topStart = 20.dp)
                    ),
                    onClick = { viewModel.onAddNoteClick() }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = stringResource(R.string.all_notes_screen_icon_add)
                    )
                }
            }
        )

        Text(
            modifier = Modifier.padding(start = Padding.huge),
            style = MaterialTheme.typography.bodyMedium,
            text = stringResource(
                R.string.all_notes_screen_subtitle,
                date.formatToString()
            )
        )

        when (val uiState = state.value) {
            is AllNotesUiState.Loading -> FullScreenLoading()
            is AllNotesUiState.EmptyContent -> EmptyNotesList()
            is AllNotesUiState.Error -> { /* TODO implement error state */
            }

            is AllNotesUiState.Content -> LazyColumn(
                modifier = Modifier.padding(Padding.regular)
            ) {
                items(uiState.notes) { note ->
                    NoteCardExtended(
                        note = note,
                        onEditClick = { viewModel.onEditClick(note) },
                        onDeleteClick = { viewModel.onDeleteClick(note) }
                    )
                }
            }
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
}

@Composable
fun EmptyNotesList() {
    Row(
        modifier = Modifier
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                imageVector = ImageVector.vectorResource(R.drawable.ic_question),
                contentDescription = stringResource(R.string.all_notes_screen_icon_no_notes),
                tint = MaterialTheme.colorScheme.onSurface
            )
            Text(
                modifier = Modifier.padding(top = Padding.medium),
                text = stringResource(R.string.all_notes_screen_empty),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AllNotesWithIconPreview() {
    LazyColumn(modifier = Modifier.padding(Padding.regular)) {
        items(PREVIEW_NOTES) { note ->
            NoteCardExtended(
                note = note,
                onEditClick = { },
                onDeleteClick = { }
            )
        }
    }
}
