package com.antsfamily.naughtynotes.presentation.noteform.view

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.antsfamily.domain.model.PracticeLocation
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
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

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        OutlinedTextField(
            value = when (selected) {
                is PracticeType -> stringResource(selected.toStringId())
                is PracticeLocation -> stringResource(selected.toStringId())
                else -> throw IllegalStateException("wrong class")
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            onValueChange = {},
            readOnly = true,
            label = { Text(title) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false }
        ) {
            entries.forEach { item ->
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
        selected = PracticeLocation.TENT
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