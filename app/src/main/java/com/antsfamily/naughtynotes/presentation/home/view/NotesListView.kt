package com.antsfamily.naughtynotes.presentation.home.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antsfamily.domain.model.NoteModel
import com.antsfamily.domain.model.SexType
import com.antsfamily.naughtynotes.R
import com.antsfamily.naughtynotes.presentation.createnote.model.toStringId
import com.antsfamily.naughtynotes.ui.theme.Padding
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

@Composable
fun NotesListView(
    modifier: Modifier = Modifier,
    notes: List<NoteModel>,
    isCurrentMonth: Boolean,
    onCreateNoteClick: () -> Unit,
    onShowAllClick: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = "Notes",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = Padding.small)
        )
        when {
            notes.size in 1..2 -> NotesListWithContent(notes)
            notes.size > 2 -> NotesListWithContentAndButton(notes.take(2)) {
                onShowAllClick()
            }
            isCurrentMonth -> NotesListEmptyWithCreateNoteButton(onCreateNoteClick)
            else -> NotesListEmpty()
        }
    }
}

@Composable
fun NotesListWithContentAndButton(
    notes: List<NoteModel>,
    onShowAllClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn {
            items(notes) {
                NoteCard(it)
            }
        }
        FilledIconButton(
            enabled = true,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(Padding.medium),
            onClick = { onShowAllClick() }
        ) {
            Text("Show all")
        }
    }
}

@Composable
fun NotesListWithContent(notes: List<NoteModel>) {
    LazyColumn {
        items(notes) {
            NoteCard(it)
        }
    }
}

@Composable
fun NotesListEmptyWithCreateNoteButton(onCreateNoteClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Padding.regular)
        ) {
            Text(
                text = stringResource(R.string.home_screen_empty_content_label),
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                modifier = Modifier.clickable { onCreateNoteClick() },
                text = AnnotatedString(
                    text = stringResource(R.string.home_screen_empty_content_button_create_note),
                    spanStyle = SpanStyle(color = MaterialTheme.colorScheme.primary)
                ),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun NotesListEmpty() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Padding.regular)
        ) {
            Icon(
                modifier = Modifier.size(64.dp).padding(Padding.small),
                imageVector = Icons.Outlined.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(R.string.home_screen_empty_content_label_2),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun NoteCard(note: NoteModel) {
    ListItem(
        overlineContent = {
            Text(note.date.format(DateTimeFormatter.ISO_DATE))
        },
        headlineContent = {
            Text(stringResource(note.type.toStringId()))
        },
        leadingContent = {
            Icon(Icons.Outlined.Favorite, null)
        },
        supportingContent = {
            Text(note.personalNote)
        }
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun NotesListViewPreview1() {
    NotesListView(
        notes = listOf(
            NoteModel(
                3643,
                LocalDate.of(2025, Month.JULY, 12),
                SexType.ANAL,
                isProtected = true,
                painRate = 2,
                rate = 4,
                personalNote = ""
            ),
            NoteModel(
                2452,
                LocalDate.of(2025, Month.JULY, 22),
                SexType.VAGINAL,
                isProtected = true,
                painRate = 2,
                rate = 4,
                personalNote = "That was something crazy"
            ),
            NoteModel(
                1231,
                LocalDate.of(2025, Month.JULY, 15),
                SexType.ANAL,
                isProtected = true,
                painRate = 2,
                rate = 4,
                personalNote = ""
            ),
            NoteModel(
                75765,
                LocalDate.of(2025, Month.JULY, 20),
                SexType.VAGINAL,
                isProtected = true,
                painRate = 2,
                rate = 4,
                personalNote = "That was something crazy"
            )
        ),
        isCurrentMonth = false,
        onCreateNoteClick = {},
        onShowAllClick = {}
    )
}