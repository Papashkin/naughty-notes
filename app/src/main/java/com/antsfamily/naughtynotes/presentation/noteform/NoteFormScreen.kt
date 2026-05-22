package com.antsfamily.naughtynotes.presentation.noteform

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.domain.model.ErrorType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.common.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.noteform.model.LoadingButton
import com.antsfamily.naughtynotes.presentation.noteform.view.NoteForm
import com.antsfamily.naughtynotes.presentation.noteform.view.SuccessDialog
import com.antsfamily.naughtynotes.presentation.util.formatToString
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun NoteFormScreen(
    dateEpoch: Long,
    noteId: Int?,
    viewModel: NoteFormViewModel = hiltViewModel<NoteFormViewModel, NoteFormViewModel.Factory> {
        it.create(dateEpoch, noteId)
    },
    onNavigateBack: () -> Unit,
    onErrorSnackbarShow: (ErrorType) -> Unit
) {
    val (isSuccessDialogVisible, setIsSuccessDialogVisible) = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        viewModel.navigateBackEvent.collect {
            onNavigateBack()
        }
    }
    LaunchedEffect(Unit) {
        viewModel.noteSaveSnackBarEvent.collect {
            setIsSuccessDialogVisible(true)
        }
    }

    val state = viewModel.state.collectAsStateWithLifecycle()

    when (val uiState = state.value) {
        is NoteFormUiState.Loading -> FullScreenLoading()
        is NoteFormUiState.Error -> onErrorSnackbarShow(uiState.type)
        is NoteFormUiState.Content -> ContentView(
            uiState,
            onIntentChanged = viewModel::onIntent,
            onNavigateBack = { onNavigateBack() },
            onSaveButtonClick = { viewModel.onIntent(NoteFormIntent.SaveButtonClick) }
        )
    }

    if (isSuccessDialogVisible) {
        SuccessDialog {
            setIsSuccessDialogVisible(false)
            onNavigateBack()
        }
    }
}

@Composable
fun ContentView(
    state: NoteFormUiState.Content,
    onIntentChanged: (NoteFormIntent) -> Unit,
    onNavigateBack: () -> Unit,
    onSaveButtonClick: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp)
                    .background(color = MaterialTheme.colorScheme.surface)
            ) {
                TopBar(
                    modifier = Modifier
                        .padding(start = Padding.tiny)
                        .fillMaxWidth(),
                    title = stringResource(state.formType.toStringId()),
                    onNavigationBack = { onNavigateBack() }
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Padding.gigantic, bottom = Padding.small),
                    text = stringResource(
                        R.string.note_form_screen_subtitle,
                        state.date.formatToString()
                    ),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        bottomBar = {
            Box(
                modifier =
                    Modifier
                        .shadow(8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surface
                        ),
            ) {
                LoadingButton(
                    textId = R.string.note_form_screen_button_save,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Padding.medium),
                    onClick = {
                        keyboardController?.hide()
                        onSaveButtonClick()
                    },
                    loading = state.isSaveButtonLoadingVisible,
                    enabled = state.isSaveButtonEnabled,
                )
            }
        }
    ) { paddingValues ->
        NoteForm(
            state = state,
            modifier =
                Modifier
                    .padding(paddingValues)
        ) {
            onIntentChanged(it)
        }
    }
}