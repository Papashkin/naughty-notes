package com.antsfamily.naughtynotes.presentation.allnotes.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.allnotes.AllNotesIntent
import com.antsfamily.naughtynotes.presentation.util.PREVIEW_NOTES
import com.antsfamily.naughtynotes.presentation.util.debouncedClickable
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun NoteCard(
    note: NoteModel,
    onIntent: (AllNotesIntent.NoteCardIntent) -> Unit,
) {
    val (menuExpanded, setMenuExpanded) = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(vertical = Padding.small),
        shape = MaterialTheme.shapes.medium
    ) {
        ListItem(
            modifier = Modifier.fillMaxWidth(),
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            headlineContent = {
                Row(
                    modifier = Modifier.weight(0.5f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                }
            },
            trailingContent = {
                Icon(
                    modifier = Modifier.debouncedClickable { setMenuExpanded(true) },
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = null
                )
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { setMenuExpanded(false) }
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.all_notes_screen_menu_edit)) },
                        onClick = {
                            setMenuExpanded(false)
                            onIntent.invoke(AllNotesIntent.NoteCardIntent.EditNote(note))
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.all_notes_screen_menu_delete)) },
                        onClick = {
                            setMenuExpanded(false)
                            onIntent.invoke(AllNotesIntent.NoteCardIntent.DeleteNote(note))
                        }
                    )
                }
            },
            supportingContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Padding.x_small)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Padding.medium)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_heart_outlined),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_bolt),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = if (note.hasOrgasm) "Yes :)" else "No :(",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = Padding.x_small)
                            )
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
private fun NoteCardExtendedPreview() {
    Column {
        PREVIEW_NOTES.forEach { note ->
            NoteCard(note, {})
        }
    }
}
