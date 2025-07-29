package com.antsfamily.sexcalendar.presentation.allnotes.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import com.antsfamily.sexcalendar.R
import com.antsfamily.sexcalendar.presentation.createnote.formatToString
import com.antsfamily.sexcalendar.presentation.createnote.model.toStringId
import com.antsfamily.sexcalendar.ui.theme.Padding
import java.time.LocalDate

@Composable
fun NoteCardExtended(note: NoteModel, index: Int) {
    ListItem(
        modifier = Modifier.fillMaxWidth(),
        colors = ListItemDefaults.colors(
            containerColor = if (index % 2 == 0) {
                MaterialTheme.colorScheme.surfaceContainer
            } else {
                MaterialTheme.colorScheme.surfaceContainerLow
            }
        ),
        overlineContent = {
            Text(
                modifier = Modifier.padding(vertical = Padding.x_small),
                text = note.date.formatToString()
            )
        },
        headlineContent = {
            Text(
                modifier = Modifier.padding(vertical = Padding.x_small),
                text = stringResource(note.type.toStringId())
            )
        },
        supportingContent = {
            Column(
                modifier = Modifier.padding(top = Padding.x_small),
                verticalArrangement = Arrangement.spacedBy(Padding.regular)
            ) {
                Row {
                    Row(modifier = Modifier.weight(0.5f)) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_pain),
                            contentDescription = null
                        )

                        Text(
                            "${note.painRate}/5",
                            modifier = Modifier.padding(horizontal = Padding.small)
                        )
                    }

                    Row(modifier = Modifier.weight(1f)) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null
                        )

                        Text(
                            "${note.rate}/5",
                            modifier = Modifier.padding(horizontal = Padding.small)
                        )
                    }
                }

                if (note.personalNote.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(Padding.x_small)
                    ) {
                        Text(text = note.personalNote, minLines = 2)
                    }
                }
            }
        }
    )
}


@Preview
@Composable
private fun NoteCardExtendedPreview() {
    val notes = listOf(
        NoteModel(
            LocalDate.now(),
            SexType.TRIBADISM,
            true,
            1,
            "something really-really long has written here just to see how it looks",
            5
        ),
        NoteModel(
            LocalDate.now(),
            SexType.VAGINAL,
            true,
            1,
            "something really-really long has written here just to see how it looks",
            5
        ),
        NoteModel(
            LocalDate.now(),
            SexType.MASTURBATION,
            true,
            1,
            "something really-really long has written here just to see how it looks",
            5
        ),
    )
    LazyColumn {
        itemsIndexed(notes) { index, note ->
            NoteExtendedItem(
                index = index,
                note = note,
                onEdit = {
                    //TODO implement edit
                },
                onDelete = {
                    //TODO implement note deletion (with undo action)
                }
            )
        }
    }
}