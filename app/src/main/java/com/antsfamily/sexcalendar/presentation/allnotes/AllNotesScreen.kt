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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.sexcalendar.R
import com.antsfamily.sexcalendar.presentation.allnotes.view.NoteExtendedItem
import com.antsfamily.sexcalendar.presentation.createnote.formatToString
import com.antsfamily.sexcalendar.presentation.home.TopBar
import com.antsfamily.sexcalendar.presentation.home.view.FullScreenLoading
import com.antsfamily.sexcalendar.ui.theme.Padding
import java.time.LocalDate

@Composable
fun AllNotesScreen(
    epoch: Long,
    viewModel: AllNotesViewModel = hiltViewModel<AllNotesViewModel, AllNotesViewModel.Factory>() {
        it.create(epoch)
    },
    navigateBack: () -> Unit,
) {
    val state = viewModel.state.collectAsState()

    when (val uiState = state.value) {
        is AllNotesUiState.Loading -> FullScreenLoading()
        is AllNotesUiState.Content -> ContentView(state = uiState) {
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
    state: AllNotesUiState.Content,
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
                text = stringResource(R.string.all_notes_screen_subtitle, state.date.formatToString())
            )

            LazyColumn {
                itemsIndexed(state.notes) { index, note ->
                    NoteExtendedItem(
                        index = index,
                        note = note,
                        onEdit = {
                            //TODO implement edit
                        },
                        onDelete = {
                            //TODO implement note deletion (with undo action)
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AllNotesWithIconPreview() {
    AllNotesScreen(epoch = LocalDate.now().toEpochDay()) {}
}
