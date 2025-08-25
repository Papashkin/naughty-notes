package com.antsfamily.naughtynotes.presentation.noteform

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.view.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.noteform.view.NoteFormContent

@Composable
fun NoteFormScreen(
    dateEpoch: Long,
    noteId: Int?,
    viewModel: NoteFormViewModel = hiltViewModel<NoteFormViewModel, NoteFormViewModel.Factory> {
        it.create(dateEpoch, noteId)
    },
    onNavigateBack: () -> Unit,
    onSnackbarShow: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val message = if (noteId == null) {
        stringResource(R.string.note_form_screen_create_snackbar_success)
    } else {
        stringResource(R.string.note_form_screen_edit_snackbar_success)
    }

    LaunchedEffect(Unit) {
        viewModel.navigateBackEvent.collect {
            onNavigateBack()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.noteSaveSnackBarEvent.collect {
            onSnackbarShow(message)
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    when (val uiState = state.value) {
        is NoteFormUiState.Loading -> FullScreenLoading()
        is NoteFormUiState.Content -> NoteFormContent(
            uiState,
            keyboardController = keyboardController,
            focusManager = focusManager,
            setPracticeType = { viewModel.setPracticeType(it) },
            setPracticeLocation = { viewModel.setPracticeLocation(it) },
            setIsProtected = { viewModel.setIsProtected(it) },
            setHasOrgasm = { viewModel.setHasOrgasm(it) },
            setHasPartnerOrgasm = { viewModel.setHasPartnerOrgasm(it) },
            setPainRate = { viewModel.setPainRate(it) },
            setPleasureRate = { viewModel.setPleasureRate(it) },
            setNote = { viewModel.setNote(it) },
            onSaveButtonClick = { viewModel.onSaveButtonClick() },
            onNavigateBackClick = onNavigateBack
        )
    }
}
