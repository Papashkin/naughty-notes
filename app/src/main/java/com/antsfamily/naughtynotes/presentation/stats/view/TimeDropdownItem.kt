package com.antsfamily.naughtynotes.presentation.stats.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.naughtynotes.presentation.stats.model.TimeSelectionItem
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDropdownItem(
    modifier: Modifier = Modifier,
    onSelect: (TimeSelectionItem) -> Unit
) {
    val (selectedItem, setSelectedItem) = remember { mutableStateOf(TimeSelectionItem.CURRENT_MONTH) }
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .clickable { isExpanded = !isExpanded }
            .padding(Padding.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(selectedItem.toStringId()),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.width(Padding.x_small))
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowDown,
            contentDescription = null,
            modifier = Modifier.size(18.dp)
        )
    }

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { isExpanded = false }
    ) {
        TimeSelectionItem.entries.forEach { item ->
            DropdownMenuItem(
                text = {
                    Text(stringResource(item.toStringId()))
                },
                trailingIcon = {
                    if (item == selectedItem) {
                        Icon(Icons.Rounded.Check, null)
                    } else {
                        null
                    }
                },
                onClick = {
                    setSelectedItem(item)
                    onSelect(item)
                    isExpanded = false
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PracticeDropdown1Preview() {
    TimeDropdownItem {}
}