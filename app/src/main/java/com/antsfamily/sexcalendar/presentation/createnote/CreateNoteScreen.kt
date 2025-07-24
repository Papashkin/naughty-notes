package com.antsfamily.sexcalendar.presentation.createnote

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.antsfamily.domain.model.SexType
import com.antsfamily.sexcalendar.R
import com.antsfamily.sexcalendar.presentation.createnote.model.LoadingButton
import com.antsfamily.sexcalendar.presentation.createnote.view.RatingBar
import com.antsfamily.sexcalendar.presentation.createnote.view.SexTypeDropdown
import com.antsfamily.sexcalendar.presentation.home.TopBar
import com.antsfamily.sexcalendar.presentation.home.view.FullScreenLoading
import com.antsfamily.sexcalendar.ui.theme.Padding

const val CREATE_NOTE_NOTE_LENGTH_MAX = 60

@Composable
fun CreateNoteScreen(
    dateEpoch: Long,
    viewModel: CreateNoteViewModel = hiltViewModel<CreateNoteViewModel, CreateNoteViewModel.Factory> {
        it.create(dateEpoch)
    },
    onNavigateBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.navigateBackEvent.collect {
            onNavigateBack()
        }
    }

    val state = viewModel.state.collectAsState()

    when (val uiState = state.value) {
        is CreateNoteUiState.Loading -> FullScreenLoading()
        is CreateNoteUiState.Content -> CreateNoteContent(
            uiState,
            setSexType = { viewModel.setSexType(it) },
            setIsProtected = { viewModel.setIsProtected(it) },
            setPainRate = { viewModel.setPainRate(it) },
            setPleasureRate = { viewModel.setPleasureRate(it) },
            setNote = { viewModel.setNote(it) },
            onSaveButtonClicked = { viewModel.onSaveButtonClicked() },
            onNavigateBack = onNavigateBack
        )
    }
}

@Composable
fun CreateNoteContent(
    state: CreateNoteUiState.Content,
    setSexType: (SexType) -> Unit,
    setIsProtected: (Boolean) -> Unit,
    setPainRate: (Int) -> Unit,
    setPleasureRate: (Int) -> Unit,
    setNote: (String) -> Unit,
    onSaveButtonClicked: () -> Unit,
    onNavigateBack: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .statusBarsPadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 90.dp)
                .verticalScroll(rememberScrollState())
        ) {
            TopBar(
                modifier = Modifier
                    .padding(start = Padding.tiny)
                    .fillMaxWidth(),
                title = stringResource(R.string.note_screen_title),
                onNavigationBack = {
                    onNavigateBack()
                }
            )

            Column(modifier = Modifier.padding(horizontal = Padding.large)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = Padding.xx_large),
                    text = stringResource(
                        R.string.note_screen_subtitle,
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
                        text = stringResource(R.string.note_screen_protection_switch_label),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Padding.small),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Switch(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        checked = state.isProtected,
                        onCheckedChange = { setIsProtected(it) }
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
                        text = stringResource(R.string.note_screen_rate_bar_pain_label),
                        modifier = Modifier.padding(Padding.small),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    RatingBar(
                        rating = state.painRate,
                        selectedIcon = ImageVector.vectorResource(R.drawable.ic_pain),
                        defaultIcon = ImageVector.vectorResource(R.drawable.ic_pain_outlined),
                        scaleMinLabel = R.string.note_screen_pain_rate_min_label,
                        scaleMaxLabel = R.string.note_screen_pain_rate_max_label,
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
                        text = stringResource(R.string.note_screen_rate_bar_pleasure_label),
                        modifier = Modifier.padding(Padding.small),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    RatingBar(
                        rating = state.rate,
                        selectedIcon = Icons.Default.Favorite,
                        defaultIcon = Icons.Default.FavoriteBorder,
                        scaleMinLabel = R.string.note_screen_pleasure_rate_min_label,
                        scaleMaxLabel = R.string.note_screen_pleasure_rate_max_label,
                        onRatingChanged = { setPleasureRate(it) }
                    )
                }

                OutlinedTextField(
                    label = {
                        Text(
                            text = stringResource(R.string.note_screen_note_text_field_label),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    value = state.note,
                    modifier = Modifier
                        .padding(top = Padding.tiny, bottom = Padding.medium)
                        .fillMaxWidth(),
                    onValueChange = {
                        setNote(it)
                    },
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
                onClick = { onSaveButtonClicked() },
                loading = state.isSaveButtonLoadingVisible,
                enabled = state.isSaveButtonEnabled,
            ) {
                Text(text = stringResource(R.string.note_screen_button_save))
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreateNoteContentPreview(onNavigateBack: () -> Unit) {
    CreateNoteContent(
        state = CreateNoteUiState.Content.Default,
        {}, {}, {}, {}, {}, {}, {}
    )
}
