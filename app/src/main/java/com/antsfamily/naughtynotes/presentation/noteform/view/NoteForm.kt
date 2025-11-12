package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    Column {
        HorizontalDividerWithText(text = "General")
        PracticeDropdown<PracticeType>(
            modifier = Modifier.padding(
                top = Padding.regular,
                start = Padding.large,
                end = Padding.large
            ),
            title = stringResource(R.string.note_form_screen_practice_type_dropdown_label),
            entries = PracticeType.entries,
            selected = state.type
        ) {
            setPracticeType(it)
        }

        PracticeDropdown<PracticeLocation>(
            modifier = Modifier.padding(
                top = Padding.medium,
                start = Padding.large,
                end = Padding.large
            ),
            title = stringResource(R.string.note_form_screen_practice_location_dropdown_label),
            entries = PracticeLocation.entries,
            selected = state.location
        ) {
            setPracticeLocation(it)
        }

        HorizontalDividerWithText(
            modifier = Modifier.padding(top = Padding.medium),
            text = "Details"
        )

        ChipList(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = Padding.regular,
                    start = Padding.large,
                    end = Padding.large
                ),
            Triple(
                R.string.note_form_screen_protection_label, state.isProtected
            ) {
                keyboardController?.hide()
                setIsProtected(it)
            },
            Triple(R.string.note_form_screen_your_orgasm_label, state.hasOrgasm) {
                keyboardController?.hide()
                setHasOrgasm(it)
            },
            Triple(R.string.note_form_screen_partner_orgasm_label, state.hasPartnerOrgasm) {
                keyboardController?.hide()
                setHasPartnerOrgasm(it)
            },
        )

        HorizontalDividerWithText(
            modifier = Modifier.padding(top = Padding.medium),
            text = stringResource(R.string.note_form_screen_rate_bar_pleasure_label),
        )

        Column(
            modifier = Modifier
                .padding(top = Padding.small)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(Padding.x_small)
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
                rating = state.pleasureRate,
                selectedIcon = ImageVector.vectorResource(R.drawable.ic_heart_filled),
                defaultIcon = ImageVector.vectorResource(R.drawable.ic_heart_outlined),
                onRatingChanged = { setPleasureRate(it) }
            )
        }

        HorizontalDividerWithText(
            modifier = Modifier.padding(top = Padding.medium),
            text = stringResource(R.string.note_form_screen_rate_bar_pain_label),
        )

        Column(
            modifier = Modifier
                .padding(top = Padding.small)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(Padding.x_small)
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
                rating = state.painRate,
                selectedIcon = ImageVector.vectorResource(R.drawable.ic_broken_heart_filled),
                defaultIcon = ImageVector.vectorResource(R.drawable.ic_broken_heart_outlined),
                onRatingChanged = { setPainRate(it) }
            )
        }

        HorizontalDividerWithText(
            modifier = Modifier.padding(top = Padding.medium),
            text = "Feedback",
        )

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
                .padding(
                    top = Padding.x_small,
                    bottom = Padding.medium,
                    start = Padding.large,
                    end = Padding.large
                )
                .fillMaxWidth(),
            onValueChange = { setNote(it) },
            minLines = 4,
            supportingText = {
                Text(text = "${state.note.length}/$CREATE_NOTE_NOTE_LENGTH_MAX")
            }
        )
    }
}

@Composable
fun ChipList(
    modifier: Modifier = Modifier,
    vararg values: Triple<Int, Boolean, (Boolean) -> Unit>
) {
    FlowRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Padding.small),
        verticalArrangement = Arrangement.SpaceEvenly,
        itemVerticalAlignment = Alignment.CenterVertically,
        maxLines = 2
    ) {
        values.forEach { value ->
            Button(
                onClick = {
                    value.third(value.second.not())
                },
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (value.second) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surface
                    },
                    contentColor = if (value.second) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    },
                ),
            ) {
                Text(
                    text = stringResource(value.first),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
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