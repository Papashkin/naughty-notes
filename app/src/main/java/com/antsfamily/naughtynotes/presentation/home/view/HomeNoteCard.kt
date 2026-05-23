package com.antsfamily.naughtynotes.presentation.home.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.util.PREVIEW_NOTES
import com.antsfamily.naughtynotes.presentation.util.formatToString
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun HomeNoteCard(
    note: NoteModel,
) {

    Card(shape = MaterialTheme.shapes.medium) {
        ListItem(
            modifier = Modifier.fillMaxWidth(),
            colors = ListItemDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
            headlineContent = {
                Text(
                    modifier = Modifier.padding(bottom = Padding.x_small),
                    text = note.types.joinToString(", "),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            supportingContent = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(Padding.x_small)
                ) {
                    Text(
                        text = stringResource(
                            R.string.home_screen_note_card_subtitle_date,
                            note.date.formatToString()
                        ),
                        style = MaterialTheme.typography.labelMedium,
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Padding.large)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                modifier = Modifier.size(14.dp),
                                imageVector = ImageVector.vectorResource(R.drawable.ic_heart_outlined),
                                contentDescription = "pleasure_rate",
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = note.experienceRate.toString(),
                                style = MaterialTheme.typography.labelMedium,
                                modifier = Modifier.padding(horizontal = Padding.x_small)
                            )
                        }

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
            }
        )
    }
}

@Preview
@Composable
private fun HomeNoteCard_Preview() {
    val note = PREVIEW_NOTES.random()
    HomeNoteCard(note)
}
