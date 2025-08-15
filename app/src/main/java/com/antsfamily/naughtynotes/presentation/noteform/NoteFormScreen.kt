package com.antsfamily.naughtynotes.presentation.noteform

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.antsfamily.domain.model.SexType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.home.view.FullScreenLoading
import com.antsfamily.naughtynotes.presentation.noteform.model.LoadingButton
import com.antsfamily.naughtynotes.presentation.noteform.view.RatingBar
import com.antsfamily.naughtynotes.presentation.noteform.view.SexTypeDropdown
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

const val CREATE_NOTE_NOTE_LENGTH_MAX = 60

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
            setSexType = { viewModel.setSexType(it) },
            setIsProtected = { viewModel.setIsProtected(it) },
            setPainRate = { viewModel.setPainRate(it) },
            setPleasureRate = { viewModel.setPleasureRate(it) },
            setNote = { viewModel.setNote(it) },
            onSaveButtonClick = { viewModel.onSaveButtonClick() },
            onNavigateBackClick = onNavigateBack
        )
    }
}

@Composable
fun NoteFormContent(
    state: NoteFormUiState.Content,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    setSexType: (SexType) -> Unit,
    setIsProtected: (Boolean) -> Unit,
    setPainRate: (Int) -> Unit,
    setPleasureRate: (Int) -> Unit,
    setNote: (String) -> Unit,
    onSaveButtonClick: () -> Unit,
    onNavigateBackClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .fillMaxSize()
            .imePadding()
            .statusBarsPadding()
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
                onNavigationBack = {
                    onNavigateBackClick()
                }
            )

            Column(modifier = Modifier.padding(horizontal = Padding.large)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Padding.xx_large),
                    text = stringResource(
                        R.string.note_form_screen_subtitle,
                        state.date.formatToString()
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )

                SexTypeDropdown(
                    modifier = Modifier.padding(top = Padding.xx_large, bottom = Padding.small),
                    selected = state.type
                ) {
                    setSexType(it)
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Padding.small)
                        .background(
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            shape = RoundedCornerShape(Padding.small)
                        ),
                ) {
                    Text(
                        text = stringResource(R.string.note_form_screen_protection_switch_label),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Padding.small),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Switch(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        checked = state.isProtected,
                        onCheckedChange = {
                            keyboardController?.hide()
                            setIsProtected(it)
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Padding.small)
                        .background(
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            shape = RoundedCornerShape(Padding.x_small)
                        )
                ) {
                    Text(
                        text = stringResource(R.string.note_form_screen_rate_bar_pain_label),
                        modifier = Modifier.padding(Padding.small),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    RatingBar(
                        rating = state.painRate,
                        selectedIcon = ImageVector.vectorResource(R.drawable.ic_broken_heart_filled),
                        defaultIcon = ImageVector.vectorResource(R.drawable.ic_broken_heart_outlined),
                        scaleMinLabel = R.string.note_form_screen_pain_rate_min_label,
                        scaleMaxLabel = R.string.note_form_screen_pain_rate_max_label,
                        onRatingChanged = { setPainRate(it) }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Padding.small)
                        .background(
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            shape = RoundedCornerShape(Padding.x_small)
                        )
                ) {
                    Text(
                        text = stringResource(R.string.note_form_screen_rate_bar_pleasure_label),
                        modifier = Modifier.padding(Padding.small),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    RatingBar(
                        rating = state.pleasureRate,
                        selectedIcon = ImageVector.vectorResource(R.drawable.ic_heart_filled),
                        defaultIcon = ImageVector.vectorResource(R.drawable.ic_heart_outlined),
                        scaleMinLabel = R.string.note_form_screen_pleasure_rate_min_label,
                        scaleMaxLabel = R.string.note_form_screen_pleasure_rate_max_label,
                        onRatingChanged = { setPleasureRate(it) }
                    )
                }

                OutlinedTextField(
                    label = {
                        Text(
                            text = stringResource(R.string.note_form_screen_note_text_field_label),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    value = state.note,
                    modifier = Modifier
                        .padding(top = Padding.tiny, bottom = Padding.medium)
                        .fillMaxWidth(),
                    onValueChange = { setNote(it) },
                    minLines = 4,
                    supportingText = {
                        Text(text = "${state.note.length}/$CREATE_NOTE_NOTE_LENGTH_MAX")
                    }
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .background(color = MaterialTheme.colorScheme.surface),
        ) {
            LoadingButton(
                modifier = Modifier
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateNoteContentPreview() {
    NoteFormContent(
        state = NoteFormUiState.Content.Default,
        keyboardController = null,
        focusManager = LocalFocusManager.current,
        setSexType = {},
        setIsProtected = {},
        setPainRate = {},
        setPleasureRate = {},
        setNote = {},
        onSaveButtonClick = {},
        onNavigateBackClick = {}
    )
}
