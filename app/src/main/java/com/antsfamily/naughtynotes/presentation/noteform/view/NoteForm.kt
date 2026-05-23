package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.noteform.NoteFormIntent
import com.antsfamily.naughtynotes.presentation.noteform.NoteFormUiState
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.ChipList
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.ChipType
import com.antsfamily.naughtynotes.presentation.noteform.model.chip.NoteChip
import com.antsfamily.naughtynotes.presentation.util.CREATE_NOTE_NOTE_LENGTH_MAX
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun NoteForm(
    state: NoteFormUiState.Content,
    modifier: Modifier = Modifier,
    onIntentChanged: (NoteFormIntent) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            modifier = Modifier.padding(
                top = Padding.medium,
                bottom = Padding.small,
                start = Padding.medium,
            ),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = stringResource(R.string.note_form_screen_practice_type_dropdown_label),
        )
        PracticeTypeChipGrid(
            chipList = state.types
        ) { type, isSelected ->
            onIntentChanged(NoteFormIntent.SetPracticeType(type, isSelected))
        }

        HorizontalDividerWithText(
            modifier = Modifier.padding(bottom = Padding.small),
            text = stringResource(R.string.note_form_screen_practice_location_dropdown_label),
        )
        PracticeLocationChipGrid(
            items = state.locations
        ) { location, isSelected ->
            onIntentChanged(NoteFormIntent.SetPracticeLocation(location, isSelected))
        }

        HorizontalDividerWithText(
            modifier = Modifier.padding(vertical = Padding.small),
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
            modifier = Modifier.padding(vertical = Padding.small),
            text = stringResource(R.string.note_form_screen_rate_bar_pleasure_label),
        )
        BubbleSlider(
            modifier = Modifier
                .testTag("experience_slider"),
            rate = state.experienceRate,
        ) {
            onIntentChanged(NoteFormIntent.SetExperienceRate(it))
        }

        HorizontalDividerWithText(
            modifier = Modifier.padding(vertical = Padding.small),
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
        onIntentChanged = {}
    )
}