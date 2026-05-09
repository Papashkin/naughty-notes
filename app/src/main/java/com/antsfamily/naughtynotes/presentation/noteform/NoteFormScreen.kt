package com.antsfamily.naughtynotes.presentation.noteform

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
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
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .imePadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 90.dp)
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) {
                    detectTapGestures(onPress = { focusManager.clearFocus() })
                }
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
                    .padding(start = Padding.gigantic),
                text = stringResource(
                    R.string.note_form_screen_subtitle,
                    state.date.formatToString()
                ),
                style = MaterialTheme.typography.bodySmall
            )
            HorizontalDivider(thickness = Padding.medium, color = MaterialTheme.colorScheme.surface)
            NoteForm(
                state = state,
                keyboardController = keyboardController,
            ) {
                onIntentChanged(it)
            }
        }
        LoadingButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp)
                .fillMaxWidth(),
            onClick = {
                keyboardController?.hide()
                onSaveButtonClick()
            },
            loading = state.isSaveButtonLoadingVisible,
            enabled = state.isSaveButtonEnabled,
        ) {
            Text(text = stringResource(R.string.note_form_screen_button_save))
        }
    }
}