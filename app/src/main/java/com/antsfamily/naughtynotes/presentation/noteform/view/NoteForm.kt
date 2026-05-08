package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.noteform.NoteFormIntent
import com.antsfamily.naughtynotes.presentation.noteform.NoteFormUiState
import com.antsfamily.naughtynotes.presentation.noteform.model.RatingType
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.ChipList
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.ChipType
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.NoteChip
import com.antsfamily.naughtynotes.presentation.util.CREATE_NOTE_NOTE_LENGTH_MAX
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun NoteForm(
    state: NoteFormUiState.Content,
    keyboardController: SoftwareKeyboardController?,
    onIntentChanged: (NoteFormIntent) -> Unit,
) {
    Column {
        HorizontalDividerWithText(
            modifier = Modifier.padding(bottom = Padding.medium),
            text = "General"
        )
        PracticeDropdown<PracticeType>(
            modifier = Modifier.padding(horizontal = Padding.large),
            title = stringResource(R.string.note_form_screen_practice_type_dropdown_label),
            entries = PracticeType.entries,
            selected = state.type,
        ) {
            onIntentChanged(
                NoteFormIntent.SetPracticeType(it)
            )
        }

        PracticeDropdown<PracticeLocation>(
            modifier = Modifier.padding(
                top = Padding.medium,
                start = Padding.large,
                end = Padding.large
            ),
            title = stringResource(R.string.note_form_screen_practice_location_dropdown_label),
            entries = PracticeLocation.entries,
            selected = state.location,
        ) {
            onIntentChanged(
                NoteFormIntent.SetPracticeLocation(it)
            )
        }

        HorizontalDividerWithText(
            modifier = Modifier.padding(vertical = Padding.medium),
            text = "Details"
        )

        ChipList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.large),
            chips = state.chips
        ) { type, isSelected ->
            onIntentChanged(NoteFormIntent.SetNoteChipSelectionChanged(type, isSelected))
        }

        HorizontalDividerWithText(
            modifier = Modifier.padding(vertical = Padding.medium),
            text = stringResource(R.string.note_form_screen_rate_bar_pain_label),
        )

        Column(
            modifier = Modifier
                .padding(top = Padding.small)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraSmall
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Padding.x_small, horizontal = Padding.large),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.note_form_screen_pain_rate_min_label),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = stringResource(R.string.note_form_screen_pain_rate_max_label),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            RatingBar(
                type = RatingType.PAIN,
                rating = state.painRate,
                onRatingChanged = {
                    onIntentChanged(
                        NoteFormIntent.SetPainRate(it)
                    )
                }
            )
        }

        HorizontalDividerWithText(
            modifier = Modifier.padding(vertical = Padding.medium),
            text = stringResource(R.string.note_form_screen_rate_bar_pleasure_label),
        )

        Column(
            modifier = Modifier
                .padding(top = Padding.small)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = MaterialTheme.shapes.extraSmall
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Padding.x_small, horizontal = Padding.large),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.note_form_screen_pleasure_rate_min_label),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = stringResource(R.string.note_form_screen_pleasure_rate_max_label),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
            RatingBar(
                type = RatingType.PLEASURE,
                rating = state.pleasureRate,
                onRatingChanged = {
                    onIntentChanged(
                        NoteFormIntent.SetPleasureRate(it)
                    )
                }
            )
        }

        HorizontalDividerWithText(
            modifier = Modifier.padding(vertical = Padding.medium),
            text = "Feedback",
        )

        OutlinedTextField(
            label = {
                Text(
                    text = stringResource(R.string.note_form_screen_note_text_field_label),
                    style = MaterialTheme.typography.bodySmall
                )
            },
            textStyle = MaterialTheme.typography.bodySmall,
            value = state.note,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Padding.x_small,
                    bottom = Padding.medium,
                    start = Padding.large,
                    end = Padding.large
                ),
            onValueChange = {
                onIntentChanged(
                    NoteFormIntent.SetNote(it)
                )
            },
            minLines = 4,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f),
            ),
            shape = MaterialTheme.shapes.medium,
            supportingText = {
                Text(text = "${state.note.length}/$CREATE_NOTE_NOTE_LENGTH_MAX")
            }
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun NoteFormPreview() {
    val chips = ChipType.entries.map {
        NoteChip(it, true)
    }

    NoteForm(
        state = NoteFormUiState.Content.Default.copy(chips = chips),
        keyboardController = null,
        onIntentChanged = {}
    )
}