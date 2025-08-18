package com.antsfamily.naughtynotes.presentation.allnotes.view

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
import com.antsfamily.domain.model.PracticeType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.util.toDescriptionStringId
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding
import java.time.LocalDate
import java.time.Month

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
                Row(
                    modifier = Modifier.weight(0.5f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = Padding.small),
                        text = stringResource(note.type.toStringId()),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    if (note.type.isProtectionNeeded) {
                        Icon(
                            modifier = Modifier
                                .padding(start = Padding.x_small)
                                .size(14.dp),
                            imageVector = if (note.isProtected) {
                                ImageVector.vectorResource(R.drawable.ic_protection)
                            } else {
                                ImageVector.vectorResource(R.drawable.ic_protection_negative)
                            },
                            contentDescription = null
                        )
                    }
                }
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
                            modifier = Modifier.weight(0.3f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_broken_heart_outlined),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${note.painRate}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = Padding.x_small)
                            )
                        }

                        Row(
                            modifier = Modifier.weight(0.3f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(18.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_heart_outlined),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "${note.rate}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(horizontal = Padding.x_small)
                            )
                        }

                        Row(
                            modifier = Modifier.weight(0.3f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
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
    val note = PREVIEW_NOTES.first()
    NoteCardExtended(note, {}, {})
}

val PREVIEW_NOTES = listOf(
    NoteModel(
        3643,
        LocalDate.of(2025, Month.JULY, 12),
        PracticeType.ANAL,
        isProtected = true,
        hasOrgasm = false,
        painRate = 2,
        rate = 4,
        personalNote = ""
    ),
    NoteModel(
        2452,
        LocalDate.of(2025, Month.JULY, 22),
        PracticeType.VAGINAL,
        isProtected = true,
        hasOrgasm = false,
        painRate = 2,
        rate = 4,
        personalNote = "That was something crazy"
    ),
    NoteModel(
        1231,
        LocalDate.of(2025, Month.JULY, 15),
        PracticeType.ANAL,
        isProtected = false,
        hasOrgasm = false,
        painRate = 2,
        rate = 4,
        personalNote = ""
    ),
    NoteModel(
        75765,
        LocalDate.of(2025, Month.JULY, 20),
        PracticeType.VAGINAL,
        isProtected = true,
        hasOrgasm = true,
        painRate = 2,
        rate = 4,
        personalNote = "That was something crazy"
    )
)