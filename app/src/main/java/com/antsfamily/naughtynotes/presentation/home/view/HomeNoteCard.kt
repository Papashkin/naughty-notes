package com.antsfamily.naughtynotes.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.home.model.HomeNoteCardModel
import com.antsfamily.naughtynotes.presentation.noteform.model.ExperienceType
import com.antsfamily.naughtynotes.presentation.noteform.view.RevealedText
import com.antsfamily.naughtynotes.presentation.util.PREVIEW_NOTES
import com.antsfamily.naughtynotes.presentation.util.formatToString
import com.antsfamily.naughtynotes.presentation.util.toColor
import com.antsfamily.naughtynotes.presentation.util.toDescriptionStringId
import com.antsfamily.naughtynotes.presentation.util.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding

@Composable
fun HomeNoteCard(
    note: HomeNoteCardModel,
) {
    Card(shape = MaterialTheme.shapes.large) {
        Box {
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ),
                leadingContent = {
                    Box(
                        modifier =
                            Modifier
                                .size(40.dp)
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
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 2.dp, y = -2.dp)
                                .size(12.dp)
                                .background(
                                    color = note.experienceType.toColor(),
                                    shape = CircleShape
                                )
                        )
                    }
                },
                headlineContent = {
                    RevealedText(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(bottom = Padding.x_small),
                        textId = if (note.types.size > 1) {
                            R.string.home_screen_recent_activity_multiple_activities
                        } else {
                            note.types.first().toStringId()
                        },
                    )
                },
                supportingContent = {
                    Text(
                        modifier = Modifier.width(240.dp),
                        text = stringResource(note.types.first().toDescriptionStringId()),
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
            Text(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(Padding.small),
                text = note.date.formatToString(),
                style = MaterialTheme.typography.labelSmall,
            )
        }
    }
}

@Preview
@Composable
private fun HomeNoteCard_Preview() {
    val activities = PREVIEW_NOTES.map {
        HomeNoteCardModel(
            it.date,
            it.types,
            ExperienceType.getTypeByValue(it.experienceRate)
        )
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(Padding.medium)
    ) {
        activities.forEach { note ->
            HomeNoteCard(note)
        }
    }
}
