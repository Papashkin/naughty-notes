package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.domain.model.StatInfo
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.util.TestTag
import com.antsfamily.naughtynotes.presentation.util.toDropdownStringId
import com.antsfamily.naughtynotes.presentation.util.toStringId
import kotlin.enums.EnumEntries

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T> PracticeDropdown(
    modifier: Modifier = Modifier,
    title: String,
    entries: EnumEntries<T>,
    selected: T,
    noinline onSelect: (T) -> Unit
) where T : Enum<T> {

    var isExpanded by remember { mutableStateOf(false) }
    val tag = when (selected) {
        is PracticeType -> TestTag.NOTE_FORM_SCREEN_PRACTICE_TYPE_DROPDOWN
        is PracticeLocation -> TestTag.NOTE_FORM_SCREEN_LOCATION_DROPDOWN
        else -> throw IllegalStateException("wrong class")
    }
    ExposedDropdownMenuBox(
        modifier = modifier
            .height(48.dp)
            .shadow(elevation = 1.dp, shape = MaterialTheme.shapes.medium),
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        OutlinedTextField(
            value = when (selected) {
                is PracticeType -> stringResource(selected.toDropdownStringId())
                is PracticeLocation -> stringResource(selected.toDropdownStringId())
                else -> throw IllegalStateException("wrong class")
            },
            textStyle = MaterialTheme.typography.bodySmall,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text(title) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f),
            ),
            shape = MaterialTheme.shapes.medium,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .testTag(tag.value)
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            entries.filter {
                when (it) {
                    is StatInfo -> it.isNotUnknown
                    else -> false
                }
            }.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            when (item) {
                                is PracticeType -> stringResource(item.toStringId())
                                is PracticeLocation -> stringResource(item.toStringId())
                                else -> throw IllegalStateException("wrong class")
                            }
                        )
                    },
                    onClick = {
                        onSelect(item)
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PracticeDropdown1Preview() {
    PracticeDropdown(
        title = stringResource(R.string.note_form_screen_practice_location_dropdown_label),
        entries = PracticeLocation.entries,
        selected = PracticeLocation.UNKNOWN
    ) {}
}

@Preview(showBackground = true)
@Composable
private fun PracticeDropdown2Preview() {
    PracticeDropdown(
        title = stringResource(R.string.note_form_screen_practice_type_dropdown_label),
        entries = PracticeType.entries,
        selected = PracticeType.THREESOME
    ) {}
}