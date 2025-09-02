package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.noteform.NoteFormUiState
import com.antsfamily.naughtynotes.presentation.util.CREATE_NOTE_NOTE_LENGTH_MAX
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun NoteForm(
    state: NoteFormUiState.Content,
    keyboardController: SoftwareKeyboardController?,
    setPracticeType: (PracticeType) -> Unit,
    setPracticeLocation: (PracticeLocation) -> Unit,
    setIsProtected: (Boolean) -> Unit,
    setHasOrgasm: (Boolean) -> Unit,
    setHasPartnerOrgasm: (Boolean) -> Unit,
    setPainRate: (Int) -> Unit,
    setPleasureRate: (Int) -> Unit,
    setNote: (String) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = Padding.large)) {
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

        NoteFormSwitcher(
            titleId = R.string.note_form_screen_protection_switch_label,
            iconId = R.drawable.ic_protection,
            isChecked = state.isProtected
        ) {
            keyboardController?.hide()
            setIsProtected(it)
        }

        NoteFormSwitcher(
            titleId = R.string.note_form_screen_orgasm_switch_label,
            iconId = R.drawable.ic_bolt,
            isChecked = state.hasOrgasm
        ) {
            keyboardController?.hide()
            setHasOrgasm(it)
        }

        NoteFormSwitcher(
            titleId = R.string.note_form_screen_partner_orgasm_switch_label,
            iconId = R.drawable.ic_bolt,
            isChecked = state.hasPartnerOrgasm
        ) {
            keyboardController?.hide()
            setHasPartnerOrgasm(it)
        }

        Column(
            modifier = Modifier
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

@Preview(showBackground = true)
@Composable
private fun NoteFormPreview() {
    NoteForm(
        state = NoteFormUiState.Content.Default.copy(hasOrgasm = true, isProtected = true),
        keyboardController = null,
        setPracticeType = {},
        setPracticeLocation = {},
        setIsProtected = {},
        setHasOrgasm = {},
        setHasPartnerOrgasm = {},
        setPainRate = {},
        setPleasureRate = {},
        setNote = {},
    )
}