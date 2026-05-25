package com.antsfamily.naughtynotes.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceRate
import com.antsfamily.naughtynotes.presentation.noteform.view.RevealedText
import com.antsfamily.naughtynotes.presentation.util.PREVIEW_NOTES
import com.antsfamily.naughtynotes.presentation.util.formatToString
import com.antsfamily.naughtynotes.presentation.util.toBadgeStringId
import com.antsfamily.naughtynotes.presentation.util.toDescriptionStringId
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun HomeNoteCard(
    note: NoteModel,
) {
    Card(shape = MaterialTheme.shapes.large) {
        Box {
            ListItem(
                modifier = Modifier.fillMaxWidth(),
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ),
                leadingContent = {
                    Box(
                        modifier =
                            Modifier
                                .size(48.dp)
                                .padding(Padding.tiny)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainerHigh,
                                    shape = MaterialTheme.shapes.medium
                                )
                    ) {
                        Icon(
                            modifier = Modifier.align(Alignment.Center),
                            imageVector = ImageVector.vectorResource(R.drawable.ic_heart_outlined),
                            contentDescription = null,
                        )
                    }
                },
                headlineContent = {
                    RevealedText(
                        modifier = Modifier
                            .width(180.dp)
                            .padding(bottom = Padding.x_small),
                        textId = if (note.types.size > 1) {
                            R.string.home_scre_recent_activity_multiple_activities
                        } else {
                            note.types.first().toStringId()
                        },
                    )
                },
                supportingContent = {
                    Text(
                        modifier = Modifier.width(180.dp),
                        text = stringResource(note.types.first().toDescriptionStringId()),
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
            Column(
                modifier = Modifier.align(Alignment.TopEnd),
                horizontalAlignment = Alignment.End
            ) {
                Badge(
                    modifier = Modifier.padding(Padding.small)
                ) {
                    Text(
                        modifier =
                            Modifier
                                .padding(Padding.x_small),
                        text = stringResource(
                            ExperienceRate
                                .getTypeByValue(note.experienceRate)
                                .toBadgeStringId()
                        ),
                        style = MaterialTheme.typography.labelSmall,
                    )
                }
                Text(
                    modifier = Modifier.padding(horizontal = Padding.regular),
                    text = note.date.formatToString(),
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeNoteCard_Preview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(Padding.medium)
    ) {
        PREVIEW_NOTES.forEach { note ->
            HomeNoteCard(note)
        }
    }
}
