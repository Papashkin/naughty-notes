package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.TopBar
import com.antsfamily.naughtynotes.presentation.noteform.NoteFormUiState
import com.antsfamily.naughtynotes.presentation.noteform.formatToString
import com.antsfamily.naughtynotes.presentation.noteform.model.LoadingButton
import com.antsfamily.naughtynotes.presentation.util.CREATE_NOTE_NOTE_LENGTH_MAX
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun NoteFormContent(
    state: NoteFormUiState.Content,
    keyboardController: SoftwareKeyboardController?,
    focusManager: FocusManager,
    setPracticeType: (PracticeType) -> Unit,
    setPracticeLocation: (PracticeLocation) -> Unit,
    setIsProtected: (Boolean) -> Unit,
    setHasOrgasm: (Boolean) -> Unit,
    setHasPartnerOrgasm: (Boolean) -> Unit,
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

                PracticeDropdown<PracticeType>(
                    modifier = Modifier.padding(top = Padding.large),
                    title = stringResource(R.string.note_form_screen_practice_type_dropdown_label),
                    entries = PracticeType.entries,
                    selected = state.type
                ) {
                    setPracticeType(it)
                }

                PracticeDropdown<PracticeLocation>(
                    modifier = Modifier.padding(top = Padding.small),
                    title = stringResource(R.string.note_form_screen_practice_location_dropdown_label),
                    entries = PracticeLocation.entries,
                    selected = state.location
                ) {
                    setPracticeLocation(it)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Padding.small)
                        .background(
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            shape = RoundedCornerShape(Padding.small)
                        )
                        .padding(Padding.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_protection),
                        null
                    )
                    Text(
                        text = stringResource(R.string.note_form_screen_protection_switch_label),
                        modifier = Modifier.padding(start = Padding.x_small),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = state.isProtected,
                        onCheckedChange = {
                            keyboardController?.hide()
                            setIsProtected(it)
                        }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Padding.small)
                        .background(
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            shape = RoundedCornerShape(Padding.small)
                        )
                        .padding(Padding.small),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.ic_bolt),
                        null
                    )
                    Text(
                        text = stringResource(R.string.note_form_screen_orgasm_switch_label),
                        modifier = Modifier.padding(start = Padding.x_small),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = state.hasOrgasm,
                        onCheckedChange = {
                            keyboardController?.hide()
                            setHasOrgasm(it)
                        }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Padding.small)
                        .background(
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            shape = RoundedCornerShape(Padding.small)
                        )
                        .padding(Padding.small),

                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_bolt),
                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.note_form_screen_partner_orgasm_switch_label),
                        modifier = Modifier.padding(start = Padding.x_small),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(Modifier.weight(1f))
                    Switch(
                        checked = state.hasPartnerOrgasm,
                        onCheckedChange = {
                            keyboardController?.hide()
                            setHasPartnerOrgasm(it)
                        }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = Padding.small)
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
                        .padding(top = Padding.small)
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
                        .padding(top = Padding.small, bottom = Padding.medium)
                        .fillMaxWidth(),
                    onValueChange = { setNote(it) },
                    minLines = 4,
                    supportingText = {
                        Text(text = "${state.note.length}/$CREATE_NOTE_NOTE_LENGTH_MAX")
                    }
                )
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

@Preview(showBackground = true)
@Composable
private fun CreateNoteContentPreview() {
    NoteFormContent(
        state = NoteFormUiState.Content.Default.copy(hasOrgasm = true, isProtected = true),
        keyboardController = null,
        focusManager = LocalFocusManager.current,
        setPracticeType = {},
        setPracticeLocation = {},
        setIsProtected = {},
        setHasOrgasm = {},
        setHasPartnerOrgasm = {},
        setPainRate = {},
        setPleasureRate = {},
        setNote = {},
        onSaveButtonClick = {},
        onNavigateBackClick = {}
    )
}