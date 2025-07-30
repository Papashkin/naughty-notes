package com.antsfamily.sexcalendar.presentation.allnotes.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import com.antsfamily.sexcalendar.R
import com.antsfamily.sexcalendar.presentation.createnote.model.toDescriptionStringId
import com.antsfamily.sexcalendar.presentation.createnote.model.toStringId
import com.antsfamily.sexcalendar.ui.theme.Padding
import java.time.LocalDate

@Composable
fun NoteCardExtended(
    note: NoteModel,
    onEditClick: (NoteModel) -> Unit,
    onDeleteClick: (NoteModel) -> Unit
) {
    val (menuExpanded, setMenuExpanded) = remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.padding(vertical = Padding.small),
        shape = RoundedCornerShape(12.dp)
    ) {
        ListItem(
            modifier = Modifier.fillMaxWidth(),
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            headlineContent = {
                Text(
                    modifier = Modifier.padding(vertical = Padding.small),
                    text = stringResource(note.type.toStringId()),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            trailingContent = {
                Icon(
                    modifier = Modifier.clickable { setMenuExpanded(true) },
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
                            onEditClick(note)
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(R.string.all_notes_screen_menu_delete)) },
                        onClick = {
                            setMenuExpanded(false)
                            onDeleteClick(note)
                        }
                    )
                }
            },
            supportingContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Padding.x_small)
                ) {
                    Box {
                        Text(
                            text = note.personalNote.ifBlank { stringResource(note.type.toDescriptionStringId()) },
                            minLines = 2,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Row(
                            modifier = Modifier.weight(0.5f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_pain),
                                contentDescription = null
                            )
                            Text(
                                text = "${note.painRate}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = Padding.x_small)
                            )
                        }

                        Row(
                            modifier = Modifier.weight(0.5f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(16.dp),
                                imageVector = Icons.Default.Favorite,
                                contentDescription = null
                            )
                            Text(
                                text = "${note.rate}",
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
    val note =
        NoteModel(
            15315,
            LocalDate.now(),
            SexType.TRIBADISM,
            true,
            1,
            "",
            5
        )
    NoteCardExtended(note, {}, {})
}