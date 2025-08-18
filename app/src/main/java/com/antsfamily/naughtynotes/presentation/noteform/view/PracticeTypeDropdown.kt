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
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.util.toStringId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SexTypeDropdown(
    modifier: Modifier = Modifier,
    selected: PracticeType,
    onSelect: (PracticeType) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = { isExpanded = !isExpanded }
    ) {
        OutlinedTextField(
            value = stringResource(selected.toStringId()),
            textStyle = MaterialTheme.typography.bodyMedium,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.note_form_screen_sex_type_dropdown_label)) },
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
            PracticeType.entries.forEach { item ->
                DropdownMenuItem(
                    text = { Text(stringResource(item.toStringId())) },
                    onClick = {
                        onSelect(item)
                        isExpanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SexTypeDropdownPreview() {
    SexTypeDropdown(selected = PracticeType.MASTURBATION) {}
}